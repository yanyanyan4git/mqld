package com.mqld.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mqld.annotation.FireAuthority;
import com.mqld.enums.AuthorityType;
import com.mqld.enums.ResultType;
import com.mqld.model.User;
import com.mqld.service.UserService;
import com.mqld.util.JsonUtil;

@Controller
public class MainController {
	@Autowired
	UserService userService;

	@RequestMapping("/main")				
	public String home(HttpServletRequest request){
		
		request.setAttribute("login", true);
		return "main";				
	}
	
	
	@RequestMapping("/getUser")				
	public void getUser(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		
		User user =(User) session.getAttribute("user");
		user=userService.getUser(user.getID());
		
		if (null!=user) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("user", user);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "无效的参数");
		}
		
	}
}
