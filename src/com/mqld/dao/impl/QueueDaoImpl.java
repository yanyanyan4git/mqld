package com.mqld.dao.impl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.mqld.constant.Constant;
import com.mqld.dao.QueueDao;
import com.mqld.model.Page;
import com.mqld.model.QueueItem;
import com.mqld.model.QueueProcess;
import com.mqld.model.TeachersPerfQueryConditionDTO;
import com.mqld.util.JdbcUtil;
import com.mqld.util.StringUtils;

@Repository
public class QueueDaoImpl implements QueueDao {
	@Autowired
	JdbcTemplate JdbcTemplate;
	
	private static final String GET_TEACHER_STATUS="SELECT u.ID AS teacherID, u.name AS teacherName,t.startWorkTime,t.endWorkTime,IFNULL(t.onWork,false) AS teacherOnWork,t.style AS teacherStyle,t.maxStudentNum,t.contactInfo AS teacherContactInfo,IFNULL(cnt.count,0) AS currentQueueNum ,IFNULL(tcnt.count,0) AS totalQueueNum FROM teacher t,user u LEFT JOIN (SELECT b.ID, COUNT(*) AS count FROM queue q RIGHT JOIN user b ON b.ID=q.teacherID Where status=? AND DATE_FORMAT(q.createTime,'%Y-%m-%d')>=? GROUP BY b.ID) cnt ON u.ID=cnt.ID  LEFT JOIN (SELECT b.ID, COUNT(*) AS count FROM queue q LEFT JOIN user b ON b.ID=q.teacherID WHERE DATE_FORMAT(q.createTime,'%Y-%m-%d')>=?  GROUP BY b.ID) tcnt ON u.ID=tcnt.ID WHERE u.authority=? AND t.userID=u.ID LIMIT ?,?";
	private static final String GET_STUDENT_STATUS="SELECT q.ID,q.pictureNum,q.studentPath,q.studentComment,u.name AS studentName FROM queue q, user u,teacher t WHERE t.userID=q.teacherID AND u.ID=q.studentID AND q.teacherID=? AND q.status=? AND DATE_FORMAT(q.createTime,'%Y-%m-%d')>=? ORDER BY q.createTime LIMIT ?,?";
	private static final String GET_CURRENT_STUDENT_STATUS="SELECT q.pictureNum,q.studentPath,q.studentComment,u.name AS teacherName ,t.maxStudentNum ,a.rownum AS currentQueueNum FROM (SELECT @rownum:=@rownum+1 AS rownum, qu.teacherID,qu.studentID, qu.ID FROM (SELECT @rownum:=0) r, (SELECT * FROM queue WHERE  teacherID=(SELECT teacherID from queue WHERE studentID=? AND DATE_FORMAT(createTime,'%Y-%m-%d')=?) AND DATE_FORMAT(createTime,'%Y-%m-%d')=?  AND status=? ORDER BY createTime)qu )a ,queue q,user u, teacher t WHERE  u.ID=q.teacherID  AND t.userID=q.teacherID  AND a.ID=q.ID AND a.teacherID=q.teacherID AND q.studentID=?";
	private static final String GET_TEACHER_QUEUE_PROCESS="SELECT IFNULL(t.onWork,false) AS onWork,t.maxStudentNum,IFNULL(cnt.count,0) AS totalQueueNum FROM teacher t LEFT JOIN (SELECT t.userID, COUNT(*) AS count FROM queue q RIGHT JOIN teacher t ON t.userID=q.teacherID Where DATE_FORMAT(q.createTime,'%Y-%m-%d')>=? GROUP BY t.userID) cnt ON t.userID=cnt.userID WHERE t.userID=?";
	private static final String GET_QUEUE_INFO="SELECT q.ID,DATE_FORMAT(q.createTime,'%Y-%m-%d %H:%i') AS createTime,q.status,q.pictureNum,q.studentPath,q.studentComment,q.teacherPath,q.teacherComment,u.name AS teacherName FROM queue q, user u WHERE  u.ID=q.teacherID AND q.studentID=? ORDER BY q.createTime LIMIT ?,?";
	private static final String ADD_QUEUE="INSERT INTO queue(createTime,status,teacherID,studentID,pictureNum,studentPath,studentComment) VALUES(NOW(),?,?,?,?,?,?)";
	private static final String RESOLVE_QUEUE="UPDATE queue SET status=?,teacherPath=?,teacherComment=? WHERE ID=?";
	private static final String DELETE_QUEUE="DELETE FROM queue where studentID=? AND status=?";
	private static final String IS_QUEUE_EXIST="SELECT ID FROM queue where studentID=? AND status='排队中' AND DATE_FORMAT(createTime,'%Y-%m-%d')>=?";
	private static final String GET_TEACHER_COUNT="SELECT IFNULL(COUNT(*),0) FROM teacher";
	private static final String GET_STUDENT_COUNT="SELECT IFNULL(COUNT(*),0) FROM queue WHERE status='排队中' AND teacherID=? AND DATE_FORMAT(createTime,'%Y-%m-%d')>=?";
	private static final String  ADD_EVALUATION="INSERT INTO performance(queueID,profLevel,attitude,perfComment) VALUES(?,?,?,?)";
	private static final String EVALUATE_QUEUE="UPDATE queue SET status=? WHERE ID=?";
	private static final String GET_PERFORMANCE="SELECT q.ID,u.name,q.studentPath,q.studentComment,q.teacherPath,q.teacherComment,p.profLevel,p.attitude,p.perfComment FROM user u,queue q,performance p WHERE u.ID=q.studentID AND p.queueID=q.ID AND q.teacherID=? LIMIT ?,?";
	private static final String GET_TEACHERS_PERFORMANCE_FIELDS_PART="SELECT q.createTime,u.name AS studentName,us.name AS teacherName,us.ID ,q.studentPath,q.studentComment,q.teacherPath,q.teacherComment,p.profLevel,p.attitude,p.perfComment ";
	private static final String GET_TEACHERS_PERFORMANCE_CONDITION_PART="FROM queue q LEFT JOIN user u ON u.ID=q.studentID LEFT JOIN user us ON us.ID=q.teacherID LEFT JOIN performance p ON q.ID=p.queueID WHERE  q.status='已评价' ";
	private static final String GET_TEACHER_PERFORMANCE_COUNT=" SELECT COUNT(*) FROM performance p ,queue q WHERE q.ID=p.queueID and q.teacherID=?";
	private static final String GET_COUNT_PART="SELECT COUNT(*) ";
	private static final Logger logger=Logger.getLogger(QueueDaoImpl.class);
	
	@Override
	public boolean queue(QueueItem queue) {
		int l=queue.getStudentComment().length();
		logger.debug("Begin to ADD_QUEUE");
		int	count=JdbcTemplate.update(ADD_QUEUE,Constant.STATUS_PENDING,queue.getTeacherID(),queue.getStudentID(),queue.getPictureNum(),queue.getStudentPath(),queue.getStudentComment());
		logger.debug("complete to ADD_QUEUE..."+count);
		return count>0;
	}

	@Override
	public boolean resolveQueue(QueueItem queue) {
		logger.debug("Begin to RESOLVE_QUEUE");
		int	count=JdbcTemplate.update(RESOLVE_QUEUE,Constant.STATUS_RESOLVE,queue.getTeacherPath(),queue.getTeacherComment(),queue.getID());
		logger.debug("complete to RESOLVE_QUEUE..."+count);
		return count>0;
	}

	@Override
	public boolean cancelQueue(String id) {
		logger.debug("Begin to DELETE_QUEUE");
		int	count=JdbcTemplate.update(DELETE_QUEUE,id,Constant.STATUS_PENDING);
		logger.debug("complete to DELETE_QUEUE..."+count);
		return count>0;
	}
	
	@Override
	public Page<QueueItem> getTeacherStatus(Page<QueueItem> page) {
		RowMapper<QueueItem> rowMapper=new BeanPropertyRowMapper<QueueItem>(QueueItem.class);
		logger.debug("Begin to GET_TEACHER_STATUS");
		List<QueueItem> queues=null;
		queues = JdbcTemplate.query(GET_TEACHER_STATUS, rowMapper,Constant.STATUS_PENDING,StringUtils.getCurrDate(),StringUtils.getCurrDate(),Constant.TEACHER,page.getStartNum(),page.getPageSize());
		logger.debug("complete to GET_TEACHER_STATUS...");
		page.setRecords(queues);
		return page;
	}

	@Override
	public Page<QueueItem> getStudentStatus(String teacherID,Page<QueueItem> page) {
		RowMapper<QueueItem> rowMapper=new BeanPropertyRowMapper<QueueItem>(QueueItem.class);
		logger.debug("Begin to GET_STUDENT_STATUS");
		List<QueueItem> queues= JdbcTemplate.query(GET_STUDENT_STATUS, rowMapper,teacherID,Constant.STATUS_PENDING,StringUtils.getCurrDate(),page.getStartNum(),page.getPageSize());
		logger.debug("complete to GET_STUDENT_STATUS...");
		page.setRecords(queues);
		return page;
	}

	@Override
	public boolean isQueued(String studentID) {
		logger.debug("Begin to IS_QUEUE_EXIST");
		Integer queueId;
		try {
			queueId = JdbcTemplate.queryForObject(IS_QUEUE_EXIST, Integer.class,studentID,StringUtils.getCurrDate());
		} catch (EmptyResultDataAccessException e) {
			return false;
		}
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
		int count = JdbcTemplate.queryForObject(GET_STUDENT_COUNT, Integer.class,teacherID,StringUtils.getCurrDate());
		logger.debug("complete to GET_STUDENT_COUNT...");
		return count;
	}

	@Override
	public QueueItem getStudentCurrentStatus(String id) {
		RowMapper<QueueItem> rowMapper=new BeanPropertyRowMapper<QueueItem>(QueueItem.class);
		logger.debug("Begin to GET_CURRENT_STUDENT_STATUS");
		QueueItem queueItem=null;
		try {
			queueItem = JdbcTemplate.queryForObject(GET_CURRENT_STUDENT_STATUS, rowMapper,id,StringUtils.getCurrDate(),StringUtils.getCurrDate(),Constant.STATUS_PENDING,id);
		} catch (DataAccessException e) {
			logger.debug("no result when GET_CURRENT_STUDENT_STATUS...");
			return null;
		}
		logger.debug("complete to GET_CURRENT_STUDENT_STATUS...");
		return queueItem;
	}

	@Override
	public Page<QueueItem> getQueueInfo(String id,Page<QueueItem> page) {
		RowMapper<QueueItem> rowMapper=new BeanPropertyRowMapper<QueueItem>(QueueItem.class);
		logger.debug("Begin to GET_QUEUE_INFO");
		List<QueueItem> queues= JdbcTemplate.query(GET_QUEUE_INFO, rowMapper,id,page.getStartNum(),page.getPageSize());
		logger.debug("complete to GET_QUEUE_INFO...");
		page.setRecords(queues);
		return page;
	}

	@Override
	public QueueProcess getTeacherQueueProcess(String teacherID) {
		RowMapper<QueueProcess> rowMapper=new BeanPropertyRowMapper<QueueProcess>(QueueProcess.class);
		logger.debug("Begin to GET_TEACHER_QUEUE_PROCESS");
		QueueProcess process = JdbcTemplate.queryForObject(GET_TEACHER_QUEUE_PROCESS, rowMapper,StringUtils.getCurrDate(),teacherID);
		logger.debug("complete to GET_TEACHER_QUEUE_PROCESS...");
		return process;
	}

	@Override
	public boolean addEvaluation(QueueItem queue) {
		logger.debug("Begin to ADD_EVALUATION");
		int	count=JdbcTemplate.update(ADD_EVALUATION,queue.getID(),queue.getProfLevel(),queue.getAttitude(),queue.getPerfComment());

		logger.debug("complete to ADD_EVALUATION..."+count);
		if (count>0) {
			count=JdbcTemplate.update(EVALUATE_QUEUE,Constant.STATUS_EVALUATED,queue.getID());
		}
		return count>0;
	}

	@Override
	public Page<QueueItem> getTeacherPerf(String ID,Page<QueueItem> page) {
		RowMapper<QueueItem> rowMapper=new BeanPropertyRowMapper<QueueItem>(QueueItem.class);
		logger.debug("Begin to GET_PERFORMANCE");
		List<QueueItem> queues= JdbcTemplate.query(GET_PERFORMANCE, rowMapper,ID,page.getStartNum(),page.getPageSize());
		logger.debug("complete to GET_PERFORMANCE...");
		page.setRecords(queues);
		return page;
	}

	@Override
	public Page<QueueItem> getTeachersPerf(TeachersPerfQueryConditionDTO condition,Page<QueueItem> page) {
		condition.setPagination(true);
		
		DataSource dataSource=JdbcTemplate.getDataSource();
		Connection conn=null;
		PreparedStatement stmt=null;
		ResultSet rs=null;
		try {
			conn=dataSource.getConnection();
			condition.setPagination(true);
			System.out.println(getTeachersPerfStatement(condition));
			stmt=conn.prepareStatement(getTeachersPerfStatement(condition));
			logger.debug("Begin to GET_PERFORMANCE_COUNT");
			int i=0;
			setStmtProp(condition, stmt, i);
			rs=stmt.executeQuery();
			if (rs.next()) {
				int count=rs.getInt(1);
				page.setTotalRecord(count);
				}
			stmt.close();
			rs.close();
			logger.debug("complete to GET_PERFORMANCE_COUNT...");
			
			logger.debug("Begin to GET_PERFORMANCE");
			condition.setPagination(false);
			System.out.println(getTeachersPerfStatement(condition));
			stmt=conn.prepareStatement(getTeachersPerfStatement(condition));
			int j=0;
			j=setStmtProp(condition, stmt, j);
			stmt.setInt(++j, page.getStartNum());
			System.out.println("stmt.setInt("+j+","+page.getStartNum()+");");
			stmt.setInt(++j, page.getPageSize());
			System.out.println("stmt.setInt("+j+","+page.getPageSize()+");");
			rs=stmt.executeQuery();
			List<QueueItem> queues=new ArrayList<QueueItem>();
			while (rs.next()) {
				QueueItem qItem=new QueueItem();
				SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
				qItem.setCreateTime(sdf.format(rs.getDate("createTime")));
				qItem.setTeacherID(rs.getString("ID"));
				qItem.setStudentName(rs.getString("studentName"));
				qItem.setTeacherName(rs.getString("teacherName"));
				qItem.setStudentPath(rs.getString("studentPath"));
				qItem.setStudentComment(rs.getString("studentComment"));
				qItem.setTeacherPath(rs.getString("teacherPath"));
				qItem.setTeacherComment(rs.getString("teacherComment"));
				qItem.setProfLevel(rs.getString("profLevel"));
				qItem.setAttitude(rs.getString("attitude"));
				qItem.setPerfComment(rs.getString("perfComment"));
				queues.add(qItem);
				}
			page.setRecords(queues);
			logger.debug("complete to GET_PERFORMANCE...");
		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			JdbcUtil.release(rs, stmt, conn);
		}
		return page;
	}

	private int setStmtProp(TeachersPerfQueryConditionDTO condition, PreparedStatement stmt, int i)
			throws SQLException {
		if (null != condition.getID()) {
			stmt.setString(++i, condition.getID());
			System.out.println("stmt.setString("+i+","+condition.getID()+");");
		}
		
		if (!"".equals(condition.getStartCreateTime())&&!"".equals(condition.getEndCreateTime())&&null != condition.getStartCreateTime() && null != condition.getEndCreateTime()) {
			stmt.setString(++i, condition.getStartCreateTime());
			System.out.println("stmt.setString("+i+","+condition.getStartCreateTime()+");");
			stmt.setString(++i, condition.getEndCreateTime());
			System.out.println("stmt.setString("+i+","+condition.getEndCreateTime()+");");
		}

		if (null != condition.getLowTotalScore() && null != condition.getHighTotalScore()) {
			stmt.setInt(++i, condition.getLowTotalScore());
			System.out.println("stmt.setString("+i+","+condition.getLowTotalScore()+");");
			stmt.setInt(++i, condition.getHighTotalScore());
			System.out.println("stmt.setString("+i+","+condition.getHighTotalScore()+");");
		}
		return i;
	}

	@Override
	public int getTeacherPerfCount(String ID) {
		logger.debug("Begin to GET_TEACHER_PERFORMANCE_COUNT");
		int count = JdbcTemplate.queryForObject(GET_TEACHER_PERFORMANCE_COUNT, Integer.class,ID);
		logger.debug("complete to GET_TEACHER_PERFORMANCE_COUNT...");
		return count;
	}
	
	private String getTeachersPerfStatement(TeachersPerfQueryConditionDTO condition) {
		StringBuilder sb;
		if (condition.isPagination()) {
			sb = new StringBuilder(GET_COUNT_PART);
		} else {
			sb = new StringBuilder(GET_TEACHERS_PERFORMANCE_FIELDS_PART);
		}
		sb.append(GET_TEACHERS_PERFORMANCE_CONDITION_PART);
		if (null != condition.getID()) {
			sb.append("and us.ID=? ");
		}

		if (!"".equals(condition.getStartCreateTime())&&!"".equals(condition.getEndCreateTime())&&null != condition.getStartCreateTime() && null != condition.getEndCreateTime()) {
			sb.append("and q.createTime>=? and q.createTime<=? ");
		}

		if (null != condition.getLowTotalScore() && null != condition.getHighTotalScore()) {
			sb.append("and (p.attitude+p.profLevel)>=? and (p.attitude+p.profLevel)<=? ");
		}
		sb.append("ORDER BY (p.attitude+p.profLevel) ");
		if (condition.isScoreAsc()) {
			sb.append("Asc ");
		} else {
			sb.append("Desc ");
		}
		if (!condition.isPagination()) {
		sb.append(",q.createTime Desc LIMIT ?,?");
		}
		String sqlStatement=sb.toString();
		return sqlStatement;
	}

}
