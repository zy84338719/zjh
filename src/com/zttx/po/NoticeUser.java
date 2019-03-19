package com.zttx.po;

public class NoticeUser {
	private String msgid;
	private String receiverid;
	private Integer state;	//消息操作状态，0表示“未读”，1表示“已读”
	public String getMsgid() {
		return msgid;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	public String getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
}
