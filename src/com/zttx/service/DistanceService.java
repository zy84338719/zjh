package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.DistanceMapper;
import com.zttx.po.Distance;
import com.zttx.po.DistanceGroupVolumeCustom;
import com.zttx.vo.DistanceQueryVo;
import com.zttx.vo.SectionQueryVo;

@Transactional
@Service(value="distanceService")
public class DistanceService {
	
	@Autowired
	private DistanceMapper distanceMapper;

	//根据里程段查询各班组的超欠挖信息
	public List<DistanceGroupVolumeCustom> queryGroupVolumeByDistance(
			SectionQueryVo sectionQueryVo) {
		// TODO Auto-generated method stub
		return distanceMapper.queryGroupVolumeByDistance(sectionQueryVo);
	}

	//处理超欠挖状态
	public Integer handleOverOwe(DistanceQueryVo distanceQueryVo) {
		// TODO Auto-generated method stub
		return distanceMapper.handleOverOwe(distanceQueryVo);
	}

	//多条件查询各班组信息
	public List<DistanceGroupVolumeCustom> queryGroupByType(
			DistanceQueryVo distanceQueryVo) {
		// TODO Auto-generated method stub
		return distanceMapper.queryGroupByType(distanceQueryVo);
	}
	
	/**
	 * 以下均为数据对接部分逻辑
	 * @param distance
	 * @return
	 */
	//查看是否存在
	public boolean isExistDistance(Distance distance) {
		// TODO Auto-generated method stub
		Distance isExist = distanceMapper.findDistance(distance);
		if(isExist == null){
			return false;
		}
		return true;
	}

	public Integer updateDistance(Distance distance) {
		// TODO Auto-generated method stub
		return distanceMapper.updateDistance(distance);
	}

	public Integer saveDistance(Distance distance) {
		// TODO Auto-generated method stub
		return distanceMapper.saveDistance(distance);
	}

	public void updateDistanceVolume(Distance own_distance) {
		// TODO Auto-generated method stub
		distanceMapper.updateDistanceVolume(own_distance);
	}
	//判断截面对应的里程是不是存在
	public Distance findByDistance(Float distance) {
		// TODO Auto-generated method stub
		return distanceMapper.findByDistance(distance);
	}
	
	
	

}
