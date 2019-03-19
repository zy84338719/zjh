package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Urge;
import com.zttx.po.UrgeCustom;
import com.zttx.po.User;


public interface UrgeMapper {

	List<UrgeCustom> queryMyReceived(User user);

	Integer deleteUrge(Urge urge);

	void save(Urge urge);

	Integer getCount(Urge urge);
	
}
