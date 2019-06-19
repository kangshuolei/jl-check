package com.hbsi.sample.entity;


import java.io.Serializable;
import java.util.Date;
import com.hbsi.domain.SampleRecord;
import com.hbsi.util.DateUtil;
import java.util.List;


public class SampleRecords implements Serializable{
	private List<Integer> sampleRecordIdList;//样本列表
	private Integer sampleBatch;//批次号
	
	
	public Integer getSampleBatch() {
		return sampleBatch;
	}
	
	public void setSampleBatch(Integer sampleBatch) {
		this.sampleBatch = sampleBatch;
	}
	
	public List<Integer> getSampleRecordIdList() {
		return sampleRecordIdList;
	}
	
	public void setSampleRecordIdList(List<Integer> sampleRecordIdList) {
		this.sampleRecordIdList = sampleRecordIdList;
	}
	
}
