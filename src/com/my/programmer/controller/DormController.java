package com.my.programmer.controller;

import com.my.programmer.dao.DormDao;
import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Floor;
import com.my.programmer.page.Page;
import com.my.programmer.service.DormiService;
import com.my.programmer.service.FloorService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequestMapping("/dorm")
@Controller
public class DormController {
    @Autowired
    DormiService dormiService;

    @Autowired
    FloorService floorService;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        //寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
        model.setViewName("dorm/dorm_list");
        model.addObject("floorList",floorService.findAll());
        return model;
    }


    @RequestMapping(value ="/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "floorNo",defaultValue = "",required = false)String floorNo,
            @RequestParam(value = "dormNo",defaultValue = "",required = false)String dormNo,
            Page page
    ) {
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        //%%表示查询所有用户
        queryMap.put("floorNo","%"+floorNo+"%");
        queryMap.put("dormNo","%"+dormNo+"%");
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        //easyui根据rows和total自动计算分页数量
        ret.put("rows",dormiService.findList(queryMap));
        ret.put("total",dormiService.getTotal(queryMap));
        return ret;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Dormitory dormitory){
        Map<String,String> ret = new HashMap<>();
        if(dormitory==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(dormitory.getFloorNo())){
            ret.put("type","error");
            ret.put("msg","楼栋编号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(dormitory.getDormNo())){
            ret.put("type","error");
            ret.put("msg","宿舍编号不能为空");
            return  ret;
        }
        if(dormitory.getBedCount()==null){
            ret.put("type","error");
            ret.put("msg","床位不能为空");
            return  ret;
        }
        //如果用户添加的宿舍已存在，且所属楼栋和用户设置的楼栋编号一样，则表示重复添加
        List<Dormitory> dormitoryList = dormiService.findByDormNo(dormitory.getDormNo());
        if(dormitoryList!=null){
            for(Dormitory exisdorm :dormitoryList){
                if(dormitory.getFloorNo().equals(exisdorm.getFloorNo())){
                    ret.put("type","error");
                    ret.put("msg","该宿舍已存在");
                    return ret;
                }
            }
        }
        if(dormiService.add(dormitory)<=0){
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
    public Map<String,String> edit(Dormitory dormitory){
        Map<String,String> ret = new HashMap<>();
        if(dormitory==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(dormitory.getFloorNo())){
            ret.put("type","error");
            ret.put("msg","楼栋编号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(dormitory.getDormNo())){
            ret.put("type","error");
            ret.put("msg","宿舍编号不能为空");
            return  ret;
        }
        if(dormitory.getBedCount()==null){
            ret.put("type","error");
            ret.put("msg","床位不能为空");
            return  ret;
        }
        //1、根据用户输入的宿舍编号进入数据库校验是否存在
        List<Dormitory> dormitoryList = dormiService.findByDormNo(dormitory.getDormNo());
        if(dormitoryList!=null){
            for(Dormitory exisdorm :dormitoryList){
                if(dormitory.getFloorNo().equals(exisdorm.getFloorNo())){
                    ret.put("type","error");
                    ret.put("msg","该宿舍已存在");
                    return ret;
                }
            }
        }
        if(dormiService.edit(dormitory)<=0){
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
            count = dormiService.delete(id);
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
