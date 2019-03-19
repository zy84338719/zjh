package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Distance;
import com.zttx.po.DistanceGroupVolumeCustom;
import com.zttx.vo.DistanceQueryVo;
import com.zttx.vo.SectionQueryVo;

public interface DistanceMapper {

	//根据里程段查询各班组的超欠挖信息
	List<DistanceGroupVolumeCustom> queryGroupVolumeByDistance(
			SectionQueryVo sectionQueryVo);

	//处理超欠挖状态
	Integer handleOverOwe(DistanceQueryVo distanceQueryVo);

	//多条件查询各班组超欠挖信息
	List<DistanceGroupVolumeCustom> queryGroupByType(
			DistanceQueryVo distanceQueryVo);

	//更新里程
	Integer updateDistance(Distance distance);

	//保存里程
	Integer saveDistance(Distance distance);
	
	//查询工程是否存在
	Distance findDistance(Distance distance);

	//更新里程方量
	void updateDistanceVolume(Distance own_distance);

	//查询截面对应的里程记录是否存在
	Distance findByDistance(Float distance);
	
	

}
