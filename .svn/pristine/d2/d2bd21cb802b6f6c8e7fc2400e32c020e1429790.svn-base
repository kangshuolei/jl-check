package com.hbsi.export.service.impl;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;
import org.springframework.util.ResourceUtils;

import com.hbsi.common.utils.word.MSWordTool;
import com.hbsi.dao.BreakConclusionMapper;
import com.hbsi.dao.DiameterDeviationMapper;
import com.hbsi.dao.ReverseBendingMapper;
import com.hbsi.dao.StrengthDeviationMapper;
import com.hbsi.dao.UserMapper;
import com.hbsi.dao.VersonNumberMapper;
import com.hbsi.dao.WireDataMapper;
import com.hbsi.dao.WireRopeMapper;
import com.hbsi.dao.WireStandardMapper;
import com.hbsi.domain.BreakConclusion;
import com.hbsi.domain.DiameterDeviation;
import com.hbsi.domain.ExportDomain;
import com.hbsi.domain.ReverseBending;
import com.hbsi.domain.StrengthDeviation;
import com.hbsi.domain.User;
import com.hbsi.domain.VersonNumber;
import com.hbsi.domain.WireData;
import com.hbsi.domain.WireRope;
import com.hbsi.domain.WireStandard;
import com.hbsi.entity.WireRopeData;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.export.service.ReportExportService;
import com.hbsi.response.Response;
import com.hbsi.util.Arith;

@Service
public class ReportExportServiceImpl implements ReportExportService {

	/**
	 * author:shazhou
	 */
	@Autowired
	private VersonNumberMapper versonNumberMapper;
	@Autowired
	private WireRopeMapper wireRopeMapper;
	@Autowired
	private WireDataMapper wireDataMapper;
	@Autowired
	private DiameterDeviationMapper DDMapper;
	@Autowired
	private BreakConclusionMapper breakConclusionMapper;
	@Autowired
	private ReverseBendingMapper RBMapper;
	@Autowired
	private StrengthDeviationMapper sdMapper;
	@Autowired
	private WireStandardMapper wsMapper;
	@Autowired
	private UserMapper userMapper;

	Logger logger = LoggerFactory.getLogger(this.getClass());

	public Response<?> ExportReport(String entrustNum, HttpServletResponse response) throws IOException {
		File file = File.createTempFile("resultReport", ".docx");
		File file2 = ResourceUtils.getFile(file.getAbsolutePath());
		file.deleteOnExit();
		// 在D盘固定路径下生成文件夹存储报告
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
		String month = sdf.format(new Date());
		String basePath = "D:" + File.separator + "钢丝绳报告" + File.separator + entrustNum;
		File file3 = new File(basePath);
		if (!file3.exists()) {
			file3.mkdirs();
			System.out.println(file3.getAbsolutePath());
		}
		String filePath = basePath + File.separator + "综合报告.docx";

		try (FileOutputStream fos = new FileOutputStream(file);
				InputStream stream = getClass().getClassLoader().getResourceAsStream("templates/steelWireReport.docx");
				FileOutputStream output = new FileOutputStream(filePath);
				FileInputStream is = new FileInputStream(file2);) {
			MSWordTool changer = new MSWordTool();
			changer.setTemplate(stream);
			Map<String, Object> map = new HashMap<String, Object>();
			Map<String, Object> table1 = new HashMap<>();
			Map<String, Object> table2 = new HashMap<>();
			Map<String, Object> table3 = new HashMap<>();
			table1 = HeadOfReport(entrustNum);
			table2 = BreakStockData(entrustNum);
			// 将table1和table2数据放到一个map中，开始调用替换模板的算法
			table3.putAll(table1);
			table3.putAll(table2);
			map.put("table", table3);
			changer.replaceTemplete(map, fos);
			int len = 0;
			byte[] bt = new byte[1024];
			while ((len = is.read(bt)) != -1) {
				output.write(bt, 0, len);
			}
		} catch (Exception e1) {
			logger.error("导出word报告失败，原因是:{}", e1);
			return new Response<>("000019120", "报告生成失败", null);
		}
		return new Response<>("200", "ok", null);

	}

	/**
	 * 生成Json数据传递给前端
	 */
	public Response<?> ExportReportJson(String entrustNum) throws IOException {
		if (ObjectUtils.isEmpty(entrustNum)) {
			return new Response<>("000017110", "输入的委托单号为空", null);
		} else {
			Map<String, Object> map = new HashMap<String, Object>();
			try {
				Map<String, Object> table1 = new HashMap<>();
				Map<String, Object> table2 = new HashMap<>();
				List<BreakConclusion> breakConclusionList = new ArrayList<>();
				table1 = HeadOfReportForJason(entrustNum);
				// 将list按照实验钢丝类别重新排个序
				List<BreakConclusion> dataList = breakConclusionMapper.selectByEntrustNum(entrustNum);
				List<Double> breakConclusionSequence = getBreakConclusionSequence(entrustNum);
				for (int i = 0; i < breakConclusionSequence.size(); i++) {
					for (int j = 0; j < dataList.size(); j++) {
						if (dataList.get(j).getnDiamete().equals(breakConclusionSequence.get(i))) {
							breakConclusionList.add(dataList.get(j));
						}
					}
				}
				// 将table1和table2数据放到一个map中，开始调用替换模板的算法
				table2.putAll(table1);
				table2.put("list", breakConclusionList);
				map.put("table", table2);
			} catch (Exception e) {
				logger.error("report拆股实验信息获得失败，原因是：{}", e);
				return new Response<>("000019120", "报告生成失败", null);
			}
			return new Response<>(map);
		}
	}

	/**
	 * 从wireRope数据库中可以读取到的头文件信息和综合判定 该方法用于生成word表
	 * 
	 * @param entrustNum
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> HeadOfReport(String entrustNum) throws Exception {
		WireRope wireRope = wireRopeMapper.selectByEnsNum(entrustNum);
//		VersonNumber versonNumber = versonNumberMapper.selectByReportCategory("钢丝绳检测报告");
		Map<String, Object> table = new HashMap<String, Object>();
		table.put("bgbh", wireRope.getReportNumber());
		table.put("bgrq", wireRope.getReportDate());
		// 根据标准号查询标准的汉语名称
		WireStandard wireStandard = wsMapper.selectWireStandByStandNum(wireRope.getJudgeStandard());
		table.put("cpmc", wireStandard.getProName().toString());
		table.put("ypzt", "合格".toString());
		table.put("cpbz", wireRope.getJudgeStandard());
		table.put("jcbz", wireRope.getTestStandard());
		table.put("wth", wireRope.getEnstrustmentNumber());
		table.put("gg", wireRope.getSpecification());
		table.put("jg", wireRope.getStructure());
		table.put("cpbh", wireRope.getProducerNumber());
		table.put("qdjb", wireRope.getStrengthLevel());
		table.put("bmzt", wireRope.getSurfaceState());
		table.put("nf", wireRope.getTwistingMethod());
		table.put("wd", wireRope.getTemperature().toUpperCase());
		table.put("jlh", wireRope.getRecorderNumber());
		if (ObjectUtils.isEmpty(wireRope.getMemo())) {
			table.put("memo", "——");
		} else {
			table.put("memo", wireRope.getMemo());
		}
		if (wireRope.getMinBreakTensile() >= 100) {
			table.put("leastbreak", String.format("%.0f", wireRope.getMinBreakTensile()));
			table.put("testbreak", String.format("%.0f", wireRope.getMeasureBreakTensile()));
		} else {
			// 如果为空，画个横岗
			if (ObjectUtils.isEmpty(wireRope.getMinBreakTensile())) {
				table.put("leastbreak", "——");
			} else {
				table.put("leastbreak", wireRope.getMinBreakTensile().toString());
			}
			if (ObjectUtils.isEmpty(wireRope.getMeasureBreakTensile())) {
				table.put("testbreak", "——");
			} else {
				table.put("testbreak", wireRope.getMeasureBreakTensile());
			}
		}
		table.put("evaluation", wireRope.getEvaluation());
		if (wireRope.getJudgeStandard().equals("YB/T 5359-2010")
				|| wireRope.getJudgeStandard().equals("YB/T 5295-2010")) {
			table.put("pdorqd", "破断拉力标准值(kN)");
			table.put("scpdorqd", "破断拉力实测值(kN)");
		} else {
			table.put("pdorqd", "抗拉强度标准值(N/mm²)");
			table.put("scpdorqd", "抗拉强度实测值(N/mm²)");
		}
		return table;
	}

	/**
	 * 从wireRope数据库中可以读取到的头文件信息和综合判定 用于前端展示，生成Json数据时调用
	 * 
	 * @param entrustNum
	 * @return
	 * @throws Exception
	 */
	public Map<String, Object> HeadOfReportForJason(String entrustNum) throws Exception {
		WireRope wireRope = wireRopeMapper.selectByEnsNum(entrustNum);
		// 将合格存进wireRope中
		wireRope.setSampleState("合格");
		wireRopeMapper.updateByPrimaryKey(wireRope);
		Map<String, Object> table = new HashMap<String, Object>();
		wireRope.setEnstrustmentNumber(wireRope.getEnstrustmentNumber().toUpperCase());
		System.out.println(wireRope);
		WireStandard wireStandard = wsMapper.selectWireStandByStandNum(wireRope.getJudgeStandard());
		if (ObjectUtils.isEmpty(wireRope.getMemo())) {
			wireRope.setMemo("——");
		}
		table.put("wireRope", wireRope);
		if (wireRope.getJudgeStandard().equals("YB/T 5359-2010")
				|| wireRope.getJudgeStandard().equals("YB/T 5295-2010")) {
			table.put("pdorqd", "破断拉力标准值(kN)");
		} else {
			table.put("pdorqd", "抗拉强度标准值(N/mm²)");
		}
		// 综合报告的版本号
		VersonNumber versonNumber = versonNumberMapper.selectByReportCategory("钢丝绳检测报告");
		table.put("versonNumber", versonNumber.getVersonNumber());
		table.put("proName", wireStandard.getProName());
		return table;
	}

	/**
	 * 冲拆股实验结论表中调用拆股实验结论数据 该方法用于word报告
	 */
	public Map<String, Object> BreakStockData(String entrustNum) {
		// table存储数据用于生成word报告
		Map<String, Object> table = new HashMap<>();
		List<BreakConclusion> dataList = breakConclusionMapper.selectByEntrustNum(entrustNum);
		// 查询得到breakConclusion的展示顺序
		List<Double> sequenceList = getBreakConclusionSequence(entrustNum);
		if (sequenceList.size() != dataList.size()) {
			logger.error("查询得到的breakConclusion个数跟实验钢丝类别中的公称直径个数不一样");
			throw new BaseException(ExceptionEnum.BREAK_CONCLUSION_SELECT_FAFILED);
		} else {
			int i;
			for (i = 0; i < sequenceList.size(); i++) {
				for (int k = 0; k < dataList.size(); k++) {
					// 寻找dataList中跟当前顺序列表中的公称直径一样的
					if (dataList.get(k).getnDiamete().equals(sequenceList.get(i))) {
						// 得到该公称直径的数据，开始展示
						BreakConclusion bc = dataList.get(k);
						// 公称直径
						table.put("ppp" + i + i + i, String.format("%.2f", bc.getnDiamete()));
						// 钢丝根数
						table.put("bbb" + i + i + i, bc.getSteelNumber().toString());
						// 公称强度
						table.put("ccc" + i + i + i, bc.getStandardStrength().toString());
						// 直径
						table.put("ddd" + i + i + i, bc.getStandardDiameter());
						table.put("eee" + i + i + i, bc.getMeasureDiameter());
						// 强度
						table.put("fff" + i + i + i, bc.getStandardTensile());
						table.put("ggg" + i + i + i, bc.getMeasureTensile());
						// 打结率
						table.put("hhh" + i + i + i, bc.getStandardKnot());
						table.put("mmm" + i + i + i, bc.getMeasureKnot());
						// 扭转
						table.put("qqq" + i + i + i, bc.getStandardReverse());
						table.put("rrr" + i + i + i, bc.getMeasureReverse());
						// 弯曲
						table.put("sss" + i + i + i, bc.getStandardBending());
						table.put("kkk" + i + i + i, bc.getMeasureBending());
						// 锌层重量
						table.put("yyy" + i + i + i, bc.getStandardZinc());
						table.put("zzz" + i + i + i, bc.getMeasureZinc());
					}
				}
			}
			for (int j = i; j < 10; j++) {
				// 公称直径
				table.put("ppp" + j + j + j, "——".toString());
//			钢丝根数
				table.put("bbb" + j + j + j, "——".toString());
//			公称强度
				table.put("ccc" + j + j + j, "——".toString());
//			直径
				table.put("ddd" + j + j + j, "——".toString());
				table.put("eee" + j + j + j, "——".toString());
//			强度
				table.put("fff" + j + j + j, "——".toString());
				table.put("ggg" + j + j + j, "——".toString());
				// 打结率
				table.put("hhh" + j + j + j, "——".toString());
				table.put("mmm" + j + j + j, "——".toString());
//			扭转
				table.put("qqq" + j + j + j, "——".toString());
				table.put("rrr" + j + j + j, "——".toString());
//			弯曲
				table.put("sss" + j + j + j, "——".toString());
				table.put("kkk" + j + j + j, "——".toString());
				// 锌层重量
				table.put("yyy" + j + j + j, "——".toString());
				table.put("zzz" + j + j + j, "——".toString());
			}

		}
		return table;
	}

	/**
	 * 该方法是拆股实验结论保存到数据库中
	 * 
	 * @return
	 */
	public Response<?> saveBreakConclusionData(String entrustNum) {
		try {
			// 若要重新判定，先根据委托单号删除数据库breakConclusion表中的相关信息
			breakConclusionMapper.deleteByEntrustNum(entrustNum);
			List<WireData> dataList = wireDataMapper.selectByEnNum(entrustNum);
			WireRope wireRope = wireRopeMapper.selectByEnsNum(entrustNum);
			Map<Double, List<WireData>> dataMap = new HashMap<>();
			/**
			 * 获取试验钢丝类别公称抗拉强度
			 */
			Integer strengthLevel = Integer.parseInt(wireRope.getStrengthLevel());// 抗拉强度
			Map<Double, Integer> strengthMap = new HashMap<>();
			String cl = wireRope.getTrialClass();
			if (cl == null || "".equals(cl)) {
				WireRope wr = wireRopeMapper.selectByEnsNum(wireRope.getEnstrustmentNumber());
				cl = wr.getTrialClass();
			}
			List<String[]> nlist = new ArrayList<>();
			if (cl != null && !"".equals(cl)) {

				String[] str = cl.split(",");
				for (int i = 0; i < str.length; i++) {
					String s = str[i];
					String[] ss = s.split("\\*");
					String[] sa = ss[1].split("/");
					ss[1] = sa[0];
					nlist.add(ss);
				}
			} else {
				return null;
			}
			for (int i = 0; i < nlist.size(); i++) {
				String[] nd = nlist.get(i);
				double ndiamate = Double.parseDouble(nd[0]);
				if (nd.length > 2) {
					Integer strength = Integer.parseInt(nd[2]);
					strengthMap.put(ndiamate, strength);
				} else {
					strengthMap.put(ndiamate, strengthLevel);
				}
			}

			/**
			 * 将dataList数组数据按照ndiameter进行分组
			 */
			List<WireData> l = null;
			for (WireData wireData : dataList) {
				if (!dataMap.containsKey(wireData.getnDiamete1())) {
					l = new ArrayList<WireData>();
					l.add(wireData);
				} else {
					l = dataMap.get(wireData.getnDiamete1());
					l.add(wireData);
				}
				dataMap.put(wireData.getnDiamete1(), l);
			}
			Iterator<Double> it = dataMap.keySet().iterator();
			double ndiameter = 0;
			// 用于表示第i列拆股实验数据
			int i = -1;
			while (it.hasNext()) {
				// 将拆股实验结论信息保存数据库中
				BreakConclusion breakConclusion = new BreakConclusion();
				breakConclusion.setState("000");
				breakConclusion.setEntrustmentNumber(entrustNum);
				i = i + 1;
				ndiameter = it.next();
				/**
				 * 对应表中的的公称直径，钢丝根数，公称抗拉强度，直径标准值，直径实测值
				 */
				breakConclusion.setnDiamete(ndiameter);
				// 取出该公称直径对应的wireData列表
				List<WireData> dlist = dataMap.get(ndiameter);
				Integer j = 0;
				for (WireData w : dlist) {
					j++;
				}
				// 钢丝根数
				breakConclusion.setSteelNumber(j);
				// 公称强度
				Integer wireStrengthLevel = strengthMap.get(ndiameter);
				if (wireStrengthLevel == null || wireStrengthLevel == 0) {
					wireStrengthLevel = strengthLevel;
				}
				breakConclusion.setStandardStrength(wireStrengthLevel);
				// 打结率(先判定是否有打结率，根据wireData数据中是否有wireData值来判定)
				if (!ObjectUtils.isEmpty(dlist.get(0).getKnotRate())) {
					Map<String, Object> knotRateTable = new HashMap<>();
					knotRateTable = knotJudge(dlist);
					breakConclusion.setStandardKnot(knotRateTable.get("standKnotRate").toString());
					breakConclusion.setMeasureKnot(knotRateTable.get("measKnotRate").toString());
				} else {
					breakConclusion.setStandardKnot("——".toString());
					breakConclusion.setMeasureKnot("——".toString());
				}
				// 锌层重量
				breakConclusion.setStandardZinc("——");
				breakConclusion.setMeasureZinc("——");
				// 直径判断(如果实测直径为空，说明不用判定直径这一项)
				if (!ObjectUtils.isEmpty(dlist.get(0).getDiamete())) {
					Map<String, Object> diaTable = new HashMap<>();
					diaTable = diameterJudge(ndiameter, wireRope, dlist);
					breakConclusion.setStandardDiameter(diaTable.get("standDiameter").toString());
					breakConclusion.setMeasureDiameter(diaTable.get("measDiameter").toString());
				} else {
					breakConclusion.setStandardDiameter("——".toString());
					breakConclusion.setMeasureDiameter("——".toString());
				}
				// 判断扭转,扭转实测值(查看扭转实测值有没有，如果有说明进行了这一项判定)
				if (!ObjectUtils.isEmpty(dlist.get(0).getTurnTimes())) {
					Map<String, Object> revTable = new HashMap<>();
					revTable = reverseJudge(ndiameter, wireRope, dlist, strengthMap);
					breakConclusion.setStandardReverse(revTable.get("standReverse").toString());
					breakConclusion.setMeasureReverse(revTable.get("measReverse").toString());
				} else {
					breakConclusion.setStandardReverse("——".toString());
					breakConclusion.setMeasureReverse("——".toString());
				}
				// 弯曲标准值，弯曲实测值
				if (!ObjectUtils.isEmpty(dlist.get(0).getWindingTimes())) {
					Map<String, Object> bendTable = new HashMap<>();
					bendTable = bendingJudge(ndiameter, wireRope, dlist, strengthMap);
					breakConclusion.setStandardBending(bendTable.get("standBending").toString());
					breakConclusion.setMeasureBending(bendTable.get("measBending").toString());
				} else {
					breakConclusion.setStandardBending("——".toString());
					breakConclusion.setMeasureBending("——".toString());
				}
				// 破断拉力或者抗拉强度判定（按理说应该至少有一个的，不用判定是否存在）
				Map<String, Object> breakOrStrenTable = new HashMap<>();
				breakOrStrenTable = breakOrStrengthJudge(ndiameter, wireRope, dlist, strengthMap);
				if (wireRope.getJudgeStandard().equals("YB/T 5359-2010")
						|| wireRope.getJudgeStandard().equals("YB/T 5295-2010")) {
					breakConclusion.setStandardTensile(breakOrStrenTable.get("standBreakTensile").toString());
					breakConclusion.setMeasureTensile(breakOrStrenTable.get("measBreakTensile").toString());
				} else {
					breakConclusion.setStandardTensile(breakOrStrenTable.get("standTensileStrength").toString());
					breakConclusion.setMeasureTensile(breakOrStrenTable.get("measTensileStrength").toString());
				}
				breakConclusionMapper.insert(breakConclusion);
			}
		} catch (Exception e) {
			logger.error("saveBreakConclusionData方法中，保存实验结论失败了，原因是:{}", e);
			return new Response<>("000019140", "综合报告生成失败", null);
		}
		return new Response<>("200", "ok", null);
	}

	/**
	 * 直径判断：YB/T 5359-2010时，直径标准和直径实测值都为空
	 * 
	 * @param ndiameter
	 * @param wireRope
	 * @param dlist
	 * @return
	 */
	private Map<String, Object> diameterJudge(double ndiameter, WireRope wireRope, List<WireData> dlist) {
		/**
		 * 直径判断
		 */
		Map<String, Object> map = new HashMap<>();
		if (wireRope.getJudgeStandard().equals("YB/T 5295-2010")) {
			map.put("standDiameter", "——");
			map.put("measDiameter", "——");
			return map;
		}
		WireRopeData wireRopeData = new WireRopeData();
		wireRopeData.setNdiamete(ndiameter);
		wireRopeData.setStandardNum(wireRope.getJudgeStandard());
		String surface1 = wireRope.getSurfaceState();
		String surface = "";// 用于扭转弯曲和判定
		if (surface1.contains("A类") || surface1.contains("A级")) {
			surface = "A";
		} else {
			if (wireRope.getJudgeStandard().equals("EN 12385-4:2002 + A1：2008")
					|| wireRope.getJudgeStandard().equals("ISO 2408-2004")
					|| wireRope.getJudgeStandard().equals("API 9A 2011")) {
				surface = "U,B";
			} else {
				surface = "U,B,AB";
			}
		}
		wireRopeData.setSurface(surface);
		if (wireRope.getJudgeStandard().equals("EN 12385-4:2002 + A1：2008")) {
			if (wireRope.getStructure().contains("V")) {
				wireRopeData.setUsage("Z");
			} else {
				wireRopeData.setUsage("Y");
			}
		}
		DiameterDeviation dd = DDMapper.selectDiaByType(wireRopeData);
		if (wireRope.getJudgeStandard().equals("YB/T 5359-2010")
				|| wireRope.getJudgeStandard().equals("YB/T 4542-2016")) {
			/**
			 * 标准名为：YB/T 5359-2010时，直径标准和直径实测值都为空
			 */
			map.put("standDiameter", "——");
			map.put("measDiameter", "——");
		} else {
			/**
			 * 求直径标准值的范围
			 */
			String minDia = new String();
			String maxDia = new String();
			if (wireRope.getJudgeStandard().equals("EN 12385-4:2002 + A1：2008")
					|| wireRope.getJudgeStandard().equals("ISO 2408-2004")
					|| wireRope.getJudgeStandard().equals("API 9A 2011")) {
				minDia = String.format("%.3f", Arith.sub(ndiameter, dd.getValue()));
				maxDia = String.format("%.3f", Arith.add(ndiameter, dd.getValue()));
			} else {
				minDia = String.format("%.2f", Arith.sub(ndiameter, dd.getValue()));
				maxDia = String.format("%.2f", Arith.add(ndiameter, dd.getValue()));
			}
			StringBuilder standDia = new StringBuilder();
			standDia.append(minDia).append("～").append(maxDia);
			map.put("standDiameter", standDia.toString());
			/**
			 *  求实测直径的范围
			 */
			double minMeasDia = dlist.get(0).getDiamete1();
			double maxMeasDia = dlist.get(0).getDiamete1();
			for (WireData w : dlist) {
				double measDia = w.getDiamete1();
				if (minMeasDia > measDia) {
					minMeasDia = measDia;
				}
				if (maxMeasDia < measDia) {
					maxMeasDia = measDia;
				}
				StringBuilder measDiameter = new StringBuilder();
				measDiameter.append(String.format("%.2f", minMeasDia)).append("～")
						.append(String.format("%.2f", maxMeasDia));
				map.put("measDiameter", measDiameter.toString());
			}
		}
		return map;
	}

	/**
	 * 扭转的判定
	 * 
	 * @param wireRope
	 * @param dlist
	 * @return
	 */
	private Map<String, Object> reverseJudge(double ndiameter, WireRope wireRope, List<WireData> dlist,
			Map<Double, Integer> strengthMap) {
		// 该map存储该方法的返回结果。
		Map<String, Object> map = new HashMap<>();
		// YB/T 5295-2010该标准不用判定，直接给一个空值就行
		if (wireRope.getJudgeStandard().equals("YB/T 5295-2010")) {
			map.put("standReverse", "——");
			map.put("measReverse", "——");
			return map;
		}
		// rb是用于reverseBendingMapper中查找对应的扭转值
		ReverseBending rb = new ReverseBending();
		rb.setStandardNum(wireRope.getJudgeStandard());
		/**
		 * 注：这里现在只是设定了20067标准，如果出现了不存在数据库中的公称强度值，就应该在该处添加类似的判定，后人需要谨记
		 */
		Integer wireStrengthLevel = strengthMap.get(ndiameter);
		if (wireStrengthLevel == null || wireStrengthLevel == 0) {
			wireStrengthLevel = Integer.parseInt(wireRope.getStrengthLevel());
		}
		if (wireRope.getJudgeStandard().equals("GB/T 20067-2017")) {
			if (wireStrengthLevel <= 1570) {
				rb.setRatedStrength(1570 + "");
			} else if (wireStrengthLevel <= 1770) {
				rb.setRatedStrength(1770 + "");
			} else if (wireStrengthLevel <= 1960) {
				rb.setRatedStrength(1960 + "");
			} else if (wireStrengthLevel <= 2160) {
				rb.setRatedStrength(2160 + "");
			}
		} else {
			rb.setRatedStrength(wireStrengthLevel.toString());
		}
		String surface1 = wireRope.getSurfaceState();
		String surface = "";// 用于扭转弯曲和判定
		if (surface1.contains("光面")) {
			surface = "UB";
		} else if (surface1.contains("A类") || surface1.contains("A级")) {
			surface = "A";
		} else if (surface1.contains("AB类") || surface1.contains("AB级")) {
			surface = "AB";
		} else if (surface1.contains("B类") || surface1.contains("B级")) {
			surface = "UB";
		} else if (surface1.contains("光面和B类") || surface1.contains("光面和B级")) {
			surface = "UB";
		}
		System.out.println(surface + "--------------------------------");
		// 标准YB/T 4542-2016 中，不流行判定表面状态
		if (wireRope.getJudgeStandard().equals("YB/T 4542-2016")) {
			rb.setSurfaceState("");
		} else {
			rb.setSurfaceState(surface);
		}
		rb.setfDiamete(ndiameter);
		List<ReverseBending> rlist = RBMapper.selectRBDataByCon(rb);
		int[] d = new int[2];
		for (ReverseBending r : rlist) {
			if ("R".equals(r.getType())) {
				d[0] = r.getValueRob();
			} else if ("B".equals(r.getType())) {
				d[1] = r.getValueRob();
			} else if ("C".equals(r.getType())) {
				d[0] = r.getValueRob();
			} else if ("P".equals(r.getType())) {
				d[1] = r.getValueRob();
			}
		}
		StringBuilder standReverse = new StringBuilder();
		/**
		 * 标准GB/T 20067-2017中只有圆股钢丝，压实股钢丝扭转的判定，没有弯曲
		 */
		if (wireRope.getJudgeStandard().equals("GB/T 20067-2017")) {
			if (wireRope.getStructure().contains("K")) {
				standReverse.append("≥").append(d[1]);
			} else {
				standReverse.append("≥").append(d[0]);
			}
		} else if (wireRope.getJudgeStandard().equals("API 9A 2011")
				|| wireRope.getJudgeStandard().equals("ISO 2408-2004")) {
			// 根据是否为异形股，判定有区别
			String structure = wireRope.getStructure();
			/* 扭转次数判定 */
			String sourceStr = wireRope.getStructure();
			String[] sourceStrArray = sourceStr.split("×");
			if (structure.contains("V")) {
				if (sourceStrArray[0].equals("1")) {
					standReverse.append("≥").append(Math.round(d[0] * 0.75));
				} else {
					standReverse.append("≥").append(Math.round(d[0] - 1));
				}
			} else {
				/**
				 * 单捻钢丝绳
				 */
				if (sourceStrArray[0].equals("1")) {
					standReverse.append("≥").append(Math.round(d[0] * 0.75));
				} else {
					standReverse.append("≥").append(Math.round(d[0] * 0.85));
				}
			}
		} else if (wireRope.getJudgeStandard().equals("EN 12385-4:2002 + A1：2008")) {
			// 根据是否为异形股，判定有区别
			String structure = wireRope.getStructure();
			/* 扭转次数判定 */
			String sourceStr = wireRope.getStructure();
			String[] sourceStrArray = sourceStr.split("×");
			if (sourceStrArray[0].equals("1")) {
				standReverse.append("≥").append(Math.round(d[0] * 0.75));
			} else {
				if (structure.contains("V")) {
					standReverse.append("≥").append(Math.round(d[0]));
				} else {
					standReverse.append("≥").append(Math.round(d[0] * 0.85));
				}
			}
		} else if (wireRope.getJudgeStandard().equals("GB/T 20118-2017")
				|| wireRope.getJudgeStandard().equals("GB/T 8918-2006")) {
			// 根据是否为异形股，判定有区别
			String structure = wireRope.getStructure();
			if (structure.contains("V")) {
				standReverse.append("≥").append(d[0] - 1);
			} else {
				standReverse.append("≥").append(d[0]);
			}
		} else {
			standReverse.append("≥").append(d[0]);
		}
		// 扭转标准值
		map.put("standReverse", standReverse.toString());
		/**
		 * 扭转实测值，弯曲实测值,抗拉强度实测值
		 */
		Integer minMeasReverse = dlist.get(0).getTurnTimes();
		Integer maxMeasReverse = dlist.get(0).getTurnTimes();
		for (WireData w : dlist) {
			if (minMeasReverse > w.getTurnTimes()) {
				minMeasReverse = w.getTurnTimes();
			}
			if (maxMeasReverse < w.getTurnTimes()) {
				maxMeasReverse = w.getTurnTimes();
			}
		}
		StringBuilder measReverse = new StringBuilder();
		measReverse.append(minMeasReverse).append("～").append(maxMeasReverse);
		map.put("measReverse", measReverse.toString());
		return map;
	}

	/**
	 * 弯曲判定：若是标准GB/T 20067-2017中只有圆股钢丝，压实股钢丝扭转的判定，没有弯曲
	 * 
	 * @param ndiameter
	 * @param wireRope
	 * @param dlist
	 * @return
	 */
	private Map<String, Object> bendingJudge(double ndiameter, WireRope wireRope, List<WireData> dlist,
			Map<Double, Integer> strengthMap) {
		// 用于存储该方法的返回值
		Map<String, Object> map = new HashMap<String, Object>();
		/**
		 * 标准GB/T 20067-2017中只有圆股钢丝，压实股钢丝扭转的判定，没有弯曲
		 */
		if (wireRope.getJudgeStandard().equals("YB/T 5295-2010")
				|| wireRope.getJudgeStandard().equals("GB/T 20067-2017")) {
			map.put("standBending", "——");
			map.put("measBending", "——");
		} else {
			// rb的定义适用于查询扭转弯曲值
			ReverseBending rb = new ReverseBending();
			rb.setStandardNum(wireRope.getJudgeStandard());
			Integer wireStrengthLevel = strengthMap.get(ndiameter);
			// 如果实验钢丝类别中没有指定公称强度，那么就用表头中的
			if (wireStrengthLevel == null || wireStrengthLevel == 0) {
				wireStrengthLevel = Integer.parseInt(wireRope.getStrengthLevel());
			}
			rb.setRatedStrength(wireStrengthLevel.toString());
			String surface1 = wireRope.getSurfaceState();
			// 用于扭转弯曲和判定
			String surface = "";
			if (surface1.contains("光面")) {
				surface = "UB";
			} else if (surface1.contains("A类") || surface1.contains("A级")) {
				surface = "A";
			} else if (surface1.contains("AB类") || surface1.contains("AB级")) {
				surface = "AB";
			} else if (surface1.contains("B类") || surface1.contains("B级")) {
				surface = "UB";
			} else if (surface1.contains("光面和B类") || surface1.contains("光面和B级")) {
				surface = "UB";
			}
			// 标准YB/T 4542-2016 中，不流行判定表面状态
			if (wireRope.getJudgeStandard().equals("YB/T 4542-2016")) {
				rb.setSurfaceState("");
			} else {
				rb.setSurfaceState(surface);
			}
			rb.setfDiamete(ndiameter);
			List<ReverseBending> rlist = RBMapper.selectRBDataByCon(rb);
			int[] d = new int[2];
			for (ReverseBending r : rlist) {
				if ("B".equals(r.getType())) {
					d[1] = r.getValueRob();
				}
			}
			StringBuilder standBending = new StringBuilder();
			if (wireRope.getJudgeStandard().equals("EN 12385-4:2002 + A1：2008")) {
				// 根据是否为异形股，判定有区别
				String structure = wireRope.getStructure();
				/* 扭转次数判定 */
				String sourceStr = wireRope.getStructure();
				String[] sourceStrArray = sourceStr.split("×");
				if (sourceStrArray[0].equals("1")) {
					standBending.append("≥").append(Math.round(d[1] * 0.75));
				} else {
					if (structure.contains("V")) {
						standBending.append("≥").append(Math.round(d[1]));
					} else {
						standBending.append("≥").append(Math.round(d[1] * 0.9));
					}
				}
			} else {
				standBending.append("≥").append(d[1]);
			}
			// 弯曲标准值
			map.put("standBending", standBending.toString());
			// 弯曲实测值
			Integer minMeasBending = dlist.get(0).getWindingTimes();
			Integer maxMeasBending = dlist.get(0).getWindingTimes();
			for (WireData w : dlist) {
				if (minMeasBending > w.getWindingTimes()) {
					minMeasBending = w.getWindingTimes();
				}
				if (maxMeasBending < w.getWindingTimes()) {
					maxMeasBending = w.getWindingTimes();
				}
			}
			StringBuilder measBending = new StringBuilder();
			measBending.append(minMeasBending).append("～").append(maxMeasBending);
			map.put("measBending", measBending.toString());
		}
		return map;
	}

	/**
	 * 破断拉力或者抗拉强度判定：YB/T 5359-2010中是破断拉力的判定，其他标准中是抗拉强度的判定
	 * 
	 * @param ndiameter
	 * @param wireRope
	 * @param dlist
	 * @return
	 */
	private Map<String, Object> breakOrStrengthJudge(double ndiameter, WireRope wireRope, List<WireData> dlist,
			Map<Double, Integer> strengthMap) {

		Map<String, Object> map = new HashMap<>();
		/**
		 * 该标准中没有抗拉强度，判断破断拉力 要求为：不低于平均值*0.92
		 */
		if (wireRope.getJudgeStandard().equals("YB/T 5295-2010")) {
			map.put("standBreakTensile", "——");
			map.put("measBreakTensile", "——");
			return map;
		} else if (wireRope.getJudgeStandard().equals("YB/T 5359-2010")) {
			// 获得该标准的破断拉力标准值
			double breakAll = 0;
			for (WireData wireData : dlist) {
				breakAll += wireData.getBreakTensile1();
			}
			double average = breakAll / dlist.size();// 平均值
			Double standBreakTens = Arith.mul(average, 0.92);
			String afterFormat = null;

			if (dlist.get(0).getMachineType().contains("DC-KL")) {
				afterFormat = String.format("%.2f", standBreakTens);
			} else {
				afterFormat = String.format("%.3f", standBreakTens);
			}
			StringBuilder standBreakTensile = new StringBuilder();
			standBreakTensile.append("≥").append(afterFormat);
			map.put("standBreakTensile", standBreakTensile.toString());
			// 获得该标准的破断拉力实测值区间
			double minBreakTens = dlist.get(0).getBreakTensile1();
			double maxBreakTens = dlist.get(0).getBreakTensile1();
			// 遍历dlist，获得实测值的最小，最大值
			for (WireData w : dlist) {
				if (minBreakTens > w.getBreakTensile1()) {
					minBreakTens = w.getBreakTensile1();
				}
				if (maxBreakTens < w.getBreakTensile1()) {
					maxBreakTens = w.getBreakTensile1();
				}
			}
			StringBuilder measBreakTensile = new StringBuilder();
			if (dlist.get(0).getMachineType().contains("DC-KL")) {
				measBreakTensile.append(String.format("%.2f", minBreakTens)).append("～")
						.append(String.format("%.2f", maxBreakTens));
				map.put("measBreakTensile", measBreakTensile.toString());
			} else {
				measBreakTensile.append(String.format("%.3f", minBreakTens)).append("～")
						.append(String.format("%.3f", maxBreakTens));
				map.put("measBreakTensile", measBreakTensile.toString());
			}
		} else if (wireRope.getJudgeStandard().equals("GB/T 8918-2006")) {
			/**
			 * 求得抗拉强度的标准值范围 要求：强度级别～强度级别+强度偏差（strength_deviation表中读取）
			 */
			WireRopeData wireRopeData = new WireRopeData();
			wireRopeData.setStandardNum(wireRope.getJudgeStandard());
			StrengthDeviation sd = null;
			wireRopeData.setNdiamete(ndiameter);
			sd = sdMapper.selectByDiamate(wireRopeData);
			Integer wireStrengthLevel = strengthMap.get(ndiameter);
			if (wireStrengthLevel == null || wireStrengthLevel == 0) {
				wireStrengthLevel = Integer.parseInt(wireRope.getStrengthLevel());
			}
			Integer maxStrength = (int) Math.round(wireStrengthLevel + sd.getValue());
			StringBuilder standTensileStrength = new StringBuilder();
			standTensileStrength.append(wireStrengthLevel).append("～").append(maxStrength);
			map.put("standTensileStrength", standTensileStrength.toString());
			/**
			 * 求抗拉强度的实测值范围：取最大值，最小值
			 */
			Integer maxTensileSrength = dlist.get(0).getTensileStrength();
			Integer minTensileStrength = dlist.get(0).getTensileStrength();
			for (WireData w : dlist) {
				if (minTensileStrength > w.getTensileStrength()) {
					minTensileStrength = w.getTensileStrength();
				}
				if (maxTensileSrength < w.getTensileStrength()) {
					maxTensileSrength = w.getTensileStrength();
				}
			}
			StringBuilder measTensileStrength = new StringBuilder();
			measTensileStrength.append(minTensileStrength).append("～").append(maxTensileSrength);
			map.put("measTensileStrength", measTensileStrength.toString());
		} else if (wireRope.getJudgeStandard().equals("YB/T 4542-2016")) {
			/**
			 * 求得抗拉强度的标准值范围 要求：强度级别～强度级别+强度偏差（strength_deviation表中读取）
			 */
			WireRopeData wireRopeData = new WireRopeData();
			wireRopeData.setStandardNum(wireRope.getJudgeStandard());
			StrengthDeviation sd = null;
			wireRopeData.setNdiamete(ndiameter);
			sd = sdMapper.selectByDiamate(wireRopeData);
			Integer wireStrengthLevel = strengthMap.get(ndiameter);
			if (wireStrengthLevel == null || wireStrengthLevel == 0) {
				wireStrengthLevel = Integer.parseInt(wireRope.getStrengthLevel());
			}
			Integer maxStrength = (int) Math.round(wireStrengthLevel + sd.getValue());
			StringBuilder standTensileStrength = new StringBuilder();
			standTensileStrength.append(wireStrengthLevel - 50).append("～").append(maxStrength);
			map.put("standTensileStrength", standTensileStrength.toString());
			/**
			 * 求抗拉强度的实测值范围：取最大值，最小值
			 */
			Integer maxTensileSrength = dlist.get(0).getTensileStrength();
			Integer minTensileStrength = dlist.get(0).getTensileStrength();
			for (WireData w : dlist) {
				if (minTensileStrength > w.getTensileStrength()) {
					minTensileStrength = w.getTensileStrength();
				}
				if (maxTensileSrength < w.getTensileStrength()) {
					maxTensileSrength = w.getTensileStrength();
				}
			}
			StringBuilder measTensileStrength = new StringBuilder();
			measTensileStrength.append(minTensileStrength).append("～").append(maxTensileSrength);
			map.put("measTensileStrength", measTensileStrength.toString());
		} else if (wireRope.getJudgeStandard().equals("ISO 2408-2004")
				|| wireRope.getJudgeStandard().equals("API 9A 2011")
				|| wireRope.getJudgeStandard().equals("GB/T 20118-2017")) {
			/**
			 * 求得抗拉强度的标准值范围 要求：>=zuixiao
			 */
			WireRopeData wireRopeData = new WireRopeData();
			wireRopeData.setStandardNum(wireRope.getJudgeStandard());
			StrengthDeviation sd = null;
			wireRopeData.setNdiamete(ndiameter);
			sd = sdMapper.selectByDiamate(wireRopeData);
			Integer wireStrengthLevel = strengthMap.get(ndiameter);
			if (wireStrengthLevel == null || wireStrengthLevel == 0) {
				wireStrengthLevel = Integer.parseInt(wireRope.getStrengthLevel());
			}
			String structure = wireRope.getStructure();
			if (structure.contains("V")) {
				StringBuilder standTensileStrength = new StringBuilder();
				Integer minStenStrength = (int) Arith.revision(wireStrengthLevel * 0.95);
				standTensileStrength.append("≥").append(minStenStrength);
				map.put("standTensileStrength", standTensileStrength.toString());
			} else {
				StringBuilder standTensileStrength = new StringBuilder();
				standTensileStrength.append("≥").append(wireStrengthLevel - 50);
				map.put("standTensileStrength", standTensileStrength.toString());
			}
			/**
			 * 求抗拉强度的实测值范围：取最大值，最小值
			 */
			Integer maxTensileSrength = dlist.get(0).getTensileStrength();
			Integer minTensileStrength = dlist.get(0).getTensileStrength();
			for (WireData w : dlist) {
				if (minTensileStrength > w.getTensileStrength()) {
					minTensileStrength = w.getTensileStrength();
				}
				if (maxTensileSrength < w.getTensileStrength()) {
					maxTensileSrength = w.getTensileStrength();
				}
			}
			StringBuilder measTensileStrength = new StringBuilder();
			measTensileStrength.append(minTensileStrength).append("～").append(maxTensileSrength);
			map.put("measTensileStrength", measTensileStrength.toString());
		} else {
			/**
			 * 在其他标准中，抗拉强度范围比较统一：≥强度级别-50
			 */
			Integer wireStrengthLevel = strengthMap.get(ndiameter);
			if (wireStrengthLevel == null || wireStrengthLevel == 0) {
				wireStrengthLevel = Integer.parseInt(wireRope.getStrengthLevel());
			}
			StringBuilder standTensileStrength = new StringBuilder();
			standTensileStrength.append("≥").append(wireStrengthLevel - 50);
			map.put("standTensileStrength", standTensileStrength.toString());
			/**
			 * 抗拉强度实测值
			 */
			Integer minTensileStrength = dlist.get(0).getTensileStrength();
			Integer maxTensileStrength = dlist.get(0).getTensileStrength();
			for (WireData w : dlist) {
				if (minTensileStrength > w.getTensileStrength()) {
					minTensileStrength = w.getTensileStrength();
				}
				if (maxTensileStrength < w.getTensileStrength()) {
					maxTensileStrength = w.getTensileStrength();
				}
			}
			StringBuilder measTensileStrength = new StringBuilder();
			measTensileStrength.append(minTensileStrength).append("～").append(maxTensileStrength);
			map.put("measTensileStrength", measTensileStrength.toString());
		}
		return map;
	}

	/**
	 * 打结拉力判定结果保存
	 * 
	 * @return
	 */
	public Map<String, Object> knotJudge(List<WireData> dlist) {
		Map<String, Object> map = new HashMap<String, Object>();
		// 打结率的标准值是大于等于50；
		map.put("standKnotRate", "≥50");
		// 计算打结率的范围
		double minMeasKnotRate = dlist.get(0).getKnotRate();
		double maxMeasKnotRate = dlist.get(0).getKnotRate();
		for (WireData w : dlist) {
			if (minMeasKnotRate > w.getKnotRate()) {
				minMeasKnotRate = w.getKnotRate();
			}
			if (maxMeasKnotRate < w.getKnotRate()) {
				maxMeasKnotRate = w.getKnotRate();
			}
		}
		map.put("measKnotRate", Math.round(minMeasKnotRate) + "～" + Math.round(maxMeasKnotRate));
		return map;

	}

	/**
	 * 根据传入的ExportDomain信息，更新数据库
	 */
	@Override
	public Response<?> updateConclusion(ExportDomain exportDomain) {
		// 接收WireRope，并更新
		WireRope w = exportDomain.getWireRope();
		wireRopeMapper.updateByPrimaryKeySelective(w);
		// 修改产品名称，保存到wireStand数据库表中
		String proName = exportDomain.getProName();
		WireStandard wireStandard = wsMapper.selectWireStandByStandNum(w.getJudgeStandard());
		wireStandard.setProName(proName);
		wsMapper.updateByPrimaryKey(wireStandard);
		// 接收拆股结论List数据，并更新
		List<BreakConclusion> list = exportDomain.getList();
		// 查询用户名的权限，看是否足以完成以下操作
		String userName = exportDomain.getUserName();
		User user = userMapper.selectByUsername(userName);
		String versonNumber = exportDomain.getVersonNumber();
		VersonNumber primaryKey = versonNumberMapper.selectByReportCategory("钢丝绳检测报告");
		// 检测传入的值是否跟存储的值相同
		if (!versonNumber.equals(primaryKey.getVersonNumber())) {
			if (user.getUserRank().equals("管理员")) {

				// 如果传入的版本号跟数据库中存储的不同，则更新数据库中信息
				primaryKey.setVersonNumber(versonNumber);
				versonNumberMapper.updateByPrimaryKey(primaryKey);
			} else {
				return new Response<>("000015000", "只有管理员才有资格修改版本号", null);
			}
		}
		// 拆股实验结论的修改
		for (BreakConclusion b : list) {
			BreakConclusion bc = breakConclusionMapper.selectByPrimaryKey(b.getId());
			// 判断实测值是否被更改，如果被更改，就判定是不是管理员（只有管理员有资格更改）
			if (!b.getMeasureBending().equals(bc.getMeasureBending())
					|| !b.getMeasureDiameter().equals(bc.getMeasureDiameter())
					|| !b.getMeasureReverse().equals(bc.getMeasureReverse())
					|| !b.getMeasureTensile().equals(bc.getMeasureTensile())
					|| !b.getMeasureKnot().equals(bc.getMeasureKnot())
					|| !b.getMeasureZinc().equals(bc.getMeasureZinc())) {
				if (user.getUserRank().contains("管理员")) {
					try {
						breakConclusionMapper.updateByPrimaryKey(b);
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				} else {
					return new Response<>("00001000", "普通用户只能修改标准值，不能改实测值", null);
				}
			} else {
				breakConclusionMapper.updateByPrimaryKey(b);
			}
		}

		return new Response<>("200", "ok", null);
	}

	/**
	 * 综合报告中，应该按照实验钢丝类别的顺序排序
	 */
	private List<Double> getBreakConclusionSequence(String entrustNum) {
		String trialClass = wireRopeMapper.selectByEnsNum(entrustNum).getTrialClass();
		String[] splits = trialClass.split(",");
		List<Double> ndiameterSequenceList = new ArrayList<>();
		for (String s : splits) {
			String ndiameter = s.substring(0, s.indexOf("*"));
			ndiameterSequenceList.add(Double.parseDouble(ndiameter));
		}
		return ndiameterSequenceList;
	}
}
