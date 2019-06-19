package com.hbsi.steel.service;

import java.util.List;

import com.hbsi.domain.SteelWireData;
import com.hbsi.response.Response;
import com.hbsi.steel.entity.SteelWireRecord;

/**
 * 钢丝报告处理
 * @author lixuyang
 * @version 1.0，2018-09-29
 *
 */
public interface SteelService {
	
	/**
	 * 调入数据
	 * @param entrustmentNum
	 * @return
	 */
	Response<SteelWireRecord> selectSteelWireDataList(String info);
	 
	/**
	 * 修改钢丝数据（判定）
	 * @param list
	 * @return
	 */
	Response<List<SteelWireData>> updateSteelWire(List<SteelWireData> list);
}
