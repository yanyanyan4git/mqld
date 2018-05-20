package com.mqld.service;

import com.mqld.model.Page;
import com.mqld.model.QueueItem;
import com.mqld.model.TeachersPerfQueryConditionDTO;

public interface QueueService {
	Page<QueueItem> getTeacherStatus(Page<QueueItem> page);
	Page<QueueItem> getStudentStatus(String teacherID,Page<QueueItem> page);
	boolean queue(QueueItem queue);
	boolean resolveQueue(QueueItem queue);
	boolean cancelQueue(String id);
	boolean isQueued(String id);
	QueueItem getStuCurrStatus(String stuID);
	Page<QueueItem> getQueueHistory(String stuID,Page<QueueItem> page);
	boolean canQueue(String teacherID);
	boolean evaluate(QueueItem qItem);
	Page<QueueItem> getTeacherPerf(String ID,Page<QueueItem> page);
	Page<QueueItem> getTeachersPerf(TeachersPerfQueryConditionDTO condition, Page<QueueItem> page);
}
