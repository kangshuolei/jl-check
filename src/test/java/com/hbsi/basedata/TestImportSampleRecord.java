package com.hbsi.basedata;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.basedata.samplerecord.service.ImportSampleRecordService;
import com.hbsi.common.utils.BaseDataImportUtil;
import com.hbsi.domain.SampleRecord;

import ch.qos.logback.classic.Logger;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class TestImportSampleRecord {

	@Autowired
	private ImportSampleRecordService importSampleRecordService;
	@Test
	public void testImportData() throws InvalidFormatException, IOException {
	
		String path="/Users/admin/Desktop/JLSJ/标准gbt20118测试数据/批次号009.xlsx";
		List<SampleRecord> list = new ArrayList<>();
		try {
			list = BaseDataImportUtil.importIntoSimpleRecord(path);
		} catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(list.size());
		importSampleRecordService.saveSampleRecordList(list);
	}
}
