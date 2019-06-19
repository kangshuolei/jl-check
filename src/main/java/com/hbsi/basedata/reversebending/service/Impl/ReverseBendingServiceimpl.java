package com.hbsi.basedata.reversebending.service.Impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.reversebending.service.ReverseBendingService;
import com.hbsi.dao.ReverseBendingMapper;
import com.hbsi.domain.ReverseBending;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
@Service
public class ReverseBendingServiceimpl implements ReverseBendingService {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private ReverseBendingMapper reverseBendingMapper;
	/**
	 * 查询得到整张表数据
	 */
	@Override
	public Response<List<ReverseBending>> selectReverseBending() {
		int message=0;
		List<ReverseBending> list=null;
		try {
			list=reverseBendingMapper.selectReverseBending();
			message=1;
			logger.info("导入表成功了");
		} catch (Exception e) {
			message=-1;
			logger.error("导入表失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.REVERSEBENDING_SELECT_FAILED);
		}
		return new Response<List<ReverseBending>>(list);
	}
	/**
	 * 添加整张扭转弯曲表数据
	 */
	@Override
	public Response<Integer> saveReverseBending(List<ReverseBending> list) {
		int message=0;
		try {
			for(int i=0;i<list.size();i++) {
				reverseBendingMapper.insertSelective(list.get(i));
			}
			message=1;
			logger.info("添加数据成功了");
		} catch (Exception e) {
			message=-1;
			logger.error("导入表数据失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.REVERSEBENDING_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 保存单个扭转弯曲数据
	 */
	@Override
	public Response<Integer> saveSingleReverseBending(ReverseBending reverseBending) {
		int message=0;
		try {
				reverseBendingMapper.insertSelective(reverseBending);
			message=1;
			logger.info("添加数据成功了");
		} catch (Exception e) {
			message=-1;
			logger.error("添加数据失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.REVERSEBENDING_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 更改其中某条数据
	 */
	@Override
	public Response<Integer> updateReverseBending(ReverseBending reverseBending) {
		int message=0;
		try {
			reverseBendingMapper.updateByPrimaryKeySelective(reverseBending);
			message=1;
			logger.info("更改数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("更新数据失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.REVERSEBENDING_UPDATE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 根据id删除其中某条数据
	 */
	@Override
	public Response<Integer> deleteReverseBending(Integer id) {
		int message=0;
		try {
			reverseBendingMapper.deleteByPrimaryKey(id);
			message=1;
			logger.info("删除数据成功了");
		} catch (Exception e) {
			message =-1;
			logger.error("删除功败垂成，原因是：{}",e);
			throw new BaseException(ExceptionEnum.REVERSEBENDING_DELETE_FAILED);		
		}
		
		return new Response<Integer>(message);
	}
	/**
	 * 删除整张扭转弯曲表
	 */
	@Override
	public Response<Integer> deleteAllData() {
		int message=0;
		try {
			reverseBendingMapper.deleteAllData();
			message=1;
			logger.info("删除整张表数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("删除表数据失败了，我也看不懂为啥：{}",e);
			throw new BaseException(ExceptionEnum.REVERSEBENDING_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}

}
