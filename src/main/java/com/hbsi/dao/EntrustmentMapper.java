package com.hbsi.dao;

import java.util.List;

import com.hbsi.domain.Entrustment;

public interface EntrustmentMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(Entrustment record);//添加委托单

	int insertSelective(Entrustment record);

	Entrustment selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(Entrustment record);//修改委托单

	int updateByPrimaryKey(Entrustment record);
	
//	int deleteEntrustment(Entrustment entrustment);//删除委托单
	
	List<Entrustment> selectEntrustmentList();//查询委托单列表
	
	Entrustment selectEntrustmentNumber(String entNum);//查询委托单号是否已存在
	/**
	 * 模糊查询
	 * @param ent
	 * @return
	 */
	List<Entrustment> selectEnts(Entrustment ent);
	/**
	 * 查询表中数据总条数
	 * @return
	 */
	Integer selectCount();
	/**
	 * 查询最老的一条数据
	 * @return
	 */
	Entrustment selectOldestEntrust();
	/**
	 * 查询最新的一条数据
	 * @return
	 */
	Entrustment selectLatestEntrust();
	/**
	 * 查询所有，降序排列
	 * @return
	 */
	List<Entrustment> selectAllByDesc();
	/**
	 * 根据批次号查询对应的委托单号
	 * @param sampleBatch
	 * @return
	 */
	Entrustment selectEntrustByBatchNum(Integer sampleBatch);
	/**
	 * 根据批次号查询,不关心state值。
	 * @param sampleBatch
	 * @return
	 */
	List<Entrustment> selectRealByBatch(Integer sampleBatch);
	/**
	 * 根据批次号查询对应的委托单号
	 * @param sampleBatch
	 * @return
	 */
	Entrustment selectEntrustByBatch(Integer sampleBatch);
	/**
	 * 真正删除一条数据
	 * @param entrustment
	 * @return
	 */
	Integer deleteEntrustmentReally(Integer sampleBatch);
	
	/**
	 * 通过设置state=999，模拟删除数据
	 * @param entrustment
	 * @return
	 */
	Integer deleteEntrustment(Entrustment entrustment);
}