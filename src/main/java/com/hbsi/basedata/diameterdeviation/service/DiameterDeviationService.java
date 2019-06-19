package com.hbsi.basedata.diameterdeviation.service;

import java.util.List;

import com.hbsi.domain.DiameterDeviation;
import com.hbsi.response.Response;

public interface DiameterDeviationService {

	/**
	 * 将直径表导入数据库
	 * @param diameterDeviationlist
	 * @return
	 */
	Response<Integer> saveDiameterDeviation(List<DiameterDeviation> diameterDeviationList);
	/**
	 * 添加单条直径数据
	 * @param diameterDeviation
	 * @return
	 */
	Response<Integer> saveSingleDiameterDeviation(DiameterDeviation diameterDeviation);
	/**
	 * 更新直径数据
	 * @param diameterDeviation
	 * @return
	 */
	Response<Integer> updateDiameterDeviation(DiameterDeviation diameterDeviation);
	/**
	 * 根据id删除某条直径数据
	 * @param id
	 * @return
	 */
	Response<Integer> deleteDiameterDeviation(Integer id);
	/**
	 * 删除整张直径表
	 * @return
	 */
	Response<Integer> deleteAllData();
	/**
	 * 查找得到整张直径表
	 * @return
	 */
	Response<List<DiameterDeviation>> selectDiameterDeviation();
}
