package com.mqld.dao;

import com.mqld.model.Page;
import com.mqld.model.QueueItem;
import com.mqld.model.QueueProcess;
import com.mqld.model.TeachersPerfQueryConditionDTO;

public interface QueueDao {

	Page<QueueItem> getTeacherStatus(Page<QueueItem> page);

	Page<QueueItem> getStudentStatus(String teacherID,Page<QueueItem> page);

	boolean queue(QueueItem queue);

	boolean resolveQueue(QueueItem queue);

	boolean cancelQueue(String id);

	boolean isQueued(String studentID);
	
	int getTeacherCount();
	
	int getStudentCount(String teacherID);
	
	QueueItem getStudentCurrentStatus(String id);
	
	Page<QueueItem> getQueueInfo(String id,Page<QueueItem> page);

	QueueProcess getTeacherQueueProcess(String teacherID);

	boolean addEvaluation(QueueItem qItem);

	Page<QueueItem> getTeacherPerf(String ID,Page<QueueItem> page);

	Page<QueueItem> getTeachersPerf(TeachersPerfQueryConditionDTO condition, Page<QueueItem> page);

	int getTeacherPerfCount(String ID);

}
