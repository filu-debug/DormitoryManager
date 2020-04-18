package com.my.programmer.controller;

import com.my.programmer.entity.HouseParent;
import com.my.programmer.entity.StuRepai;
import com.my.programmer.page.Page;
import com.my.programmer.service.HpRepaiService;
import com.my.programmer.service.StuRepaiService;
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

@RequestMapping("/hprepai")
@Controller
public class HpRepaiController {

    @Autowired
    HpRepaiService hpRepaiService;

    @Autowired
    WkRepaiService wkRepaiService;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        //寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
        model.setViewName("repai/hprepai_list");
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
        if(userType.equals("4")){
            HouseParent houseParent = (HouseParent) request.getSession().getAttribute("user");
            queryMap.put("floorNo",houseParent.getFloorNo());
        }
        Map<String,Object> ret = new HashMap<>();
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        ret.put("rows",hpRepaiService.findList(queryMap));//easyui根据rows和total自动计算分页数量
        ret.put("total",hpRepaiService.getTotal(queryMap));
        return ret;
    }

    /**
     * 发布任务
     * @return
     */
    @Transactional
    @RequestMapping(value = "/publish",method = RequestMethod.POST)
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
            StuRepai existsStuRepai = hpRepaiService.findById(stuRepai.getId());
            if(stuRepai.getState()!="已发布"){
                Map<String,Object> map = new HashMap<>();
                count+=wkRepaiService.setHpStateById(stuRepai.getId());
                count+=wkRepaiService.setStuStateById(stuRepai.getId());
                stuRepai.setState("新的任务");
                stuRepai.setStarttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
                count+=wkRepaiService.add(stuRepai);
            }
        }
        if(count<3){
            if(count==0&&!stuRepais[0].getState().equals("新的申请")){
                ret.put("type","error");
                ret.put("msg","该任务已经发布过了");
                return  ret;
            }
            ret.put("type","error");
            ret.put("msg","发布失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","发布成功！");
        return ret;
    }

    /**
     * 拒绝任务
     * @param ids
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/letReturn",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> letReturn(
            @RequestParam(value = "ids[]",defaultValue = "" ,required = false)long[]ids
    ) {
        Map<String, String> ret = new HashMap<>();
        if(ids==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        long count = 0;
        StuRepai existsStuRepai = null;
        for(long id:ids){
            existsStuRepai = hpRepaiService.findById(id);
            if(existsStuRepai==null){
                count += hpRepaiService.setHpStateById(id);
                count +=hpRepaiService.setStuStateById(id);
            }
        }
        if(count<2){
            if(count==0&&existsStuRepai.getState().equals("已拒绝")){
                ret.put("type","error");
                ret.put("msg","该任务已拒绝过了");
                return  ret;
            }
            ret.put("type","error");
            ret.put("msg","操作失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","已拒绝！");
        return ret;
    }
}
