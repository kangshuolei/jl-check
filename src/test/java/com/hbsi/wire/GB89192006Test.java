package com.hbsi.wire;

import java.io.IOException;
import java.util.List;

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
import com.hbsi.wire.service.WireRopeServiceGB8918;

import ch.qos.logback.classic.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=JlCheckApplication.class)
@WebAppConfiguration
public class GB89192006Test {

	@Autowired
	private WireDataMapper wireDataMapper;
	@Autowired
	private WireRopeMapper wireRopeMapper;
	@Autowired
	private WireRopeServiceGB8918 wireRopeService;
	@Autowired
	 private WireRopeService wireRopeServicFirst;
	static BaseDataImportUtil baseDataImpUtil;
	
	@Test
	public void testWireRope() throws InvalidFormatException, IOException {
		String path="/Users/admin/Desktop/dataForTest/gb89192006.xlsx";
		List<WireData> list = baseDataImpUtil.importTestWireData(path);
		List<WireData> list2 = wireDataMapper.selectByEnNum("WG181107666");
		if(list2.size() == 0) {
			
			for(WireData w : list) {
				w.setEntrustmentNumber("WG181107666");
				int effectedNum = wireDataMapper.insert(w);
			}
		}
		WireRope wireRope=wireRopeMapper.selectByEnsNum("WG181107666");
		wireRope.setWireDataList(list);
		wireRope.setJudgeStandard("GB/T 8918-2006");
		wireRope.setSpecification("14");
		wireRope.setStructure("4V×39S＋5FC");
		wireRope.setStrengthLevel("1960");
		wireRope.setSurfaceState("光面");
		wireRope.setTrialClass("1*15/15,0.75*24/24");
		wireRope.setTwistingMethod("左交互捻 SZ");
		wireRopeService.judgeWire(wireRope);
//		for(int i=0;i<wireRope.getWireDataList().size();i++) {
//			WireData w=wireRope.getWireDataList().get(i);
//			Double tensile = w.getBreakTensile();
//			System.out.println(tensile);
//		}
		System.out.println(wireRope.getMeasureBreakTensile());
		System.out.println(wireRope.getMinBreakTensile());
		System.out.println(wireRope.getEvaluation());
	}
}
