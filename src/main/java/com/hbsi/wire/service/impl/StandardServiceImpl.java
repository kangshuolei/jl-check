package com.hbsi.wire.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.dao.WireStandardMapper;
import com.hbsi.domain.WireStandard;
import com.hbsi.response.Response;
import com.hbsi.wire.service.StandardService;
@Service
public class StandardServiceImpl implements StandardService{
	private Logger logger=LoggerFactory.getLogger(StandardService.class);
	@Autowired
	private WireStandardMapper wireStandardMapper;

	@Override
	public Response<List<WireStandard>> selectStandardList() {
		List<WireStandard> list=wireStandardMapper.selectWireStandardList();
		logger.info("查询所有的标准文件");
		return new Response<List<WireStandard>>(list);
	}

}
