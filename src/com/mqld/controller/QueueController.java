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
import com.mqld.util.JsonUtil;
@Controller
public class QueueController {
	@Autowired
	QueueService queueService;
	
	@FireAuthority(authorityTypes=AuthorityType.STUDENT)
	@RequestMapping("/queue")				
	public String queue(HttpServletRequest request){
		return "queue";				
	}
	
	@FireAuthority(authorityTypes=AuthorityType.STUDENT, resultType=ResultType.json)
	@RequestMapping("/getQueues")				
	public void getQueues(HttpServletRequest request,HttpServletResponse response,@RequestParam("currentPage")String currentPage,@RequestParam("pageSize")String pageSize){
		int currentPageNum =Integer.parseInt(currentPage);
		int pageSizeNum=Integer.parseInt(pageSize);
		Page<QueueItem> page=new Page<QueueItem>(currentPageNum, pageSizeNum);
		page=queueService.getTeacherStatus(page);
		if (null==page) {
			JsonUtil.flushError(response, "服务器出错");
		}else {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("page", page);
			JsonUtil.flushData(response, data);
		}
		
	}
	
	@FireAuthority(authorityTypes=AuthorityType.STUDENT, resultType=ResultType.json)
	@RequestMapping("/doQueue")				
	public void doQueue(HttpServletRequest request,HttpServletResponse response){
		int picNum=Integer.parseInt(request.getParameter("picNum"));
		String path=request.getParameter("path");
		String comment=request.getParameter("comment");
		QueueItem queueItem=new QueueItem();
		
		queueItem.setPictureNum(picNum);
		queueItem.setStudentPath(path);
		queueItem.setStudentComment(comment);
		boolean flag=queueService.queue(queueItem);
		if (flag) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "无效的参数");
		}
	}
	
	@FireAuthority(authorityTypes=AuthorityType.STUDENT, resultType=ResultType.json)
	@RequestMapping("/cancelQueue")				
	public void cancelQueue(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		User user =(User) session.getAttribute("user");
		boolean flag=queueService.cancelQueue(user.getID());
		if (flag) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "取消失败");
		}
		
	}
	
	@FireAuthority(authorityTypes=AuthorityType.STUDENT, resultType=ResultType.json)
	@RequestMapping("/isQueued")				
	public void isQueued(HttpServletRequest request,HttpServletResponse response,HttpSession session){
		User user =(User) session.getAttribute("user");
		boolean flag= queueService.isQueued(user.getID());
		if (flag) {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			JsonUtil.flushData(response, data);
		}else {
			JsonUtil.flushError(response, "取消失败");
		}
		
	}
}
