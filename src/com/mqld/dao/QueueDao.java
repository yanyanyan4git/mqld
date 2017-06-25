package com.mqld.dao;

import com.mqld.model.Page;
import com.mqld.model.QueueItem;

public interface QueueDao {

	Page<QueueItem> getTeacherStatus(Page<QueueItem> page);

	Page<QueueItem> getStudentStatus(String teacherID,Page<QueueItem> page);

	boolean Queue(QueueItem queue);

	boolean resolveQueue(QueueItem queue);

	boolean cancelQueue(String id);

	boolean isQueued(String studentID);
	
	int getTeacherCount();
	
	int getStudentCount(String teacherID);
}
