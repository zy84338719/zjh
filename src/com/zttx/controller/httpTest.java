package com.zttx.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONObject;

import com.zttx.utils.HttpUtil;

@WebServlet("/getJson")
public class httpTest extends HttpServlet {

public void doGet(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		
		try {
			String version = new String(request.getParameter("version").getBytes("iso8859-1"),"utf-8");
			String method = new String(request.getParameter("method").getBytes("iso8859-1"),"utf-8");
			String vid = new String(request.getParameter("vid").getBytes("iso8859-1"),"utf-8");
			String vKey = new String(request.getParameter("vKey").getBytes("iso8859-1"),"utf-8");
			System.out.println("version的值为：" + version);
			
			String url = "http://60.195.248.67:89/gpsonline/GPSAPI?version=" + version + "&method=" + method + "&vid="
					+ vid + "&vKey=" + vKey;
			System.out.println("url为" + url);
			String result = HttpUtil.getInstance().httpGet(null,
					url);
			System.out.println("result====="+result);
			
			JSONObject jsStr = JSONObject.fromObject(result);
			System.out.println(jsStr);
			responseOutWithJson(response, jsStr);
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
		doGet(request, response);
	}

	protected void responseOutWithJson(HttpServletResponse response, Object responseObject) {
		// 将实体对象转换为JSON Object转换
		JSONObject responseJSONObject = JSONObject.fromObject(responseObject);
		response.setCharacterEncoding("UTF-8");
		response.setContentType("application/json; charset=utf-8");
		PrintWriter out = null;
		try {
			out = response.getWriter();
			out.append(responseJSONObject.toString());
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (out != null) {
				out.close();
			}
		}
	}

}
