package com.zttx.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttx.po.Point;
import com.zttx.po.SectionCustom;
import com.zttx.po.SectionVolume;
import com.zttx.service.GroupService;
import com.zttx.service.PointService;
import com.zttx.service.SectionService;
import com.zttx.vo.Message;
import com.zttx.vo.SectionQueryVo;

@Controller
@RequestMapping("/section")
public class SectionController {
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private PointService pointService;
	
	/**
	 * 查询最新截面信息
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findNewestSection")
	public @ResponseBody Message findNewestSection()
			throws Exception{
		Message msg = new Message();
		SectionCustom sectionCustom = new SectionCustom();
		List<SectionCustom> section_list = sectionService.findNewestSection();
		List<Point> point_list = new ArrayList<Point>();
		if(section_list.size() > 0){
			sectionCustom = section_list.get(0);
			point_list = pointService.findPointBySectionId(sectionCustom.getSectionid());
			sectionCustom.setPoint(point_list);
			msg.setCode(100);
			msg.setMsg("获取成功");
			msg.setObj(sectionCustom);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无最新数据");
		}
		return msg;
	}
	
	/**
	 * 查询某里程的截面信息
	 * @param sectionQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findSectionByDistance")
	public @ResponseBody Message findSectionByDistance(@RequestBody SectionQueryVo sectionQueryVo)
			throws Exception{
		Message msg = new Message();
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
				msg.setObj(sectionCustom);
			}
		}else{
			msg.setCode(101);
			msg.setMsg("暂无该截面数据！");
		}
		return msg;
	}
	
	/**
	 * 查询里程段的全部断面的超欠挖数据
	 * @param sectionQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryOverOweByDistance")
	public @ResponseBody Message queryOverOweByDistance(@RequestBody SectionQueryVo sectionQueryVo)
			throws Exception{
		Message msg = new Message();
		List<SectionCustom> section_list = new ArrayList<SectionCustom>();
		section_list = sectionService.findSectionByDistance(sectionQueryVo);
		if(section_list.size() > 0){
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(section_list);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无该里程段的数据！");
		}
		return msg;
	}
	
	/**
	 * 计算里程段的超欠挖信息
	 * @param sectionQueryVo
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/findVolumeByDistance")
	public @ResponseBody Message findVolumeByDistance(@RequestBody SectionQueryVo sectionQueryVo)
			throws Exception{
		Message msg = new Message();
		SectionVolume sectionVolume = new SectionVolume();
		sectionVolume = sectionService.findVolumeByDistance(sectionQueryVo);
		if(sectionVolume != null){
			msg.setCode(100);
			msg.setMsg("获取数据成功！");
			msg.setObj(sectionVolume);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无该里程段的超欠挖数据！");
		}
		return msg;
	}
	
	
}
