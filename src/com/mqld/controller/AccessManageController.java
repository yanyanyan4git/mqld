package com.mqld.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mqld.annotation.FireAuthority;
import com.mqld.enums.AuthorityType;
import com.mqld.enums.ResultType;
import com.mqld.model.Page;
import com.mqld.model.User;
import com.mqld.service.UserService;
import com.mqld.util.JsonUtil;
@Controller
public class AccessManageController {
	@Autowired
	UserService userService;
	
	private static final Logger logger=Logger.getLogger(AccessManageController.class);
	
	@FireAuthority(authorityTypes=AuthorityType.ADMIN)
	@RequestMapping("/accessManage")				
	public String login( HttpServletRequest request){
		return "access_manage";				
	}
	@FireAuthority(authorityTypes=AuthorityType.ADMIN, resultType=ResultType.json)
	@RequestMapping("/getUsers")				
	public void getUsers( HttpServletRequest request ,@RequestParam("currentPage")String currentPage,@RequestParam("pageSize")String pageSize,HttpServletResponse response){
		logger.debug(">>doRegist");
		System.out.println(currentPage);
		int currentPageNum =Integer.parseInt(currentPage);
		int pageSizeNum=Integer.parseInt(pageSize);
		Page<User> page=new Page<User>(currentPageNum, pageSizeNum);
		page=userService.getUsers(page);
		if (null!=page) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("page", page);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "false");
		}
		
	}
	@FireAuthority(authorityTypes=AuthorityType.ADMIN, resultType=ResultType.json)
	@RequestMapping("/doManagement")				
	public void doMangement( HttpServletRequest request ,@RequestParam("ID")String ID,@RequestParam("authority")String authority,@RequestParam("name")String name,@RequestParam("psw")String psw,HttpServletResponse response){
		logger.debug(">>doMangement");
		
		boolean flag=userService.manageUser(ID, name, authority,psw);
		if (flag) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("result", flag);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "false");
		}
		
	}
	
	@FireAuthority(authorityTypes=AuthorityType.ADMIN, resultType=ResultType.json)
	@RequestMapping("/delUser")				
	public void delUser( HttpServletRequest request ,@RequestParam("IDs")String ID,HttpServletResponse response){
		logger.debug(">>doMangement");
		String [] IDArr=ID.split(",");
		final List<String> IDs=new ArrayList<String>();
		for(String id:IDArr){
			IDs.add(id);
		}
		
		int sucessCount=userService.delUsers(IDs);
		if (sucessCount==IDs.size()) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("result", true);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "É¾³ý³É¹¦£º"+sucessCount+"£¬É¾³ýÊ§°Ü£º"+(IDs.size()-sucessCount));
		}
		
	}
}
