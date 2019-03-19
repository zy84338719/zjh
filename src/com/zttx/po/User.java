package com.zttx.po;


/**
 * User类
 * @author liyuan
 *
 */
public class User {
	private String userid;//用户ID
	private String loginname;//登录名
	private String password;//登录密码
	private String username;//用户名
	private String telphone;//手机号
	private String role;//所属角色:测量人员，班组人员，领导，admin
	private String groupid;//所属组ID
	private String secondauthorityid;//二级权限ID
	//2017-11-29
	private Float score;//查询TaskUser表中的得分信息
	//2017-12-05
	private Float totalscore;//任务总分数
	private Integer state;	//任务完成状态
	private String dealresult; //任务完成结果
	private String _userid;//用户ID
	
	private Integer department;//所属标段
	
	
	
	public String getDealresult() {
		return dealresult;
	}
	public void setDealresult(String dealresult) {
		this.dealresult = dealresult;
	}
	public Integer getDepartment() {
		return department;
	}
	public void setDepartment(Integer department) {
		this.department = department;
	}
	public String get_userid() {
		return _userid;
	}
	public void set_userid(String _userid) {
		this._userid = _userid;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getLoginname() {
		return loginname;
	}
	public void setLoginname(String loginname) {
		this.loginname = loginname;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getTelphone() {
		return telphone;
	}
	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		this.role = role;
	}
	public String getGroupid() {
		return groupid;
	}
	public void setGroupid(String groupid) {
		this.groupid = groupid;
	}
	public String getSecondauthorityid() {
		return secondauthorityid;
	}
	public void setSecondauthorityid(String secondauthorityid) {
		this.secondauthorityid = secondauthorityid;
	}
	public Float getScore() {
		return score;
	}
	public void setScore(Float score) {
		this.score = score;
	}
	public Float getTotalscore() {
		return totalscore;
	}
	public void setTotalscore(Float totalscore) {
		this.totalscore = totalscore;
	}
	public Integer getState() {
		return state;
	}
	public void setState(Integer state) {
		this.state = state;
	}
	
	
	
}
