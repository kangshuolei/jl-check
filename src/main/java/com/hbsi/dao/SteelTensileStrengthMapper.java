package com.hbsi.dao;

import java.util.List;

import com.hbsi.domain.SteelTensileStrength;

public interface SteelTensileStrengthMapper {

	int deleteByPrimaryKey(Integer id);
	/**
	 * 删除整张表内容，让我们重新开始吧
	 * @return int
	 */
	int deleteAllData();

	int insert(SteelTensileStrength record);

	int insertSelective(SteelTensileStrength record);

	SteelTensileStrength selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(SteelTensileStrength record);

	int updateByPrimaryKey(SteelTensileStrength record);
	
	SteelTensileStrength selectSteelTSData(SteelTensileStrength sts);//查询抗拉强度范围值
	
//	SteelTensileStrength selectSteelTSData2(SteelTensileStrength sts);//降级重新判定（最大值）
	
	List<SteelTensileStrength> selectSTSDataList(SteelTensileStrength sts);//查询符合规定的List
	/**
	 * 查找得到整张表内容
	 * @return list
	 */
	List<SteelTensileStrength> selectSteelTensileStrength();
	
}