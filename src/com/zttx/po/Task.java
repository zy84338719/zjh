package com.zttx.po;

import java.util.Date;
/**
 * 任务清单
 * 2017年10月15日
 * @author ZackLee
 *
 */
public class Task {
	
	private String taskid;	//任务清单ID
	private String title;	//任务标题
	private String content;	//任务内容
	private Date createtime;	//任务创建时间
	private Date deadline;	//截止时间
	private String note;	//任务备注
	private String createrid;	//任务创建人ID
	private Date starttime;	//任务开始时间
	//2017-10-23
	private String imgurl1;	//图片路径
	private String imgurl2;	//图片路径
	private String imgurl3;	//图片路径
	private String imgurl4;	//图片路径
	private String imgurl5;	//图片路径
	//2017-11-29
	private Integer level;	//任务等级，1,2,3
	//2017-12-06
	private String creater;	//创建人名
	
	public String getTaskid() {
		return taskid;
	}
	public void setTaskid(String taskid) {
		this.taskid = taskid;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public Date getCreatetime() {
		return createtime;
	}
	public void setCreatetime(Date createtime) {
		this.createtime = createtime;
	}
	public Date getDeadline() {
		return deadline;
	}
	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	public String getCreaterid() {
		return createrid;
	}
	public void setCreaterid(String createrid) {
		this.createrid = createrid;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public String getImgurl1() {
		return imgurl1;
	}
	public void setImgurl1(String imgurl1) {
		this.imgurl1 = imgurl1;
	}
	public String getImgurl2() {
		return imgurl2;
	}
	public void setImgurl2(String imgurl2) {
		this.imgurl2 = imgurl2;
	}
	public String getImgurl3() {
		return imgurl3;
	}
	public void setImgurl3(String imgurl3) {
		this.imgurl3 = imgurl3;
	}
	public String getImgurl4() {
		return imgurl4;
	}
	public void setImgurl4(String imgurl4) {
		this.imgurl4 = imgurl4;
	}
	public String getImgurl5() {
		return imgurl5;
	}
	public void setImgurl5(String imgurl5) {
		this.imgurl5 = imgurl5;
	}
	public Integer getLevel() {
		return level;
	}
	public void setLevel(Integer level) {
		this.level = level;
	}
	public String getCreater() {
		return creater;
	}
	public void setCreater(String creater) {
		this.creater = creater;
	}

}
