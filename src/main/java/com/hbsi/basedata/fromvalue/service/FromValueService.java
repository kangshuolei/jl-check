package com.hbsi.basedata.fromvalue.service;

import java.util.List;

import com.hbsi.domain.FromValue;
import com.hbsi.response.Response;

public interface FromValueService {

	/**
	 * 以list方式添加表单数据
	 * @param fromValue
	 * @return
	 */
	Response<Integer> saveFromValue(List<FromValue> formValueList);
	/**
	 * 根据表单数据删除表单数据
	 * @param id
	 * @return
	 */
	Response<Integer> deleteFromValue(Integer id);
	/**
	 *  根据用户id更改用户信息
	 * @param fromValue
	 * @return
	 */
	Response<Integer> updateFromValue(FromValue fromValue);
	/**
	 *  查询得到整张表的信息
	 * @return
	 */
	Response<List<FromValue>> selectFromValue();
	/**
	 * 添加单条表单数据
	 * @param fromValue
	 * @return
	 */
	Response<Integer> saveSingleFromValue(FromValue fromValue);
	/**
	 *  删除表单整张表
	 * @return
	 */
	Response<Integer> deleteAllData();
	/**
	 * 根据标准号查询FromValue列表
	 * @param standardNum
	 * @return
	 */
	List<FromValue> selectByStandardNum(String standardNum);
	
}
