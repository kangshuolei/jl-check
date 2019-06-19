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

import com.hbsi.domain.ExportDomain;
import com.hbsi.export.service.ReportExportService;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/export")
public class ReportExportController {

	/**
	 * author:shazhou
	 */
	@Autowired
	private ReportExportService  reportEService;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 导出测试报告结论（word下载形式）
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/steelWireReport")
	public void reportExportWord(String entrustNum,HttpServletResponse response) throws IOException{
		Map<String,Object> map=new HashMap<>();
		try {
			reportEService.ExportReport(entrustNum, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * 导出测试报告结论（json格式展示）
	 * @param id
	 * @param response
	 * @throws IOException
	 */
	@GetMapping("/steelWireReportJson")
	public  Response<?> reportExportJson(String entrustNum) throws IOException{
		return reportEService.ExportReportJson(entrustNum);
	}
	
	/**
	 * 手动修改结论报告信息
	 */
	@PostMapping("/updateConclusion")
	public Response<?> updateSteelWireConlusion(@RequestBody ExportDomain exportDomain){
		return reportEService.updateConclusion(exportDomain);
	}
	/**
	 * 生成并保存实验结论
	 */
	@PostMapping("/saveBreakConclusion")
	public Response<?> saveBreakConclusion(@RequestBody Map<String,String> entrustNum){
		return reportEService.saveBreakConclusionData(String.valueOf(entrustNum.get("entrustNum")));
	}
	
}
