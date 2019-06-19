package com.hbsi.basedata.samplerecord.service;

import java.util.List;

import com.hbsi.domain.SampleRecord;
import com.hbsi.response.Response;

public interface ImportSampleRecordService {

	/**
	 * 导入数据操作
	 * @return
	 */
	public Response<Integer> saveSampleRecordList(List<SampleRecord> list);
}
