package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Set;


public interface SetMapper {

	//根据截面ID查询点信息
	int save(Set set);

	//查询全部设置信息
	List<Set> find();

	//更新设置信息
	int update(Set set);
	
	
	

}
