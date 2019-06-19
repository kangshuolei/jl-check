package com.hbsi.basedata.zinclayerweight.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.zinclayerweight.service.ZincLayerWeightService;
import com.hbsi.dao.ZincLayerWeightMapper;
import com.hbsi.domain.ZincLayerWeight;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
@Service
public class ZincLayerWeightServiceImpl implements ZincLayerWeightService{

	/**
	 * author:周袁正
	 */
	private final Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ZincLayerWeightMapper zincLayerWeightMapper;
	
	/**
	 * 导入整张锌层重量表
	 */
	@Override
	public Response<Integer> saveZincLayerWeight(List<ZincLayerWeight> zincLayerWeightList) {
		int message=0;
		try {
			for(int i=0;i<zincLayerWeightList.size();i++) {
				zincLayerWeightMapper.insertSelective(zincLayerWeightList.get(i));
			}
			message=1;
			logger.info("导入锌层重量数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("很遗憾，导入失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.ZINCLAYERWEIGTH_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
    /**
     * 添加单条锌层重量数据
     */
	@Override
	public Response<Integer> saveSingleZincLayerWeight(ZincLayerWeight zincLayerWeight) {
		int message=0;
		try {
			zincLayerWeightMapper.insertSelective(zincLayerWeight);
			message=1;
			logger.info("添加单条锌层重量数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("添加锌层重量数据失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.ZINCLAYERWEIGTH_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
     *删除整张锌层数据 
     */
	@Override
	public Response<Integer> deleteAllData() {
		int message=0;
		try {
			zincLayerWeightMapper.deleteAllData();
			logger.info("删除整张表数据");
			message=1;
		} catch (Exception e) {
			message=-1;
			logger.error("删除失败，原因是：{}",e);
			throw new BaseException(ExceptionEnum.ZINCLAYERWEIGTH_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
     * 更改单条锌层重量数据
     */
	@Override
	public Response<Integer> updateZincLayerWeight(ZincLayerWeight zincLayerWeight) {
		int message=0;
		try {
			zincLayerWeightMapper.updateByPrimaryKey(zincLayerWeight);
			message=1;
			logger.info("更新该条锌层重量信息成功");
		} catch (Exception e) {
			message=-1;
			logger.error("更新失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.ZINCLAYERWEIGTH_UPDATE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
     * 根据id删除单条锌层重量数据
     */
	@Override
	public Response<Integer> deleteZincLayerWeight(Integer id) {
		int message=0;
		try {
			zincLayerWeightMapper.deleteByPrimaryKey(id);
			message=1;
			logger.info(" 删除单条锌层重量信息成功");
		} catch (Exception e) {
			message=-1;
			logger.error("删除失败了，原因是：{}",e);
		}
		return new Response<Integer>(message);
	}
	/**
     * 查询整张锌层重量数据
     */
	@Override
	public Response<List<ZincLayerWeight>> selectZincLayerWeight() {
		int message=0;
		List<ZincLayerWeight> list=null;
		try {
			list = zincLayerWeightMapper.selectZincLayerWeight();
			message=1;
			logger.info("查询锌层重量表成功，表数据为：{}",list);
		} catch (Exception e) {
			message=-1;
			logger.error("查询失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.ZINCLAYERWEIGTH_SELECT_FAILED);
		}
		return new Response<List<ZincLayerWeight>>(list);
	}

}
