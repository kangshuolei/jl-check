package com.hbsi.entrustment.service;

import java.util.List;

import com.hbsi.domain.EntrustmentData;
import com.hbsi.domain.VersonNumber;
import com.hbsi.response.Response;

/**
 * 委托单管理
 * @author lixuyang
 * @version 1.0，2018-09-27
 *
 */
public interface EntrustmentDataService {
	
	/**
	 * 模糊查询委托单数据
	 * @param entrustmentNumber
	 * @return
	 */
	Response<List<EntrustmentData>> selectEntData(String entrustmentNumber);
	
	/**
	 * 查询或新建委托单报告
	 * @param info
	 * @return
	 */
	Response<EntrustmentData> selectOrSaveEntData(String info);
	
	/**
	 * 添加委托单数据和钢丝数据（提交按钮）
	 * @param info
	 * @return
	 */
	Response<Integer> saveEntDataAndSteelData(String info);
	
	/**
	 * 查询版本号
	 * @return
	 */
	Response<VersonNumber> selectVersionNum();
	
	/**
	 * 修改版本号
	 * @param info
	 * @return
	 */
	Response<Integer> updateVersionNum(String info);
}
