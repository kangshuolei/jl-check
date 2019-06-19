package com.hbsi.wire.service;

import com.hbsi.domain.WireRope;
import com.hbsi.response.Response;

/**
 * ISO标准
 * 
 * @author lixuyang
 *
 */
public interface Iso24082004Service {
	
	/**
	 * 钢丝绳综合判定
	 * @param wireRope
	 * @return
	 */
	Response<WireRope> judgeWireRopeIso24082004(WireRope wireRope);

}
