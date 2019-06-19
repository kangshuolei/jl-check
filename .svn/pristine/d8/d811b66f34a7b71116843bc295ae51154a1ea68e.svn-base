package com.hbsi.basedata.wireattributesgbt200672017.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.wireattributesgbt200672017.service.WireAttributesService;
import com.hbsi.dao.WireAttributesGbt200672017Mapper;
import com.hbsi.domain.WireAttributesGbt200672017;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
@Service
public class WireAttributesServiceImpl implements WireAttributesService {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WireAttributesGbt200672017Mapper wireAttributesGbt200672017Mapper;
	/**
	 * 导入整张表
	 */
	@Override
	public Response<Integer> saveWireattributes(List<WireAttributesGbt200672017> list) {
		int message=0;
		try {
			for(int i=0;i<list.size();i++) {
				wireAttributesGbt200672017Mapper.insertSelective(list.get(i));
			}
			message=1;
			logger.info("添加表成功");
		} catch (Exception e) {
			message=-1;
			logger.error("添加表失败，原因是：{}",e);
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 添加单个数据
	 */
	@Override
	public Response<Integer> saveSingleWireattribute(WireAttributesGbt200672017 single) {
		int message=0;
		try {
			wireAttributesGbt200672017Mapper.insertSelective(single);
			message=1;
			logger.info("添加单条数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("添加单条数据失败，原因是：{}",e);
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 更改单条数据
	 */
	@Override
	public Response<Integer> updateWireattributes(WireAttributesGbt200672017 single) {
		int message=0;
		try {
			wireAttributesGbt200672017Mapper.updateByPrimaryKey(single);
			message=1;
			logger.info("更新表数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("添加表数据失败因为：{}",e);
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_UPDATE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 根据id删除单条数据
	 */
	@Override
	public Response<Integer> deleteWireattributes(Integer id) {
		int message=0;
		try {
			wireAttributesGbt200672017Mapper.deleteByPrimaryKey(id);
			message=1;
			logger.info("删除成功了");
		} catch (Exception e) {
			message=-1;
			logger.error("删除失败了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 删除整张表数据
	 */
	@Override
	public Response<Integer> deleteAllData() {
		int message=0;
		try {
			wireAttributesGbt200672017Mapper.deleteAllData();
			message=1;
			logger.info("删除数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error("删除表数据失败了");
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 查找得到整张表数据
	 */
	@Override
	public Response<List<WireAttributesGbt200672017>> selectWireattributes() {
		int message=0;
		List<WireAttributesGbt200672017> list=null;
		try {
			list = wireAttributesGbt200672017Mapper.selectWireAttributesGbt200672017();
			message=1;
			logger.info("查询表数据成功，数据为：{}",list);
		} catch (Exception e) {
			message=-1;
			logger.error("查询表数据失败了");
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SELECT_FAILED);
		}
		return new Response<List<WireAttributesGbt200672017>>(list);
	}
}
