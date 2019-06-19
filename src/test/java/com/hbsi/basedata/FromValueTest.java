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
import com.hbsi.basedata.fromvalue.service.FromValueService;
import com.hbsi.domain.FromValue;
import com.hbsi.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class FromValueTest {

	@Resource
	private FromValueService fromValueService;
//	@Test
	public void testSelectAllData() {
		Response<List<FromValue>> list = fromValueService.selectFromValue();
		System.out.println(list.toString());
	}
	
//	@Test
	public void testDeleteFromValue() {
		Response<Integer> response = fromValueService.deleteFromValue(1);
		System.out.println(response);
	}
//	@Test
	public void testSaveFromValueById() {
		FromValue fromValue=new FromValue();
		fromValue.setRatedStrength("djdjd");
		fromValue.setStandardNum("sdfaf");
		Response<Integer> response = fromValueService.saveSingleFromValue(fromValue);
		System.out.println(response);
	}
//	@Test
	public void testSaveFromValue() {
		FromValue fromValue=new FromValue();
		fromValue.setRatedStrength("1111");
		fromValue.setStandardNum("1111");
		FromValue fromValue1=new FromValue();
		fromValue1.setRatedStrength("2222");
		fromValue1.setStandardNum("2222");
		FromValue fromValue2=new FromValue();
		fromValue2.setRatedStrength("3333");
		fromValue2.setStandardNum("3333");
		List<FromValue> list = new ArrayList<FromValue>();
        list.add(fromValue);
        list.add(fromValue1);
        list.add(fromValue2);
        Response<Integer> response = fromValueService.saveFromValue(list);
		System.out.println(list);
		System.out.println(response);
	}
	
//	@Test
	public void testupdateFromValue() {
		FromValue fromValue=new FromValue();
		fromValue.setId(3);
		fromValue.setRatedStrength("1111");
		fromValue.setStandardNum("1111");
		Response<Integer> response = fromValueService.updateFromValue(fromValue);
		System.out.println(response);
	}
	@Test
	public void testDeleteAllData() {
		Integer i=1;
		String m="aaa"+i+i+i;
		System.out.println(m);
//		Response<Integer> deleteAllData = fromValueService.deleteAllData();
		
	}
	

}
