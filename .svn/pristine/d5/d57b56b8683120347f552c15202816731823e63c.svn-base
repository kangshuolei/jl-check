package com.hbsi.basedata.tensilestrength.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.tensilestrength.service.TensileStrengthService;
import com.hbsi.dao.TensileStrengthMapper;
import com.hbsi.domain.TensileStrength;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;



@Service
public class TensileStrengthServiceImpl implements TensileStrengthService {

	/**
	 * author: 周袁正
	 */

	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private TensileStrengthMapper tensileStrengthMapper;
	/**
	 * 保存整张抗拉力强度表数据
	 */
	@Override
	public Response<Integer> saveTensileStrength(List<TensileStrength> tensileStrengthList) {
		int message=0;
		try {
			for(int i=0;i<tensileStrengthList.size();i++) {
			tensileStrengthMapper.insert(tensileStrengthList.get(i));	
			}
			logger.info("导入抗拉强度表到数据库成功");
			message=1;
		} catch (Exception e) {
			logger.error("妈耶，导入失败了，失败原因：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.TENSILESTRENGTH_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
    /**
     *  添加单条抗拉力强度数据
     */
	@Override
	public Response<Integer> saveSingleTensileStrength(TensileStrength tensileStregth) {
		int message=0;
		try {
			tensileStrengthMapper.insert(tensileStregth);
			logger.info("添加单条抗拉强度数据成功");
			message=1;
		} catch (Exception e) {
			logger.error("添加单条数据失败，失败原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.TENSILESTRENGTH_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
    /**
     * 根据id删除单条数据
     */
	@Override
	public Response<Integer> deleteTensileStrength(Integer id) {
		int message=0;
		try {
			tensileStrengthMapper.deleteByPrimaryKey(id);
			logger.info("根据id：{}删除单条数据成功",id);
			message=1;
		} catch (Exception e) {
			logger.error("奇了怪哉，竟然失败了，失败原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.TENSILESTRENGTH_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
    /**
     * 更新整张抗拉力强度表
     */
	@Override
	public Response<Integer> updateTensileStrength(TensileStrength tensileStrength) {
		int message=0;
		try {
			tensileStrengthMapper.updateByPrimaryKeySelective(tensileStrength);
			message=1;
			logger.info("更新单条数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error(" 嘿嘿嘿，失败了，原因是{}",e);
			throw new BaseException(ExceptionEnum.TENSILESTRENGTH_UPDATE_FAILED);
		}
		return new Response<Integer>(message);
	}
    /**
     *   查询整张抗拉力强度表
     */
	@Override
	public Response<List<TensileStrength>> selectTensileStrength() {
		int message=0;
		List<TensileStrength> list=null;
		try {
			list = tensileStrengthMapper.selectAllData();
			message=1;
			logger.info("查询得到整张抗拉强度表数据：{}",list);
		} catch (Exception e) {
			message=-1;
			logger.error("查询失败，原因是：{}",e);
			throw new BaseException(ExceptionEnum.TENSILESTRENGTH_SELECT_FAILED);
		}
		return new Response<List<TensileStrength>>(list);
	}
	/**
	 * 删除所有的抗拉力强度表数据
	 * @return
	 */
	@Override
	public Response<Integer> deleteAllData() {
		int message=0;
		try {
			tensileStrengthMapper.deleteAllData();
			message=1;
			logger.info("删除整张表数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error(" 删除整张抗拉力强度表出错了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.TENSILESTRENGTH_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}

}
