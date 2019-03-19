package com.zttx.mapper;

import java.util.List;

import com.zttx.po.User;
import com.zttx.po.Warning;
import com.zttx.po.WarningCustom;
import com.zttx.po.WarningUser;

public interface WarningMapper {
	
	//插入warning_user表数据
	public int saveWarningUser(WarningUser wu);
	
	//插入warning表数据
	public int saveWarning(Warning w);

	//查询登录用户警报信息列表
	public List<WarningCustom> queryWarningByUser(User user);

	//根据warning参数查询warning信息
	public WarningCustom findWarning(Warning warning);

	//更改用户警报关联表的状态信息
	public int updateWarningUserState(WarningUser warningUser);
	
}
