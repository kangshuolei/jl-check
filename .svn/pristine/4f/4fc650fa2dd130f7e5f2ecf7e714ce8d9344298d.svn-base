package com.hbsi.export.service;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import com.hbsi.domain.BreakStockDomain;
import com.hbsi.response.Response;

public interface BreakOrigRecordService {

	/**
	 * 拆股实验原始记录（以word流的方式写回给前端）
	 * @return 
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 */
	public Response BreakOrigRecord(String entrustNum, HttpServletResponse response) throws FileNotFoundException, IOException; 
	/**
	 * 拆股实验的头文件信息
	 * @param entrustNum
	 * @return
	 */
	public Map<String,Object> HeadOfBreak(String entrustNum);
	/**
	 * 拆股实验的steelData列表信息，从steelData数据库中读取
	 * @param entrustNum
	 * @return
	 */
	public List<Map<String, Object>> SteelDataRecord(String entrustNum);
	/**
	 * 拆股实验原始记录数据以map的形式返回给前端
	 * @param entrustNum
	 * @return
	 * @throws IOException
	 */
	Response BreakOrigRecordJson(String entrustNum) throws IOException;
	/**
	 * 该方法用于前端修改版本号的值，只有管理员才有资格改
	 * @param breakStockDomain
	 * @return
	 */
	Response updateBreakStockRecord(BreakStockDomain breakStockDomain);

}
