package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Point;

public interface PointMapper {
	
	//根据截面ID查询点信息
	public List<Point> findPointBySectionId(String sectionid);

}
