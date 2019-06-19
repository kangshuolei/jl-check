package com.hbsi.sample;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import com.hbsi.JlCheckApplication;
import com.hbsi.domain.SampleRecord;
import com.hbsi.response.PageResult;
import com.hbsi.response.Response;
import com.hbsi.sample.service.SampleRecordService;
import com.hbsi.sample.entity.SampleRecords;

/**
 * 试验数据管理
 * 
 * @author lixuyang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class SampleRecordTest {
	
	@Resource
	private SampleRecordService sampleRecordService;
	
	/**
	 * 查询试验记录
	 */
//	@Test
	public void testSelectSampleRecordList() {
		JSONObject json = new JSONObject();
		json.put("entrustmentNumber", "");
		json.put("type", "");
		json.put("machineNumber", "");
		json.put("offset", 1);
		json.put("limit", 20);
		Response<PageResult> response = sampleRecordService.selectSampleRecordList(json.toJSONString());
		System.out.println(response);
	}
	
	/**
	 * 查询错误处理列表
	 */
//	@Test
	public void testSelectSampleErrorList() {
		JSONObject json = new JSONObject();
		json.put("sampleBatch", "");
		json.put("type", "");
		json.put("machineNumber", "");
		json.put("offset", 1);
		json.put("limit", 20);
		Response<PageResult> response = sampleRecordService.selectSampleErrorList(json.toJSONString());
		System.out.println(response);
	}
	
	/**
	 * 删除错误数据
	 */
//	@Test
	public void testDeleteSampleError() {
		JSONObject json = new JSONObject();
		json.put("id", 1);
		Response<Integer> response = sampleRecordService.deleteSampleError(json.toJSONString());
		System.out.println(response);
	}
	
	/**
	 * 修改批次号
	 */
	@Test
	public void testUpdateSampleBatch() {
		JSONObject json = new JSONObject();
		JSONObject json1 = new JSONObject();
		JSONObject json2 = new JSONObject();
		JSONObject json3 = new JSONObject();
		JSONObject json4 = new JSONObject();
		json1.put("id", 31);
		json2.put("id", 0);
		json3.put("id", 33);
		json4.put("id", 35);
		JSONArray array = new JSONArray();
		array.add(json1);
		array.add(json2);
		array.add(json3);
		array.add(json4);
		json.put("ids", array);
		json.put("sampleBatch", 127);
		Response<SampleRecords> response = sampleRecordService.updateSampleBatch(json.toJSONString());
		System.out.println(response);
		
	}

}
