package com.hbsi.entrustment.service;

import com.hbsi.domain.Entrustment;
import com.hbsi.response.Response;

public interface SaveEntrustmenService {

	/**
	 * 生成新的批次号，保存到entrustment表中
	 */
	public Response<Integer> saveNewBatch();
	/** 
	 * 保存修改过的委托单号
	 * @param entrustment
	 * @return
	 */
	public Response saveEntrustment(Entrustment entrustment);
	/**
	 * 得到倒序排列的entrustment表中所有信息
	 * @return
	 */
	public Response<Object> selectEntrustmentList(Integer offset, Integer limit);
	/**
	 * 根据传过来的参数一次生成多个新批次号
	 * @return
	 */
	public Response<Integer> saveManyNewBatch(String info);
}
