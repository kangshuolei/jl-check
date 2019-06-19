package com.hbsi.sample.service;

import java.util.List;

import com.hbsi.domain.SampleRecord;
import com.hbsi.domain.SteelWireData;
import com.hbsi.response.PageResult;
import com.hbsi.response.Response;
import com.hbsi.sample.entity.SampleRecords;

/**
 * 试验数据管理
 * @author lixuyang
 * @version 1.0，2018-09-27
 *
 */
public interface SampleRecordService {
	
	/**
	 * 查询试验记录
	 * @param info
	 * @return
	 */
	Response<PageResult> selectSampleRecordList(String info);
	
	/**
	 * 错误处理列表
	 * @param info
	 * @return
	 */
	Response<PageResult> selectSampleErrorList(String info);
	/**
	 * 重置钢丝试验编号
	 * @param sampleBatch
	 * @param type
	 * @return
	 */
	Response<SampleRecord> resetSampleNumber(Integer sampleBatch,String type);
	
	/**
	 * 删除错误数据
	 * @param info
	 * @return
	 */
	Response<Integer> deleteSampleError(String info);
	/**
	 * 查询试样批次和委托单号
	 * @return
	 */
	Response<List<SampleRecord>> selectSampleBatch();
	
	/**
	 * 修改批次号
	 * @return
	 */
	Response<SampleRecords> updateSampleBatch(String info);
	/**
	 * 查询错误的批次号
	 * @param sampleBatch
	 * @return
	 */
	Response<PageResult> selectErrorDataList(Integer sampleBatch,String type,String machineNumber,Integer offset,Integer limit);
	/**
	 * 修改试样编号
	 * @param info
	 * @return
	 */
	Response<SampleRecord> updateSampleNumber(String info);

}
