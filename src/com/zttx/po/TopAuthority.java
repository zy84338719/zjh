package com.zttx.po;
/**
 * 一级权限
 * 2017年11月1日
 * @author ZackLee
 *
 */
public class TopAuthority {
	
	private String topid;
	private String departmentname;
	private String iconurl;
	private String functionurl;
	
	public String getTopid() {
		return topid;
	}
	public void setTopid(String topid) {
		this.topid = topid;
	}
	public String getDepartmentname() {
		return departmentname;
	}
	public void setDepartmentname(String departmentname) {
		this.departmentname = departmentname;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	public String getFunctionurl() {
		return functionurl;
	}
	public void setFunctionurl(String functionurl) {
		this.functionurl = functionurl;
	}

}
