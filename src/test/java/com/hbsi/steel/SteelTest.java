package com.hbsi.steel;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.hbsi.JlCheckApplication;
import com.hbsi.domain.SampleRecord;
import com.hbsi.domain.SteelWireData;
import com.hbsi.response.Response;
import com.hbsi.steel.entity.SteelWireRecord;
import com.hbsi.steel.service.SteelService;

/**
 * 钢丝报告处理
 * 
 * @author lixuyang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class SteelTest {
	
	@Resource
	private SteelService steelService;
	
	/**
	 * 调入钢丝数据
	 */
//	@Test
	public void testSelectSteelWireDataList() {
		JSONObject json = new JSONObject();
		json.put("entrustmentNumber", "WG20181123128");
		Response<SteelWireRecord> response = steelService.selectSteelWireDataList(json.toString());
		System.out.println(response);
	}
	
	/**
	 * 修改钢丝数据（判定）
	 */
//	@Test
	public void testUpdateSteelWire() {
		List<SteelWireData> list = new ArrayList<>();
		SteelWireData s = new SteelWireData();
		s.setId(17);
		s.setfDiamete(1.15);//成品直径
		s.setStrengthLevel("1870");//强度级别
		s.setUse("Z");//用途
		s.setSurface("U");//表面状态
		s.setBreakTensile(2.150);//破断拉力
		s.setTorsionTimes(24);//扭转次数
		s.setBendingTimes(29);//弯曲次数
		list.add(s);
		Response<List<SteelWireData>> response = steelService.updateSteelWire(list);
		System.out.println(response);
	}

}
