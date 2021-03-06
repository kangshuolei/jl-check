package com.hbsi.entrustment;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.hbsi.JlCheckApplication;
import com.hbsi.domain.EntrustmentData;
import com.hbsi.domain.VersonNumber;
import com.hbsi.entrustment.service.EntrustmentDataService;
import com.hbsi.response.Response;

/**
 * 委托单数据
 * 
 * @author lixuyang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class EntrustmentDataTest {
	
	@Resource
	private EntrustmentDataService entrustmentDataService;
	
	/**
	 * 查询或新建委托单
	 */
//	@Test
	public void testSelectOrSaveEntrustmentData() {
		JSONObject json = new JSONObject();
		json.put("entrustmentNumber", "WG20181029125");
		Response<EntrustmentData> response = entrustmentDataService.selectOrSaveEntData(json.toJSONString());
		System.out.println(response);
	}
	
	/**
	 * 添加委托单数据和钢丝数据（提交按钮）
	 */
//	@Test
	public void testSaveEntrustmentData() {
		JSONObject json = new JSONObject();
		json.put("id", 83);
		json.put("productionNumber", "夜2头");//生产序号
		json.put("pDiamete", 3.00);//原直径
		json.put("fDiamete", 1.15);//成品直径
		json.put("strengthLevel", "1870");////强度级别
		json.put("use", "Z");//用途
		json.put("surface", "U");//表面状态
		json.put("steelLength", 7500);//长度
		json.put("board", 1);//机台
		json.put("wheelNumber", "X270");//轮号
		json.put("gradeSteel", "72A");//钢号
		json.put("producer", "张璀");//生产者
		json.put("memo", "检5小绳");//备注
		JSONObject json2 = new JSONObject();
		json2.put("id", 85);
		json2.put("productionNumber", "夜2头");//生产序号
		json2.put("pDiamete", 3.00);//原直径
		json2.put("fDiamete", 1.15);//成品直径
		json2.put("strengthLevel", "1870");////强度级别
		json2.put("use", "Z");//用途
		json2.put("surface", "U");//表面状态
		json2.put("steelLength", 7500);//长度
		json2.put("board", 1);//机台
		json2.put("wheelNumber", "X270");//轮号
		json2.put("gradeSteel", "72A");//钢号
		json2.put("producer", "张璀");//生产者
		json2.put("memo", "检5小绳");//备注
		JSONArray array = new JSONArray();
		array.add(json);
		array.add(json2);
		JSONObject object= new JSONObject();
		object.put("entrustmentNumber", "LMJ980109");
		object.put("reviewer", "张三");
		object.put("steelNumber", 15);
		object.put("testClerkNumber", "02");
		object.put("testMachine", "WJ");
		object.put("steelWireDataList", array);
		String info = object.toJSONString();
		System.out.println(info);
		Response<Integer> response = entrustmentDataService.saveEntDataAndSteelData(info);
		System.out.println(response);
	}
	
	/**
	 * 查询版本号
	 */
//	@Test
	public void testSelectVersionNum() {
		Response<VersonNumber> response = entrustmentDataService.selectVersionNum();
		System.out.println(response);
	}
	
	/**
	 * 修改版本号
	 */
//	@Test
	public void testUpdateVersionNum() {
		JSONObject json = new JSONObject();
		json.put("versionNum", "JL/JL (Q) 030-6");//JL/JL (Q) 030-6
		Response<Integer> response = entrustmentDataService.updateVersionNum(json.toJSONString());
		System.out.println(response);
	}

}
