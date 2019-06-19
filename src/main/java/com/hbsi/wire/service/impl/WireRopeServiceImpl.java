package com.hbsi.wire.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Pattern;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hbsi.dao.DiameterDeviationMapper;
import com.hbsi.dao.EntrustmentDataMapper;
import com.hbsi.dao.EntrustmentMapper;
import com.hbsi.dao.ReverseBendingMapper;
import com.hbsi.dao.SampleRecordMapper;
import com.hbsi.dao.SteelWireDataMapper;
import com.hbsi.dao.TensileStrengthMapper;
import com.hbsi.dao.UserMapper;
import com.hbsi.dao.WireAttributesGbt201182017Mapper;
import com.hbsi.dao.WireDataMapper;
import com.hbsi.dao.WireRopeMapper;
import com.hbsi.domain.DiameterDeviation;
import com.hbsi.domain.Entrustment;
import com.hbsi.domain.ReverseBending;
import com.hbsi.domain.SampleRecord;
import com.hbsi.domain.SteelWireData;
import com.hbsi.domain.TensileStrength;
import com.hbsi.domain.WireAttributesGbt201182017;
import com.hbsi.domain.WireData;
import com.hbsi.domain.WireRope;
import com.hbsi.entity.WireRopeData;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.PageResult;
import com.hbsi.response.Response;
import com.hbsi.thickwire.service.ThickWireService;
import com.hbsi.user.service.UserAuth;
import com.hbsi.util.Arith;
import com.hbsi.wire.service.En123852002Service;
import com.hbsi.wire.service.Iso24082004Service;
import com.hbsi.wire.service.WireRopeService;
import com.hbsi.wire.service.WireRopeServiceAPI9A2011;
import com.hbsi.wire.service.WireRopeServiceGB8918;
import com.hbsi.wire.service.WireRopeServiceMT716;
import com.hbsi.wire.service.WireRopeServiceYBT4542;
import com.hbsi.wire.service.WireRopeServiceYBT52952010;
import com.hbsi.wire.service.Ybt53592010Service;

@Service
public class WireRopeServiceImpl implements WireRopeService {
	private Logger logger = LoggerFactory.getLogger(WireRopeService.class);
	@Autowired
	private WireRopeMapper wireRopeMapper;
	@Autowired
	private EntrustmentMapper entrustmentMapper;
	@Autowired
	private WireDataMapper wireDataMapper;
	@Autowired
	private SteelWireDataMapper steelWireDataMapper;
	// GBT20118-2017 属性
	@Autowired
	private WireAttributesGbt201182017Mapper wireAttrMapperGBT201182017;
	// 直径允许误差
	@Autowired
	private DiameterDeviationMapper diameterDeviationMapper;

	/* 抗拉强度允许值 */
	@Autowired
	private TensileStrengthMapper tensileStrengthMapper;
	/* 扭转和弯曲次数 */
	@Autowired
	private ReverseBendingMapper reverseBendingMapper;
	@Autowired
	private EntrustmentDataMapper entDataMapper;

	/* 各个标准的判定 */
	@Resource
	private WireRopeService wireRopeService;// [GB/T 20118-2017]
	@Resource
	private WireRopeServiceGB8918 wireRopeServiceGB8918;// [GB 8918-2006]
	@Resource
	private WireRopeServiceMT716 wireRopeServiceMT716;// [MT 716-2005]
	@Resource
	private ThickWireService thickWireService;// [GB/T 20067-2017]
	@Resource
	private Ybt53592010Service ybt53592010Service;// [YB/T5359-2010]
	@Resource
	private En123852002Service en123852002Service;// [EN 12385-4:2002 + A1：2008]
	@Resource
	private WireRopeServiceYBT4542 wireRopeServiceYBT4542;// [YB/T 4542-2006]
	@Resource
	private WireRopeServiceAPI9A2011 wireRopeServiceAPI9A2011;// API 9A 2011]
	@Resource
	private Iso24082004Service iso24082004Service;// [ISO 2408-2004]
	@Resource
	private WireRopeServiceYBT52952010 wireRopeServiceYBT52952010;
	@Autowired
	private UserMapper userMapper;

	/**
	 * 查询委托单号列表
	 */
	public Response<List<String>> selectWireEntList(String judgeStandard, String enstrustmentNumber) {
		WireRope wireRope = new WireRope();
		wireRope.setJudgeStandard(judgeStandard);
		wireRope.setEnstrustmentNumber(enstrustmentNumber);
		List<String> list = wireRopeMapper.selectEntList(wireRope);
		return new Response<List<String>>(list);
	}

	/**
	 * 查询和创建钢丝绳报告
	 */
	@Override
	public Response<WireRope> selectOrCreateWR(String enstrustmentNumber, String standardNumber, Integer sampleBatch,
			String userName) {
		UserAuth userAuth = (user) -> ObjectUtils.isEmpty(user) ? false : "管理员".equals(user.getUserRank());
		if (standardNumber == null || "".equals(standardNumber)) {
			logger.debug("检测标准不能为空");
			return new Response<>("00001111", "请选择判定标准", null);
		}
		if (enstrustmentNumber != null && !"".equals(enstrustmentNumber)) {
//			Entrustment en1=new Entrustment();
//			en1.setEntrustmentNumber(enstrustmentNumber);
			Entrustment e1 = entrustmentMapper.selectEntrustmentNumber(enstrustmentNumber);
			if (e1 == null || e1.getEntrustmentNumber() == null) {
				logger.debug("该委托单号不存在");
				return new Response<>("00001111", "您输入的委托单号不存在", null);
			}
		} else {
			if (sampleBatch != null && sampleBatch != 0) {
				Entrustment e1 = entrustmentMapper.selectEntrustByBatchNum(sampleBatch);
				if (e1 == null || e1.getEntrustmentNumber() == null || "".equals(e1.getEntrustmentNumber())) {
					logger.debug("该委托单号不存在");
					return new Response<>("00001111", "该批次号尚未添加委托单或者该批次号不存在", null);
				} else {
					enstrustmentNumber = e1.getEntrustmentNumber();
				}
			}
		}
		WireRope wireRope = wireRopeMapper.selectByEnsNum(enstrustmentNumber);
		if (wireRope == null) {
//			Entrustment e=new Entrustment();
//			e.setEntrustmentNumber(enstrustmentNumber);
			Entrustment en = entrustmentMapper.selectEntrustmentNumber(enstrustmentNumber);
			SimpleDateFormat sdf1 = new SimpleDateFormat("yyyy-MM-dd");
//			SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM");
//			SimpleDateFormat sd=new SimpleDateFormat("yyMM");
			wireRope = new WireRope();
			wireRope.setEnstrustmentNumber(enstrustmentNumber);
			wireRope.setStockSplitMethod("部分试验");
			Date date = new Date();
			wireRope.setReportDate(sdf1.format(date));
			String batch = enstrustmentNumber.substring(enstrustmentNumber.length() - 3);
			wireRope.setReportNumber("L-" + "20" + enstrustmentNumber.substring(2, 4) + "-"
					+ enstrustmentNumber.substring(4, 6) + "-" + batch);
			wireRope.setRecorderNumber("DG" + enstrustmentNumber.substring(2));
			wireRope.setJudgeStandard(standardNumber);
			try {
				int i = wireRopeMapper.insertSelective(wireRope);
			} catch (Exception ex) {
				logger.error("添加钢丝绳报告出现错误:{}", ex.getLocalizedMessage());
				throw new BaseException(ExceptionEnum.WIREROPE_SAVE_ERROR);
			}
		} else if ("100".equals(wireRope.getState()) && !userAuth.isAdmin(userMapper.selectByUsername(userName))) {
			logger.debug("该报告已提交！查看请联系管理员");
			return new Response<>("00001112", "该报告已提交！查看请联系管理员", null);
		} else {

			List<WireData> list = wireDataMapper.selectByEnNum(enstrustmentNumber);
			wireRope.setWireDataList(list);

		}
		return new Response<WireRope>(wireRope);
	}

	/**
	 * 调入数据 1、先查看钢丝绳数据中有没有数据，如果没有，将钢丝数据取出来，对应添加到钢丝绳数据中返回
	 */
	@Override
	public Response<List<WireData>> selectWireData(WireRope wireRope) {
		String entrustmentNumber = wireRope.getEnstrustmentNumber();
		if (entrustmentNumber == null || "".equals(entrustmentNumber)) {
			return new Response<List<WireData>>("00001111", "委托单号未输入", null);
		}
		WireRope wr = wireRopeMapper.selectByEnsNum(entrustmentNumber);
		if (wr != null) {
			exchange(wr, wireRope);
			System.out.println("***" + wr);
		} else {
			wr = wireRope;
		}
		List<WireData> wireList = wireDataMapper.selectByEnNum(entrustmentNumber);
		if (wireList != null && wireList.size() > 0) {
			int i = wireDataMapper.deleteByEntrustment(entrustmentNumber);
			if (i >= 0) {
				wireList = null;
			}
		}
		if (wireList == null || wireList.size() == 0) {
			wireList = new ArrayList<WireData>();
			List<SteelWireData> slist = steelWireDataMapper.selectByEnNum(entrustmentNumber);
			System.out.println(slist);
			if (slist == null || slist.size() == 0) {
				logger.error("没有数据");
			} else {
				List<String[]> l = new ArrayList<>();
				String cl = wireRope.getTrialClass();
				if (cl == null || "".equals(cl)) {
					cl = wr.getTrialClass();
				}
				if (cl != null && !"".equals(cl)) {
					String[] str = cl.split(",");
					for (int i = 0; i < str.length; i++) {
						String s = str[i];
						String[] ss = s.split("\\*");
						String[] sa = ss[1].split("/");
						ss[1] = sa[0];
						l.add(ss);
					}
				} else {
					return new Response<List<WireData>>("00001111", "请输入试验钢丝类别", null);
				}
				int n = 1;// 钢丝根数
				int m = 0;
				for (SteelWireData s : slist) {
					WireData w = new WireData();
					if (l.size() > 0) {
						String[] nd = l.get(m);
						w.setnDiamete(Double.parseDouble(nd[0]));
						Integer num = Integer.parseInt(nd[1]);
						if (n == num) {
							n = 1;
							m++;
						} else {
							n++;
						}
					}
					w.setDiamete(s.getfDiamete1());
					w.setBreakTensile(s.getBreakTensile1());
					w.setTensileStrength(s.getTensileStrength());
					w.setKnotTension(s.getKnotTension());
					w.setKnotRate(s.getKnotRate());
					w.setTurnTimes(s.getTorsionTimes());
					w.setWindingTimes(s.getBendingTimes());
					w.setEntrustmentNumber(entrustmentNumber);
					wireList.add(w);

				}
//				List<WireData> list=wireDataMapper.selectByEnNum(wireRope.getEnstrustmentNumber());
				try {
					int j = wireRopeMapper.updateByenstrustmentNumber(wireRope);
//					if(list==null||list.size()==0) {
					int i = wireDataMapper.insertWireDataBatch(wireList);
//					}else {
					System.out.println("************");
//						int i=wireDataMapper.updateWireDataBatch(wireList);
//					}

				} catch (Exception e) {
					logger.error("添加钢丝绳数据失败,{}", e.getLocalizedMessage());
					e.printStackTrace();
					throw new BaseException(ExceptionEnum.WIREROPE_DATA_SAVE_ERROR);
				}
			}
		}
		return new Response<List<WireData>>(wireList);
	}

	@Autowired
	private SampleRecordMapper sampleRecordMapper;

	/**
	 * 调入数据，在实验记录表中取数据
	 */
	public Response<List<WireData>> selectWireDataList(WireRope wireRope) {
		String entrustmentNumber = wireRope.getEnstrustmentNumber();
		if (entrustmentNumber == null || "".equals(entrustmentNumber)) {
			return new Response<List<WireData>>("00001111", "委托单号未输入", null);
		}
		WireRope wr = wireRopeMapper.selectByEnsNum(entrustmentNumber);
		if (wr != null) {
			exchange(wr, wireRope);
			System.out.println("***" + wr);
		} else {
			wr = wireRope;
		}

		/**
		 * 钢丝绳数据表里没有，则去试验记录表中查询并组合
		 */
//		if(wireList==null||wireList.size()==0) {

		// 根据委托单号查询实验记录表
		// 查询委托单号查询批次号
		Entrustment ee = entrustmentMapper.selectEntrustmentNumber(entrustmentNumber);
		if (ee.getSampleBatch() == null || ee.getSampleBatch() == 0) {
			logger.info("批次号不存在");
			return new Response<>("00005290", "批次号不存在", null);
		}
		// 根据委托单号查询实验记录表（改为批次号）
		List<SampleRecord> sampleList = sampleRecordMapper.selectData(ee.getSampleBatch());
//			List<SampleRecord> sampleList=sampleRecordMapper.selectSampleData(entrustmentNumber);
		if (sampleList.size() <= 0) {
			logger.info("委托单不存在此实验记录");
			return new Response<>("00005290", "委托单不存在实验记录", null);
		}
		// 将试验数据组合成钢丝绳数据
		List<WireData> list = comboWireData(sampleList, entrustmentNumber);
		/**
		 * 拆分钢丝公称直径
		 */
		List<String[]> l = new ArrayList<>();
		String cl = wireRope.getTrialClass();
		if (cl == null || "".equals(cl)) {
			cl = wr.getTrialClass();
		}
		if (cl != null && !"".equals(cl)) {
			String[] str = cl.split(",");
			for (int i = 0; i < str.length; i++) {
				String s = str[i];
				String[] ss = s.split("\\*");
				String[] sa = ss[1].split("/");
				ss[1] = sa[0];
				l.add(ss);
			}
		} else {
			return new Response<List<WireData>>("00001111", "请输入试验钢丝类别", null);
		}
		int size = 0;// 试验钢丝的总根数
		for (int i = 0; i < l.size(); i++) {
			String[] nd = l.get(i);
			Integer num1 = Integer.parseInt(nd[1]);
			size += num1;
		}
		if (list != null && list.size() > 0) {
			if (list.size() != size) {
				logger.debug("试验钢丝类别填写错误，请检查");
				return new Response<List<WireData>>("00001111", "试验钢丝类别填写错误，请检查", null);
			} else {
				/**
				 * 先到钢丝绳数据表里查询,若已有数据则先将数据删除
				 */
				List<WireData> wireList = wireDataMapper.selectByEnNum(entrustmentNumber);
				if (wireList != null && wireList.size() > 0) {
					int i = wireDataMapper.deleteByEntrustment(entrustmentNumber);
					if (i >= 0) {
						wireList = null;
					}
				}
				if (wireList == null || wireList.size() <= 0) {
					wireList = new ArrayList<WireData>();
					int n = 1;// 每种直径额钢丝根数
					int m = 0;
					for (int i = 0; i < list.size(); i++) {
						WireData wireData = new WireData();
						WireData s1 = null;
						if (l.size() > 0) {
							String[] nd = l.get(m);
							double diamate = Double.parseDouble(nd[0]);
							Integer strengthL = 0;
							if (nd.length > 2) {
								strengthL = Integer.parseInt(nd[2]);
							}
							if (diamate > 0.5) {
								s1 = list.get(i);
								s1.setnDiamete(diamate);
								s1.setStrengthLevel(strengthL);
								Integer num = Integer.parseInt(nd[1]);
								if (n == num) {
									n = 1;
									m++;
								} else {
									n++;
								}
							} else {
								s1 = list.get(i);
								WireData s2 = list.get(i + 1);
								s1.setnDiamete(diamate);
								s1.setStrengthLevel(strengthL);
								s1.setKnotTension(s2.getBreakTensile1());
								Integer num = Integer.parseInt(nd[1]);
								i++;
								if (n == num) {
									n = 1;
									m++;
								} else {
									n++;
								}
							}
						}
//								double d = (Arith.div(s1.getBreakTensile1(), Arith.mul(Math.PI, Arith.mul(s1.getnDiamete1()/2, s1.getnDiamete1()/2))))*1000;
//								s1.setTensileStrength((int) Math.round(d));
						wireData = s1;
						wireList.add(wireData);
					}
					try {
						int j = wireRopeMapper.updateByenstrustmentNumber(wireRope);
//							if(list==null||list.size()==0) {
						int i = wireDataMapper.insertWireDataBatch(wireList);
//							}else {
						System.out.println("************");
//								int i=wireDataMapper.updateWireDataBatch(wireList);
//							}
						return new Response<List<WireData>>(wireList);
					} catch (Exception e) {
						logger.error("添加钢丝绳数据失败,{}", e.getLocalizedMessage());
						e.printStackTrace();
						throw new BaseException(ExceptionEnum.WIREROPE_DATA_SAVE_ERROR);
					}
				}
			}
			return new Response<List<WireData>>(null);
		} else {
			logger.debug("试验数据不存在");
			return new Response<List<WireData>>("00001111", "试验数据不存在", null);
		}

//		}

	}

	/**
	 * 将试验记录数据组合成钢丝绳数据
	 * 
	 * @param list
	 * @return
	 */
	private List<WireData> comboWireData(List<SampleRecord> list, String entrustmentNumber) {
		Map<Integer, WireData> map = new HashMap<Integer, WireData>();
		for (SampleRecord record : list) {
			WireData steelWireData = null;
			if (!map.containsKey(record.getSampleNumber())) {
				steelWireData = new WireData();
			} else {
				steelWireData = map.get(record.getSampleNumber());
			}
			if ("拉力".equals(record.getType())) {
				steelWireData.setBreakTensile(record.getExperimentalData());
				steelWireData.setMachineNumber(record.getMachineNumber());
				steelWireData.setMachineType(record.getMachineType());
			} else if ("弯曲".equals(record.getType())) {
				steelWireData.setWindingTimes(record.getExperimentalData().intValue());
			} else if ("扭转".equals(record.getType())) {
				steelWireData.setTurnTimes(record.getExperimentalData().intValue());
			}
			steelWireData.setEntrustmentNumber(entrustmentNumber);
			map.put(record.getSampleNumber(), steelWireData);
		}
		List<WireData> slist = new ArrayList<WireData>(map.values());
		return slist;
	}

	private String validateWireRope(WireRope wireRope) {
		if (wireRope.getEnstrustmentNumber() == null || "".equals(wireRope.getEnstrustmentNumber())) {
			return "委托单号为空，请输入委托单号";
		}
		if (wireRope.getSpecification() == null || "".equals(wireRope.getSpecification())) {
			return "规格不能为空，请填写规格";
		}

		if (!Pattern.matches("^\\d+(\\.\\d+)?$", wireRope.getSpecification())) {
			return "规格应为数值";
		}
		if (wireRope.getStructure() == null || "".equals(wireRope.getStructure())) {
			return "结构不能为空，请输入或选择结构";
		}
		if (wireRope.getSurfaceState() == null || "".equals(wireRope.getSurfaceState())) {
			return "表面状态不能为空";
		}
		if (wireRope.getStrengthLevel() == null || "".equals(wireRope.getStrengthLevel())) {
			return "强度级别不能为空";
		}
		List<WireData> list = wireRope.getWireDataList();
		if (list == null || list.size() == 0) {
			return "钢丝绳中钢丝数据为空";
		}
		if (!wireRope.getStructure().contains("V")) {
			for (WireData d : list) {
				if (d.getDiamete1() == null || d.getDiamete1() == 0) {
					return "您的钢丝数据中直径有为空或者为0的项";
				}
				if((d.getWindingTimes()==null||d.getWindingTimes()==0)&&(d.getKnotTension()==null||d.getKnotTension()==0)) {
					return "您的钢丝数据中弯曲次数有为空或者为0的项";
				}
			}
		}
		for (WireData d : list) {
			if((d.getWindingTimes()==null||d.getWindingTimes()==0)&&(d.getKnotTension()==null||d.getKnotTension()==0)) {
				return "您的钢丝数据中弯曲次数有为空或者为0的项";
			}
		}
		
		return "SUCCESS";
	}

	/**
	 * 钢丝绳总和判定 标准 GBT20118-2017 先保存数据 1.计算最小钢丝破断拉力 2.计算实测钢丝拉力总和 3、直径判定 4、抗拉强度判定
	 * 5、扭转次数判定 6、弯曲次数判定 7、
	 */
	@Override
	public Response<WireRope> judgeWireRopeGbt201182017(WireRope wireRope) {

		/* 数据保存 */
		if (wireRope == null) {
			logger.error("没有数据");
			return new Response<WireRope>("00001111", "钢丝绳数据为空", null);
		} else {
			String m = validateWireRope(wireRope);

			if (!"SUCCESS".equals(m)) {
				return new Response<WireRope>("00001111", m, null);
			}
//			wireRope.setJudgeStandard("GB/T 20118-2017");
			/* 计算钢丝破断拉力 */
			double wireBreak = calWireBreakTensile(wireRope);
			if (wireBreak == -1) {
				logger.debug("试验钢丝类别填写错误");
				return new Response<WireRope>("00001111", "试验钢丝类别填写错误或者不试验钢丝类别填写错误", null);
			}
			wireBreak = Arith.getValue(wireBreak);
			/**
			 * 计算钢丝绳最小破断拉力总和 K*D*D*R0/1000
			 */
			// 查询捻制损失系数
			WireAttributesGbt201182017 att = wireAttrMapperGBT201182017
					.selectTanningByStructure(wireRope.getStructure());
			if (att == null || att.getId() == null) {
				return new Response<WireRope>("00001111", "您输入的结构在当前判定标准中不存在", null);
			}
			double minBreakForce = att.getMinimumBreakingForce();
			double diamate = Double.parseDouble(wireRope.getSpecification());// 钢丝绳直径
			double strengthLevel = Double.parseDouble(wireRope.getStrengthLevel());
			double breakValue = Arith
					.div(Arith.mul(Arith.mul(minBreakForce, Math.pow(diamate, 2)), (double) strengthLevel), 1000.0);
//			double  standardBreak=Arith.getValue(breakValue);

			double wireBreakAll = breakValue * att.getTanningLossFactor();// 钢丝绳最小破断拉力总和
			wireBreakAll = Arith.getValue(wireBreakAll);
			wireRope.setMinBreakTensile(wireBreakAll);
			wireRope.setMeasureBreakTensile(wireBreak);
			/* 修改钢丝绳数据 */
			saveWireData(wireRope);
			Map<Double, int[]> resultMap = judgeWireRope(wireRope);
			// 直径允许有不超过3%的钢丝超过规定
			int min_strength = 0;
			int min_turn = 0;
			Integer num = wireRope.getWireDataList().size();// 试验钢丝的总根数
			// 根据钢丝绳结构查询钢丝绳允许低值钢丝根数
			if ((wireRope.getNonTrialClass() != null && wireRope.getNonTrialClass().length() != 0)
					|| "部分试验".equals(wireRope.getStockSplitMethod())) {
				if (att.getPartialIntensityLow() == null || att.getPartialReverseLow() == null) {
					// 部分试验
					min_strength = (int) (num * 0.06);
					min_turn = (int) (num * 0.05);
				} else {
					min_strength = att.getPartialIntensityLow();
					min_turn = att.getPartialReverseLow();
				}
			} else {
				// 100%试验
				min_strength = (int) (num * 0.025);
				min_turn = (int) (num * 0.025);
			}
			// sb中的数据存储到evaluation中
			StringBuilder sb = new StringBuilder();
			// sb1中的数据存储到memo中
			StringBuilder sb1 = new StringBuilder();
			int[] all = resultMap.get(0d);
			if (wireBreak < wireBreakAll) {
				sb.append("不合格");
				sb1.append("实测钢丝破断拉力小于最小破断拉力总和,");
			} else {
				if (all != null && all.length != 0) {
					if (all[0] > 0) {
						sb.append("不合格");
					} else if (all[1] > num * 0.03) {
						sb.append("不合格");
					} else if (all[2] > 0) {
						sb.append("不合格");
					} else if (all[3] > 0) {
						sb.append("不合格");
					} else if (all[5] > 0) {
						sb.append("不合格");
					} else if (all[7] > 0) {
						sb.append("不合格");
					} else if (all[1] > num * 0.03) {
						sb.append("不合格");
					} else if (all[4] > min_strength) {
						sb.append("不合格");
					} else if (all[6] > min_turn || all[8] > min_turn) {
						sb.append("不合格");
					} else if (all[14] > all[13] * 0.05) {// 小于0.5的钢丝要至少95%符合规定
						sb.append("不合格");
					} else {
						sb.append("合格");
					}
				}
			}

//			sb.append(",");
			Iterator<Double> it = resultMap.keySet().iterator();
			// 不合格,] Φ4.05钢丝[直径有2根有偏差但在允许范围内 ] Φ3.15钢丝[直径有1根不合格 ]
			/**
			 * int diamaten=0;//直径不合格数量 int[0] int diamatem=0;//直径有偏差但在允许范围内的直径数量 int[1]
			 * 直径允许有不超过3%的测量钢丝超出规定 int breakn=0;//破断拉力不合格数量 int[2] int strengthn=0;//抗拉强度不合格
			 * int[3] int strengthm=0;//抗拉强度低值 int[4] int turnn=0;//扭转次数不合格数量 int[5] int
			 * turnm=0;//扭转次数低值 int[6] int windingn=0;//弯曲次数不合格数量 int[7] int
			 * windingm=0;//弯曲次数低值 int[8] 符合，在"综合判定"栏显示"合格"
			 * 不符合，"综合判定"栏显示"不合格"，并列出不符合项不合格情况说明
			 */
			while (it.hasNext()) {
				double ndiamate = it.next();
				if (ndiamate != 0 && ndiamate != 0.0) {
					int[] re = resultMap.get(ndiamate);
					int i = 0;
					for (; i < re.length; i++) {
						if (re[i] != 0) {// 有低值或者不合格则跳出本层循环
							break;
						}
					}
					if (i >= re.length) {
						continue;// 若该公称直径下没有不合格或者低值则跳出本次循环
					} else {
						// 若该公称直径下有低值或者不合格则显示
						sb1.append("Φ").append(String.format("%.2f", ndiamate)).append("钢丝[");
						if (re[0] > 0) {
							sb1.append("直径有").append(re[0]).append("根不合格 ");
						}
						if (re[1] > 0) {
							sb1.append("直径有").append(re[1]).append("根有偏差但在允许范围内 ");
						}
//						if(re[2]>0) {
//							sb.append("破断拉力有").append(re[2]).append("根不合格 ");
//						}
						if (re[3] > 0) {
							sb1.append("抗拉强度有").append(re[3]).append("根不合格 ");
						}
						if (re[4] > 0) {
							sb1.append("抗拉强度有").append(re[4]).append("根低值 ");
						}
						if (re[5] > 0) {
							sb1.append("扭转有").append(re[5]).append("根不合格 ");
						}
						if (re[6] > 0) {
							sb1.append("扭转有").append(re[6]).append("根低值 ");
						}
						if (re[7] > 0) {
							sb1.append("反复弯曲有").append(re[7]).append("根不合格 ");
						}
						if (re[8] > 0) {
							sb1.append("反复弯曲有").append(re[8]).append("根低值 ");
						}
						if (re[13] > 0) {
							sb1.append("打结拉力有").append(re[13]).append("根不合格");
						}
//						if(re[9]>0) {
//							sb.append("有").append(re[9]).append("根抗拉强度和扭转均低值 ");
//						}
//						if(re[10]>0) {
//							sb.append("有").append(re[10]).append("根抗拉强度和反复弯曲均低值 ");
//						}
//						if(re[11]>0) {
//							sb.append("有").append(re[11]).append("根扭转和反复弯曲均低值 ");
//						}
//						if(re[12]>0) {
//							sb.append("有").append(re[12]).append("根抗拉强度、扭转和反复弯曲均低值 ");
//						}

					}
					sb1.append("]  ");
				}
			}
			System.out.println("*******" + sb.toString());
			wireRope.setEvaluation(sb.toString());
			wireRope.setMemo(sb1.toString());
			try {
				wireRopeMapper.updateRopeEvaluatuion(wireRope);
				return new Response<WireRope>(wireRope);
			} catch (Exception e) {
				logger.error("钢丝绳判定结果保存失败,{}", e.getLocalizedMessage());
				throw new BaseException(ExceptionEnum.WIREROPE_SAVE_ERROR);
			}
		}

	}

	private Map<Double, int[]> judgeWireRope(WireRope wireRope) {
		WireAttributesGbt201182017 att = wireAttrMapperGBT201182017.selectTanningByStructure(wireRope.getStructure());
		// 钢丝绳的股类型
		String shape = att.getShape();
		String structure = wireRope.getStructure();
		if (structure.contains("V")) {
			shape = "异形股";
		} else {
			shape = "圆形股";
		}
		List<WireData> dataList = wireRope.getWireDataList();

		WireRopeData wireRopeData = new WireRopeData();
		wireRopeData.setStandardNum(wireRope.getJudgeStandard());
		/**
		 * 判断钢丝绳表面状态
		 */
		String surface1 = wireRope.getSurfaceState();
		String surface = "";// 用于扭转弯曲和判定
		if (surface1.contains("光面")) {
			surface = "UB";
		} else if (surface1.contains("A类") || surface1.contains("A级")) {
			surface = "A";
		}else if (surface1.contains("AB类") || surface1.contains("AB级")) {
			surface = "AB";
		} else if (surface1.contains("B类") || surface1.contains("B级")) {
			surface = "UB";
		}  else if (surface1.contains("光面和B类") || surface1.contains("光面和B级")) {
			surface = "UB";
		}
		String diaSurface = surface;// 用于直径偏差
		if (surface.equals("UB") || surface.equals("AB")) {
			diaSurface = "U,B,AB";
		}
//		if(surface1.contains("光面")&&surface1.contains("B级")) {
//			surface="U,B";//用于扭转弯曲判定
//		}
		wireRopeData.setSurface(diaSurface);
		Integer strengthLevel = Integer.parseInt(wireRope.getStrengthLevel());// 抗拉强度
		/*******************/

		/**
		 * 按照试验钢丝数据公称直径组合数据
		 */
		Map<Double, List<WireData>> dataMap = new HashMap<>();
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
		/**
		 * 获取试验钢丝类别公称抗拉强度
		 */
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
		 * 对dataMap数据进行操作，按照不同的直径进行判定
		 */
		// 定义存放钢丝不合格根数和低值根数的map，以公称直径作为key
		/**
		 * int diamaten=0;//直径不合格数量 int[0] int diamatem=0;//直径有偏差但在允许范围内的直径数量 int[1] int
		 * breakn=0;//破断拉力不合格数量 int[2] 不判断破断拉力 int strengthn=0;//抗拉强度不合格 int[3] int
		 * strengthm=0;//抗拉强度低值 int[4] int turnn=0;//扭转次数不合格数量 int[5] int
		 * turnm=0;//扭转次数低值 int[6] int windingn=0;//弯曲次数不合格数量 int[7] int
		 * windingm=0;//弯曲次数低值 int[8] int[9] 抗拉强度 扭转次数均低值 int[10] 抗拉强度 弯曲次数均低值 int[11]
		 * 扭转次数和弯曲次数均低值 int[12] 抗拉强度 扭转次数 弯曲次数均 int[13] 小于0.5的钢丝的打结拉力不符合要求的根数
		 */
		Map<Double, int[]> resultMap = new HashMap<Double, int[]>();
		Set<Double> diaSet = dataMap.keySet();
		Iterator<Double> it = diaSet.iterator();
		// 公称直径
		double ndiamate = 0;
		// 钢丝最小破断拉力系数

		DiameterDeviation dd = null;// 钢丝直径允许最大偏差 根据公称直径所在范围进行查询

		// 查询扭转弯曲次数的最低值条件
		ReverseBending rb = new ReverseBending();
		rb.setStandardNum(wireRope.getJudgeStandard());
		rb.setRatedStrength(strengthLevel + "");
		rb.setSurfaceState(surface);
		int[] all = new int[15];// 所有的钢丝判定结果，all[13] 代表小于0.5钢丝根数 all[14]代表小于0.5钢丝不符合要求的数目
		while (it.hasNext()) {
			ndiamate = it.next();// 一种钢丝公称直径
			Integer wireStrengthLevel = strengthMap.get(ndiamate);
			if (wireStrengthLevel == null || wireStrengthLevel == 0) {
				wireStrengthLevel = strengthLevel;
			}
			wireRopeData.setNdiamete(ndiamate);

			dd = diameterDeviationMapper.selectDiaByType(wireRopeData);// 钢丝直径允许的最大偏差

			/**
			 * 计算标准抗拉强度允许值
			 */
			// 根据标准号和抗拉强度级别查询最低抗拉强度值
			TensileStrength t = new TensileStrength();
			t.setStandardNum(wireRope.getJudgeStandard());
			t.setRatedStrength(wireStrengthLevel);
			List<TensileStrength> tensileList = tensileStrengthMapper.selectByStrengthLevel(t);
			// 甲
			Integer first = 0;
			// 乙
			Integer second = 0;
			// 丙
			Integer third = 0;
			/* 如果公称抗拉强度不在规定数据中，则按照P11 表9中注进行计算，甲栏为该公称抗拉强度降50，乙栏为降5%，丙栏为降10% 修约成整数 */
			if (tensileList == null || tensileList.size() == 0) {
				first = strengthLevel - 50;
//				second=(int)(strengthLevel*(1-0.05));//需要进行修约成整数
//				third=(int)(strengthLevel*(1-0.1));
				second = Arith.revision(wireStrengthLevel * (1 - 0.05));
				third = Arith.revision(wireStrengthLevel * (1 - 0.1));
			} else {
				for (TensileStrength t1 : tensileList) {
					if ("甲".equals(t1.getType())) {
						first = t1.getStrengthValue();
					} else if ("乙".equals(t1.getType())) {
						second = t1.getStrengthValue();
					} else if ("丙".equals(t1.getType())) {
						third = t1.getStrengthValue();
					}
				}
			}

			/********************************************/
			// 查询公称直径对应的扭转弯曲次数
			rb.setfDiamete(ndiamate);
			rb.setRatedStrength(wireStrengthLevel + "");
			List<ReverseBending> rlist = reverseBendingMapper.selectRBDataByCon(rb);
			int[] d = new int[2];
			for (ReverseBending r : rlist) {
				if ("R".equals(r.getType())) {
					d[0] = r.getValueRob();
				} else if ("B".equals(r.getType())) {
					d[1] = r.getValueRob();
				}
			}

			List<WireData> dlist = dataMap.get(ndiamate);// 公称直径的钢丝数据
			int[] re = new int[14];// 该公称直径的判定结果 re[13] 公称直径小于0.5的打结拉力不符合的根数
			boolean f1 = false;// 抗拉强度低值
			boolean f2 = false;// 扭转次数低值
			boolean f3 = false;// 弯曲次数低值

			for (WireData wireData : dlist) {
				wireData.setDiameteJudge("");
				wireData.setStrengthJudge("");
				wireData.setTensileJudge("");
				wireData.setTurnJudge("");
				wireData.setWindingJudge("");
				wireData.setZincJudge("");
				wireData.setKnotJudge("");
				/**
				 * 计算抗拉强度
				 */
				double strength = (Arith.div(wireData.getBreakTensile1(),
						Arith.mul(Math.PI, Arith.mul(wireData.getnDiamete1() / 2, wireData.getnDiamete1() / 2))))
						* 1000;
				wireData.setTensileStrength(Arith.revision(strength));
				/**
				 * 直径判定
				 */
				

				/**
				 * 抗拉强度判断 先在抗拉强度表中进行查询，按照11页进行判定。若查询为空，按照表9 注，进行计算允许最低抗拉强度低值
				 */
				int minTurnTimes = d[0];// 扭转次数允许最低值
				int minWindTimes = d[1];// 弯曲次数允许最低值
				if ("圆形股".equals(shape)) {
					double diamete = wireData.getDiamete1();// 钢丝直径
					if (Math.abs(Arith.sub(diamete, ndiamate)) > dd.getValue() * 1.5) {
						re[0]++;// 直径不合格数量加1
						all[0]++;
						wireData.setDiameteJudge("**");
					} else if (Math.abs(Arith.sub(diamete, ndiamate)) > dd.getValue()) {

						re[1]++;// 直径允许最大偏差+1
						all[1]++;
						wireData.setDiameteJudge("*");
					}
					// 抗拉强度判定
					if (wireData.getTensileStrength() < third) {
						re[3]++;// 不合格
						all[3]++;
						wireData.setStrengthJudge("**");
					} else if (wireData.getTensileStrength() < first) {
						re[4]++;// 低值
						all[4]++;
						wireData.setStrengthJudge("*");
						f1 = true;
					}
					// 钢丝直径小于0.5 打结拉力替代扭转个弯曲
					if (ndiamate < 0.5) {
						all[13]++;
						// 打结拉力应不小于钢丝公称抗拉强度50%的拉力
						double knotTension = wireData.getKnotTension();// 试验打结拉力
//						double k1=strengthLevel*Math.PI*Math.pow((wireData.getnDiamete1()/2.0), 2)*0.5;
						double k1 = Arith
								.mul(Arith.div(Arith.mul(Arith.mul(Math.PI, Math.pow(wireData.getnDiamete1() / 2.0, 2)),
										(double) wireStrengthLevel), 1000.0), 0.5);
						if (knotTension < k1) {
							re[13]++;
							all[14]++;
							wireData.setKnotJudge("**");
						}
						double la = Arith.div(Arith.mul(Arith.mul(Math.PI, Math.pow(wireData.getnDiamete1() / 2.0, 2)),
								(double) wireStrengthLevel), 1000.0);
						double knotRate = Arith.div(knotTension, la);
						wireData.setKnotRate(Double.valueOf(Math.round(Arith.mul(knotRate, 100.0))));

						int effectedNum = wireDataMapper.updateByPrimaryKeySelective(wireData);
						continue;
					}
					// 扭转次数判定
					// 根据钢丝的公称直径获取扭转次数

					if (wireData.getTurnTimes() < Math.round(Arith.mul((double) minTurnTimes, Arith.sub(1.0, 0.2)))) {
						re[5]++;
						all[5]++;
						wireData.setTurnJudge("**");
					} else if (wireData.getTurnTimes() < minTurnTimes) {
						re[6]++;
						all[6]++;
						wireData.setTurnJudge("*");
						f2 = true;
					}
					// 弯曲次数判定
//					if(wireData.getWindingTimes()<minWindTimes) {
//						re[7]++;
//						all[7]++;
//						wireData.setWindingJudge("**");
//						f3=true;
//					}
					// 弯曲次数判定
					if (wireData.getWindingTimes() < Math.round(minWindTimes * (1 - 0.2))) {
						re[7]++;
						all[7]++;
						wireData.setWindingJudge("**");
					} else if (wireData.getWindingTimes() < minWindTimes) {
						re[8]++;
						all[8]++;
						wireData.setWindingJudge("*");
						f3 = true;
					}

				} else if ("异形股".equals(shape)) {
					re[0]=0;
					re[1]=0;
					all[0]=0;
					all[0]=0;
					if (wireData.getTensileStrength() < third) {
						re[3]++;
						all[3]++;
						wireData.setStrengthJudge("**");
					} else if (wireData.getTensileStrength() < second) {
						re[4]++;
						all[4]++;
						wireData.setStrengthJudge("*");
						f1 = true;
					}

					// 钢丝直径小于0.5 打结拉力替代扭转个弯曲
					if (ndiamate < 0.5) {
						all[13]++;
						// 打结拉力应不小于钢丝公称抗拉强度50%的拉力
						double knotTension = wireData.getKnotTension();// 试验打结拉力
//						double k1=strengthLevel*Math.PI*Math.pow((wireData.getnDiamete1()/2.0), 2)*0.5;
						double k1 = Arith
								.mul(Arith.div(Arith.mul(Arith.mul(Math.PI, Math.pow(wireData.getnDiamete1() / 2.0, 2)),
										(double) wireStrengthLevel), 1000.0), 0.5);
						if (knotTension < k1) {
							re[13]++;
							all[14]++;
							wireData.setKnotJudge("**");
						}
						double la = Arith.div(Arith.mul(Arith.mul(Math.PI, Math.pow(wireData.getnDiamete1() / 2.0, 2)),
								(double) wireStrengthLevel), 1000.0);
						double knotRate = Arith.div(knotTension, la);
						wireData.setKnotRate(Double.valueOf(Math.round(Arith.mul(knotRate, 100.0))));

						int effectedNum = wireDataMapper.updateByPrimaryKeySelective(wireData);
						continue;
					}

					/* 扭转次数判定 */
					if (wireData.getTurnTimes() < minTurnTimes * (1 - 0.3)) {
						re[5]++;
						all[5]++;
						wireData.setTurnJudge("**");
					} else if (wireData.getTurnTimes() < minTurnTimes-1) {
						re[6]++;
						all[6]++;
						wireData.setTurnJudge("*");
						f2 = true;
					}
					// 弯曲次数判定
					if (wireData.getWindingTimes() < minWindTimes * (1 - 0.2)) {
						re[7]++;
						all[7]++;
						wireData.setWindingJudge("**");
					} else if (wireData.getWindingTimes() < minWindTimes) {
						re[8]++;
						all[8]++;
						wireData.setWindingJudge("*");
						f3 = true;
					}
				}
				if (f1 & f2 & !f3) {
					re[9]++;
					all[9]++;
				}
				if (f1 & f3 & !f2) {
					re[10]++;
					all[10]++;
				}
				if (f2 & f3 & !f1) {
					re[11]++;
					all[11]++;
				}
				if (f1 & f2 & f3) {
					re[12]++;
					all[12]++;
				}
				try {
					int effectedNum = wireDataMapper.updateByPrimaryKeySelective(wireData);
//					System.out.println("^^^^"+wireData);
					if (effectedNum <= 0) {
						logger.error("这条wireData数据的判定结果没有保存成功");
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
			resultMap.put(ndiamate, re);
		}
		resultMap.put(0d, all);
		return resultMap;
	}

	/**
	 * 计算钢丝的最小破断拉力
	 * 
	 * @param wireRope
	 * @return 试验钢丝的拉力+不试验钢丝的计算拉力 试验钢丝的拉力为一股中拉力相加，*股数 不试验钢丝的计算拉力=绳级*面积
	 */
	public double calWireBreakTensile(WireRope wireRope) {
		/* 试验钢丝的拉力 */
		String trialClass = wireRope.getTrialClass();
		String[] s = trialClass.split(",");
		List<double[]> dlist = new ArrayList<>();
		List<double[]> list = new ArrayList<>();
		// 获取试验钢丝的公称直径、根数、股数
		for (int i = 0; i < s.length; i++) {
			String s1 = s[i];
			if (!(s1.contains("*") && s1.contains("/"))) {
				return -1;
			}
			try {
				double[] d = new double[4];
				String[] nd = s1.split("\\*");// nd[0]钢丝直径 nd[1] 6/36
				String[] nds = nd[1].split("/");
				d[0] = Double.parseDouble(nd[0]);
				d[1] = Double.parseDouble(nds[0]);
				d[2] = Double.parseDouble(nds[1]);
//				d[0]=Double.parseDouble(s1.substring(0, s1.indexOf("*")));//钢丝直径
//				d[1]=Double.parseDouble(s1.substring(s1.indexOf("*")+1,s1.indexOf("/")));//钢丝每股根数
//				d[2]=Double.parseDouble(s1.substring(s1.indexOf("/")+1));//总根数
				d[2] = d[2] / d[1];// 股数
				dlist.add(d);
			} catch (Exception e) {
				e.printStackTrace();
				return -1;
			}
		}
		// 计算试验钢丝的拉力
		List<WireData> dataList = wireRope.getWireDataList();
		for (int i = 0; i < dlist.size(); i++) {
			double[] d = dlist.get(i);
			double f = 0;
			for (WireData data : dataList) {
				if (data.getnDiamete1() == d[0]) {
					f += data.getBreakTensile1();
				}
			}
			d[3] = f;
			list.add(d);
		}
		double trialF = 0;// 试验钢丝拉力总和
		for (double[] d : list) {
			trialF += d[2] * d[3];
		}
//		System.out.println(trialF+"   *************************");
		// 计算不试验钢丝拉力总和
		String nonTrial = wireRope.getNonTrialClass();
		double nonTrialF = 0;// 不试验钢丝拉力总和
		if (nonTrial != null && !"".equals(nonTrial)) {
			if (nonTrial.contains("L")) {
				String[] non = nonTrial.split("=");
				if (non != null && non.length > 0) {
					try {
						nonTrialF = Double.parseDouble(non[1]);
					} catch (Exception e) {
						e.printStackTrace();
						return -1;
					}
				}
			} else {
				String[] nons = nonTrial.split(",");
				for (String ns : nons) {
					String[] nss = ns.split("\\*");
					double diamate = Double.parseDouble(nss[0]);
					if (nss[1] != null && nss[1].contains("/")) {
						String[] ns1 = nss[1].split("/");
						try {
							Integer n = Integer.parseInt(ns1[1]);
							if (nss.length == 3) {
								Integer strengthL = Integer.parseInt(nss[2]);
								nonTrialF += Arith.div(Math.PI * Math.pow(diamate / 2, 2) * strengthL * n, 1000.0);
							} else {
								nonTrialF += Arith.div(Math.PI * Math.pow(diamate / 2, 2)
										* (Integer.parseInt(wireRope.getStrengthLevel())) * n, 1000.0);
							}

						} catch (Exception e) {
							e.printStackTrace();
							return -1;
						}

					} else {
						if (nss.length == 3) {
							Integer strengthL = Integer.parseInt(nss[2]);
							int n = Integer.parseInt(nss[1]);
							nonTrialF += Arith.div(Math.PI * Math.pow(diamate / 2, 2) * strengthL * n, 1000.0);
						} else {
							int n = Integer.parseInt(nss[1]);
							nonTrialF += Arith.div(Math.PI * Math.pow(diamate / 2, 2)
									* (Integer.parseInt(wireRope.getStrengthLevel())) * n, 1000.0);
						}
					}

				}
			}
		}

//		System.out.println(nonTrialF+"  **********8");
		return (trialF + nonTrialF);
	}

	/**
	 * 提交钢丝绳报告数据
	 */
	public Response<WireRope> saveWire(WireRope wireRope) {
		if (wireRope == null) {
			logger.info("没有数据");
		} else {
			WireRope w = wireRopeMapper.selectByEnsNum(wireRope.getEnstrustmentNumber());
			exchange(w, wireRope);
			List<WireData> dataList = wireDataMapper.selectByEnNum(wireRope.getEnstrustmentNumber());
			List<WireData> list = wireRope.getWireDataList();
			List<WireData> dlist = new ArrayList<>();
			for (WireData d : dataList) {
				for (WireData l : list) {
					if (d.getId() == l.getId() || d.getId().equals(l.getId())) {
						wireDataChange(d, l);
						dlist.add(d);
						break;
					}
				}
			}
			try {

				if (w.getEvaluation() == null || "".equals(w.getEvaluation())) {
					int i = wireRopeMapper.updateByPrimaryKey(w);
					for (WireData wireData : dlist) {
						System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
						wireData.setJudgeStandard(wireRope.getJudgeStandard());
						int j = wireDataMapper.updateByPrimaryKeySelective(wireData);
					}
					return new Response<WireRope>("00001111", "您尚未综合判定，不能提交报告", null);
				} else {
					w.setState("100");
					int i = wireRopeMapper.updateByPrimaryKey(w);
					for (WireData wireData : dlist) {
						System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$4");
						wireData.setJudgeStandard(wireRope.getJudgeStandard());
						int j = wireDataMapper.updateByPrimaryKeySelective(wireData);
					}
				}
			} catch (Exception e) {
				logger.error("修改钢丝绳数据出现错误:{}", e.getLocalizedMessage());
				e.printStackTrace();
			}
		}
		return new Response<WireRope>(null);
	}

	/**
	 * 修改钢丝绳数据
	 * 
	 * @param wireRope
	 */
	public void saveWireData(WireRope wireRope) {
		WireRope w = wireRopeMapper.selectByEnsNum(wireRope.getEnstrustmentNumber());
		exchange(w, wireRope);
		List<WireData> dataList = wireDataMapper.selectByEnNum(wireRope.getEnstrustmentNumber());
		List<WireData> list = wireRope.getWireDataList();
		List<WireData> dlist = new ArrayList<>();
		for (WireData d : dataList) {
			for (WireData l : list) {
				if (d.getId() == l.getId() || d.getId().equals(l.getId())) {
					wireDataChange(d, l);
					dlist.add(d);
					break;
				}
			}
		}
		try {
			int i = wireRopeMapper.updateByPrimaryKey(w);
//			int j=wireDataMapper.updateWireDataBatch(dlist);
			for (WireData wireData : dlist) {
				wireData.setJudgeStandard(wireRope.getJudgeStandard());
				int j = wireDataMapper.updateByPrimaryKeySelective(wireData);
			}
		} catch (Exception e) {
			logger.error("修改钢丝绳数据出现错误:{}", e.getLocalizedMessage());
			e.printStackTrace();
		}
	}

	private void exchange(WireRope w, WireRope r) {
		if (r.getProducerNumber() != null && !"".equals(r.getProducerNumber())) {
			w.setProducerNumber(r.getProducerNumber());
		}
		if (r.getReportNumber() != null && !"".equals(r.getReportNumber())) {
			w.setReportNumber(r.getReportNumber());
		}
		if (r.getReportDate() != null && !"".equals(r.getReportDate())) {
			w.setReportDate(r.getReportDate());
		}
		if (r.getStructure() != null && !"".equals(r.getStructure())) {
			w.setStructure(r.getStructure());
		}
		if (r.getSpecification() != null && !"".equals(r.getSpecification())) {
			w.setSpecification(r.getSpecification());
		}
		if (r.getStrengthLevel() != null && !"".equals(r.getStrengthLevel())) {
			w.setStrengthLevel(r.getStrengthLevel());
		}
		if (r.getSurfaceState() != null && !"".equals(r.getSurfaceState())) {
			w.setSurfaceState(r.getSurfaceState());
		}
		if (r.getTwistingMethod() != null && !"".equals(r.getTwistingMethod())) {
			w.setTwistingMethod(r.getTwistingMethod());
		}
		if (r.getMeasureBreakTensile() != null) {
			w.setMeasureBreakTensile(r.getMeasureBreakTensile());
		}
		if (r.getMinBreakTensile() != null) {
			w.setMinBreakTensile(r.getMinBreakTensile());
		}
		if (r.getDiamete() != null) {
			w.setDiamete(r.getDiamete());
		}
		w.setDiameteNonRundness(r.getDiameteNonRundness());
		if (r.getStockSplitMethod() != null) {
			w.setStockSplitMethod(r.getStockSplitMethod());
		}
		if (r.getTrialClass() != null) {
			w.setTrialClass(r.getTrialClass());
		}
		if (r.getNonTrialClass() != null) {
			w.setNonTrialClass(r.getNonTrialClass());
		}
		w.setCenterDiamete(r.getCenterDiamete());
		w.setCenterStrength(r.getCenterStrength());
		if (r.getEvaluation() != null && !"".equals(r.getEvaluation())) {
			w.setEvaluation(r.getEvaluation());
		}
		if (r.getMemo() != null) {
			w.setMemo(r.getMemo());
		}
		if (r.getTemperature() != null) {
			w.setTemperature(r.getTemperature());
		}
		if (r.getTestStandard() != null) {
			w.setTestStandard(r.getTestStandard());
		}
		if (r.getRecorderNumber() != null) {
			w.setRecorderNumber(r.getRecorderNumber());
		}
		if (r.getRecorderMemo() != null) {
			w.setRecorderMemo(r.getRecorderMemo());
		}
		if (!ObjectUtils.isEmpty(r.getJudgeStandard())) {
			w.setJudgeStandard(r.getJudgeStandard());
		}

	}

	private void wireDataChange(WireData w, WireData d) {
		if (d.getnDiamete() != null) {
			w.setnDiamete(d.getnDiamete1());
		}
		if (d.getDiamete1() != null && d.getDiamete1() != 0) {
			w.setDiamete(d.getDiamete1());
		}
//		if(d.getDiamete()!=null) {
//			w.setDiamete(d.getDiamete());
//		}
//		if(d.getBreakTensile()!=null) {
//			w.setBreakTensile(d.getBreakTensile());
//		}
	}

	/**
	 * 调整标准号
	 */
	@Override
	public Response<WireRope> updateStandard(String enstrustmentNumber, String standardNumber) {
		WireRope wireRope = new WireRope();
		wireRope.setEnstrustmentNumber(enstrustmentNumber);
		wireRope.setTestStandard(standardNumber);
		try {
			int i = wireRopeMapper.updateStandard(wireRope);
			return new Response<WireRope>(wireRope);
		} catch (Exception e) {
			logger.debug("调整检测标准号失败,{}", e.getLocalizedMessage());
			throw new BaseException(ExceptionEnum.WIREROPE_STANDARD_UPDATE_ERROR);
		}
	}

	/**
	 * 调整直径
	 */
	public Response<WireRope> updateDiamate(WireRope wireRope) {
		if (wireRope == null) {
			logger.info("没有数据");
			return new Response<WireRope>("00001111", "传入数据为空", null);
		} else {

//			List<WireData> dataList=wireDataMapper.selectByEnNum(wireRope.getEnstrustmentNumber());
			List<WireData> dataList = wireRope.getWireDataList();
			try {
				int i = wireRopeMapper.updateTrialClass(wireRope);
//				int j=wireDataMapper.updateWireDataBatch(dataList);
				for (WireData wireData : dataList) {
					double d = (Arith.div(wireData.getBreakTensile1(),
							Arith.mul(Math.PI, Arith.mul(wireData.getnDiamete1() / 2, wireData.getnDiamete1() / 2))))
							* 1000;
//					System.out.println(d);
					wireData.setTensileStrength((int) (Math.round(d)));// 抗拉强度
					int j = wireDataMapper.updateByPrimaryKey(wireData);
				}
//				wireRope=wireRopeMapper.selectByEnsNum(wireRope.getEnstrustmentNumber());
				dataList = wireDataMapper.selectByEnNum(wireRope.getEnstrustmentNumber());
				wireRope.setWireDataList(dataList);
				return new Response<WireRope>(wireRope);
			} catch (Exception e) {
				logger.error("修改钢丝绳数据出现错误:{}", e.getLocalizedMessage());
				throw new BaseException(ExceptionEnum.WIREROPE_DATA_SAVE_ERROR);
			}
		}
	}

	/**
	 * 综合判定
	 * 
	 * @param judgeStandard
	 * @return
	 */
	@Override
	public Response<WireRope> judgeWireRopeAll(WireRope wireRope) {
		String judgeStandard = wireRope.getJudgeStandard();
		if (judgeStandard == null || "".equals(judgeStandard)) {
			logger.info("判定标准为空");
			return new Response<>("00001000", "请选择判定标准", null);
		}
		if (judgeStandard.contains("GB/T 20118-2017")) {
			return judgeWireRopeGbt201182017(wireRope);
		}
		if (judgeStandard.contains("GB/T 20067-2017")) {
			return thickWireService.judgeThickWire(wireRope);
		}
		if (judgeStandard.contains("GB/T 8918-2006")) {
			return wireRopeServiceGB8918.judgeWire(wireRope);
		}
		if (judgeStandard.contains("YB/T 5359-2010")) {
			return ybt53592010Service.judgeWireRopeYbt53592010(wireRope);
		}
//		if(judgeStandard.contains("MT 716-2005 ")) {
//			return wireRopeServiceMT716.judgeWire(wireRope);
//		}
		if (judgeStandard.contains("API 9A 2011")) {
			return wireRopeServiceAPI9A2011.judgeWireRopeAPI9A2011(wireRope);
		}
		if (judgeStandard.contains("EN 12385-4:2002 + A1：2008")) {
			return en123852002Service.judgeWireRopeEn123852002(wireRope);
		}
		if (judgeStandard.contains("ISO 2408-2004")) {
			return iso24082004Service.judgeWireRopeIso24082004(wireRope);
		}
		if (judgeStandard.contains("YB/T 4542-2016")) {
			return wireRopeServiceYBT4542.judgeWire(wireRope);
		}
		if (judgeStandard.contains("YB/T 5295-2010")) {
			return wireRopeServiceYBT52952010.judgeWire(wireRope);
		}
		return new Response<WireRope>(wireRope);
	}

	/**
	 * 查询钢丝绳数据
	 */
	public Response<WireRope> selectWireRope(String entrustmentNumber, Integer sampleBatch) {
		if (entrustmentNumber != null && !"".equals(entrustmentNumber)) {
//			Entrustment en1=new Entrustment();
//			en1.setEntrustmentNumber(entrustmentNumber);
			Entrustment e1 = entrustmentMapper.selectEntrustmentNumber(entrustmentNumber);
			if (e1 == null || e1.getEntrustmentNumber() == null) {
				logger.debug("该委托单号不存在");
				return new Response<>("00001111", "您输入的委托单号不存在", null);
			}
		} else {
			if (sampleBatch != null && sampleBatch != 0) {
				Entrustment e1 = entrustmentMapper.selectEntrustByBatchNum(sampleBatch);
				if (e1 == null || e1.getEntrustmentNumber() == null || "".equals(e1.getEntrustmentNumber())) {
					logger.debug("该委托单号不存在");
					return new Response<>("00001111", "该批次号尚未添加委托单或者该批次号不存在", null);
				} else {
					entrustmentNumber = e1.getEntrustmentNumber();
				}
			}
		}
		WireRope wireRope = wireRopeMapper.selectByEnsNum(entrustmentNumber);
		if (wireRope != null) {
			if ("100".equals(wireRope.getState())) {
				return new Response<WireRope>("00001111", "该报告已经提交", null);
			} else {
				List<WireData> list = wireDataMapper.selectByEnNum(entrustmentNumber);
				if (list != null) {
					wireRope.setWireDataList(list);
				} else {
					wireRope.setWireDataList(null);
				}
				return new Response<WireRope>(wireRope);
			}

		} else {
			return new Response<>("00001111", "当前委托单号或者批次号尚未创建钢丝绳报告", null);
		}
	}

	public Response<PageResult> selectWireRopeList(String producerNumber, String judgeStandard, Integer offset,
			Integer limit) {
		if (offset == null) {
			offset = 1;
		}
		if (limit == null) {
			limit = 20;
		}
		WireRope w = new WireRope();
//		w.setReportNumber(reportNumber);
		w.setProducerNumber(producerNumber);
		w.setJudgeStandard(judgeStandard);
		PageHelper.startPage(offset, limit);
		List<WireRope> list = wireRopeMapper.selectWireRopeList(w);
		PageInfo<WireRope> p = new PageInfo<>(list);
		return new Response<PageResult>(new PageResult(limit, offset, p.getTotal(), list));

	}

	/**
	 * 调入数据
	 */
	public Response<List<WireData>> selectWireDataList1(WireRope wireRope) {
		String entrustmentNumber = wireRope.getEnstrustmentNumber();
		if (entrustmentNumber == null || "".equals(entrustmentNumber)) {
			return new Response<List<WireData>>("00001111", "委托单号未输入", null);
		}
		WireRope wr = wireRopeMapper.selectByEnsNum(entrustmentNumber);
		if (wr != null) {
			exchange(wr, wireRope);
			System.out.println("***" + wr);
		} else {
			wr = wireRope;
		}

		/**
		 * 钢丝绳数据表里没有，则去试验记录表中查询并组合
		 */
//		if(wireList==null||wireList.size()==0) {

		// 根据委托单号查询实验记录表
		// 查询委托单号查询批次号
		Entrustment ee = entrustmentMapper.selectEntrustmentNumber(entrustmentNumber);
		if (ee.getSampleBatch() == null || ee.getSampleBatch() == 0) {
			logger.info("批次号不存在");
			return new Response<>("00005290", "批次号不存在", null);
		}
		// 根据委托单号查询实验记录表（改为批次号）
		List<SampleRecord> sampleList = sampleRecordMapper.selectData(ee.getSampleBatch());
//			List<SampleRecord> sampleList=sampleRecordMapper.selectSampleData(entrustmentNumber);
		if (sampleList.size() <= 0) {
			logger.info("委托单不存在此实验记录");
			return new Response<>("00005290", "委托单不存在实验记录", null);
		}
		// 将试验数据组合成钢丝绳数据
		List<WireData> list = comboWireData1(sampleList, entrustmentNumber);
		/**
		 * 拆分钢丝公称直径
		 */
		List<String[]> l = new ArrayList<>();
		String cl = wireRope.getTrialClass();
		if (cl == null || "".equals(cl)) {
			cl = wr.getTrialClass();
		}
		if (cl != null && !"".equals(cl)) {
			String[] str = cl.split(",");
			for (int i = 0; i < str.length; i++) {
				String s = str[i];
				String[] ss = s.split("\\*");
				String[] sa = ss[1].split("/");
				ss[1] = sa[0];
				l.add(ss);
			}
		} else {
			return new Response<List<WireData>>("00001111", "请输入试验钢丝类别", null);
		}
		int size = 0;// 试验钢丝的总根数
		for (int i = 0; i < l.size(); i++) {
			String[] nd = l.get(i);
			Integer num1 = Integer.parseInt(nd[1]);
			size += num1;
		}
		if (list != null && list.size() > 0) {
			if (list.size() != size) {
				logger.debug("试验钢丝类别填写错误，请检查");
				return new Response<List<WireData>>("00001111", "试验钢丝类别填写错误，请检查", null);
			} else {
				/**
				 * 先到钢丝绳数据表里查询,若已有数据则先将数据删除
				 */
				List<WireData> wireList = wireDataMapper.selectByEnNum(entrustmentNumber);
				if (wireList != null && wireList.size() > 0) {
					int i = wireDataMapper.deleteByEntrustment(entrustmentNumber);
					if (i >= 0) {
						wireList = null;
					}
				}
				if (wireList == null || wireList.size() <= 0) {
					wireList = new ArrayList<WireData>();
					int n = 1;// 每种直径额钢丝根数
					int m = 0;
					for (int i = 0; i < list.size(); i++) {
						WireData wireData = new WireData();
						WireData s1 = null;
						if (l.size() > 0) {
							String[] nd = l.get(m);
							double diamate = Double.parseDouble(nd[0]);
							Integer strengthL = 0;
							if (nd.length > 2) {
								strengthL = Integer.parseInt(nd[2]);
							}
							s1 = list.get(i);
							s1.setnDiamete(diamate);
							s1.setStrengthLevel(strengthL);
							Integer num = Integer.parseInt(nd[1]);
							if (n == num) {
								n = 1;
								m++;
							} else {
								n++;
							}
						}
//								double d = (Arith.div(s1.getBreakTensile1(), Arith.mul(Math.PI, Arith.mul(s1.getnDiamete1()/2, s1.getnDiamete1()/2))))*1000;
//								s1.setTensileStrength((int) Math.round(d));
						wireData = s1;
						wireList.add(wireData);
					}
					try {
						int j = wireRopeMapper.updateByenstrustmentNumber(wr);
//							if(list==null||list.size()==0) {
						int i = wireDataMapper.insertWireDataBatch(wireList);
//							}else {
						System.out.println("************");
//								int i=wireDataMapper.updateWireDataBatch(wireList);
//							}
						return new Response<List<WireData>>(wireList);
					} catch (Exception e) {
						logger.error("添加钢丝绳数据失败,{}", e.getLocalizedMessage());
						e.printStackTrace();
						throw new BaseException(ExceptionEnum.WIREROPE_DATA_SAVE_ERROR);
					}
				}
			}
			return new Response<List<WireData>>(null);
		} else {
			logger.debug("试验数据不存在");
			return new Response<List<WireData>>("00001111", "试验数据不存在", null);
		}

//		}

	}

	/**
	 * 将试验记录数据组合成钢丝绳数据
	 * 
	 * @param list
	 * @return
	 */
	private List<WireData> comboWireData1(List<SampleRecord> list, String entrustmentNumber) {
		Map<Integer, WireData> map = new HashMap<Integer, WireData>();
		for (int i = 0; i < list.size(); i++) {
			WireData wireData = new WireData();
			SampleRecord record = list.get(i);
			if (!map.containsKey(record.getSampleNumber())) {
				wireData = new WireData();
			} else {
				wireData = map.get(record.getSampleNumber());
			}
			if ("拉力".equals(record.getType())) {
				wireData.setBreakTensile(record.getExperimentalData());
				wireData.setMachineNumber(record.getMachineNumber());
				wireData.setMachineType(record.getMachineType());
				if (list.size() - 1 > i) {
					SampleRecord s = list.get(i + 1);
					if ("打结拉力".equals(s.getType())) {
						wireData.setKnotTension(s.getExperimentalData());
						i++;
					}
				}
			} else if ("弯曲".equals(record.getType())) {
				wireData.setWindingTimes(record.getExperimentalData().intValue());
			} else if ("扭转".equals(record.getType())) {
				wireData.setTurnTimes(record.getExperimentalData().intValue());
			}
			wireData.setEntrustmentNumber(entrustmentNumber);
			map.put(record.getSampleNumber(), wireData);
		}
		List<WireData> slist = new ArrayList<WireData>(map.values());
		return slist;
	}

	/**
	 * 调整判定标准
	 */
	@Override
	@Transactional
	public Response<WireRope> updateJudgeStandard(String info) {
		JSONObject json = JSON.parseObject(info);
		String entrustmentNumber = json.getString("entrustmentNumber");
		String judgeStandard = json.getString("judgeStandard");
		if (entrustmentNumber == null || "".equals(entrustmentNumber)) {
			return new Response<WireRope>("00001111", "委托单号为空", null);
		}
		if (judgeStandard == null || "".equals(judgeStandard)) {
			return new Response<WireRope>("00001111", "判定标准为空", null);
		}
		try {
			WireRope w = new WireRope();
			w.setEnstrustmentNumber(entrustmentNumber);
			w.setJudgeStandard(judgeStandard);
			w.setMeasureBreakTensile(null);
			w.setMinBreakTensile(null);
			wireRopeMapper.updateJudgeStandard(w);
			List<WireData> list = wireDataMapper.selectByEnNum(entrustmentNumber);
			for (WireData data : list) {
				data.setDiameteJudge("");
				data.setKnotJudge("");
				data.setStrengthJudge("");
				data.setTensileJudge("");
				data.setTurnJudge("");
				data.setWindingJudge("");
				data.setZincJudge("");
				data.setStrengthJudge("");
				wireDataMapper.updateByPrimaryKey(data);
			}
			WireRope wireRope = wireRopeMapper.selectByEnsNum(entrustmentNumber);
			List<WireData> list1 = wireDataMapper.selectByEnNum(entrustmentNumber);
			wireRope.setWireDataList(list1);
			return new Response<WireRope>(wireRope);
		} catch (Exception e) {
			return new Response<WireRope>("00001111", "调整失败", null);
		}

	}

	public static void main(String[] args) {
		String cl = "4.25*18/108,4.10*9/54,3.70*9/54,2.75*9/54,2.35*9/54";
//		if(cl==null||"".equals(cl)) {
//			cl=wr.getTrialClass();
//		}
		if (cl != null && !"".equals(cl)) {
			String[] str = cl.split(",");
			for (int i = 0; i < str.length; i++) {
				String s = str[i];
				String[] ss = s.split("\\*");
				String[] sa = ss[1].split("/");
				ss[1] = sa[0];
//				l.add(ss);
			}
		}
	}

}
