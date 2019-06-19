package com.hbsi.entrustment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.domain.EntrustmentData;
import com.hbsi.domain.VersonNumber;
import com.hbsi.entrustment.service.EntrustmentDataService;
import com.hbsi.response.Response;

/**
 * 委托单管理
 * @author lixuyang
 * @version 1.0，2018-09-27
 *
 */
@RestController
@RequestMapping("/entrustment")
public class EntrustmentDataController {
	
	@Autowired
	private EntrustmentDataService entrustmentDataService;
	
	/**
	 * 模糊查询委托单数据
	 * @param info
	 * @return
	 */
	@PostMapping("/selectEntData")
	public Response<List<EntrustmentData>> selectEntData(@RequestBody String info){
		return entrustmentDataService.selectEntData(info);
	}
	
	/**
	 * 查询或新建委托单报告
	 * @param info
	 * @return
	 */
	@PostMapping("/selectOrSaveEntData")
	public Response<EntrustmentData> selectOrSaveEntData(@RequestBody String info){
		return entrustmentDataService.selectOrSaveEntData(info);
	}
	
	/**
	 * 添加委托单数据和钢丝数据（提交按钮）
	 * @param info
	 * @return
	 */
	@PostMapping("/saveEntDataAndSteelData")
	public Response<Integer> saveEntDataAndSteelData(@RequestBody String info){
		return entrustmentDataService.saveEntDataAndSteelData(info);
	}
	
	/**
	 * 查询版本号
	 * @param info
	 * @return
	 */
	@PostMapping("/selectVersionNum")
	public Response<VersonNumber> selectVersionNum(){
		return entrustmentDataService.selectVersionNum();
	}
	
	/**
	 * 修改版本号
	 * @param info
	 * @return
	 */
	@PostMapping("/updateVersionNum")
	public Response<Integer> updateVersionNum(@RequestBody String info){
		return entrustmentDataService.updateVersionNum(info);
	}

}
