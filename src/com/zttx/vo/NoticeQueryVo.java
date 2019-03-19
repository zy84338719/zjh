package com.zttx.vo;

import java.util.Date;

public class NoticeQueryVo {
	private String msgid;  //主键ID
	private String senderid; //发送者ID
	private Date createtime; //消息创建时间
	private Integer state; //消息状态，0表示未处理，1表示已处理
	private String distanceid;  //工程表ID
	private String content;  //消息内容
	private Integer type;  //消息类型
	private String groupid;	//班组ID
	private String userid;	//登录用户ID
	private Integer module_type;  //模块类型
	private String receiverid; //消息接收人员ID
	
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getSenderid() {
		return senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public String getDistanceid() {
		return distanceid;
	}
	public void setDistanceid(String distanceid) {
		this.distanceid = distanceid;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getModule_type() {
		return module_type;
	}
	public void setModule_type(Integer module_type) {
		this.module_type = module_type;
	}
	public String getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	
}
