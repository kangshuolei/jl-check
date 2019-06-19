package com.hbsi.basedata.fromvalue.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.fromvalue.service.FromValueService;
import com.hbsi.dao.FromValueMapper;
import com.hbsi.domain.FromValue;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;

@Service
public class FromValueServiceImpl implements FromValueService {
	
	/**
	 * author:shahou
	 */

	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private FromValueMapper fromValueMapper;
	
	/**
	 * 已list的方式添加表单数据
	 */
	@Override
	public Response<Integer> saveFromValue(List<FromValue> fromValueList) {
		int message=0;
		try {
			for(int i=0;i<fromValueList.size();i++) {
				fromValueMapper.insert(fromValueList.get(i));
			}
			message=1;
			logger.info("成功导入该表到数据库");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("导入到数据库失败，原因是：{}",e);
			throw new BaseException(ExceptionEnum.FROMVALUE_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 添加单条表单数据
	 */
	@Override
	public Response<Integer> saveSingleFromValue(FromValue fromValue) {
		int message=0;
	    try {
			fromValueMapper.insertSelective(fromValue);
			logger.info("插入单条数据成功");
			message=1;
		} catch (Exception e) {
			logger.error("插入过程出错，我不会告诉你出错原因是：{}",e);
			message=-1;
			throw new BaseException(ExceptionEnum.FROMVALUE_SAVE_FAILED);
		}
		return new Response<Integer>(message);
	}

	/**
	 * 根据id删除表单数据
	 */
	@Override
	public Response<Integer> deleteFromValue(Integer id) {
		int message=0;
		try {
			fromValueMapper.deleteByPrimaryKey(id);
			logger.info("根据id：{}删除表单数据成功",id);
			message=1;
		} catch (Exception e) {
			message=-1;
			logger.error("删除id为{}的表单数据失败, 错误信息为：{}",id,e);
			throw new BaseException(ExceptionEnum.FROMVALUE_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}

	/**
	 * 更改表单数据
	 */
	@Override
	public Response<Integer> updateFromValue(FromValue fromValue) {
		int message=0;
		
		try {
			fromValueMapper.updateByPrimaryKeySelective(fromValue);
			logger.info("修改表单数据成功");
			message=1;
		} catch (Exception e) {
			logger.error("修改表单数据失败");
			message=-1;
			throw new BaseException(ExceptionEnum.FROMVALUE_UPDATE_FAILED);
		}
		return new Response<Integer>(message);
	}

	/**
	 * 查找得到整个表单
	 */
	@Override
	public Response<List<FromValue>> selectFromValue() {
		List<FromValue> list=null;
		int message=0;
		try {
			list = fromValueMapper.selectAllData();
			logger.info("查询得到所有的表单数据{}",list);
			message=1;
		} catch (Exception e) {
			logger.error("啊呀，查询失败了，奴家也很无奈");
			message=-1;
			throw new BaseException(ExceptionEnum.FROMVALUE_SELECT_FAILED);
		}
		return new Response<List<FromValue>>(list);
	}
    /**
     * 删除表单数据整张表
     */
	@Override
	public Response<Integer> deleteAllData() {
		int message=0;
		try {
			fromValueMapper.deleteAllData();
			message=1;
			logger.info("删除整张表数据成功");
		} catch (Exception e) {
			message=-1;
			logger.error(" 删除整张表单出错了，原因是：{}",e);
			throw new BaseException(ExceptionEnum.FROMVALUE_DELETE_FAILED);
		}
		return new Response<Integer>(message);
	}
	/**
	 * 根据标准号查询列表
	 */
	@Override
	public List<FromValue> selectByStandardNum(String standardNum) {
		List<FromValue> list = new ArrayList<>();
		try {
			list = fromValueMapper.selectByStandardNum(standardNum);
		}catch(Exception e) {
			logger.error("查询列表失败了，原因是:{}",e);
			throw new BaseException(ExceptionEnum.FROMVALUE_SELECT_FAILED);
		}
		return list;
	}

}
