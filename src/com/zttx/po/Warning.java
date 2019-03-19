package com.zttx.po;

import java.util.Date;
/**
 * 警告信息
 * 2017年10月24日
 * @author ZackLee
 *
 */
public class Warning {
	
	private String warningid;	//警告消息ID
	private String senderid;	//消息发送人员ID
	private Date createtime;	//消息创建时间
	private Integer state;	//消息状态
	private Float startdistance;	//起始里程
	private Float enddistance;	//终止里程
	private Integer type;	//消息类型
	private Float theoryvolume;	//理论混凝土方量
	private Float pouringvolume;	//浇筑方量
	private Float pouringerror;	//浇筑误差
	private String tender;	//施工标段
	private String content;	//警报消息内容
	
	public String getWarningid() {
		return warningid;
	}
	public void setWarningid(String warningid) {
		this.warningid = warningid;
	}
	public String getSenderid() {
		return senderid;
	}
	public void setSenderid(String senderid) {
		this.senderid = senderid;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	public Float getStartdistance() {
		return startdistance;
	}
	public void setStartdistance(Float startdistance) {
		this.startdistance = startdistance;
	}
	public Float getEnddistance() {
		return enddistance;
	}
	public void setEnddistance(Float enddistance) {
		this.enddistance = enddistance;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Float getTheoryvolume() {
		return theoryvolume;
	}
	public void setTheoryvolume(Float theoryvolume) {
		this.theoryvolume = theoryvolume;
	}
	public Float getPouringvolume() {
		return pouringvolume;
	}
	public void setPouringvolume(Float pouringvolume) {
		this.pouringvolume = pouringvolume;
	}
	public Float getPouringerror() {
		return pouringerror;
	}
	public void setPouringerror(Float pouringerror) {
		this.pouringerror = pouringerror;
	}
	public String getTender() {
		return tender;
	}
	public void setTender(String tender) {
		this.tender = tender;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}

}
