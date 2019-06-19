package com.hbsi.wire;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.dao.WireDataMapper;
import com.hbsi.dao.WireRopeMapper;
import com.hbsi.domain.WireData;
import com.hbsi.domain.WireRope;
import com.hbsi.wire.service.WireRopeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class WireRopeTest {
	
	@Resource
	private WireRopeService wireRopeService;
	@Autowired
	private WireRopeMapper wireRopeMapper;
	@Autowired
	private WireDataMapper wireDataMapper;
	static BaseDataImportUtil baseDataImpUtil;
//	@Test
	public void testselectOrCreateWR() {
		//System.out.println(wireRopeService.selectOrCreateWR("WG180927B007","",1));
	}
//	@Test
	public void testselectWireData() {
		WireRope wireRope=new WireRope();
		wireRope.setEnstrustmentNumber("WG180927B007");
		wireRope.setId(1);
		wireRope.setTrialClass("4.05*5/25,3.15*3/9");
		System.out.println(wireRopeService.selectWireData(wireRope));
	}
//	@Test
	public void testJudge() {
		WireRope wireRope=wireRopeMapper.selectWireRope("WG180927B007");
		wireRope.setStrengthLevel("1770");
		wireRope.setStructure("6×31WS＋IWR");
		wireRope.setSurfaceState("A级镀锌");
		
//		wireRope.setEnstrustmentNumber("WG180927B007");
//		wireRope.setId(1);
//		wireRope.setTrialClass("4.05*5/25,3.15*3/9");
		System.out.println(wireRopeService.judgeWireRopeGbt201182017(wireRope));
	
	}
//	@Test
	public void testUpdateStandard() {
		wireRopeService.updateStandard("WG180927B007", "GB/T228.1-2010");
	}
	
//	@Test
	public void testMadeByZhou() throws InvalidFormatException, IOException {
		String path="/Users/admin/Desktop/dataForTest/Gbt201182017.xlsx";
		List<WireData> list = baseDataImpUtil.importTestWireData3(path);
		List<WireData> list2 = wireDataMapper.selectByEnNum("WG1810024111");
		if(list2 == null) {
			
			for(WireData w : list) {
				w.setEntrustmentNumber("WG1810024111");
				int num = wireDataMapper.insert(w);
			}
		}
		WireRope wireRope=wireRopeMapper.selectByEnsNum("WG1810024111"); 
		wireRope.setWireDataList(list);
		wireRope.setStrengthLevel("1670");
		wireRope.setStructure("6X37M+FC");
		wireRope.setSurfaceState("光面");
		wireRope.setSpecification("30");
		wireRope.setJudgeStandard("GB/T 20118-2017");
		wireRope.setTrialClass("1.40*36/36");
		wireRope.setTwistingMethod("右交互捻sZ");
		wireRopeService.judgeWireRopeGbt201182017(wireRope);
		System.out.println(wireRope.getMeasureBreakTensile());
		System.out.println(wireRope.getMinBreakTensile());
		System.out.println(wireRope.getEvaluation());
	
	}
	//测试记录号的生成格式是否正确
	@Test
	public void testReportNumber() {
		String enstrustmentNumber = "WG1812075";
		String sampleBatch = enstrustmentNumber.substring(enstrustmentNumber.length()-3);
//		SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//		SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
//		SimpleDateFormat sd=new SimpleDateFormat("yyMM");
//	    /**
//	     * 事实证明这种方法并不可行呀
//	     */
//		SimpleDateFormat sd1=new SimpleDateFormat("yMMdd");
//		Date date = new Date();
//		System.out.println(sdf1.format(date));
//		System.out.println("L-"+sdf.format(date)+"-"+sampleBatch);
//		System.out.println("DG"+sd.format(date)+sampleBatch);
//		System.out.println(sd1.format(date)+"看一下这样格式化日期行不行");
		String batch=enstrustmentNumber.substring(enstrustmentNumber.length()-3);
		System.out.println("L-"+"20"+enstrustmentNumber.substring(2, 4)+"-"+enstrustmentNumber.substring(4, 6)+"-"+sampleBatch);
		System.out.println("DG"+enstrustmentNumber.substring(2));
	}

}
