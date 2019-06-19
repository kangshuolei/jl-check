package com.hbsi.wire.service;
/**
 * 标准的业务逻辑
 * @author cxh
 *
 */

import java.util.List;

import com.hbsi.domain.WireStandard;
import com.hbsi.response.Response;

public interface StandardService {
	/**
	 * 查询标准列表
	 * @return
	 */
	Response<List<WireStandard>> selectStandardList();

}
