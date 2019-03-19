package com.zttx.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zttx.mapper.FileInfoMapper;
import com.zttx.po.File_info;

@Transactional
@Service(value="fileinfoService")
public class FileInfoService {
	
	@Autowired
	private FileInfoMapper fileInfoMapper;

	public File_info findById(String fileid) {
		// TODO Auto-generated method stub
		return fileInfoMapper.findById(fileid);
	}

	public Integer save(File_info file_info) {
		// TODO Auto-generated method stub
		return fileInfoMapper.save(file_info);
	}

	public Integer update(File_info file_info) {
		// TODO Auto-generated method stub
		return fileInfoMapper.update(file_info);
	}
	
	

}
