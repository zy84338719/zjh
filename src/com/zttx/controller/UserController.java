package com.zttx.controller;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.request.MyHttpRequest;
import com.zttx.po.AuthorityCustom;
import com.zttx.po.Group;
import com.zttx.po.GroupCustom;
import com.zttx.po.SecondAuthority;
import com.zttx.po.TopAuthority;
import com.zttx.po.User;
import com.zttx.po.UserAuthority;
import com.zttx.po.UserCustom;
import com.zttx.service.AuthorityService;
import com.zttx.service.GroupService;
import com.zttx.service.UserService;
import com.zttx.service.UserService4;
import com.zttx.vo.Message;


/**
 * 用户Controller
 *
 */
@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService; 
	
	@Autowired
	private UserService4 userService4; 
	
	@Autowired
	private AuthorityService authorityService;
	
	@Autowired
	private GroupService groupService;
	/*
	 * 用户登录请求
	 */
	@RequestMapping("/login")
	public @ResponseBody Message login(@RequestBody User user) throws Exception{
		Message msg = new Message();
		
		//[1]判断请求参数是否完整
		if(user == null || user.getTelphone() == null || user.getTelphone().trim().equals("") || user.getPassword() == null || user.getPassword().trim().equals("")){
			msg.setCode(101);//请求参数不完整
			msg.setMsg("请求参数不完整");
			return msg;
		}
		
//		//[2]查询用户是否存在
//		UserCustom userCustom = userService.findUser(user);
//		if(userCustom == null){
//			msg.setCode(102);//用户名或密码不存在
//			msg.setMsg("用户名或密码不存在");
//			return msg;
//		}
//		
//		//[3]登录成功返回
//		msg.setCode(100);
//		msg.setMsg("用户登录成功");
//		msg.setObj(userCustom);
		
		//[3]用户登录接口测试
		System.out.println("登录用户名："+user.getTelphone());
    	String result = MyHttpRequest.httpURLConectionGET("http://crtg-3.com:8080/api/Authentication/Get?Username="+URLEncoder.encode(user.getTelphone(),"UTF-8")+"&password="+user.getPassword());
		//String result = MyHttpRequest.httpURLConnectionPOST("http://crtg-3.com:8080/api/Authentication/Get","Username="+user.getTelphone()+"&password="+user.getPassword());
    	JSONObject jsonObject = JSONObject.fromObject(result); 
    	String errorCode = jsonObject.getString("errorCode");
    	if(errorCode.equals("0")){
    		//张吉怀用户表验证成功：
    		String _userid = jsonObject.getString("uid");
    		User userCustom = userService.findBy_userid(_userid);
    		if(userCustom != null){
    			//本地数据库验证成功,一标
    			msg.setCode(100);
    			msg.setMsg("一标用户登录成功");
    			userCustom.setDepartment(1);
    			msg.setObj(userCustom);
    		}else{
    			userCustom = userService4.findBy_userid(_userid);
    			if(userCustom!= null){
    				//四标用户
    				msg.setCode(100);
        			msg.setMsg("四标用户登录成功");
        			userCustom.setDepartment(4);
        			msg.setObj(userCustom);
    			}else{
    				msg.setCode(101);
        			msg.setMsg("用户登录失败");
    			}
    			
    		}
    	}else{
    		//登陆失败
    		msg.setCode(101);
			msg.setMsg("用户登录失败");
    	}
		return msg;
	}
	
	@RequestMapping("/findLeader")
	public @ResponseBody Message findLeader()throws Exception{
		Message msg=new Message();
		
		List<UserCustom> userCustom_list=userService.findLeader();
		if(userCustom_list.size()>0){
			msg.setCode(100);
			msg.setMsg("获取领导数据成功！");
			msg.setObj(userCustom_list);
		}else{
			msg.setCode(101);
			msg.setMsg("获取领导数据失败！");
		}
		
		return msg;
	}
	
	/**
	 * 查看用户详情
	 */
	@RequestMapping("/findUserInfo")
	public @ResponseBody Message findUserInfo(@RequestBody User user)throws Exception{
		Message msg = new Message();
		UserCustom user_new = userService.findUser(user);
		if(user_new != null){
			msg.setCode(100);
			msg.setMsg("获取用户数据成功！");
			msg.setObj(user_new);
		}else{
			msg.setCode(101);
			msg.setMsg("获取用户数据失败！");
		}
		return msg;
	}
	
	/**
	 * 更新用户信息
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/updateUserInfo")
	public @ResponseBody Message updateUserInfo(@RequestBody User user)throws Exception{
		Message msg = new Message();
		int result = userService.updateUser(user);
		if(result == 1){
			msg.setCode(100);
			msg.setMsg("更改用户信息成功！");
		}else{
			msg.setCode(101);
			msg.setMsg("更改用户信息失败！");
		}
		return msg;
	}
	
	/**
	 * 查询用户一级权限
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryTopAuthority")
	public @ResponseBody Message queryTopAuthority(@RequestBody AuthorityCustom authorityCustom)throws Exception{
		Message msg = new Message();
		UserAuthority userAuthority = authorityService.queryUserAuthority(authorityCustom);
		List<TopAuthority> topAuthorityList = new ArrayList<TopAuthority>();
		if(userAuthority != null){
			String[] topList = userAuthority.getTopauthority().split(";");
			if(topList.length>0){
				for(int i=0; i<topList.length; i++){
					String topid = topList[i];
					TopAuthority topAuthority = authorityService.findTopAuthorityByTopId(topid);
					topAuthorityList.add(topAuthority);
				}
			}
			msg.setCode(100);
			msg.setObj(topAuthorityList);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无一级权限");
		}
		return msg;
	}
	
	/**
	 * 根据用户id和topid查询用户具有的二级权限
	 * @param authorityCustom
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/querySecondAuthority")
	public @ResponseBody Message querySecondAuthority(@RequestBody AuthorityCustom authorityCustom)
		throws Exception{
		Message msg = new Message();
		UserAuthority userAuthority = authorityService.queryUserAuthority(authorityCustom);
		List<SecondAuthority> secondAuthorityList = new ArrayList<SecondAuthority>();
	
		if(userAuthority != null){
			System.out.println(userAuthority.getSecondauthority());
			String[] secondList = userAuthority.getSecondauthority().split(";");
			if(secondList.length>0){
				for(int i=0; i<secondList.length; i++){
					String secondid = secondList[i];
					SecondAuthority secondAuthority = authorityService.findSecondAuthorityBySecondId(secondid);
					if(secondAuthority!=null && secondAuthority.getTopid().equals(authorityCustom.getTopid())){	//判断一级权限是否为页面传过来的一级权限
						secondAuthorityList.add(secondAuthority);
					}
				}
			}
			msg.setCode(100);
			msg.setObj(secondAuthorityList);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无一级权限");
		}
		return msg;
	}
	
	/**
	 * 测试短信
	 * @param user
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/testmsg")
	public @ResponseBody Message testmsg()throws Exception{
		Message msg = new Message();
		
		return msg;
	}
	
		/**
		 * 根据分组查询用户信息
		 * @return
		 * @throws Exception
		 */
		@RequestMapping("/queryUserByGroup")
		public @ResponseBody Message queryUserByGroup()throws Exception{
		Message msg = new Message();
		List<GroupCustom> groupCustomList = groupService.queryAllGroupOnlyNameAndId();
		if(groupCustomList.size()>0){
			for(int i=0; i<groupCustomList.size(); i++){
				String code_l = groupCustomList.get(i).getCode();
				String code = null;
				if(code_l.length()>14){
					code = groupCustomList.get(i).getCode().substring(0, 11);
					Group g = groupService.findGroupByCodeOnlyName(code);
					if(g != null){
						groupCustomList.get(i).setFathergroupname(g.getGroupname());
					}
				}
				List<User> userList = userService.findUserOnlyNameAndIdByGroupid(groupCustomList.get(i).getGroupid());
				if(userList != null && userList.size()>0 && userList.get(0) != null){
					groupCustomList.get(i).setUserList(userList);
				}else{
					groupCustomList.remove(i);
					i--;
				}
				
			}
			msg.setCode(100);
			msg.setMsg("用户分组获取成功！");
			msg.setObj(groupCustomList);
		}else{
			msg.setCode(101);
			msg.setMsg("暂无用户分组信息！");
		}
		return msg;		
	}
	
	/*
	 * 设置用户角色
	 */
	@RequestMapping("/updateSetting")
	public @ResponseBody Message updateSetting(@RequestBody User user) throws Exception{
		Message msg = new Message();
		Integer result = userService.updateRole(user);
		
		if(result > 0){
			msg.setCode(100);
			msg.setMsg("操作成功");
			return msg;
		}
		
		msg.setCode(101);
		msg.setMsg("操作失败");
		return msg;
	}
	
	/**
	 * 查询所有
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/queryAllDepartment")
	public @ResponseBody Message queryAllDepartment(@RequestBody User user)throws Exception{
		Message msg = new Message();
		
		user = userService.findUser(user);
		
		//普通成员直接返回空
		if(user.getRole() == null || user.getRole().equals("0")){
			//普通成员，没有权限
			msg.setCode(101);
			msg.setMsg("普通用户无法使用此功能！");
			return msg;
		}
		
		//查询当前用户所在部门
		Group group = groupService.findGroupById(user.getGroupid());
		
		//查询所有部门
		List<GroupCustom> groupCustomList = groupService.queryAllGroupOnlyNameAndId();
		if(groupCustomList.size()>0){
			for(int i=0; i<groupCustomList.size(); i++){
				String code_l = groupCustomList.get(i).getCode();
				String code = null;
				if(code_l.length()>14){
					//找父级部门
					code = groupCustomList.get(i).getCode().substring(0, 11);
					Group g = groupService.findGroupByCodeOnlyName(code);
					if(g != null){
						groupCustomList.get(i).setFathergroupname(g.getGroupname());
					}
				}
				List<User> userList = userService.findUserOnlyNameAndIdByGroupid(groupCustomList.get(i).getGroupid());
				if(userList != null && userList.size()>0 && userList.get(0) != null){
					groupCustomList.get(i).setUserList(userList);
				}else{
					groupCustomList.remove(i);
					i--;
				}
				
			}
			
			//过滤部分部门
			//如果是领导班子的直接过
			if(group.getCode().equals("001.001.001.001")){
				
			}else{
				//否则只能查看到本部门
				for(int i=0;i<groupCustomList.size();i++){
					if(!groupCustomList.get(i).getCode().startsWith(group.getCode())){
						groupCustomList.remove(i);
						i--;
					}
				}
			}
			msg.setObj(groupCustomList);
			
			if(groupCustomList.size() <= 0){
				msg.setCode(101);
				msg.setMsg("没有您要查询的部门");
				return msg;
			}else{
				msg.setCode(100);
				msg.setMsg("部门列表获取成功");
				return msg;
			}
			
			
		}else{
			msg.setCode(101);
			msg.setMsg("暂无部门列表信息！");
		}
		return msg;		
	}
	
}
