package com.hbsi.wire.service;

import com.hbsi.domain.WireRope;
import com.hbsi.response.Response;

public interface WireRopeServiceMT716 {
	/**
	 * MT716-2005
	 * @param wireRope
	 * @return
	 */
	Response<WireRope> judgeWire(WireRope wireRope);

}
