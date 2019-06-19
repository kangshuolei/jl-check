package com.hbsi.wire.controller;

import java.util.List;

import javax.annotation.Resource;

import org.nutz.mvc.annotation.GET;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
/**
 * 钢丝绳标准接口
 * @author cxh
 *
 */

import com.hbsi.domain.WireStandard;
import com.hbsi.response.Response;
import com.hbsi.wire.service.StandardService;
@RestController
@RequestMapping("standard")
public class WireStandardController {
	@Resource
	private StandardService standardService;
	/**
	 * 查询所有的标准列表
	 * @return
	 */
	@GetMapping("selectStandardList")
	public Response<List<WireStandard>> selectStandardList(){
		return standardService.selectStandardList();
	}
}
