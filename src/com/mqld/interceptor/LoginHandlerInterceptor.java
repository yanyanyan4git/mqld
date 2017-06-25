package com.mqld.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.mqld.model.User;

public class LoginHandlerInterceptor extends HandlerInterceptorAdapter {


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object arg2) throws Exception {
		// TODO Auto-generated method stub
		HttpSession session=request.getSession();
		User user=(User)session.getAttribute("user");
		String requestURI=request.getRequestURI();
		
		if (requestURI.contains("main.action")||requestURI.contains("welcome.action")||requestURI.contains("login.action")) {
			return true;
		}
		if (null==user) {
			 StringBuilder sb = new StringBuilder();
		        sb.append(request.getContextPath());
		        sb.append("/main.action");
		        response.sendRedirect(sb.toString());
		      return false;
		}
		return true;
	}

}
