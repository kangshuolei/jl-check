package com.hbsi.basedata.reversebending.service;

import java.util.List;

import com.hbsi.domain.ReverseBending;
import com.hbsi.response.Response;

public interface ReverseBendingService {
    /**
     * 查找得到整张扭转弯曲表
     * @return
     */
	Response<List<ReverseBending>> selectReverseBending();
	/**
	 * 导入整张扭转弯曲表
	 * @param list
	 * @return
	 */
	Response<Integer> saveReverseBending(List<ReverseBending> list);
	/**
	 * 添加单个扭转弯曲数据
	 * @param reverseBending
	 * @return
	 */
	Response<Integer> saveSingleReverseBending(ReverseBending reverseBending);
	/**
	 * 更改单条扭转弯曲数据
	 * @param reverseBending
	 * @return
	 */
	Response<Integer> updateReverseBending(ReverseBending reverseBending);
	/**
	 * 根据id删除单条扭转弯曲数据
	 * @param id
	 * @return
	 */
	Response<Integer> deleteReverseBending(Integer id);
	/**
	 * 删除整张扭转弯曲表，为了更好的重新开始
	 * @return
	 */
	Response<Integer> deleteAllData();
}
