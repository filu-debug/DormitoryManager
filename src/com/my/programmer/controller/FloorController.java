package com.my.programmer.controller;

import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Floor;
import com.my.programmer.entity.HouseParent;
import com.my.programmer.entity.User;
import com.my.programmer.page.Page;
import com.my.programmer.service.FloorService;
import com.my.programmer.service.HouseParentService;
import com.my.programmer.service.impl.FloorServiceImpl;
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

@RequestMapping("/floor")
@Controller
public class FloorController {
    @Autowired
    private FloorService floorService;

    @Autowired
    HouseParentService houseParentService;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        //寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
        model.setViewName("floor/floor_list");
        //获取楼管列表
        model.addObject("HpList",houseParentService.findAll());
        return model;
    }

    @RequestMapping(value ="/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "floorNo",defaultValue = "",required = false)String floorNo,
            @RequestParam(value = "floorType",defaultValue = "",required = false)String floorType,
            Page page
    ) {
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        //%%表示查询所有用户
        queryMap.put("floorNo","%"+floorNo+"%");
        queryMap.put("floorType","%"+floorType+"%");
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        //easyui根据rows和total自动计算分页数量
        ret.put("rows",floorService.findList(queryMap));
        ret.put("total",floorService.getTotal(queryMap));
        return ret;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Floor floor){
        Map<String,String> ret = new HashMap<>();
        if(floor==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(floor.getFloorNo())){
            ret.put("type","error");
            ret.put("msg","楼栋编号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(floor.getFloorManager())){
            ret.put("type","error");
            ret.put("msg","楼管不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(floor.getFloorType())){
            ret.put("type","error");
            ret.put("msg","类型不能为空");
            return  ret;
        }
        if((floorService.findByFloorNo(floor.getFloorNo()))!=null){
            ret.put("type","error");
            ret.put("msg","该楼栋已存在");
            return ret;
        }
        if(floorService.add(floor)<=0){
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
    public Map<String,String> edit(Floor floor){
        Map<String,String> ret = new HashMap<>();
        if(floor==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(floor.getFloorNo())){
            ret.put("type","error");
            ret.put("msg","楼栋编号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(floor.getFloorType())){
            ret.put("type","error");
            ret.put("msg","类型不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(floor.getFloorManager())){
            ret.put("type","error");
            ret.put("msg","楼管不能为空");
            return  ret;
        }
        //1、根据用户输入的用户名查询数据库
        Floor existsFloor = floorService.findByFloorNo(floor.getFloorNo());
        if(existsFloor!=null){
            //2、再将查到的那条记录的ID与修改框中回显的原用户的ID进行比对，如果ID不同，表示用户输入的用户名已经被使用了
            if(existsFloor.getId()!=floor.getId()){
                ret.put("type","error");
                ret.put("msg","该楼栋已存在");
                return ret;
            }
        }
        if(floorService.edit(floor)<=0){
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
            Floor floor = floorService.findFloorById(id);
            List<Dormitory> existsDrom = floorService.queryByFloorFromDorm(floor.getFloorNo());
            if(existsDrom!=null){
                ret.put("type","error");
                ret.put("msg","抱歉，请先确定您要删除的寝室中是否有正在使用的寝室");
                return  ret;
            }
            count = floorService.delete(id);
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
