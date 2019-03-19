package com.zttx.po;
/**
 * 二级权限
 * 2017年11月1日
 * @author ZackLee
 *
 */
public class SecondAuthority {

	private String secondid;
	private String functionname;
	private String iconurl;
	private String topid;
	private String functionurl;
	
	public String getSecondid() {
		return secondid;
	}
	public void setSecondid(String secondid) {
		this.secondid = secondid;
	}
	public String getFunctionname() {
		return functionname;
	}
	public void setFunctionname(String functionname) {
		this.functionname = functionname;
	}
	public String getIconurl() {
		return iconurl;
	}
	public void setIconurl(String iconurl) {
		this.iconurl = iconurl;
	}
	public String getTopid() {
		return topid;
	}
	public void setTopid(String topid) {
		this.topid = topid;
	}
	public String getFunctionurl() {
		return functionurl;
	}
	public void setFunctionurl(String functionurl) {
		this.functionurl = functionurl;
	}
}
