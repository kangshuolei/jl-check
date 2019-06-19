package com.hbsi.steel.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSONObject;
import com.hbsi.dao.EntrustmentDataMapper;
import com.hbsi.dao.EntrustmentMapper;
import com.hbsi.dao.ReverseBendingMapper;
import com.hbsi.dao.SampleRecordMapper;
import com.hbsi.dao.SteelTensileStrengthMapper;
import com.hbsi.dao.SteelWireDataMapper;
import com.hbsi.domain.Entrustment;
import com.hbsi.domain.EntrustmentData;
import com.hbsi.domain.ReverseBending;
import com.hbsi.domain.SampleRecord;
import com.hbsi.domain.SteelTensileStrength;
import com.hbsi.domain.SteelWireData;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
import com.hbsi.steel.entity.SteelWireRecord;
import com.hbsi.steel.service.SteelService;
import com.hbsi.util.Arith;

/**
 * 钢丝报告处理
 * 
 * @author lixuyang
 * @version 1.0，2018-09-29
 *
 */
@Service
public class SteelServiceImpl implements SteelService {

	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private SteelWireDataMapper steelWireDataMapper;// 钢丝
	@Autowired
	private SampleRecordMapper sampleRecordMapper;// 试验记录
	@Autowired
	private EntrustmentDataMapper entrustmentDataMapper;// 委托单数据
	@Autowired
	private EntrustmentMapper entrustmentMapper;// 委托单
	@Autowired
	private SteelTensileStrengthMapper steelTensileStrengthMapper;// 钢丝抗拉强度
	@Autowired
	private ReverseBendingMapper reverseBendingMapper;// 扭转或弯曲

	/**
	 * 调入数据
	 * 
	 * @param entrustmentNum
	 * @return
	 */
	@Transactional
	@Override
	public Response<SteelWireRecord> selectSteelWireDataList(String info) {
		JSONObject json = JSONObject.parseObject(info);
		String entrustmentNumber = json.getString("entrustmentNumber");
		Integer steelNumber = json.getInteger("steelNumber");// 钢丝根数
		if (entrustmentNumber == null || "".equals(entrustmentNumber)) {
			logger.info("委托单号不能为空");
			return new Response<>("00007290", "委托单号不能为空", null);
		}
		if (steelNumber == null || steelNumber == 0) {
			logger.info("钢丝根数不能为空");
			return new Response<>("00007290", "钢丝根数不能为空", null);
		}
		SteelWireRecord re = new SteelWireRecord();// 返回
		/**
		 * 调入数据前，查询机器数据
		 */
		EntrustmentData entData = entrustmentDataMapper.selectEntrustmentData(entrustmentNumber);
		if (entData.getTestClerkNumber() != null && !"".equals(entData.getTestClerkNumber())) {
			re.setTestClerkNUmber(entData.getTestClerkNumber());
		}
		if (entData.getTestMachine() != null && !"".equals(entData.getTestMachine())) {
			re.setMachineNumber(entData.getTestMachine());
		}

		/**
		 * 调入数据前，先查询实验记录表，如果有数据，则根据钢丝根数重新调入。 没有数据，则查询钢丝数据，如果有数据，则显示做过的钢丝数据。
		 */
		// 查询钢丝数据是否已存在
		List<SteelWireData> steelList = steelWireDataMapper.selectByEnNum(entrustmentNumber);
		// 查询委托单号查询批次号
		Entrustment ee = entrustmentMapper.selectEntrustmentNumber(entrustmentNumber);
		if (ee.getSampleBatch() == null || ee.getSampleBatch() == 0) {
			logger.info("批次号不存在");
			return new Response<>("00005290", "批次号不存在", null);
		}
		// 根据委托单号查询实验记录表（改为批次号）
		List<SampleRecord> sampleList = sampleRecordMapper.selectData(ee.getSampleBatch());
		if (sampleList.size() <= 0) {
			if (steelList.size() > 0) {// 存在
				re.setSteelWireDataList(steelList);
			} else {
				logger.info("实验记录不存在");
				return new Response<>("00005290", "实验记录不存在", null);
			}
		}

		/**
		 * 实验记录存在，钢丝数据也存在时，则根据委托单号删除钢丝之前数据，重新调入
		 */
		if (steelList != null && steelList.size() > 0) {
			int i = steelWireDataMapper.deleteByEntrustment(entrustmentNumber);
			if (i >= 0) {
				steelList = null;
			}
		}
		if (steelList == null || steelList.size() == 0) {
			// 根据实验数据组合钢丝数据List
			SteelWireRecord swr = comboWireData(sampleList, entrustmentNumber);
			// 委托单数据（机器数据）
			// 查询表中是否有该委托单数据
			EntrustmentData ed = entrustmentDataMapper.selectEntrustmentData(entrustmentNumber);
			if (ed != null) {
				// 根据委托单号添加数据
				EntrustmentData ed1 = new EntrustmentData();
				ed1.setEntrustmentNumber(entrustmentNumber);
				ed1.setTestClerkNumber(swr.getTestClerkNUmber());
				ed1.setTestMachine(swr.getMachineNumber());
				Integer result = entrustmentDataMapper.updateByPrimaryKeySelective(ed1);
			}
			// 钢丝数据
			List<SteelWireData> list = swr.getSteelWireDataList();
			if (list.size() > steelNumber) {// 判断list长度
				List<SteelWireData> newList = list.subList(0, steelNumber);// 取钢丝根数的数据
				list = newList;// 返回新的list
			}
			if (list.size() > 0) {
				// 查询表中是否有该委托单数据
				List<SteelWireData> l = steelWireDataMapper.selectByEnNum(entrustmentNumber);
				Integer result = null;
				if (l.size() > 0) {
					// 修改
					result = steelWireDataMapper.updateByPrimaryKeySelective(l);
				} else {
					// 新增
					result = steelWireDataMapper.insertSteelData(list);
				}
				if (result > 0) {
					logger.info("查询成功");
				} else {
					logger.error("查询钢丝数据信息异常 异常信息：{}", ExceptionEnum.STEEL_WIRE_SELECT_FAILED);
					throw new BaseException(ExceptionEnum.STEEL_WIRE_SELECT_FAILED);
				}
			}
			re.setMachineNumber(swr.getMachineNumber());
			re.setTestClerkNUmber(swr.getTestClerkNUmber());
			re.setSteelWireDataList(list);
		}
		return new Response<SteelWireRecord>(re);
	}

	/**
	 * 将实验记录数据组合成钢丝数据
	 * 
	 * @param list
	 * @param steelNumber
	 * @return
	 */
	private SteelWireRecord comboWireData(List<SampleRecord> list, String entrustmentNumber) {
//		Map<Integer, SteelWireData> map=new HashMap<Integer,SteelWireData>();
		LinkedHashMap<Integer, SteelWireData> map = new LinkedHashMap<Integer, SteelWireData>();
		StringBuilder clerk = new StringBuilder();
		StringBuilder machine = new StringBuilder();
		for(int i=0;i<list.size();i++) {
			SteelWireData steelWireData=null;
			SampleRecord record=list.get(i);
			if (!map.containsKey(record.getSampleNumber())) {
				steelWireData = new SteelWireData();
			} else {
				steelWireData = map.get(record.getSampleNumber());
			}
			if (!clerk.toString().contains(record.getTestMembersNumber())) {
				clerk.append(record.getTestMembersNumber());
				clerk.append("  ");
			}
			if (!machine.toString().contains(record.getMachineNumber())) {
				machine.append(record.getMachineNumber());
				machine.append("  ");
			}
			if ("拉力".equals(record.getType())) {
				steelWireData.setBreakTensile(record.getExperimentalData());
				steelWireData.setMachineNumber(record.getMachineNumber());
				steelWireData.setMachineType(record.getMachineType());
				if(list.size()-1>i) {
					SampleRecord s=list.get(i+1);
					if("打结拉力".equals(s.getType())) {
						steelWireData.setKnotTension(s.getExperimentalData());
						i++;
					}
				}
				
			} else if ("弯曲".equals(record.getType())) {
				steelWireData.setBendingTimes(record.getExperimentalData().intValue());
			} else if ("扭转".equals(record.getType())) {
				steelWireData.setTorsionTimes(record.getExperimentalData().intValue());
			}
			steelWireData.setEntrustmentNumber(entrustmentNumber);
			map.put(record.getSampleNumber(), steelWireData);
		}
		List<SteelWireData> slist = new ArrayList<SteelWireData>(map.values());
		SteelWireRecord sr = new SteelWireRecord();
		sr.setMachineNumber(machine.toString());
		sr.setTestClerkNUmber(clerk.toString());
		sr.setSteelWireDataList(slist);
		return sr;
	}

	/**
	 * 修改钢丝数据（判定）
	 * 
	 * @param list
	 * @return
	 */
	@Transactional
	@Override
	public Response<List<SteelWireData>> updateSteelWire(List<SteelWireData> list) {
		String standardNum = "J&L 10302-2018";
		if (list.size() <= 0) {
			logger.info("钢丝数据不能为空");
			return new Response<>("00007290", "钢丝数据不能为空", null);
		}
		List<SteelWireData> swList = new ArrayList<>();
		for (int i = 0; i < list.size(); i++) {
			SteelWireData s = new SteelWireData();
			s.setId(list.get(i).getId());
			s.setfDiamete(list.get(i).getfDiamete1());// 成品直径
			s.setStrengthLevel(list.get(i).getStrengthLevel());// 强度级别
			s.setUse(list.get(i).getUse().toUpperCase());// 用途（小写转大写）
			s.setSurface(list.get(i).getSurface().toUpperCase());// 表面状态（小写转大写）
			s.setBreakTensile(list.get(i).getBreakTensile1());// 破断拉力
			s.setTorsionTimes(list.get(i).getTorsionTimes());// 扭转次数
			s.setBendingTimes(list.get(i).getBendingTimes());// 弯曲次数
			s.setKnotTension(list.get(i).getKnotTension());//打结拉力
			if (s.getId() == null || s.getId() == 0) {
				logger.info("id为空");
				return new Response<>("00007290", "id为空", null);
			}
			if (s.getfDiamete1() == null || s.getfDiamete1() == 0) {
				logger.info("成品直径为空");
				return new Response<>("00007290", "成品直径为空", null);
			}
			if (s.getStrengthLevel() == null || "".equals(s.getStrengthLevel())) {
				logger.info("强度级别为空");
				return new Response<>("00007290", "强度级别为空", null);
			}
			if (s.getUse() == null || "".equals(s.getUse())) {
				logger.info("用途为空");
				return new Response<>("00007290", "用途为空", null);
			}
			if (s.getSurface() == null || "".equals(s.getSurface())) {
				logger.info("表面状态为空");
				return new Response<>("00007290", "表面状态为空", null);
			}
			if (s.getBreakTensile1() == null || s.getBreakTensile1() == 0) {
				logger.info("破断拉力为空");
				return new Response<>("00007290", "破断拉力为空", null);
			}
			/**
			 * 计算抗拉强度
			 */
			double d = (Arith.div(s.getBreakTensile1(),
					Arith.mul(Math.PI, Arith.mul(s.getfDiamete1() / 2, s.getfDiamete1() / 2)))) * 1000;
			s.setTensileStrength(Arith.revision(d));// 抗拉强度

			/**
			 * 计算判定结果
			 */
			// 1.查询抗拉强度范围
			SteelTensileStrength sts = new SteelTensileStrength();
			sts.setStandardNum(standardNum);// 标准文件编号
			sts.setRatedStrength(s.getStrengthLevel());// 公称强度
			sts.setUsage(s.getUse());// 用途
			sts.setfDiamete(s.getfDiamete1());// 成品直径
			sts.setStrengthValue(s.getTensileStrength());// 抗拉强度值
			SteelTensileStrength sts1 = steelTensileStrengthMapper.selectSteelTSData(sts);
			logger.info("根据sts:{}查询抗拉强度范围值,结果：{}", sts, sts1);

			if (sts1 == null) {
				logger.info("没有找到抗拉强度的判定数据");
				return new Response<>("00007291", "没有找到抗拉强度的判定数据", null);
			}

			// （0代表合格，1代表重判后合格，2代表不合格）
			int stateX = 0;// 抗拉强度
			int stateY = 0;// 扭转
			int stateZ = 0;// 弯曲
			int stateI = 0;// 打结拉力
			SteelTensileStrength sts2 = null;
			ReverseBending r2 = null;
			ReverseBending b2 = null;

			// 直径小于0.5
			if (s.getfDiamete1() < 0.5) {
				/**
				 * 判定抗拉强度
				 */
				if (s.getTensileStrength() >= sts1.getStrengthValueMin()
						&& s.getTensileStrength() <= sts1.getStrengthValueMax()) {
					// 抗拉强度合格
					stateX = 0;
				} else {
					// 抗拉强度不合格，重新判定
					if (s.getUse().equals("Z")) {// 重要用途，按一般用途重新判定
						sts2 = judgTensileStrength(sts);
						if (s.getTensileStrength() >= sts2.getStrengthValueMin()
								&& s.getTensileStrength() <= sts2.getStrengthValueMax()) {
							// 抗拉强度重判后合格
							stateX = 1;
						} else {
							// 抗拉强度重判后不合格，查询到符合的等级，比对结果
							Map<String, Object> map = new HashMap<>();
							map = resultKnotSts(s, sts, sts1, sts2, stateX);
							sts2 = (SteelTensileStrength) map.get("sts");
							stateX = (int) map.get("state"); 
						}
					} else if (s.getUse().equals("Y")) {// 一般用途，降级判定
						Map<String, Object> map = new HashMap<>();
						map = resultKnotSts(s, sts, sts1, sts2, stateX);
						sts2 = (SteelTensileStrength) map.get("sts");
						stateX = (int) map.get("state"); 
					}
				}
				
				/**
				 * 计算打结率
				 */
				double knotTension=0;
				Integer strengthLevel = Integer.parseInt(s.getStrengthLevel());// 强度级别
				if (s.getKnotTension() != null && s.getKnotTension() != 0) {
					knotTension = s.getKnotTension();// 打结拉力
					double la = Arith.div(
							Arith.mul(Arith.mul(Math.PI, Math.pow(s.getfDiamete1() / 2.0, 2)), (double) strengthLevel),
							1000.0);
					double knotRate = Arith.div(knotTension, la);
					s.setKnotRate(Double.valueOf(Math.round(Arith.mul(knotRate, 100.0))));// 放入打结率
				}
				/**
				 * 判定打结拉力（打结拉力应不小于钢丝公称抗拉强度50%的拉力）
				 */
				double k1 = Arith.mul(Arith.div(
						Arith.mul(Arith.mul(Math.PI, Math.pow(s.getfDiamete1() / 2.0, 2)), (double) strengthLevel),
						1000.0), 0.5);
				if (knotTension < k1) {
					// 不合格
					stateI = 2;
				} else {
					stateI = 0;
				}

				/**
				 * 综合结果
				 */
				if (stateX == 0 && stateI == 0) {
					s.setJudge(s.getStrengthLevel() + s.getUse());// 判定
				} else if (stateX == 1 && stateI == 0) {
					s.setJudge(sts2.getRatedStrength() + sts2.getUsage());// 判定
				} else {
					s.setJudge("不合格");
				}

			} else {// 直径大于0.5
				if (s.getTorsionTimes() == null || s.getTorsionTimes() == 0) {
					logger.info("扭转次数为空");
					return new Response<>("00007290", "扭转次数为空", null);
				}
				if (s.getBendingTimes() == null || s.getBendingTimes() == 0) {
					logger.info("弯曲次数为空");
					return new Response<>("00007290", "弯曲次数为空", null);
				}
				// 2.查询扭转值
				ReverseBending r = new ReverseBending();
				r.setStandardNum(standardNum);// 标准文件编号
				r.setRatedStrength(s.getStrengthLevel());// 公称强度
				if (s.getSurface().equals("U") || s.getSurface().equals("B")) {
					r.setSurfaceState("UB");// 表面状态
				} else {
					r.setSurfaceState(s.getSurface());
				}
				r.setUsage(s.getUse());// 用途
				r.setType("R");// 扭转
				r.setfDiamete(s.getfDiamete1());// 成品直径
				r.setRob(s.getTorsionTimes());// 扭转次数
				System.out.println(r);
				ReverseBending r1 = reverseBendingMapper.selectRBData(r);
				logger.info("根据r:{}查询扭转值,结果：{}", r, r1);
				if (r1 == null) {
					logger.info("没有找到扭转的判定数据");
					return new Response<>("00007292", "没有找到扭转的判定数据", null);
				}
				
				// 3.查询弯曲值
				ReverseBending b = new ReverseBending();
				b.setStandardNum(standardNum);// 标准文件编号
				b.setRatedStrength(s.getStrengthLevel());// 公称强度
				if (s.getSurface().equals("U") || s.getSurface().equals("B")) {
					b.setSurfaceState("UB");// 表面状态
				} else {
					b.setSurfaceState(s.getSurface());
				}
				b.setUsage(s.getUse());// 用途
				b.setType("B");// 弯曲
				b.setfDiamete(s.getfDiamete1());// 成品直径
				b.setRob(s.getBendingTimes());// 弯曲次数
				ReverseBending b1 = reverseBendingMapper.selectRBData(b);
				logger.info("根据b:{}查询弯曲值,结果：{}", b, b1);
				if (b1 == null) {
					logger.info("没有找到弯曲的判定数据");
					return new Response<>("00007293", "没有找到弯曲的判定数据", null);
				}

				/**
				 * 判定抗拉强度
				 */
				if (s.getTensileStrength() >= sts1.getStrengthValueMin()
						&& s.getTensileStrength() <= sts1.getStrengthValueMax()) {
					stateX = 0;// 抗拉强度合格
				} else {
					// 抗拉强度不合格，重新判定
					if (s.getUse().equals("Z")) {// 重要用途，按一般用途重新判定
						sts2 = judgTensileStrength(sts);
						if(sts2 == null) {
							stateX = 2;
						}else {
							if (s.getTensileStrength() >= sts2.getStrengthValueMin() && s.getTensileStrength() <= sts2.getStrengthValueMax()) {
								// 抗拉强度重判后合格
								r2 = judgReverse(r);// 判定扭转是否符合一般用途
								b2 = judgBending(b);// 判定弯曲是否符合一般用途
								if (s.getTorsionTimes() >= r2.getValueRob() && s.getBendingTimes() >= b2.getValueRob()) {
									stateX = 1;
								} else {
									// 判定扭转降级后是否符合
									r2 = judgReverse2(r, sts2.getRatedStrength());
									// 判定弯曲降级后是否符合
									b2 = judgBending2(b, sts2.getRatedStrength());
									//防止将完级后，没有数据
									if(r2==null || b2==null) {
								    	stateX = 2;
								    }else {
								    	if (s.getTorsionTimes() >= r2.getValueRob() && s.getBendingTimes() >= b2.getValueRob()) {
											stateX = 1;
										} else {
											stateX = 2;
										}
								    }
									
								}
							} else {
								// 抗拉强度重判后不合格，查询到符合的等级，比对结果
								Map<String, Object> map = new HashMap<>();
								map = resultSts(s, sts, sts1, sts2, r, r2, b, b2, stateX);
								sts2 = (SteelTensileStrength) map.get("sts");
								stateX = (int) map.get("state");
							}
						}
						
					} else if (s.getUse().equals("Y")) {// 一般用途，降级判定
						Map<String, Object> map = new HashMap<>();
						map = resultSts(s, sts, sts1, sts2, r, r2, b, b2, stateX);
						sts2 = (SteelTensileStrength) map.get("sts");
						stateX = (int) map.get("state");
					}
				}

				/**
				 * 判定扭转
				 */
				if (s.getTorsionTimes() >= r1.getValueRob()) {
					stateY = 0;// 扭转合格
				} else {
					// 扭转不合格，重新判定
					if (s.getUse().equals("Z")) {// 重要用途，按一般用途重新判定
						r2 = judgReverse(r);
						if(r2 == null) {
							stateY = 2;
						}else {
							// 按一般用途，判定是否符合
							if (s.getTorsionTimes() >= r2.getValueRob()) {
								// 扭转合格
								sts2 = judgTensileStrength(sts);// 判定抗拉强度是否符合一般用途
								b2 = judgBending(b);// 判定弯曲是否符合一般用途
								if (s.getTensileStrength() >= sts2.getStrengthValueMin()
										&& s.getTensileStrength() <= sts2.getStrengthValueMax()
										&& s.getBendingTimes() >= b2.getValueRob()) {
									stateY = 1;
								} else {
									sts2 = judgTensileStrength2(sts, r2.getRatedStrength());// 判定抗拉强度，是否符合前扭转值对应的公称强度
									b2 = judgBending2(b, r2.getRatedStrength());// 判定弯曲，是否符合当前扭转值对应的公称强度
								    //防止降完级后，没有数据
									if(sts2==null || b2==null) {
								    	stateY = 2;
								    }else {
								    	if (s.getTensileStrength() >= sts2.getStrengthValueMin()
												&& s.getTensileStrength() <= sts2.getStrengthValueMax()
												&& s.getBendingTimes() >= b2.getValueRob()) {
											stateY = 1;
										}else {
											stateY = 2;
										}
								    }
									
								}
							} else {
								// 扭转不合格，查询到符合的公称强度级别，判定其他项是否也符合
								Map<String, Object> map = new HashMap<>();
								map = resultR(s, sts, sts2, r, r2, b, b2, stateY);
								r2 = (ReverseBending) map.get("r");
								stateY = (int) map.get("state");
							}
						}
						
					} else if (s.getUse().equals("Y")) {// 一般用途，降级判定
						Map<String, Object> map = new HashMap<>();
						map = resultR(s, sts, sts2, r, r2, b, b2, stateY);
						r2 = (ReverseBending) map.get("r");
						stateY = (int) map.get("state");
					}
				}

				/**
				 * 判定弯曲
				 */
				if (s.getBendingTimes() >= b1.getValueRob()) {
					// 弯曲合格
					stateZ = 0;
				} else {
					// 弯曲不合格，重新判定
					if (s.getUse().equals("Z")) {// 重要用途，按一般用途重新判定
						b2 = judgBending(b);
						if(b2 == null) {
							stateZ = 2;
						}else {
							if (s.getBendingTimes() >= b2.getValueRob()) {
								// 弯曲合格
								sts2 = judgTensileStrength(sts);// 判定抗拉强度是否符合一般用途
								r2 = judgReverse(r);// 判定扭转是否符合一般用途
								if (s.getTensileStrength() >= sts2.getStrengthValueMin()
										&& s.getTensileStrength() <= sts2.getStrengthValueMax()
										&& s.getTorsionTimes() >= r2.getValueRob()) {
									stateZ = 1;
								} else {
									sts2 = judgTensileStrength2(sts, b2.getRatedStrength());// 判定抗拉强度降级后是否符合
									r2 = judgReverse2(r, b2.getRatedStrength());// 判定扭转降级后是否符合
									//防止降完级后，没有数据
									if(sts2==null || r2==null) {
										stateZ = 2;
									}else {
										if (s.getTensileStrength() >= sts2.getStrengthValueMin()
												&& s.getTensileStrength() <= sts2.getStrengthValueMax()
												&& s.getTorsionTimes() >= r2.getValueRob()) {
											stateZ = 1;
										} else {
											stateZ = 2;
										}
									}
									
								}

							} else {
								// 弯曲不合格，查询到符合的公称强度级别，比对结果
								Map<String, Object> map = new HashMap<>();
								map = resultB(s, sts, sts2, r, r2, b, b2, stateZ);
								b2 = (ReverseBending) map.get("b");
								stateZ = (int) map.get("state");
							}
						}
						
					} else if (s.getUse().equals("Y")) {// 一般用途，降级判定
						Map<String, Object> map = new HashMap<>();
						map = resultB(s, sts, sts2, r, r2, b, b2, stateZ);
						b2 = (ReverseBending) map.get("b");
						stateZ = (int) map.get("state");
					}
				}
				/**
				 * 综合结果
				 */
				if (stateX == 0 && stateY == 0 && stateZ == 0) {
					s.setJudge(s.getStrengthLevel() + s.getUse());// 判定
				} else if (stateX == 1 && stateY == 0 && stateZ == 0) {
					s.setJudge(sts2.getRatedStrength() + sts2.getUsage());// 判定
				} else if (stateX == 0 && stateY == 1 && stateZ == 0) {
					s.setJudge(r2.getRatedStrength() + r2.getUsage());// 判定
				} else if (stateX == 0 && stateY == 0 && stateZ == 1) {
					s.setJudge(b2.getRatedStrength() + b2.getUsage());// 判定
				} else if (stateX == 2 || stateY == 2 || stateZ == 2) {
					s.setJudge("不合格");
				}else if(stateX == 1 && stateY == 1 && stateZ == 0) {
					int n = Integer.parseInt(sts2.getRatedStrength());
					int m = Integer.parseInt(r2.getRatedStrength());
					int min = (n<m)?n:m;
					if(min==n) {
						s.setJudge(sts2.getRatedStrength() + sts2.getUsage());// 判定
					}else if(min==m) {
						s.setJudge(r2.getRatedStrength() + r2.getUsage());// 判定
					}
				}else if(stateX == 0 && stateY == 1 && stateZ == 1) {
					int m = Integer.parseInt(r2.getRatedStrength());
					int q = Integer.parseInt(b2.getRatedStrength());
					int min = (m<q)?m:q;
					if(min==m) {
						s.setJudge(r2.getRatedStrength() + r2.getUsage());// 判定
					}else if(min==q) {
						s.setJudge(b2.getRatedStrength() + b2.getUsage());// 判定
					}
				}else if(stateX == 1 && stateY == 0 && stateZ == 1) {
					int n = Integer.parseInt(sts2.getRatedStrength());
					int q = Integer.parseInt(b2.getRatedStrength());
					int min = (n<q)?n:q;
					if(min==n) {
						s.setJudge(sts2.getRatedStrength() + sts2.getUsage());// 判定
					}else if(min==q) {
						s.setJudge(b2.getRatedStrength() + b2.getUsage());// 判定
					}
				}else if(stateX == 1 && stateY == 1 && stateZ == 1) {
					int n = Integer.parseInt(sts2.getRatedStrength());
					int m = Integer.parseInt(r2.getRatedStrength());
					int q = Integer.parseInt(b2.getRatedStrength());
					int min = (n<m)?n:m;
					min = (min<q)?min:q;
					if(min==n) {
						s.setJudge(sts2.getRatedStrength() + sts2.getUsage());// 判定
					}else if(min==m) {
						s.setJudge(r2.getRatedStrength() + r2.getUsage());// 判定
					}else if(min==q) {
						s.setJudge(b2.getRatedStrength() + b2.getUsage());// 判定
					}
				}
			}

			swList.add(s);
		}
		try {
			Integer result = steelWireDataMapper.updateByPrimaryKeySelective(swList);
			logger.info("根据swList:{}修改钢丝数据信息,结果：{}", swList, result);
		} catch (Exception e) {
			logger.error("修改钢丝数据信息异常 异常信息：{}", e.getMessage());
			throw new BaseException(ExceptionEnum.STEEL_WIRE_UPDATE_FAILED);
		}
		return new Response<>(swList);
	}

	/**
	 * 按一般用途判定抗拉强度
	 * 
	 * @param sts
	 * @return
	 */
	private SteelTensileStrength judgTensileStrength(SteelTensileStrength sts) {
		sts.setUsage("Y");
		SteelTensileStrength sts2 = steelTensileStrengthMapper.selectSteelTSData(sts);
		logger.info("根据sts:{}按一般用途查询抗拉强度范围值,结果：{}", sts, sts2);
		return sts2;
	}

	/**
	 * 降级后判定抗拉强度
	 * 
	 * @param sts
	 * @return
	 */
	private SteelTensileStrength judgTensileStrength2(SteelTensileStrength sts, String ratedStrength) {
		sts.setRatedStrength(ratedStrength);
		sts.setUsage("Y");
		SteelTensileStrength sts2 = steelTensileStrengthMapper.selectSteelTSData(sts);
		logger.info("根据sts:{}降级后查询抗拉强度范围值,结果：{}", sts, sts2);
		return sts2;
	}

	/**
	 * 按一般用途判定扭转
	 * 
	 * @param r
	 * @return
	 */
	private ReverseBending judgReverse(ReverseBending r) {
		r.setUsage("Y");
		ReverseBending r2 = reverseBendingMapper.selectRBData(r);
		logger.info("根据r:{}按一般用途查询扭转值,结果：{}", r, r2);
		return r2;
	}

	/**
	 * 降级后判定扭转
	 * 
	 * @param r
	 * @return
	 */
	private ReverseBending judgReverse2(ReverseBending r, String ratedStrength) {
		r.setRatedStrength(ratedStrength);// 公称强度
		r.setUsage("Y");
		ReverseBending r2 = reverseBendingMapper.selectRBData(r);
		logger.info("根据r:{}降级后查询当前扭转值对应强度级别,结果：{}", r, r2);
		return r2;
	}

	/**
	 * 按一般用途判定弯曲
	 * 
	 * @param b
	 * @return
	 */
	private ReverseBending judgBending(ReverseBending b) {
		b.setUsage("Y");
		ReverseBending b2 = reverseBendingMapper.selectRBData(b);
		logger.info("根据b:{}按一般用途查询弯曲值,结果：{}", b, b2);
		return b2;
	}

	/**
	 * 降级后判定弯曲
	 * 
	 * @param b
	 * @return
	 */
	private ReverseBending judgBending2(ReverseBending b, String ratedStrength) {
		b.setRatedStrength(ratedStrength);
		b.setUsage("Y");
		ReverseBending b2 = reverseBendingMapper.selectRBData(b);
		logger.info("根据b:{}降级后查询当前弯曲值对应强度级别,结果：{}", b, b2);
		return b2;
	}
	
	/**
	 * 抗拉强度判定（仅用于小于0.5的打结拉力）
	 * @param s
	 * @param sts
	 * @param sts2
	 * @param stateX
	 * @return
	 */
	private Map<String, Object> resultKnotSts(SteelWireData s, SteelTensileStrength sts, SteelTensileStrength sts1, SteelTensileStrength sts2, int stateX) {
		// 抗拉强度重判后不合格，查询符合的所有数据List
		List<SteelTensileStrength> stslist = steelTensileStrengthMapper.selectSTSDataList(sts);
		logger.info("根据sts:{}查询满足当前抗拉强度级别的所有数据,结果：{}", sts, stslist.size());
		// 按照抗拉强度升序
		Collections.sort(stslist, new Comparator<SteelTensileStrength>() {

			@Override
			public int compare(SteelTensileStrength o1, SteelTensileStrength o2) {
				int a = 0;
				if(s.getTensileStrength() > sts1.getStrengthValueMax()) {
					a = o1.getRatedStrength().compareTo(o2.getRatedStrength());//升序
				}else if(s.getTensileStrength() < sts1.getStrengthValueMin()){
					a = o2.getRatedStrength().compareTo(o1.getRatedStrength());//降序
				}
				return a;
			}
		});
		if(stslist.size() <= 0) {
			 stateX = 2;
		}else {
			for(int j = 0;j<stslist.size(); j++) {
				//为了拼接判定结果，需要给sts2赋值
				sts.setDiameteMin(stslist.get(j).getDiameteMin());
				sts.setDiameteMax(stslist.get(j).getDiameteMax());
				sts.setRatedStrength(stslist.get(j).getRatedStrength());
//				sts.setUsage("Y");
				sts2 = sts;
				if (s.getTensileStrength() >= sts2.getStrengthValueMin() && s.getTensileStrength() <= sts2.getStrengthValueMax()) {
					stateX = 1;
					break;
				} else {
					stateX = 2;
				}
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("state", stateX);
		map.put("sts", sts2);
		return map;
	}
	
	/**
	 * 抗拉强度判定
	 * @param s
	 * @param sts
	 * @param sts2
	 * @param r
	 * @param r2
	 * @param b
	 * @param b2
	 * @param stateX
	 * @return
	 */
	private Map<String, Object> resultSts(SteelWireData s, SteelTensileStrength sts,SteelTensileStrength sts1, SteelTensileStrength sts2,ReverseBending r, ReverseBending r2,ReverseBending b, ReverseBending b2, int stateX){ 
		//查询符合的所有数据List
		List<SteelTensileStrength> stslist = steelTensileStrengthMapper.selectSTSDataList(sts);
		logger.info("根据sts:{}查询满足当前抗拉强度级别的所有数据,结果：{}", sts, stslist.size());
		// 按照抗拉强度排序
		Collections.sort(stslist, new Comparator<SteelTensileStrength>() {

			@Override
			public int compare(SteelTensileStrength o1, SteelTensileStrength o2) {
				int a = 0;
				if(s.getTensileStrength() > sts1.getStrengthValueMax()) {
					a = o1.getRatedStrength().compareTo(o2.getRatedStrength());//升序
				}else if(s.getTensileStrength() < sts1.getStrengthValueMin()){
					a = o2.getRatedStrength().compareTo(o1.getRatedStrength());//降序
				}
				return a;
			}
		});
		if(stslist.size() <= 0) {
			 stateX = 2;
		}else {
			for(int j = 0;j<stslist.size(); j++) {
				//为了拼接判定结果，需要给sts2赋值
				sts.setRatedStrength(stslist.get(j).getRatedStrength());
//				sts.setUsage("Y");
				sts2 = sts;
				r2 = judgReverse2(r, sts2.getRatedStrength());// 判定扭转降级后是否符合
				b2 = judgBending2(b, sts2.getRatedStrength());// 判定弯曲降级后是否符合
				if(r2==null || b2==null) {
					stateX = 2;
				}else {
					if (s.getTorsionTimes() >= r2.getValueRob()
							&& s.getBendingTimes() >= b2.getValueRob()) {
						stateX = 1;
						break;
					} else {
						stateX = 2;
					}
				}
				
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("state", stateX);
		map.put("sts", sts2);
		return map;
	}
	
	/**
	 * 扭转判定
	 * @param s
	 * @param sts
	 * @param sts2
	 * @param r
	 * @param r2
	 * @param b
	 * @param b2
	 * @param stateY
	 * @return
	 */
	private Map<String, Object> resultR(SteelWireData s, SteelTensileStrength sts, SteelTensileStrength sts2,ReverseBending r, ReverseBending r2,ReverseBending b, ReverseBending b2, int stateY) {
		//查询符合的所有数据List
		List<ReverseBending> rlist = reverseBendingMapper.selectRBDataList(r);
		logger.info("根据r:{}查询满足当前扭转值的所有数据,结果：{}", r, rlist.size());
		// 按照抗拉强度升序
		Collections.sort(rlist, new Comparator<ReverseBending>() {

			@Override
			public int compare(ReverseBending o1, ReverseBending o2) {
				return o1.getRatedStrength().compareTo(o2.getRatedStrength());
			}
		});
		if (rlist.size() <= 0) {
			stateY = 2;
		} else {
			for (int j = 0; j < rlist.size(); j++) {
				// 为了拼接判定结果，需要给r2赋值
				r.setRatedStrength(rlist.get(j).getRatedStrength());
//			    r.setUsage("Y");
				r2 = r;
				sts2 = judgTensileStrength2(sts, r2.getRatedStrength());// 判定抗拉强度，是否符合前扭转值对应的公称强度
				b2 = judgBending2(b, r2.getRatedStrength());// 判定弯曲，是否符合当前扭转值对应的公称强度
				if(sts2==null || b2==null) {
					stateY = 2;
				}else {
					if (s.getTensileStrength() >= sts2.getStrengthValueMin()
							&& s.getTensileStrength() <= sts2.getStrengthValueMax()
							&& s.getBendingTimes() >= b2.getValueRob()) {
						stateY = 1;
						break;
					} else {
						stateY = 2;
					}
				}
				
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("state", stateY);
		map.put("r", r2);
		return map;
	}
	
	/**
	 * 弯曲判定
	 * @param s
	 * @param sts
	 * @param sts2
	 * @param r
	 * @param r2
	 * @param b
	 * @param b2
	 * @param stateZ
	 * @return
	 */
	private Map<String, Object> resultB(SteelWireData s, SteelTensileStrength sts, SteelTensileStrength sts2,ReverseBending r, ReverseBending r2,ReverseBending b, ReverseBending b2, int stateZ) {
		//查询符合的所有数据List
		List<ReverseBending> blist = reverseBendingMapper.selectRBDataList(b);
		logger.info("根据b:{}查询满足当前弯曲值的所有数据,结果：{}", b, blist.size());
		// 按照抗拉强度升序
		Collections.sort(blist, new Comparator<ReverseBending>() {

			@Override
			public int compare(ReverseBending o1, ReverseBending o2) {
				return o1.getRatedStrength().compareTo(o2.getRatedStrength());
			}
		});
		if (blist.size() <= 0) {
			stateZ = 2;
		} else {
			for (int j = 0; j < blist.size(); j++) {
				// 为了拼接判定结果，需要给b2赋值
				b.setRatedStrength(blist.get(j).getRatedStrength());
//				b.setUsage("Y");
				b2 = b;
				sts2 = judgTensileStrength2(sts, b2.getRatedStrength());// 判定抗拉强度降级后是否符合
				r2 = judgReverse2(r, b2.getRatedStrength());// 判定扭转降级后是否符合
				if(sts2==null || r2==null) {
					stateZ = 2;
				}else {
					if (s.getTensileStrength() >= sts2.getStrengthValueMin()
							&& s.getTensileStrength() <= sts2.getStrengthValueMax()
							&& s.getTorsionTimes() >= r2.getValueRob()) {
						stateZ = 1;
					} else {
						stateZ = 2;
					}
				}
				
			}
		}
		Map<String, Object> map = new HashMap<>();
		map.put("state", stateZ);
		map.put("b", b2);
		return map;
	}

}
