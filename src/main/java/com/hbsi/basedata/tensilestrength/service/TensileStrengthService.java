package com.hbsi.basedata.tensilestrength.service;

import java.util.List;

import com.hbsi.domain.TensileStrength;
import com.hbsi.response.Response;

public interface TensileStrengthService {

	/**
	 *  导入抗拉强度表到数据库
	 * @param tensileStrength
	 * @return
	 */
	Response<Integer> saveTensileStrength(List<TensileStrength> tensileStrengthList);
	/**
	 *  添加单个抗拉强度数据
	 * @param tensileStregth
	 * @return
	 */
	Response<Integer> saveSingleTensileStrength(TensileStrength tensileStregth);
	/***
	 *  根据id删除单条数据
	 * @param id
	 * @return
	 */
	Response<Integer> deleteTensileStrength(Integer id);
	/**
	 *  更改单条数据
	 * @param tensileStrength
	 * @return
	 */
	Response<Integer> updateTensileStrength(TensileStrength tensileStrength);
	/**
	 * 
	 * 查询抗拉强度整张表的数据
	 * @return
	 */
	Response<List<TensileStrength>> selectTensileStrength();
	/**
	 * 删除整张抗拉力强度表
	 * @return
	 */
	Response<Integer> deleteAllData();
}
