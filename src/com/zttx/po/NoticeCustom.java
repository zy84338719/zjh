package com.zttx.po;

public class NoticeCustom extends Notice {
	private String groupid; //班组ID
	private String groupname; //班组名称
	private String code; //班组编号
	
	
	//新增发送者
	private String sender;	//发送者
	
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	
}
