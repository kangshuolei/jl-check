package com.hbsi.basedata.wireattributesgbt200672017.service;

import java.util.List;

import com.hbsi.domain.WireAttributesGbt200672017;
import com.hbsi.response.Response;

public interface WireAttributesService {

	/**
     * 导入整张表数据
     * @param list
     * @return
     */
	Response<Integer> saveWireattributes(List<WireAttributesGbt200672017> list);
	/**
	 * 添加单个数据
	 * @param single
	 * @return
	 */
	Response<Integer> saveSingleWireattribute(WireAttributesGbt200672017 single);
	/**
	 * 更新单个表数据
	 * @param single
	 * @return
	 */
	Response<Integer> updateWireattributes(WireAttributesGbt200672017 single);
	/**
	 * 根据id删除单个数据
	 * @param id
	 * @return
	 */
	Response<Integer> deleteWireattributes(Integer id);
	/**
	 * 删除整张表数据
	 * @return
	 */
	Response<Integer> deleteAllData();
	/**
	 * 查询得到整张表数据
	 * @return
	 */
	Response<List<WireAttributesGbt200672017>> selectWireattributes();
}
