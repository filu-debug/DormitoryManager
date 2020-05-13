package com.my.programmer.controller;

import com.my.programmer.entity.*;
import com.my.programmer.page.Page;
import com.my.programmer.service.DormiService;
import com.my.programmer.service.EwBlanceService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 楼管水电费余额管理,学生查询水电费，共用控制器
 */
@RequestMapping("/ewblance")
@Controller
public class EwBlanceController {
    @Autowired
    DormiService dormiService;

    @Autowired
    EwBlanceService ewBlanceService;

    //将楼管对象提到外面
    private HouseParent houseParent = null;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model,HttpServletRequest request) {
        //寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
        model.setViewName("ew/ewblance_list");
        String userType = (String) request.getSession().getAttribute("userType");
        if("4".equals(userType)) {
            houseParent = (HouseParent) request.getSession().getAttribute("user");
            //如果是宿管登录，就获取当前楼栋的所有宿舍的宿舍列表（用于添加水电费信息）
            List<Dormitory> dormList = ewBlanceService.findAllByFloorNo(houseParent.getFloorNo());
            model.addObject("dormList",dormList);
        }
        return model;
    }

    @RequestMapping(value ="/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "dormNo",defaultValue = "",required = false)String dormNo,
            HttpServletRequest request,
            Page page
    ) {
        String userType = (String) request.getSession().getAttribute("userType");
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        //%%表示查询所有
        if("4".equals(userType)){
            queryMap.put("floorNo","%"+houseParent.getFloorNo()+"%");
            queryMap.put("dormNo","%"+dormNo+"%");
            ret.put("total",ewBlanceService.getTotal(queryMap));
        }else if("2".equals(userType)){
            Student student = (Student) request.getSession().getAttribute("user");
            queryMap.put("dormNo","%"+student.getDormNo()+"%");
            queryMap.put("floorNo","%"+student.getFloorNo()+"%");
        }
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //easyui根据rows和total自动计算分页数量
        //rows保存查询到的列表
        ret.put("rows",ewBlanceService.findList(queryMap));
        return ret;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Utilities utilities){
        Map<String,String> ret = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        if(utilities==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(utilities.getEleBalance())){
            ret.put("type","error");
            ret.put("msg","请输入电费余额");
            return  ret;
        }
        if(StringUtils.isEmpty(utilities.getWaterBalance())){
            ret.put("type","error");
            ret.put("msg","请输入水费余额");
            return  ret;
        }
        if(StringUtils.isEmpty(utilities.getDormNo())){
            ret.put("type","error");
            ret.put("msg","请选择宿舍");
            return  ret;
        }
        map.put("dormNo",utilities.getDormNo());
        map.put("floorNo",houseParent.getFloorNo());
        if((ewBlanceService.findAllByDormNoAndFloorNo(map))!=null){
            ret.put("type","error");
            ret.put("msg","请勿重复添加");
            return ret;
        }
        utilities.setFloorNo(houseParent.getFloorNo());
        if(ewBlanceService.add(utilities)<=0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","添加成功！");
        return ret;
    }

    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Utilities utilities){
        Map<String,Object> map = new HashMap<>();
        Map<String,String> ret = new HashMap<>();
        if(utilities==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(utilities.getEleBalance())){
            ret.put("type","error");
            ret.put("msg","请输入电费余额");
            return  ret;
        }
        if(StringUtils.isEmpty(utilities.getWaterBalance())){
            ret.put("type","error");
            ret.put("msg","请输入水费余额");
            return  ret;
        }

        if(ewBlanceService.edit(utilities)<=0){
            ret.put("type","error");
            ret.put("msg","修改失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功！");
        return ret;
    }

    @RequestMapping(value = "/delete",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> delete(
            @RequestParam(value = "ids[]",defaultValue = "" ,required = false)long[]ids
    ) {
        Map<String, String> ret = new HashMap<>();
        if(ids==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        long count = 0;
        for(long id:ids){
            count = ewBlanceService.delete(id);
        }
        if(count<=0){
            ret.put("type","error");
            ret.put("msg","删除失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","删除成功！");
        return ret;
    }
}
