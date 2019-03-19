package com.zttx.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.zttx.mapper.PositionMapper;
import com.zttx.po.Employee;

@Service("positionService")
public class PositionService {
	
	@Autowired
	private PositionMapper positionMapper;
	
	//查询所有人员
	public List<Employee> findAllEmployee(Employee employee) throws Exception{
		return positionMapper.findAllEmployee(employee);
	}
		
	//查询共有多少个基站
	public int findRFIDNum() throws Exception{
		return positionMapper.findRFIDNum();
	}
	
	//查询该员工在哪个基站
	public Integer findEmpRFID(String cardno) throws Exception{
		return positionMapper.findEmpRFID(cardno);
	}
	
}
