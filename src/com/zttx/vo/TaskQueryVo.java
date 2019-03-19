package com.zttx.vo;

import java.util.Date;

import com.zttx.po.Task;

public class TaskQueryVo extends Task{

	private String username;
	private String groupname;
	//任务接收信息
	private String userids;
	private Integer state;
	
	private String title;
	
	private Date time1;
	private Date time2;
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}

	public Date getTime1() {
		return time1;
	}
	public void setTime1(Date time1) {
		this.time1 = time1;
	}
	public Date getTime2() {
		return time2;
	}
	public void setTime2(Date time2) {
		this.time2 = time2;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getUserids() {
		return userids;
	}
	public void setUserids(String userids) {
		this.userids = userids;
	}
}
