package com.my.programmer.controller;

import com.my.programmer.entity.User;
import com.my.programmer.entity.Worker;
import com.my.programmer.page.Page;
import com.my.programmer.service.WorkerService;
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
import java.util.Map;

@RequestMapping("/worker")
@Controller
public class WorkerController {
    @Autowired
    WorkerService workerService;
    
    @RequestMapping("/list")
    public ModelAndView index(ModelAndView model) {
        //寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
        model.setViewName("worker/worker_list");
        return model;
    }

    @RequestMapping(value ="/get_list",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,Object> getList(
            @RequestParam(value = "name",defaultValue = "",required = false)String name,
            @RequestParam(value = "workNo",defaultValue = "",required = false)String workNo,
            HttpServletRequest request,
            Page page
    ) {
        String userType = (String) request.getSession().getAttribute("userType");
        Map<String,Object> ret = new HashMap<>();
        Map<String,Object> queryMap = new HashMap<>();
        //%%表示查询所有用户
        if(userType.equals("3")){
            Worker worker = (Worker) request.getSession().getAttribute("user");
            queryMap.put("name","%"+worker.getName()+"%");
            queryMap.put("workNo","%"+worker.getWorkNo()+"%");
        }else {
            queryMap.put("name","%"+name+"%");
            queryMap.put("workNo","%"+workNo+"%");
        }
        //分页偏移量
        queryMap.put("offset",page.getOffset());
        //每页显示的记录条数
        queryMap.put("pageSize",page.getRows());
        //rows保存查询到的列表
        ret.put("rows",workerService.findList(queryMap));//easyui根据rows和total自动计算分页数量
        ret.put("total",workerService.getTotal(queryMap));
        return ret;
    }

    @RequestMapping(value = "/add",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> add(Worker worker){
        Map<String,String> ret = new HashMap<>();
        if(worker==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(StringUtils.isEmpty(worker.getWorkNo())){
            ret.put("type","error");
            ret.put("msg","工号不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(worker.getName())){
            ret.put("type","error");
            ret.put("msg","姓名不能为空");
            return  ret;
        }
        if(worker.getAge()==null){
            ret.put("type","error");
            ret.put("msg","年龄不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(worker.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(worker.getPhone())){
            ret.put("type","error");
            ret.put("msg","电话不能为空");
            return  ret;
        }
        if((workerService.findByPhone(worker.getPhone()))!=null){
            ret.put("type","error");
            ret.put("msg","该号码已存在");
            return ret;
        }
        if((workerService.findByWorkNo(worker.getWorkNo()))!=null){
            ret.put("type","error");
            ret.put("msg","该工人已存在");
            return ret;
        }
        if(workerService.add(worker)<=0){
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
    public Map<String,String> edit(Worker worker,HttpServletRequest request){
        Map<String,String> ret = new HashMap<>();
        String userType = (String) request.getSession().getAttribute("userType");
        if(worker==null){
            ret.put("type","error");
            ret.put("msg","数据绑定出错,请联系开发者");
            return  ret;
        }
        if(userType.equals("1")){
            if(StringUtils.isEmpty(worker.getWorkNo())){
                ret.put("type","error");
                ret.put("msg","工号不能为空");
                return  ret;
            }
            if(StringUtils.isEmpty(worker.getName())){
                ret.put("type","error");
                ret.put("msg","姓名不能为空");
                return  ret;
            }
            if(worker.getAge()==null){
                ret.put("type","error");
                ret.put("msg","年龄不能为空");
                return  ret;
            }
        }
        if(StringUtils.isEmpty(worker.getPassword())){
            ret.put("type","error");
            ret.put("msg","密码不能为空");
            return  ret;
        }
        if(StringUtils.isEmpty(worker.getPhone())){
            ret.put("type","error");
            ret.put("msg","电话不能为空");
            return  ret;
        }
        if((workerService.findByPhone(worker.getPhone()))!=null){
            ret.put("type","error");
            ret.put("msg","该号码已存在");
            return ret;
        }
        if(userType.equals("1")){
            Worker existsWorker = workerService.findByWorkNo(worker.getWorkNo());
            if(existsWorker!=null){
                //2、再将查到的那条记录的ID与修改框中回显的ID进行比对，如果ID不同，表示用户输入的工号已经被使用了
                if(existsWorker.getId()!=worker.getId()){
                    ret.put("type","error");
                    ret.put("msg","该用户已存在");
                    return ret;
                }
            }
        }
        if(workerService.edit(worker)<=0){
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
            count = workerService.delete(id);
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
