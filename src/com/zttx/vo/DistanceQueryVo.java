package com.zttx.vo;

import java.util.Date;

public class DistanceQueryVo {

	private String distanceid;	//里程ID
	private Integer handle_state;	//超欠挖处理状态，“通过”或者“拒绝”（1或者2）
	private Date rectify_time;	//整改时间设置
	
	private Float start_distance;	//开始里程
	private Float end_distance;	//终止里程
	private String groupid;	//班组id
	private Date begintime;	//开始时间
	private Date endtime;	//结束时间
	//2017-10-17
	private String groupname;	//班组名
	
	public String getDistanceid() {
		return distanceid;
	}
	public void setDistanceid(String distanceid) {
		this.distanceid = distanceid;
	}
	public Integer getHandle_state() {
		return handle_state;
	}
	public void setHandle_state(Integer handle_state) {
		this.handle_state = handle_state;
	}
	public Date getRectify_time() {
		return rectify_time;
	}
	public void setRectify_time(Date rectify_time) {
		this.rectify_time = rectify_time;
	}
	public Float getStart_distance() {
		return start_distance;
	}
	public void setStart_distance(Float start_distance) {
		this.start_distance = start_distance;
	}
	public Float getEnd_distance() {
		return end_distance;
	}
	public void setEnd_distance(Float end_distance) {
		this.end_distance = end_distance;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public Date getBegintime() {
		return begintime;
	}
	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
}
