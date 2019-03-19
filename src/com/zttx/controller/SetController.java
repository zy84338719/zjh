package com.zttx.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.zttx.po.Set;
import com.zttx.service.SetService;
import com.zttx.utils.GUIDSeqGenerator;
import com.zttx.vo.Message;

@Controller
@RequestMapping("/set")
public class SetController {
	
	@Autowired
	private SetService setService;
	
	/**
	 * 保存超挖深度与土方量设置信息
	 * @param set
	 * @return
	 * @throws Exception
	 */
	@RequestMapping("/saveExceedValAndVolume")
	public @ResponseBody Message saveExceedValAndVolume(@RequestBody Set set)throws Exception{
		Message msg = new Message();
		String id = GUIDSeqGenerator.getInstance().newGUID();
		set.setSetid(id);
		int result = 0;
		List<Set> set_list = setService.find();
		if(set_list.size() == 0){
			result = setService.save(set);
		}else{
			Set set_old = set_list.get(0);
			set.setSetid(set_old.getSetid());
			result = setService.update(set);
		}
		if(result == 1){
			msg.setCode(100);
			msg.setMsg("设置数据插入成功！");
		}else{
			msg.setCode(101);
			msg.setMsg("设置数据插入失败！");
		}
		return msg;
	}
}
