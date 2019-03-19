package com.zttx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttx.po.DistanceGroupVolumeCustom;
import com.zttx.po.Group;
import com.zttx.po.Point;
import com.zttx.po.SectionCustom;
import com.zttx.po.SectionVolume;
import com.zttx.po.User;
import com.zttx.po.Warning;
import com.zttx.po.WarningCustom;
import com.zttx.po.WarningUser;
import com.zttx.service.DistanceService;
import com.zttx.service.GroupService;
import com.zttx.service.PointService;
import com.zttx.service.SectionService;
import com.zttx.service.UserService;
import com.zttx.service.WarningService;
import com.zttx.utils.DateFormatUtils;
import com.zttx.utils.GUIDSeqGenerator;
import com.zttx.vo.DistanceQueryVo;
import com.zttx.vo.Message;
import com.zttx.vo.SectionQueryVo;
import com.zttx.vo.UserQueryVo;

@Controller
@RequestMapping("/warning")
public class WarningController {
	
	@Autowired
	private WarningService warningService;
	
	@Autowired
	private UserService userService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private DistanceService distanceService;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private PointService pointService;
	/**
	 * 保存警报消息
	 * @param warning
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveWarning")
	public @ResponseBody Message saveWarning(@RequestBody Warning warning)throws Exception{
		Message msg = new Message();
		//2017-10-28根据里程段查询所属标段
		SectionQueryVo sectionQueryVo = new SectionQueryVo();
		sectionQueryVo.setStart_distance(warning.getStartdistance());
		sectionQueryVo.setEnd_distance(warning.getEnddistance());
		List<DistanceGroupVolumeCustom> distanceGroupVolume_list = distanceService.queryGroupVolumeByDistance(sectionQueryVo);
		String tenderid = null;
		if(distanceGroupVolume_list.size()>0){
			tenderid = distanceGroupVolume_list.get(0).getTenderid();
		}
		warning.setTender(tenderid);
		//2017-10-28
		String warningid = GUIDSeqGenerator.getInstance().newGUID();
		Date createtime = DateFormatUtils.format1(new Date());
		warning.setWarningid(warningid);
		warning.setCreatetime(createtime);
		int result = warningService.saveWarning(warning);
		UserQueryVo userQueryVo = new UserQueryVo();
		userQueryVo.setGroupname("指挥部");
		List<User> user_list1 = userService.findUserByGroup(userQueryVo);
		if(user_list1.size()>0){
			for(int i=0; i<user_list1.size(); i++){
				WarningUser wu = new WarningUser();
				wu.setWarningid(warningid);
				wu.setReceiverid(user_list1.get(i).getUserid());
				wu.setState(0);
				int res = warningService.saveWarningUser(wu);
			}
		}
		userQueryVo.setGroupname("项目经理部");
		List<User> user_list2 = userService.findUserByGroup(userQueryVo);
		if(user_list2.size()>0){
			for(int i=0; i<user_list2.size(); i++){
				WarningUser wu = new WarningUser();
				wu.setWarningid(warningid);
				wu.setReceiverid(user_list2.get(i).getUserid());
				wu.setState(0);
				int res = warningService.saveWarningUser(wu);
			}
		}
		
		if(result == 1){
			msg.setCode(100);
			msg.setMsg("警报消息推送成功！");
		}else{
			msg.setCode(101);
			msg.setMsg("警报消息推送失败！");
		}
		return msg;
	}
	
	/**
	 * 根据登录用户查询班组里程信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDistanceByUser")
	public @ResponseBody Message queryDistanceByUser(@RequestBody User user)throws Exception{
		Message msg = new Message();
		User user_new = userService.findUser(user);
		Group group = groupService.findGroupById(user_new.getGroupid());
		DistanceQueryVo distanceQueryVo = new DistanceQueryVo();
		if(group != null){
			distanceQueryVo.setGroupid(group.getGroupid());
			distanceQueryVo.setHandle_state(-1);
			List<DistanceGroupVolumeCustom> distanceGroupVolume_list = distanceService.queryGroupByType(distanceQueryVo);
			if(distanceGroupVolume_list.size() > 0){
				msg.setCode(100);
				msg.setMsg("获取数据成功！");
				msg.setObj(distanceGroupVolume_list);
			}else{
				msg.setCode(101);
				msg.setMsg("暂无符合条件的数据！");
			}
		}else{
			msg.setCode(102);
			msg.setMsg("暂无分组数据！");
		}
		return msg;
	}
	
	/**
	 * 查询登录用户警报信息列表
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryWarningByUser")
	public @ResponseBody Message queryWarningByUser(@RequestBody User user)throws Exception{
		Message msg = new Message();
		List<WarningCustom> warningCustom_list = warningService.queryWarningByUser(user);
		if(warningCustom_list.size()>0){
			msg.setCode(100);
			msg.setMsg("用户警报信息列表获取成功！");
			msg.setObj(warningCustom_list);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无警报信息！");
		}
		return msg;
	}
	
	/**
	 * 查找警报消息详情
	 * @param warning
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findWarningDetail")
	public @ResponseBody Message findWarningDetail(@RequestBody Warning warning)throws Exception{
		Message msg = new Message();
		WarningCustom warning_new = warningService.findWarning(warning);
		//2017-10-28根据里程段查询所属标段
		SectionQueryVo sectionQueryVo = new SectionQueryVo();
		//以结束里程作为截面里程查询该截面的点坐标信息
		sectionQueryVo.setDistance(warning_new.getEnddistance());
		SectionCustom sectionCustom = new SectionCustom();
		List<SectionCustom> section_list = sectionService.findSectionByDistance(sectionQueryVo);
		List<Point> point_list = new ArrayList<Point>();
		if(section_list.size() > 0){
			sectionCustom = section_list.get(0);
			float f = sectionCustom.getDistance() - sectionQueryVo.getDistance();
			if(Math.abs(f) >= 0.5){
				msg.setCode(101);
				msg.setMsg("暂无该截面数据！");
			}else{
				point_list = pointService.findPointBySectionId(sectionCustom.getSectionid());
				sectionCustom.setPoint(point_list);
				msg.setCode(100);
				msg.setMsg("获取成功！");
			}
		}
		//设置起止里程段
		sectionCustom.setStart_distance(warning_new.getStartdistance());
		sectionCustom.setEnd_distance(warning_new.getEnddistance());
		//统计超欠挖信息
		SectionVolume sectionVolume = new SectionVolume();
		sectionQueryVo.setStart_distance(warning_new.getStartdistance());
		sectionQueryVo.setEnd_distance(warning_new.getEnddistance());
		sectionVolume = sectionService.findVolumeByDistance(sectionQueryVo);
		if(sectionVolume != null){
			sectionCustom.setOwevolume(sectionVolume.getOwevolume());
		}
		/*SectionCustom sectionCustom = new SectionCustom();
		List<SectionCustom> section_list = sectionService.findSectionByDistance(sectionQueryVo);
		List<Point> point_list = new ArrayList<Point>();
		if(section_list.size() > 0){
			sectionCustom = section_list.get(0);
			float f = sectionCustom.getDistance() - sectionQueryVo.getEnd_distance();
			if(Math.abs(f) >= 0.5){
				msg.setCode(101);
				msg.setMsg("暂无该截面数据！");
				msg.setObj(sectionCustom);
			}else{
				point_list = pointService.findPointBySectionId(sectionCustom.getSectionid());
				sectionCustom.setPoint(point_list);
				msg.setCode(100);
				msg.setMsg("获取成功！");
				msg.setObj(sectionCustom);
			}
		}else{
			msg.setCode(101);
			msg.setMsg("暂无该截面数据！");
		}*/
		msg.setObj(sectionCustom);
		return msg;
	}
	
	/**
	 * 获取混凝土浇筑警报
	 * @param warning
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findPouringWarning")
	public @ResponseBody Message findPouringWarning(@RequestBody Warning warning)throws Exception{
		Message msg = new Message();
		WarningCustom warning_new = warningService.findWarning(warning);
		if(warning_new != null){
			msg.setCode(100);
			msg.setObj(warning_new);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无消息！");
		}
		return msg;
	}
	
	/**
	 * 更改用户警报关联表的状态信息
	 * @param warningUser
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateWarningUserState")
	public @ResponseBody Message updateWarningUserState(@RequestBody WarningUser warningUser)
			throws Exception{
		Message msg = new Message();
		int result = warningService.updateWarningUserState(warningUser);
		if(result == 1){
			msg.setCode(100);
			msg.setMsg("已标记为已读！");
		}else{
			msg.setCode(101);
			msg.setMsg("更改失败！");
		}
		return msg;
	}

}
