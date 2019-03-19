package com.zttx.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttx.po.Employee;
import com.zttx.po.EmployeeCustom;
import com.zttx.service.PositionService;
import com.zttx.utils.DateFormatUtils;
import com.zttx.vo.Message;

@Controller
@RequestMapping("/position")
public class PositionController {
	
	@Autowired
	private PositionService positionService;
	
	/**
	 * 查询每个基站里的所有施工人员
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/empLocation")
	public @ResponseBody Message empLocation() throws Exception{
		Message msg=new Message();
		List<EmployeeCustom> eclist=null;
		//生成当天时间的字符串，如'2017-12-03'
		Date dt=new Date();
		String lasttime=DateFormatUtils.format3(dt);
		Employee emplastime=new Employee();
		emplastime.setLasttime(lasttime);
		
		//查询基站数量
		int RFIDnum=positionService.findRFIDNum();
		
		//查询所有施工人员信息
		List<Employee> empList=positionService.findAllEmployee(emplastime);	
		
		
		
		if(empList!=null){
			//基站--人员
			eclist=new ArrayList<EmployeeCustom>(RFIDnum);
			
			
			for(int i=0;i<RFIDnum;i++){
				//基站信息以及该基站的人员列表
				EmployeeCustom ec = new EmployeeCustom();
				List<Employee> employees = new ArrayList<Employee>();
				
				ec.setBrushmach(i+1);
				ec.setEmploylist(employees);
				eclist.add(ec);
			}
			//遍历施工人员，查询每个施工人员的位置
			for(int i=0;i<empList.size();i++){
				Integer brushmach=positionService.findEmpRFID(empList.get(i).getCardno());
				if(brushmach != null){
					eclist.get(brushmach-1).getEmploylist().add(empList.get(i));	
				}		
			}
			msg.setCode(100);
			msg.setMsg("查询人员定位成功！");
			msg.setObj(eclist);
		}
		
		return msg;
	}
	
	@RequestMapping("/empDetail")
	public @ResponseBody Message empDetail() throws Exception{
		
		return null;
	}
	
}
