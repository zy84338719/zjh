package com.zttx.po;

import java.util.Date;

/**
 * 截面
 * @author liyuan
 * 2017年10月5日
 */
public class Section {

	private String sectionid; //截面ID
	private Float distance;//推进历程，单位：米
	private Float actualarea;//实测面积
	private Float referarea;//参考面积
	private Float exceedarea;//超挖面积
	private Float owearea;//欠挖面积
	private Float refervolume;//参考土方量
	private Float exceedvolume;//超挖土方量
	private Float owevolume;//欠挖土方量
	private String groupid;//负责班组ID
	
	private String measureid;//测量人员ID
	private Date buildtime;//施工时间
	private Date measuretime;//测量时间
	
	private Integer stage;//施工阶段
	//2017-10-22
	private String tenderid;	//标段ID

	//add by liyuan on 2017-12-12
	private Boolean isDeleted;
	private Date createtime;
	
	private String _sectionid;
	
	
	
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

	public String get_sectionid() {
		return _sectionid;
	}

	public void set_sectionid(String _sectionid) {
		this._sectionid = _sectionid;
	}

	public String getSectionid() {
		return sectionid;
	}

	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}

	public Float getDistance() {
		return distance;
	}

	public void setDistance(Float distance) {
		this.distance = distance;
	}

	public Float getActualarea() {
		return actualarea;
	}

	public void setActualarea(Float actualarea) {
		this.actualarea = actualarea;
	}

	public Float getReferarea() {
		return referarea;
	}

	public void setReferarea(Float referarea) {
		this.referarea = referarea;
	}

	public Float getExceedarea() {
		return exceedarea;
	}

	public void setExceedarea(Float exceedarea) {
		this.exceedarea = exceedarea;
	}

	public Float getOwearea() {
		return owearea;
	}

	public void setOwearea(Float owearea) {
		this.owearea = owearea;
	}

	public Float getRefervolume() {
		return refervolume;
	}

	public void setRefervolume(Float refervolume) {
		this.refervolume = refervolume;
	}

	public Float getExceedvolume() {
		return exceedvolume;
	}

	public void setExceedvolume(Float exceedvolume) {
		this.exceedvolume = exceedvolume;
	}

	public Float getOwevolume() {
		return owevolume;
	}

	public void setOwevolume(Float owevolume) {
		this.owevolume = owevolume;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getMeasureid() {
		return measureid;
	}

	public void setMeasureid(String measureid) {
		this.measureid = measureid;
	}

	public Date getBuildtime() {
		return buildtime;
	}

	public void setBuildtime(Date buildtime) {
		this.buildtime = buildtime;
	}

	public Date getMeasuretime() {
		return measuretime;
	}

	public void setMeasuretime(Date measuretime) {
		this.measuretime = measuretime;
	}

	public Integer getStage() {
		return stage;
	}

	public void setStage(Integer stage) {
		this.stage = stage;
	}

	public String getTenderid() {
		return tenderid;
	}

	public void setTenderid(String tenderid) {
		this.tenderid = tenderid;
	}
	
	
}
