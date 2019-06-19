package com.hbsi.basedata.steeltensilestrength.service;

import java.util.List;

import com.hbsi.domain.SteelTensileStrength;
import com.hbsi.response.Response;

public interface SteelTSservice {
    /**
     * 导入整张表数据
     * @param list
     * @return
     */
	Response<Integer> saveSteelTS(List<SteelTensileStrength> list);
	/**
	 * 添加单条数据
	 * @param steelTensileStrength
	 * @return
	 */
	Response<Integer> saveSingleSteelTS(SteelTensileStrength steelTensileStrength);
	/**
	 * 更改某条数据信息
	 * @param steelTensileStrength
	 * @return
	 */
	Response<Integer> updateSteelTS(SteelTensileStrength steelTensileStrength);
	/**
	 * 根据id删除某条信息
	 * @param id
	 * @return
	 */
	Response<Integer> deleteSteelTS(Integer id);
	/**
	 * 清空整张表
	 * @return
	 */
	Response<Integer> deleteAllData();
	/**
	 * 查找得到整张表信息
	 * @return
	 */
	Response<List<SteelTensileStrength>> selectSteelTS();
}
