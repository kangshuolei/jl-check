package com.hbsi.wire.service;

import com.hbsi.domain.WireRope;
import com.hbsi.response.Response;

/**
 * 欧盟标准
 * 
 * @author lixuyang
 *
 */
public interface En123852002Service {
	
	/**
	 * 钢丝绳综合判定
	 * @param wireRope
	 * @return
	 */
	Response<WireRope> judgeWireRopeEn123852002(WireRope wireRope);

}
