package com.hbsi.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.basedata.diameterdeviation.service.DiameterDeviationService;
import com.hbsi.basedata.fromvalue.service.FromValueService;
import com.hbsi.basedata.wireattributesgbt201182017.service.Wireattributesgbt201182017Service;
import com.hbsi.domain.DiameterDeviation;
import com.hbsi.domain.FromValue;
import com.hbsi.domain.WireAttributesGbt201182017;
import com.hbsi.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class WireAttribuyesTest {

	@Resource
	private Wireattributesgbt201182017Service  wireattributesgbt201182017Service;
//	@Test
	public void selectWireattributes() {
		Response<List<WireAttributesGbt201182017>> response = wireattributesgbt201182017Service.selectWireattributes();
		System.out.println(response);
	}
	
//	@Test
	public void deleteWireattributes() {
		Response<Integer> response = wireattributesgbt201182017Service.deleteWireattributes(1);
		System.out.println(response);
	}
//	@Test
	public void saveSingleWireattribute() {
		WireAttributesGbt201182017 single=new WireAttributesGbt201182017();
		single.setStructure("sdfadfadd");
		
		Response<Integer> response = wireattributesgbt201182017Service.saveSingleWireattribute(single);
		System.out.println(response);
	}
//	@Test
	public void saveWireattributes() {
		WireAttributesGbt201182017 single=new WireAttributesGbt201182017();
		single.setStructure("kjkjussaf");
		single.setIntensityLow(122);
		WireAttributesGbt201182017 single1=new WireAttributesGbt201182017();
		single1.setStructure("qqwqsasdfaf");
		single1.setIntensityLow(133);
		WireAttributesGbt201182017 single2=new WireAttributesGbt201182017();
		single2.setStructure("dsxcvaf");
		single2.setIntensityLow(144);
		List<WireAttributesGbt201182017> list = new ArrayList<WireAttributesGbt201182017>();
        list.add(single);
        list.add(single2);
        list.add(single1);
        Response<Integer> response = wireattributesgbt201182017Service.saveWireattributes(list);
		System.out.println(list);
		System.out.println(response);
	}
	
//	@Test
	public void updateWireattributes() {
		WireAttributesGbt201182017 single=new WireAttributesGbt201182017();
		single.setId(7);
		single.setStructure("kjkjussaf");
		single.setIntensityLow(1);
		Response<Integer> response = wireattributesgbt201182017Service.updateWireattributes(single);
		System.out.println(response);
	}
//	@Test
	public void testDeleteAllData() {
		Response<Integer> deleteAllData = wireattributesgbt201182017Service.deleteAllData();
		
	}
	

}
