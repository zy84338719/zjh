package com.zttx.po;


/**
 * 催促扩充表
 * @author liyuan
 * 2017年12月9日
 */
public class UrgeCustom extends Urge{
	private String sendername;
	
	private String receivername;

	public String getSendername() {
		return sendername;
	}

	public void setSendername(String sendername) {
		this.sendername = sendername;
	}

	public String getReceivername() {
		return receivername;
	}

	public void setReceivername(String receivername) {
		this.receivername = receivername;
	}
	
	
	
}
