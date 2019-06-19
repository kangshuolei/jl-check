package com.hbsi.entrustment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.hbsi.domain.Entrustment;
import com.hbsi.entrustment.service.SaveEntrustmenService;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/createEntrustment")
public class EntrustmentCreatedController {

	@Autowired
	private SaveEntrustmenService saveEntrustmentService;
	/**
	 * 想办法搞到一个新的批次号给前端传过去
	 * @return
	 */
	@RequestMapping("/createNewBatch")
	public Response<Integer> createNewBatch(){
		return saveEntrustmentService.saveNewBatch();
	}
	/**
	 * 把批次号跟对应的委托单号接收，保存到委托单号表中去
	 * @param entrustment
	 * @return
	 */
	@PostMapping("/saveEntrustment")
	public Response saveEntrustment(@RequestBody Entrustment entrustment){
		return saveEntrustmentService.saveEntrustment(entrustment);
	}
	
	/**
	 * 一次性生成多个批次号
	 * @param entrustment
	 * @return
	 */
	@PostMapping("/saveManyBatch")
	public Response saveManyEntrustment(@RequestBody String info){
		return saveEntrustmentService.saveManyNewBatch(info);
	}
	/**
	 * 委托单号表中的所有信息，倒序排列，给前端传过去
	 * @return
	 */
	@PostMapping("/getEntrustmentList")
	public Response<Object> getEntrustmentList(@RequestBody String info){
		JSONObject json = JSONObject.parseObject(info);
		Integer offset = json.getInteger("offset");
		Integer limit = json.getInteger("limit");
		return saveEntrustmentService.selectEntrustmentList(offset,limit);
	}
}
