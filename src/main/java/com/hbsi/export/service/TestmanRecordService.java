package com.hbsi.export.service;

import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hbsi.response.Response;

public interface TestmanRecordService {

	/**
	 * 导出测试人记录的excel数据，给前端下载
	 * @param entrustNum
	 * @param response
	 * @return 
	 * @throws IOException
	 * @throws Exception 
	 */
	public Response ExportTestmanRecord(String entrustNum) throws IOException, Exception;
	/**
	 * 导出测试人记录的Json数据，给前端展示
	 * @param entrustNum
	 * @return
	 */
	public List<Map<Integer,Object>> getRecordList(String entrustNum);
}
