package com.hbsi.breakconclusion.service;

import java.util.List;

import com.hbsi.domain.BreakConclusion;
import com.hbsi.response.Response;

public interface BreakConclusionService {
    /**
     * 保存单条拆股实验结论信息
     * @param breakConclusion
     * @return
     */
	public Response<Integer> saveBreakConclusion(BreakConclusion breakConclusion);
	/**
	 * 查询拆股实验结论列表
	 * @param entrust
	 * @return
	 */
	public List<BreakConclusion> selectConclusionByEntrust(String entrust);
	/**
	 * 根据委托单号删除结论信息
	 * @param entrustNum
	 * @return
	 */
	public Response<Integer> deleteConclusion(String entrustNum);
    /**
     * 更新单条拆股实验结论信息
     * @param breakConclusion
     * @return
     */
	public Response<Integer> updateBreakConclusion(BreakConclusion breakConclusion);
    /**
     * 根据id更新拆股实验结论信息列表
     * @param conclusionList
     * @return
     */
	public Response<Integer> updateBreakConclusionList(List<BreakConclusion> conclusionList);
	/**
	 * 保存结论信息列表
	 * @param conclusionList
	 * @return
	 */
	public Response<Integer> saveBreakConclusionList(List<BreakConclusion> conclusionList);
}
