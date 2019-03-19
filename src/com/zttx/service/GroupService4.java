package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper4.GroupMapper4;
import com.zttx.po.Group;
import com.zttx.po.GroupCustom;

@Transactional
@Service(value="groupService4")
public class GroupService4 {
	
	@Autowired
	private GroupMapper4 groupMapper4;
	
	//根据ID查询班组信息
	public Group findGroupById(String groupid) throws Exception{
		return groupMapper4.findGroupById(groupid);
	}

	//查询全部班组信息
	public List<GroupCustom> queryAllGroup() {
		// TODO Auto-generated method stub
		return groupMapper4.queryAllGroup();
	}

	//通过Code获取群组信息
	public Group findGroupByCode(String code) {
		// TODO Auto-generated method stub
		return groupMapper4.findGroupByCode(code);
	}

	public Boolean isExistGroup(String _groupid) {
		// TODO Auto-generated method stub
		Group group = groupMapper4.findBy_groupid(_groupid);
		if(group != null){
			return true;
		}else{
			return false;
		}
	}

	public Integer updateGroup_cop(Group group) {
		// TODO Auto-generated method stub
		return groupMapper4.updateGroup_cop(group);
	}

	public Integer saveGroup_cop(Group group) {
		// TODO Auto-generated method stub
		return groupMapper4.saveGroup_cop(group);
	}

	public Group findBy_groupid(String _groupid) {
		// TODO Auto-generated method stub
		return groupMapper4.findBy_groupid(_groupid);
	}

	public List<GroupCustom> queryAllGroupOnlyNameAndId() {
		// TODO Auto-generated method stub
		return groupMapper4.queryAllGroupOnlyNameAndId();
	}

	public Group findGroupByCodeOnlyName(String code) {
		// TODO Auto-generated method stub
		return groupMapper4.findGroupByCodeOnlyName(code);
	}

	
}
