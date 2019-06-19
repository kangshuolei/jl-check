package com.hbsi.breakconclusion.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.breakconclusion.service.BreakConclusionService;
import com.hbsi.dao.BreakConclusionMapper;
import com.hbsi.domain.BreakConclusion;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
@Service
public class BreakConclusionServiceImpl implements BreakConclusionService {

	/**
	 * author:shazhou
	 */
	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private BreakConclusionMapper breakConclusionMapper;
	/**
	 * 保存单条拆股实验信息
	 */
	@Override
	public Response<Integer> saveBreakConclusion(BreakConclusion breakConclusion) {
		Integer message=0;
		try {
			breakConclusionMapper.insert(breakConclusion);
			message=1;
		}catch(Exception e) {
			logger.error("插入拆股实验结论表失败,原因是:{}",e);
			message = -1;
			throw new BaseException(ExceptionEnum.BREAK_CONCLUSION_SAVE_FAFILED);
		}
		return new Response<>(message);
	}
	
	/**
	 * 保存拆股实验信息列表
	 */
	@Override
	public Response<Integer> saveBreakConclusionList(List<BreakConclusion> conclusionList) {
		Integer message=0;
		try {
			for(int i=0;i<conclusionList.size(); i++) {
				breakConclusionMapper.insert(conclusionList.get(i));
			}
			message=1;
		}catch(Exception e) {
			logger.error("插入拆股实验结论表失败,原因是:{}",e);
			message = -1;
			throw new BaseException(ExceptionEnum.BREAK_CONCLUSION_SAVE_FAFILED);
		}
		return new Response<>(message);
	}
    /**
     * 更新单条拆股实验信息
     */
	@Override
	public Response<Integer> updateBreakConclusion(BreakConclusion breakConclusion) {
		Integer message=0;
		try {
			breakConclusionMapper.updateByPrimaryKeySelective(breakConclusion);
			message=1;
		}catch(Exception e) {
			message=-1;
			logger.error("更新拆股实验结论表失败,原因是:{}",e);
			throw new BaseException(ExceptionEnum.BREAK_CONCLUSION_UPDATE_FAFILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 根据委托单号查询结论信息
	 */
	@Override
	public List<BreakConclusion> selectConclusionByEntrust(String entrustNum) {
		Integer message=0;
		List<BreakConclusion> list = null;
		try {
			list = breakConclusionMapper.selectByEntrustNum(entrustNum);
			message=1;
		}catch(Exception e) {
			logger.error("查询拆股实验结论的过程中出错了,原因是:{}",e);
			message = -1;
			throw new BaseException(ExceptionEnum.BREAK_CONCLUSION_SELECT_FAFILED);
		}
		return list;
	}
    /**
     * 根据委托单号删除拆股实验结论信息
     */
	@Override
	public Response<Integer> deleteConclusion(String entrustNum) {
		Integer effectedNum=0;
		try {
			effectedNum = breakConclusionMapper.deleteByEntrustNum(entrustNum);
		} catch (Exception e) {
			logger.error("根据委托单号删除数据失败了，原因是：{}",e);
			effectedNum=-1;
			throw new BaseException(ExceptionEnum.BREAK_CONCLUSION_DELETE_FAFILED);
		}
		return new Response<>(effectedNum);
	}
    /**
     * 根据id更新拆股实验列表
     */
	@Override
	public Response<Integer> updateBreakConclusionList(List<BreakConclusion> conclusionList) {
		Integer message = 0;
		try {
			for(int i=1;i<conclusionList.size();i++) {
				breakConclusionMapper.updateByPrimaryKey(conclusionList.get(i));
			}
			message=1;
		} catch (Exception e) {
			message= -1;
			logger.error("更新多条拆股实验结论信息失败了，原因是:{}",e);
			throw new BaseException(ExceptionEnum.BREAK_CONCLUSION_UPDATE_FAFILED);
		}
		return new Response<>(message);
	}

}
