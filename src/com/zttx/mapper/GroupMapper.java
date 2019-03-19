package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Group;
import com.zttx.po.GroupCustom;

public interface GroupMapper {
	
	//根据群组ID查询群组信息
	public Group findGroupById(String groupid);

	//查询全部班组信息
	public List<GroupCustom> queryAllGroup();

	//通过Code查询群组信息
	public Group findGroupByCode(String code);

	public Group findBy_groupid(String _groupid);

	public Integer updateGroup_cop(Group group);

	public Integer saveGroup_cop(Group group);

	public List<GroupCustom> queryAllGroupOnlyNameAndId();

	public Group findGroupByCodeOnlyName(String code);

	
	
	
	

}
