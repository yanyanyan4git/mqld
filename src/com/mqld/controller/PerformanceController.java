package com.mqld.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.mqld.annotation.FireAuthority;
import com.mqld.enums.AuthorityType;
@Controller
public class PerformanceController {
	@FireAuthority(authorityTypes=AuthorityType.TEACHER)
	@RequestMapping("/performance")				
	public String login(HttpServletRequest request){
		return "performance";				
	}
}
