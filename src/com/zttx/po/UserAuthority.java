package com.zttx.po;
/**
 * 用户权限关联
 * 2017年11月1日
 * @author ZackLee
 *
 */
public class UserAuthority {

	private String userid;
	private String topauthority;
	private String secondauthority;
	private String password;
	
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getTopauthority() {
		return topauthority;
	}
	public void setTopauthority(String topauthority) {
		this.topauthority = topauthority;
	}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getSecondauthority() {
		return secondauthority;
	}
	public void setSecondauthority(String secondauthority) {
		this.secondauthority = secondauthority;
	}
}
