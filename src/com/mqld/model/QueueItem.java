package com.mqld.model;

public class QueueItem {
	private int ID;
	private String createTime;
	private String studentID;
	private String teacherID;
	private String startWorkTime;
	private String endWorkTime;
	private String studentName;
	private String teacherName;
	private String teacherStyle;
	private String maxStudentNum;
	private String teacherContactInfo;
	private String status;
	private String studentPath;
	private String studentComment;
	private String teacherPath;
	private String teacherComment;
	private String profLevel;
	private String attitude;
	private String perfComment;
	private int currentQueueNum;
	private int totalQueueNum;
	private int pictureNum;
	private boolean teacherOnWork;
	
	public String getStudentID() {
		return studentID;
	}
	public void setStudentID(String studentID) {
		this.studentID = studentID;
	}
	public String getTeacherID() {
		return teacherID;
	}
	public void setTeacherID(String teacherID) {
		this.teacherID = teacherID;
	}
	public String getStartWorkTime() {
		return startWorkTime;
	}
	public void setStartWorkTime(String startWorkTime) {
		this.startWorkTime = startWorkTime;
	}
	public String getEndWorkTime() {
		return endWorkTime;
	}
	public void setEndWorkTime(String endWorkTime) {
		this.endWorkTime = endWorkTime;
	}
	public String getStudentName() {
		return studentName;
	}
	public void setStudentName(String studentName) {
		this.studentName = studentName;
	}
	public String getTeacherName() {
		return teacherName;
	}
	public void setTeacherName(String teacherName) {
		this.teacherName = teacherName;
	}
	public String getTeacherStyle() {
		return teacherStyle;
	}
	public void setTeacherStyle(String teacherStyle) {
		this.teacherStyle = teacherStyle;
	}
	public String getMaxStudentNum() {
		return maxStudentNum;
	}
	public void setMaxStudentNum(String maxStudentNum) {
		this.maxStudentNum = maxStudentNum;
	}
	public String getTeacherContactInfo() {
		return teacherContactInfo;
	}
	public void setTeacherContactInfo(String teacherContactInfo) {
		this.teacherContactInfo = teacherContactInfo;
	}
	public int getCurrentQueueNum() {
		return currentQueueNum;
	}
	public void setCurrentQueueNum(int currentQueueNum) {
		this.currentQueueNum = currentQueueNum;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getPictureNum() {
		return pictureNum;
	}
	public void setPictureNum(int pictureNum) {
		this.pictureNum = pictureNum;
	}
	public String getStudentPath() {
		return studentPath;
	}
	public void setStudentPath(String studentPath) {
		this.studentPath = studentPath;
	}
	public String getStudentComment() {
		return studentComment;
	}
	public void setStudentComment(String studentComment) {
		this.studentComment = studentComment;
	}
	public String getTeacherPath() {
		return teacherPath;
	}
	public void setTeacherPath(String teacherPath) {
		this.teacherPath = teacherPath;
	}
	public String getTeacherComment() {
		return teacherComment;
	}
	public void setTeacherComment(String teacherComment) {
		this.teacherComment = teacherComment;
	}
	public String getProfLevel() {
		return profLevel;
	}
	public void setProfLevel(String profLevel) {
		this.profLevel = profLevel;
	}
	public String getAttitude() {
		return attitude;
	}
	public void setAttitude(String attitude) {
		this.attitude = attitude;
	}
	public String getPerfComment() {
		return perfComment;
	}
	public void setPerfComment(String perfComment) {
		this.perfComment = perfComment;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public int getTotalQueueNum() {
		return totalQueueNum;
	}
	public void setTotalQueueNum(int totalQueueNum) {
		this.totalQueueNum = totalQueueNum;
	}
	public boolean isTeacherOnWork() {
		return teacherOnWork;
	}
	public void setTeacherOnWork(boolean teacherOnWork) {
		this.teacherOnWork = teacherOnWork;
	}
	
}
