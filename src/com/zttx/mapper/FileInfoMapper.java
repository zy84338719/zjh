package com.zttx.mapper;

import java.util.List;

import com.zttx.po.File_info;
import com.zttx.po.Point;

public interface FileInfoMapper {

	File_info findById(String fileid);

	Integer save(File_info file_info);

	Integer update(File_info file_info);
	
	
}
