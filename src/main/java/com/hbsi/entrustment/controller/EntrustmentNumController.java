package com.hbsi.entrustment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.entrustment.service.EntrustmentNumService;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/entrustmentNum")
public class EntrustmentNumController {

	@Autowired
	private EntrustmentNumService entrustmentNumService;

	/**
	 * 保存生成的默认委托单号
	 * 
	 * @return
	 */
	@GetMapping("/showEntrustmentDefaultNum")
	public Response<String> showEntrustmentDefaultNum(Integer batchNum) {
		return entrustmentNumService.createEntrustmentDefaultNum(batchNum);
	}
}
