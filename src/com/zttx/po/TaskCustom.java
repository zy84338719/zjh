package com.zttx.po;

import java.util.List;


public class TaskCustom extends Task{

	private List<User> user_list;	//任务接收人员
	private String sender;	//任务创建人员
	private String receiver;	//任务接收人员
	private Integer state;	//状态
	private List<TaskUserCustom> taskUserCustom_list;	//任务接收人员列表

	public List<User> getUser_list() {
		return user_list;
	}

	public void setUser_list(List<User> user_list) {
		this.user_list = user_list;
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

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public List<TaskUserCustom> getTaskUserCustom_list() {
		return taskUserCustom_list;
	}

	public void setTaskUserCustom_list(List<TaskUserCustom> taskUserCustom_list) {
		this.taskUserCustom_list = taskUserCustom_list;
	}
	
	
	
}
