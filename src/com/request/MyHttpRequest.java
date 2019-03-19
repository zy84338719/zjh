package com.request;


import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.ParseException;
import java.util.Date;

import net.sf.json.JSONObject;

import com.zttx.po.Section;
import com.zttx.utils.DateFormatUtils;

public class MyHttpRequest {

//	public static final String GET_URL = "http://112.4.27.9/mall-back/if_user/store_list?storeId=32";
////    public static final String POST_URL = "http://112.4.27.9/mall-back/if_user/store_list";
//	// 妙兜测试接口
//	public static final String POST_URL = "http://121.40.204.11:8180/mdserver/service/installLock";
//    
    /**
     * 接口调用 GET
     */
    public static String httpURLConectionGET(String get_url) {
        try {
            URL url = new URL(get_url);    // 把字符串转换为URL请求地址
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 打开连接
            connection.connect();// 连接会话
            // 获取输入流
            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8"));
            String line;
            StringBuilder sb = new StringBuilder();
            while ((line = br.readLine()) != null) {// 循环读取流
                sb.append(line);
            }
            br.close();// 关闭流
            connection.disconnect();// 断开连接
            System.out.println(sb.toString());
            return sb.toString();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("失败!");
        }
        return null;
    }
    
    /**
     * 接口调用  POST
     */
    public static String httpURLConnectionPOST (String post_url,String params) {
        try {
            URL url = new URL(post_url);
            
            // 将url 以 open方法返回的urlConnection  连接强转为HttpURLConnection连接  (标识一个url所引用的远程对象连接)
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();// 此时cnnection只是为一个连接对象,待连接中
            
            // 设置连接输出流为true,默认false (post 请求是以流的方式隐式的传递参数)
            connection.setDoOutput(true);
            
            // 设置连接输入流为true
            connection.setDoInput(true);
            
            // 设置请求方式为post
            connection.setRequestMethod("POST");
            
            // post请求缓存设为false
            connection.setUseCaches(false);
            
            // 设置该HttpURLConnection实例是否自动执行重定向
            connection.setInstanceFollowRedirects(true);
            
            // 设置请求头里面的各个属性 (以下为设置内容的类型,设置为经过urlEncoded编码过的from参数)
            // application/x-javascript text/xml->xml数据 application/x-javascript->json对象 application/x-www-form-urlencoded->表单数据
            // ;charset=utf-8 必须要，不然妙兜那边会出现乱码【★★★★★】
            connection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded;charset=utf-8");	
            
            // 建立连接 (请求未开始,直到connection.getInputStream()方法调用时才发起,以上各个参数设置需在此方法之前进行)
            connection.connect();
            
            // 创建输入输出流,用于往连接里面输出携带的参数,(输出内容为?后面的内容)
            DataOutputStream dataout = new DataOutputStream(connection.getOutputStream());
            
//            String app_key = "app_key="+ URLEncoder.encode("4f7bf8c8260124e6e9c6bf094951a111", "utf-8");		// 已修改【改为错误数据，以免信息泄露】
//            String agt_num = "&agt_num="+ URLEncoder.encode("10111", "utf-8");				// 已修改【改为错误数据，以免信息泄露】
//            String pid = "&pid="+ URLEncoder.encode("BLZXA150401111", "utf-8");				// 已修改【改为错误数据，以免信息泄露】
//            String departid = "&departid="+ URLEncoder.encode("10007111", "utf-8");			// 已修改【改为错误数据，以免信息泄露】
//            String install_lock_name = "&install_lock_name="+ URLEncoder.encode("南天大门", "utf-8");
//            String install_address = "&install_address="+ URLEncoder.encode("北京育新", "utf-8");
//            String install_gps = "&install_gps="+ URLEncoder.encode("116.350888,40.011001", "utf-8");
//            String install_work = "&install_work="+ URLEncoder.encode("小李", "utf-8");
//            String install_telete = "&install_telete="+ URLEncoder.encode("13000000000", "utf-8");
//            String intall_comm = "&intall_comm="+ URLEncoder.encode("一切正常", "utf-8");
            
            // 格式 parm = aaa=111&bbb=222&ccc=333&ddd=444
            //String parm = app_key+ agt_num+ pid+ departid+ install_lock_name+ install_address+ install_gps+ install_work+ install_telete+ intall_comm;
            
            // 将参数输出到连接
            dataout.writeBytes(params);
            
            // 输出完成后刷新并关闭流
            dataout.flush();
            dataout.close(); // 重要且易忽略步骤 (关闭流,切记!) 
            
//            System.out.println(connection.getResponseCode());
            
            // 连接发起请求,处理服务器响应  (从连接获取到输入流并包装为bufferedReader)
            BufferedReader bf = new BufferedReader(new InputStreamReader(connection.getInputStream(), "UTF-8")); 
            String line;
            StringBuilder sb = new StringBuilder(); // 用来存储响应数据
            
            // 循环读取流,若不到结尾处
            while ((line = bf.readLine()) != null) {
//                sb.append(bf.readLine());
            	sb.append(line).append(System.getProperty("line.separator"));
            }
            bf.close();    // 重要且易忽略步骤 (关闭流,切记!) 
            connection.disconnect(); // 销毁连接
          //  System.out.println(sb.toString());
            return sb.toString();
    
        } catch (Exception e) {
            e.printStackTrace();
        }
		return null;
    }
    
    public static void main(String[] args) {
//        httpURLConectionGET();
    	//[1]用户接口测试
//    	String result = httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=user&Id=4E64C633-2156-470C-8D17-A76500F1200B");
//    	JSONObject jsonObject = JSONObject.fromObject(result); 
//    	//{"data":{"userid":"4e64c633-2156-470c-8d17-a76500f1200b","loginname":"陈绍武","password":"SRSQXCFfs1OgrsxnQXzHyewfVzYBEVas","username":"陈绍武","telphone":"13632983902","groupid":"085ffebe-59f0-411a-a316-a76500bd2a20"},"isSuccess":true,"resultCode":200,"message":"获取数据成功"}
// 	    Integer resultCode=jsonObject.getInt("resultCode");
// 	    if(resultCode == 200){
// 	    	//获取成功
// 	    	JSONObject userObj = jsonObject.getJSONObject("data");
// 	    	
// 	    	String userid = userObj.getString("userid");
// 	    	String loginname = userObj.getString("loginname");
// 	    	String password = userObj.getString("password");
// 	    	String groupid = userObj.getString("groupid");
// 	    	String username = userObj.getString("username");
// 	    	String telphone = userObj.getString("telphone");
// 	    	User user = new User();
// 	    	user.setUserid(userid);
// 	    	user.setLoginname(loginname);
// 	    	user.setPassword(password);
// 	    	user.setGroupid(groupid);
// 	    	user.setUsername(username);
// 	    	user.setTelphone(telphone);
// 	    	
// 	    	//判断数据库中是否存在，存在则更新，不存在则插入
// 	    	//TODO
// 	    }
    	
//    	//[2]群组接口测试
//    	String result = httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=group&Id=d2b4efe6-ae5c-432e-a55a-a84900f4561c");
//    	JSONObject jsonObject = JSONObject.fromObject(result); 
//    	//{"data":{"groupid":"334e5585-5e05-42a5-8972-a76500bd1110","isDeleted":false,"createTime":"2017-04-30 11:28:22","groupname":"二分部","code":"001.001.003"},"isSuccess":true,"resultCode":200,"message":"成功"}
//    	Integer resultCode=jsonObject.getInt("resultCode");
//    	System.out.println(result);
// 	    if(resultCode == 200){
// 	    	//获取成功
// 	    	JSONObject groupObj = jsonObject.getJSONObject("data");
// 	    	
// 	    	
// 	    	String groupid = groupObj.getString("groupid");
// 	    	
// 	    	Boolean isDeleted = groupObj.getBoolean("isDeleted");
// 	    	String groupname = groupObj.getString("groupname");
// 	    	String code = groupObj.getString("code");
// 	    	String createTimeStr = groupObj.getString("createTime");
// 	    	Date createtime = null;
//			try {
//				createtime = DateFormatUtils.format4(createTimeStr);
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
// 	    	Group group = new Group();
// 	    	group.setGroupid(groupid);
// 	    	group.setGroupname(groupname);
// 	    	group.setCode(code);
// 	    	group.setCreatetime(createtime);
// 	    	group.setIsDeleted(isDeleted);
// 	    	
// 	    	//判断数据库中是否存在，存在则更新，不存在则插入
// 	    	//TODO
// 	    }
//    	
    	//[3]权限接口测试
//    	String result = httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=permission&Id="+"d2b4efe6-ae5c-432e-a55a-a84900f4561c,d2b4efe6-ae5c-432e-a55a-a84900f4561c");
//    	JSONObject jsonObject = JSONObject.fromObject(result); 
//    	System.out.println(jsonObject);
//    	Integer resultCode=jsonObject.getInt("resultCode");
//    	
// 	    if(resultCode == 200){
// 	    	String topauthority = "";
// 	    	String secondauthority = "";
// 	    	//获取成功
// 	    	JSONArray permissionArr = jsonObject.getJSONArray("data");
// 	    	for(int i=0;i<permissionArr.size();i++){
// 	    		String permission = permissionArr.getString(i);
// 	    		if(!permission.contains("_")){
// 	    			continue;
// 	    		}
// 	    		//解析权限信息
// 	    		String[] permission_num = permission.split("_");
// 	    		if(permission_num[0].equals("00")){
// 	    			//一级权限
// 	    			if(permission_num[1].equals("3DScan")){
// 	    				topauthority += "1;";
// 	    			}else
// 	    				if(permission_num[1].equals("QualityTrace")){
// 	 	    				topauthority += "2;";
// 	 	    			}else
// 	 	    				if(permission_num[1].equals("WorkList")){
// 	 	 	    				topauthority += "3;";
// 	 	 	    			}else
// 	 	 	    				if(permission_num[1].equals("BuildProgress")){
// 	 	 	 	    				topauthority += "4;";
// 	 	 	 	    			}else
// 	 	 	 	    				if(permission_num[1].equals("CarLocation")){
// 	 	 	 	 	    				topauthority += "5;";
// 	 	 	 	 	    			}else
//	 	 	 	  	    				if(permission_num[1].equals("PersonLocation")){
//	 	 	 	 	 	    				topauthority += "6;";
//	 	 	 	 	 	    			}else
//		 	 	 	  	    				if(permission_num[1].equals("VideoMonitor")){
//		 	 	 	 	 	    				topauthority += "7;";
//		 	 	 	 	 	    			}else
//			 	 	 	  	    				if(permission_num[1].equals("TechnicalDisclosure")){
//			 	 	 	 	 	    				topauthority += "8;";
//			 	 	 	 	 	    			}
// 	    			
// 	    		}else{
// 	 	    			//二级权限
// 	    				//3D扫描二级权限
// 	 	    			if(permission_num[1].equals("Statistics")){
// 	 	    				secondauthority += "101;";
// 	 	    			}else
// 	 	    				if(permission_num[1].equals("Push")){
// 	 	    					secondauthority += "102;";
// 	 	 	    			}else
// 	 	 	    			if(permission_num[1].equals("Query")){
// 	 	 	    				secondauthority += "103;";
// 	 	 	 	    		}else
// 	 	 	 	    			if(permission_num[1].equals("Dealing")){
// 	 	 	 	    				secondauthority += "104;";
// 	 	 	 	 	    	}else
// 	 	 	 	 	    		if(permission_num[1].equals("Set")){
// 	 	 	 	 	    			secondauthority += "105;";
// 	 	 	 	 	 	    	}else
// 	 	 	 	 	 	    	//质量溯源二级权限
// 		 	 	 	  	    	if(permission_num[1].equals("MaterialEntering")){
// 		 	 	 	  	    		secondauthority += "201;";
// 		 	 	 	 	 	    }else
// 			 	 	 	  	    if(permission_num[1].equals("MaterialCheck")){
// 			 	 	 	  	    	secondauthority += "202;";
// 			 	 	 	 	 	}else
// 				 	 	 	  	if(permission_num[1].equals("MaterialApply")){
// 				 	 	 	  	    secondauthority += "203;";
// 				 	 	 	 	}else
//	 				 	 	 	if(permission_num[1].equals("MaterialAudit")){
//	 				 	 	 	 	secondauthority += "204;";
//	 		 		 	 	 	}else
//	 		 			 	 	if(permission_num[1].equals("MaterialProvide")){
//	 		 			 	 	 	secondauthority += "205;";
//	 		 			 	 	}else
//	 		 				 	if(permission_num[1].equals("ComponentProcessing")){
//	 		 				 	 	secondauthority += "206;";
//	 		 				 	}else
//	 		 	 				if(permission_num[1].equals("ComponentAudit")){
//	 		 	 				 	secondauthority += "207;";
//	 		 	 				}else
//	 		 		 			if(permission_num[1].equals("ComponentProvide")){
//	 		 		 				secondauthority += "208;";
//	 		 		 		 	}else
//	 		 		 		 	if(permission_num[1].equals("ComponentInstall")){
//	 		 		 		 		secondauthority += "209;";
//	 		 		 		 	}else
//	 		 		 		 	if(permission_num[1].equals("ComponentCheck")){
//	 		 		 		 		secondauthority += "210;";
//	 		 		 		 	}else
//		 		 		 		if(permission_num[1].equals("SubQualityTrace")){
//		 		 		 		  	secondauthority += "211;";
//		 		 		 		}else
//		 		 		 		if(permission_num[1].equals("MaterialTrace")){
//		 		 		 			secondauthority += "212;";
//		 		 		 		}else
//		 		 		 			//进度管理二级权限
//				 		 		 	if(permission_num[1].equals("ConstructionCreate")){
//				 		 		 		secondauthority += "401;";
//				 		 		 	}else
//				 		 		 	if(permission_num[1].equals("ProgressEntry")){
//				 		 		 		secondauthority += "402;";
//					 		 		}else
//					 		 		if(permission_num[1].equals("ProgressQuery")){
//					 		 			secondauthority += "403;";
//					 		 		}else
//					 		 			//技术交底二级权限
//					 		 		 	if(permission_num[1].equals("TechnicalDisclosureInput")){
//					 		 		 		secondauthority += "801;";
//					 		 		 	}else
//					 		 		 	if(permission_num[1].equals("TechnicalDisclosureCheck")){
//					 		 		 		secondauthority += "802;";
//						 		 		}else
//						 		 		if(permission_num[1].equals("TechnicalDisclosureDistribute")){
//						 		 			secondauthority += "803;";
//						 		 		}else
//						 		 		if(permission_num[1].equals("TechnicalDisclosureSignature")){
//						 		 		 	secondauthority += "804;";
//						 		 		}else
//						 		 		if(permission_num[1].equals("TechnicalDisclosureQuery")){
//						 		 		 	secondauthority += "805;";
//							 		 	}else
//							 		 	if(permission_num[1].equals("TechnicalDisclosureStatistics")){
//							 		 		secondauthority += "806;";
//							 		 	}
// 	 	    			
// 	 	    		}
// 	    		
// 	    		System.out.println(permission);
// 	    	}
//
// 	    	if(topauthority.length()>0){
// 	    		topauthority = topauthority.substring(0,topauthority.length()-1);
// 	    	}
// 	    	if(secondauthority.length()>0){
// 	    		secondauthority = secondauthority.substring(0,secondauthority.length()-1);
// 	    	}
// 	    	
// 	    	//查询用户
// 	    	System.out.println(topauthority);
// 	    	System.out.println(secondauthority);
// 	    	//判断数据库中是否存在，存在则更新，不存在则插入
// 	    	//TODO
// 	    }
    	
//    	//[4]Distance接口测试
//    	String result = httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=distance&Id=79fb28ce-f01a-4d1a-9790-a854010ab665");
//    	JSONObject jsonObject = JSONObject.fromObject(result); 
//    	Integer resultCode=jsonObject.getInt("resultCode");
//    	System.out.println(jsonObject);
// 	    if(resultCode == 200){
// 	    	//获取成功
// 	    	JSONObject distanceObj = jsonObject.getJSONObject("data");
// 	    	if(distanceObj.toString().equals("null")){
// 	    		return;
// 	    	}
// 	    	String _distanceid = distanceObj.getString("distanceid");
// 	    	Boolean isDeleted = distanceObj.getBoolean("isDeleted");
//			Date createtime = null;
// 	    	try {
//				createtime = DateFormatUtils.format4(distanceObj.getString("createTime"));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
// 	    	Float begindistance = (float) distanceObj.getDouble("begindistance");
// 	    	Float enddistance = (float) distanceObj.getDouble("enddistance");
// 	    	Date begintime = null;
//			try {
//				begintime = DateFormatUtils.format4(distanceObj.getString("begintime"));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
//			
//			String groupid = distanceObj.getString("groupid");
//			//
//			
// 	    	Date endtime = begintime;
//
// 	    	
// 	    	Distance distance = new Distance();
// 	    	distance.set_distanceid(_distanceid);
// 	    	distance.setIsDeleted(isDeleted);
// 	    	distance.setCreatetime(createtime);
// 	    	distance.setBegindistance(begindistance);
// 	    	distance.setEnddistance(enddistance);
// 	    	distance.setBegintime(begintime);
// 	    	distance.setEndtime(endtime);
// 	    	distance.setGroupid(groupid);
// 	    	
// 	    	System.out.println(distance.get_distanceid());
// 	    	
// 	    	//判断数据库中是否存在，存在则更新，不存在则插入
// 	    
// 	    }
    	
    	//[5]section接口测试
    	String result = httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=section&Id=82ca55ce-3b4a-490c-b3ba-a86800a9976f");
    	JSONObject jsonObject = JSONObject.fromObject(result); 
    	//{"data":["CoreDailyAttendanceView","CoreDailyInspectionOfWork","CoreMoveResumeView","ProcessTemplateView","TunnelExcavationQuarterPlanDelete","CoreMaintenanceView","CoreIndividualProjectNew","TunnelExcavationMonthPlanEdit","TunnelExcavationYearPlanView","OverBreakNew","CoreStructureProjectNew","EquipmentWorkNew","ProcessTemplateTimeNew","CoreIndividualProjectEdit","TunnelExcavationQuarterPlanEdit","CoreMonthlyAttendanceStatisticsView","EquipmentWorkView","OverBreakView","TunnelExcavationWeekPlanView","TunnelExcavationYearPlanNew","CoreStructureProjectDelete","CoreDailyAttendanceStatisticsView","TunnelExcavationMonthPlanDelete","TunnelExcavationMonthPlanNew","EquipmentInfoDelete","TunnelExcavationQuarterPlanView","DeviceEdit","ProcessTemplateTimeEdit","TunnelExcavationWeekPlanDelete","CoreMaterialNew","EquipmentInfoNew","OverBreakDelete","OverBreakEdit","ProcessTemplateEdit","TunnelExcavationMonthPlanView","ProcessTemplateTimeView","CoreMaintainPlanView","TunnelExcavationWeekPlanNew","DeviceDelete","CoreEmployeeInfoView","CoreRoleDelete","CoreRoleView","CoreEmployeeInfoDelete","CoreUserDelete","CoreMessageRecordView","CoreConfigEdit","CoreConfigView","CorePermissionDelete","CoreMenuEdit","CoreMenuView","CoreRolePermissionView","CoreDataDictionaryNew","CoreUserEdit","CoreMenuDelete","CorePermissionNew","CorePermissionView","CoreRoleUserView","CoreRoleNew","CoreOperationRecordView","CoreUserView","CoreRoleUserDelete","CoreOnlineView","CoreDataDictionaryDelete","CoreRoleUserNew","CoreUserChangePassword","CoreEmployeeInfoEdit","CorePermissionEdit","CoreUserNew","CoreRoleEdit","CoreMenuNew","CoreDataDictionaryEdit","CoreDataDictionaryView","CoreRolePermissionEdit","CoreOrganizationView","CoreOrganizationNew","CoreOrganzaitionEdit","CoreOrganizationDelete","TeamWorkNew","TeamWorkEdit","TeamWorkView","TeamWorkDelete","CoreEmployeeInfoNew","InspectionOfWorkList","InspectionOfWorkNew","InspectionOfWorkEdit","DeleteInspectionOfWork","CoreUserOrganizationDataList","CoreUserOrganizationDataAdd","CoreUserOrganizationDataDelete","PartWorkView","PartWorkNew","PartWorkEdit","PartWorkDelete","ExcavationProcessTimeStatisticsView","PartWorkActualAmount","ProjectDailySummaryView","SystemConfigManagerView","CoreMessageSwitchManagerView","WeiCheckRecordView","WeiOperateRecordView","LocationInfoView","PersonnelPositioningView","CoreSteelView","CoreSteelDelete","CoreSteelEdit","CoreSteelNew","CoreMaintainView","ApprovalBillView","FSMaterialEdit","FSMaterialView","FSMaterialNew","FSMaterialDelete","JBZMaterialView","JBZMaterialNew","JBZMaterialEdit","JBZMaterialDelete","CoreApprovalBillDelete","DataIn3DView","DataIn3DNew","DataIn3DEdit","DataIn3DDelete","Data3DWarningView","Data3DWarningDelete","Data3DWarningSms","WarningMessageView","CoreIndividualProjectDelete","TwoLiningView","TwoLiningNew","TwoLiningEdit","TwoLiningDelete","CoreIndividualProjectView","CoreStructureProjectEdit","CoreMaintenancePlanView","DeviceView","EquipmentInfoView","ProcessTemplateNew","TunnelExcavationYearPlanDelete","CoreMaterialEdit","CoreStructureProjectView","CoreMaterialDelete","EquipmentWorkEdit","ProcessTemplateTimeDelete","EquipmentWorkDelete","CoreEquipmentInfoView","CoreDailyAttendance","EquipmentInfoEdit","CoreMaterialView","TunnelExcavationWeekPlanEdit","ProcessTemplateDelete","TunnelExcavationQuarterPlanNew","TunnelExcavationYearPlanEdit","20_ComponentProvide","20_MaterialApply","80_TechnicalDisclosureQuery","20_MaterialEntering","80_TechnicalDisclosureSignature","00_TechnicalDisclosure","20_MaterialProvide","00_3DScan","20_MaterialTrace","80_TechnicalDisclosureStatistics","10_Statistics","00_BuildProgress","40_ConstructionCreate","00_PersonLocation","20_ComponentProcessing","00_VideoMonitor","40_ProgressEntry","40_ProgressQuery","00_QualityTrace","10_Push","10_Set","00_WorkList","80_TechnicalDisclosureDistribute","20_ComponentInstall","10_Query","20_MaterialCheck","20_ComponentAudit","10_Dealing","20_SubQualityTrace","20_MaterialAudit","00_CarLocation","80_TechnicalDisclosureInput","20_ComponentCheck","80_TechnicalDisclosureCheck"],"isSuccess":true,"resultCode":200,"message":"获取权限成功"}
    	Integer resultCode=jsonObject.getInt("resultCode");
    	System.out.println(jsonObject);
 	    if(resultCode == 200){
 	    	//获取成功
 	    	JSONObject sectionObj = jsonObject.getJSONObject("data");
 	    	if(sectionObj.toString().equals("null")){
 	    		return;
 	    	}
 	    	String _sectionid = sectionObj.getString("sectionid");
 	    	Boolean isDeleted = sectionObj.getBoolean("isDeleted");
 	    	Float distance = (float) sectionObj.getDouble("distance");
 	    	
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
			section.setMeasuretime(measuretime);
			section.setExceedarea(exceedarea);
			section.setExceedvolume(exceedvolume);
 	    	
			System.out.println(section.get_sectionid());
			
 	    	//判断数据库中是否存在，存在则更新，不存在则插入
 	    	
 	    }
//    
//    	[6]technicalbasis接口测试
//    	String result = httpURLConnectionPOST("http://219.134.241.186:8080/api/app/GetData","getType=technicalbasis&Id=78dce548-d634-4ac5-9a65-a85400c0f8af");
//    	JSONObject jsonObject = JSONObject.fromObject(result); 
//    	Integer resultCode=jsonObject.getInt("resultCode");
//    	System.out.println(jsonObject);
// 	    if(resultCode == 200){
// 	    	//获取成功
// 	    	JSONObject fileObj = jsonObject.getJSONObject("data");
// 	    	
// 	    	String fileid = fileObj.getString("id");
// 	    	String construction_units = fileObj.getString("construction_units");
// 	    	
//			Date upload_time = null;
// 	    	try {
// 	    		upload_time = DateFormatUtils.format4(fileObj.getString("upload_time"));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
// 	    	
// 	    	Date createTime = null;
//			try {
//				createTime = DateFormatUtils.format4(fileObj.getString("createTime"));
//			} catch (ParseException e) {
//				// TODO Auto-generated catch block
//				e.printStackTrace();
//			}
 	    	
// 	    	String pPath = fileObj.getString("pPath");
// 	    	String individualProjectName = fileObj.getString("individualProjectName");
// 	    	Boolean isDeleted = fileObj.getBoolean("isDeleted");
// 	    	String file_name = fileObj.getString("file_name");
// 	    	String vPath = fileObj.getString("vPath");
// 	    	String uploader_id = fileObj.getString("uploader_id");
// 	    	String file_type = fileObj.getString("file_type");
//			
//			File_info file_info = new File_info();
//			file_info.setFile_id(fileid);
//			file_info.setConstruction_units(construction_units);
//			file_info.setUpload_time(upload_time);
//			file_info.setProject_name(individualProjectName);
//			file_info.setUploader(uploader_id);
//			file_info.setFile_type(file_type);
//			file_info.setPath(pPath);
//			file_info.setvPath(vPath);
//			file_info.setFile_name(file_name);
 	    	
 	    	//判断数据库中是否存在，存在则更新，不存在则插入
			
// 	    }
    	
//    	//[3]用户登录接口测试
//    	String result = httpURLConectionGET("http://crtg-3.com:8080/api/Authentication/Get?Username="+URLEncoder.encode("李袁_测试")+"&password=123456");
//    	JSONObject jsonObject = JSONObject.fromObject(result); 
//    	String errorCode = jsonObject.getString("errorCode");
//    	if(errorCode.equals("0")){
//    		//登陆成功
//    		String userid = jsonObject.getString("uid");
//    		System.out.println(userid);
//    	}else{
//    		//登陆失败
//    	}
    }
}