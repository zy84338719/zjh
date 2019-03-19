package com.zttx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class SMSUtil {
	/**
	 * @param args
	 * @throws IOException
	 */
	public static void sendSms(String content,String[] mobiles) throws IOException {
		// 发送内容
		System.out.println(content);
		// 创建StringBuffer对象用来操作字符串
		StringBuffer sb = new StringBuffer("http://http.yunsms.cn/tx/?");

		// 向StringBuffer追加用户名
		sb.append("uid=20011160");

		// 向StringBuffer追加密码（密码采用MD5 32位 小写）
		sb.append("&pwd=6C7480F8E40A5333B9C4866EBEB8A462");
		// 向StringBuffer追加手机号码
		sb.append("&mobile=" + mobiles[0]);
		
		if(mobiles.length > 1){
			for(int i=1;i<mobiles.length;i++){
				if(mobiles[i] != null && !mobiles[i].equals("")){
					sb.append(","+mobiles[i]);
				}
			}
		}

		// 向StringBuffer追加消息内容转URL标准码
		sb.append("&content=" + URLEncoder.encode(content,"GBK"));
		// System.out.println(sb.toString());
		// 创建url对象
		URL url = new URL(sb.toString());
		System.out.println("发送内容："+sb.toString());
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

	public static void main(String args[]){
		try {
			sendSms("标题李袁", new String[]{"18260631810"});
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
