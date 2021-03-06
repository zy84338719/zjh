package com.zttx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 
 * 文件作用：http接口使用实例
 * 
 * 创建时间：2009-06-03
 * 
 * 
 * 100 发送成功 101 验证失败 102 短信不足 103 操作失败 104 非法字符 105 内容过多 106 号码过多 107 频率过快 108
 * 号码内容空 109 账号冻结 110 禁止频繁单条发送 111 系统暂定发送 112 有错误号码 113 定时时间不对 114 账号被锁，10分钟后登录
 * 115 连接失败 116 禁止接口发送 117 绑定IP不正确 120 系统升级
 */

public class yunsms {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// 发送内容
		String content = "你有一项新工作“"+"标题"+"”需要处理，发布人："+"李袁"+"，登录地址:http://www.crtg-3.com:8081/zjh_pages/login.html";
		System.out.println(content);
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://http.yunsms.cn/tx/?");

		// 向StringBuffer追加用户名
		sb.append("uid=20011160");

		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&pwd=6C7480F8E40A5333B9C4866EBEB8A462");
		// 向StringBuffer追加手机号码
		sb.append("&mobile=18260631810");

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=" + URLEncoder.encode(content));
		// System.out.println(sb.toString());
		// 创建url对象
		URL url = new URL(sb.toString());
		System.out.println(sb.toString());
		// 打开url连接
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// 设置url请求方式 ‘get’ 或者 ‘post’
		connection.setRequestMethod("POST");

		// 发送
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		// 返回发送结果
		String inputline = in.readLine();

		// 返回结果为‘100’ 发送成功
		System.out.println(inputline);
	}
	

}
