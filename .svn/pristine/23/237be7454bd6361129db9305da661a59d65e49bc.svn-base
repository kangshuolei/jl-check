package com.hbsi.entrustment.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.domain.Entrustment;
import com.hbsi.entrustment.entity.EntInfo;
import com.hbsi.entrustment.service.EntrustmentService;
import com.hbsi.response.Response;

/**
 * 委托单管理
 * @author lixuyang
 * @version 1.0，2018-09-27
 *
 */
@RestController
@RequestMapping("/entrustment")
public class EntrustmentController {
	
	@Autowired
	private EntrustmentService entrustmentService;
	
	/**
	 * 添加委托单
	 * @param entrustment
	 * @return
	 */
	@PostMapping("/saveEntrustment")
	public Response<Integer> saveEntrustment(@RequestBody Entrustment entrustment){
		return entrustmentService.saveEntrustment(entrustment);
	}
	
	/**
	 * 修改委托单
	 * @param entrustment
	 * @return
	 */
	@PostMapping("/updateEntrustment")
	public Response<Integer> updateEntrustment(@RequestBody EntInfo ei){
		return entrustmentService.updateEntrustment(ei);
	}
	
	/**
	 * 删除委托单
	 * @param entrustment
	 * @return
	 */
	@PostMapping("/deleteEntrustment")
	public Response<Integer> deleteEntrustment(@RequestBody Entrustment entrustment){
		return entrustmentService.deleteEntrustment(entrustment);
	} 
	
	/**
	 * 模糊查询委托单
	 * @param ent
	 * @return
	 */
	@GetMapping("selectEntrustment")
	public Response<List<Entrustment>> selectEntrustment(String ent){
		return entrustmentService.selectEntrustment(ent);
	}
	/**
	 * 查询委托单列表（改为====查询当天的批次号）
	 * @return
	 */
//	@PostMapping("/selectEntrustmentList")
//	public Response<List<Entrustment>> selectEntrustmentList(){
//		return entrustmentService.selectEntrustmentList();
//	}

}
