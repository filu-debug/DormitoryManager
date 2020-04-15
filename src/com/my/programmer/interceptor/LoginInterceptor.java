package com.my.programmer.interceptor;

import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.JsonObject;
import net.sf.json.JSONObject;
import org.springframework.web.servlet.HandlerAdapter;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.my.programmer.entity.User;

/**
 * 检查过滤后的所有请求的登录状态
 * @author X1YOGA
 *
 */
public class LoginInterceptor implements HandlerInterceptor{

	//请求完成后
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}

	//请求进行中
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		// TODO Auto-generated method stub
		
	}
	//请求发生前
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		
		//拿到登录成功时存入session中的user
		  Object user = request.getSession().getAttribute("user");
		  response.setContentType("text/html;charset=utf-8");
		  if(user==null) {
			  //request.getRequestDispatcher("/system/login").forward(request, response);
			  //如果用户未登录或登录失效,跳转到登录页面让用户登录
			  request.getSession().setAttribute("loginInvalid", "登录失效或未登录，请您先登录");
			  //如果相等，表示当前请求是一个ajax请求
			  if("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))){
				  Map<String,String> ret = new HashMap<>();
				  ret.put("type","error");
				  ret.put("msg","登录状态已失效，请重新登录");
				  response.getWriter().write(JSONObject.fromObject(ret).toString());
				  return false;
			  }
			  response.sendRedirect(request.getContextPath()+"/system/login");
			  return false;
		  }
		  
		  return true;
	}


}
