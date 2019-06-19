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
import com.hbsi.basedata.reversebending.service.ReverseBendingService;
import com.hbsi.dao.ReverseBendingMapper;
import com.hbsi.domain.DiameterDeviation;
import com.hbsi.domain.FromValue;
import com.hbsi.domain.ReverseBending;
import com.hbsi.entity.WireRopeData;
import com.hbsi.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class ReverseBendingTest {

	@Resource
	private ReverseBendingService reverseBendingService;
	@Resource
	private ReverseBendingMapper reverseBendingMapper;
	@Test
	public void testselectReverseBending() {
//		 Response<List<ReverseBending>> selectDiameterDeviation = reverseBendingService.selectReverseBending();
//		System.out.println(selectDiameterDeviation);
		ReverseBending r = new ReverseBending();
		r.setfDiamete(1.95);
		r.setStandardNum("GB/T 20118-2017");
		r.setSurfaceState("AB");
		r.setRatedStrength("1670");
//		w.setUsage(usage);
		System.out.println(reverseBendingMapper.selectRBDataByCon(r));
	}
	
//	@Test
	public void testdeleteReverseBending() {
		Response<Integer> response = reverseBendingService.deleteReverseBending(1);
		System.out.println(response);
	}
//	@Test
	public void testsaveSingleReverseBending() {
		ReverseBending reverseBending=new ReverseBending();
		reverseBending.setRatedStrength("1233");
		Response<Integer> response = reverseBendingService.saveSingleReverseBending(reverseBending);
		System.out.println(response);
	}
//	@Test
	public void testsaveReverseBending() {
		ReverseBending reverseBending=new ReverseBending();
		reverseBending.setRatedStrength("1ddd");
		ReverseBending reverseBending1=new ReverseBending();
		reverseBending1.setRatedStrength("12sds");
		ReverseBending reverseBending2=new ReverseBending();
		reverseBending2.setRatedStrength("1dassd");
		List<ReverseBending> list = new ArrayList<ReverseBending>();
        list.add(reverseBending);
        list.add(reverseBending1);
        list.add(reverseBending2);
        Response<Integer> response = reverseBendingService.saveReverseBending(list);
		System.out.println(list);
		System.out.println(response);
	}
	
//	@Test
	public void testupdateReverseBending() {
		ReverseBending reverseBending=new ReverseBending();
		reverseBending.setId(1);
		reverseBending.setRatedStrength("1ddfafadfadfd");
		Response<Integer> response = reverseBendingService.updateReverseBending(reverseBending);
		System.out.println(response);
	}
//	@Test
	public void testDeleteAllData() {
		Response<Integer> deleteAllData = reverseBendingService.deleteAllData();
		
	}
	

}
