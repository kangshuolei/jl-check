package com.hbsi.common.utils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Row;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.ObjectUtils;

import com.hbsi.basedata.fromvalue.service.FromValueService;
import com.hbsi.common.utils.excel.ImportExcel;
import com.hbsi.domain.DiameterDeviation;
import com.hbsi.domain.FromValue;
import com.hbsi.domain.ReverseBending;
import com.hbsi.domain.SampleRecord;
import com.hbsi.domain.SteelTensileStrength;
import com.hbsi.domain.StrengthDeviation;
import com.hbsi.domain.TensileStrength;
import com.hbsi.domain.WireAttributesApi9a2011;
import com.hbsi.domain.WireAttributesEn123852002;
import com.hbsi.domain.WireAttributesGBT89182006;
import com.hbsi.domain.WireAttributesGbt200672017;
import com.hbsi.domain.WireAttributesGbt201182017;
import com.hbsi.domain.WireAttributesYbt53592010;
import com.hbsi.domain.WireData;
import com.hbsi.domain.ZincLayerWeight;
import com.hbsi.response.Response;

import ch.qos.logback.core.net.SyslogOutputStream;

/**
 * 基础数据导入工具类
 * 
 * @author hds
 *
 */
public class BaseDataImportUtil {

	/**
	 * 导入表单数据
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<FromValue> importFromValue(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 0);
		List<FromValue> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			FromValue fromvalue = new FromValue();
			fromvalue.setStandardNum(name);
			String ratedStrength = ei.getCellValue(row, 0).toString();
			String surfaceState = ei.getCellValue(row, 1).toString();
			String twistingMethod = ei.getCellValue(row, 2).toString();
			if (StringUtils.isNotBlank(ratedStrength))
				fromvalue.setRatedStrength(ratedStrength);
			fromvalue.setSurfaceState(surfaceState.toString());
			fromvalue.setTwistingMethod(twistingMethod.toString());
			fromvalue.setState("000");
			System.out.println(fromvalue);
			list.add(fromvalue);
			System.out.print("\n");
		}
		System.out.println(list);
		System.out.println(list.size());
		return list;
	}

	/**
	 * String filename=file.getName(); String name = filename.substring(0,
	 * filename.indexOf(".")); System.out.println(name); ImportExcel ei = new
	 * ImportExcel(file, 0, 0); 导入表单数据
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<FromValue> importFromValue(File file) throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 0);
		List<FromValue> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			FromValue fromvalue = new FromValue();
			fromvalue.setStandardNum(name);
			String ratedStrength = ei.getCellValue(row, 0).toString();
			String surfaceState = ei.getCellValue(row, 1).toString();
			String twistingMethod = ei.getCellValue(row, 2).toString();
			if (StringUtils.isNotBlank(ratedStrength))
				fromvalue.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
			fromvalue.setSurfaceState(surfaceState.toString());
			fromvalue.setTwistingMethod(twistingMethod.toString());
			fromvalue.setState("000");
			System.out.println(fromvalue);
			list.add(fromvalue);
			System.out.print("\n");
		}
		System.out.println(list);
		return list;
	}

	/**
	 * 导入抗拉强度数据
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<TensileStrength> importTensileStrength(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 4);
		List<TensileStrength> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		TensileStrength tensileStrength;
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 1; j < ei.getLastCellNum(); j++) {
				String val = ei.getCellValue(row, j).toString();
				if (ObjectUtils.isEmpty(val)) {

				} else {
					tensileStrength = new TensileStrength();
					tensileStrength.setStandardNum(name);
					Integer value = Integer.valueOf(val.substring(0, val.indexOf(".")));
					String type = ei.getCellValue(row, 0).toString();
					String ratedStrengthVal = ei.getCellValue(ei.getRow(0), j).toString();
					Integer ratedStrength = Integer
							.valueOf(ratedStrengthVal.substring(0, ratedStrengthVal.indexOf(".")));
					tensileStrength.setStrengthValue(value);
					tensileStrength.setRatedStrength(ratedStrength);
					tensileStrength.setType(type.replace("最低抗拉强度", ""));
					System.out.print(tensileStrength + ", ");
					System.out.print("\n");
					list.add(tensileStrength);
				}
			}

		}
		System.out.println(list.size());
		System.out.println(list);
		return list;
	}

	/**
	 * 导入抗拉强度数据
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<TensileStrength> importTensileStrength(File file) throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 3);
		List<TensileStrength> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		TensileStrength tensileStrength;
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 1; j < ei.getLastCellNum(); j++) {
				String val = ei.getCellValue(row, j).toString();
				if (ObjectUtils.isEmpty(val)) {

				} else {
					tensileStrength = new TensileStrength();
					tensileStrength.setStandardNum(name);
					Integer value = Integer.valueOf(val.substring(0, val.indexOf(".")));
					String type = ei.getCellValue(row, 0).toString();
					String ratedStrengthVal = ei.getCellValue(ei.getRow(0), j).toString();
					Integer ratedStrength = Integer
							.valueOf(ratedStrengthVal.substring(0, ratedStrengthVal.indexOf(".")));
					tensileStrength.setStrengthValue(value);
					tensileStrength.setRatedStrength(ratedStrength);
					tensileStrength.setType(type.replace("最低抗拉强度", ""));
					tensileStrength.setState("000");
					System.out.print(tensileStrength + ", ");
					System.out.print("\n");
					list.add(tensileStrength);
				}
			}

		}
		System.out.println(list.size());
		System.out.println(list);
		return list;
	}

	/**
	 * 导入锌层重量数据
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ZincLayerWeight> importZincLayerWeight(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 8);
		List<ZincLayerWeight> list = new ArrayList<ZincLayerWeight>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				String val = ei.getCellValue(row, j).toString();
				if (ObjectUtils.isEmpty(val)) {

				} else {
					ZincLayerWeight zincLayerWeight = new ZincLayerWeight();
					zincLayerWeight.setStandardNum(name);
					Integer value = Integer.valueOf(val.substring(0, val.indexOf(".")));
					String type = ei.getCellValue(ei.getRow(0), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					zincLayerWeight.setDiameteMax(diameteMax);
					zincLayerWeight.setDiameteMin(diameteMin);
					zincLayerWeight.setSurfaceState(type);
					zincLayerWeight.setValue(value);
					zincLayerWeight.setState("000");
					System.out.print(zincLayerWeight + ", ");
					System.out.print("\n");
					list.add(zincLayerWeight);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 导入锌层重量数据
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ZincLayerWeight> importZincLayerWeight(File file) throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 7);
		List<ZincLayerWeight> list = new ArrayList<ZincLayerWeight>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				String val = ei.getCellValue(row, j).toString();
				if (ObjectUtils.isEmpty(val)) {

				} else {
					ZincLayerWeight zincLayerWeight = new ZincLayerWeight();
					zincLayerWeight.setStandardNum(name);
					Integer value = Integer.valueOf(val.substring(0, val.indexOf(".")));
					String type = ei.getCellValue(ei.getRow(0), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					zincLayerWeight.setDiameteMax(diameteMax);
					zincLayerWeight.setDiameteMin(diameteMin);
					zincLayerWeight.setSurfaceState(type);
					zincLayerWeight.setValue(value);
					zincLayerWeight.setState("000");
					System.out.print(zincLayerWeight + ", ");
					System.out.print("\n");
					list.add(zincLayerWeight);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 导入直径数据
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<DiameterDeviation> importDiameterDeviation(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 3);
		List<DiameterDeviation> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		// 获取值，获取该值对应列第一行的数据（类别），该行的第一列的数据（最小值），第三列的数据（最大值。
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < 5; j++) {
				Object val = ei.getCellValue(row, j);
				if (ObjectUtils.isEmpty(val)) {

				} else {
					DiameterDeviation diameterDeviation = new DiameterDeviation();
					diameterDeviation.setStandardNum(name);
					Double value = Double.valueOf(val.toString());
					String type = ei.getCellValue(ei.getRow(0), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					diameterDeviation.setDiameteMin(diameteMin);
					diameterDeviation.setDiameteMax(diameteMax);
					diameterDeviation.setType(type);
					diameterDeviation.setValue(value);
					diameterDeviation.setState("000");
					diameterDeviation.setUsage("Z");
					System.out.print(diameterDeviation + ", ");
					System.out.print("\n");
					list.add(diameterDeviation);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 导入绳数据
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<WireAttributesGbt200672017> importWireAttributesGbt200672017(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 0);
		List<WireAttributesGbt200672017> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {

			Row row = ei.getRow(i);

			WireAttributesGbt200672017 wireAttributes = new WireAttributesGbt200672017();

			String structure = ei.getCellValue(row, 0).toString();

			Double minimumBreakingForce = Double.valueOf(ei.getCellValue(row, 1).toString());

			Double tanningLossFactor = Double.valueOf(ei.getCellValue(row, 2).toString());

//			String partialIntensityLowval = ei.getCellValue(row, 3).toString();
//			if(StringUtils.isNotBlank(partialIntensityLowval));
//			Integer partialIntensityLow = Integer.valueOf(partialIntensityLowval.substring(0,partialIntensityLowval.indexOf(".")));
//			
//			String partialReverseLowval = ei.getCellValue(row, 4).toString();
//			if(StringUtils.isNotBlank(partialReverseLowval));
//			Integer partialReverseLow = Integer.valueOf(partialReverseLowval.substring(0,partialReverseLowval.indexOf(".")));
//			
//			String intensityLowval = ei.getCellValue(row, 3).toString();
//			if(StringUtils.isNotBlank(intensityLowval));
//			Integer intensityLow = Integer.valueOf(intensityLowval.substring(0,intensityLowval.indexOf(".")));
//			
//			String reverseLowval = ei.getCellValue(row, 4).toString();
//			if(StringUtils.isNotBlank(reverseLowval));
//			Integer reverseLow = Integer.valueOf(reverseLowval.substring(0,reverseLowval.indexOf(".")));
//			
//			Double twistDistance = Double.valueOf(ei.getCellValue(row, 5).toString());
			wireAttributes.setStructure(structure);
			wireAttributes.setMinimumBreakingForce(minimumBreakingForce);
			wireAttributes.setTanningLossFactor(tanningLossFactor);
//			wireAttributes.setPartialIntensityLow(partialIntensityLow);
//			wireAttributes.setPartialReverseLow(partialReverseLow);
//			wireAttributes.setIntensityLow(intensityLow);
//			wireAttributes.setReverseLow(reverseLow);
//			wireAttributes.setTwistDistance(twistDistance);
			System.out.println(wireAttributes);

			list.add(wireAttributes);
			System.out.print("\n");
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 导入绳数据
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<WireAttributesGbt201182017> importWireAttributesGbt201182017(File file)
			throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 2);
		List<WireAttributesGbt201182017> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = ei.getDataRowNum(); i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);

			WireAttributesGbt201182017 wireAttributes = new WireAttributesGbt201182017();

			String structure = ei.getCellValue(row, 0).toString();

			Double minimumBreakingForce = Double.valueOf(ei.getCellValue(row, 1).toString());

			Double tanningLossFactor = Double.valueOf(ei.getCellValue(row, 2).toString());

			String partialIntensityLowval = ei.getCellValue(row, 3).toString();
			if (StringUtils.isNotBlank(partialIntensityLowval))
				;
			Integer partialIntensityLow = Integer
					.valueOf(partialIntensityLowval.substring(0, partialIntensityLowval.indexOf(".")));

			String partialReverseLowval = ei.getCellValue(row, 3).toString();
			if (StringUtils.isNotBlank(partialReverseLowval))
				;
			Integer partialReverseLow = Integer
					.valueOf(partialReverseLowval.substring(0, partialReverseLowval.indexOf(".")));

			String intensityLowval = ei.getCellValue(row, 3).toString();
			if (StringUtils.isNotBlank(intensityLowval))
				;
			Integer intensityLow = Integer.valueOf(intensityLowval.substring(0, intensityLowval.indexOf(".")));

			String reverseLowval = ei.getCellValue(row, 4).toString();
			if (StringUtils.isNotBlank(reverseLowval))
				;
			Integer reverseLow = Integer.valueOf(reverseLowval.substring(0, reverseLowval.indexOf(".")));

			Double twistDistance = Double.valueOf(ei.getCellValue(row, 5).toString());

			wireAttributes.setStructure(structure);
			wireAttributes.setMinimumBreakingForce(minimumBreakingForce);
			wireAttributes.setTanningLossFactor(tanningLossFactor);
			wireAttributes.setPartialIntensityLow(partialIntensityLow);
			wireAttributes.setPartialReverseLow(partialReverseLow);
			wireAttributes.setIntensityLow(intensityLow);
			wireAttributes.setReverseLow(reverseLow);
			wireAttributes.setTwistDistance(twistDistance);
			wireAttributes.setState("000");
			System.out.println(wireAttributes);

			list.add(wireAttributes);
			System.out.print("\n");
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 导入扭转数据
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ReverseBending> importReverse(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 3);
		List<ReverseBending> list = new ArrayList<>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 2; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 7; j <= 18; j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					String type = "R";
					ReverseBending reverseBeding = new ReverseBending();
					reverseBeding.setStandardNum(name);
					Integer valueRob = Integer.valueOf(val.toString().substring(0, val.toString().indexOf(".")));
					String ratedStrength = ei.getCellValue(ei.getRow(1), j).toString();
					String surfaceState = ei.getCellValue(ei.getRow(0), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					reverseBeding.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
					reverseBeding.setSurfaceState(surfaceState);
					reverseBeding.setDiameteMin(diameteMin);
					reverseBeding.setDiameteMax(diameteMax);
					reverseBeding.setValueRob(valueRob);
					reverseBeding.setType(type);
					System.out.print(reverseBeding + ", ");
					System.out.print("\n");
					list.add(reverseBeding);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 导入扭转数据
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ReverseBending> importReverse(File file) throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 5);
		List<ReverseBending> list = new ArrayList<>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 2; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					ReverseBending reverseBeding = new ReverseBending();
					reverseBeding.setStandardNum(name);
					Integer valueRob = Integer.valueOf(val.toString().substring(0, val.toString().indexOf(".")));
					String surfaceState = ei.getCellValue(ei.getRow(0), j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(1), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					reverseBeding.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
					reverseBeding.setSurfaceState(surfaceState);
					reverseBeding.setDiameteMin(diameteMin);
					reverseBeding.setDiameteMax(diameteMax);
					reverseBeding.setValueRob(valueRob);
					reverseBeding.setType("R");
					System.out.print(reverseBeding + ", ");
					System.out.print("\n");
					list.add(reverseBeding);
				}

			}
		}
		return list;
	}

	/**
	 * 导入弯曲数据
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ReverseBending> importBending(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 1);
		List<ReverseBending> list = new ArrayList<>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					ReverseBending reverseBeding = new ReverseBending();
					reverseBeding.setStandardNum(name);
					Integer valueRob = Integer.valueOf(val.toString().substring(0, val.toString().indexOf(".")));
//					String surfaceState = ei.getCellValue(ei.getRow(0),j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(0), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					reverseBeding.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
//					reverseBeding.setSurfaceState(surfaceState);
					reverseBeding.setDiameteMin(diameteMin);
					reverseBeding.setDiameteMax(diameteMax);
					reverseBeding.setValueRob(valueRob);
					reverseBeding.setType("B");
					System.out.print(reverseBeding + ", ");
					System.out.print("\n");
					list.add(reverseBeding);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 导入弯曲数据
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ReverseBending> importBending(File file) throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 1);
		List<ReverseBending> list = new ArrayList<>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					ReverseBending reverseBeding = new ReverseBending();
					reverseBeding.setStandardNum(name);
					Integer valueRob = Integer.valueOf(val.toString().substring(0, val.toString().indexOf(".")));
//					String surfaceState = ei.getCellValue(ei.getRow(0),j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(1), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					reverseBeding.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
//					reverseBeding.setSurfaceState(surfaceState);
					reverseBeding.setDiameteMin(diameteMin);
					reverseBeding.setDiameteMax(diameteMax);
					reverseBeding.setValueRob(valueRob);
					reverseBeding.setType("B");
					System.out.print(reverseBeding + ", ");
					System.out.print("\n");
					list.add(reverseBeding);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 李旭阳直径数据导入方法
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<SteelTensileStrength> importSteelTensileStrength(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 2);
		List<SteelTensileStrength> list = new ArrayList<SteelTensileStrength>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 2; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j = j + 2) {
				Object val1 = ei.getCellValue(row, j);
				Object val2 = ei.getCellValue(row, j + 1);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val1) || ObjectUtils.isEmpty(val2) || ObjectUtils.isEmpty(k)) {

				} else {
					SteelTensileStrength steelTensileStrength = new SteelTensileStrength();
					steelTensileStrength.setStandardNum(name);
					Integer valmin = Integer.valueOf(val1.toString().substring(0, val1.toString().indexOf(".")));
					Integer valmax = Integer.valueOf(val2.toString().substring(0, val2.toString().indexOf(".")));
					String usage = ei.getCellValue(ei.getRow(0), j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(1), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					steelTensileStrength.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
					steelTensileStrength.setUsage(usage);
					steelTensileStrength.setDiameteMin(diameteMin);
					steelTensileStrength.setDiameteMax(diameteMax);
					steelTensileStrength.setStrengthValueMin(valmin);
					steelTensileStrength.setStrengthValueMax(valmax);
					steelTensileStrength.setState("000");
					System.out.print(steelTensileStrength + ", ");
					System.out.print("\n");
					list.add(steelTensileStrength);
				}

			}

		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 李旭阳直径数据导入方法
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<SteelTensileStrength> importSteelTensileStrength(File file)
			throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 2);
		List<SteelTensileStrength> list = new ArrayList<SteelTensileStrength>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 2; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j = j + 2) {
				Object val1 = ei.getCellValue(row, j);
				Object val2 = ei.getCellValue(row, j + 1);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val1) || ObjectUtils.isEmpty(val2) || ObjectUtils.isEmpty(k)) {

				} else {
					SteelTensileStrength steelTensileStrength = new SteelTensileStrength();
					steelTensileStrength.setStandardNum(name);
					Integer valmin = Integer.valueOf(val1.toString().substring(0, val1.toString().indexOf(".")));
					Integer valmax = Integer.valueOf(val2.toString().substring(0, val2.toString().indexOf(".")));
					String usage = ei.getCellValue(ei.getRow(0), j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(1), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					steelTensileStrength.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
					steelTensileStrength.setUsage(usage);
					steelTensileStrength.setDiameteMin(diameteMin);
					steelTensileStrength.setDiameteMax(diameteMax);
					steelTensileStrength.setStrengthValueMin(valmin);
					steelTensileStrength.setStrengthValueMax(valmax);
					steelTensileStrength.setState("000");
					System.out.print(steelTensileStrength + ", ");
					System.out.print("\n");
					list.add(steelTensileStrength);
				}

			}

		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 李旭阳的导入扭转
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ReverseBending> importReverse2(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 1);
		List<ReverseBending> list = new ArrayList<>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 3; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					ReverseBending reverseBending = new ReverseBending();
					reverseBending.setStandardNum(name);
					Integer valueRob = Integer.valueOf(val.toString().substring(0, val.toString().indexOf(".")));
					String usage = ei.getCellValue(ei.getRow(0), j).toString();
					String surfaceState = ei.getCellValue(ei.getRow(1), j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(2), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					reverseBending.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
					reverseBending.setUsage(usage);
					reverseBending.setSurfaceState(surfaceState);
					reverseBending.setDiameteMin(diameteMin);
					reverseBending.setDiameteMax(diameteMax);
					reverseBending.setValueRob(valueRob);
					reverseBending.setType("R");
					System.out.print(reverseBending + ", ");
					System.out.print("\n");
					list.add(reverseBending);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 李旭阳的导入扭转
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ReverseBending> importReverse2(File file) throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 1);
		List<ReverseBending> list = new ArrayList<>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 3; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					ReverseBending reverseBending = new ReverseBending();
					reverseBending.setStandardNum(name);
					Integer valueRob = Integer.valueOf(val.toString().substring(0, val.toString().indexOf(".")));
					String usage = ei.getCellValue(ei.getRow(0), j).toString();
					String surfaceState = ei.getCellValue(ei.getRow(1), j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(2), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					reverseBending.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
					reverseBending.setUsage(usage);
					reverseBending.setSurfaceState(surfaceState);
					reverseBending.setDiameteMin(diameteMin);
					reverseBending.setDiameteMax(diameteMax);
					reverseBending.setValueRob(valueRob);
					reverseBending.setType("R");
					System.out.print(reverseBending + ", ");
					System.out.print("\n");
					list.add(reverseBending);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 李旭阳的导入弯曲
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ReverseBending> importBending2(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 0);
		List<ReverseBending> list = new ArrayList<ReverseBending>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 3; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					ReverseBending reverseBending = new ReverseBending();
					reverseBending.setStandardNum(name);
					Integer valueRob = Integer.valueOf(val.toString().substring(0, val.toString().indexOf(".")));
					String usage = ei.getCellValue(ei.getRow(0), j).toString();
					String surfaceState = ei.getCellValue(ei.getRow(1), j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(2), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					reverseBending.setUsage(usage);
					reverseBending.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
					reverseBending.setSurfaceState(surfaceState);
					reverseBending.setDiameteMin(diameteMin);
					reverseBending.setDiameteMax(diameteMax);
					reverseBending.setValueRob(valueRob);
					reverseBending.setType("B");
					System.out.print(reverseBending + ", ");
					System.out.print("\n");
					list.add(reverseBending);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 李旭阳的导入弯曲
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<ReverseBending> importBending2(File file) throws InvalidFormatException, IOException {
		String filename = file.getName();
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(file, 0, 0);
		List<ReverseBending> list = new ArrayList<ReverseBending>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
		String sheetName = ei.getSheet().getSheetName();
		for (int i = 3; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 3; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					ReverseBending reverseBending = new ReverseBending();
					reverseBending.setStandardNum(name);
					Integer valueRob = Integer.valueOf(val.toString().substring(0, val.toString().indexOf(".")));
					String usage = ei.getCellValue(ei.getRow(0), j).toString();
					String surfaceState = ei.getCellValue(ei.getRow(1), j).toString();
					String ratedStrength = ei.getCellValue(ei.getRow(2), j).toString();
					Double diameteMin = Double.valueOf(ei.getCellValue(ei.getRow(i), 0).toString());
					Double diameteMax = Double.valueOf(ei.getCellValue(ei.getRow(i), 2).toString());
					reverseBending.setUsage(usage);
					reverseBending.setRatedStrength(ratedStrength.substring(0, ratedStrength.indexOf(".")));
					reverseBending.setSurfaceState(surfaceState);
					reverseBending.setDiameteMin(diameteMin);
					reverseBending.setDiameteMax(diameteMax);
					reverseBending.setValueRob(valueRob);
					reverseBending.setType("B");
					System.out.print(reverseBending + ", ");
					System.out.print("\n");
					list.add(reverseBending);
				}

			}
		}
		System.out.println(list.size());
		return list;
	}

	public static List<WireAttributesGbt201182017> importWireAttributesGbt2011820171(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 0);
		List<WireAttributesGbt201182017> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			WireAttributesGbt201182017 wireAttributes = new WireAttributesGbt201182017();
			String structure = ei.getCellValue(row, 0).toString();
			Double minimumBreakingForce = Double.valueOf(ei.getCellValue(row, 1).toString());
			System.out.println(minimumBreakingForce);
			Double tanningLossFactor = Double.valueOf(ei.getCellValue(row, 2).toString());
			String partTensileLow = ei.getCellValue(row, 3).toString();
			Integer partTensileLowVal = Integer.valueOf(partTensileLow.substring(0, partTensileLow.indexOf(".")));
			String partReverseLow = ei.getCellValue(row, 4).toString();
			Integer partReverseLowVal = Integer.valueOf(partReverseLow.substring(0, partReverseLow.indexOf(".")));
			wireAttributes.setStructure(structure);
			wireAttributes.setMinimumBreakingForce(minimumBreakingForce);
			wireAttributes.setTanningLossFactor(tanningLossFactor);
			wireAttributes.setPartialIntensityLow(partTensileLowVal);
			wireAttributes.setPartialReverseLow(partReverseLowVal);
			System.out.println(wireAttributes);
			list.add(wireAttributes);
			System.out.print("\n");
		}
		System.out.println(list.size());
		return list;
	}

	public static List<WireAttributesYbt53592010> importWireAttributesYbt53592010(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 1);
		List<WireAttributesYbt53592010> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			WireAttributesYbt53592010 wireAttributes = new WireAttributesYbt53592010();
			String structure = ei.getCellValue(row, 0).toString();
			Double minimumBreakingForce = Double.valueOf(ei.getCellValue(row, 1).toString());
			Double tanningLossFactor = Double.valueOf(ei.getCellValue(row, 2).toString());
			String partTensileLow = ei.getCellValue(row, 3).toString();
			Integer partTensileLowVal = Integer.valueOf(partTensileLow.substring(0, partTensileLow.indexOf(".")));
			String partReverseLow = ei.getCellValue(row, 4).toString();
			Integer partReverseLowVal = Integer.valueOf(partReverseLow.substring(0, partReverseLow.indexOf(".")));
			String allIntensityLow = ei.getCellValue(row, 5).toString();
			Integer intensityLow = Integer.valueOf(allIntensityLow.substring(0, allIntensityLow.indexOf(".")));
			String allTensileLow = ei.getCellValue(row, 6).toString();
			Integer reverseLow = Integer.valueOf(allTensileLow.substring(0, allTensileLow.indexOf(".")));
			wireAttributes.setStructure(structure);
			wireAttributes.setMinimumBreakingForce(minimumBreakingForce);
			wireAttributes.setTanningLossFactor(tanningLossFactor);
			wireAttributes.setPartialIntensityLow(partTensileLowVal);
			wireAttributes.setPartialReverseLow(partReverseLowVal);
			wireAttributes.setIntensityLow(intensityLow);
			wireAttributes.setReverseLow(reverseLow);
			System.out.println(wireAttributes);
			list.add(wireAttributes);
			System.out.print("\n");
		}
		System.out.println(list.size());
		return list;
	}

	public static List<WireAttributesGBT89182006> importWireAttributesGbt89182006(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 1);
		List<WireAttributesGBT89182006> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			WireAttributesGBT89182006 wireAttributes = new WireAttributesGBT89182006();
			String structure = ei.getCellValue(row, 0).toString();
			Double minimumBreakingForce = Double.valueOf(ei.getCellValue(row, 1).toString());
			Double tanningLossFactor = Double.valueOf(ei.getCellValue(row, 2).toString());
//				String partTensileLow = ei.getCellValue(row, 3).toString();
//				Integer partTensileLowVal = Integer.valueOf(par.tTensileLow.substring(0,partTensileLow.indexOf(".")));
//				String partReverseLow = ei.getCellValue(row, 4).toString();
//				Integer partReverseLowVal = Integer.valueOf(partReverseLow.substring(0,partReverseLow.indexOf(".")));
//				String allIntensityLow = ei.getCellValue(row, 5).toString();
//				Integer intensityLow = Integer.valueOf(allIntensityLow.substring(0,allIntensityLow.indexOf(".")));
//				String allTensileLow = ei.getCellValue(row, 6).toString();
//				Integer reverseLow = Integer.valueOf(allTensileLow.substring(0,allTensileLow.indexOf(".")));
			wireAttributes.setStructure(structure);
			wireAttributes.setMinimumBreakingForce(minimumBreakingForce);
			wireAttributes.setTanningLossFactor(tanningLossFactor);
			wireAttributes.setState("000");
//				wireAttributes.setPartialIntensityLow(partTensileLowVal);
//				wireAttributes.setPartialReverseLow(partReverseLowVal);
//				wireAttributes.setIntensityLow(intensityLow);
//				wireAttributes.setReverseLow(reverseLow);
			System.out.println(wireAttributes);
			list.add(wireAttributes);
			System.out.print("\n");
		}
		System.out.println(list.size());
		return list;
	}

	public static List<WireAttributesGBT89182006> importWireAttributesGbt891820061(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 2);
		List<WireAttributesGBT89182006> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			WireAttributesGBT89182006 wireAttributes = new WireAttributesGBT89182006();
			String structure = ei.getCellValue(row, 0).toString();
			Double minimumBreakingForce = Double.valueOf(ei.getCellValue(row, 1).toString());
			Double tanningLossFactor = Double.valueOf(ei.getCellValue(row, 2).toString());
//				String partTensileLow = ei.getCellValue(row, 3).toString();
//				Integer partTensileLowVal = Integer.valueOf(partTensileLow.substring(0,partTensileLow.indexOf(".")));
//				String partReverseLow = ei.getCellValue(row, 4).toString();
//				Integer partReverseLowVal = Integer.valueOf(partReverseLow.substring(0,partReverseLow.indexOf(".")));
//				String allIntensityLow = ei.getCellValue(row, 5).toString();
//				Integer intensityLow = Integer.valueOf(allIntensityLow.substring(0,allIntensityLow.indexOf(".")));
//				String allTensileLow = ei.getCellValue(row, 6).toString();
//				Integer reverseLow = Integer.valueOf(allTensileLow.substring(0,allTensileLow.indexOf(".")));
			wireAttributes.setStructure(structure);
			wireAttributes.setMinimumBreakingForce(minimumBreakingForce);
			wireAttributes.setTanningLossFactor(tanningLossFactor);
			wireAttributes.setState("000");
//				wireAttributes.setPartialIntensityLow(partTensileLowVal);
//				wireAttributes.setPartialReverseLow(partReverseLowVal);
//				wireAttributes.setIntensityLow(intensityLow);
//				wireAttributes.setReverseLow(reverseLow);
			System.out.println(wireAttributes);
			list.add(wireAttributes);
			System.out.print("\n");
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 仅用于gbt89182006 标准的测试算法
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<WireData> importTestWireData(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String fileName = split[split.length - 1];
		String standerdNum = fileName.substring(0, fileName.indexOf("."));
		ImportExcel ie = new ImportExcel(path, 0, 0);
		System.out.println(ie.getSheet().getSheetName());
		List<WireData> list = new ArrayList<>();
		for (int i = 1; i < ie.getLastDataRowNum(); i++) {
			WireData wireData = new WireData();
			Row row = ie.getRow(i);
			double ndiameter = Double.valueOf(ie.getCellValue(row, 1).toString());
			double diameter = Double.valueOf(ie.getCellValue(row, 2).toString());
			double breakTensile = Double.valueOf(ie.getCellValue(row, 3).toString());
			String tensile = ie.getCellValue(row, 4).toString();
			Integer tensileStrength = Integer.parseInt(tensile.substring(0, tensile.indexOf(".")));
			String r = ie.getCellValue(row, 5).toString();
			Integer reverse = Integer.parseInt(r.substring(0, r.indexOf(".")));
			String bend = ie.getCellValue(row, 6).toString();
			Integer bending = Integer.parseInt(bend.substring(0, bend.indexOf(".")));
			wireData.setBreakTensile(breakTensile);
			wireData.setnDiamete(ndiameter);
			wireData.setDiamete(diameter);
			wireData.setTensileStrength(tensileStrength);
			wireData.setTurnTimes(reverse);
			wireData.setWindingTimes(bending);
			list.add(wireData);
			System.out.println(wireData);
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 用于gbt200672017算法的测试数据导入算法
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<WireData> importTestWireData1(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String fileName = split[split.length - 1];
		String standerdNum = fileName.substring(0, fileName.indexOf("."));
		ImportExcel ie = new ImportExcel(path, 0, 0);
		System.out.println(ie.getSheet().getSheetName());
		List<WireData> list = new ArrayList<>();
		for (int i = 1; i < ie.getLastDataRowNum(); i++) {
			WireData wireData = new WireData();
			Row row = ie.getRow(i);
			double ndiameter = Double.valueOf(ie.getCellValue(row, 1).toString());
			double diameter = Double.valueOf(ie.getCellValue(row, 2).toString());
			double breakTensile = Double.valueOf(ie.getCellValue(row, 3).toString());
			String tensile = ie.getCellValue(row, 4).toString();
			Integer tensileStrength = Integer.parseInt(tensile.substring(0, tensile.indexOf(".")));
			String r = ie.getCellValue(row, 5).toString();
			Integer reverse = Integer.parseInt(r.substring(0, r.indexOf(".")));
//			String bend=ie.getCellValue(row, 6).toString();
//			Integer bending=Integer.parseInt(bend.substring(0,bend.indexOf(".")));
			wireData.setBreakTensile(breakTensile);
			wireData.setnDiamete(ndiameter);
			wireData.setDiamete(diameter);
			wireData.setTensileStrength(tensileStrength);
			wireData.setTurnTimes(reverse);
//			wireData.setWindingTimes(bending);
			list.add(wireData);
			System.out.println(wireData);
//			System.out.println("\n");
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 仅用于YBT53592010 标准的测试算法
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<WireData> importTestWireData2(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String fileName = split[split.length - 1];
		String standerdNum = fileName.substring(0, fileName.indexOf("."));
		ImportExcel ie = new ImportExcel(path, 0, 0);
		System.out.println(ie.getSheet().getSheetName());
		List<WireData> list = new ArrayList<>();
		for (int i = 1; i < ie.getLastDataRowNum(); i++) {
			WireData wireData = new WireData();
			Row row = ie.getRow(i);
			double ndiameter = Double.valueOf(ie.getCellValue(row, 1).toString());
			double breakTensile = Double.valueOf(ie.getCellValue(row, 2).toString());
			String r = ie.getCellValue(row, 3).toString();
			Integer reverse = Integer.parseInt(r.substring(0, r.indexOf(".")));
			String bend = ie.getCellValue(row, 4).toString();
			Integer bending = Integer.parseInt(bend.substring(0, bend.indexOf(".")));
			wireData.setBreakTensile(breakTensile);
			wireData.setnDiamete(ndiameter);
			wireData.setTurnTimes(reverse);
			wireData.setWindingTimes(bending);
			list.add(wireData);
			System.out.println(wireData);
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 仅用于GBT201182017 标准的测试数据导入
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<WireData> importTestWireData3(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String fileName = split[split.length - 1];
		String standerdNum = fileName.substring(0, fileName.indexOf("."));
		ImportExcel ie = new ImportExcel(path, 0, 0);
		System.out.println(ie.getSheet().getSheetName());
		List<WireData> list = new ArrayList<>();
		for (int i = 1; i < ie.getLastDataRowNum(); i++) {
			WireData wireData = new WireData();
			Row row = ie.getRow(i);
			double ndiameter = Double.valueOf(ie.getCellValue(row, 1).toString());
			double diameter = Double.valueOf(ie.getCellValue(row, 2).toString());
			double breakTensile = Double.valueOf(ie.getCellValue(row, 3).toString());
			String tensile = ie.getCellValue(row, 4).toString();
			Integer tensileStrength = Integer.parseInt(tensile.substring(0, tensile.indexOf(".")));
			String r = ie.getCellValue(row, 5).toString();
			Integer reverse = Integer.parseInt(r.substring(0, r.indexOf(".")));
			String bend = ie.getCellValue(row, 6).toString();
			Integer bending = Integer.parseInt(bend.substring(0, bend.indexOf(".")));
			wireData.setBreakTensile(breakTensile);
			wireData.setnDiamete(ndiameter);
			wireData.setDiamete(diameter);
			wireData.setTensileStrength(tensileStrength);
			wireData.setTurnTimes(reverse);
			wireData.setWindingTimes(bending);
			list.add(wireData);
			System.out.println(wireData);
		}
		System.out.println(list.size());
		return list;
	}

	/**
	 * 将数据导入simple_record数据库中的方法
	 * 
	 * @param file
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<SampleRecord> importIntoSimpleRecord(String path) throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String fileName = split[split.length - 1];
		ImportExcel ei = new ImportExcel(path, 0, 0);
		String sheetName = ei.getSheet().getSheetName();
		List<SampleRecord> list = new ArrayList<>();
		// 获取值，获取该值对应列第一行的数据（表面状态），第二行的数据（公称强度），该行的第一列的数据（最小值），第三列的数据（最大值。
//		String sheetName = ei.getSheet().getSheetName();
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			for (int j = 1; j < ei.getLastCellNum(); j++) {
				Object val = ei.getCellValue(row, j);
				Object k = ei.getCellValue(ei.getRow(0), j);
				if (ObjectUtils.isEmpty(val) || ObjectUtils.isEmpty(k)) {

				} else {
					SampleRecord sampleRecord = new SampleRecord();
					sampleRecord.setCreateDate(new Timestamp((new Date()).getTime()));
					sampleRecord.setSampleBatch(262);
					// 实验员编号
					sampleRecord.setTestMembersNumber("12L");
					// 实验数据
					sampleRecord.setExperimentalData(Double.valueOf(val.toString()));
					String sampleNum = ei.getCellValue(ei.getRow(i), 0).toString();
					sampleRecord.setSampleNumber(Integer.valueOf(sampleNum.substring(0, sampleNum.indexOf("."))));
					if (k.toString().contains("拉力")) {
						sampleRecord.setType("拉力");
						sampleRecord.setMachineType("DC-L");
						sampleRecord.setMachineNumber("DC-L11");
					} else if (k.toString().contains("扭转")) {
						sampleRecord.setType("扭转");
						sampleRecord.setMachineType("DC-T");
						sampleRecord.setMachineNumber("DC-T11");
					} else if (k.toString().contains("弯曲")) {
						sampleRecord.setType("弯曲");
						sampleRecord.setMachineType("DC-T");
						sampleRecord.setMachineNumber("DC-T11");
					}
					System.out.println(sampleRecord);
//					System.out.print("\n");
					list.add(sampleRecord);
				}
			}
		}
		System.out.println(list);
		System.out.println(list.size());
		return list;
	}

	/**
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<WireAttributesEn123852002> importWireAttributesEn123852002(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 1);
		List<WireAttributesEn123852002> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			WireAttributesEn123852002 wireAttributes = new WireAttributesEn123852002();
			String structure = ei.getCellValue(row, 0).toString();
			Double minimumBreakingForce = Double.valueOf(ei.getCellValue(row, 1).toString());
			Double fillingFactor = Double.valueOf(ei.getCellValue(row, 2).toString());
			wireAttributes.setStructure(structure);
			wireAttributes.setMinimumBreakingForce(minimumBreakingForce);
			wireAttributes.setFillingFactor(fillingFactor);
			wireAttributes.setState("000");
			list.add(wireAttributes);
			System.out.println(wireAttributes);
			System.out.print("\n");
		}
		System.out.println(list.size());
		return list;
	}
	/**
	 * 
	 * @param path
	 * @return
	 * @throws InvalidFormatException
	 * @throws IOException
	 */
	public static List<WireAttributesApi9a2011> importWireAttributesApi9A(String path)
			throws InvalidFormatException, IOException {
		String[] split = path.split("/");
		System.out.println(split[split.length - 1]);
		String filename = split[split.length - 1];
		String name = filename.substring(0, filename.indexOf("."));
		System.out.println(name);
		ImportExcel ei = new ImportExcel(path, 0, 1);
		List<WireAttributesApi9a2011> list = new ArrayList<>();
		String sheetName = ei.getSheet().getSheetName();
		System.out.println(sheetName);
		for (int i = 1; i <= ei.getLastDataRowNum(); i++) {
			Row row = ei.getRow(i);
			WireAttributesApi9a2011 wireAttributes = new WireAttributesApi9a2011();
			String structure = ei.getCellValue(row, 0).toString();
			Double minimumBreakingForce = Double.valueOf(ei.getCellValue(row, 1).toString());
			Double fillingFactor = Double.valueOf(ei.getCellValue(row, 2).toString());
			wireAttributes.setStructure(structure);
			wireAttributes.setMinimumBreakingForce(minimumBreakingForce);
			wireAttributes.setFillingFactor(fillingFactor);
			wireAttributes.setState("000");
			list.add(wireAttributes);
			System.out.println(wireAttributes);
			System.out.print("\n");
		}
		System.out.println(list.size());
		return list;
	}

	public static void main(String[] args) throws InvalidFormatException, IOException {

//		String path="/Users/admin/Desktop/GBT20118-2006.xls";
//		String path="/Users/admin/Desktop/GBT 20118-2017.xlsx";
//		String path="/Users/admin/Desktop/J&L 10302-2018.xls";
//		String path="/Users/admin/Desktop/GBT 20067-2017.xlsx";
//		String path="/Users/admin/Desktop/GBT20118-2006.xls";
//		String path="/Users/admin/Desktop/YBT5359-2010.xls";
//		String path="/Users/admin/Desktop/GB8918-2006.xls";
//		String path="/Users/admin/Desktop/GBT200672017.xlsx";
//		String path="/Users/admin/Desktop/gb89192006.xlsx";
//		String path="/Users/admin/Desktop/Ybt53592010.xlsx";
//		String path="/Users/admin/Desktop/批次号/批次号029.xlsx";
//		String path="/Users/admin/Desktop/批次号/批次号119.xlsx";

//		importIntoSimpleRecord(path);
		String path = "/Users/admin/Desktop/EN12385-4-2002.xls";
		importDiameterDeviation(path);
//		importFromValue(path);
//		importBending(path);
//		importWireAttributesGbt89182006(path);
//		importWireAttributesGbt891820061(path);
//		importTensileStrength(path);
//		importDiameterDeviation(path);
//		importWireAttributesApi9A(path);
//		importReverse(path);
//		importBending(path);
//		importFromValue(path);
//		importZincLayerWeight(path);
//		importWireAttributesYbt53592010(path);
//		List<SteelTensileStrength> list = importSteelTensileStrength(path);
//		importReverse2(path);
//		importWireAttributesGbt200672017(path);
//		List<WireAttributesGbt201182017> list = importWireAttributesGbt2011820171(path);
//		List<ReverseBending> list1 = importReverse(path);
//		System.out.println(list);
//		File file=new File(path);
//		List<FromValue> fromValueList = importFromValue(file);
//		System.out.println(list);
//		System.out.println(list.size());

	}
}
