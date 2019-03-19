package com.zttx.mapper4;

import java.util.List;

import com.zttx.po.QualityAuthority;
import com.zttx.po.User;
import com.zttx.po.UserAuthority;
import com.zttx.po.UserCustom;
import com.zttx.vo.TaskQueryVo;
import com.zttx.vo.UserQueryVo;

public interface UserMapper4 {
	//根据用户账号密码查找用户
	public UserCustom findUser(User user) throws Exception;
	
	//查询领导
	public List<UserCustom> findLeader() throws Exception;
		
	//根据groupid查询用户
	public List<User> findUserByGroupid(String groupid) throws Exception;

	//更新用户信息
	public int updateUser(User user) throws Exception;

	//条件查询用户
	public List<UserCustom> findUserByType(TaskQueryVo taskQueryVo);

	//根据角色查询用户
	public List<User> findUserByRole(UserQueryVo userQueryVo);

	//根据班组查询用户
	public List<User> findUserByGroup(UserQueryVo userQueryVo);

	/**
	 * 下面都是用户数据同步代码
	 */
	//查询用户是否存在
	public User findBy_userid(String _userid);

	//跟新用户
	public Integer updateUser_cop(User user);

	//保存用户
	public Integer saveUser_cop(User user);

	public Integer updatePermission(UserAuthority userAuthority);

	public Integer savePermission(UserAuthority userAuthority);

	public Integer findUserAuthority(String userid);

	public void deleteQualityAuthority(UserAuthority queryUserAuthority);

	public void insertIntoQualityAuthority(QualityAuthority qualityAuthority);

	public List<User> findUserScore(UserQueryVo userQueryVo);

	public List<User> findUserOnlyNameAndIdByGroupid(String groupid);
}
