package com.mqld.service;

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
}
