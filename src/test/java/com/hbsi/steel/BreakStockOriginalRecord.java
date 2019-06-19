package com.hbsi.steel;

import java.io.IOException;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.dao.WireDataMapper;
import com.hbsi.domain.WireData;
import com.hbsi.domain.WireRope;
import com.hbsi.export.service.BreakOrigRecordService;
import com.hbsi.export.service.ReportExportService;
import com.hbsi.response.Response;
import com.hbsi.thickwire.service.ThickWireService;
import com.hbsi.wire.service.WireRopeService;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=JlCheckApplication.class)
@WebAppConfiguration 
public class BreakStockOriginalRecord {

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
	Logger logger=LoggerFactory.getLogger(this.getClass());
	static BaseDataImportUtil baseDataImpUtil;
	@Test
	public void testWireThick() throws InvalidFormatException, IOException {

//		Response<WireRope> response = wireRopeServicFirst.selectOrCreateWR("WG180927B007");
	//    String path="/Users/admin/Desktop/GBT200672017.xlsx";
	//	List<WireData> list = baseDataImpUtil.importTestWireData1(path);
	//	System.out.println(list.size());
//		List<WireData> wireDataList = wireDataMapper.selectByEnNum("WG1811009");
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
//		WireRope wireRope=new WireRope();
//		wireRope.setId(19);
//		wireRope.setWireDataList(list);
//		wireRope.setEnstrustmentNumber("WG1811009");
//		wireRope.setJudgeStandard("GB/T 20067-2017");
//		wireRope.setSpecification("66");
//		wireRope.setStructure("6X36WS+FC");
//		wireRope.setStrengthLevel("1770");
//		wireRope.setTemperature("18");
//		wireRope.setTestStandard("heiheiheihahahha");
//		wireRope.setSurfaceState("光面");
//		wireRope.setTrialClass("3.70*14/14,3.15*7/7,3.05*7/7,2.35*7/7");
//		wireRope.setTwistingMethod("右交互捻 ZS");
//		thickWireService.judgeThickWire(wireRope);
//		System.out.println(wireRope.getEvaluation());
//		System.out.println(wireRope.getMeasureBreakTensile());
//		System.out.println(wireRope.getMinBreakTensile());
	//	reportExportService.ExportReport("WG1811009");
		breakORService.BreakOrigRecordJson("WG1811009");
	}
}

