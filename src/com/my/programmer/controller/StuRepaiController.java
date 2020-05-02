package com.my.programmer.controller;

import com.my.programmer.entity.StuRepai;
import com.my.programmer.entity.Student;
import com.my.programmer.entity.User;
import com.my.programmer.page.Page;
import com.my.programmer.service.HpRepaiService;
import com.my.programmer.service.StuRepaiService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("sturepai")
@Controller
public class StuRepaiController {

    @Autowired
    StuRepaiService stuRepaiService;

    @Autowired
    HpRepaiService hpRepaiService;

    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        //寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
        model.setViewName("repai/sturepai_list");
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
        if(userType.equals("2")){
            Student student = (Student) request.getSession().getAttribute("user");
            queryMap.put("floorNo",student.getFloorNo());
            queryMap.put("dormNo",student.getDormNo());
        }
        Map<String,Object> ret = new HashMap<>();
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        ret.put("rows",stuRepaiService.findList(queryMap));//easyui根据rows和total自动计算分页数量
        ret.put("total",stuRepaiService.getTotal(queryMap));
        return ret;
    }

    @Transactional(rollbackFor = Exception.class)//添加事务控制,任何异常都回滚
    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(StuRepai stuRepai, HttpServletRequest request){
        String userType = (String) request.getSession().getAttribute("userType");
        Map<String,String> ret = new HashMap<>();
        if(stuRepai==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(userType.equals("2")){
            Student student = (Student) request.getSession().getAttribute("user");
            stuRepai.setStuName(student.getStuName());
            stuRepai.setFloorNo(student.getFloorNo());
            stuRepai.setDormNo(student.getDormNo());
            stuRepai.setPhone(student.getPhone());
        }
        if(StringUtils.isEmpty(stuRepai.getRetype())){
            ret.put("type","error");
            ret.put("msg","维修类别不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(stuRepai.getDiscr())){
            ret.put("type","error");
            ret.put("msg","描述不能为空");
            return  ret;
        }
        stuRepai.setStarttime(new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        stuRepai.setState("等待审批");
        if(stuRepaiService.add(stuRepai)<=0){
            ret.put("type","error");
            ret.put("msg","申请失败");
            return  ret;
        }
        stuRepai.getId();
        stuRepai.setState("新的申请");
        if(hpRepaiService.add(stuRepai)<=0){
            ret.put("type","error");
            ret.put("msg","申请失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","申请完成，请耐心等待管理员处理！");
        return ret;
    }

    /**
     * 评价
     * @return
     */
    @Transactional(rollbackFor = Exception.class)
    @RequestMapping(value = "/eval",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> edit(String eval,long id){
        Map<String,String> ret = new HashMap<>();
        Map<String,Object> map = new HashMap<>();
        if(eval==""||id==0){
            ret.put("type","error");
            ret.put("msg","数据绑定出错，请联系管理员！");
            return ret;
        }

        String stuState = stuRepaiService.getStateById(id);
        if(!stuState.equals("已完成")){
            ret.put("type","error");
            ret.put("msg","该任务未完成，不能进行评价！");
            return ret;
        }
        String stuEval = stuRepaiService.getEvalById(id);
        if(!stuEval.equals("默认好评")&&stuEval!=""&&stuEval!=null){
            ret.put("type","error");
            ret.put("msg","您已经评价过该任务了！");
            return ret;
        }
        map.put("id",id);
        map.put("eval",eval);
        int count = 0;
        count = stuRepaiService.setEval(map);
        count += stuRepaiService.setEvalToReceive(map);
        if(count<2){
            ret.put("type","error");
            ret.put("msg","评价出错");
            return ret;
        }
        ret.put("type","success");
        ret.put("msg","评价成功！");
        return ret;
    }
}
