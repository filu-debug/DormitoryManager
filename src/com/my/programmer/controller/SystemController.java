package com.my.programmer.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.programmer.entity.Student;
import com.my.programmer.service.StudentService;
import org.apache.commons.collections.map.HashedMap;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.my.programmer.entity.User;
import com.my.programmer.service.UserService;
import com.my.programmer.util.CpachaUtil;

@RequestMapping("/system")
@Controller
public class SystemController {
	
	//获取UserService对象
	@Autowired
	private UserService userService;

	@Autowired
	StudentService studentService;

	@RequestMapping("/index")
	public ModelAndView index(ModelAndView model) {
		//寻找/WEB-INF/views/system路径下的index.jsp文件(自动拼上.jsp后缀)
		model.setViewName("system/index");
		return model;
	}
	
	/**
	 * 登录页面
	 * @param model
	 * @return
	 */
	@RequestMapping("/login")
	public ModelAndView login(ModelAndView model) {
		model.setViewName("system/login");
		return model;
	}
	
	/**
	 * 登录页面表单提交
	 * @return
	 */
	@RequestMapping(value = "/login",method = RequestMethod.POST)
	@ResponseBody //将ret对象以JSon格式返回给页面
	public Map<String,String> login(
			//这四个参数接收登录页面提交的的数据
			@RequestParam(value = "username",required = true)String username,
			@RequestParam(value = "password",required = true)String password,
			@RequestParam(value = "vcode",required = true)String vcode,
			@RequestParam(value = "type",required = true)String type,
			HttpServletRequest request
			) {
		Map<String, String> ret = new HashedMap();
		if(StringUtils.isEmpty(username)){
			ret.put("type", "error");
			ret.put("msg", "用户名不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(password)){
			ret.put("type", "error");
			ret.put("msg", "密码不能为空!");
			return ret;
		}
		if(StringUtils.isEmpty(vcode)){
			ret.put("type", "error");
			ret.put("msg", "验证码不能为空!");
			return ret;
		}
		//取出生成验证码时放进session中的4位验证码，如果失效，就表示用户长时间没有任何操作
		String loginCpacha = (String)request.getSession().getAttribute("loginCpacha");
		if(StringUtils.isEmpty(loginCpacha)) {
			ret.put("type", "error");
			ret.put("msg", "您长时间未操作，会话已断开，请刷新页面后重试!");
			return ret;
		}
		//将生成验证码后存入session中的验证码拿来与用户输入的验证码进行比对（都转成全部大写后比对）
		if( !vcode.toUpperCase().equals(loginCpacha.toUpperCase()) ) {
			ret.put("type", "error");
			ret.put("msg", "验证码错误");
			return ret;
		}
		//走到这里，表示用户输入的是正确的验证码，那就将之前生成验证码时存入session中的验证码清空，没有必要保留了。
		request.getSession().setAttribute("loginCpacha", null);
		
		
		//去数据库中查找用户
		if(type .equals("1")) {
			//type==1就是管理员
			
			//将用户输入的用户名，传给此方法的形参
			User user = userService.findByUserName(username);
			//如果user为空，则表示根据用户提供的用户名没有查到数据
			if(user==null) {
				ret.put("type", "error");
				ret.put("msg", "不存在该用户");
				return ret;
			}
			//如果用户提供的密码，与数据库中返回的密码不一致，就表示密码不正确
			if(!password.equals(user.getPassword())){
				ret.put("type", "error");
				ret.put("msg", "密码错误");
				return ret;
			}
			//走到这里，表示密码正确,可以将当前用户存入session会话对象中
			request.getSession().setAttribute("user", user);
		}
		if(type .equals("2")) {
			//学生
			Student student = new Student();
			student.setStuNo(username);
			Student studentexists = studentService.findByStuNo(student.getStuNo());
			if(studentexists==null) {
				ret.put("type", "error");
				ret.put("msg", "不存在该学生");
				return ret;
			}
			//如果用户提供的密码，与数据库中返回的密码不一致，就表示密码不正确
			if(!password.equals(studentexists.getPassword())){
				ret.put("type", "error");
				ret.put("msg", "密码错误");
				return ret;
			}
			//走到这里，表示密码正确,可以将当前用户存入session会话对象中
			request.getSession().setAttribute("user", studentexists);
		}
		if(type .equals("3")) {
			//工人
		}
		if(type .equals("4")) {
			//宿管
		}
		
		request.getSession().setAttribute("userType",type);
		ret.put("type", "success");
		ret.put("msg", "登录成功!");
		return ret;
	}
	
	/**
	 * 获取验证码
	 * @param response
	 * @param vl
	 * @param w
	 * @param h
	 * @param request
	 */
	@RequestMapping("/get_cpacha")
	public void get_cpacha(HttpServletResponse response,
			@RequestParam(value = "vl",defaultValue = "4")int vl,
			@RequestParam(value = "w",defaultValue = "98")int w,
			@RequestParam(value = "h",defaultValue = "33")int h,
			HttpServletRequest request) {
		//创建验证码工具类对象，设置验证码长度，图片的宽度、高度
		CpachaUtil cpachaUtil = new CpachaUtil(vl, w, h);
		//生成验证码
		String vcode = cpachaUtil.generatorVCode();
		//将验证码存入session会话对象中
		request.getSession().setAttribute("loginCpacha", vcode);
		//获得旋转字体的验证码图片，放入先前生成的验证码，加上干扰线
		BufferedImage VCodeImage = cpachaUtil.generatorRotateVCodeImage(vcode, true);
		//调用ImageIO类的write方法，将最终的图片放在response.getOutputStream()返回的ServletOutputStream对象中
		//然后打印到前端页面
		try {
			ImageIO.write(VCodeImage, "gif", response.getOutputStream());
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
