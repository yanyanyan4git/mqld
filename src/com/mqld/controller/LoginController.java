package com.mqld.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.mqld.model.User;
import com.mqld.service.UserService;
import com.mqld.util.JsonUtil;
import com.mqld.util.StringUtils;
@Controller
public class LoginController {
	@Autowired
	UserService userService;
	
	@RequestMapping(value="/login", method = RequestMethod.POST,consumes = "application/json")				
	public void login(@RequestBody Map<String, String> jObject,HttpServletRequest request,HttpServletResponse response ,HttpSession session){
		String userName=null;
		String password=null;
			userName = jObject.get("username");
			password = jObject.get("password");
		User user=userService.login(userName, password);
		if (null==user) {
			JsonUtil.flushError(response, "failed");
		}else {
			session.setAttribute("user", user);
			session.setMaxInactiveInterval(12*60*60);
			Map< String, Object> data=new TreeMap<String, Object>();
			data.put("user", user);
			JsonUtil.flushData(response, data);
		}
	}
	
	@RequestMapping(value="/updatePsw", method = RequestMethod.POST,consumes = "application/json")				
	public void updatePsw(@RequestBody Map<String, String> jObject,HttpServletRequest request,HttpServletResponse response ,HttpSession session){
		
			User user=(User) session.getAttribute("user");
			String password = jObject.get("password");
			User newUser=userService.login(user.getID(), password);
			if (null==newUser) {
				JsonUtil.flushError(response, "failed");
				return;
			}
			String newPassword = jObject.get("newPassword");
		boolean flag=userService.setPassWord(user.getID(), newPassword);
		if (!flag) {
			JsonUtil.flushError(response, "failed");
		}else {
			Map< String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			session.setAttribute("user", null);
			JsonUtil.flushData(response, data);
		}
	}
	
	@RequestMapping("/logout")				
	public void logout(HttpServletRequest request,HttpServletResponse response ,HttpSession session){
		System.out.println(11);
		session.setAttribute("user", null);
		User user=(User) session.getAttribute("user");
		System.out.println(user);
		if (null!=user) {
			JsonUtil.flushError(response, "failed");
		}else {
			Map< String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			JsonUtil.flushData(response, data);
		}
	}
}
