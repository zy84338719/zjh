package com.zttx.po;

import java.util.Date;

/**
 * 催促表
 * @author liyuan
 * 2017年12月9日
 */
public class Urge {
	private Integer urgeid;
	private String sender;
	private String receiver;
	private String taskid;
	private Date updatetime;
	
	public Integer getUrgeid() {
		return urgeid;
	}
	public void setUrgeid(Integer urgeid) {
		this.urgeid = urgeid;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public Date getUpdatetime() {
		return updatetime;
	}
	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}
	
	
}
