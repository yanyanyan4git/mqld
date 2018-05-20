package com.mqld.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class User {
	@JsonProperty("ID")
	private String ID;
	private String name;
	private String gender;
	private String password;
	private String authority;
	private String style;
	private String contactInfo;
	private String startWorkTime;
	private String endWorkTime;
	private int maxStudentNum;
	private boolean onWork;
	public String getID() {
		return ID;
	}
	public void setID(String iD) {
		ID = iD;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getGender() {
		return gender;
	}
	public void setGender(String gender) {
		this.gender = gender;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getAuthority() {
		return authority;
	}
	public void setAuthority(String authority) {
		this.authority = authority;
	}
	public String getStyle() {
		return style;
	}
	public void setStyle(String style) {
		this.style = style;
	}
	public String getContactInfo() {
		return contactInfo;
	}
	public void setContactInfo(String contactInfo) {
		this.contactInfo = contactInfo;
	}
	public String getStartWorkTime() {
		return startWorkTime;
	}
	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
	}
	public String getEndWorkTime() {
		return endWorkTime;
	}
	public void setEndWorkTime(String endWorkTime) {
		this.endWorkTime = endWorkTime;
	}
	public int getMaxStudentNum() {
		return maxStudentNum;
	}
	public void setMaxStudentNum(int maxStudentNum) {
		this.maxStudentNum = maxStudentNum;
	}
	public boolean isOnWork() {
		return onWork;
	}
	public void setOnWork(boolean onWork) {
		this.onWork = onWork;
	}
	
}
