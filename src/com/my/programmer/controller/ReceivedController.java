package com.my.programmer.controller;

import com.my.programmer.entity.StuRepai;
import com.my.programmer.entity.Student;
import com.my.programmer.entity.Worker;
import com.my.programmer.page.Page;
import com.my.programmer.service.ReceiveService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/accepted")
@Controller
public class ReceivedController {
    @Autowired
    ReceiveService receiveService;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        //寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
        model.setViewName("repai/received_list");
        return model;
    }

    @RequestMapping(value ="/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            HttpServletRequest request,
            Page page
    ) {
        String userType = (String) request.getSession().getAttribute("userType");
        Map<String,Object> queryMap = new HashMap<>();
        if(userType.equals("3")){
            Worker worker = (Worker) request.getSession().getAttribute("user");
            queryMap.put("workNo",worker.getWorkNo());
        }
        Map<String,Object> ret = new HashMap<>();
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        ret.put("rows",receiveService.findList(queryMap));//easyui根据rows和total自动计算分页数量
        ret.put("total",receiveService.getTotal(queryMap));
        return ret;
    }

    @Transactional
    @RequestMapping(value = "/complete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> publish(
            @RequestBody StuRepai[] stuRepais
    ) {
        Map<String, String> ret = new HashMap<>();
        if(stuRepais==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        long count = 0;
        int length = stuRepais.length;
        for(StuRepai stuRepai:stuRepais){
            Map<String,Object> map = new HashMap<>();
            map.put("state","已完成");
            map.put("id",stuRepai.getId());
            count+=receiveService.setStuStateByMap(map);
            count+=receiveService.setHpStateByMap(map);
            count+=receiveService.setWkstateByMap(map);
            count+=receiveService.setReceivedStateByMap(map);
        }
        if(count<4){
            ret.put("type","error");
            ret.put("msg","操作失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","任务状态已更改！");
        return ret;
    }
}
