package com.hbsi.thickwire.service;
/**
 * @description 钢丝绳业务逻辑
 * @author zyz
 *
 */

import java.util.List;

import com.hbsi.domain.WireRope;
import com.hbsi.response.Response;

public interface ThickWireService {
	Response<WireRope> judgeThickWire(WireRope wireRope);

}
