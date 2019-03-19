package com.zttx.mapper;

import java.util.List;

import com.zttx.po.Employee;

public interface PositionMapper {
	
	//查询所有人员
	public List<Employee> findAllEmployee(Employee employee) throws Exception;
	
	//查询共有多少个基站
	public int findRFIDNum() throws Exception;
	
	//查询该员工在哪个基站
	public Integer findEmpRFID(String cardno) throws Exception;

}
