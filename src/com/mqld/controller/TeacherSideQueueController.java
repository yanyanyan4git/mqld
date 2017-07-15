package com.mqld.controller;

import java.util.Map;
import java.util.TreeMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.mqld.annotation.FireAuthority;
import com.mqld.enums.AuthorityType;
import com.mqld.enums.ResultType;
import com.mqld.model.Page;
import com.mqld.model.QueueItem;
import com.mqld.model.User;
import com.mqld.service.QueueService;
import com.mqld.service.UserService;
import com.mqld.util.JsonUtil;
@Controller
public class TeacherSideQueueController {
	@Autowired
	QueueService queueService;
	
	@Autowired
	UserService userService;
	
	@FireAuthority(authorityTypes=AuthorityType.TEACHER)
	@RequestMapping("/teacherSideQueue")				
	public String teacherSideQueue(HttpServletRequest request){
		return "teacher_side_queue";				
	}
	
	@FireAuthority(authorityTypes=AuthorityType.TEACHER, resultType=ResultType.json)
	@RequestMapping("/resolveQueue")				
	public void resolveQueue(HttpServletRequest request,@RequestParam("comment")String comment,@RequestParam("path")String path,@RequestParam("queueID")String queueID,HttpServletResponse response,HttpSession session){
		QueueItem queueItem=new QueueItem();
		queueItem.setID(Integer.parseInt(queueID));
		queueItem.setTeacherPath(path);
		queueItem.setTeacherComment(comment);
		boolean flag=queueService.resolveQueue(queueItem);
		if (flag) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "无效的参数");
		}
	}
	
	@FireAuthority(authorityTypes=AuthorityType.TEACHER, resultType=ResultType.json)
	@RequestMapping("/getQueuedStudent")				
	public void getQueuedStudent(HttpServletRequest request,HttpServletResponse response,@RequestParam("currentPage")String currentPage,@RequestParam("pageSize")String pageSize,HttpSession session){
		int currentPageNum =Integer.parseInt(currentPage);
		int pageSizeNum=Integer.parseInt(pageSize);
		User user =(User)session.getAttribute("user");
		Page<QueueItem> page=new Page<QueueItem>(currentPageNum, pageSizeNum);
		page=queueService.getStudentStatus(user.getID(), page);
		if (null==page) {
			JsonUtil.flushError(response, "服务器出错");
		}else {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("page", page);
			JsonUtil.flushData(response, data);
		}
		
	}
	
	@FireAuthority(authorityTypes=AuthorityType.TEACHER, resultType=ResultType.json)
	@RequestMapping("/setWorkTime")				
	public void setWorkTime(HttpServletRequest request,HttpServletResponse response,@RequestParam("startWorkTime")String startWorkTime,@RequestParam("endWorkTime")String endWorkTime,HttpSession session){
		User user =(User) session.getAttribute("user");
		user.setEndWorkTime(endWorkTime);
		user.setStartWorkTime(startWorkTime);
		boolean flag=userService.updateTeacher(user);
		if (flag) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "无效的参数");
		}
		
	}
	
	@FireAuthority(authorityTypes=AuthorityType.TEACHER, resultType=ResultType.json)
	@RequestMapping("/setOnWork")				
	public void setOnWork(HttpServletRequest request,HttpServletResponse response,@RequestParam("onWork")boolean onWork,HttpSession session){
		User user =(User) session.getAttribute("user");
		user.setOnWork(onWork);
		boolean flag=userService.updateTeacher(user);
		if (flag) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "无效的参数");
		}
		
	}
}
