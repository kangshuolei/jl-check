package com.hbsi.basedata;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JlCheckApplication.class)
@WebAppConfiguration
public class TestDisposeExcel {

	/**
	 * 处理excel
	 */
	@Test
	public void testDisposeExcel() {
		try (FileInputStream fis = new FileInputStream("/Users/admin/Desktop/api239第一版.xls");
				FileOutputStream fos = new FileOutputStream("/Users/admin/Desktop/api239最终版.xls");
				Workbook workBook = new HSSFWorkbook(fis);) {
			HSSFSheet sheet = (HSSFSheet) workBook.getSheetAt(0);

			CellReference cellReference = new CellReference("A1");
			boolean flag = false;
			for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {
				Row r = sheet.getRow(i);
				if (r == null) {
					// 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
					sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
					continue;
				}
				flag = false;
				for (Cell c : r) {
					if (c.getCellType() != Cell.CELL_TYPE_BLANK) {
						flag = true;
						break;
					}
				}
				if (flag) {
					i++;
					continue;
				} else {// 如果是空白行（即可能没有数据，但是有一定格式）
					if (i == sheet.getLastRowNum()) {
						// 如果到了最后一行，直接将那一行remove掉
						sheet.removeRow(r);
					} else {
						// 如果还没到最后一行，则数据往上移一行
						sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
					}
				}
			}
			System.out.println("有效行数为:" + (sheet.getLastRowNum() + 1));
			
			workBook.write(fos);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
