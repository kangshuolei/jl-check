package com.hbsi.export.controller;


import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.export.service.AccountReportService;
import com.hbsi.response.Response;

@RestController
@RequestMapping("/export")
public class AccountreportController {

	@Autowired
	private AccountReportService accountReportService;
	/**
	 * 导出统计报告 
	 * @throws IOException 
	 */
	@RequestMapping(value ="/AccountReportExcel", method =RequestMethod.GET )
	public Response<?> AccountReportExportExcel(String producerNumber, String judgeStandard, HttpServletResponse response) throws IOException {
		return accountReportService.accountReportExport(producerNumber, judgeStandard, response);
	}
}
