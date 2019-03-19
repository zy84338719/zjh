package com.zttx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttx.po.Task;
import com.zttx.po.TaskCustom;
import com.zttx.po.Urge;
import com.zttx.po.UrgeCustom;
import com.zttx.po.User;
import com.zttx.service.TaskService;
import com.zttx.service.UrgeService;
import com.zttx.service.UserService;
import com.zttx.utils.DateFormatUtils;
import com.zttx.utils.SMSUtil;
import com.zttx.vo.Message;


/**
 * 催促Controller
 *
 */
@Controller
@RequestMapping("/urge")
public class UrgeController {
	
	@Autowired
	private UrgeService urgeService; 

	@Autowired
	private TaskService taskService;
	
	@Autowired
	private UserService userService;
	
	//保存催促消息
	@RequestMapping("/saveUrge")
	public @ResponseBody Message saveUrge(@RequestBody Task task) throws Exception{
		Message msg = new Message();
		List<String> sms_senders = new ArrayList<String>();
		
		//查询该任务的所有接受者
		List<User> userList = taskService.findTaskDetailUser(task);
		boolean flag = false;
		//给未完成的接收者推送催促消息
		for(User user:userList){
			if(user.getState() != 1){
				//创建Urge
				Urge urge = new Urge();
				urge.setTaskid(task.getTaskid());
				urge.setReceiver(user.getUserid());
				urge.setSender(task.getCreater());
				urge.setUpdatetime(DateFormatUtils.format1(new Date()));
				
				//首先判断该用户已经接受到过催促
				Integer count = urgeService.getCount(urge);
				if(count == 0){
					urgeService.save(urge);
					sms_senders.add(task.getCreater());
					flag = true;
				}
				
			}
		}
		if(flag){
			msg.setMsg("催促成功");
			msg.setCode(100);
			
			//补充：发送通知短信
//			User query = new User();
//			query.setUserid(task.getCreater());
//			User sender = userService.findUser(query);
//			
//			TaskCustom taskCustom = taskService.findTaskDetail(task);
//			String sms_content = "你有一项未完成工作催促通知，请今天务必完成“"+taskCustom.getTitle()+"”，发布人："+sender.getUsername()+"，登录地址：http://www.crtg-3.com:8081/zjh_pages/login.html";
//			String[] sms_mobiles = new String[100];
//			for(int i=0;i<sms_senders.size();i++){
//				sms_mobiles[i] = sms_senders.get(i);
//			}
//			SMSUtil.sendSms(sms_content, sms_mobiles);
		
		}else{
			msg.setMsg("您已经催促过了");
			msg.setCode(101);
		}
		
		return msg;
	}
		
	//查询我接收到的催促消息
	@RequestMapping("/queryUrgeList")
	public @ResponseBody Message queryUrgeList(@RequestBody User user) throws Exception{
		Message msg = new Message();
		
		List<UrgeCustom> urgeList = urgeService.queryMyReceived(user);
		Integer count = 0;
		if(urgeList != null && urgeList.size() > 0){
			count = urgeList.size();
		}
		msg.setCode(100);
		msg.setObj(urgeList);
		msg.setMsg("您有"+count+"条催促消息");
		return msg;
	}
	
	//删除某条催促消息
		@RequestMapping("/deleteUrge")
		public @ResponseBody Message deleteUrge(@RequestBody Urge urge) throws Exception{
			Message msg = new Message();
			
			Integer result = urgeService.deleteUrge(urge);
			if(result <= 0){
				msg.setCode(101);
				msg.setMsg("操作失败！");
				return msg;
			}
			msg.setCode(100);
			return msg;
		}


	//催促某个人某个任务
	@RequestMapping("/saveOne")
	@ResponseBody public Message saveOne(@RequestBody Urge urge) throws Exception{
		Message msg = new Message();
		
		urge.setUpdatetime(DateFormatUtils.format1(new Date()));
		
		//首先判断该用户已经接受到过催促
		Integer count = urgeService.getCount(urge);
		if(count == 0){
			urgeService.save(urge);
			
//			User query = new User();
//			query.setUserid(urge.getSender());
//			User sender = userService.findUser(query);
			
//			Task task = new Task();
//			task.setTaskid(urge.getTaskid());
//			TaskCustom taskCustom = taskService.findTaskDetail(task);
//			String sms_content = "你有一项未完成工作催促通知，请今天务必完成“"+taskCustom.getTitle()+"”，发布人："+sender.getUsername()+"，登录地址：http://www.crtg-3.com:8081/zjh_pages/login.html";
//			String[] sms_mobiles = new String[1];
//			for(int i=0;i<sms_senders.size();i++){
//				sms_mobiles[i] = sms_senders.get(i);
//			}
//			sms_mobiles[0] = 
//			SMSUtil.sendSms(sms_content, sms_mobiles);
			
			msg.setCode(100);
			msg.setMsg("催促成功");
			return msg;
	
		}else{
			msg.setCode(101);
			msg.setMsg("您已经催促过了");
			return msg;
		}
	}
}
