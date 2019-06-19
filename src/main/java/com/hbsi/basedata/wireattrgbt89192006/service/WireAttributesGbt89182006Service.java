package com.hbsi.basedata.wireattrgbt89192006.service;

import java.util.List;

import com.hbsi.domain.WireAttributesGBT89182006;
import com.hbsi.response.Response;

public interface WireAttributesGbt89182006Service {
    /**
     * 保存整张表数据
     * @param list
     * @return
     */
	Response<Integer> saveWireAttrList(List<WireAttributesGBT89182006> list);
	/**
	 * 添加单条数据
	 * @param wireAttr
	 * @return
	 */
	Response<Integer> saveSingleWireAttr(WireAttributesGBT89182006 wireAttr);
	/**
	 * 查询得到整张表数据
	 * @return
	 */
	Response<List<WireAttributesGBT89182006>> selectWireAttr();
	/**
	 * 更新单条数据
	 * @param wireAttr
	 * @return
	 */
	Response<Integer> updateWireAttr(WireAttributesGBT89182006 wireAttr);
	/**
	 * 删除单条数据
	 * @param id
	 * @return
	 */
	Response<Integer> deleteSingleWireAttr(Integer id);
	/**
	 * 删除所有数据
	 * @return
	 */
	Response<Integer> deleteAllData();
}
