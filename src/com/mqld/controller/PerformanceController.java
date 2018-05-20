package com.mqld.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.mqld.annotation.FireAuthority;
import com.mqld.enums.AuthorityType;
import com.mqld.enums.ResultType;
import com.mqld.model.Page;
import com.mqld.model.QueueItem;
import com.mqld.model.TeachersPerfQueryConditionDTO;
import com.mqld.model.User;
import com.mqld.service.QueueService;
import com.mqld.util.JsonUtil;
@Controller
public class PerformanceController {
	
	@Autowired
	QueueService queueService;
	
	@FireAuthority(authorityTypes=AuthorityType.TEACHER)
	@RequestMapping("/performance")				
	public String performance(HttpServletRequest request){
		return "performance";				
	}
	
	@FireAuthority(authorityTypes=AuthorityType.ADMIN)
	@RequestMapping("/adminSidePerformance")				
	public String adminSidePerformance(HttpServletRequest request){
		return "admin_side_performance";				
	}
	
	@FireAuthority(authorityTypes=AuthorityType.TEACHER, resultType=ResultType.json)
	@RequestMapping("/getTeacherPerf")				
	public void getTeacherPerf(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestParam("currentPage")String currentPage,@RequestParam("pageSize")String pageSize){
		int currentPageNum =Integer.parseInt(currentPage);
		int pageSizeNum=Integer.parseInt(pageSize);
		Page<QueueItem> page=new Page<QueueItem>(currentPageNum, pageSizeNum);
		User user =(User) session.getAttribute("user");
		page=queueService.getTeacherPerf(user.getID(),page);
		if (null==page) {
			JsonUtil.flushError(response, "服务器出错");
		}else {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("page", page);
			JsonUtil.flushData(response, data);
		}	
	}
	
	@FireAuthority(authorityTypes=AuthorityType.ADMIN, resultType=ResultType.json)
	@RequestMapping(value="/getTeachersPerf" ,method=RequestMethod.POST,consumes = "application/json")				
	public void getTeachersPerf(HttpServletRequest request,HttpServletResponse response,@RequestParam("currentPage")String currentPage,@RequestParam("pageSize")String pageSize,@RequestBody TeachersPerfQueryConditionDTO condition){
		int currentPageNum =Integer.parseInt(currentPage);
		int pageSizeNum=Integer.parseInt(pageSize);
		Page<QueueItem> page=new Page<QueueItem>(currentPageNum, pageSizeNum);
		page=queueService.getTeachersPerf(condition,page);
		if (null==page) {
			JsonUtil.flushError(response, "服务器出错");
		}else {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("page", page);
			JsonUtil.flushData(response, data);
		}	
	}
}
