package com.hbsi.dao;

import java.util.List;

import com.hbsi.domain.EntrustmentData;

public interface EntrustmentDataMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(EntrustmentData record);//添加报告数据

	int insertSelective(EntrustmentData record);

	EntrustmentData selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(EntrustmentData record);//补全报告数据

	int updateByPrimaryKey(EntrustmentData record);
	/**
	 * 根据委托单号，将该批次数据状态设置成999.
	 * @param EntrustNum
	 * @return
	 */
	int updateByEntrustNum(String entrustNum);
	/**
	 * 真正删除这个委托单号的数据
	 * @param entrustNum
	 * @return
	 */
	int deleteByEntrustNumReally(String entrustNum);
	
	EntrustmentData selectEntrustmentData(String entrustmentNumber);//查询报告是否已存在
	
	List<EntrustmentData> selectEntsData(String entrustmentNumber);//模糊查询委托单数据
}