package com.zttx.po;

public class WarningCustom extends Warning {
	
	private String receiver;
	private String sender;
	private String receiverid;
	private String tendername;
	public String getReceiver() {
		return receiver;
	}
	public void setReceiver(String receiver) {
		this.receiver = receiver;
	}
	public String getSender() {
		return sender;
	}
	public void setSender(String sender) {
		this.sender = sender;
	}
	public String getReceiverid() {
		return receiverid;
	}
	public void setReceiverid(String receiverid) {
		this.receiverid = receiverid;
	}
	public String getTendername() {
		return tendername;
	}
	public void setTendername(String tendername) {
		this.tendername = tendername;
	}

}
