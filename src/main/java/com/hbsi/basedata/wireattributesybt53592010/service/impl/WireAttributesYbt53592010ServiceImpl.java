package com.hbsi.basedata.wireattributesybt53592010.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.wireattributesybt53592010.service.WireAttributesYbt53592010Service;
import com.hbsi.dao.WireAttributesYbt53592010Mapper;
import com.hbsi.domain.WireAttributesYbt53592010;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;

@Service
public class WireAttributesYbt53592010ServiceImpl implements WireAttributesYbt53592010Service {

	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Autowired
	private WireAttributesYbt53592010Mapper wireAttrMapper;
	@Override
	public Response<Integer> saveWireAttrList(List<WireAttributesYbt53592010> list) {
		int message=0;
		System.out.println(list);
		try {
			for(int i=0;i<list.size();i++) {
				wireAttrMapper.insertSelective(list.get(i));
				System.out.println(i);
			}
			logger.info("导入list成功");
			message=1;
		} catch (Exception e) {
			logger.error(" 导入list失败了，原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	@Override
	public Response<Integer> saveWireAttr(WireAttributesYbt53592010 wireAttr) {
		int message=0;
		try {
			wireAttrMapper.insert(wireAttr);
			logger.info("导入单条数据成功");
			message=1;
		} catch (Exception e) {
			logger.error(" 导入单条数据失败了，原因是：{}",e.getMessage());
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	@Override
	public Response<Integer> updateWireAttr(WireAttributesYbt53592010 wireAttr) {
		int message=0;
		try {
			wireAttrMapper.updateByPrimaryKeySelective(wireAttr);
			message=1;
			logger.info("更新单条信息成功");
		} catch (Exception e) {
			logger.error("更新单条数据失败了，原因是：{}",e.getMessage());
			message=-1;
			throw new BaseException(ExceptionEnum.WIREROPE_STANDARD_UPDATE_ERROR);
		}
		return new Response<Integer>(message);
	}
	@Override
	public Response<List<WireAttributesYbt53592010>> selectWireAttrList() {
		int message=0;
		List<WireAttributesYbt53592010> list=new ArrayList<>();
		try {
			list=wireAttrMapper.selectWireAttrList();
			message=1;
			logger.info("查询绳数据列表");
		} catch (Exception e) {
			logger.error("查询数据失败了，原因是：{}",e.getMessage());
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_SELECT_FAILED);
		}
		return new Response<List<WireAttributesYbt53592010>>(list);
	}
	@Override
	public Response<Integer> deleteWireAttr(Integer id) {
		int message=0;
		try {
			wireAttrMapper.deleteByPrimaryKey(id);
			logger.info("删除单条数据成功");
			message=1;
		} catch (Exception e) {
			logger.error(" 删除单条数据失败了，原因是：{}",e.getMessage());
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
	@Override
	public Response<Integer> deleteWireAttr() {
		int message=0;
		try {
			wireAttrMapper.deleteAllData();
			logger.info("删除表数据成功");
			message=1;
		} catch (Exception e) {
			logger.error(" 删除表数据失败了，原因是：{}",e.getMessage());
			message=-1;
			throw new BaseException(ExceptionEnum.WIREATTRIBUTES_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
	
}
