package com.my.programmer.controller;

import com.my.programmer.entity.User;
import com.my.programmer.page.Page;
import com.my.programmer.service.UserService;
import com.my.programmer.service.impl.UserServiceImpl;
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

@RequestMapping("/user")
@Controller
public class UserController {
	@Autowired
	private UserService userService = new UserServiceImpl();

	@RequestMapping("/list")
	public ModelAndView index(ModelAndView model) {
		//寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
		model.setViewName("user/user_list");
		return model;
	}

	@RequestMapping(value ="/get_list",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,Object> getList(
			@RequestParam(value = "username",defaultValue = "",required = false)String username,
			Page page
	) {
		Map<String,Object> ret = new HashMap<>();
		Map<String,Object> queryMap = new HashMap<>();
		//%%表示查询所有用户
		queryMap.put("username","%"+username+"%");
		//分页偏移量
		queryMap.put("offset",page.getOffset());
		//每页显示的记录条数
		queryMap.put("pageSize",page.getRows());
		//rows保存查询到的列表
		ret.put("rows",userService.findList(queryMap));//easyui根据rows和total自动计算分页数量
		ret.put("total",userService.getTotal(queryMap));
		return ret;
	}

	@RequestMapping(value = "/add",method = RequestMethod.POST)
	@ResponseBody
	public Map<String,String> add(User user){
		Map<String,String> ret = new HashMap<>();
		if(user==null){
			ret.put("type","error");
			ret.put("msg","数据绑定出错,请联系开发者");
			return  ret;
		}
		if(StringUtils.isEmpty(user.getUsername())){
			ret.put("type","error");
			ret.put("msg","用户名不能为空");
			return  ret;
		}
		if(StringUtils.isEmpty(user.getPassword())){
			ret.put("type","error");
			ret.put("msg","密码不能为空");
			return  ret;
		}
		if((userService.findByUserName(user.getUsername()))!=null){
			ret.put("type","error");
			ret.put("msg","该用户已存在");
			return ret;
		}
		if(userService.add(user)<=0){
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
	public Map<String,String> edit(User user){
		Map<String,String> ret = new HashMap<>();
		if(user==null){
			ret.put("type","error");
			ret.put("msg","数据绑定出错,请联系开发者");
			return  ret;
		}
		if(StringUtils.isEmpty(user.getUsername())){
			ret.put("type","error");
			ret.put("msg","用户名不能为空");
			return  ret;
		}
		if(StringUtils.isEmpty(user.getPassword())){
			ret.put("type","error");
			ret.put("msg","密码不能为空");
			return  ret;
		}
		//1、根据用户输入的用户名查询数据库
		User existsUser = userService.findByUserName(user.getUsername());
		if(existsUser!=null){
			//2、再将查到的那条记录的ID与修改框中回显的原用户的ID进行比对，如果ID不同，表示用户输入的用户名已经被使用了
			if(existsUser.getId()!=user.getId()){
				ret.put("type","error");
				ret.put("msg","该用户已存在");
				return ret;
			}
		}
		if(userService.edit(user)<=0){
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
			count = userService.delete(id);
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
