package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.TaskMapper;
import com.zttx.po.Task;
import com.zttx.po.TaskCustom;
import com.zttx.po.TaskUser;
import com.zttx.po.TaskUserCustom;
import com.zttx.po.User;
import com.zttx.vo.TaskQueryVo;

@Transactional
@Service(value="taskService")
public class TaskService {
	
	@Autowired
	private TaskMapper taskMapper;

	//保存用户任务信息
	public int saveTaskUser(TaskUser taskUser) {
		// TODO Auto-generated method stub
		return taskMapper.saveTaskUser(taskUser);
	}

	//保存任务信息
	public int saveTask(Task task) {
		// TODO Auto-generated method stub
		return taskMapper.saveTask(task);
	}

	//查询登录用户发布的任务列表
	public List<TaskCustom> queryTaskList(User user) {
		// TODO Auto-generated method stub
		return taskMapper.queryTaskList(user);
	}

	//查询登录用户收到的任务列表
	public List<TaskUserCustom> queryTaskUserList(User user) {
		// TODO Auto-generated method stub
		return taskMapper.queryTaskUserList(user);
	}

	//查询任务详情
	public TaskCustom findTaskDetail(Task task) {
		// TODO Auto-generated method stub
		return taskMapper.findTaskDetail(task);
	}

	//根据任务ID查询相关人员信息
	public List<User> findTaskDetailUser(Task task) {
		// TODO Auto-generated method stub
		return taskMapper.findTaskDetailUser(task);
	}

	//用户任务反馈
	public int dealTask(TaskUser taskUser) {
		// TODO Auto-generated method stub
		return taskMapper.dealTask(taskUser);
	}

	//根据taskID查询任务关联表中是否存在状态为1表示“已完成”的记录
	public List<TaskUser> queryTaskUserByState(Task task) {
		// TODO Auto-generated method stub
		return taskMapper.queryTaskUserByState(task);
	}

	//根据taskID删除task中的记录，以及taskUser关联表中的数据
	public int deleteTask(Task task) {
		// TODO Auto-generated method stub
		return taskMapper.deleteTask(task);
	}

	//根据taskID删除taskUser关联表中的数据
	public int deleteTaskUser(Task task) {
		// TODO Auto-generated method stub
		return taskMapper.deleteTaskUser(task);
	}

	//更新task信息
	public int modify(Task task) {
		// TODO Auto-generated method stub
		return taskMapper.modify(task);
	}

	//根据Taskid查询任务接收人员列表
	public List<TaskUserCustom> queryTaskUserByTaskId(String taskid) {
		// TODO Auto-generated method stub
		return taskMapper.queryTaskUserByTaskId(taskid);
	}

	//查询全部任务列表
	public List<TaskCustom> queryAllTask() {
		// TODO Auto-generated method stub
		return taskMapper.queryAllTask();
	}

	//根据任务标题模糊查询任务列表
	public List<TaskCustom> queryTaskByTitle(TaskQueryVo taskQueryVo) {
		// TODO Auto-generated method stub
		return taskMapper.queryTaskByTitle(taskQueryVo);
	}

	//查询任务未完成人数
	public Integer queryUnFinishedCount(TaskCustom taskCustom) {
		// TODO Auto-generated method stub
		return taskMapper.queryUnFinishedCount(taskCustom);
	}

	//查询未完成的任务清单
	public List<TaskCustom> queryAllUnfinishedTasks() {
		// TODO Auto-generated method stub
		return taskMapper.queryAllUnfinishedTasks();
	}

	//审核任务完成
	public Integer verifyTask(TaskUser taskUser) {
		// TODO Auto-generated method stub
		return taskMapper.verifyTask(taskUser);
	}

	//按部门查询清单列表
	public List<TaskCustom> queryTaskListByGroupid(String groupid) {
		// TODO Auto-generated method stub
		return taskMapper.queryTaskListByGroupid(groupid);
	}
	
	
}
