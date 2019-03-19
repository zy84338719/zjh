package com.zttx.utils;

import java.util.Date;
import java.util.List;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.zttx.mapper.TaskMapper;
import com.zttx.po.TaskCustom;

/**
 * 活动线程：每隔一定时间查询活动是否已经结束
 * @author Administrator
 *
 */
public class ActivityThread extends Thread
{	

	
	 ApplicationContext context = new ClassPathXmlApplicationContext("spring/applicationContext-dao.xml"); 
	 TaskMapper taskMapper = (TaskMapper) context.getBean("taskMapper");
	 private long interval = 24*60*60*1000;//24小时
	
	public void run()
    {
        while (true)
        {
        	//获取所有清单信息
        	List<TaskCustom> TaskList = taskMapper.queryAllTask();
        	for(TaskCustom task:TaskList){
        		//查询每个清单的完成情况
        		
        	}
        	try {
				Thread.sleep(interval);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
        }
    }

}
