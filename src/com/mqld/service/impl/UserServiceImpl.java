package com.mqld.service.impl;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mqld.constant.Constant;
import com.mqld.dao.UserDao;
import com.mqld.dao.impl.QueueDaoImpl;
import com.mqld.model.Page;
import com.mqld.model.User;
import com.mqld.service.UserService;
import com.mqld.util.StringUtils;

@Service
public class UserServiceImpl implements UserService {
	@Autowired
	UserDao userDao;
	private static final Logger logger=Logger.getLogger(UserServiceImpl.class);
	@Override
	public boolean registUser(User user) {
		if (StringUtils.validateEmpty(user.getPassword(),user.getID(),user.getAuthority())) {
			logger.error("null parameter");
			return false;
		}
		boolean flag=userDao.addUser(user);
		if (flag==true&&user.getAuthority().equals(Constant.TEACHER)) {
			flag=userDao.addTeacher(user);
		}
		return flag;
	}
	
	@Override
	public User login(String id, String password) {
		if (StringUtils.validateEmpty(password,id)) {
			logger.error("null parameter");
			return null;
		}
		return userDao.login(id, password);
	}

	@Override
	public Page<User> getUsers(Page<User> page) {
		int totalRecord= userDao.getUserCount();
		page.setTotalRecord(totalRecord);
		return userDao.getUsers(page);
	}

	@Override
	public Page<User> getTeacher(Page<User> page) {
		return userDao.getUsers(Constant.TEACHER, page);
	}

	@Override
	public User getUser(String id) {
		// TODO Auto-generated method stub
		return userDao.getUser(id);
	}

	@Override
	public boolean manageUser(String ID,String name,String authority) {
		if (StringUtils.validateEmpty(ID,name,authority)) {
			logger.error("null parameter");
			return false;
		}
		User user = new User();
		user.setID(ID);
		user.setName(name);
		user.setAuthority(authority);
		boolean flag=userDao.manageUser(user);
		return flag;
	}

	@Override
	public boolean updateTeacher(User user) {
		if (StringUtils.validateEmpty(user.getID())) {
			logger.error("null parameter");
			return false;
		}
		String endWorkTime=user.getEndWorkTime();
		String startWorkTime=user.getStartWorkTime();
		int maxStudentNum;
		try {
			maxStudentNum = calculateTime(startWorkTime,endWorkTime);
		} catch (ParseException e) {
			return false;
		}
		user.setMaxStudentNum(maxStudentNum);
		
		return userDao.updateTeacher(user);
	}

	private int calculateTime(String startWorkTime, String endWorkTime) throws ParseException {
		DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    Date d1 = df.parse("2004-01-02 "+endWorkTime);
	    Date d2 = df.parse("2004-01-02 "+startWorkTime);
	    long diff = d1.getTime() - d2.getTime();
	    int minutes = (int)diff / (1000 * 60  );
		return minutes/Constant.MIN_PER_STU;
	}

	@Override
	public int delUsers(final List<String> ID) {
		int count=userDao.batchDeleteUsers(ID);
		if (count==0) {
			return 0;
		}
		userDao.batchDeleteUsersFromQueue(ID);
		return count;
	}

	@Override
	public boolean setPassWord(String ID, String psw) {
		if (StringUtils.validateEmpty(ID,psw)) {
			logger.error("null parameter");
			return false;
		}
		return userDao.setPassWord(ID,psw);
	}
	
}
