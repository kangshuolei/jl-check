package com.hbsi.entrustment.service.impl;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hbsi.dao.EntrustmentDataMapper;
import com.hbsi.dao.EntrustmentMapper;
import com.hbsi.dao.SampleRecordMapper;
import com.hbsi.dao.SteelWireDataMapper;
import com.hbsi.domain.Entrustment;
import com.hbsi.entrustment.service.SaveEntrustmenService;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.PageResult;
import com.hbsi.response.Response;

@Service
public class SaveEntrustmentServiceImpl implements SaveEntrustmenService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	@Autowired
	private EntrustmentMapper entrustmentMapper;
	@Autowired
	private SampleRecordMapper sampleRecordMapper;
	@Autowired
	private EntrustmentDataMapper entrustmentDataMapper;
	@Autowired
	private SteelWireDataMapper steelWireDataMapper;

	/**
	 * 生成新的批次号返回给前端
	 */
	@Override
	public Response<Integer> saveNewBatch() {
		Integer sampleBatch;
		Integer count = entrustmentMapper.selectCount();
		System.out.println(count);
		// 小于999说明不用往历史委托单号表中添加数据
		if (count == 0) {
			sampleBatch = 1;
			return new Response<Integer>(sampleBatch);
		} else {
			Entrustment entrust = entrustmentMapper.selectLatestEntrust();
			/**
			 * 生成新的批次号：旧批次号如果小于999，加一 就的批次号等于999.新批次号为1
			 */
			if (entrust.getSampleBatch() != 999) {
				sampleBatch = entrust.getSampleBatch() + 1;
			} else {
				sampleBatch = 1;
			}
			List<Entrustment> entrustList = entrustmentMapper.selectRealByBatch(sampleBatch);
			// 为空说明该委托单号没有被使用过
			if (entrustList != null) {
				System.out.println(entrustList + "----------------mark--------------");
				try {
					// 申请新的批次号，真正删除这个批次号在sampleRecord中对应的数据
					sampleRecordMapper.deleteEntrustmentReally(sampleBatch);
					// 将entrustmentData表中，真横的删除这条数据
					entrustmentDataMapper.deleteByEntrustNumReally(entrustList.get(0).getEntrustmentNumber());
					// 删除该条数据，并返回这条数据的批次号
					entrustmentMapper.deleteEntrustmentReally(sampleBatch);
					// 把上一轮该批次号对应的数据全部删除
					steelWireDataMapper.deleteByEntrustment(entrustList.get(0).getEntrustmentNumber());
				} catch (Exception e) {
					logger.error("删除sampleRecord表中内容，或者插入数据到sampleRecordHistory中时出错了,原因是:{}", e);
				}
			}
		}
		return new Response<Integer>(sampleBatch);
	}

	/**
	 * 保存修改完成后的委托单号
	 */
	@Override
	public Response<?> saveEntrustment(Entrustment entrustment) {
		/**
		 * 只有两个字段，现在添加上state和创建时间，插入到数据库中去
		 */
		Entrustment entrus = entrustmentMapper.selectEntrustByBatch(entrustment.getSampleBatch());
		if (entrus != null) {
			return new Response<>("00001600", "不能重复添加该批次号", null);
		} else {
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Timestamp newTime = Timestamp.valueOf(sdf.format(new Date()));
			entrustment.setCreateTime(newTime);
			entrustment.setState("000");
			System.out.println(entrustment);
			try {
				entrustmentMapper.insert(entrustment);
			} catch (Exception e) {
				logger.error("插入新的批次号和委托单号，到委托单号表出错了，原因是:{}", e);
				throw new BaseException(ExceptionEnum.ENTRUSTMENT_SAVE_FAILED);
			}
			return new Response<>("200", "ok", null);
		}
	}

	/**
	 * 得到委托单号表中的所有的999条数据，还得倒序
	 */
	@Override
	public Response<Object> selectEntrustmentList(Integer offset, Integer limit) {
		List<Entrustment> listByDesc = new ArrayList<>();
		// 分页信息
		try {
			PageHelper.startPage(offset, limit);// 对下一行语句进行分页
			listByDesc = entrustmentMapper.selectAllByDesc();
		} catch (Exception e) {
			logger.error("查询999条委托单号信息失败了，原因是:{}", e);
			throw new BaseException(ExceptionEnum.ENTRUSTMENT_SELECT_FAILED);
		}
		PageInfo<Entrustment> pageInfo = new PageInfo<Entrustment>(listByDesc);
		PageResult result = new PageResult(pageInfo.getPageSize(), pageInfo.getPageNum(), pageInfo.getTotal(),
				pageInfo.getList());
		return new Response<>(result);
	}

	/***
	 * 根据要求的数量一次性生成多个批次号
	 */
	@Transactional(rollbackFor = Exception.class)
	@Override
	public Response<Integer> saveManyNewBatch(String info) {
		JSONObject json = JSONObject.parseObject(info);
		Integer batchNum = json.getInteger("batchNum");
		if (batchNum == null || batchNum == 0) {
			return new Response<>("000017130", "批次生成时输入的个数为空", null);
		} else {
			for (int i = 0; i < batchNum; i++) {
				// 生成新的批次号，然后保存到数据库
				Response<Integer> newBatch = saveNewBatch();
				Entrustment entrustment = new Entrustment();
				entrustment.setSampleBatch(Integer.parseInt(newBatch.getData().toString()));
				saveEntrustment(entrustment);
			}
		}
		return new Response<>("200", "ok", null);
	}

}
