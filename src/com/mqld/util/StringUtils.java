package com.mqld.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class StringUtils {
	public static boolean isEmpty(String str) {
		return null==str||str.equals("");
	}
	
	public static boolean validateEmpty(String... args) {
		boolean flag = false;
		for(String arg : args){
			flag=flag||StringUtils.isEmpty(arg);
		}
		return flag;
		
	}
	
	public static String getCurrDate() {
		Date date =new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		
		return sdf.format(date);
	}
}
