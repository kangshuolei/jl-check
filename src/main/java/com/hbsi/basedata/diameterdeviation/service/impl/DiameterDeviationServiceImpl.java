package com.hbsi.basedata.diameterdeviation.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.diameterdeviation.service.DiameterDeviationService;
import com.hbsi.dao.DiameterDeviationMapper;
import com.hbsi.domain.DiameterDeviation;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
@Service
public class DiameterDeviationServiceImpl implements DiameterDeviationService{

	/**
	 * author:zhouyuanzheng
	 */
	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private DiameterDeviationMapper diameterDeviationMapper;
	
	/**
	 * 将直径表导入数据库
	 */
	@Override
	public Response<Integer> saveDiameterDeviation(List<DiameterDeviation> diameterDeviationList) {
		int  message=0;
		try {
			for(int i=0; i<diameterDeviationList.size();i++) {
				diameterDeviationMapper.insert(diameterDeviationList.get(i));
			}
			message=1;
			logger.info("添加表成功");
		} catch (Exception e) {
			message=-1;
			logger.error("添加数据失败，原因是：{}",e);
			throw new BaseException(ExceptionEnum.DIAMETERDEVIATION_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 添加单条直径数据
	 */
	@Override
	public Response<Integer> saveSingleDiameterDeviation(DiameterDeviation diameterDeviation) {
		int message=0;
		try {
			diameterDeviationMapper.insertSelective(diameterDeviation);
			message=1;
			logger.info("直径表数据导入到数据库成功");
		} catch (Exception e) {
			message=-1;
			logger.error("直径表数据导入失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.DIAMETERDEVIATION_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 更新直径数据
	 */
	@Override
	public Response<Integer> updateDiameterDeviation(DiameterDeviation diameterDeviation) {
		int message=0;
		try {
			diameterDeviationMapper.updateByPrimaryKeySelective(diameterDeviation);
			message=1;
			logger.info("更新直径表信息成功");
		} catch (Exception e) {
			message=-1;
			logger.error("更新表信息出错了");
			throw new BaseException(ExceptionEnum.DIAMETERDEVIATION_UPDATE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 根据id删除某条直径数据
	 */
	@Override
	public Response<Integer> deleteDiameterDeviation(Integer id) {
		int message=0;
		try {
			diameterDeviationMapper.deleteByPrimaryKey(id);
			message=1;
			logger.info("根据id：{} 删除表信息，成功",id);
		} catch (Exception e) {
			message=-1;
			logger.error("删除表信息失败，原因是：{}",e);
			throw new BaseException(ExceptionEnum.DIAMETERDEVIATION_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 删除所有的直径数据
	 */
	@Override
	public Response<Integer> deleteAllData() {
		int message=0;
		try {
			diameterDeviationMapper.deleteAllData();
			message=1;
			logger.info("删除直径表成功");
		} catch (Exception e) {
			message=-1;
			logger.error("删除直径表出错，原因是：{}",e);
			throw new BaseException(ExceptionEnum.DIAMETERDEVIATION_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 查找得到整张直径表
	 */
	@Override
	public Response<List<DiameterDeviation>> selectDiameterDeviation() {
		int message=0;
		List<DiameterDeviation> list=new ArrayList<DiameterDeviation>();
		try {
			list = diameterDeviationMapper.selectDiameterDeviation();
			message=1;
			logger.info("查询得到直径表为：{}",list);
		} catch (Exception e) {
			message=-1;
			logger.error("查询失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.DIAMETERDEVIATION_SELECT_FAILED);
		}
		return new Response<List<DiameterDeviation>>(list);
	}

}
