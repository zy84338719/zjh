package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Point;
import com.zttx.po.Section;
import com.zttx.po.SectionCustom;
import com.zttx.po.SectionVolume;
import com.zttx.vo.SectionQueryVo;

public interface SectionMapper {
	//查找最新截面信息
	public List<SectionCustom> findNewestSection()throws Exception;

	//根据里程数查询对应的截面信息
	public List<SectionCustom> findSectionByDistance(SectionQueryVo sectionQueryVo);

	//查询对应里程段的超欠挖土方量
	public SectionVolume findVolumeByDistance(SectionQueryVo sectionQueryVo);

	//查询对应里程段中最早一次扫描数据
	public List<SectionCustom> findOldSectionByDistance(
			SectionQueryVo sectionQuery_new);

	//查询是否存在截面
	public Section isExistSection(Section section);

	//更新界面信息
	public Integer updateSection_cop(Section section);

	//保存截面信息
	public Integer saveSection_cop(Section section);

	//根据_Sectionid查询section
	public Section findSectionBy_sectionid(String _sectionid);

	//删除某截面的所有点
	public void deletePoints(String sectionid);

	public void savePoint(Point point);

	public Section isExistSectionByDistanceAndStage(Section section);
	
	//删除某个截面：根据sectionid
	public void deleteSection(Section section);

}
