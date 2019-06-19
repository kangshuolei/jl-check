package com.hbsi.steel.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hbsi.domain.SampleRecord;
import com.hbsi.domain.SteelWireData;
import com.hbsi.response.Response;
import com.hbsi.sample.service.SampleRecordService;
import com.hbsi.steel.entity.SteelWireRecord;
import com.hbsi.steel.service.SteelService;

/**
 * 钢丝报告处理
 * @author lixuyang
 * @version 1.0，2018-09-29
 *
 */
@RestController
@RequestMapping("/steel")
public class SteelController {
	
	@Autowired
	private SteelService steelService;
	
	/**
	 * 调入数据   将实验记录数据进行整合，组成钢丝数据
	 * @param entrustmentNumber
	 * @return
	 */
	@PostMapping("/selectSteelWireDataList")
	public Response<SteelWireRecord> selectSteelWireDataList(@RequestBody String info){
		return steelService.selectSteelWireDataList(info);
	}
	
	/**
	 * 修改钢丝数据（判定）
	 * @param steelWireData
	 * @return
	 */
	@PostMapping("/updateSteelWire")
	public Response<List<SteelWireData>> updateSteelWire(@RequestBody SteelWireRecord r){
		List<SteelWireData> list=r.getSteelWireDataList();
		return steelService.updateSteelWire(list);
	}

}
