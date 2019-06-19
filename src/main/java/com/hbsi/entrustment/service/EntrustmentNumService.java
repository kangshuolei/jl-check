package com.hbsi.entrustment.service;

import com.hbsi.response.Response;

/**
 * 生成委托单号默认值
 * 
 * @author lijizhi
 *
 */
public interface EntrustmentNumService {

	/**
	 * 创建委托单号
	 * 
	 * @return
	 */
	Response<String> createEntrustmentDefaultNum(Integer batchNum);

	
}
