package com.my.programmer.controller;

import com.my.programmer.entity.Floor;
import com.my.programmer.entity.HouseParent;
import com.my.programmer.page.Page;
import com.my.programmer.service.FloorService;
import com.my.programmer.service.HouseParentService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@RequestMapping("/houseparent")
@Controller
public class HouseParentController {
    @Autowired
    HouseParentService houseParentService;

    @Autowired
    FloorService floorService;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        model.setViewName("houseparent/houseparent_list");
        model.addObject("floorList",floorService.findAll());
        return model;
    }

    @RequestMapping(value ="/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",defaultValue = "",required = false)String name,
            Page page
    ) {
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        //%%表示查询所有用户
        queryMap.put("name","%"+name+"%");
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        //easyui根据rows和total自动计算分页数量
        ret.put("rows",houseParentService.findList(queryMap));
        ret.put("total",houseParentService.getTotal(queryMap));
        return ret;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(HouseParent houseParent){
        Map<String,String> ret = new HashMap<>();
        if(houseParent==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(houseParent.getWorkNo())){
            ret.put("type","error");
            ret.put("msg","工号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(houseParent.getName())){
            ret.put("type","error");
            ret.put("msg","姓名不能为空");
            return  ret;
        }
        if(houseParent.getPhone()==null){
            ret.put("type","error");
            ret.put("msg","电话不能为空");
            return  ret;
        }
        if((houseParentService.findByWorkNo(houseParent.getWorkNo()))!=null){
            ret.put("type","error");
            ret.put("msg","该楼管已存在");
            return ret;
        }
        if((houseParentService.findByPhone(houseParent.getPhone()))!=null){
            ret.put("type","error");
            ret.put("msg","请输入您自己的手机号");
            return ret;
        }
        HouseParent resHp = houseParentService.findByFloorNo(houseParent.getFloorNo());
        if((resHp!=null)){
            ret.put("type","error");
            ret.put("msg","抱歉，该楼栋目前已由"+resHp.getName()+"负责管理");
            return ret;
        }
        if(houseParentService.add(houseParent)<=0){
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
    public Map<String,String> edit(HouseParent houseParent){
        Map<String,String> ret = new HashMap<>();
        if(houseParent==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(houseParent.getWorkNo())){
            ret.put("type","error");
            ret.put("msg","工号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(houseParent.getName())){
            ret.put("type","error");
            ret.put("msg","姓名不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(houseParent.getPhone())){
            ret.put("type","error");
            ret.put("msg","电话不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(houseParent.getFloorNo())){
            ret.put("type","error");
            ret.put("msg","负责楼栋不能为空");
            return  ret;
        }
        //1、根据用户输入的工号查询数据库
        HouseParent existsHp = houseParentService.findByWorkNo(houseParent.getWorkNo());
        if(existsHp!=null){
            //2、再将查到的那条记录的ID与修改框中回显的原用户的ID进行比对，如果ID不同，表示用户输入的工号已经被使用了
            if(existsHp.getId()!=houseParent.getId()){
                ret.put("type","error");
                ret.put("msg","该楼管已存在");
                return ret;
            }
        }
        if(houseParentService.edit(houseParent)<=0){
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
            count = houseParentService.delete(id);
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
