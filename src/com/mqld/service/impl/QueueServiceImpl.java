package com.mqld.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.mqld.dao.QueueDao;
import com.mqld.model.Page;
import com.mqld.service.QueueService;
import com.mqld.model.QueueItem;

@Service
public class QueueServiceImpl implements QueueService {
	@Autowired
	QueueDao queueDao;
	
	@Override
	public Page<QueueItem> getTeacherStatus(Page<QueueItem> page) {
		// TODO Auto-generated method stub
		return queueDao.getTeacherStatus(page);
	}

	@Override
	public Page<QueueItem> getStudentStatus(String teacherID,Page<QueueItem> page) {
		// TODO Auto-generated method stub
		return queueDao. getStudentStatus(teacherID,page);
	}

	@Override
	public boolean queue(QueueItem queue) {
		if (queueDao.isQueued(queue.getStudentID())) {
			return false;
		}
		return queueDao.Queue(queue);
	}

	@Override
	public boolean resolveQueue(QueueItem queue) {
		// TODO Auto-generated method stub
		return queueDao.resolveQueue(queue);
	}

	@Override
	public boolean cancelQueue(String id) {
		// TODO Auto-generated method stub
		return queueDao.cancelQueue(id);
	}

	@Override
	public boolean isQueued(String id) {
		return queueDao.isQueued(id);
	}


}
