package com.zttx.po;

import java.util.Date;

/**
 * 截面点
 * @author liyuan
 * 2017年10月5日
 */
public class Point {
	private String pointid;  //ID
	private Float pointx;	//点横坐标
	private Float pointy;  //点纵坐标
	private String sectionid;  //截面UUID
	private Date scantime;  //扫描时间
	private Float offset;	//偏差值
	
	private Integer type; //0-正常点,1-超挖点,2-欠挖点
	
	private String _pointid;
	
	

	public String get_pointid() {
		return _pointid;
	}

	public void set_pointid(String _pointid) {
		this._pointid = _pointid;
	}

	public String getPointid() {
		return pointid;
	}

	public void setPointid(String pointid) {
		this.pointid = pointid;
	}

	public Float getPointx() {
		return pointx;
	}

	public void setPointx(Float pointx) {
		this.pointx = pointx;
	}

	public Float getPointy() {
		return pointy;
	}

	public void setPointy(Float pointy) {
		this.pointy = pointy;
	}

	public String getSectionid() {
		return sectionid;
	}

	public void setSectionid(String sectionid) {
		this.sectionid = sectionid;
	}

	public Date getScantime() {
		return scantime;
	}

	public void setScantime(Date scantime) {
		this.scantime = scantime;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Float getOffset() {
		return offset;
	}

	public void setOffset(Float offset) {
		this.offset = offset;
	}
	
	
}
