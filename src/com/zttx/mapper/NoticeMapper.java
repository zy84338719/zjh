package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Notice;
import com.zttx.po.NoticeCustom;
import com.zttx.po.NoticeNumberCustom;
import com.zttx.po.NoticeUser;
import com.zttx.vo.NoticeQueryVo;

public interface NoticeMapper {
	
	//插入notice_user表数据
	public int saveNoticeUser(NoticeUser nu);
	
	//插入notice表数据
	public int saveNotice(NoticeQueryVo nqv);

	//查询登录用户的全部信息
	public List<Notice> queryMessageListByUserId(NoticeQueryVo noticeQueryVo);

	//根据消息ID查询消息详情
	public NoticeCustom queryDetail(Notice notice);

	//查询登录用户各模块的全部消息数量
	public NoticeNumberCustom queryMessageCount(NoticeQueryVo noticeQueryVo);

	//更改用户消息处理状态
	public int updateNoticeUserState(NoticeQueryVo noticeQueryVo);
	
}
