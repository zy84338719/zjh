package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper4.UserMapper4;
import com.zttx.po.QualityAuthority;
import com.zttx.po.User;
import com.zttx.po.UserAuthority;
import com.zttx.po.UserCustom;
import com.zttx.vo.TaskQueryVo;
import com.zttx.vo.UserQueryVo;

@Transactional
@Service(value="userService4")
public class UserService4 {
	
	@Autowired
	private UserMapper4 userMapper4;
	
	public UserCustom findUser(User user) throws Exception{		
		UserCustom existUser = userMapper4.findUser(user);
		return existUser;
	}
	
	//查询领导
	public List<UserCustom> findLeader() throws Exception{		
		return userMapper4.findLeader();
	}
	
	//根据groupid查询用户
	public List<User> findUserByGroupid(String groupid) throws Exception{
		return userMapper4.findUserByGroupid(groupid);
	}

	//更新用户信息
	public int updateUser(User user) throws Exception {
		// TODO Auto-generated method stub
		return userMapper4.updateUser(user);
	}

	//查询用户信息
	public List<UserCustom> findUserByType(TaskQueryVo taskQueryVo) {
		// TODO Auto-generated method stub
		return userMapper4.findUserByType(taskQueryVo);
	}

	//根据角色类型查询用户
	public List<User> findUserByRole(UserQueryVo userQueryVo) {
		// TODO Auto-generated method stub
		return userMapper4.findUserByRole(userQueryVo);
	}

	//根据班组查询用户信息
	public List<User> findUserByGroup(UserQueryVo userQueryVo) {
		// TODO Auto-generated method stub
		return userMapper4.findUserByGroup(userQueryVo);
	}
	
	/**
	 * 下面都是数据同步方法
	 */
	//根据_userid查询是否存在
	public Boolean isExistUser(String _userid) throws Exception{
		User user = userMapper4.findBy_userid(_userid);
		if(user == null){
			return false;
		}else{
			return true;
		}
	}

	public Integer updateUser_cop(User user) throws Exception{
		// TODO Auto-generated method stub
		return userMapper4.updateUser_cop(user);
	}

	public Integer saveUser_cop(User user) throws Exception{
		// TODO Auto-generated method stub
		return userMapper4.saveUser_cop(user);
	}

	public User findBy_userid(String _userid) {
		// TODO Auto-generated method stub
		return userMapper4.findBy_userid(_userid);
	}

	public Boolean isExistPermission(String userid) {
		// TODO Auto-generated method stub
		Integer count = userMapper4.findUserAuthority(userid);
		if(count > 0){
			return true;
		}else{
			return false;
		}
	}

	public Integer updatePermission(UserAuthority userAuthority) {
		// TODO Auto-generated method stub
		return userMapper4.updatePermission(userAuthority);
	}

	public Integer savePermission(UserAuthority userAuthority) {
		// TODO Auto-generated method stub
		return userMapper4.savePermission(userAuthority);
	}

	public void deleteQualityAuthority(UserAuthority queryUserAuthority) {
		// TODO Auto-generated method stub
		userMapper4.deleteQualityAuthority(queryUserAuthority);
	}

	public void insertIntoQualityAuthority(QualityAuthority qualityAuthority) {
		// TODO Auto-generated method stub
		userMapper4.insertIntoQualityAuthority(qualityAuthority);
	}

	public List<User> findUserScore(UserQueryVo userQueryVo) {
		// TODO Auto-generated method stub
		return userMapper4.findUserScore(userQueryVo);
	}

	public List<User> findUserOnlyNameAndIdByGroupid(String groupid) {
		// TODO Auto-generated method stub
		return userMapper4.findUserOnlyNameAndIdByGroupid(groupid);
	}

}
