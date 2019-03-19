package com.zttx.vo;
/**
 * Message类
 * @author liyuan
 *	用于所有ajax请求返回的对象（json格式）
 */
public class Message {

	private Integer code;  //状态码
	private String msg;    //具体信息
	
	private String back_url; //跳转路径
	
	private Object obj;//数据对象，可以是单个对象，也可以是一个集合
	
	public Object getObj() {
		return obj;
	}
	public void setObj(Object obj) {
		this.obj = obj;
	}
	public String getBack_url() {
		return back_url;
	}
	public void setBack_url(String back_url) {
		this.back_url = back_url;
	}
	public Integer getCode() {
		return code;
	}
	public void setCode(Integer code) {
		this.code = code;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	
	
	
}
