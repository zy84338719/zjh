package com.zttx.controller;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttx.po.DistanceGroupVolumeCustom;
import com.zttx.po.Group;
import com.zttx.po.GroupCustom;
import com.zttx.po.SectionCustom;
import com.zttx.service.DistanceService;
import com.zttx.service.GroupService;
import com.zttx.service.SectionService;
import com.zttx.vo.DistanceQueryVo;
import com.zttx.vo.Message;
import com.zttx.vo.SectionQueryVo;

@Controller
@RequestMapping("/distance")
public class DistanceController {

	@Autowired
	private DistanceService distanceService;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private GroupService groupService;
	
	/**
	 * 根据里程段查询各班组的超欠挖信息
	 * @param sectionQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryGroupVolumeByDistance")
	public @ResponseBody Message queryGroupVolumeByDistance(@RequestBody SectionQueryVo sectionQueryVo)
			throws Exception{
		Message msg = new Message();
		List<DistanceGroupVolumeCustom> distanceGroupVolume_list = distanceService.queryGroupVolumeByDistance(sectionQueryVo);
		if(distanceGroupVolume_list.size() > 0){
			for(int i = 0; i < distanceGroupVolume_list.size(); i++){
				DistanceGroupVolumeCustom disGroup = distanceGroupVolume_list.get(i);
				Float begindistance = disGroup.getBegindistance();
				Float enddistance = disGroup.getEnddistance();
				SectionQueryVo sectionQuery_new = new SectionQueryVo();
				sectionQuery_new.setStart_distance(begindistance);
				sectionQuery_new.setEnd_distance(enddistance);
				//查询最早一次的截面扫描数据
				List<SectionCustom> oldsection_list = sectionService.findOldSectionByDistance(sectionQuery_new);
				//查询最新一次的截面扫描数据
				List<SectionCustom> newsection_list = sectionService.findSectionByDistance(sectionQuery_new);
				if(oldsection_list.size() > 0){
					Date oldmeasuretime = oldsection_list.get(0).getMeasuretime();
					distanceGroupVolume_list.get(i).setOldmeasuretime(oldmeasuretime);
				}
				if(newsection_list.size() > 0){
					Date newmeasuretime = newsection_list.get(0).getMeasuretime();
					distanceGroupVolume_list.get(i).setNewmeasuretime(newmeasuretime);
				}
			}
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(distanceGroupVolume_list);
		}else{
			msg.setCode(101);
			msg.setMsg("该里程段暂无数据！");
		}
		return msg;
	}
	
	/**
	 * 根据里程ID处理超欠挖信息
	 * @param distanceQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/handleOverOweByDistanceId")
	public @ResponseBody Message handleOverOweByDistanceId(@RequestBody DistanceQueryVo distanceQueryVo)throws Exception{
		Message msg = new Message();
		Integer result = distanceService.handleOverOwe(distanceQueryVo);	//处理超欠挖状态
		if(result == 1){
			msg.setCode(100);
			msg.setMsg("操作成功！");
		}else{
			msg.setCode(101);
			msg.setMsg("操作失败！");
		}
		return msg;
	}
	
	/**
	 * 多条件查询班组超欠挖信息
	 * @param distanceQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryGroupByType")
	public @ResponseBody Message queryGroupByType(@RequestBody DistanceQueryVo distanceQueryVo)
			throws Exception{
		Message msg = new Message();
		List<DistanceGroupVolumeCustom> distanceGroupVolume_list = distanceService.queryGroupByType(distanceQueryVo);
		if(distanceGroupVolume_list.size() > 0){
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(distanceGroupVolume_list);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无符合条件的数据！");
		}
		return msg;
	}
	
	/**
	 * 推送  根据起始里程查询班组
	 * @param sqv
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryDistanceByBeginEnd")
	public @ResponseBody Message queryDistanceByBeginEnd(@RequestBody SectionQueryVo sqv) throws Exception{
		Message msg=new Message();
		if(sqv.getStart_distance()!=null&&sqv.getEnd_distance()!=null){
			List<DistanceGroupVolumeCustom> distance_list=distanceService.queryGroupVolumeByDistance(sqv);
			if(distance_list.size()>0){
				for(int i=0; i<distance_list.size(); i++){
					String code = distance_list.get(i).getCode().substring(0, 11);
					Group g = groupService.findGroupByCode(code);
					distance_list.get(i).setFathergroupname(g.getGroupname());
				}
				msg.setCode(100);
				msg.setMsg("获取数据成功！");
				msg.setObj(distance_list);
			}else{
				msg.setCode(101);
				msg.setMsg("该里程段无数据！");
			}
		}
		return msg;
	}
	
	/**
	 * 查询全部班组信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAllGroup")
	public @ResponseBody Message queryAllGroup()throws Exception{
		Message msg = new Message();
		List<GroupCustom> group_list = groupService.queryAllGroup();
		if(group_list.size()>0){
			for(int i=0; i<group_list.size(); i++){
				String code_l = group_list.get(i).getCode();
				String code = null;
				if(code_l.length()>14){
					code = group_list.get(i).getCode().substring(0, 11);
					Group g = groupService.findGroupByCode(code);
					if(g != null){
						group_list.get(i).setFathergroupname(g.getGroupname());
					}
				}
			}
			msg.setCode(100);
			msg.setObj(group_list);
		}else{
			msg.setCode(101);
			msg.setObj("暂无分组信息！");
		}
		return msg;
	}
}
