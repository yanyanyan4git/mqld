package com.mqld.controller;

import javax.servlet.http.HttpServletRequest;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class MainController {
	

	@RequestMapping("/main")				
	public String home(HttpServletRequest request){
		
		request.setAttribute("login", true);
		return "main";				
	}
}
