package com.zttx.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttx.po.Notice;
import com.zttx.po.NoticeCustom;
import com.zttx.po.NoticeNumberCustom;
import com.zttx.po.NoticeUser;
import com.zttx.po.User;
import com.zttx.service.NoticeService;
import com.zttx.service.UserService;
import com.zttx.utils.DateFormatUtils;
import com.zttx.utils.GUIDSeqGenerator;
import com.zttx.vo.Message;
import com.zttx.vo.NoticeQueryVo;

@Controller
@RequestMapping("/notice")
public class NoticeController {
	
	@Autowired
	private NoticeService noticeService;
	
	@Autowired
	private UserService userService;
	
	@RequestMapping("/saveNotice")
	public @ResponseBody Message saveNotice(@RequestBody NoticeQueryVo nqv) throws Exception{
		Message msg=new Message();
		NoticeUser nu=null;
		Date dt=new Date();
		Date sdf=DateFormatUtils.format1(dt);
		
		String msgid=GUIDSeqGenerator.getInstance().newGUID();
		
		if(nqv!=null){
			nqv.setMsgid(msgid);
			nqv.setCreatetime(sdf);
			int res=noticeService.saveNotice(nqv);
			
			if(res>0){
				List<User> user_list=userService.findUserByGroupid(nqv.getGroupid());
				for(int i=0;i<user_list.size();i++){
					nu=new NoticeUser();
					nu.setMsgid(msgid);
					nu.setReceiverid(user_list.get(i).getUserid());
					nu.setState(0);
					int res2=noticeService.saveNoticeUser(nu);
					if(res2==0){
						msg.setCode(101);
						msg.setMsg("插入消息数据有误！");
					}
				}
				msg.setCode(100);
				msg.setMsg("插入消息数据成功！");
				msg.setObj(nqv);
			}else{
				msg.setCode(101);
				msg.setMsg("插入消息数据有误！");
			}
		}
		
		return msg;
	}
	
	@RequestMapping("/saveLeaderNotice")
	public @ResponseBody Message saveLeaderNotice(@RequestBody NoticeQueryVo nqv) throws Exception{
		Message msg=new Message();
		NoticeUser nu=null;		
		
		if(nqv!=null){
			
			String messageid=nqv.getMsgid();
			String[] str=messageid.split("#");
			
			for(int i=1;i<str.length;i++){
				nu=new NoticeUser();
				nu.setMsgid(str[i]);
				nu.setReceiverid(nqv.getUserid());
				
				int res=noticeService.saveNoticeUser(nu);
				if(res==0){
					msg.setCode(101);
					msg.setMsg("插入领导数据有误！");
					return msg;
				}
			}
			msg.setCode(100);
			msg.setMsg("插入大领导数据成功！");
		}
		
		return msg;
	}
	
	/**
	 * 查询登录用户的全部消息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMessageList")
	public @ResponseBody Message queryMessageList(@RequestBody NoticeQueryVo noticeQueryVo)throws Exception{
		Message msg = new Message();
		List<Notice> notice_list = noticeService.queryMessageListByUserId(noticeQueryVo);
		if(notice_list.size() > 0){
			msg.setCode(100);
			msg.setMsg("获取消息列表成功！");
			msg.setObj(notice_list);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无消息数据！");
		}
		return msg;
	}
	
	/**
	 * 根据消息ID查询消息详情
	 * @param notice
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDetail")
	public @ResponseBody Message queryDetail(@RequestBody Notice notice)throws Exception{
		Message msg = new Message();
		NoticeCustom notice_new = noticeService.queryDetail(notice);
		if(notice != null){
			msg.setCode(100);
			msg.setMsg("获取消息详情成功！");
			msg.setObj(notice_new);
		}else{
			msg.setCode(101);
			msg.setMsg("获取消息详情失败！");
		}
		return msg;
	}
	
	/**
	 * 查询登录用户的全部模块消息数量
	 * @param noticeQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryMessageCount")
	public @ResponseBody Message queryMessageCount(@RequestBody NoticeQueryVo noticeQueryVo)throws Exception{
		Message msg = new Message();
		NoticeNumberCustom noticeCustom = noticeService.queryMessageCount(noticeQueryVo);
		if(noticeCustom != null){
			msg.setCode(100);
			msg.setMsg("获取消息数据成功！");
			msg.setObj(noticeCustom);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无消息数据！");
		}
		return msg;
	}
	/**
	 * 更新用户通知状态（反馈信息）
	 * @param noticeQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateNoticeUserState")
	public @ResponseBody Message updateNoticeUserState(@RequestBody NoticeQueryVo noticeQueryVo)throws Exception{
		Message msg = new Message();
		int result = noticeService.updateNoticeUserState(noticeQueryVo);
		if(result ==1){
			msg.setCode(100);
			msg.setMsg("消息状态更改成功！");
		}else{
			msg.setCode(101);
			msg.setMsg("消息状态更改失败！");
		}
		return msg;
	}
}
