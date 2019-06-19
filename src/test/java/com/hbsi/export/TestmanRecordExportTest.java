package com.hbsi.export;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.hbsi.JlCheckApplication;
import com.hbsi.export.service.TestmanRecordService;

@RunWith(SpringJUnit4ClassRunner.class)
@Configuration
@SpringBootTest(classes =JlCheckApplication.class)
public class TestmanRecordExportTest {

	@Autowired
	private TestmanRecordService testmanRecordService;
	@Test
	public void testExport() throws IOException {
	
//		testmanRecordService.ExportTestmanRecord("WG20181125126");
	}
}
