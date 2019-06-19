package com.hbsi.entrustment.service.impl;

import java.text.SimpleDateFormat;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.hbsi.entrustment.service.EntrustmentNumService;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;

@Service
public class EntrustmentNumServiceImpl implements EntrustmentNumService {

	Logger logger = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * 创建默认的委托单号
	 * 
	 * 根据时间+批次号 = 生成委托单号 例如：WG181130B125
	 */
	public Response<String> createEntrustmentDefaultNum(Integer batchNum) {
		if(batchNum != null && !batchNum.equals("")) {
			String entrustmentDefaultNum = null;
			try {
				Date date = new Date();// 获取当前日期
				SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");// 格式化 形式
				String yMD = sdf.format(date);// 格式化当前日期
				SimpleDateFormat hours = new SimpleDateFormat("HHmm");
				String hM = hours.format(date);// 格式化当前日期
				int i = Integer.parseInt(hM);
				// 5点45之前 yyMMdd+B 之后 yyMMdd+Y
				String num = i > 1745 ? yMD + "Y" : yMD + "B";
				entrustmentDefaultNum = "WG" + num + batchNum;
				logger.info("生成委托单号成功!");
				return new Response<String>(entrustmentDefaultNum);
			} catch (NumberFormatException e) {
				logger.error("生成委托单号失败!,原因{}",e);
				throw new BaseException(ExceptionEnum.ENTRUSTMENT_NUM_CREATE_FAILED);
			}			
		}else {
			throw new BaseException(ExceptionEnum.ENTRUSTMENT_NUM_CREATE_FAILED);
		}
		
		
	}

}
