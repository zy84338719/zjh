package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.SectionMapper;
import com.zttx.po.Point;
import com.zttx.po.Section;
import com.zttx.po.SectionCustom;
import com.zttx.po.SectionVolume;
import com.zttx.vo.SectionQueryVo;

@Transactional
@Service(value="sectionService")
public class SectionService {
	
	@Autowired
	private SectionMapper sectionMapper;
	
	//查询最新截面信息
	public List<SectionCustom> findNewestSection() throws Exception{
		return sectionMapper.findNewestSection();
	}

	//根据里程数查询对应的截面信息
	public List<SectionCustom> findSectionByDistance(SectionQueryVo sectionQueryVo) {
		// TODO Auto-generated method stub
		return sectionMapper.findSectionByDistance(sectionQueryVo);
	}

	//查询对应里程段的超欠挖土方量
	public SectionVolume findVolumeByDistance(SectionQueryVo sectionQueryVo) {
		// TODO Auto-generated method stub
		return sectionMapper.findVolumeByDistance(sectionQueryVo);
	}

	//查询对应里程段中最早一次扫描数据
	public List<SectionCustom> findOldSectionByDistance(
			SectionQueryVo sectionQuery_new) {
		// TODO Auto-generated method stub
		return sectionMapper.findOldSectionByDistance(sectionQuery_new);
	}

	/**
	 * 下面都是数据对接相关方法
	 * @param section
	 * @return
	 */
	public Section isExistSection(Section section) throws Exception{
		Section isExist = sectionMapper.isExistSection(section);
		return isExist;
		
	}

	public Integer updateSection_cop(Section section) throws Exception{
		
		return sectionMapper.updateSection_cop(section);
	}

	public Integer saveSection_cop(Section section) throws Exception{
		
		return sectionMapper.saveSection_cop(section);
	}

	//根据_sectionid查询section
	public Section findSectionBy_sectionid(String _sectionid) {
		// TODO Auto-generated method stub
		return sectionMapper.findSectionBy_sectionid(_sectionid);
	}

	//删除某截面的所有点
	public void deletePoints(String sectionid) {
		// TODO Auto-generated method stub
		sectionMapper.deletePoints(sectionid);
	}

	//保存点信息
	public void savePoint(Point point) {
		// TODO Auto-generated method stub
		sectionMapper.savePoint(point);
	}
	//根据distance和stage查询section是否存在
	public Section isExistSectionByDistanceAndStage(Section section) {
		return sectionMapper.isExistSectionByDistanceAndStage(section);
	}

	public void deleteSection(Section section) {
		// TODO Auto-generated method stub
		sectionMapper.deleteSection(section);
	}

}
