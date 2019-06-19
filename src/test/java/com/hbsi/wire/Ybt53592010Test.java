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
import com.hbsi.wire.service.Ybt53592010Service;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes=JlCheckApplication.class)
@WebAppConfiguration
public class Ybt53592010Test {

	@Autowired
	private Ybt53592010Service ybtService;
	@Autowired
	private WireRopeService wireRopeService;
	@Autowired
	private WireRopeMapper wireRopeMapper;
	@Autowired
	private WireDataMapper wireDataMapper;
	static BaseDataImportUtil baseDataImpUtil;
	
	@Test
	public void testTbt53592010() throws InvalidFormatException, IOException {
		String path="/Users/admin/Desktop/dataForTest/Ybt53592010.xlsx";
		List<WireData> list = baseDataImpUtil.importTestWireData2(path);
		for(WireData w : list) {
			w.setEntrustmentNumber("WG1811080999");
			int insert = wireDataMapper.insert(w); 
		}
		WireRope wireRope= wireRopeMapper.selectByEnsNum("WG1811080999");
		wireRope.setWireDataList(list);
		wireRope.setStrengthLevel("2160");
		wireRope.setSurfaceState("光面");
		wireRope.setTrialClass("1.38*10/10,1.00*5/5,0.85*5/5,0.78*5/5");
		wireRope.setJudgeStandard("YB/T 5359-2010");
		wireRope.setStructure("8×K26WS＋IWRC");
		wireRope.setTwistingMethod("右交互捻 sZ");
		wireRope.setSpecification("22");
		ybtService.judgeWireRopeYbt53592010(wireRope);
		System.out.println(wireRope.getEvaluation());
		System.out.println(wireRope.getMeasureBreakTensile());
		System.out.println(wireRope.getMinBreakTensile());
	}
}
