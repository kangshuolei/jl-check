package com.hbsi.basedata;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.basedata.zinclayerweight.service.ZincLayerWeightService;
import com.hbsi.domain.ZincLayerWeight;
import com.hbsi.response.Response;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes = JlCheckApplication.class)
@WebAppConfiguration
public class ZincLayerWeightTest {

	@Resource
	private ZincLayerWeightService zincLayerWeightService;

//	@Test
	public void testSelectAllData() {
		Response<List<ZincLayerWeight>> selectZincLayerWeight = zincLayerWeightService.selectZincLayerWeight();
		System.out.println(selectZincLayerWeight);
	}

//	@Test
	public void testDeleteZincLayerWeight() {
		Response<Integer> response = zincLayerWeightService.deleteZincLayerWeight(7);
		System.out.println(response);
	}

//	@Test
	public void testsaveSingleZincLayerWeight() {
		ZincLayerWeight zincLayerWeight = new ZincLayerWeight();
		zincLayerWeight.setDiameteMax(0.1);
		zincLayerWeight.setDiameteMin(0.15);
		zincLayerWeight.setStandardNum("111");
		zincLayerWeight.setSurfaceState("A");
		zincLayerWeight.setValue(1233);
		Response<Integer> response = zincLayerWeightService.saveSingleZincLayerWeight(zincLayerWeight);
		System.out.println(response);
	}

//	@Test
	public void testsaveZincLayerWeight() {
		ZincLayerWeight zincLayerWeight = new ZincLayerWeight();
		zincLayerWeight.setDiameteMax(0.1);
		zincLayerWeight.setDiameteMin(0.15);
		zincLayerWeight.setStandardNum("111");
		zincLayerWeight.setSurfaceState("A");
		zincLayerWeight.setValue(1233);
		ZincLayerWeight zincLayerWeight2 = new ZincLayerWeight();
		zincLayerWeight2.setDiameteMax(0.1);
		zincLayerWeight2.setDiameteMin(0.15);
		zincLayerWeight2.setStandardNum("111");
		zincLayerWeight2.setSurfaceState("A");
		zincLayerWeight2.setValue(1233);
		List<ZincLayerWeight> list = new ArrayList<ZincLayerWeight>();
		list.add(zincLayerWeight);
		list.add(zincLayerWeight2);
		Response<Integer> response = zincLayerWeightService.saveZincLayerWeight(list);
		System.out.println(list);
		System.out.println(response);
	}

//	@Test
	public void testupdateZincLayerWeight() {
		ZincLayerWeight zincLayerWeight2 = new ZincLayerWeight();
		zincLayerWeight2.setId(1);
		zincLayerWeight2.setDiameteMax(0.12);
		zincLayerWeight2.setDiameteMin(0.17);
		zincLayerWeight2.setStandardNum("222");
		zincLayerWeight2.setSurfaceState("B");
		zincLayerWeight2.setValue(1233);
		Response<Integer> response = zincLayerWeightService.updateZincLayerWeight(zincLayerWeight2);
		System.out.println(response);
	}

//	@Test
	public void testDeleteAllData() {
		Response<Integer> deleteAllData = zincLayerWeightService.deleteAllData();

	}
//	/**
//	 * 测试三种read方法
//	 * @throws IOException 
//	 */
////	@Test
//	public void testReadMetod1() throws IOException {
//		File file = new File("/Users/admin/Desktop/test.docx");
//		InputStream is = new FileInputStream(file);
//		OutputStream out = new FileOutputStream("/Users/admin/Desktop/xiangze/ctest.docx");
//		byte[] b=new byte[is.available()];
//		System.out.println(is.available()+"看看这个是不是文件总长度");
//		int len = 0;//代表当前读到缓冲区的长度
//		while(len<is.available()) {
//			len+=is.read(b, len, is.available()-len);
//			System.out.println(len+"/n");
//		}
//		out.write(b);
//		System.out.println(b.length+"kankan现在b的长度");
//		out.flush();
//		out.close();
//		is.close();
//	}
//	
//	/**
//	 * 测试三种read方法
//	 * @throws IOException 
//	 */
////	@Test
//	public void testReadMetod2() throws IOException {
//		File file = new File("/Users/admin/Desktop/test.docx");
//		InputStream is = new FileInputStream(file);
//		OutputStream out = new FileOutputStream("/Users/admin/Desktop/xiangze/csstest.docx");
//		byte[] b=new byte[1024];
//		int len = 0;//代表当前读到缓冲区的长度
//		while((len = is.read(b)) != -1) {
//			System.out.println(len);
//			out.write(b);
//		}
//		out.flush();
//		out.close();
//		is.close();
//	}
//	
//	/**
//	 * 测试三种read方法
//	 * @throws IOException 
//	 */
////	@Test
//	public void testReadMetod3() throws IOException {
//		File file = new File("/Users/admin/Desktop/test.docx");
//		InputStream is = new FileInputStream(file);
//		OutputStream out = new FileOutputStream("/Users/admin/Desktop/xiangze/csstest.docx");
//		byte[] b=new byte[is.available()];
//		int len = 0;//代表当前读到缓冲区的长度
//		while((len = is.read(b)) != -1) {
//			System.out.println(len);
//			out.write(b);
//		}
//		out.flush();
//		out.close();
//		is.close();
//	}
//	/**
//	 * 测试yml的加载方式
//	 */
//	@Value("${path.basePath}")
//	String basePath;
////	@Test
//	public void testYmlLoading() {
//		System.out.println(basePath);
//	}
//	//测试流怎么关闭
//	@Test
//	public void testStreamCloseMethod() {
//		try {
//		File file = new File("/Users/admin/Desktop/test.docx");
//		InputStream is = new FileInputStream(file);
//		OutputStream out = new FileOutputStream("/Users/admin/Desktop/xiangze/csstest.docx");
//		byte[] b=new byte[is.available()];
//		int len = 0;//代表当前读到缓冲区的长度
//			while((len = is.read(b)) != -1) {
//				out.write(b);
//			}
//			while(out != null) {
//				out.close();
//				System.out.println(out.toString());
//			}
//		is.close();
//	}catch(Exception e) {
//		}
//	}

//	@Test
	public void testFileListFunction() {
		File file = new File("/Users/admin/Desktop/xiangze/02Y.xls");
		boolean exists = file.exists();
		System.out.println(exists);
		String path = "/Users/admin/Desktop/xiangze/";
		File file2 = new File(path);
		String[] fileNames = file2.list();
		System.out.println(Arrays.asList(fileNames)); 
//		//测试DateFormat方法
//		SimpleDateFormat monthFormat = new SimpleDateFormat("yyyy-MM");
//		String monthDate = monthFormat.format(new Date());
//		System.out.println(monthDate);
//		SimpleDateFormat dayFormat = new SimpleDateFormat("yyMMdd");
//		String dayForm = dayFormat.format(new Date());
//		System.out.println(dayForm);
	}

//	@Test
	public void testArrayListMethod() {
//        String[] s = {"aa","bb","cc"};
//        List<String> strlist = Arrays.asList(s);
//        for(String str:strlist){
//            System.out.println(str);
//        }
//        System.out.println("------------------------");
//        //基本数据类型结果打印为一个元素
//        int[] i ={11,22,33}; 
//        List intlist = Arrays.asList(i);
//        for(Object o:intlist){
//            System.out.println(((Integer)o));
//        }
//        System.out.println("------------------------");
//        Integer[] ob = {11,22,33};
//        List<Integer> oblist = Arrays.asList(ob);
//        for(int a:oblist){
//            System.out.println(a);
//        }
		System.out.println("------------------------");
		String[] sb = { "aa", "bb", "cc" };
		List<String> strlist = Arrays.asList(sb);
		System.out.println(strlist);
		System.out.println("------------------------");
		List<String> ebsCodes = new ArrayList();
		ebsCodes.add("USERNAME");
		ebsCodes.add("REAP");
		ebsCodes.add("NLS");
		System.out.println(ebsCodes);

	}

	/**
	 * 这里是测试的FIleInputStream导出方式
	 */
//	@Test
	public void testPOIImportExcelINputStream() {
		String finalXlsxPath = "/Users/admin/Desktop/xiangze/29B.xls";
//		InputStream stream = getClass().getClassLoader().getResourceAsStream(finalXlsxPath); 
//		POIFSFileSystem fs = new POIFSFileSystem(stream);
//		Workbook workBook = new HSSFWorkbook(fs);
		// 读取Excel文档
		File finalXlsxFile = new File(finalXlsxPath);
		try (FileInputStream in = new FileInputStream(finalXlsxFile);
				FileOutputStream fos = new FileOutputStream("/Users/admin/Desktop/xiangze/test.xls")) {
			Workbook workBook = new HSSFWorkbook(in);
//			int len =0;
//			if((len = in.read())!= -1) {
//				fos.write(len);
//			}
			workBook.write(fos);
			workBook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("export is successful");
	}

	/**
		InputStream stream = getClass().getClassLoader().getResourceAsStream(finalXlsxPath);
	 * 这里是测试的POIFSFileSystem导入方式
	 */
//	@Test
	public void testPOIImportExcelPOIFSFileSystem() {
//		String finalXlsxPath = "/Users/admin/Desktop/xiangze/29B.xls";
//		String basePath = getClass().getClassLoader().getResource("").getPath();
//		System.out.println(basePath);
//		
		try (POIFSFileSystem fs = new POIFSFileSystem(new FileInputStream(new File("/Users/admin/Desktop/xiangze/29B.xls")));
				FileOutputStream fos = new FileOutputStream("/Users/admin/Desktop/xiangze/test2.xls")) {
			Workbook workBook = new HSSFWorkbook(fs);
			
		        //获取第一个画布
		        Sheet sheet = workBook.getSheetAt(0);
		        CellReference cellReference = new CellReference("A4");
		        boolean flag = false;
		        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum();) {  
		            Row r = sheet.getRow(i);
		            if(r == null){
		                //如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动  
		                sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);  
		                	continue;  
		            }  
		            flag = false;
		            for(Cell c : r){
		                if(c.getCellType() != Cell.CELL_TYPE_BLANK){  
		                    flag = true;  
		                       break;  
		                }
		            }  
		            if(flag){  
		                i++;  
		                continue;  
		            }else{//如果是空白行（即可能没有数据，但是有一定格式）  
		                if(i == sheet.getLastRowNum()){
		                	//如果到了最后一行，直接将那一行remove掉  
		                	sheet.removeRow(r);  
		                }else{
		                	//如果还没到最后一行，则数据往上移一行  
		                	sheet.shiftRows(i+1, sheet.getLastRowNum(),-1);  
		                }
		            }  
		        }
		        System.out.println("有效行数为:"+(sheet.getLastRowNum()+1));

			
			
			// 读取Excel文档
//			int len =0;
			workBook.write(fos);
			workBook.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("export is successful");
	}

}
