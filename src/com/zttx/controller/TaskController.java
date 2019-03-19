package com.zttx.controller;

import java.io.File;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.zttx.constants.Constants;
import com.zttx.po.Group;
import com.zttx.po.GroupCustom;
import com.zttx.po.Task;
import com.zttx.po.TaskCustom;
import com.zttx.po.TaskUser;
import com.zttx.po.TaskUserCustom;
import com.zttx.po.User;
import com.zttx.po.UserCustom;
import com.zttx.service.GroupService;
import com.zttx.service.TaskService;
import com.zttx.service.UserService;
import com.zttx.utils.DateFormatUtils;
import com.zttx.utils.GUIDSeqGenerator;
import com.zttx.vo.Message;
import com.zttx.vo.TaskQueryVo;
import com.zttx.vo.UserQueryVo;

@Controller
@RequestMapping("/task")
public class TaskController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private TaskService taskService;
	
	/**
	 * 条件查询用户分组信息
	 * @param taskQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUser")
	public @ResponseBody Message queryUser(@RequestBody TaskQueryVo taskQueryVo)throws Exception{
		Message msg = new Message();
		List<UserCustom> userCustom_list = userService.findUserByType(taskQueryVo);
		if(userCustom_list.size() > 0){
			msg.setCode(100);
			msg.setMsg("获取用户分组数据成功！");
			msg.setObj(userCustom_list);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无用户分组数据！");
		}
		return msg;
	}
	
	/**
	 * 根据分组查询用户信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryUserGroup")
	public @ResponseBody Message queryUserGroup(@RequestBody UserQueryVo userQueryVo)throws Exception{
		Message msg = new Message();
		System.out.println(userQueryVo.getTime1());
		System.out.println(userQueryVo.getTime2());
		//查询所有班组
		List<GroupCustom> groupCustomList = groupService.queryAllGroup();
		if(groupCustomList.size()>0){
			
			for(int i=0; i<groupCustomList.size(); i++){
				String code_l = groupCustomList.get(i).getCode();
				String code = null;
				if(code_l.length()>14){
					code = groupCustomList.get(i).getCode().substring(0, 11);
					Group g = groupService.findGroupByCode(code);
					if(g != null){
						groupCustomList.get(i).setFathergroupname(g.getGroupname());
					}
				}
				userQueryVo.setGroupid(groupCustomList.get(i).getGroupid());
				List<User> userList = userService.findUserScore(userQueryVo);
	//			List<User> userList = userService.findUserByGroupid(groupCustomList.get(i).getGroupid());
				if(userList != null && userList.size()>0 && userList.get(0) != null){
					groupCustomList.get(i).setUserList(userList);
				}else{
					groupCustomList.remove(i);
					i--;
				}
				
			}
			msg.setCode(100);
			msg.setMsg("用户分组获取成功！");
			msg.setObj(groupCustomList);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无用户分组信息！");
		}
		return msg;		
	}
	
	/**
	 * 保存任务信息，并循环插入用户任务表
	 * @param taskQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/save")
	public @ResponseBody Message save(@RequestBody TaskQueryVo taskQueryVo)throws Exception{
		Message msg = new Message();
		Task task = new Task();
		TaskUser taskUser = new TaskUser();
		
		String createrid = taskQueryVo.getCreaterid();
		String content = taskQueryVo.getContent();
		String title = taskQueryVo.getTitle();
		String note = taskQueryVo.getNote();
		Date deadline = taskQueryVo.getDeadline();
		Date starttime = taskQueryVo.getStarttime();
		Integer state = taskQueryVo.getState();
		String imgurl1 = taskQueryVo.getImgurl1();
		String imgurl2 = taskQueryVo.getImgurl2();
		String imgurl3 = taskQueryVo.getImgurl3();
		String userids = taskQueryVo.getUserids();
		Integer level = taskQueryVo.getLevel();
		//分割用户ID
		String[] str=userids.split("#");
		String taskid = GUIDSeqGenerator.getInstance().newGUID();	//生成任务ID，唯一标识
		task.setTaskid(taskid);
		task.setContent(content);
		task.setCreaterid(createrid);
		task.setNote(note);
		task.setTitle(title);
		task.setDeadline(deadline);
		task.setStarttime(starttime);
		task.setCreatetime(DateFormatUtils.format1(new Date()));	//生成任务创建时间
		task.setLevel(level);
		//2017-10-23
		task.setImgurl1(imgurl1);
		task.setImgurl2(imgurl2);
		task.setImgurl3(imgurl3);
		int result = taskService.saveTask(task);
		if(result == 1){
			msg.setCode(100);
			msg.setMsg("任务信息插入成功！");
		}
		taskUser.setTaskid(taskid);
		taskUser.setState(state);
		if(task.getLevel() == 1){
			taskUser.setScore(0.5f);
		}else
			if(task.getLevel() == 2){
				taskUser.setScore(1.0f);
			}else
				if(task.getLevel() == 3){
					taskUser.setScore(2.0f);
				}
		for(int i=1;i<str.length;i++){
			taskUser.setUserid(str[i]);
			int res = taskService.saveTaskUser(taskUser);
			if(res==0){
				msg.setCode(101);
				msg.setMsg("数据插入失败！");
				return msg;
			}
		}
		
		//补充：短信通知
//		User query = new User();
//		query.setUserid(createrid);
//		User sender = userService.findUser(query);
//		String sms_content = "你有一项新工作“"+title+"”需要处理，发布人："+sender.getUsername()+"，登录地址:http://www.crtg-3.com:8081/zjh_pages/login.html";
//		String[] sms_mobiles = new String[100];
//		
//		for(int i=1;i<str.length;i++){
//			query.setUserid(str[i]);
//			User user = userService.findUser(query);
//			sms_mobiles[i-1] = user.getTelphone();
//		}
//		SMSUtil.sendSms(sms_content, sms_mobiles);
		return msg;
	}
	
	/**
	 * 查询登录用户发布的任务信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTaskList")
	public @ResponseBody Message queryTaskList(@RequestBody User user)throws Exception{
		Message msg = new Message();
		List<TaskCustom> task_list = taskService.queryTaskList(user);
		if(task_list.size()>0){
			for(int i=0; i<task_list.size(); i++){
				/*List<TaskUserCustom> taskUser_list = taskService.queryTaskUserByTaskId(task_list.get(i).getTaskid());
				if(taskUser_list.size()>0){
					task_list.get(i).setTaskUserCustom_list(taskUser_list);
				}*/
				Task task = new Task();
				task.setTaskid(task_list.get(i).getTaskid());
				List<User> user_list = taskService.findTaskDetailUser(task);
				if(user_list.size()>0){
					task_list.get(i).setUser_list(user_list);
				}
			
				//查询任务完成情况
				Integer result = taskService.queryUnFinishedCount(task_list.get(i));
				if(result > 0){
					task_list.get(i).setState(0);
				}else{
					task_list.get(i).setState(1);
				}
				
			}
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(task_list);
		}else{
			msg.setCode(101);
			msg.setMsg("获取数据成功！");
		}
		return msg;
	}
	
	/**
	 * 查询登录用户收到的任务信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTaskUserList")
	public @ResponseBody Message queryTaskUserList(@RequestBody User user)throws Exception{
		Message msg = new Message();
		List<TaskUserCustom> task_list = taskService.queryTaskUserList(user);
		if(task_list.size()>0){
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(task_list);
		}else{
			msg.setCode(101);
			msg.setMsg("获取数据成功！");
		}
		return msg;
	}
	
	/**
	 * 查询任务详情信息
	 * @param task
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findTaskDetail")
	public @ResponseBody Message findTaskDetail(@RequestBody Task task)throws Exception{
		Message msg = new Message();
		TaskCustom taskCustom = taskService.findTaskDetail(task);
		if(taskCustom != null){
			List<User> user_list = taskService.findTaskDetailUser(task);
			if(user_list.size()>0){
				taskCustom.setUser_list(user_list);
			}
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(taskCustom);
		}else{
			msg.setCode(101);
			msg.setMsg("获取数据失败！");
		}
		return msg;
	}
	
	/**
	 * 用户任务反馈：提交处理结果，待审核
	 * @param taskUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/dealTask")
	public @ResponseBody Message dealTask(@RequestBody TaskUser taskUser)throws Exception{
		Message msg = new Message();
		Date dealtime = DateFormatUtils.format1(new Date());
		Task task = new Task();
		task.setTaskid(taskUser.getTaskid());
//		TaskCustom taskCustom = taskService.findTaskDetail(task);
//		if(taskCustom.getLevel() == 1){
//			taskUser.setScore((float) 1);
//		}else if(taskCustom.getLevel() == 2){
//			taskUser.setScore((float) 1.5);
//		}else if(taskCustom.getLevel() == 3){
//			taskUser.setScore((float) 2);
//		}else{
//			taskUser.setScore((float) 0);
//		}
		taskUser.setDealtime(dealtime);
		taskUser.setState(2);	//设置任务状态为2，表示“已提交，待审核”
		int result = taskService.dealTask(taskUser);
		if(result == 1){
			msg.setCode(100);
			msg.setMsg("任务反馈成功！");
		}else{
			msg.setCode(101);
			msg.setMsg("任务反馈失败！");
		}
		return msg;
	}
	
	//上传图片(包括步骤图片)
	@RequestMapping("/uploadImage")
	public @ResponseBody Message uploadImage(@RequestParam("file") MultipartFile attachFile,
			HttpServletRequest request, HttpServletResponse response, HttpSession session) {
				String originalFilename = attachFile.getOriginalFilename();
				String suffix = originalFilename.substring(originalFilename
						.lastIndexOf("."));
				
				String fileName = GUIDSeqGenerator.getInstance().newGUID() + suffix;
//				System.out.println(fileName);
				// 创建实际存储路径
				String filePath = session.getServletContext().getRealPath(Constants.imgPath) + "/";
				
				File targetFile = new File(filePath, fileName);
				
				if (!targetFile.exists()) {
					targetFile.mkdirs();
				}

				// 保存附件
				try {
					attachFile.transferTo(targetFile);
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				Message msg = new Message();
				msg.setObj(Constants.imgPath + fileName);
				msg.setCode(100);
				msg.setMsg("成功");
				return msg;
		}
	
	
	@RequestMapping("/recall")
	public @ResponseBody Message recall(@RequestBody Task task)throws Exception{
		Message msg = new Message();
		/*List<TaskUser> taskUserList = taskService.queryTaskUserByState(task);
		if(taskUserList.size()>0){	//根据taskID查看，已向用户发送的任务中有已完成的记录
			msg.setCode(101);
			msg.setMsg("已有人完成，无法撤回任务！");
		}else{	//尚未有人完成，则撤回（删除）信息，并删除相关的用户关联信息
			int result1 = taskService.deleteTask(task);
			int result2 = taskService.deleteTaskUser(task);
			msg.setCode(100);
			msg.setMsg("任务撤回成功！");
		}*/
		
		int result1 = taskService.deleteTask(task);
		int result2 = taskService.deleteTaskUser(task);
		msg.setCode(100);
		msg.setMsg("任务撤回成功！");
		return msg;
	}
	
	/**
	 * 修改任务
	 * @param task
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/modify")
	public @ResponseBody Message modify(@RequestBody Task task)throws Exception{
		Message msg = new Message();
		List<TaskUser> taskUserList = taskService.queryTaskUserByState(task);
		if(taskUserList.size()>0){
			msg.setCode(101);
			msg.setMsg("已有人完成，无法修改！");
		}else{
			task.setCreatetime(DateFormatUtils.format1(new Date()));
			int result = taskService.modify(task);
			TaskCustom taskNew = taskService.findTaskDetail(task);
			msg.setCode(100);
			msg.setMsg("任务信息修改成功！");
			msg.setObj(taskNew);
		}
		return msg;
	}
	
	/**
	 * 查询全部任务信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAllTask")
	public @ResponseBody Message queryAllTask() throws Exception{
		Message msg = new Message();
		List<TaskCustom> taskCustom_list = taskService.queryAllTask();
		if(taskCustom_list.size()>0){
			for(int i=0; i<taskCustom_list.size(); i++){
				/*List<TaskUserCustom> taskUser_list = taskService.queryTaskUserByTaskId(taskCustom_list.get(i).getTaskid());
				if(taskUser_list.size()>0){
					taskCustom_list.get(i).setTaskUserCustom_list(taskUser_list);
				}*/
				Task task = new Task();
				task.setTaskid(taskCustom_list.get(i).getTaskid());
				List<User> user_list = taskService.findTaskDetailUser(task);
				if(user_list.size()>0){
					taskCustom_list.get(i).setUser_list(user_list);
				}
			}
			msg.setCode(100);
			msg.setMsg("获取全部任务信息成功！");
			msg.setObj(taskCustom_list);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无任务信息！");
		}
		return msg;
	}
	
	/**
	 * 根据任务标题和时间区间模糊查询任务列表
	 * @param task
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTaskByTitle")
	public @ResponseBody Message queryTaskByTitle(@RequestBody TaskQueryVo taskQueryVo) throws Exception{
		Message msg = new Message();
		//时间校正
//		if(taskQueryVo.getTime1() != null){
//			Long time = (taskQueryVo.getTime1().getTime()) - 8*60*1000;
//			taskQueryVo.setTime1(new Date(time));
//		}
//		
//		if(taskQueryVo.getTime2() != null){
//			Long time = (taskQueryVo.getTime2().getTime()) - 8*60*1000;
//			taskQueryVo.setTime2(new Date(time));
//		}
//		
//		System.out.println(taskQueryVo.getTime1());
//		System.out.println(taskQueryVo.getTime2());
		
		List<TaskCustom> taskCustom_list = taskService.queryTaskByTitle(taskQueryVo);
		if(taskCustom_list.size()>0){
			for(int i=0; i<taskCustom_list.size(); i++){
				/*List<TaskUserCustom> taskUser_list = taskService.queryTaskUserByTaskId(taskCustom_list.get(i).getTaskid());
				if(taskUser_list.size()>0){
					taskCustom_list.get(i).setTaskUserCustom_list(taskUser_list);
				}*/
				Task task_new = new Task();
				task_new.setTaskid(taskCustom_list.get(i).getTaskid());
				List<User> user_list = taskService.findTaskDetailUser(task_new);
				if(user_list.size()>0){
					taskCustom_list.get(i).setUser_list(user_list);
				}
			}
			msg.setCode(100);
			msg.setMsg("获取全部任务信息成功！");
			msg.setObj(taskCustom_list);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无任务信息！");
		}
		return msg;
	}
	
	
	/**
	 * add by 李袁  on 2018-02-13
	 * 查询所有未完成的任务清单
	 */
	@RequestMapping("/queryAllUnfinishedTasks")
	public @ResponseBody Message queryAllUnfinishedTasks() throws Exception{
		Message msg = new Message();
		List<TaskCustom> task_list = taskService.queryAllUnfinishedTasks();
//		System.out.println(task_list.size()+"sssss");
		if(task_list.size()>0){
			for(int i=0; i<task_list.size(); i++){
				
				Task task = new Task();
				task.setTaskid(task_list.get(i).getTaskid());
				List<User> user_list = taskService.findTaskDetailUser(task);
				if(user_list.size()>0){
					task_list.get(i).setUser_list(user_list);
				}
			
				//查询任务完成情况
				Integer result = taskService.queryUnFinishedCount(task_list.get(i));
				if(result > 0){
					task_list.get(i).setState(0);
				}else{
					task_list.get(i).setState(1);
				}
				
			}
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(task_list);
		}else{
			msg.setCode(101);
			msg.setMsg("获取数据成功！");
		}
		return msg;
	}
	
	/**
	 * 审核通过
	 */
	@RequestMapping("/verifyTask")
	@ResponseBody public Message verifyTask(@RequestBody TaskUser taskUser){
//		System.out.println(taskUser.getUserid());
		Message msg = new Message();
		Integer result = taskService.verifyTask(taskUser);
		if(result > 0){
			msg.setCode(100);
			msg.setMsg("操作成功");
			
			return msg;
		}
		msg.setCode(101);
		msg.setMsg("操作失败");
		return msg;
		
	}
	
	/**
	 * add by 李袁  on 2018-02-19
	 * 查询部门的任务清单
	 */
	@RequestMapping("/queryTaskListByGroupid")
	public @ResponseBody Message queryTaskListByGroupid(@RequestBody Group group) throws Exception{
		Message msg = new Message();
		List<TaskCustom> task_list = taskService.queryTaskListByGroupid(group.getGroupid());
//		System.out.println(task_list.size()+"========");
		if(task_list.size()>0){
			for(int i=0; i<task_list.size(); i++){
				
				Task task = new Task();
				task.setTaskid(task_list.get(i).getTaskid());
				List<User> user_list = taskService.findTaskDetailUser(task);
				if(user_list.size()>0){
					task_list.get(i).setUser_list(user_list);
				}
			
				//查询任务完成情况
				Integer result = taskService.queryUnFinishedCount(task_list.get(i));
				if(result > 0){
					task_list.get(i).setState(0);
				}else{
					task_list.get(i).setState(1);
				}
				
			}
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(task_list);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无数据！");
		}
		return msg;
	}
}
