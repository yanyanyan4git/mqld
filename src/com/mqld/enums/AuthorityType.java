package com.mqld.enums;

import com.mqld.constant.Constant;

public enum AuthorityType {
	ADMIN(Constant.ADMIN),
	TEACHER(Constant.TEACHER),
	STUDENT(Constant.STUDENT),
	;
	private String name;

	private AuthorityType(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}