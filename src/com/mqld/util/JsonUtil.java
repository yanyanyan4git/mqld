package com.mqld.util;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;

import com.mqld.model.ResultSet;

public class JsonUtil {
	private  static JSONObject getJason(String error,Map<String, Object> data){
		ResultSet rs=new ResultSet();
		if (error!=null) {
			rs.setError(error);
		}else{
			rs.setData(data);
		}
		JSONObject json=new JSONObject(rs);
		return json;
	}
	
	private static void flushJson(HttpServletResponse response,JSONObject json){
		response.setContentType("application/json;charset=utf-8");
	    PrintWriter out;
		try {
			out = response.getWriter();
			out.write(json.toString());  
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}  
	}
	
	public static void flushData(HttpServletResponse response,Map<String, Object> data){
		flushJson(response,getJason(null, data));
	}
	
	public static void flushError(HttpServletResponse response,String error){
		flushJson(response,getJason(error, null));
	}
}
