package com.my.programmer.controller;

import com.my.programmer.entity.StuRepai;
import com.my.programmer.entity.Worker;
import com.my.programmer.page.Page;
import com.my.programmer.service.ReceiveService;
import com.my.programmer.service.WkRepaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/wkrepai")
@Controller
public class WkRepaiController {

    @Autowired
    WkRepaiService wkRepaiService;

    @Autowired
    ReceiveService receiveService;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        model.setViewName("repai/wkrepai_list");
        return model;
    }

    @RequestMapping(value ="/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            Page page
    ) {
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        ret.put("rows",wkRepaiService.findList(queryMap));//easyui根据rows和total自动计算分页数量
        ret.put("total",wkRepaiService.getTotal(queryMap));
        return ret;
    }

    /**
     * 接取任务
     * @param stuRepais
     * @return
     */
    @Transactional
    @RequestMapping(value = "/receive",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> receive(
            @RequestBody StuRepai[] stuRepais, HttpServletRequest request
            ) {
        Map<String, String> ret = new HashMap<>();
        if(stuRepais==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        long count = 0;
        String userType = (String) request.getSession().getAttribute("userType");
        Worker worker = null;
        if(userType.equals("3")){
            worker = (Worker) request.getSession().getAttribute("user");
        }
        for(StuRepai stuRepai:stuRepais){
            StuRepai existsStuRepai = receiveService.findById(stuRepai.getId());
            if(existsStuRepai==null){
                Map<String,Object> map = new HashMap<>();
                map.put("reMan",worker.getName());
                map.put("reManPhone",worker.getPhone());
                map.put("id",stuRepai.getId());
                count+=receiveService.setHpStateById(stuRepai.getId());
                count+=receiveService.setStuStateById(stuRepai.getId());
                stuRepai.setState("待完成");
                stuRepai.setStarttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                stuRepai.setWorkNo(worker.getWorkNo());
                count+=receiveService.add(stuRepai);
                count+=receiveService.setHpReManAndReManPhone(map);
                count+=receiveService.setStuReManAndReManPhone(map);
                count+=receiveService.setWkstate(stuRepai.getId());
            }
        }
        if(count<6){
            if(count==0){
                ret.put("type","error");
                ret.put("msg","该任务您已经接取过了");
                return  ret;
            }
            ret.put("type","error");
            ret.put("msg","接取失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","接取成功！");
        return ret;
    }
}
