package com.zttx.utils;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

public class HttpUtil {
	private HttpUtil() {
		
	}
	
	private static final HttpUtil httpUtil = new HttpUtil();
	
	public static HttpUtil getInstance(){
		return httpUtil;
	}
	
	public String httpGet(HashMap<String, String> params, String urlStr) throws UnsupportedEncodingException{
		StringBuffer result = new StringBuffer();
		BufferedReader reader = null;
		try {
			URL url = new URL(getUrl(params, urlStr));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置通用的请求属性
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("DTN", "1");
            connection.setRequestProperty("User-agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.9.2.1000 Chrome/39.0.2146.0 Safari/537.36");
            connection.setConnectTimeout(120000);
            connection.setReadTimeout(120000);
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
		} catch (Exception e) {
			e.printStackTrace();
	        
		}
		// 使用finally块来关闭输入流
        finally {
            try {
                if (reader != null) {
                	reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return result.toString();
	}
	
	
	public String httpGet(HashMap<String, String> params, String urlStr,String Referer) throws UnsupportedEncodingException{
		StringBuffer result = new StringBuffer();
		BufferedReader reader = null;
		try {
			URL url = new URL(getUrl(params, urlStr));
			HttpURLConnection connection = (HttpURLConnection) url.openConnection();
			// 设置通用的请求属性
            connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
            connection.setRequestProperty("Connection", "Keep-Alive");
            connection.setRequestProperty("DTN", "1");
            connection.setRequestProperty("Referer", Referer);
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setRequestProperty("User-agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.9.2.1000 Chrome/39.0.2146.0 Safari/537.36");
            connection.setConnectTimeout(120000);
            connection.setReadTimeout(120000);
            connection.connect();

            reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream(),"utf-8"));
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
		} catch (Exception e) {
			e.printStackTrace();
		}
		// 使用finally块来关闭输入流
        finally {
            try {
                if (reader != null) {
                	reader.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
		return result.toString();
	}
	/********************************httpPost*************************************************/
	
	public String httpPost(String string, HashMap<String, String> params, String Referer) {
		// TODO 自动生成的方法存根
		StringBuffer sb = new StringBuffer();
		try {
            //创建连接
            URL url = new URL(Referer);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(120000);
            connection.setReadTimeout(120000);
            // head
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.setRequestProperty("Referer", Referer);
            connection.setRequestProperty("Host", "wx.meituan.com");
            connection.setRequestProperty("Origin", "http://wx.meituan.com");
            connection.setRequestProperty("X-Requested-With", "XMLHttpRequest");
            connection.setRequestProperty("User-agent",
                    "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Maxthon/4.9.2.1000 Chrome/39.0.2146.0 Safari/537.36");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.write(getBodyParams(params).getBytes("utf-8")); // 需指定编码格式，否则中文无法发送
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return sb.toString();
	}
	
	public String httpPost(String urlStr, HashMap<String, String> params){
		StringBuffer sb = new StringBuffer();
		try {
            //创建连接
            URL url = new URL(urlStr);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setDoOutput(true);
            connection.setDoInput(true);
            connection.setRequestMethod("POST");
            connection.setUseCaches(false);
            connection.setInstanceFollowRedirects(true);
            connection.setConnectTimeout(120000);
            connection.setReadTimeout(120000);
            // head
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded; charset=utf-8");
            connection.connect();
            //POST请求
            DataOutputStream out = new DataOutputStream(
                    connection.getOutputStream());
            out.write(getBodyParams(params).getBytes("utf-8")); // 需指定编码格式，否则中文无法发送
            out.flush();
            out.close();

            //读取响应
            BufferedReader reader = new BufferedReader(new InputStreamReader(
                    connection.getInputStream()));
            String lines;
            while ((lines = reader.readLine()) != null) {
                lines = new String(lines.getBytes(), "utf-8");
                sb.append(lines);
            }
            reader.close();
            // 断开连接
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
		
		return sb.toString();
	}
	
	/**
	 * 拼接POST请求参数
	 * @param params
	 * @return
	 */
	private String getBodyParams(HashMap<String, String> params) {
		StringBuffer sb = new StringBuffer();
		// 添加url参数
		if (params != null && params.size() > 0) {
			try {
				Iterator<Entry<String, String>> entryKeyIterator  = params.entrySet().iterator();
				while (entryKeyIterator.hasNext()) {
					Entry<String, String> entry = entryKeyIterator.next();
					String key = entry.getKey();
					String value = entry.getValue();
					sb.append(URLEncoder.encode(key, "UTF-8"));
					sb.append("=");
					sb.append(URLEncoder.encode(value, "UTF-8"));
					sb.append("&");
				}
				int position = sb.lastIndexOf("&");
				sb.replace(position, position + 1, "");
			} catch (Exception e) {
				return sb.toString();
			}
		}
		return sb.toString();
	}
	
	/**
	 * 拼接GET请求参数
	 * @param params
	 * @param url
	 * @return
	 */
	private String getUrl(Map<String, String> params, String url) {
		// 添加url参数
		if (params != null) {
			Iterator<String> it = params.keySet().iterator();
			StringBuffer sb = null;
			while (it.hasNext()) {
				String key = it.next();
				String value = params.get(key).toString();
				if (sb == null) {
					sb = new StringBuffer();
					sb.append("?");
				} else {
					sb.append("&");
				}
				try {
					sb.append(URLEncoder.encode(key, "UTF-8"));
					sb.append("=");
					sb.append(URLEncoder.encode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
			url += sb.toString();
		}
		return url;
	}
}
