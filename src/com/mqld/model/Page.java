package com.mqld.model;

import java.util.List;

import com.mqld.constant.Constant;

public class Page<T> {
	private List<T> records;
	private int totalRecord;
	private int pageNum;
	private int currentPage;
	private int pageSize;
	private int startNum;
	private int startPage;
	public int getStartPage() {
		return startPage;
	}
	public int getEndPage() {
		return endPage;
	}
	private int endPage;
	public Page(int currentPage, int pageSize) {
		super();
		this.currentPage = currentPage;
		this.pageSize = pageSize;
		startNum=(currentPage-1)*pageSize;
	}
	public List<T> getRecords() {
		return records;
	}
	public void setRecords(List<T> records) {
		this.records = records;
	}
	public int getTotalRecord() {
		return totalRecord;
	}
	public void setTotalRecord(int totalRecord) {
		this.totalRecord = totalRecord;
		if (totalRecord%pageSize==0) {
			pageNum=totalRecord/pageSize;
		}else {
			pageNum=totalRecord/pageSize+1;
		}
		
		if (this.pageNum < Constant.SHOWPAGE) {
			this.startPage = 1;
			this.endPage = this.pageNum;
		}else {
			this.startPage = this.currentPage - Constant.SHOWPAGE / 2;
			this.endPage = this.currentPage + Constant.SHOWPAGE / 2;
			if (this.startPage < 1) {
				this.startPage = 1;
				this.endPage = Constant.SHOWPAGE;
			}
			if (this.endPage > this.pageNum) {
				this.startPage = this.pageNum - Constant.SHOWPAGE + 1;
				this.endPage = this.pageNum;
			}
		}
	}
	public int getPageNum() {
		return pageNum;
	}
	public void setPageNum(int pageNum) {
		this.pageNum = pageNum;
	}
	public int getCurrentPage() {
		return currentPage;
	}
	public void setCurrentPage(int currentPage) {
		this.currentPage = currentPage;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getStartNum() {
		return startNum;
	}
	public void setStartNum(int startNum) {
		this.startNum = startNum;
	}
	
	
	
}
