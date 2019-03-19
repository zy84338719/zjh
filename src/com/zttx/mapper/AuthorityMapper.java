package com.zttx.mapper;

import com.zttx.po.AuthorityCustom;
import com.zttx.po.SecondAuthority;
import com.zttx.po.TopAuthority;
import com.zttx.po.UserAuthority;


public interface AuthorityMapper {

	//查询用户权限信息
	UserAuthority queryUserAuthority(AuthorityCustom authorityCustom);

	//根据topid查询对应权限信息
	TopAuthority findTopAuthorityByTopId(String topid);

	//根据secondid查询对应二级权限信息
	SecondAuthority findSecondAuthorityBySecondId(String secondid);

	

}
