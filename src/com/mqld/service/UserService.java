package com.mqld.service;

import java.util.List;

import com.mqld.model.Page;
import com.mqld.model.User;

public interface UserService {
	User login(String name,String password);
	boolean registUser(User user);
	Page<User> getUsers( Page<User> page);
	Page<User> getTeacher( Page<User> page);
	User getUser(String id);
	boolean updateTeacher(User user);
	boolean manageUser(String ID,String name,String authority);
	int delUsers(final List<String> iD);
	boolean setPassWord(String ID,String psw);
}
