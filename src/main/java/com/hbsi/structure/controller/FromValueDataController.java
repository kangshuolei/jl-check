package com.hbsi.structure.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.response.Response;
import com.hbsi.structure.service.FromValueDataService;

@RestController
@RequestMapping("/fromValueData")
public class FromValueDataController {

	@Autowired
	private FromValueDataService fromValueDataService;
	/**
	 * 得到公称强度列表，捻法列表，表面状态列表
	 * @param standardNum
	 * @return
	 */
	@PostMapping("/getDataList")
	public Response<Map<String,List<String>>> getFromValueData(@RequestBody String info){
		return fromValueDataService.getFromValueList(info);
	}
}
