package com.hbsi.basedata.wireattributesgbt201182017.service;

import java.util.List;

import com.hbsi.domain.WireAttributesGbt201182017;
import com.hbsi.response.Response;

public interface Wireattributesgbt201182017Service {
    /**
     * 导入整张表数据
     * @param list
     * @return
     */
	Response<Integer> saveWireattributes(List<WireAttributesGbt201182017> list);
	/**
	 * 添加单个数据
	 * @param single
	 * @return
	 */
	Response<Integer> saveSingleWireattribute(WireAttributesGbt201182017 single);
	/**
	 * 更新单个表数据
	 * @param single
	 * @return
	 */
	Response<Integer> updateWireattributes(WireAttributesGbt201182017 single);
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
	Response<List<WireAttributesGbt201182017>> selectWireattributes();
}
