package com.my.programmer.controller;

import com.my.programmer.entity.HouseParent;
import com.my.programmer.entity.StuRepai;
import com.my.programmer.page.Page;
import com.my.programmer.service.HpRepaiService;
import com.my.programmer.service.StuRepaiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;

@RequestMapping("/hprepai")
@Controller
public class HpRepaiController {

    @Autowired
    HpRepaiService hpRepaiService;

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
     * @param ids
     * @return
     */
    @RequestMapping(value = "/setState",method = RequestMethod.POST)
    @ResponseBody
    public Map<String,String> setState(
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
            //count = hpRepaiService.delete(id);
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
        for(long id:ids){
            count += hpRepaiService.setHpStateById(id);
            count +=hpRepaiService.setStuStateById(id);
        }
        if(count<2){
            ret.put("type","error");
            ret.put("msg","操作失败");
            return  ret;
        }
        ret.put("type","success");
        ret.put("msg","已拒绝！");
        return ret;
    }
}
