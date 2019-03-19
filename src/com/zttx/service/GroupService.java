package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.GroupMapper;
import com.zttx.po.Group;
import com.zttx.po.GroupCustom;

@Transactional
@Service(value="groupService")
public class GroupService {
	
	@Autowired
	private GroupMapper groupMapper;
	
	//根据ID查询班组信息
	public Group findGroupById(String groupid) throws Exception{
		return groupMapper.findGroupById(groupid);
	}

	//查询全部班组信息
	public List<GroupCustom> queryAllGroup() {
		// TODO Auto-generated method stub
		return groupMapper.queryAllGroup();
	}

	//通过Code获取群组信息
	public Group findGroupByCode(String code) {
		// TODO Auto-generated method stub
		return groupMapper.findGroupByCode(code);
	}

	public Boolean isExistGroup(String _groupid) {
		// TODO Auto-generated method stub
		Group group = groupMapper.findBy_groupid(_groupid);
		if(group != null){
			return true;
		}else{
			return false;
		}
	}

	public Integer updateGroup_cop(Group group) {
		// TODO Auto-generated method stub
		return groupMapper.updateGroup_cop(group);
	}

	public Integer saveGroup_cop(Group group) {
		// TODO Auto-generated method stub
		return groupMapper.saveGroup_cop(group);
	}

	public Group findBy_groupid(String _groupid) {
		// TODO Auto-generated method stub
		return groupMapper.findBy_groupid(_groupid);
	}

	public List<GroupCustom> queryAllGroupOnlyNameAndId() {
		// TODO Auto-generated method stub
		return groupMapper.queryAllGroupOnlyNameAndId();
	}

	public Group findGroupByCodeOnlyName(String code) {
		// TODO Auto-generated method stub
		return groupMapper.findGroupByCodeOnlyName(code);
	}

	
}
