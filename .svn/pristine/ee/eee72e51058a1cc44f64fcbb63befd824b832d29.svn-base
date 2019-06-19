package com.hbsi.basedata.samplerecord.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.basedata.samplerecord.service.ImportSampleRecordService;
import com.hbsi.dao.SampleRecordMapper;
import com.hbsi.domain.SampleRecord;
import com.hbsi.response.Response;
@Service
public class ImportSampleRecordServiceImpl implements ImportSampleRecordService {

	@Autowired
	private SampleRecordMapper sampleRecordMapper;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	@Override
	public Response<Integer> saveSampleRecordList(List<SampleRecord> list) {
		Integer message=0;
		try {
			for(int i=0;i<list.size();i++) {
				sampleRecordMapper.insertSelective(list.get(i));
			}
			logger.info("the import is done perfectly");
			message=1;
		} catch (Exception e) {
			message=-1;
			logger.error("Are you kidding,something wrong happends");
			e.printStackTrace();
		}
		return new Response<Integer>(message);
	}
}
