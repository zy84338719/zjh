package com.zttx.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

/**
 * 
 * �ļ����ã�http�ӿ�ʹ��ʵ��
 * 
 * ����ʱ�䣺2009-06-03
 * 
 * 
 * 100 ���ͳɹ� 101 ��֤ʧ�� 102 ���Ų��� 103 ����ʧ�� 104 �Ƿ��ַ� 105 ���ݹ��� 106 ������� 107 Ƶ�ʹ��� 108
 * �������ݿ� 109 �˺Ŷ��� 110 ��ֹƵ���������� 111 ϵͳ�ݶ����� 112 �д������ 113 ��ʱʱ�䲻�� 114 �˺ű�����10���Ӻ��¼
 * 115 ����ʧ�� 116 ��ֹ�ӿڷ��� 117 ��IP����ȷ 120 ϵͳ����
 */

public class yunsms {

	/**
	 * @param args
	 * @throws IOException
	 */
	public static void main(String[] args) throws IOException {
		// ��������
		String content = "����һ���¹�����"+"����"+"����Ҫ���������ˣ�"+"��Ԭ"+"����¼��ַ:http://www.crtg-3.com:8081/zjh_pages/login.html";
		System.out.println(content);
		// ����StringBuffer�������������ַ���
		StringBuffer sb = new StringBuffer("http://http.yunsms.cn/tx/?");

		// ��StringBuffer׷���û���
		sb.append("uid=20011160");

		// ��StringBuffer׷�����루�������MD5 32λ Сд��
		sb.append("&pwd=6C7480F8E40A5333B9C4866EBEB8A462");
		// ��StringBuffer׷���ֻ�����
		sb.append("&mobile=18260631810");

		// ��StringBuffer׷����Ϣ����תURL��׼��
		sb.append("&content=" + URLEncoder.encode(content));
		// System.out.println(sb.toString());
		// ����url����
		URL url = new URL(sb.toString());
		System.out.println(sb.toString());
		// ��url����
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		// ����url����ʽ ��get�� ���� ��post��
		connection.setRequestMethod("POST");

		// ����
		BufferedReader in = new BufferedReader(new InputStreamReader(
				url.openStream()));

		// ���ط��ͽ��
		String inputline = in.readLine();

		// ���ؽ��Ϊ��100�� ���ͳɹ�
		System.out.println(inputline);
	}
	

}
