package com.hbsi.structure;

import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.dao.WireAttributesGBT89182006Mapper;
import com.hbsi.dao.WireAttributesGbt200672017Mapper;
import com.hbsi.dao.WireAttributesGbt201182017Mapper;
import com.hbsi.dao.WireAttributesYbt53592010Mapper;
import com.hbsi.domain.WireAttributesGBT89182006;
import com.hbsi.domain.WireAttributesGbt200672017;
import com.hbsi.domain.WireAttributesGbt201182017;
import com.hbsi.domain.WireAttributesYbt53592010;
import com.hbsi.response.Response;
import com.hbsi.structure.service.FromValueDataService;
import com.hbsi.structure.service.StructureServie;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class testStructure {

	@Autowired
	private FromValueDataService fromValueDataService;
	@Autowired
	private StructureServie structureService;
	
	@Autowired
	private WireAttributesGbt200672017Mapper wireAttributesGbt200672017Mapper;	
	@Autowired
	private WireAttributesGbt201182017Mapper wireAttributesGbt201182017Mapper;	
	@Autowired
	private WireAttributesGBT89182006Mapper wireAttributesGBT89182006Mapper;	
//	@Autowired
//	private WireAttributesMT7162006Mapper wireAttributesMT7162006Mapper;	
	@Autowired
	private WireAttributesYbt53592010Mapper wireAttributesYbt53592010Mapper;
	/**
	 * 测试三个表单数据列表
	 */
//    @Test
    public void testGetFromValueDataList() {
    	Response<Map<String, List<String>>> response = fromValueDataService.getFromValueList("GB/T 20067-2017");
    	System.out.println(response.getData());
    }
    /**
     * 测试结构列表
     */
//    @Test
    public void testGetStructureList() {
    	Response<List<?>> response = structureService.showAllStructureById("GB/T 20118-2017");
    	System.out.println(response.getData());
    	Response<List<?>> response2 = structureService.showAllStructureById("YB/T 5359-2010");
    	System.out.println(response2.getData());
    	Response<List<?>> response3 = structureService.showAllStructureById("GB/T 8918-2006");
    	System.out.println(response3.getData());
    	Response<List<?>> response4 = structureService.showAllStructureById("GB/T 20118-2017");
    	System.out.println(response4.getData());
    }
    /**
     * 测试该模糊查询结构的方法
     */
//    @Test
    public void testSqlSelectByBlurStruc() {
    	List<WireAttributesGbt200672017> list = wireAttributesGbt200672017Mapper.selectByBlurStruc("6");
    	System.out.println(list);
    	//WireAttributesGbt201182017
    	List<WireAttributesGbt201182017> list2 = wireAttributesGbt201182017Mapper.selectByBlurStruc("6");
    	System.out.println(list);
    	 List<WireAttributesGBT89182006> selectByBlurStruc = wireAttributesGBT89182006Mapper.selectByBlurStruc("6");
    	System.out.println(selectByBlurStruc);
    	List<WireAttributesYbt53592010> selectByBlurStruc2 = wireAttributesYbt53592010Mapper.selectByBlurStruc("6");
    	System.out.println(selectByBlurStruc2);
    }
}
