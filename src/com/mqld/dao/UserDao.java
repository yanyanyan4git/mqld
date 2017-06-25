package com.mqld.dao;

import java.util.List;

import com.mqld.model.Page;
import com.mqld.model.User;

public interface UserDao {

	boolean addUser(User user);
	boolean addTeacher(User user);
	boolean manageUser(User user);
	boolean updateTeacher(User user);
	boolean deleteUser(String ID);
	User getUser(String id );
	User login(String id ,String password);
	Page<User> getUsers(Page<User> page);
	Page<User> getUsers(String authority,Page<User> page);
	int getUserCount();
	int getUserCount(String authority);
}
