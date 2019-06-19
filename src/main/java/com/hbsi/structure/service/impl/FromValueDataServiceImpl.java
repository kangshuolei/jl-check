package com.hbsi.structure.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hbsi.basedata.fromvalue.service.FromValueService;
import com.hbsi.domain.FromValue;
import com.hbsi.response.Response;
import com.hbsi.structure.service.FromValueDataService;
@Service
public class FromValueDataServiceImpl implements FromValueDataService{

	/**
	 * author:shazhou
	 */
	@Autowired
	private FromValueService fromValueService;
	/**
	 * 根据标准号，从fromValue表单中查询得到强度级别，表面状态，捻法的列表
	 */
	@Override
	public Response<Map<String, List<String>>> getFromValueList(String info) {
		JSONObject json = JSON.parseObject(info);
		String standardNum = json.getString("standardNum");
		List<FromValue> fromValueList = fromValueService.selectByStandardNum(standardNum);
		//公称强度列表
		List<String> ratedStrengthList = new ArrayList<>();
		//表面状态列表
		List<String> surfaceStateList = new ArrayList<>();
		//捻法列表
		List<String> twistingMethodList = new ArrayList<>();
		//存储三哥list表，返回给前端
		Map<String,List<String>> map = new HashMap<>();
		for(FromValue fromValue:fromValueList) {
			if(fromValue.getRatedStrength()!= null && !"".equals(fromValue.getRatedStrength())) {
				ratedStrengthList.add(fromValue.getRatedStrength());
			}
			if(fromValue.getSurfaceState() != null && !"".equals(fromValue.getSurfaceState())) {
				surfaceStateList.add(fromValue.getSurfaceState());
			}
			if(fromValue.getTwistingMethod() != null && !"".equals(fromValue.getTwistingMethod())) {
				twistingMethodList.add(fromValue.getTwistingMethod());
			}
		}
		map.put("ratedStrengthList", ratedStrengthList);
		map.put("surfaceStateList", surfaceStateList);
		map.put("twistingMethodList", twistingMethodList);
		return new Response<>(map);
	}

}
