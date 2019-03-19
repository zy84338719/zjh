package com.zttx.po;
/**
 * 警告用户消息关联
 * 2017年10月24日
 * @author ZackLee
 *
 */
public class WarningUser {

	private String warningid;
	private String receiverid;
	private Integer state;
	public String getWarningid() {
		return warningid;
	}
	public void setWarningid(String warningid) {
		this.warningid = warningid;
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
