package com.mqld.service;

import com.mqld.model.Page;
import com.mqld.model.QueueItem;

public interface QueueService {
	Page<QueueItem> getTeacherStatus(Page<QueueItem> page);
	Page<QueueItem> getStudentStatus(String teacherID,Page<QueueItem> page);
	boolean queue(QueueItem queue);
	boolean resolveQueue(QueueItem queue);
	boolean cancelQueue(String id);
	boolean isQueued(String id);
	
}
