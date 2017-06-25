package com.mqld.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mqld.annotation.FireAuthority;
import com.mqld.constant.Constant;
import com.mqld.enums.AuthorityType;
import com.mqld.enums.ResultType;
import com.mqld.model.User;
import com.mqld.service.UserService;
import com.mqld.util.JsonUtil;
@Controller
public class RegistController {
	@Autowired
	UserService userService;
	
	private static final Logger logger=Logger.getLogger(RegistController.class);
	
	@FireAuthority(authorityTypes=AuthorityType.ADMIN)
	@RequestMapping("/regist")				
	public String login(HttpServletRequest request){
		return "regist";				
	}
	
	@FireAuthority(authorityTypes=AuthorityType.ADMIN, resultType=ResultType.json)
	@RequestMapping("/doRegist")		
	public void doRegist(HttpServletRequest request ,HttpServletResponse response) {
		logger.debug(">>doRegist");
		User user =getUserFromRequest(request);
		User existUser=userService.getUser(user.getID());
		if (null!=existUser) {
			JsonUtil.flushError(response, "该用户已存在");
			return;
		}
		boolean flag=userService.registUser(user);
		if (flag) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("result", true);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "数据库无改动");
		}
	}
	private User getUserFromRequest(HttpServletRequest request) {
		User user=new User();
		String userType=request.getParameter("userType");
		String userID=request.getParameter("userID");
		String userName=request.getParameter("username");
		String password=request.getParameter("password");
		String style=null;
		user.setAuthority(userType);
		user.setID(userID);
		user.setName(userName);
		user.setPassword(password);
		System.out.println(userType);
		if (userType.equals(Constant.TEACHER)) {
			style=request.getParameter("style");
			user.setStyle(style);
		}
		return user;
	}
}
