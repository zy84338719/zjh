package com.zttx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper4.AuthorityMapper4;
import com.zttx.po.AuthorityCustom;
import com.zttx.po.SecondAuthority;
import com.zttx.po.TopAuthority;
import com.zttx.po.UserAuthority;

@Transactional
@Service(value="authorityService4")
public class AuthorityService4 {
	
	@Autowired
	private AuthorityMapper4 authorityMapper4;

	//获取用户权限信息
	public UserAuthority queryUserAuthority(AuthorityCustom authorityCustom) {
		// TODO Auto-generated method stub
		return authorityMapper4.queryUserAuthority(authorityCustom);
	}

	//根据topid查询对应权限
	public TopAuthority findTopAuthorityByTopId(String topid) {
		// TODO Auto-generated method stub
		return authorityMapper4.findTopAuthorityByTopId(topid);
	}

	//根据secondid查询对应二级权限
	public SecondAuthority findSecondAuthorityBySecondId(String secondid) {
		// TODO Auto-generated method stub
		return authorityMapper4.findSecondAuthorityBySecondId(secondid);
	}

}
