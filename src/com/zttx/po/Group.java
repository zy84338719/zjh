package com.zttx.po;

import java.util.Date;

/**
 * 组
 * @author liyuan
 * 2017年10月5日
 */
public class Group {
	private String groupid; //班组ID
	private String groupname; //班组名称
	private String code; //班组编号
	private String type;  //班组类别
	
	//add by liyuan on 2017-12-12
	private Date createtime;
	private Boolean isDeleted;
	
	private String _groupid;
	
	public String get_groupid() {
		return _groupid;
	}
	public void set_groupid(String _groupid) {
		this._groupid = _groupid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Boolean getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}
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
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
}
