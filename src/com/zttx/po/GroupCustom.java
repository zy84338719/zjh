package com.zttx.po;

import java.util.List;

public class GroupCustom extends Group {

	private String fathergroupname;
	private List<User> userList;

	public String getFathergroupname() {
		return fathergroupname;
	}

	public void setFathergroupname(String fathergroupname) {
		this.fathergroupname = fathergroupname;
	}

	public List<User> getUserList() {
		return userList;
	}

	public void setUserList(List<User> userList) {
		this.userList = userList;
	}

}
