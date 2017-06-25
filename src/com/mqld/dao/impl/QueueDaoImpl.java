package com.mqld.dao.impl;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mqld.dao.QueueDao;
import com.mqld.model.Page;
import com.mqld.model.QueueItem;

@Repository
public class QueueDaoImpl implements QueueDao {
	@Autowired
	JdbcTemplate JdbcTemplate;
	
	private static final String GET_TEACHER_STATUS="SELECT u.ID AS teacherID, u.name AS teacherName,t.startWorkTime,t.endWorkTime,t.style AS teacherStyle,t.maxStudentNum,t.contactInfo AS teacherContactInfo,cnt.count AS currentQueueNum FROM teacher t,user u LEFT JOIN (SELECT b.ID, COUNT(*) AS count FROM queue q LEFT JOIN user b ON b.ID=q.teacherID Where status='排队中' GROUP BY b.ID) cnt ON u.ID=cnt.ID WHERE u.authority='助教' LIMIT ?,?";
	private static final String GET_STUDENT_STATUS="SELECT q.ID,q.pictureNum,q.studentPath,u.name AS studentName FROM queue q, user u,teacher t WHERE t.userID=q.teacherID AND u.ID=q.studentID AND q.teacherID=? AND q.status='排队中' ORDER BY q.createTime LIMIT ?,?";
	private static final String ADD_QUEUE="INSERT INTO queue(createTime,status,teacherID,studentID,pictureNum,studentPath,studentComment) VALUES(NOW(),'排队中',?,?,?,?,?)";
	private static final String RESOLVE_QUEUE="UPDATE queue SET status='已解决',teacherPath=?,teacherComment=? WHERE ID=?";
	private static final String DELETE_QUEUE="DELETE FROM queue where studentID=? AND status='排队中'";
	private static final String IS_QUEUE_EXIST="SELECT ID FROM queue where studentID=? AND status='排队中'";
	private static final String GET_TEACHER_COUNT="SELECT COUNT(*) FROM teacher";
	private static final String GET_STUDENT_COUNT="SELECT COUNT(*) FROM queue WHERE status='排队中' AND teacherID=?";
	private static final Logger logger=Logger.getLogger(QueueDaoImpl.class);
	@Override
	public boolean Queue(QueueItem queue) {
		logger.debug("Begin to ADD_QUEUE");
		int	count=JdbcTemplate.update(ADD_QUEUE,queue.getTeacherID(),queue.getStudentID(),queue.getPictureNum(),queue.getStudentPath(),queue.getStudentComment());
		logger.debug("complete to ADD_QUEUE..."+count);
		return count>0;
	}

	@Override
	public boolean resolveQueue(QueueItem queue) {
		logger.debug("Begin to RESOLVE_QUEUE");
		int	count=JdbcTemplate.update(RESOLVE_QUEUE,queue.getTeacherPath(),queue.getTeacherComment(),queue.getID());
		logger.debug("complete to RESOLVE_QUEUE..."+count);
		return count>0;
	}

	@Override
	public boolean cancelQueue(String id) {
		logger.debug("Begin to DELETE_QUEUE");
		int	count=JdbcTemplate.update(DELETE_QUEUE,id);
		logger.debug("complete to DELETE_QUEUE..."+count);
		return count>0;
	}
	
	@Override
	public Page<QueueItem> getTeacherStatus(Page<QueueItem> page) {
		RowMapper<QueueItem> rowMapper=new BeanPropertyRowMapper<QueueItem>(QueueItem.class);
		logger.debug("Begin to GET_TEACHER_STATUS");
		List<QueueItem> queues= JdbcTemplate.query(GET_TEACHER_STATUS, rowMapper,page.getStartNum(),page.getPageSize());
		logger.debug("complete to GET_TEACHER_STATUS...");
		page.setRecords(queues);
		return page;
	}

	@Override
	public Page<QueueItem> getStudentStatus(String teacherID,Page<QueueItem> page) {
		RowMapper<QueueItem> rowMapper=new BeanPropertyRowMapper<QueueItem>(QueueItem.class);
		logger.debug("Begin to GET_STUDENT_STATUS");
		List<QueueItem> queues= JdbcTemplate.query(GET_STUDENT_STATUS, rowMapper,teacherID,page.getStartNum(),page.getPageSize());
		logger.debug("complete to GET_STUDENT_STATUS...");
		page.setRecords(queues);
		return page;
	}

	@Override
	public boolean isQueued(String studentID) {
		logger.debug("Begin to IS_QUEUE_EXIST");
		Integer queueId=JdbcTemplate.queryForObject(IS_QUEUE_EXIST, Integer.class);
		logger.debug("complete to IS_QUEUE_EXIST...");
		return queueId!=null;
	}

	@Override
	public int getTeacherCount() {
		logger.debug("Begin to GET_TEACHER_COUNT");
		int count = JdbcTemplate.queryForObject(GET_TEACHER_COUNT, Integer.class);
		logger.debug("complete to GET_TEACHER_COUNT...");
		return count;
	}

	@Override
	public int getStudentCount(String teacherID) {
		logger.debug("Begin to GET_STUDENT_COUNT");
		int count = JdbcTemplate.queryForObject(GET_STUDENT_COUNT, Integer.class,teacherID);
		logger.debug("complete to GET_STUDENT_COUNT...");
		return count;
	}

}
