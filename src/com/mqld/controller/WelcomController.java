package com.mqld.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WelcomController {

	@RequestMapping("/welcome")				
	public String gohome(HttpServletRequest request){
		return "welcome";				
	}
}
