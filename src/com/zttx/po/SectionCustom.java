package com.zttx.po;

import java.util.List;

import com.zttx.po.Point;
import com.zttx.po.Section;
/**
 * “Section”截面自定义实体，包含“截面”、“班组”、“点”信息
 * 2017年10月5日
 * @author ZackLee
 *
 */
public class SectionCustom extends Section{
	
	private String measurename;
	private String groupname;
	private String tendername;	//标段名称
	//2017-10-29
	private Float start_distance;
	private Float end_distance;
	
	private List<Point> point;
	
	public List<Point> getPoint() {
		return point;
	}
	public void setPoint(List<Point> point) {
		this.point = point;
	}
	
	public String getGroupname() {
		return groupname;
	}
	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}
	public String getMeasurename() {
		return measurename;
	}
	public void setMeasurename(String measurename) {
		this.measurename = measurename;
	}
	public String getTendername() {
		return tendername;
	}
	public void setTendername(String tendername) {
		this.tendername = tendername;
	}
	public Float getStart_distance() {
		return start_distance;
	}
	public void setStart_distance(Float start_distance) {
		this.start_distance = start_distance;
	}
	public Float getEnd_distance() {
		return end_distance;
	}
	public void setEnd_distance(Float end_distance) {
		this.end_distance = end_distance;
	}
	

}
