package com.hbsi.basedata;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hbsi.JlCheckApplication;
import com.hbsi.basedata.diameterdeviation.service.DiameterDeviationService;
import com.hbsi.basedata.fromvalue.service.FromValueService;
import com.hbsi.dao.DiameterDeviationMapper;
import com.hbsi.dao.EntrustmentMapper;
import com.hbsi.domain.DiameterDeviation;
import com.hbsi.domain.Entrustment;
import com.hbsi.domain.FromValue;
import com.hbsi.entity.WireRopeData;
import com.hbsi.response.PageResult;
import com.hbsi.response.Response;
import com.hbsi.util.Arith;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class DiameterDeviationTest {

	@Autowired
	private EntrustmentMapper entrustmentMapper;
	@Autowired
	private DiameterDeviationService diameterDeviationService; 
	@Autowired
	private DiameterDeviationMapper DDMapper;
//	@Test
	public void testselectDiameterDeviation() {
		 Response<List<DiameterDeviation>> selectDiameterDeviation = diameterDeviationService.selectDiameterDeviation();
		System.out.println(selectDiameterDeviation);
	}
	
//	@Test
	public void testdeleteDiameterDeviation() {
		Response<Integer> response = diameterDeviationService.deleteDiameterDeviation(1);
		System.out.println(response);
	}
//	@Test
	public void testsaveSingleDiameterDeviation() {
		DiameterDeviation diameterDeviation=new DiameterDeviation();
		diameterDeviation.setDiameteMax(0.6);
		diameterDeviation.setDiameteMin(0.2);
		diameterDeviation.setStandardNum("wwww");
		diameterDeviation.setType("A");
		diameterDeviation.setValue(0.1);
		Response<Integer> response = diameterDeviationService.saveSingleDiameterDeviation(diameterDeviation);
		System.out.println(response);
	}
//	@Test
	public void testsaveDiameterDeviation() {
		DiameterDeviation diameterDeviation=new DiameterDeviation();
		diameterDeviation.setDiameteMax(0.6);
		diameterDeviation.setDiameteMin(0.2);
		diameterDeviation.setStandardNum("wwww");
		diameterDeviation.setType("A");
		diameterDeviation.setValue(0.1);
		DiameterDeviation diameterDeviation1=new DiameterDeviation();
		diameterDeviation1.setDiameteMax(0.3);
		diameterDeviation1.setDiameteMin(0.2);
		diameterDeviation1.setStandardNum("wwww");
		diameterDeviation1.setType("B");
		diameterDeviation1.setValue(0.1);
		DiameterDeviation diameterDeviation2=new DiameterDeviation();
		diameterDeviation2.setDiameteMax(0.7);
		diameterDeviation2.setDiameteMin(0.4);
		diameterDeviation2.setStandardNum("wwww");
		diameterDeviation2.setType("A");
		diameterDeviation2.setValue(0.6);
		List<DiameterDeviation> list = new ArrayList<DiameterDeviation>();
        list.add(diameterDeviation);
        list.add(diameterDeviation1);
        list.add(diameterDeviation2);
        Response<Integer> response = diameterDeviationService.saveDiameterDeviation(list);
		System.out.println(list);
		System.out.println(response);
	}
	
//	@Test
	public void testupdateDiameterDeviation() {
		DiameterDeviation diameterDeviation=new DiameterDeviation();
		diameterDeviation.setId(1);
		diameterDeviation.setDiameteMax(0.6);
		diameterDeviation.setDiameteMin(0.2);
		diameterDeviation.setStandardNum("wwww");
		diameterDeviation.setType("A");
		diameterDeviation.setValue(0.1);
		Response<Integer> response = diameterDeviationService.updateDiameterDeviation(diameterDeviation);
		System.out.println(response);
	}
//	@Test
	public void testDeleteAllData() {
		Response<Integer> deleteAllData = diameterDeviationService.deleteAllData();
		
	}
//	@Test
	public void testSelectMethod() {
		WireRopeData w=new WireRopeData();
		w.setNdiamete(3.7);
		w.setStandardNum("GBT 20067-2017");
		w.setSurface("A");
		DiameterDeviation deviation = DDMapper.selectDiaByBlurType(w);
		System.out.println(deviation);
	}
	@Test
	public void testMathRound() {
		System.out.println(Math.round(1116.5));
		System.out.println(Math.round(1117.5));
		System.out.println(Arith.revision(1116.5));
	}

	/**
	 * 自己写个分页方法试试
	 */
//	@Test
	public void testPageData() {
		Integer offset =1;
		Integer limit =3;
		PageHelper.startPage( offset,limit);
		List<Entrustment> allByDesc = entrustmentMapper.selectAllByDesc();
		PageInfo<Entrustment> pageInfo =new PageInfo<Entrustment>(allByDesc);
		
		PageResult result = new PageResult(pageInfo.getPageSize(), pageInfo.getPageNum(), pageInfo.getTotal(), pageInfo.getList());
		System.out.println(result.toString());
	}
}
