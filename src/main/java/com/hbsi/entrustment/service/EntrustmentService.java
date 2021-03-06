package com.hbsi.entrustment.service;

import java.util.List;

import com.hbsi.domain.Entrustment;
import com.hbsi.entrustment.entity.EntInfo;
import com.hbsi.response.Response;

/**
 * 委托单管理
 * @author lixuyang
 * @version 1.0，2018-09-27
 *
 */
public interface EntrustmentService {
	
	/**
	 * 添加委托单号
	 * @param entrustment
	 * @return
	 */
	Response<Integer> saveEntrustment(Entrustment entrustment);
	
	/**
	 * 修改委托单号
	 * @param entrustment
	 * @return
	 */
	Response<Integer> updateEntrustment(EntInfo ei);
	
	/**
	 * 删除委托单号
	 * @param entrustment
	 * @return
	 */
	Response<Integer> deleteEntrustment(Entrustment entrustment);
	
	/**
	 * 查询委托单号列表
	 * @return
	 */
	Response<List<Entrustment>> selectEntrustmentList();
	/**
	 * 模糊查询委托单号
	 * @param ent
	 * @return
	 */
	public Response<List<Entrustment>> selectEntrustment(String ent);
	/**
	 * 该方法shazhou所写，用途是根据批次号查询对应的委托单号
	 * @param sampleBatch
	 * @return
	 */
	public Response<Entrustment> selectEntruByBatchNum(Integer sampleBatch);
}
