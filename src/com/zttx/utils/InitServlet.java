package com.zttx.utils;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class InitServlet extends HttpServlet {
	
	
	 private static final long serialVersionUID = 1L;

	    public void init() throws ServletException
	    {	
	    	
	    	 System.out.println("服务启动");
	         // 启动定时验证活动是否过期的线程
	         new ActivityThread().start();
	    }
	    
	    

}

