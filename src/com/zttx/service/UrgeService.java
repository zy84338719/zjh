package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.UrgeMapper;
import com.zttx.po.Urge;
import com.zttx.po.UrgeCustom;
import com.zttx.po.User;

@Transactional
@Service(value="urgeService")
public class UrgeService {
	
	@Autowired
	private UrgeMapper urgeMapper;
	
	//查询我收到的催促消息
	public List<UrgeCustom> queryMyReceived(User user) {
		// TODO Auto-generated method stub
		return urgeMapper.queryMyReceived(user);
	}
	//删除某条催促消息
	public Integer deleteUrge(Urge urge) {
		// TODO Auto-generated method stub
		return urgeMapper.deleteUrge(urge);
	}
	//查询是否催促过
	public Integer getCount(Urge urge) {
		// TODO Auto-generated method stub
		return urgeMapper.getCount(urge);
	}
	//保存催促消息
	public void save(Urge urge) {
		// TODO Auto-generated method stub
		urgeMapper.save(urge);
	}
	
	
	
}
