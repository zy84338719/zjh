package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.SetMapper;
import com.zttx.po.Set;

@Transactional
@Service(value="setService")
public class SetService {
	
	@Autowired
	private SetMapper setMapper;

	//插入设置信息
	public int save(Set set) {
		// TODO Auto-generated method stub
		return setMapper.save(set);
	}

	//查找全部设置信息
	public List<Set> find() {
		// TODO Auto-generated method stub
		return setMapper.find();
	}

	//更新设置信息
	public int update(Set set) {
		// TODO Auto-generated method stub
		return setMapper.update(set);
	}
	
	
	

}
