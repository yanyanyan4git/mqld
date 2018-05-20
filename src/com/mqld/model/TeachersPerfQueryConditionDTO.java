package com.mqld.model;

import org.codehaus.jackson.annotate.JsonProperty;

public class TeachersPerfQueryConditionDTO {
@JsonProperty("ID")
 private String ID;
 private String startCreateTime;
 private String endCreateTime;
 private Integer lowTotalScore;
 private Integer highTotalScore;
 private boolean scoreAsc;
 private boolean pagination;
public String getID() {
	return ID;
}
public void setID(String iD) {
	ID = iD;
}
public String getStartCreateTime() {
	return startCreateTime;
}
public void setStartCreateTime(String startCreateTime) {
	this.startCreateTime = startCreateTime;
}
public String getEndCreateTime() {
	return endCreateTime;
}
public void setEndCreateTime(String endCreateTime) {
	this.endCreateTime = endCreateTime;
}

public Integer getLowTotalScore() {
	return lowTotalScore;
}
public void setLowTotalScore(Integer lowTotalScore) {
	this.lowTotalScore = lowTotalScore;
}

public Integer getHighTotalScore() {
	return highTotalScore;
}
public void setHighTotalScore(Integer highTotalScore) {
	this.highTotalScore = highTotalScore;
}
public boolean isScoreAsc() {
	return scoreAsc;
}
public void setScoreAsc(boolean scoreAsc) {
	this.scoreAsc = scoreAsc;
}
public boolean isPagination() {
	return pagination;
}
public void setPagination(boolean pagination) {
	this.pagination = pagination;
}

}
