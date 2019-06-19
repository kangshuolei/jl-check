package com.hbsi.basedata.wireattrgbt89192006.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.wireattrgbt89192006.service.WireAttributesGbt89182006Service;
import com.hbsi.dao.WireAttributesGBT89182006Mapper;
import com.hbsi.domain.WireAttributesGBT89182006;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
@Service
public class WireAttributesGbt89182006ServiceImpl implements WireAttributesGbt89182006Service {

	private Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WireAttributesGBT89182006Mapper wireAttrMapper;
	@Override
	public Response<Integer> saveWireAttrList(List<WireAttributesGBT89182006> list) {
		int message=0;
		try {
			for(int i=0;i<list.size();i++) {
				wireAttrMapper.insert(list.get(i));
				message=1;
				logger.info("导入数据似乎成功了");
			}
		} catch (Exception e) {
			logger.error("导入数据失败的原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<Integer> saveSingleWireAttr(WireAttributesGBT89182006 wireAttr) {
		int message=0;
		try {
			wireAttrMapper.insert(wireAttr);
			message=1;
			logger.info("导入数据似乎成功了");
		} catch (Exception e) {
			logger.error("导入数据失败的原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<List<WireAttributesGBT89182006>> selectWireAttr() {
		int message=0;
		List<WireAttributesGBT89182006> list=new ArrayList<>();
		try {
			list = wireAttrMapper.selectWireAttr();
			message=1;
			logger.info("查询数据似乎成功了：{}",list);
		} catch (Exception e) {
			logger.error("查询数据失败的原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SELECT_FAILED);
		}
		return new Response<List<WireAttributesGBT89182006>>(list);
	}

	@Override
	public Response<Integer> updateWireAttr(WireAttributesGBT89182006 wireAttr) {
		int message=0;
		try {
			wireAttrMapper.updateByPrimaryKeySelective(wireAttr);
			message=1;
			logger.info("更新数据似乎成功了");
		} catch (Exception e) {
			logger.error("更新数据失败的原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_UPDATE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<Integer> deleteSingleWireAttr(Integer id) {
		int message=0;
		try {
			wireAttrMapper.deleteByPrimaryKey(id);
			message=1;
			logger.info("删除数据似乎成功了");
		} catch (Exception e) {
			logger.error("删除数据失败的原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}

	@Override
	public Response<Integer> deleteAllData() {
		int message=0;
		try {
			wireAttrMapper.deleteAllData();
			message=1;
			logger.info("删除数据似乎成功了");
		} catch (Exception e) {
			logger.error("删除数据失败的原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}

}
