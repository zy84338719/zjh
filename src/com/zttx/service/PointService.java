package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.PointMapper;
import com.zttx.po.Point;

@Transactional
@Service(value="pointService")
public class PointService {
	
	@Autowired
	private PointMapper ponitMapper;
	
	//查询最新截面信息
	public List<Point> findPointBySectionId(String sectionid) throws Exception{
		return ponitMapper.findPointBySectionId(sectionid);
	}

}
