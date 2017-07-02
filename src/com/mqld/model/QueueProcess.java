package com.mqld.model;

public class QueueProcess {
	private boolean onWork;
	private int maxStudentNum;
	private int totalQueueNum;
	public boolean isOnWork() {
		return onWork;
	}
	public void setOnWork(boolean onWork) {
		this.onWork = onWork;
	}
	public int getMaxStudentNum() {
		return maxStudentNum;
	}
	public void setMaxStudentNum(int maxStudentNum) {
		this.maxStudentNum = maxStudentNum;
	}
	public int getTotalQueueNum() {
		return totalQueueNum;
	}
	public void setTotalQueueNum(int totalQueueNum) {
		this.totalQueueNum = totalQueueNum;
	}
}
