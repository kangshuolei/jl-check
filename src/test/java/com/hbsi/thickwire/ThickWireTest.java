package com.hbsi.thickwire;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.util.ObjectUtils;

import com.hbsi.JlCheckApplication;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.dao.WireDataMapper;
import com.hbsi.dao.WireRopeMapper;
import com.hbsi.domain.WireData;
import com.hbsi.domain.WireRope;
import com.hbsi.export.service.BreakOrigRecordService;
import com.hbsi.export.service.ReportExportService;
import com.hbsi.thickwire.service.ThickWireService;
import com.hbsi.wire.service.WireRopeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=JlCheckApplication.class)
@WebAppConfiguration 
public class ThickWireTest {

	@Autowired
	 private WireRopeService wireRopeServicFirst;
	@Autowired
	private ThickWireService thickWireService;
	@Autowired
	private ReportExportService reportExportService;
	@Autowired
	private WireDataMapper wireDataMapper;
	@Autowired
	private BreakOrigRecordService breakORService;
	@Autowired
	private WireRopeMapper wireRopeMapper;
	Logger logger=LoggerFactory.getLogger(this.getClass());
	static BaseDataImportUtil baseDataImpUtil;
//	@Test
	public void testWireThick() throws InvalidFormatException, IOException {
		//wireRopeServicFirst.selectOrCreateWR("WG1811009","",1);
//	    String path="/Users/admin/Desktop/dataForTest/GBT200672017.xlsx";
//		List<WireData> list = baseDataImpUtil.importTestWireData1(path);
//		System.out.println(list.size());
		List<WireData> wireDataList = wireDataMapper.selectByEnNum("WG1811009");
//		if(wireDataList.isEmpty()) {
//			try {
//				for(WireData w:list) {
//					w.setEntrustmentNumber("WG1811009");
//					wireDataMapper.insertSelective(w);
//				}
//				logger.info("导入wireDataList到数据库成功");
//			} catch (Exception e) {
//				logger.error("导入到数据库失败了，我也不知道为啥");
//			}
//			
//		}
		WireRope wireRope= wireRopeMapper.selectByEnsNum("WG1811009");
		wireRope.setWireDataList(wireDataList);
		wireRope.setJudgeStandard("GB/T 20067-2017");
		wireRope.setSpecification("66");
		wireRope.setStructure("6X36WS+FC");
		wireRope.setStrengthLevel("1770");
		wireRope.setTemperature("18");
		wireRope.setTestStandard("heiheiheihahahha");
		wireRope.setSurfaceState("光面");
		wireRope.setTrialClass("3.70*14/14,3.15*7/7,3.05*7/7,2.35*7/7");
		wireRope.setTwistingMethod("右交互捻 ZS");
		thickWireService.judgeThickWire(wireRope);
		System.out.println(wireRope.getEvaluation());
		System.out.println(wireRope.getMeasureBreakTensile());
		System.out.println(wireRope.getMinBreakTensile());
//		Map<String, Object> map = reportExportService.ExportReportJson("WG20181125137");
//		System.out.println(map);
//		breakORService.BreakOrigRecord("WG1811009");
		
	}
	
//	@Test
	public void testBasePath() {
		WireData w = new WireData();
		w.setEntrustmentNumber("WG20181125137");
		w.setnDiamete(1.38);
//		List<String> strengthList = wireDataMapper.selectStrengthJudge(w);
//		Integer i =0;
//		for(String m :strengthList) {
//			if(ObjectUtils.isEmpty(m)) {
//				
//			}else {
//				i++;
//			}
//		}
//		System.out.println(i);
//		String entrustNum = "WG20181216B086";
//		String trialClass = wireRopeMapper.selectByEnsNum(entrustNum).getTrialClass();
//		String[] splits = trialClass.split(",");
//		List<String> ndiameterSequenceList = new ArrayList<>();
//		for (String s : splits) {
//			String ndiameter = s.substring(0, s.indexOf("*"));
//			ndiameterSequenceList.add(ndiameter);
//		}
//		System.out.println(ndiameterSequenceList.toString());
	}
}
