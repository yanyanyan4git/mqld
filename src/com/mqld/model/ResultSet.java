package com.mqld.model;

import java.util.Map;

public class ResultSet {
	private String error;
	private Map<String, Object> data;
	public String getError() {
		return error;
	}
	public void setError(String error) {
		this.error = error;
	}
	public Map<String, Object> getData() {
		return data;
	}
	public void setData(Map<String, Object> data) {
		this.data = data;
	}
	
}
