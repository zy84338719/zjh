package com.zttx.po;

import java.util.Date;

/**
 * 里程表（项目表）
 * @author liyuan
 * 2017年10月5日
 */
public class Distance {
	private String distanceid;  //里程UUID
	private Float begindistance;  //距离洞口开始里程,单位米
	private Float enddistance;  //距离洞口结束里程,单位米
	private Date begintime;  //开始时间
	private Date endtime;  //结束时间
	
	private Integer state;  //里程状态(0-异常,1-正常)
	
	private Float firstexceedvolume;  //该工程 首次测量超挖总土方量
	private Float firstowevolume;  //该工程首次欠挖总土方量
	
	private Float secondexceedvolume;  //二次测量超挖土方量
	private Float secondowevolume;  //二次测量欠挖土方量
	
	private Float thirdexceedvolume;  //三次测量超挖土方量
	private Float thirdowevolume;  //三次测量欠挖土方量
	
	private Float fourthexceedvolume;  //四次测量超挖土方量
	private Float fourthowevolume;  //四次测量欠挖土方量
	
	private String groupid;  //负责组id
	
	private Integer handle_state;	//超欠挖整改状态，0为“超欠挖整改尚未完成”，1为“超欠挖整改已完成”，2为“拒绝”
	//2017-10-24
	private String tenderid;	//里程所属标段ID

	//add by liyuan on 2017-12-12
	private Boolean isDeleted;
	private Date createtime;
	
	private String _distanceid;
	
	

	public String get_distanceid() {
		return _distanceid;
	}

	public void set_distanceid(String _distanceid) {
		this._distanceid = _distanceid;
	}

	public Boolean getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(Boolean isDeleted) {
		this.isDeleted = isDeleted;
	}

	public Date getCreatetime() {
		return createtime;
	}

	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}

	public String getDistanceid() {
		return distanceid;
	}

	public void setDistanceid(String distanceid) {
		this.distanceid = distanceid;
	}

	public Float getBegindistance() {
		return begindistance;
	}

	public void setBegindistance(Float begindistance) {
		this.begindistance = begindistance;
	}

	public Float getEnddistance() {
		return enddistance;
	}

	public void setEnddistance(Float enddistance) {
		this.enddistance = enddistance;
	}

	public Date getEndtime() {
		return endtime;
	}

	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}

	public Float getFirstexceedvolume() {
		return firstexceedvolume;
	}

	public void setFirstexceedvolume(Float firstexceedvolume) {
		this.firstexceedvolume = firstexceedvolume;
	}

	public Float getFirstowevolume() {
		return firstowevolume;
	}

	public void setFirstowevolume(Float firstowevolume) {
		this.firstowevolume = firstowevolume;
	}

	public Float getSecondexceedvolume() {
		return secondexceedvolume;
	}

	public void setSecondexceedvolume(Float secondexceedvolume) {
		this.secondexceedvolume = secondexceedvolume;
	}

	public Float getSecondowevolume() {
		return secondowevolume;
	}

	public void setSecondowevolume(Float secondowevolume) {
		this.secondowevolume = secondowevolume;
	}

	public Float getThirdexceedvolume() {
		return thirdexceedvolume;
	}

	public void setThirdexceedvolume(Float thirdexceedvolume) {
		this.thirdexceedvolume = thirdexceedvolume;
	}

	public Float getThirdowevolume() {
		return thirdowevolume;
	}

	public void setThirdowevolume(Float thirdowevolume) {
		this.thirdowevolume = thirdowevolume;
	}

	public Float getFourthexceedvolume() {
		return fourthexceedvolume;
	}

	public void setFourthexceedvolume(Float fourthexceedvolume) {
		this.fourthexceedvolume = fourthexceedvolume;
	}

	public Float getFourthowevolume() {
		return fourthowevolume;
	}

	public void setFourthowevolume(Float fourthowevolume) {
		this.fourthowevolume = fourthowevolume;
	}

	public String getGroupid() {
		return groupid;
	}
	
	

	public Integer getState() {
		return state;
	}

	public void setState(Integer state) {
		this.state = state;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public Date getBegintime() {
		return begintime;
	}

	public void setBegintime(Date begintime) {
		this.begintime = begintime;
	}

	public Integer getHandle_state() {
		return handle_state;
	}

	public void setHandle_state(Integer handle_state) {
		this.handle_state = handle_state;
	}

	public String getTenderid() {
		return tenderid;
	}

	public void setTenderid(String tenderid) {
		this.tenderid = tenderid;
	}

	
	
}
