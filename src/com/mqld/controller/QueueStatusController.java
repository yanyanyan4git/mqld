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
public class QueueStatusController {
	
	@Autowired
	QueueService queueService;
	
	@FireAuthority(authorityTypes=AuthorityType.STUDENT)
	@RequestMapping("/queueStatus")				
	public String queueStatus(HttpServletRequest request){
		return "queue_status";				
	}
	
	
	@FireAuthority(authorityTypes=AuthorityType.STUDENT, resultType=ResultType.json)
	@RequestMapping("/getQueueHistory")				
	public void getQueueHistory(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestParam("currentPage")String currentPage,@RequestParam("pageSize")String pageSize){
		int currentPageNum =Integer.parseInt(currentPage);
		int pageSizeNum=Integer.parseInt(pageSize);
		User user=(User) session.getAttribute("user");
		Page<QueueItem> page=new Page<QueueItem>(currentPageNum, pageSizeNum);
		page.setTotalRecord(1);
		page=queueService.getQueueHistory(user.getID(), page);
		
		if (null==page) {
			JsonUtil.flushError(response, "服务器出错");
		}else {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("page", page);
			JsonUtil.flushData(response, data);
		}
		
	}
	
	@FireAuthority(authorityTypes=AuthorityType.STUDENT, resultType=ResultType.json)
	@RequestMapping("/doEvaluate")				
	public void doEvaluate(HttpServletRequest request,HttpServletResponse response,HttpSession session,@RequestParam("attitude")String attitude,@RequestParam("profLevel")String profLevel,@RequestParam("comment")String comment,@RequestParam("queueID")String queueID){
		
		User user=(User) session.getAttribute("user");
		QueueItem qItem=new QueueItem();
		qItem.setID(Integer.parseInt(queueID));
		qItem.setPerfComment(comment);
		qItem.setAttitude(attitude);
		qItem.setProfLevel(profLevel);
		boolean flag=queueService.evaluate(qItem);
		
		if (!flag) {
			JsonUtil.flushError(response, "服务器出错");
		}else {
			Map<String, Object> data=new TreeMap<String, Object>();
			data.put("success", true);
			JsonUtil.flushData(response, data);
		}
		
	}
}
