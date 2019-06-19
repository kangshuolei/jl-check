package com.hbsi.export.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.domain.BreakStockDomain;
import com.hbsi.export.service.BreakOrigRecordService;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/export")
public class BreakOrigRecordController {
	
	/**
	 * author:shazhou
	 */
	@Autowired
	private BreakOrigRecordService breakORService;

	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 导出拆股实验原始报告数据（word下载形式）
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/breakStockReport")
	public void breakStockOriginalWord(String entrustNum,HttpServletResponse response) throws IOException{
		try {
			breakORService.BreakOrigRecord(entrustNum, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出拆股实验原始报告数据（json格式展示）
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/breakStockReportJson")
	public Response breakStockOriginalJson(String entrustNum) throws IOException{
		Response<Map<String,Object>> rp=new Response<Map<String,Object>>();
		Map<String,Object> map=new HashMap<>();
			return breakORService.BreakOrigRecordJson(entrustNum);
	}
	
	/**
	 * 这个方法适用于前端修改拆股实验原始记录的版本好的方法
	 * @param info
	 * @return
	 */
	@PostMapping("/updateBreakOriVersonNumber")
	public Response updateBreakOriRecord(@RequestBody BreakStockDomain breakStockDomain) {
		return breakORService.updateBreakStockRecord(breakStockDomain);
	}
}
