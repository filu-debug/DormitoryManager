package com.my.programmer.controller;

import com.my.programmer.entity.Dormitory;
import com.my.programmer.entity.Student;
import com.my.programmer.page.Page;
import com.my.programmer.service.DormiService;
import com.my.programmer.service.FloorService;
import com.my.programmer.service.StudentService;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Aspect
@RequestMapping("/student")
@Controller
public class StudentController {

    @Autowired
    StudentService studentService;

    @Autowired
    DormiService dormiService;

    @Autowired
    FloorService floorService;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        //寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
        model.setViewName("student/student_list");
        model.addObject("dormList",dormiService.findAll());
        model.addObject("floorList",floorService.findAll());
        model.addObject("boyFloors",floorService.getBoyDorms());
        model.addObject("girlDorms",floorService.getGrilDorms());
        return model;
    }

    @RequestMapping(value ="/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "stuNo",defaultValue = "",required = false)String stuNo,
            @RequestParam(value = "stuName",defaultValue = "",required = false)String stuName,
            Page page
    ) {
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        //%%表示查询所有用户
        queryMap.put("stuNo","%"+stuNo+"%");
        queryMap.put("stuName","%"+stuName+"%");
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        //easyui根据rows和total自动计算分页数量
        ret.put("rows",studentService.findList(queryMap));
        ret.put("total",studentService.getTotal(queryMap));
        return ret;
    }

    /*@ResponseBody
    @RequestMapping("/dormType")
    public Map<String,Object> mytype(@RequestParam("dormType") String dormType){
        Map<String,Object> ret = new HashMap<>();
        if(dormType.equals("男")){
            ret.put("sexFloors",floorService.getBoyDorms());
        }
        ret.put("sexFloors",floorService.getGrilDorms());
        //ret.put("dormType",dormType);
        return ret;
    }*/

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Student student/*, HttpSession session,*/
                                  /*@RequestParam("dormType") String dormType*/){
        //session.setAttribute("dormType",dormType);
        Map<String,String> ret = new HashMap<>();
        if(student==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(!floorService.findByFloorNo(student.getFloorNo()).getFloorType().contains(student.getGender())){
            ret.put("type","error");
            ret.put("msg","寝室类型不对");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getStuNo())){
            ret.put("type","error");
            ret.put("msg","学号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getStuName())){
            ret.put("type","error");
            ret.put("msg","姓名不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getCollege())){
            ret.put("type","error");
            ret.put("msg","学院不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getMajor())){
            ret.put("type","error");
            ret.put("msg","专业不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getClazz())){
            ret.put("type","error");
            ret.put("msg","班级不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getFloorNo())){
            ret.put("type","error");
            ret.put("msg","楼栋不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getDormNo())){
            ret.put("type","error");
            ret.put("msg","宿舍不能为空");
            return  ret;
        }
        if((studentService.findByStuNo(student.getStuNo()))!=null){
            ret.put("type","error");
            ret.put("msg","该学生已存在");
            return ret;
        }
        Map<String,Object> bedMap = new HashMap<>();
        bedMap.put("dormNo",student.getDormNo());
        bedMap.put("floorNo",student.getFloorNo());
        Integer liveCount = studentService.liveCount(bedMap);
        bedMap.put("stayCount",liveCount+1);
        Integer bedCount = dormiService.getBedCount(bedMap);
        if(liveCount!=null){
            if(liveCount==bedCount){
                ret.put("type","error");
                ret.put("msg","该宿舍没有空床位了");
                return ret;
            }
        }
        if(studentService.add(student)<=0){
            ret.put("type","error");
            ret.put("msg","添加失败");
            return  ret;
        }
        studentService.setStayCount(bedMap);
        ret.put("type","success");
        ret.put("msg","添加成功！");
        return ret;
    }

    @Transactional//启用事务
    @RequestMapping(value = "/edit",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(Student student){
        Map<String,String> ret = new HashMap<>();
        if(student==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(!floorService.findByFloorNo(student.getFloorNo()).getFloorType().contains(student.getGender())){
            ret.put("type","error");
            ret.put("msg","寝室类型不对");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getStuNo())){
            ret.put("type","error");
            ret.put("msg","学号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getStuName())){
            ret.put("type","error");
            ret.put("msg","姓名不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getCollege())){
            ret.put("type","error");
            ret.put("msg","学院不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getMajor())){
            ret.put("type","error");
            ret.put("msg","专业不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getClazz())){
            ret.put("type","error");
            ret.put("msg","班级不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getFloorNo())){
            ret.put("type","error");
            ret.put("msg","楼栋不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(student.getDormNo())){
            ret.put("type","error");
            ret.put("msg","宿舍不能为空");
            return  ret;
        }
        Student exisStudent = studentService.findByStuNo(student.getStuNo());
        if(exisStudent!=null){
            if(student.getId()!=exisStudent.getId()){
                ret.put("type","error");
                ret.put("msg","该学生已存在");
                return ret;
            }
        }
        Map<String,Object> bedMap = new HashMap<>();
        bedMap.put("dormNo",student.getDormNo());
        bedMap.put("floorNo",student.getFloorNo());

        //只有想更换的寝室与当前寝室在同一栋楼，且寝室编号不一样的情况下，才会去考虑寝室床位问题
        if(exisStudent.getFloorNo()==student.getFloorNo()&&exisStudent.getDormNo()!=student.getDormNo()){
            Integer liveCount = studentService.liveCount(bedMap);
            bedMap.put("stayCount",liveCount+1);
            Integer bedCount = dormiService.getBedCount(bedMap);
            if(liveCount!=null){
                if(liveCount==bedCount){
                    ret.put("type","error");
                    ret.put("msg","该宿舍没有空床位了");
                    return ret;
                }
                studentService.setStayCount(bedMap);
            }
        }
        if(studentService.edit(student)<=0){
            ret.put("type","error");
            ret.put("msg","修改失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","修改成功！");
        return ret;
    }

    @Transactional(rollbackFor = {Exception.class,RuntimeException.class})
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
            Student student = studentService.getDormNoById(id);
            Map<String,Object> bedMap = new HashMap<>();
            bedMap.put("dormNo",student.getDormNo());
            bedMap.put("floorNo",student.getFloorNo());
            studentService.subStayCount(bedMap);
            count = studentService.delete(id);

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
