package com.hbsi.dao;

import java.util.List;

import com.hbsi.domain.SampleRecord;
import com.hbsi.sample.entity.SampleRecords;

public interface SampleRecordMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(SampleRecord record);

	int insertSelective(SampleRecord record);

	SampleRecord selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SampleRecord record);

	int updateByPrimaryKey(SampleRecord record);
	/**
	 * 查询试验记录
	 * @param sampleRecord
	 * @return
	 */
	List<SampleRecord> selectSampleRecordList(SampleRecord sampleRecord);
	/**
	 * 查询错误处理列表
	 * @param sampleRecord
	 * @return
	 */
	List<SampleRecord> selectSampleErrorList(SampleRecord sampleRecord);
	
	/**
	 * 查询错误数据
	 * @param sampleBatch
	 * @return
	 */
	List<SampleRecord> selectErrorData(SampleRecord sample);
	/**
	 * 根据ID删除错误数据
	 * @param id
	 * @return
	 */
	Integer deleteSampleError(Integer id);
	/**
	 * 根据试样批次添加委托单号（批量更新）
	 * @param sampleRecord
	 * @return
	 */
	Integer insertSampleEntNum(SampleRecord sampleRecord);
	/**
	 * 查询所有的批次号
	 * @return
	 */
	List<SampleRecord> selectSampleBatch();
	/**
	 * 根据委托单号查询实验数据
	 * @param entrustmentNum
	 * @return
	 */
	List<SampleRecord> selectSampleData(String entrustmentNum);
	/**
	 * 根据批次号查询实验数据
	 * @param sampleBatch
	 * @return
	 */
	List<SampleRecord> selectData(Integer sampleBatch);
	
	/**
	 * 查询委托单号是否已存在
	 * @param sampleRecord
	 * @return
	 */
	List<SampleRecord> selectEntNum(String entrustmentNumber);
	/**
	 * 根据批次号删除委托单（批量删除）
	 * @param sampleBatch
	 * @return
	 */
	Integer deleteEntNum(Integer sampleBatch);
	/**
	 * 根据批次号删除委托单（批量删除）
	 * @param sampleBatch
	 * @return
	 */
	Integer deleteEntrustmentReally(Integer sampleBatch);
	
	/**
	 * 根据id修改批次号（批量修改）
	 * @param id
	 * @return
	 */
	Integer updateSampleBatch(SampleRecords sampleRecords);
	/**
	 * 根据批次号和
	 */
	List<SampleRecord> selectBySampleAndType(SampleRecord s);
}