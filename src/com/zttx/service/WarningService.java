package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.WarningMapper;
import com.zttx.po.User;
import com.zttx.po.Warning;
import com.zttx.po.WarningCustom;
import com.zttx.po.WarningUser;

@Transactional
@Service(value="warningService")
public class WarningService {
	
	@Autowired 
	private WarningMapper warningMapper;
	
	//插入warning_user表数据
	public int saveWarningUser(WarningUser wu){
		int res=warningMapper.saveWarningUser(wu);
		return res;
	}
	
	//插入warning表数据
	public int saveWarning(Warning w){
		return warningMapper.saveWarning(w);
	}

	//查询登录用户警报信息列表
	public List<WarningCustom> queryWarningByUser(User user) {
		// TODO Auto-generated method stub
		return warningMapper.queryWarningByUser(user);
	}

	//根据warning参数查询warning信息
	public WarningCustom findWarning(Warning warning) {
		// TODO Auto-generated method stub
		return warningMapper.findWarning(warning);
	}

	//更改用户警报关联表的状态信息
	public int updateWarningUserState(WarningUser warningUser) {
		// TODO Auto-generated method stub
		return warningMapper.updateWarningUserState(warningUser);
	}

}
