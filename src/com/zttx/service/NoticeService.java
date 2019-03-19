package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.NoticeMapper;
import com.zttx.po.Notice;
import com.zttx.po.NoticeCustom;
import com.zttx.po.NoticeNumberCustom;
import com.zttx.po.NoticeUser;
import com.zttx.vo.NoticeQueryVo;

@Transactional
@Service(value="noticeService")
public class NoticeService {
	
	@Autowired 
	private NoticeMapper noticeMapper;
	
	//插入notice_user表数据
	public int saveNoticeUser(NoticeUser nu){
		int res=noticeMapper.saveNoticeUser(nu);
		
		return res;
	}
	
	//插入notice表数据
	public int saveNotice(NoticeQueryVo nqv){
		return noticeMapper.saveNotice(nqv);
	}

	//查询登录用户的全部消息
	public List<Notice> queryMessageListByUserId(NoticeQueryVo noticeQueryVo) {
		// TODO Auto-generated method stub
		return noticeMapper.queryMessageListByUserId(noticeQueryVo);
	}

	//根据消息ID查询消息详情
	public NoticeCustom queryDetail(Notice notice) {
		// TODO Auto-generated method stub
		return noticeMapper.queryDetail(notice);
	}

	//查询登录用户各模块的全部消息数量
	public NoticeNumberCustom queryMessageCount(NoticeQueryVo noticeQueryVo) {
		// TODO Auto-generated method stub
		return noticeMapper.queryMessageCount(noticeQueryVo);
	}

	//更改用户消息处理状态
	public int updateNoticeUserState(NoticeQueryVo noticeQueryVo) {
		// TODO Auto-generated method stub
		return noticeMapper.updateNoticeUserState(noticeQueryVo);
	}
	
}
