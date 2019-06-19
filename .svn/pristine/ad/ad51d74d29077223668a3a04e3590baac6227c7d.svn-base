package com.hbsi.basedata;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.assertj.core.util.Arrays;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hbsi.JlCheckApplication;
import com.hbsi.basedata.steeltensilestrength.service.SteelTSservice;
import com.hbsi.domain.SteelTensileStrength;
import com.hbsi.response.PageResult;
import com.hbsi.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JlCheckApplication.class)
@WebAppConfiguration
public class SteelTSTest {

	@Resource
	private SteelTSservice steelTSservice;

//	@Test
	public void testselectTS() {
		Response<List<SteelTensileStrength>> list = steelTSservice.selectSteelTS();
		System.out.println(list);
	}

//	@Test
	public void testSaveSteelTS() {
		SteelTensileStrength sTS = new SteelTensileStrength();
		sTS.setState("000");
		SteelTensileStrength sTS1 = new SteelTensileStrength();
		sTS1.setState("999");
		List<SteelTensileStrength> list = new ArrayList<>();
		list.add(sTS1);
		list.add(sTS);
		steelTSservice.saveSteelTS(list);
	}

//	@Test
	public void testSaveSingleSteelTS() {
		SteelTensileStrength sTS = new SteelTensileStrength();
		sTS.setState("000");
		steelTSservice.saveSingleSteelTS(sTS);
	}

//	@Test
	public void testUpdateSteelTS() {
		SteelTensileStrength sTS = new SteelTensileStrength();
		sTS.setState("000");
		sTS.setId(1);
		steelTSservice.updateSteelTS(sTS);
	}

//	@Test
	public void testDeleteSteelTS() {
		steelTSservice.deleteSteelTS(1);
	}

//	@Test
	public void testDeleteAllData() {
		steelTSservice.deleteAllData();
	}

	/**
	 * 自己写个分页方法试试
	 */
//	@Test
	public void testPageData() {
//		Integer offset =1;
//		Integer limit =3;
//		PageHelper.startPage( offset,limit);
//		
//		PageInfo<String> pageInfo =new PageInfo<String>(list);
//		
//		PageResult result = new PageResult(pageInfo.getPageSize(), pageInfo.getPageNum(), pageInfo.getTotal(), pageInfo.getList());
//		System.out.println(result.toString());
	}
}
