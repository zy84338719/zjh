package com.zttx.po;

import java.util.Date;

import com.zttx.po.Distance;
/**
 * 各里程段各班组的超欠挖量统计
 * 2017年10月7日
 * @author ZackLee
 *
 */
public class DistanceGroupVolumeCustom extends Distance{
	
	private String groupname; //群组名称
	private String code;	//群组代码
	private Date oldmeasuretime; //测量时间
	private Date newmeasuretime; //测量时间
	private String fathergroupname;	//父类组名

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

	public Date getOldmeasuretime() {
		return oldmeasuretime;
	}

	public void setOldmeasuretime(Date oldmeasuretime) {
		this.oldmeasuretime = oldmeasuretime;
	}

	public Date getNewmeasuretime() {
		return newmeasuretime;
	}

	public void setNewmeasuretime(Date newmeasuretime) {
		this.newmeasuretime = newmeasuretime;
	}

	public String getFathergroupname() {
		return fathergroupname;
	}

	public void setFathergroupname(String fathergroupname) {
		this.fathergroupname = fathergroupname;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
	}

}
