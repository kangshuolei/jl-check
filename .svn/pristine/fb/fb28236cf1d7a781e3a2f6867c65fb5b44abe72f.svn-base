package com.hbsi.basedata.wireatrributesapi9a.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.wireatrributesapi9a.service.WireAtrributesApi9a2011Service;
import com.hbsi.dao.WireAttributesApi9a2011Mapper;
import com.hbsi.domain.WireAttributesApi9a2011;
@Service
public class WireAtrributesApi9aServiceImpl implements WireAtrributesApi9a2011Service {

	/**
	 * author:shahzou
	 */
	@Autowired
	private WireAttributesApi9a2011Mapper wireAttributesApi9a2011Mapper;
	private Logger logger =LoggerFactory.getLogger(getClass());
	/**
	 * 保存多条数据
	 */
	@Override
	public Integer saveWireAtriibutesApi(List<WireAttributesApi9a2011> list) {
		try {
			for(WireAttributesApi9a2011 wireAttr : list) {
				wireAttributesApi9a2011Mapper.insert(wireAttr);
			}
		} catch (Exception e) {
			logger.error("保存数据失败了，原因是:{}",e);
			return -1;
		}
		return 1;
	}

}
