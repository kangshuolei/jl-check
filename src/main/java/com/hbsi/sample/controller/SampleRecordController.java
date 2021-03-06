package com.hbsi.sample.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.domain.SampleRecord;
import com.hbsi.response.PageResult;
import com.hbsi.response.Response;
import com.hbsi.sample.service.SampleRecordService;
import com.hbsi.sample.entity.SampleRecords;

/**
 * 试样数据管理
 * @author lixuyang
 * @version 1.0，2018-09-27
 *
 */
@RestController
@RequestMapping("/sample")
public class SampleRecordController {
	
	@Autowired
	private SampleRecordService sampleRecordService;//试验记录
	
	/**
	 * 查询试验记录
	 * @param info
	 * @return
	 */
	@Transactional
	@PostMapping("/selectSampleRecordList")
	public Response<PageResult> selectSampleRecordList(@RequestBody String info){
		return sampleRecordService.selectSampleRecordList(info);
	}
	
	/**
	 * 错误处理列表
	 * @param info
	 * @return
	 */
	@Transactional
	@PostMapping("/selectSampleErrorList")
	public Response<PageResult> selectSampleErrorList(@RequestBody String info){
		return sampleRecordService.selectSampleErrorList(info);
	}
	/**
	 * 查询错误数据
	 * @param sampleBatch
	 * @return
	 */
	@GetMapping("selectErrorDataList")
	public Response<PageResult> selectErrorDataList(Integer sampleBatch,String type,String machineNumber,Integer offset,Integer limit){
		return sampleRecordService.selectErrorDataList(sampleBatch,type,machineNumber, offset, limit);
	}
	/**
	 * 重置试验编号
	 * @param sampleBatch
	 * @param type
	 * @return
	 */
	@GetMapping("resetSampleNumber")
	public Response<SampleRecord> resetSampleNumber(Integer sampleBatch,String type){
		return sampleRecordService.resetSampleNumber(sampleBatch, type);
	}
	
	/**
	 * 删除错误数据
	 * @param info
	 * @return
	 */
	@Transactional
	@PostMapping("/deleteSampleError")
	public Response<Integer> deleteSampleError(@RequestBody String info){
		return sampleRecordService.deleteSampleError(info);
	}
	/**
	 * 查询批次号和委托单号
	 * @return
	 */
	@Transactional
	@GetMapping("selectSampleBatch")
	public Response<List<SampleRecord>> selectSampleBatch(){
		return sampleRecordService.selectSampleBatch();
	}
	
	/**
	 * 修改批次号
	 * @return
	 */
	@Transactional
	@PostMapping("updateSampleBatch")
	public Response<SampleRecords> updateSampleBatch(@RequestBody String info){
		return sampleRecordService.updateSampleBatch(info);
	}
	/**
	 * 修改试样编号 id sampleNumber
	 * @param info
	 * @return
	 */
	@PostMapping("updateSampleNumber")
	public Response<SampleRecord> updateSampleNumber(@RequestBody String info){
		return sampleRecordService.updateSampleNumber(info);
	}
	
}
