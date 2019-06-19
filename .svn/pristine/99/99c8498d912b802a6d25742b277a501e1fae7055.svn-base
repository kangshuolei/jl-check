package com.hbsi.wire.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.domain.WireRope;
import com.hbsi.response.Response;
import com.hbsi.wire.service.WireRopeService;
import com.hbsi.wire.service.WireRopeServiceYBT52952010;
@Service
public class WireRopeServiceYBT52952010Impl implements WireRopeServiceYBT52952010 {

	/**
	 * Author:shazhou 
	 */
	@Autowired
	private WireRopeService wireRopeService;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	@Override
	public Response<WireRope> judgeWire(WireRope wireRope) {
		try {
			//此处有坑，在调入数据时，判定标准意外消失，此处为补救措施，重新添加上判定标准。防止后面空指针异常
			wireRope.setJudgeStandard("YB/T 5295-2010");
			wireRopeService.saveWire(wireRope);
		} catch (Exception e) {
			logger.error("保存出错了，错误原因:{}",e);
			return new Response<>("00001900","保存钢丝绳信息出错了",null);
		}
		return new Response<WireRope>(wireRope);
	}

}
