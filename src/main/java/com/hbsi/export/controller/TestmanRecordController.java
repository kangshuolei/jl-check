package com.hbsi.export.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.export.service.TestmanRecordService;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/export")
public class TestmanRecordController {

	/**
	 * author:shazhou
	 */
	@Autowired
	private TestmanRecordService testmanRecordService;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 导出测试人记录报告excel数据
	 * @param id
	 * @param response
	 * @throws Exception 
	 */
	@GetMapping("/testmanRecordExcel")
	public Response testmanRecordExcel(String entrustNum) throws Exception{
			return testmanRecordService.ExportTestmanRecord(entrustNum);
	}
	
//	/**
//	 * 导出测试人记录报告Json数据
//	 * @param id
//	 * @param response
//	 * @throws IOException
//	 */
//	@GetMapping("/testmanRecordJson")
//	public Response<List<Map<Integer, Object>>> testmanRecordJson(String entrustNum){
//		Response<List<Map<Integer, Object>>> rp=new Response<List<Map<Integer, Object>>>();
//		List<Map<Integer, Object>> list=new ArrayList<>();
//		try {
//			list = testmanRecordService.getRecordList(entrustNum);
//			rp.setData(list);
//		} catch (Exception e) {
//			e.printStackTrace();
//		}
//		return rp;
//	}
	
}
