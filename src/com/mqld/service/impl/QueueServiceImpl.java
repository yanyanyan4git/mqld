package com.mqld.service.impl;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mqld.dao.QueueDao;
import com.mqld.model.Page;
import com.mqld.service.QueueService;
import com.mqld.util.StringUtils;
import com.mqld.model.QueueItem;
import com.mqld.model.QueueProcess;

@Service
public class QueueServiceImpl implements QueueService {
	@Autowired
	QueueDao queueDao;
	
	private static final Logger logger=Logger.getLogger(QueueServiceImpl.class);
	
	@Override
	public Page<QueueItem> getTeacherStatus(Page<QueueItem> page) {
		int totalRecord= queueDao.getTeacherCount();
		page.setTotalRecord(totalRecord);
		return queueDao.getTeacherStatus(page);
	}

	@Override
	public Page<QueueItem> getStudentStatus(String teacherID,Page<QueueItem> page) {
		int totalRecord= queueDao.getStudentCount(teacherID);
		page.setTotalRecord(totalRecord);
		return queueDao. getStudentStatus(teacherID,page);
	}

	@Override
	public boolean queue(QueueItem queue) {
		if (isQueued(queue.getStudentID())) {
			return false;
		}
		return queueDao.queue(queue);
	}

	@Override
	public boolean resolveQueue(QueueItem queue) {
		if (0==queue.getID()) {
			logger.error("null parameter");
			return false;
		}
		// TODO Auto-generated method stub
		return queueDao.resolveQueue(queue);
	}

	@Override
	public boolean cancelQueue(String id) {
		if (StringUtils.validateEmpty(id)) {
			logger.error("null parameter");
			return false;
		}
		return queueDao.cancelQueue(id);
	}

	@Override
	public boolean isQueued(String id) {
		if (StringUtils.validateEmpty(id)) {
			logger.error("null parameter");
			return false;
		}
		return queueDao.isQueued(id);
	}

	@Override
	public QueueItem getStuCurrStatus(String stuID) {
		if (StringUtils.validateEmpty(stuID)) {
			logger.error("null parameter");
			return null;
		}
		return queueDao.getStudentCurrentStatus(stuID);
	}

	@Override
	public Page<QueueItem> getQueueHistory(String stuID, Page<QueueItem> page) {
		if (StringUtils.validateEmpty(stuID)) {
			logger.error("null parameter");
			return null;
		}
		return queueDao.getQueueInfo(stuID, page);
	}

	@Override
	public boolean canQueue(String teacherID) {
		if (StringUtils.validateEmpty(teacherID)) {
			logger.error("null parameter");
			return false;
		}
		QueueProcess process= queueDao.getTeacherQueueProcess(teacherID);
		
		return process.isOnWork()&&(process.getMaxStudentNum()>process.getTotalQueueNum());
	}

	@Override
	public boolean evaluate(QueueItem qItem) {
		if (StringUtils.validateEmpty(qItem.getAttitude(),qItem.getPerfComment(),qItem.getProfLevel())) {
			logger.error("null parameter");
			return false;
		}
		return queueDao.addEvaluation(qItem);
	}

	@Override
	public Page<QueueItem> getTeacherPerf(String ID,Page<QueueItem> page) {
		int count=queueDao.getTeacherPerfCount(ID);
		page.setTotalRecord(count);
		return queueDao.getTeacherPerf( ID,page);
	}

	@Override
	public Page<QueueItem> getBadPerf(Page<QueueItem> page) {
		int count=queueDao.getBadPerfCount();
		page.setTotalRecord(count);
		return queueDao.getBadPerf( page);
	}

	


}
