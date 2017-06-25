package com.mqld.util;

public class StringUtils {
	public static boolean isEmpty(String str) {
		return str.equals("")||null==str;
	}
	
	public static boolean validateEmpty(String... args) {
		boolean flag = false;
		for(String arg : args){
			flag=flag||StringUtils.isEmpty(arg);
		}
		return flag;
		
	}
}
