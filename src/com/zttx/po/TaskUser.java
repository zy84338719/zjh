package com.zttx.po;

import java.util.Date;

public class TaskUser {

	private String taskid;	//任务清单ID
	private String userid;	//接收人员ID，与用户表关联
	private Integer state;	//任务完成状态，0表示“未完成”，1表示“已处理”，2表示“已催促”，-1表示“逾期未处理”
	private Date dealtime;	//处理时间
	private String dealresult;	//处理结果
	//2017-11-29
	private Float score;	//任务对应得分
	
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Date getDealtime() {
		return dealtime;
	}
	public void setDealtime(Date dealtime) {
		this.dealtime = dealtime;
	}
	public String getDealresult() {
		return dealresult;
	}
	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
}
