package com.zttx.po;
/**
 * 设置表
 * @author liyuan
 * 2017年10月5日
 */
public class Set {
	private String setid;//ID
	private Float exceedval; //超娃警戒值
	
	private Float exceedvolume;//混凝土方量上限值
	private Float deceedvolume;//混凝土方量下限值
	private int exceedtime;//超时未处理时限

	public String getSetid() {
		return setid;
	}

	public void setSetid(String setid) {
		this.setid = setid;
	}

	public Float getExceedval() {
		return exceedval;
	}

	public void setExceedval(Float exceedval) {
		this.exceedval = exceedval;
	}

	public Float getExceedvolume() {
		return exceedvolume;
	}

	public void setExceedvolume(Float exceedvolume) {
		this.exceedvolume = exceedvolume;
	}

	public Float getDeceedvolume() {
		return deceedvolume;
	}

	public void setDeceedvolume(Float deceedvolume) {
		this.deceedvolume = deceedvolume;
	}

	public int getExceedtime() {
		return exceedtime;
	}

	public void setExceedtime(int exceedtime) {
		this.exceedtime = exceedtime;
	}
	
	
}
