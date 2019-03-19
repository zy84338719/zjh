package com.zttx.vo;

import java.util.Date;

import com.zttx.po.User;
/**
 * UserQueryVo类
 * 用于封装 对 Topic表 的查询条件
 * @author liyuan
 *
 */
public class UserQueryVo {
	
	private Integer curPage;//当前页码
	
	private Integer startIndex;// 起始位置
	
	private Integer count;//每次查询条数
	
	private User user;//封装部分查询条件
	
	//用来做按照用户类型查询条件
	private String ukindValue;//查询条件一：用户类型,值为：all/individual/enterprise/intermediary
	
	//用来做简单查询条件
	private String key;//简单查询条件，用户ID或者用户名
	
	//开始时间
	private Date createtime_start;
	
	private Date time1;
	private Date time2;
	
	
	
	//班组ID
	private String groupid;
	
	
	//结束时间
	private Date createtime_end;
	
	private String role;	//角色
	private String groupname; //班组名称
	
	public Date getTime1() {
		return time1;
	}

	public void setTime1(Date time1) {
		this.time1 = time1;
	}

	public Date getTime2() {
		return time2;
	}

	public void setTime2(Date time2) {
		this.time2 = time2;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getGroupid() {
		return groupid;
	}

	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}

	public String getUkindValue() {
		return ukindValue;
	}

	public void setUkindValue(String ukindValue) {
		this.ukindValue = ukindValue;
	}

	public Integer getCurPage() {
		return curPage;
	}

	public void setCurPage(Integer curPage) {
		this.curPage = curPage;
	}

	public Integer getStartIndex() {
		return startIndex;
	}

	public void setStartIndex(Integer startIndex) {
		this.startIndex = startIndex;
	}

	public Integer getCount() {
		return count;
	}

	public void setCount(Integer count) {
		this.count = count;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	public Date getCreatetime_start() {
		return createtime_start;
	}

	public void setCreatetime_start(Date createtime_start) {
		this.createtime_start = createtime_start;
	}

	public Date getCreatetime_end() {
		return createtime_end;
	}

	public void setCreatetime_end(Date createtime_end) {
		this.createtime_end = createtime_end;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getGroupname() {
		return groupname;
	}

	public void setGroupname(String groupname) {
		this.groupname = groupname;
	}

}
