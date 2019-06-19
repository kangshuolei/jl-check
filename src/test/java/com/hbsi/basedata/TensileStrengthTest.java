package com.hbsi.basedata;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.basedata.tensilestrength.service.TensileStrengthService;

import com.hbsi.domain.TensileStrength;
import com.hbsi.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class TensileStrengthTest {

	@Resource
	private TensileStrengthService tensileStrengthService;
	
//	@Test
	public void testSelectAllData() {
		Response<List<TensileStrength>> selectTensileStrength = tensileStrengthService.selectTensileStrength();
		System.out.println(selectTensileStrength);
	}
	
//	@Test
	public void testDeleteTensileStrength() {
		Response<Integer> response = tensileStrengthService.deleteTensileStrength(11);
		System.out.println(response);
	}
//	@Test
	public void testSaveTensileStrengthById() {
		TensileStrength tensileStrength=new TensileStrength();
		tensileStrength.setRatedStrength(1880);
		tensileStrength.setStandardNum("332244");
		Response<Integer> response = tensileStrengthService.saveSingleTensileStrength(tensileStrength);
		System.out.println(response);
	}
//	@Test
	public void testSaveTensileStrength() {
		TensileStrength tensileStrength=new TensileStrength();
		tensileStrength.setRatedStrength(1990);
		tensileStrength.setStandardNum("44444");
		TensileStrength tensileStrength1=new TensileStrength();
		tensileStrength1.setRatedStrength(2000);
		tensileStrength1.setStandardNum("55555");
		TensileStrength tensileStrength2=new TensileStrength();
		tensileStrength2.setRatedStrength(2110);
		tensileStrength2.setStandardNum("667765");
		List<TensileStrength> list = new ArrayList<TensileStrength>();
        list.add(tensileStrength);
        list.add(tensileStrength2);
        list.add(tensileStrength1);
        Response<Integer> response = tensileStrengthService.saveTensileStrength(list);
		System.out.println(list);
		System.out.println(response);
	}
	
//	@Test
	public void testupdateTensileStrength() {
		TensileStrength tensileStrength=new TensileStrength();
		tensileStrength.setId(1);
		tensileStrength.setRatedStrength(1990);
		tensileStrength.setStandardNum("44444");
		Response<Integer> response = tensileStrengthService.updateTensileStrength(tensileStrength);
		System.out.println(response);
	}
	
}
