package com.hbsi.basedata.steeltensilestrength.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.steeltensilestrength.service.SteelTSservice;
import com.hbsi.dao.SteelTensileStrengthMapper;
import com.hbsi.domain.SteelTensileStrength;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
@Service
public class SteelTSServiceImpl implements SteelTSservice {
    
	/**
	 * author:zhouyuanzheng
	 */
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private SteelTensileStrengthMapper steelTSMapper;
	
	
	@Override
	public Response<Integer> saveSteelTS(List<SteelTensileStrength> list) {
		int message=0;
		try {
			for(int i=0;i<list.size();i++) {
				steelTSMapper.insert(list.get(i));	
			}
			message=1;
			logger.info("添加数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("添加数据失败了");
			throw new BaseException(ExceptionEnum.STEELTENSILESTRENGTH_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<Integer> saveSingleSteelTS(SteelTensileStrength steelTensileStrength) {
		int message=0;
		try {
			steelTSMapper.insert(steelTensileStrength);	
			message=1;
			logger.info("添加数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("添加数据失败了");
			throw new BaseException(ExceptionEnum.STEELTENSILESTRENGTH_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<Integer> updateSteelTS(SteelTensileStrength steelTensileStrength) {
		int message=0;
		try {
			steelTSMapper.updateByPrimaryKeySelective(steelTensileStrength);	
			message=1;
			logger.info("更新数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("更新数据失败了");
			throw new BaseException(ExceptionEnum.STEELTENSILESTRENGTH_UPDATE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<Integer> deleteSteelTS(Integer id) {
		int message=0;
		try {
			steelTSMapper.deleteByPrimaryKey(id);
			message=1;
			logger.info("删除数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("删除数据失败了");
			throw new BaseException(ExceptionEnum.STEELTENSILESTRENGTH_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<Integer> deleteAllData() {
		int message=0;
		try {
			steelTSMapper.deleteAllData();
			message=1;
			logger.info("删除数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("删除数据失败了");
			throw new BaseException(ExceptionEnum.STEELTENSILESTRENGTH_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<List<SteelTensileStrength>> selectSteelTS() {
		int message=0;
		List<SteelTensileStrength> list=new ArrayList<>();
		try {
			list = steelTSMapper.selectSteelTensileStrength();
			message=1;
			logger.info("查找数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("查找数据失败了");
			throw new BaseException(ExceptionEnum.STEELTENSILESTRENGTH_SELECT_FAILED);
		}
		return new Response<List<SteelTensileStrength>>(list);
	}

}
