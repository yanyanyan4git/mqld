package com.mqld.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mqld.controller.RegistController;
import com.mqld.dao.UserDao;
import com.mqld.model.Page;
import com.mqld.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	JdbcTemplate JdbcTemplate;
	private static final String ADD_USER ="INSERT INTO user(ID,name,gender,password,authority) VALUES(?,?,?,?,?)";
	private static final String ADD_TEACHER ="INSERT INTO teacher(userID,contactInfo,style,startWorkTime,endWorkTime,maxStudentNum) VALUES(?,?,?,?,?,?)";
	private static final String MANAGE_USER ="UPDATE user SET name=?,authority=? WHERE ID=?";
	private static final String UPDATE_TEACHER="UPDATE teacher SET contactInfo=?,style=?,startWorkTime=?,endWorkTime=?,maxStudentNum=? ,onWork=? WHERE userID=?";
	private static final String DELETE_USER ="DELETE FROM user WHERE ID=?";
	private static final String GET_USER ="SELECT u.ID,u.name,u.gender,u.authority,t.style,t.contactInfo,t.startWorkTime,t.endWorkTime,IFNULL(t.onWork,false) AS onWork FROM user u LEFT JOIN teacher t ON u.ID=t.userID WHERE u.ID=?";
	private static final String LOGIN_USER ="SELECT u.ID,u.name,u.gender,u.authority,t.style,t.contactInfo,t.startWorkTime,t.endWorkTime,IFNULL(t.onWork,false) AS onWork FROM user u LEFT JOIN teacher t ON u.ID=t.userID WHERE u.ID=? AND u.password=?";
	private static final String GET_USERS="SELECT ID,name,gender,authority FROM user ORDER BY ID LIMIT ?,?";
	private static final String GET_USERS_BY_AUTHORITY="SELECT u.name,u.gender,u.authority,t.style,t.contactInfo,t.startWorkTime,t.endWorkTime FROM user u LEFT JOIN teacher t ON u.ID=t.userID WHERE u.authority=? ORDER BY ID LIMIT ?,?";
	private static final String GET_USER_COUNT_BY_AUTHORITY="SELECT IFNULL(COUNT(*),0) FROM user WHERE authority=?";
	private static final String GET_USER_COUNT="SELECT IFNULL(COUNT(*),0) FROM user ";
	private static final Logger logger=Logger.getLogger(UserDaoImpl.class);
	@Override
	public boolean addUser(User user) {
		logger.debug("Begin to ADD_USER");
		int count=JdbcTemplate.update(ADD_USER, user.getID(),user.getName(),user.getGender(),user.getPassword(),user.getAuthority());
		logger.debug("complete to ADD_USER..."+count);
		return count>0;
	}

	@Override
	public boolean addTeacher(User user) {
		logger.debug("Begin to ADD_TEACHER");
		int	count=JdbcTemplate.update(ADD_TEACHER,user.getID(),user.getContactInfo(),user.getStyle(),user.getStartWorkTime(),user.getEndWorkTime(),user.getMaxStudentNum());
		logger.debug("complete to ADD_TEACHER..."+count);
		return count>0;
	}

	@Override
	public boolean manageUser(User user) {
		logger.debug("Begin to UPDATE_USER");
		int	count=JdbcTemplate.update(MANAGE_USER,user.getName(),user.getAuthority(),user.getID());
		logger.debug("complete to UPDATE_USER..."+count);
		return count>0;
	}

	@Override
	public boolean updateTeacher(User user) {
		logger.debug("Begin to UPDATE_TEACHER");
		int	count=JdbcTemplate.update(UPDATE_TEACHER,user.getContactInfo(),user.getStyle(),user.getStartWorkTime(),user.getEndWorkTime(),user.getMaxStudentNum(),user.isOnWork(),user.getID());
		logger.debug("complete to UPDATE_TEACHER..."+count);
		return count>0;
	}

	@Override
	public boolean deleteUser(String ID) {
		logger.debug("Begin to DELETE_USER");
		int	count=JdbcTemplate.update(DELETE_USER,ID);
		logger.debug("complete to DELETE_USER..."+count);
		return count>0;
	}

	@Override
	public User getUser(String id) {
		RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
		logger.debug("Begin to GET_USER");
		User user;
		try {
			user = JdbcTemplate.queryForObject(GET_USER, rowMapper, id);
		} catch (EmptyResultDataAccessException e) {
			return null;
		}
		logger.debug("complete to GET_USER...");
		return user;
	}

	@Override
	public User login(String id,String password) {
		RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
		logger.debug("Begin to LOGIN_USER");
		User user;
		
			try {
				user = JdbcTemplate.queryForObject(LOGIN_USER, rowMapper, id,password);
			} catch (DataAccessException e) {
				user=null;
			}
		
		logger.debug("complete to LOGIN_USER...");
		return user;
	}

	@Override
	public Page<User> getUsers(Page<User> page) {
		RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
		logger.debug("Begin to GET_USERS");
		List<User> users= JdbcTemplate.query(GET_USERS, rowMapper,page.getStartNum(),page.getPageSize());
		logger.debug("complete to GET_USERS...");
		page.setRecords(users);
		return page;
	}

	@Override
	public Page<User> getUsers(String authority, Page<User> page) {
		RowMapper<User> rowMapper=new BeanPropertyRowMapper<User>(User.class);
		logger.debug("Begin to GET_USERS_BY_AUTHORITY");
		List<User> users= JdbcTemplate.query(GET_USERS_BY_AUTHORITY, rowMapper,authority,page.getStartNum(),page.getPageSize());
		logger.debug("Begin to GET_USERS_BY_AUTHORITY");
		page.setRecords(users);
		return page;
	}

	@Override
	public int getUserCount() {
		logger.debug("Begin to GET_USER_COUNT");
		int count = JdbcTemplate.queryForObject(GET_USER_COUNT, Integer.class);
		logger.debug("complete to GET_USER_COUNT...");
		return count;
	}

	@Override
	public int getUserCount(String authority) {
		logger.debug("Begin to GET_USER_COUNT_BY_AUTHORITY");
		int count = JdbcTemplate.queryForObject(GET_USER_COUNT_BY_AUTHORITY, Integer.class,authority);
		logger.debug("complete to GET_USER_COUNT_BY_AUTHORITY...");
		return count;
	}
	
	
	
}
