package com.zttx.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.text.ParseException;
import java.util.Arrays;
import java.util.Date;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.request.MyHttpRequest;
import com.zttx.po.DataNotice;
import com.zttx.po.DataResponse;
import com.zttx.po.Distance;
import com.zttx.po.File_info;
import com.zttx.po.Group;
import com.zttx.po.Point;
import com.zttx.po.QualityAuthority;
import com.zttx.po.SecondAuthority;
import com.zttx.po.Section;
import com.zttx.po.User;
import com.zttx.po.UserAuthority;
import com.zttx.service.AuthorityService;
import com.zttx.service.AuthorityService4;
import com.zttx.service.DistanceService;
import com.zttx.service.FileInfoService;
import com.zttx.service.GroupService;
import com.zttx.service.GroupService4;
import com.zttx.service.SectionService;
import com.zttx.service.UserService;
import com.zttx.service.UserService4;
import com.zttx.utils.DateFormatUtils;
import com.zttx.utils.GUIDSeqGenerator;

/**
 * 数据Controller
 * 
 */
@Controller
@RequestMapping("/data")
public class DataController {

	@Autowired
	private UserService userService;
	
	@Autowired
	private UserService4 userService4;
	
	@Autowired
	private GroupService groupService;
	
	@Autowired
	private GroupService4 groupService4;

	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private AuthorityService4 authorityService4;
	
	@Autowired
	private SectionService sectionService;
	
	@Autowired
	private DistanceService distanceService;
	
	@Autowired
	private FileInfoService fileInfoService;
	
	/**
	 * 测试
	 * 
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/notice")
	public @ResponseBody
	DataResponse notice(@RequestBody DataNotice dataNotice) throws Exception {
		DataResponse dataResponse = new DataResponse();
		String noticeType = dataNotice.getNoticeType();
		String id = dataNotice.getId();
		
		Boolean result = false;
		// [1]前往获取用户数据
		if (noticeType.equals("user")) {
			result = getUser(id);
			if(result){
				//再更新权限
				result = getPermission(id);
			}
		}else
		// [2]前往获取班组数据
		if (noticeType.equals("group")) {
			result = getGroup(id);
		}
		// [3]前往获取权限数据
		if (noticeType.equals("permission")) {
//			if(id.contains(",")){
//				String[] ids = id.split(",");
//				for(int i=0;i<ids.length;i++){
//					result = getPermission(ids[i]);
//				}
//			}else{
//				result = getPermission(id);
//			}
			System.out.println("传进来的ID");
			result = getPermission(id);
		}
		
		// [4]前往获取截面数据
		if (noticeType.equals("section")) {
			System.out.println("传进来的ID");
			result = getSection(id);
		}
		
		// [5]前往获取项目数据
		if (noticeType.equals("distance")) {
			System.out.println("传进来的DistanceID："+id);
			result = getDistance(id);
		}
		// [6]前往获取技术交底数据
		if (noticeType.equals("technicalbasis")) {
			System.out.println("传进来的ID");
			result = getTechnology(id);
		}
		
		//测试
		if(noticeType.equals("test")){
			System.out.println("进来了");
			Group group = groupService4.findBy_groupid("4E440200-05D7-4EE1-B7D5-8946A4BCE832-00108");
			System.out.println(group.getGroupname());
		}
		
		if (result) {
			dataResponse.setIsSuccess("true");
			dataResponse.setMessage("成功");
			dataResponse.setResultCode(100);
			return dataResponse;
		} else {
			dataResponse.setIsSuccess("false");
			dataResponse.setMessage("失败");
			dataResponse.setResultCode(101);
			return dataResponse;
		}
	}

	//获取用户数据
	private boolean getUser(String _userid) throws Exception {

		String result = MyHttpRequest.httpURLConnectionPOST(
				"http://219.134.241.186:8080/api/app/GetData",
				"getType=user&Id=" + _userid);
		JSONObject jsonObject = JSONObject.fromObject(result);
		Integer resultCode = jsonObject.getInt("resultCode");
		System.out.println(jsonObject);
		if (resultCode == 200) {
			// 获取成功
			JSONObject userObj = jsonObject.getJSONObject("data");
			System.out.println(userObj);
			
			if(userObj.toString().equals("null")){
 	    		return false;
 	    	}
			
			_userid = userObj.getString("userid");
			String loginname = userObj.getString("loginname");
			String password = userObj.getString("password");
			String _groupid = userObj.getString("groupid");
			
			String username = userObj.getString("username");
			String telphone = userObj.getString("telphone");

			User user = new User();
			user.set_userid(_userid);
			user.setLoginname(loginname);
			user.setPassword(password);
			user.setUsername(username);
			user.setTelphone(telphone);
			
			Group group = groupService.findBy_groupid(_groupid);
			if(group != null){
				//一标的班组
				user.setGroupid(group.getGroupid());
				
				// 判断数据库中是否存在，存在则更新，不存在则插入
				Boolean isExist = userService.isExistUser(_userid);
				if (isExist) {
					// 更新
					Integer result1 = userService.updateUser_cop(user);
					if (result1 > 0) {
						// 更新成功
						return true;
					} else {
						// 更新失败
						return false;
					}
				} else {
					// 插入
					String userid = GUIDSeqGenerator.getInstance().newGUID();
					user.setUserid(userid);
					Integer result1 = userService.saveUser_cop(user);
					if (result1 > 0) {
						// 插入成功
						return true;
					} else {
						// 插入失败
						return false;
					}
				}
			}else{
				//四标的班组
				group = groupService4.findBy_groupid(_groupid);
				if(group != null){
					user.setGroupid(group.getGroupid());
					// 判断数据库中是否存在，存在则更新，不存在则插入
					Boolean isExist = userService4.isExistUser(_userid);
					if (isExist) {
						// 更新
						Integer result1 = userService4.updateUser_cop(user);
						if (result1 > 0) {
							// 更新成功
							return true;
						} else {
							// 更新失败
							return false;
						}
					} else {
						// 插入
						String userid = GUIDSeqGenerator.getInstance().newGUID();
						user.setUserid(userid);
						Integer result1 = userService4.saveUser_cop(user);
						if (result1 > 0) {
							// 插入成功
							return true;
						} else {
							// 插入失败
							return false;
						}
					}
				}
			}
			
		}
		return false;
	}

	//获取班组数据
	private boolean getGroup(String _groupid) throws Exception {

		String result = MyHttpRequest.httpURLConnectionPOST(
				"http://219.134.241.186:8080/api/app/GetData",
				"getType=group&Id=" + _groupid);
		JSONObject jsonObject = JSONObject.fromObject(result);
		Integer resultCode = jsonObject.getInt("resultCode");
		if (resultCode == 200) {
			// 获取成功
 	    	JSONObject groupObj = jsonObject.getJSONObject("data");
 	    	if(groupObj.toString().equals("null")){
 	    		return false;
 	    	}
 	    	
 	    	_groupid = groupObj.getString("groupid");
 	    	if(_groupid == null){
 	    		return false;
 	    	}
 	    	
 	    	String groupname = groupObj.getString("groupname");
 	    	String code = groupObj.getString("code");
 	    	
 	    	Group group = new Group();
 	    	group.set_groupid(_groupid);
 	    	group.setGroupname(groupname);
 	    	group.setCode(code);
 	    	
 	    	if(code.startsWith("001.001") || code.equals("001")){
 	    		System.out.println("一标的班组信息进来了："+code);
 	    		//一标
 	    		//判断数据库中是否存在，存在则更新，不存在则插入
 				Boolean isExist = groupService.isExistGroup(_groupid);
 				if (isExist) {
 					// 更新
 					Integer result1 = groupService.updateGroup_cop(group);
 					if (result1 > 0) {
 						// 更新成功
 						return true;
 					} else {
 						// 更新失败
 						return false;
 					}
 				} else {
 					// 插入
 					String groupid = GUIDSeqGenerator.getInstance().newGUID();
 					group.setGroupid(groupid);
 					Integer result1 = groupService.saveGroup_cop(group);
 					if (result1 > 0) {
 						// 插入成功
 						return true;
 					} else {
 						// 插入失败
 						return false;
 					}
 				}
 	    	}else
 	    		if(code.startsWith("001.002")){
 	    			System.out.println("四标的班组信息进来了："+code);
 	    			//四标
 	    			//判断数据库中是否存在，存在则更新，不存在则插入
	 	   			Boolean isExist = groupService4.isExistGroup(_groupid);
	 	   			if (isExist) {
	 	   				// 更新
	 	   				Integer result1 = groupService4.updateGroup_cop(group);
	 	   				if (result1 > 0) {
	 	   					// 更新成功
	 	   					return true;
	 	   				} else {
	 	   					// 更新失败
	 	   					return false;
	 	   				}
	 	   			} else {
	 	   				// 插入
	 	   				String groupid = GUIDSeqGenerator.getInstance().newGUID();
	 	   				group.setGroupid(groupid);
	 	   				Integer result1 = groupService4.saveGroup_cop(group);
	 	   				if (result1 > 0) {
	 	   					// 插入成功
	 	   					return true;
	 	   				} else {
	 	   					// 插入失败
	 	   					return false;
	 	   				}
	 	   			}
 	    		}
 	    	
 	    	
		}
		return false;
	}
	
	//获取权限信息
	private boolean getPermission(String _userids) throws Exception {
		System.out.println(_userids);
		String result = MyHttpRequest.httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=permission&Id="+_userids);
    	JSONObject jsonObject = JSONObject.fromObject(result); 
    	
    	Integer resultCode=jsonObject.getInt("resultCode");
    	System.out.println("权限："+jsonObject.toString());
    	if(resultCode == 200){//resultCode==200  if
 	    	 
 	    	//获取成功
 	    	JSONArray permissionArr = jsonObject.getJSONArray("data");
 	    	
 	    	for(int i=0;i<permissionArr.size();i++){//for  start
 	    		//获取每个对象
 	    		String topauthority = "";
 	 	    	String secondauthority = "";
 	 	    	
 	    		JSONObject user_permission = permissionArr.getJSONObject(i);
 	    		System.out.println("第"+i+"个："+user_permission.toString());
// 	    		
 	    		String _userid = user_permission.getString("userId");
 	    		//查询用户
 	 	    	User user = userService.findBy_userid(_userid);
 	 	    	
 	 	    	//如果用户不存在，直接返回
 	 	    	if(user == null){
 	 	    		//不是一标的
 	 	    		user = userService4.findBy_userid(_userid);
 	 	    		if( user== null){
 	 	    			//也不是四标的
 	 	    			continue;
 	 	    		}else{
 	 	    			//是四标的
 	 	 	 	    	//删除质量溯源相关权限
 	 	 	 	    	UserAuthority queryUserAuthority = new UserAuthority();
 	 	 	 	    	queryUserAuthority.setUserid(user.getUserid());
 	 	 	  	    	userService4.deleteQualityAuthority(queryUserAuthority);
 	 	 	  	    	
 	 	 	    		String permission = user_permission.getString("permission");
 	 	 	    		if(permission.equals("")){
 	 	 	    			continue;
 	 	 	    		}
 	 	 	    		String[] permissions = permission.split(";");
 	 	 	    		
 	 	 	    		for(int j=0;j<permissions.length;j++){//内层权限循环 start

 	 	 	 	    		//解析权限信息
 	 	 	 	    		String[] permission_num = permissions[j].split("_");
 	 	 	 	    		System.out.print(j+"权限名："+permission_num[1]);
 	 	 	 	    		if(permission_num[0].equals("00")){//一级权限开始  if
 	 	 	 	    			//一级权限
 	 	 	 	    			if(permission_num[1].equals("3DScan")){
 	 	 	 	    				//topauthority += "1;";
 	 	 	 	    			}else
 	 	 	 	    			if(permission_num[1].equals("QualityTrace")){
 	 	 	 	 	    			//topauthority += "2;";
 	 	 	 	 	    		}else
 	 	 	 	 	    		if(permission_num[1].equals("WorkList")){
 	 	 	 	 	 	    		topauthority += "3;";
 	 	 	 	 	 	    	}else
 	 	 	 	 	 	    	if(permission_num[1].equals("BuildProgress")){
 	 	 	 	 	 	 	    	//topauthority += "4;";
 	 	 	 	 	 	 	    }else
 	 	 	 	 	 	 	    if(permission_num[1].equals("CarLocation")){
 	 	 	 	 	 	 	 	   // topauthority += "5;";
 	 	 	 	 	 	 	 	}else
 	 	 		 	 	 	  	if(permission_num[1].equals("PersonLocation")){
 	 	 		 	 	 	 	 	 //topauthority += "6;";
 	 	 		 	 	 	 	 }else
 	 		 			 	 	 	 if(permission_num[1].equals("VideoMonitor")){
 	 		 			 	 	 	 	// topauthority += "7;";
 	 		 			 	 	 	 }else
 	 			 				 	 	 if(permission_num[1].equals("TechnicalDisclosure")){
 	 			 				 	 	 	// topauthority += "8;";
 	 			 				 	 	 }
 	 	 	 	    		}else{//一级权限结束，二级权限开始
 	 	 	 	 	    			//二级权限
 	 	 	 	    				//3D扫描二级权限
 	 	 	 	 	    			if(permission_num[1].equals("Statistics")){
 	 	 	 	 	    				//secondauthority += "101;";
 	 	 	 	 	    			}else
 	 	 	 	 	    				if(permission_num[1].equals("Push")){
 	 	 	 	 	    					//secondauthority += "102;";
 	 	 	 	 	 	    			}else
 	 	 	 	 	 	    			if(permission_num[1].equals("Query")){
 	 	 	 	 	 	    				//secondauthority += "103;";
 	 	 	 	 	 	 	    		}else
 	 	 	 	 	 	 	    			if(permission_num[1].equals("Dealing")){
 	 	 	 	 	 	 	    				//secondauthority += "104;";
 	 	 	 	 	 	 	 	    	}else
 	 	 	 	 	 	 	 	    		if(permission_num[1].equals("Set")){
 	 	 	 	 	 	 	 	    			//secondauthority += "105;";
 	 	 	 	 	 	 	 	 	    	}else
 	 	 	 	 	 	 	 	 	    	//质量溯源二级权限
 	 	 	 		 	 	 	  	    	if(permission_num[1].equals("MaterialEntering")){
// 	 	 	 		 	 	 	  	    		secondauthority += "201;";
// 	 	 	 		 	 	 	  	    		
// 	 	 	 		 	 	 	  	    		//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("201");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 	 		 	 	 	 	 	    }else
 	 	 	 			 	 	 	  	    if(permission_num[1].equals("MaterialCheck")){
// 	 	 	 			 	 	 	  	    	secondauthority += "202;";
// 	 	 	 			 	 	 	  	    	
// 	 	 	 			 	 	 	  	    	//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("202");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 	 			 	 	 	 	 	}else
 	 	 	 				 	 	 	  	if(permission_num[1].equals("MaterialApply")){
// 	 	 	 				 	 	 	  	    secondauthority += "203;";
// 	 	 	 				 	 	 	  	    
// 	 	 	 				 	 	 	  	    //查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("203");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 	 				 	 	 	 	}else
 	 	 		 				 	 	 	if(permission_num[1].equals("MaterialAudit")){
// 	 	 		 				 	 	 	 	secondauthority += "204;";
// 	 	 		 				 	 	 	 	
// 	 	 		 				 	 	 	 	//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("204");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 		 		 		 	 	 	}else
 	 	 		 		 			 	 	if(permission_num[1].equals("MaterialProvide")){
// 	 	 		 		 			 	 	 	secondauthority += "205;";
// 	 	 		 		 			 	 	 	//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("205");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 		 		 			 	 	}else
 	 	 		 		 				 	if(permission_num[1].equals("ComponentProcessing")){
// 	 	 		 		 				 	 	secondauthority += "206;";
// 	 	 		 		 				 	 	//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("206");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 		 		 				 	}else
 	 	 		 		 	 				if(permission_num[1].equals("ComponentAudit")){
// 	 	 		 		 	 				 	secondauthority += "207;";
// 	 	 		 		 	 				 	//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("207");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 		 		 	 				}else
 	 	 		 		 		 			if(permission_num[1].equals("ComponentProvide")){
// 	 	 		 		 		 				secondauthority += "208;";
// 	 	 		 		 		 				//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("208");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 		 		 		 		 	}else
 	 	 		 		 		 		 	if(permission_num[1].equals("ComponentInstall")){
// 	 	 		 		 		 		 		secondauthority += "209;";
// 	 	 		 		 		 		 		//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("209");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 		 		 		 		 	}else
 	 	 		 		 		 		 	if(permission_num[1].equals("ComponentCheck")){
// 	 	 		 		 		 		 		secondauthority += "210;";
// 	 	 		 		 		 		 		//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("210");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 		 		 		 		 	}else
 	 	 			 		 		 		if(permission_num[1].equals("SubQualityTrace")){
// 	 	 			 		 		 		  	secondauthority += "211;";
// 	 	 			 		 		 		  	//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("211");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 			 		 		 		}else
 	 	 			 		 		 		if(permission_num[1].equals("MaterialTrace")){
// 	 	 			 		 		 			secondauthority += "212;";
// 	 	 			 		 		 			//查询对应二级权限详情
// 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("212");
// 	 	 		 		 	 	 	  	    	
// 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 			 		 		 		}else
 	 	 	 			 		 		 		if(permission_num[1].equals("prewarning")){
// 	 	 	 			 		 		 			secondauthority += "213;";
// 	 	 	 			 		 		 			//查询对应二级权限详情
// 	 	 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService4.findSecondAuthorityBySecondId("213");
// 	 	 	 		 		 	 	 	  	    	
// 	 	 	 	 		 	 	 	  	    		//封装插入
// 	 	 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
// 	 	 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
// 	 	 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
// 	 	 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
// 	 	 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
// 	 	 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
// 	 	 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 	 	 			 		 		 		}
 	 	 			 		 		 		else
 	 	 			 		 		 			//进度管理二级权限
 	 	 					 		 		 	if(permission_num[1].equals("ConstructionCreate")){
 	 	 					 		 		 		//secondauthority += "401;";
 	 	 					 		 		 	}else
 	 	 					 		 		 	if(permission_num[1].equals("ProgressEntry")){
 	 	 					 		 		 		//secondauthority += "402;";
 	 	 						 		 		}else
 	 	 						 		 		if(permission_num[1].equals("ProgressQuery")){
 	 	 						 		 			//secondauthority += "403;";
 	 	 						 		 		}else
 	 	 						 		 			//技术交底二级权限
 	 	 						 		 		 	if(permission_num[1].equals("TechnicalDisclosureInput")){
 	 	 						 		 		 		//secondauthority += "801;";
 	 	 						 		 		 	}else
 	 	 						 		 		 	if(permission_num[1].equals("TechnicalDisclosureCheck")){
 	 	 						 		 		 		//secondauthority += "802;";
 	 	 							 		 		}else
 	 	 							 		 		if(permission_num[1].equals("TechnicalDisclosureDistribute")){
 	 	 							 		 			//secondauthority += "803;";
 	 	 							 		 		}else
 	 	 							 		 		if(permission_num[1].equals("TechnicalDisclosureSignature")){
 	 	 							 		 		 	//secondauthority += "804;";
 	 	 							 		 		}else
 	 	 							 		 		if(permission_num[1].equals("TechnicalDisclosureQuery")){
 	 	 							 		 		 	//secondauthority += "805;";
 	 	 								 		 	}else
 	 	 								 		 	if(permission_num[1].equals("TechnicalDisclosureStatistics")){
 	 	 								 		 		//secondauthority += "806;";
 	 	 								 		 	}
 	 	 	 	 	    			
 	 	 	 	 	    		}
 	 	 	    		}//内层循环结束 end
 	 	 	    		
 	 	 	    		System.out.println("拼接权限"+topauthority);
 	 		 	 	    	System.out.println(secondauthority);
 	 		 	    		if(topauthority.length()>0){
 	 		 	 	    		
 	 		 	 	    		topauthority = topauthority.substring(0,topauthority.length()-1);
 	 		 	 	    		topauthority = sort(topauthority);
 	 		 	 	    	}
 	 		 	 	    	if(secondauthority.length()>0){
 	 		 	 	    		
 	 		 	 	    		secondauthority = secondauthority.substring(0,secondauthority.length()-1);
 	 		 	 	    		secondauthority = sort(secondauthority);
 	 		 	 	    	}
 	 		 	 	    	
 	 		 	 	    	
 	 		 	 	    	
 	 		 	 	    	//封装权限
 	 		 	 	    	UserAuthority userAuthority = new UserAuthority();
 	 		 	 	    	userAuthority.setUserid(user.getUserid());
 	 		 	 	    	userAuthority.setTopauthority(topauthority);
 	 		 	 	    	userAuthority.setSecondauthority(secondauthority);
 	 		 	 	    	
 	 		 	 	    	//更新用户权限:先判断是否存在，若存在，更新；若不存在，则保存
 	 		 	 	    	Boolean isExist = userService4.isExistPermission(user.getUserid());
 	 		 	 	    	if(isExist){
 	 		 	 	    		//存在，更新
 	 		 	 	    		Integer result1 = userService4.updatePermission(userAuthority);
 	 		 	 	    		if(result1 > 0){
 	 		 	 	    			//return true;
 	 		 	 	    		}else{
 	 		 	 	    			//return false;
 	 		 	 	    		}
 	 		 	 	    	}else{
 	 		 	 	    		//不存在，保存
 	 		 	 	    		Integer result1 = userService4.savePermission(userAuthority);
 	 		 	 	    		if(result1 > 0){
 	 		 	 	    			//return true;
 	 		 	 	    		}else{
 	 		 	 	    			//return false;
 	 		 	 	    		}
 	 		 	 	    	}
 	 	    		}
 	 	    	}
 	 	    	//是一标的
 	 	    	//删除质量溯源相关权限
 	 	    	UserAuthority queryUserAuthority = new UserAuthority();
 	 	    	queryUserAuthority.setUserid(user.getUserid());
 	  	    	userService.deleteQualityAuthority(queryUserAuthority);
 	  	    	
 	    		String permission = user_permission.getString("permission");
 	    		if(permission.equals("")){
 	    			continue;
 	    		}
 	    		String[] permissions = permission.split(";");
 	    		
 	    		for(int j=0;j<permissions.length;j++){//内层权限循环 start

 	 	    		//解析权限信息
 	 	    		String[] permission_num = permissions[j].split("_");
 	 	    		System.out.print(j+"权限名："+permission_num[1]);
 	 	    		if(permission_num[0].equals("00")){//一级权限开始  if
 	 	    			//一级权限
 	 	    			if(permission_num[1].equals("3DScan")){
 	 	    				topauthority += "1;";
 	 	    			}else
 	 	    			if(permission_num[1].equals("QualityTrace")){
 	 	 	    			topauthority += "2;";
 	 	 	    		}else
 	 	 	    		if(permission_num[1].equals("WorkList")){
 	 	 	 	    		topauthority += "3;";
 	 	 	 	    	}else
 	 	 	 	    	if(permission_num[1].equals("BuildProgress")){
 	 	 	 	 	    	topauthority += "4;";
 	 	 	 	 	    }else
 	 	 	 	 	    if(permission_num[1].equals("CarLocation")){
 	 	 	 	 	 	    topauthority += "5;";
 	 	 	 	 	 	}else
 		 	 	 	  	if(permission_num[1].equals("PersonLocation")){
 		 	 	 	 	 	 topauthority += "6;";
 		 	 	 	 	 }else
	 			 	 	 	 if(permission_num[1].equals("VideoMonitor")){
	 			 	 	 	 	 topauthority += "7;";
	 			 	 	 	 }else
		 				 	 	 if(permission_num[1].equals("TechnicalDisclosure")){
		 				 	 	 	 topauthority += "8;";
		 				 	 	 }
 	 	    			
 	 	    		}else{//一级权限结束，二级权限开始
 	 	 	    			//二级权限
 	 	    				//3D扫描二级权限
 	 	 	    			if(permission_num[1].equals("Statistics")){
 	 	 	    				secondauthority += "101;";
 	 	 	    			}else
 	 	 	    				if(permission_num[1].equals("Push")){
 	 	 	    					secondauthority += "102;";
 	 	 	 	    			}else
 	 	 	 	    			if(permission_num[1].equals("Query")){
 	 	 	 	    				secondauthority += "103;";
 	 	 	 	 	    		}else
 	 	 	 	 	    			if(permission_num[1].equals("Dealing")){
 	 	 	 	 	    				secondauthority += "104;";
 	 	 	 	 	 	    	}else
 	 	 	 	 	 	    		if(permission_num[1].equals("Set")){
 	 	 	 	 	 	    			secondauthority += "105;";
 	 	 	 	 	 	 	    	}else
 	 	 	 	 	 	 	    	//质量溯源二级权限
 	 		 	 	 	  	    	if(permission_num[1].equals("MaterialEntering")){
 	 		 	 	 	  	    		secondauthority += "201;";
 	 		 	 	 	  	    		
 	 		 	 	 	  	    		//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("201");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 		 	 	 	 	 	    }else
 	 			 	 	 	  	    if(permission_num[1].equals("MaterialCheck")){
 	 			 	 	 	  	    	secondauthority += "202;";
 	 			 	 	 	  	    	
 	 			 	 	 	  	    	//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("202");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 			 	 	 	 	 	}else
 	 				 	 	 	  	if(permission_num[1].equals("MaterialApply")){
 	 				 	 	 	  	    secondauthority += "203;";
 	 				 	 	 	  	    
 	 				 	 	 	  	    //查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("203");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 				 	 	 	 	}else
 		 				 	 	 	if(permission_num[1].equals("MaterialAudit")){
 		 				 	 	 	 	secondauthority += "204;";
 		 				 	 	 	 	
 		 				 	 	 	 	//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("204");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 		 		 		 	 	 	}else
 		 		 			 	 	if(permission_num[1].equals("MaterialProvide")){
 		 		 			 	 	 	secondauthority += "205;";
 		 		 			 	 	 	//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("205");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 		 		 			 	 	}else
 		 		 				 	if(permission_num[1].equals("ComponentProcessing")){
 		 		 				 	 	secondauthority += "206;";
 		 		 				 	 	//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("206");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 		 		 				 	}else
 		 		 	 				if(permission_num[1].equals("ComponentAudit")){
 		 		 	 				 	secondauthority += "207;";
 		 		 	 				 	//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("207");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 		 		 	 				}else
 		 		 		 			if(permission_num[1].equals("ComponentProvide")){
 		 		 		 				secondauthority += "208;";
 		 		 		 				//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("208");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 		 		 		 		 	}else
 		 		 		 		 	if(permission_num[1].equals("ComponentInstall")){
 		 		 		 		 		secondauthority += "209;";
 		 		 		 		 		//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("209");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 		 		 		 		 	}else
 		 		 		 		 	if(permission_num[1].equals("ComponentCheck")){
 		 		 		 		 		secondauthority += "210;";
 		 		 		 		 		//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("210");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 		 		 		 		 	}else
 			 		 		 		if(permission_num[1].equals("SubQualityTrace")){
 			 		 		 		  	secondauthority += "211;";
 			 		 		 		  	//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("211");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 			 		 		 		}else
 			 		 		 		if(permission_num[1].equals("MaterialTrace")){
 			 		 		 			secondauthority += "212;";
 			 		 		 			//查询对应二级权限详情
 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("212");
 		 		 	 	 	  	    	
 	 		 	 	 	  	    		//封装插入
 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 			 		 		 		}else
 	 			 		 		 		if(permission_num[1].equals("prewarning")){
 	 			 		 		 			secondauthority += "213;";
 	 			 		 		 			//查询对应二级权限详情
 	 	 		 	 	 	  	    		SecondAuthority secAuthority = authorityService.findSecondAuthorityBySecondId("213");
 	 		 		 	 	 	  	    	
 	 	 		 	 	 	  	    		//封装插入
 	 	 		 	 	 	  	    		QualityAuthority qualityAuthority = new QualityAuthority();
 	 		 		 	 					qualityAuthority.setS_functionname(secAuthority.getFunctionname());
 	 		 		 	 					qualityAuthority.setS_functionurl(secAuthority.getFunctionurl());
 	 		 		 	 					qualityAuthority.setS_iconurl(secAuthority.getIconurl());
 	 		 		 	 					qualityAuthority.setS_topid(secAuthority.getTopid());
 	 		 		 	 					qualityAuthority.setS_userid(user.getUserid());
 	 		 		 	 					userService.insertIntoQualityAuthority(qualityAuthority);
 	 			 		 		 		}
 			 		 		 		else
 			 		 		 			//进度管理二级权限
 					 		 		 	if(permission_num[1].equals("ConstructionCreate")){
 					 		 		 		secondauthority += "401;";
 					 		 		 	}else
 					 		 		 	if(permission_num[1].equals("ProgressEntry")){
 					 		 		 		secondauthority += "402;";
 						 		 		}else
 						 		 		if(permission_num[1].equals("ProgressQuery")){
 						 		 			secondauthority += "403;";
 						 		 		}else
 						 		 			//技术交底二级权限
 						 		 		 	if(permission_num[1].equals("TechnicalDisclosureInput")){
 						 		 		 		secondauthority += "801;";
 						 		 		 	}else
 						 		 		 	if(permission_num[1].equals("TechnicalDisclosureCheck")){
 						 		 		 		secondauthority += "802;";
 							 		 		}else
 							 		 		if(permission_num[1].equals("TechnicalDisclosureDistribute")){
 							 		 			secondauthority += "803;";
 							 		 		}else
 							 		 		if(permission_num[1].equals("TechnicalDisclosureSignature")){
 							 		 		 	secondauthority += "804;";
 							 		 		}else
 							 		 		if(permission_num[1].equals("TechnicalDisclosureQuery")){
 							 		 		 	secondauthority += "805;";
 								 		 	}else
 								 		 	if(permission_num[1].equals("TechnicalDisclosureStatistics")){
 								 		 		secondauthority += "806;";
 								 		 	}
 	 	 	    			
 	 	 	    		}
 	    		}//内层循环结束 end
 	    		
 	    		System.out.println("拼接权限"+topauthority);
	 	 	    	System.out.println(secondauthority);
	 	    		if(topauthority.length()>0){
	 	 	    		
	 	 	    		topauthority = topauthority.substring(0,topauthority.length()-1);
	 	 	    		topauthority = sort(topauthority);
	 	 	    	}
	 	 	    	if(secondauthority.length()>0){
	 	 	    		
	 	 	    		secondauthority = secondauthority.substring(0,secondauthority.length()-1);
	 	 	    		secondauthority = sort(secondauthority);
	 	 	    	}
	 	 	    	
	 	 	    	
	 	 	    	
	 	 	    	//封装权限
	 	 	    	UserAuthority userAuthority = new UserAuthority();
	 	 	    	userAuthority.setUserid(user.getUserid());
	 	 	    	userAuthority.setTopauthority(topauthority);
	 	 	    	userAuthority.setSecondauthority(secondauthority);
	 	 	    	
	 	 	    	//更新用户权限:先判断是否存在，若存在，更新；若不存在，则保存
	 	 	    	Boolean isExist = userService.isExistPermission(user.getUserid());
	 	 	    	if(isExist){
	 	 	    		//存在，更新
	 	 	    		Integer result1 = userService.updatePermission(userAuthority);
	 	 	    		if(result1 > 0){
	 	 	    			//return true;
	 	 	    		}else{
	 	 	    			//return false;
	 	 	    		}
	 	 	    	}else{
	 	 	    		//不存在，保存
	 	 	    		Integer result1 = userService.savePermission(userAuthority);
	 	 	    		if(result1 > 0){
	 	 	    			//return true;
	 	 	    		}else{
	 	 	    			//return false;
	 	 	    		}
	 	 	    	}
 	    		
 	    	}//for end
 	    	
 	    	
    	}else{//resultCode==201 if else
    		return false;
    	}
		return true;
	}
	
	//获取截面数据
	private boolean getSection(String _sectionid) throws Exception{
		//[5]section接口测试
    	String result = MyHttpRequest.httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=section&Id="+_sectionid);
    	JSONObject jsonObject = JSONObject.fromObject(result); 
    	Integer resultCode=jsonObject.getInt("resultCode");
    	System.out.println(jsonObject);
 	    if(resultCode == 200){
 	    	//获取成功
 	    	JSONObject sectionObj = jsonObject.getJSONObject("data");
 	    	if(sectionObj.toString().equals("null")){
 	    		return false;
 	    	}
 	    	_sectionid = sectionObj.getString("sectionid");
 	    	Boolean isDeleted = sectionObj.getBoolean("isDeleted");
 	    	Float distance = (float) sectionObj.getDouble("distance");
 	    	System.out.println("具体地城："+distance);
 	    	
			Date createtime = null;
 	    	try {
				createtime = DateFormatUtils.format4(sectionObj.getString("createTime"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	
 	    	Float actualarea = (float) sectionObj.getDouble("actualarea");
 	    	Float referarea = (float) sectionObj.getDouble("referarea");
 	    	Float exceedarea = (float) sectionObj.getDouble("exceedarea");
 	    	Float owearea = (float) sectionObj.getDouble("owearea");
 	    	Float exceedvolume = (float) sectionObj.getDouble("exceedvolume");
 	    	Float owevolume = (float) sectionObj.getDouble("owevolume");
 	    	
 	    	//	String groupid = sectionObj.getString("groupid");
 	    	String measureid = sectionObj.getString("measureid");
 	    	
 	    	Integer stage = sectionObj.getInt("stage");
 	    	
 	    	Date buildtime = null;
			try {
				buildtime = DateFormatUtils.format4(sectionObj.getString("buildtime"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	Date measuretime = null;
			try {
				measuretime = DateFormatUtils.format4(sectionObj.getString("measuretime"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	
			//判断数据库中是否存在，存在则更新，不存在则插入
			Section section = new Section();
			section.set_sectionid(_sectionid);
			section.setActualarea(actualarea);
			section.setOwevolume(owevolume);
			section.setOwearea(owearea);
			section.setCreatetime(createtime);
			section.setBuildtime(buildtime);
			section.setMeasureid(measureid);
			section.setIsDeleted(isDeleted);
			section.setStage(stage);
			section.setReferarea(referarea);
			section.setDistance(distance);
			//section.setGroupid(groupid);
			section.setMeasuretime(measuretime);
			section.setExceedarea(exceedarea);
			section.setExceedvolume(exceedvolume);
 	    	
			//设置不存在的默认值
			section.setRefervolume(0.0f);
			
			User measurer = userService.findBy_userid(measureid);
			if(measurer != null){
				section.setMeasureid(measurer.getUserid());
			}else{
				section.setMeasureid("10000");
			}
			
			
			//查询该里程截面对应的工程
			Distance own_distance = distanceService.findByDistance(distance);
			if(own_distance==null){
				//工程还没有，不能插入截面
				return false;
			}
			
			section.setGroupid(own_distance.getGroupid());
			//根据_sectionid查询是否存在截面
			Section isExist = sectionService.isExistSection(section);
			
			if (isExist != null) {
				System.out.println("不空，更新");
				// 更新，一般不会存在这种情况
				Integer result1 = sectionService.updateSection_cop(section);
				//删除截面点信息
				sectionService.deletePoints(isExist.getSectionid());
				
				//保存最新点信息
				//保存点
				JSONArray pointArray = sectionObj.getJSONArray("points");
				if(pointArray != null && pointArray.size()>0){
					//删除该界面所有点信息，重新保存新的点信息
					sectionService.deletePoints(section.getSectionid());
					//循环插入新点
					for(int i=0;i<pointArray.size();i++){
						JSONObject pointObj = pointArray.getJSONObject(i);
						String _pointid = pointObj.getString("pointid");
						Float pointx = (float) pointObj.getDouble("pointx");
						Float pointy = (float) pointObj.getDouble("pointy");
						Float offset = (float) pointObj.getDouble("offset");
						Integer type = 0;
						if(offset > 0){
							type = 1;
						}else
							if(offset < 0){
								type = 2;
							}
						Date scantime = null;
						try {
							scantime = DateFormatUtils.format4(pointObj.getString("scantime"));
						} catch (ParseException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						
						//封装点信息
						Point point  = new Point();
						point.set_pointid(_pointid);
						point.setPointid(GUIDSeqGenerator.getInstance().newGUID());
						point.setPointx(pointx);
						point.setPointy(pointy);
						point.setScantime(scantime);
						point.setOffset(offset);
						point.setSectionid(section.getSectionid());
						point.setType(type);
						//保存点信息
						sectionService.savePoint(point);
					}
					
				}
				//3、更新distance中超欠挖量,首先判断stage阶段，其次还原该阶段上一次扫描的超欠挖量，然后在更新本截面增加的超欠挖量
				if(stage == 1){
					//操作第一阶段的
					Float new_exceedvolume = own_distance.getFirstexceedvolume();
					new_exceedvolume = new_exceedvolume - isExist.getExceedvolume() + exceedvolume;
					own_distance.setFirstexceedvolume(new_exceedvolume);
					
					Float new_owevolume = own_distance.getFirstowevolume();
					new_owevolume = new_owevolume - isExist.getOwevolume() + owevolume;
					own_distance.setFirstexceedvolume(new_owevolume);
				}else
					if(stage == 2){
						//操作第二阶段的
						Float new_exceedvolume = own_distance.getSecondexceedvolume();
						new_exceedvolume = new_exceedvolume - isExist.getExceedvolume() + exceedvolume;
						own_distance.setSecondexceedvolume(new_exceedvolume);
						
						Float new_owevolume = own_distance.getSecondowevolume();
						new_owevolume = new_owevolume - isExist.getOwevolume() + owevolume;
						own_distance.setSecondexceedvolume(new_owevolume);
					}else
						if(stage == 3){
							//操作第三阶段的
							Float new_exceedvolume = own_distance.getThirdexceedvolume();
							new_exceedvolume = new_exceedvolume - isExist.getExceedvolume() + exceedvolume;
							own_distance.setThirdexceedvolume(new_exceedvolume);
							
							Float new_owevolume = own_distance.getThirdowevolume();
							new_owevolume = new_owevolume - isExist.getOwevolume() + owevolume;
							own_distance.setThirdexceedvolume(new_owevolume);
						}else
							if(stage == 4){
								//操作第四阶段的
								Float new_exceedvolume = own_distance.getFourthexceedvolume();
								new_exceedvolume = new_exceedvolume - isExist.getExceedvolume() + exceedvolume;
								own_distance.setFourthexceedvolume(new_exceedvolume);
								
								Float new_owevolume = own_distance.getFourthowevolume();
								new_owevolume = new_owevolume - isExist.getOwevolume() + owevolume;
								own_distance.setFourthexceedvolume(new_owevolume);
							}
				//更新里程表
				distanceService.updateDistanceVolume(own_distance);
				return true;
			} else {
				//如果_sectionid不存在
				//判断distance和stage一起作为主键的记录是否存在
				Section existSection = sectionService.isExistSectionByDistanceAndStage(section);
				if(existSection!=null){
					//如果存在，删除，再插入；继续做distance中超欠挖量的更新
					//1、删除
					sectionService.deleteSection(existSection);
					//2、插入
					String sectionid = GUIDSeqGenerator.getInstance().newGUID();
					section.setSectionid(sectionid);
					Integer result1 = sectionService.saveSection_cop(section);
					if (result1 > 0) {
						// 插入成功
						//保存点
						JSONArray pointArray = sectionObj.getJSONArray("points");
						if(pointArray != null && pointArray.size()>0){
							//删除该界面所有点信息，重新保存新的点信息
							sectionService.deletePoints(section.getSectionid());
							//循环插入新点
							for(int i=0;i<pointArray.size();i++){
								JSONObject pointObj = pointArray.getJSONObject(i);
								String _pointid = pointObj.getString("pointid");
								Float pointx = (float) pointObj.getDouble("pointx");
								Float pointy = (float) pointObj.getDouble("pointy");
								Float offset = (float) pointObj.getDouble("offset");
								Integer type = 0;
								if(offset > 0){
									type = 1;
								}else
									if(offset < 0){
										type = 2;
									}
								Date scantime = null;
								try {
									scantime = DateFormatUtils.format4(pointObj.getString("scantime"));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								//封装点信息
								Point point  = new Point();
								point.set_pointid(_pointid);
								point.setPointid(GUIDSeqGenerator.getInstance().newGUID());
								point.setPointx(pointx);
								point.setPointy(pointy);
								point.setScantime(scantime);
								point.setOffset(offset);
								point.setSectionid(section.getSectionid());
								point.setType(type);
								//保存点信息
								sectionService.savePoint(point);
							}
							
						}
						//3、更新distance中超欠挖量,首先判断stage阶段，其次还原该阶段上一次扫描的超欠挖量，然后在更新本截面增加的超欠挖量
						if(stage == 1){
							//操作第一阶段的
							Float new_exceedvolume = own_distance.getFirstexceedvolume();
							new_exceedvolume = new_exceedvolume - existSection.getExceedvolume() + exceedvolume;
							own_distance.setFirstexceedvolume(new_exceedvolume);
							
							Float new_owevolume = own_distance.getFirstowevolume();
							new_owevolume = new_owevolume - existSection.getOwevolume() + owevolume;
							own_distance.setFirstexceedvolume(new_owevolume);
						}else
							if(stage == 2){
								//操作第二阶段的
								Float new_exceedvolume = own_distance.getSecondexceedvolume();
								new_exceedvolume = new_exceedvolume - existSection.getExceedvolume() + exceedvolume;
								own_distance.setSecondexceedvolume(new_exceedvolume);
								
								Float new_owevolume = own_distance.getSecondowevolume();
								new_owevolume = new_owevolume - existSection.getOwevolume() + owevolume;
								own_distance.setSecondexceedvolume(new_owevolume);
							}else
								if(stage == 3){
									//操作第三阶段的
									Float new_exceedvolume = own_distance.getThirdexceedvolume();
									new_exceedvolume = new_exceedvolume - existSection.getExceedvolume() + exceedvolume;
									own_distance.setThirdexceedvolume(new_exceedvolume);
									
									Float new_owevolume = own_distance.getThirdowevolume();
									new_owevolume = new_owevolume - existSection.getOwevolume() + owevolume;
									own_distance.setThirdexceedvolume(new_owevolume);
								}else
									if(stage == 4){
										//操作第四阶段的
										Float new_exceedvolume = own_distance.getFourthexceedvolume();
										new_exceedvolume = new_exceedvolume - existSection.getExceedvolume() + exceedvolume;
										own_distance.setFourthexceedvolume(new_exceedvolume);
										
										Float new_owevolume = own_distance.getFourthowevolume();
										new_owevolume = new_owevolume - existSection.getOwevolume() + owevolume;
										own_distance.setFourthexceedvolume(new_owevolume);
									}
						//更新里程表
						distanceService.updateDistanceVolume(own_distance);
						return true;
					} else {
						// 插入失败
						return false;
					}
				}else{
					//如果不存在，则直接插入
					String sectionid = GUIDSeqGenerator.getInstance().newGUID();
					section.setSectionid(sectionid);
					Integer result1 = sectionService.saveSection_cop(section);
					if (result1 > 0) {
						// 插入成功
						//保存点
						JSONArray pointArray = sectionObj.getJSONArray("points");
						if(pointArray != null && pointArray.size()>0){
							//删除该界面所有点信息，重新保存新的点信息
							sectionService.deletePoints(section.getSectionid());
							//循环插入新点
							for(int i=0;i<pointArray.size();i++){
								JSONObject pointObj = pointArray.getJSONObject(i);
								String _pointid = pointObj.getString("pointid");
								Float pointx = (float) pointObj.getDouble("pointx");
								Float pointy = (float) pointObj.getDouble("pointy");
								Float offset = (float) pointObj.getDouble("offset");
								Integer type = 0;
								if(offset > 0){
									type = 1;
								}else
									if(offset < 0){
										type = 2;
									}
								Date scantime = null;
								try {
									scantime = DateFormatUtils.format4(pointObj.getString("scantime"));
								} catch (ParseException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
								
								//封装点信息
								Point point  = new Point();
								point.set_pointid(_pointid);
								point.setPointid(GUIDSeqGenerator.getInstance().newGUID());
								point.setPointx(pointx);
								point.setPointy(pointy);
								point.setScantime(scantime);
								point.setOffset(offset);
								point.setSectionid(section.getSectionid());
								point.setType(type);
								//保存点信息
								sectionService.savePoint(point);
							}
							
						}
						//3、更新distance中超欠挖量,首先判断stage阶段，直接更新本截面增加的超欠挖量
						if(stage == 1){
							//操作第一阶段的
							Float new_exceedvolume = own_distance.getFirstexceedvolume();
							new_exceedvolume = new_exceedvolume + exceedvolume;
							own_distance.setFirstexceedvolume(new_exceedvolume);
							
							Float new_owevolume = own_distance.getFirstowevolume();
							new_owevolume = new_owevolume + owevolume;
							own_distance.setFirstexceedvolume(new_owevolume);
						}else
							if(stage == 2){
								//操作第二阶段的
								Float new_exceedvolume = own_distance.getSecondexceedvolume();
								new_exceedvolume = new_exceedvolume + exceedvolume;
								own_distance.setSecondexceedvolume(new_exceedvolume);
								
								Float new_owevolume = own_distance.getSecondowevolume();
								new_owevolume = new_owevolume + owevolume;
								own_distance.setSecondexceedvolume(new_owevolume);
							}else
								if(stage == 3){
									//操作第三阶段的
									Float new_exceedvolume = own_distance.getThirdexceedvolume();
									new_exceedvolume = new_exceedvolume + exceedvolume;
									own_distance.setThirdexceedvolume(new_exceedvolume);
									
									Float new_owevolume = own_distance.getThirdowevolume();
									new_owevolume = new_owevolume + owevolume;
									own_distance.setThirdexceedvolume(new_owevolume);
								}else
									if(stage == 4){
										//操作第四阶段的
										Float new_exceedvolume = own_distance.getFourthexceedvolume();
										new_exceedvolume = new_exceedvolume + exceedvolume;
										own_distance.setFourthexceedvolume(new_exceedvolume);
										
										Float new_owevolume = own_distance.getFourthowevolume();
										new_owevolume = new_owevolume + owevolume;
										own_distance.setFourthexceedvolume(new_owevolume);
									}
						//更新里程表
						distanceService.updateDistanceVolume(own_distance);
						return true;
					} else {
						// 插入失败
						return false;
					}
				}
			
				
			}
			
 	    }
 	    return false;
	}
	
	//获取工程数据
	private boolean getDistance(String _distanceid) throws Exception{
		String result = MyHttpRequest.httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=distance&Id="+_distanceid);
    	JSONObject jsonObject = JSONObject.fromObject(result); 
    	Integer resultCode=jsonObject.getInt("resultCode");
    	System.out.println(jsonObject);
    	
 	    if(resultCode == 200){
 	    	//获取成功
 	    	JSONObject distanceObj = jsonObject.getJSONObject("data");
 	    	if(distanceObj.toString().equals("null")){
 	    		return false;
 	    	}
 	    	//[1]解析数据
 	    	_distanceid = distanceObj.getString("distanceid");
 	    	Boolean isDeleted = distanceObj.getBoolean("isDeleted");
			Date createtime = null;
 	    	try {
				createtime = DateFormatUtils.format4(distanceObj.getString("createTime"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	Float begindistance = (float) distanceObj.getDouble("begindistance");
 	    	Float enddistance = (float) distanceObj.getDouble("enddistance");
 	    	Date begintime = null;
			try {
				begintime = DateFormatUtils.format4(distanceObj.getString("begintime"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	Date endtime = begintime;
 	    	
 	    	String _groupid = distanceObj.getString("groupid");
 	    	
 	    	//[2]根据_groupid查询groupid
 	    	Group group = groupService.findBy_groupid(_groupid);
 	    	
 	    	//[3]封装对象
 	    	Distance distance = new Distance();
 	    	distance.set_distanceid(_distanceid);
 	    	distance.setIsDeleted(isDeleted);
 	    	distance.setCreatetime(createtime);
 	    	distance.setBegindistance(begindistance);
 	    	distance.setEnddistance(enddistance);
 	    	distance.setBegintime(begintime);
 	    	distance.setEndtime(endtime);
 	    	
 	    	//设置默认值
 	    	distance.setFirstexceedvolume(0.0f);
 	    	distance.setFirstowevolume(0.0f);
 	    	distance.setSecondexceedvolume(0.0f);
 	    	distance.setSecondowevolume(0.0f);
 	    	distance.setThirdexceedvolume(0.0f);
 	    	distance.setThirdowevolume(0.0f);
 	    	distance.setFourthexceedvolume(0.0f);
 	    	distance.setFourthowevolume(0.0f);
 	    	
 	    	if(group != null){
 	    		distance.setGroupid(group.getGroupid());
 	    	}
 	    
 	    	//[4]判断数据库中是否存在，存在则更新，不存在则插入
 	    	boolean isExist = distanceService.isExistDistance(distance);
 	    	Integer result1 = 0;
 	    	if(isExist){
 	    		//已存在，更新即可
 	    		result1 = distanceService.updateDistance(distance); 
 	    	}else{
 	    		//不存在，插入
 	    		distance.setDistanceid(GUIDSeqGenerator.getInstance().newGUID());
 	    		result1 = distanceService.saveDistance(distance); 
 	    	}
 	    	if(result1 > 0){
 	    		return true;
 	    	}else{
 	    		return false;
 	    	}
 	    }	
 	    return false;
	}
		
	//获取技术交底数据
	private boolean getTechnology(String _distanceid) throws Exception{
		String result = MyHttpRequest.httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=technicalbasis&Id="+_distanceid);
	    JSONObject jsonObject = JSONObject.fromObject(result); 
	    Integer resultCode=jsonObject.getInt("resultCode");
	    System.out.println(jsonObject);
	 	if(resultCode == 200){
	 		//获取成功
 	    	JSONObject fileObj = jsonObject.getJSONObject("data");
 	    	
 	    	String fileid = fileObj.getString("id");
 	    	String construction_units = fileObj.getString("construction_units");
 	    	
			Date upload_time = null;
 	    	try {
 	    		upload_time = DateFormatUtils.format4(fileObj.getString("upload_time"));
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 	    	
 	    	String pPath = fileObj.getString("pPath");
 	    	String individualProjectName = fileObj.getString("individualProjectName");
 	    	//Boolean isDeleted = fileObj.getBoolean("isDeleted");
 	    	String file_name = fileObj.getString("file_name");
 	    	String vPath = fileObj.getString("vPath");
 	    	String uploader_id = fileObj.getString("uploader_id");
 	    	String file_type = fileObj.getString("file_type");
			
			File_info file_info = new File_info();
			file_info.setFile_id(fileid);
			file_info.setConstruction_units(construction_units);
			file_info.setUpload_time(upload_time);
			file_info.setProject_name(individualProjectName);
			User uploader = userService.findBy_userid(uploader_id);
			if(uploader != null){
				file_info.setUploader(uploader.getUsername());
			}
			file_info.setFile_type(file_type);
			file_info.setPath(pPath);
			file_info.setvPath(vPath);
			file_info.setFile_name(file_name);
 	    	
 	    	//判断数据库中是否存在，存在则更新，不存在则插入
			if(fileInfoService.findById(fileid) == null){
				//添加
				Integer result1 = fileInfoService.save(file_info);
				if(result1 > 0){
					//转发通知
				
					// 创建StringBuffer对象用来操作字符串
					StringBuffer sb = new StringBuffer("http://crtg-3.com:8081/technical_clarification_web/FileServlet?method=17");

					sb.append("&path="+pPath);

					sb.append("&fileName="+URLEncoder.encode(file_name,"utf-8"));
					
					sb.append("&fileId="+fileid);
					
					// 创建url对象
					URL url = new URL(sb.toString());
					// 打开url连接
					HttpURLConnection connection = (HttpURLConnection) url.openConnection();

					// 设置url请求方式 ‘get’ 或者 ‘post’
					connection.setRequestMethod("POST");

					// 发送
					BufferedReader in = new BufferedReader(new InputStreamReader(
							url.openStream()));

					// 返回发送结果
					String inputline = in.readLine();

					// 返回结果为‘100’ 发送成功
					System.out.println(inputline);
					
					return true;
				}else{
					return false;
				}
			
			}else{
				//更新
				//Integer result1 = fileInfoService.update(file_info);
//				if(result1 > 0){
//					return true;
//				}else{
//					return false;
//				}
				return false;
			}
		}
	 	
	 	return false;
	}		
	
	private String sort(String str){
		String[] strs = str.split(";");
		Arrays.sort(strs);
		String result = "";
		for(int i=0;i<strs.length;i++){
			result = result + strs[i] + ";";
		}
		
		result = result.substring(0,result.length()-1);
		return result;
	}
	
}
