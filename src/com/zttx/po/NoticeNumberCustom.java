package com.zttx.po;
/**
 * 首页各模块消息分类统计数量
 * 2017年10月17日
 * @author ZackLee
 *
 */
public class NoticeNumberCustom {

	private Integer threeD_number;	//3D模块消息数量
	private Integer quality_number;	//质量追溯消息数量
	private Integer task_number;	//任务清单消息数量
	
	public Integer getThreeD_number() {
		return threeD_number;
	}
	public void setThreeD_number(Integer threeD_number) {
		this.threeD_number = threeD_number;
	}
	public Integer getQuality_number() {
		return quality_number;
	}
	public void setQuality_number(Integer quality_number) {
		this.quality_number = quality_number;
	}
	public Integer getTask_number() {
		return task_number;
	}
	public void setTask_number(Integer task_number) {
		this.task_number = task_number;
	}
}
