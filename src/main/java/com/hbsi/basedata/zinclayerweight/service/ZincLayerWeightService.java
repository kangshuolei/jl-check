package com.hbsi.basedata.zinclayerweight.service;

import java.util.List;

import com.hbsi.domain.ZincLayerWeight;
import com.hbsi.response.Response;

public interface ZincLayerWeightService {

	/**
	 * 添加锌层重量表数据
	 * @param zincLayerWeightList
	 * @return
	 */
	Response<Integer> saveZincLayerWeight(List<ZincLayerWeight> zincLayerWeightList);
	/**
	 * 保存单条锌层重量数据
	 * @param zincLayerWeight
	 * @return
	 */
	Response<Integer> saveSingleZincLayerWeight(ZincLayerWeight zincLayerWeight);
	/**
	 * 删除整张表数据
	 * @return
	 */
	Response<Integer> deleteAllData();
	/**
	 * 更改锌层重量数据
	 * @param zincLayerWeight
	 * @return
	 */
	Response<Integer> updateZincLayerWeight(ZincLayerWeight zincLayerWeight);
	/**
	 * 根据id删除锌层重量数据
	 * @param id
	 * @return
	 */
	Response<Integer> deleteZincLayerWeight(Integer id);
	/**
	 * 得到整张锌层重量数据
	 * @return
	 */
	Response<List<ZincLayerWeight>> selectZincLayerWeight();
	
	
	
}
