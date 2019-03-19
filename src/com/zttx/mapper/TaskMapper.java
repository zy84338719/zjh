package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Task;
import com.zttx.po.TaskCustom;
import com.zttx.po.TaskUser;
import com.zttx.po.TaskUserCustom;
import com.zttx.po.User;
import com.zttx.vo.TaskQueryVo;


public interface TaskMapper {

	//保存用户任务信息
	int saveTaskUser(TaskUser taskUser);

	//保存任务信息
	int saveTask(Task task);

	//查询登录用户发布的任务列表
	List<TaskCustom> queryTaskList(User user);

	//查询登录用户收到的任务列表
	List<TaskUserCustom> queryTaskUserList(User user);

	//查看任务详情
	TaskCustom findTaskDetail(Task task);

	//根据任务ID查询相关人员信息
	List<User> findTaskDetailUser(Task task);

	//用户任务反馈
	int dealTask(TaskUser taskUser);

	//根据taskID查询任务关联表中是否存在状态为1表示“已完成”的记录
	List<TaskUser> queryTaskUserByState(Task task);

	//根据taskID删除任务表与关联表数据
	int deleteTask(Task task);

	//根据taskID删除任务关联表数据
	int deleteTaskUser(Task task);

	//更新task信息
	int modify(Task task);

	//根据Taskid查询任务接收人员列表
	List<TaskUserCustom> queryTaskUserByTaskId(String taskid);

	//查询全部任务列表
	List<TaskCustom> queryAllTask();

	//根据任务标题查询任务列表
	List<TaskCustom> queryTaskByTitle(TaskQueryVo taskQueryVo);

	Integer queryUnFinishedCount(TaskCustom taskCustom);

	//查询所有未完成的工作清单
	List<TaskCustom> queryAllUnfinishedTasks();

	//审核通过任务
	Integer verifyTask(TaskUser taskUser);

	List<TaskCustom> queryTaskListByGroupid(String groupid);
	
}
