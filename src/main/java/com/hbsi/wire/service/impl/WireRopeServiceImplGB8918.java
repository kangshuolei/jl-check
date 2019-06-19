package com.hbsi.wire.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hbsi.dao.DiameterDeviationMapper;
import com.hbsi.dao.ReverseBendingMapper;
import com.hbsi.dao.StrengthDeviationMapper;
import com.hbsi.dao.WireAttributesGBT89182006Mapper;
import com.hbsi.dao.WireDataMapper;
import com.hbsi.dao.WireRopeMapper;
import com.hbsi.domain.DiameterDeviation;
import com.hbsi.domain.ReverseBending;
import com.hbsi.domain.StrengthDeviation;
import com.hbsi.domain.WireAttributesGBT89182006;
import com.hbsi.domain.WireData;
import com.hbsi.domain.WireRope;
import com.hbsi.entity.WireRopeData;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
import com.hbsi.util.Arith;
import com.hbsi.wire.service.WireRopeService;
import com.hbsi.wire.service.WireRopeServiceGB8918;

/**
 * GBT8918-2006
 * @author cxh
 *
 */
@Service
public class WireRopeServiceImplGB8918 implements WireRopeServiceGB8918{
	private Logger logger=LoggerFactory.getLogger(WireRopeServiceImplGB8918.class);
	@Autowired
	private WireAttributesGBT89182006Mapper attMapper;
	@Autowired
	private StrengthDeviationMapper sdMapper;
	@Autowired
	private ReverseBendingMapper reverseBendingMapper;
	@Resource
	private WireRopeService wireRopeService;
	@Autowired
	private DiameterDeviationMapper diameterDeviationMapper;
	@Autowired
	private WireRopeMapper wireRopeMapper;
	@Autowired
	private WireDataMapper wireDataMapper;
	/**
	 * 钢丝绳判定
	 */
	public Response<WireRope> judgeWire(WireRope wireRope) {
		if(wireRope==null) {
			logger.info("传入数据为空");
		}else {
			/*计算钢丝破断拉力*/
			double wireBreak=wireRopeService.calWireBreakTensile(wireRope);
//			wireBreak=Math.round(wireBreak*100)/100;
			wireBreak=Arith.getValue(wireBreak);
			/**
			 * 计算钢丝绳最小破断拉力总和
			 * K*D*D*R0/1000
			 * */
			//查询钢丝绳系数
			WireAttributesGBT89182006 att=attMapper.selectByStructure(wireRope.getStructure());
			double minBreakForce=att.getMinimumBreakingForce();
			double diamate=Double.parseDouble(wireRope.getSpecification());//钢丝绳直径
			double strengthLevel=Double.parseDouble(wireRope.getStrengthLevel());
			double breakValue=Arith.div(Arith.mul(Arith.mul(minBreakForce, Math.pow(diamate, 2)), (double)strengthLevel),1000.0);
			double standardBreak=Arith.getValue(breakValue);//需要先修约，再计算总和
			
			double wireBreakAll=standardBreak * att.getTanningLossFactor();//钢丝绳最小破断拉力总和
//			wireBreakAll=Math.round(wireBreakAll*100)/100;
			wireBreakAll=Arith.getValue(wireBreakAll);
			wireRope.setMinBreakTensile(wireBreakAll);
			wireRope.setMeasureBreakTensile(wireBreak);
			/*修改钢丝绳数据*/
			wireRopeService.saveWireData(wireRope);
			
			Map<Double, int[]> resultMap=judgeWireRope(wireRope);
			//sb中的数据存储到evaluation中
			String sb=new String();
			//sb1中的数据存储到memo中
			StringBuilder sb1=new StringBuilder();
			int[] all=resultMap.get(0d);
			Iterator<Double> it=resultMap.keySet().iterator();
			int num=0;
			if(wireBreak<wireBreakAll) {
				sb="不合格";
				sb1.append("实测钢丝破断拉力小于最小破断拉力总和,");
			}
			while(it.hasNext()) {
				double ndiamate=it.next();
				if(ndiamate!=0&&ndiamate!=0.0) {
					int[] re=resultMap.get(ndiamate);
					int i=0;
					for(;i<re.length;i++) {
						if(re[i]!=0) {//有低值或者不合格则跳出本层循环
							break;
						}
					}
					if(i>=re.length) {
						continue;//若该公称直径下没有不合格或者低值则跳出本次循环
					}else {
						if("部分试验".equals(wireRope.getStockSplitMethod())) {
							if(re[5]>1) {
								num++;
							}
						}else if("100%试验".equals(wireRope.getStockSplitMethod())) {
							int n=re[6];
							if(re[5]>Arith.mul((double)n, 0.04)) {
								num++;
							}
						}
						//若该公称直径下有不合格则显示
						if(re[0]>0||re[2]>0||re[3]>0||re[4]>0) {
							sb1.append("Φ").append(String.format("%.2f", ndiamate)).append("钢丝[");
							if(re[0]>0) {
								sb1.append("直径有").append(re[1]).append("根不合格 ");
							}
							if(re[2]>0) {
								sb1.append("抗拉强度有").append(re[2]).append("根不合格 ");
							}
							if(re[3]>0) {
								sb1.append("弯曲次数有").append(re[3]).append("根不合格 ");
							}
							if(re[4]>0) {
								sb1.append("扭转有").append(re[4]).append("根不合格");
							}
							sb1.append("] ");
						}
					}
				}
			}
			if(num>0) {
				sb="不合格";
			}else {
				sb="合格";
			}
			wireRope.setEvaluation(sb.toString());
			wireRope.setMemo(sb1.toString());
			try {
				int i=wireRopeMapper.updateRopeEvaluatuion(wireRope);
			} catch (Exception e) {
				logger.error("钢丝绳判定结果保存失败,{}",e.getLocalizedMessage());
				throw new BaseException(ExceptionEnum.WIREROPE_SAVE_ERROR);
			}
		}
		return new Response<WireRope>(wireRope);
	}
	
	/**
	 * 钢丝绳综合判定
	 * @param wireRope
	 * @return
	 * 判断直径   抗拉强度    反复弯曲次数    扭转次数    打结拉伸
	 */
	private Map<Double, int[]> judgeWireRope(WireRope wireRope){
		//查询钢丝绳系数
		WireAttributesGBT89182006 att=attMapper.selectByStructure(wireRope.getStructure());
		
		//钢丝绳的股类型
		String shape=att.getShape();
		String structure=wireRope.getStructure();
		if(structure.contains("V")||structure.contains("Q")) {
			shape="异形股";
		}else {
			shape="圆形股";
		}
		List<WireData> dataList=wireRope.getWireDataList();
		/**
		 * 判断钢丝绳表面状态
		 */
		String surface1=wireRope.getSurfaceState();
		String surface="";
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
		//钢丝绳的抗拉强度级别
		Integer strengthLevel=Integer.parseInt(wireRope.getStrengthLevel());
		/**
		 * 获取试验钢丝类别公称抗拉强度
		 */
		Map<Double, Integer> strengthMap=new HashMap<>();
		String cl=wireRope.getTrialClass();
		if(cl==null||"".equals(cl)) {
			WireRope wr=wireRopeMapper.selectByEnsNum(wireRope.getEnstrustmentNumber());
			cl=wr.getTrialClass();
		}
		List<String[]> nlist=new ArrayList<>();
		if(cl!=null&&!"".equals(cl)) {
			
			String[] str=cl.split(",");
			for(int i=0;i<str.length;i++) {
				String s=str[i];
				String[] ss=s.split("\\*");
				String[] sa=ss[1].split("/");
				ss[1]=sa[0];
				nlist.add(ss);
			}
		}else {
			return null;
		}
		for(int i=0;i<nlist.size();i++) {
			String[] nd=nlist.get(i);
			double ndiamate=Double.parseDouble(nd[0]);
			if(nd.length>2) {
				Integer strength=Integer.parseInt(nd[2]);
				strengthMap.put(ndiamate, strength);
			}else {
				strengthMap.put(ndiamate, strengthLevel);
			}
		}
		/**
		 * 按照试验钢丝数据公称直径组合数据
		 */
		Map<Double, List<WireData>> dataMap=new HashMap<>();
		List<WireData> l=null;
		for(WireData wireData:dataList) {
			if(!dataMap.containsKey(wireData.getnDiamete1())) {
				l=new ArrayList<WireData>();
				l.add(wireData);
			}else {
				l=dataMap.get(wireData.getnDiamete1());
				l.add(wireData);
			}
			dataMap.put(wireData.getnDiamete1(), l);
		}
		
		//公称直径
		double ndiamate=0;
		//钢丝最小破断拉力系数
		DiameterDeviation dd=null;//钢丝直径允许最大偏差  根据公称直径所在范围进行查询 
		//查询扭转弯曲次数的最低值条件
		ReverseBending rb=new ReverseBending();
		rb.setStandardNum(wireRope.getJudgeStandard());
//		rb.setRatedStrength(strengthLevel+"");
		rb.setSurfaceState(surface);
		//抗拉强度允许偏差值判断
		WireRopeData wireRopeData=new WireRopeData();
		wireRopeData.setStandardNum(wireRope.getJudgeStandard());
		StrengthDeviation sd=null;
		/**
		 * 定义数组，保存判定结果
		 */
		Map<Double, int[]> resultMap=new HashMap<Double,int[]>();
		//int[0]：存储直径偏差超过50% int[1]: 存储直径不合格 int[2]:抗拉强度不合格 int[3]:弯曲不合格 int[4]:扭转不合格 
		int[] all=new int[6];//保存总的结果
		/**
		 * 循环数据，进行判定
		 */
		Iterator<Double> it=dataMap.keySet().iterator();
		while(it.hasNext()) {
			ndiamate=it.next();
			Integer strengthL=strengthMap.get(ndiamate);
			if(strengthL==null||strengthL==0) {
				strengthL=strengthLevel;
			}
			rb.setRatedStrength(strengthL+"");
			//根据公称直径查询抗拉强度允许偏差值
			wireRopeData.setNdiamete(ndiamate);
			sd=sdMapper.selectByDiamate(wireRopeData);
			//查询公称直径对应的扭转弯曲次数
			 rb.setfDiamete(ndiamate);
			List<ReverseBending> rlist=reverseBendingMapper.selectRBDataByCon(rb);
			int[] d=new int[2];
			for(ReverseBending r:rlist) {
				if("R".equals(r.getType())) {
					d[0]=r.getValueRob();
				}else if("B".equals(r.getType())) {
					d[1]=r.getValueRob();
				}
			}
			int minWindTimes=d[1];
			int minTurnTimes=d[0];
			/**
			 * 查询直径偏差表，获得该标准的直径偏差
			 */
			wireRopeData.setSurface(surface);
			DiameterDeviation diameterDeviation = diameterDeviationMapper.selectDiaByBlurType(wireRopeData);
			double dDeviation = diameterDeviation.getValue();
			int[] re=new int[7];//用户记录该直径每项的判定结果int[0]直径不合格数  int[2]抗拉强度  int[3]弯曲  int[4]扭转 int[5]:同公称直径中综合判定不合格的根数 int[6]该直径钢丝根数
			List<WireData> dlist=dataMap.get(ndiamate);
			for(WireData wireData:dlist) {
				wireData.setDiameteJudge("");
				wireData.setStrengthJudge("");
				wireData.setTensileJudge("");
				wireData.setTurnJudge("");
				wireData.setWindingJudge("");
				wireData.setZincJudge("");
				/**
				 * 计算抗拉强度
				 */
				double streng = (Arith.div(wireData.getBreakTensile1(), Arith.mul(Math.PI, Arith.mul(wireData.getnDiamete1()/2, wireData.getnDiamete1()/2))))*1000;
				wireData.setTensileStrength((int) Math.round(streng));
				// 用于存储这根钢丝是否合格
				int message = 0;
				re[6]++;//当前直径钢丝根数增加
//				//直径判定
//				double diamate=wireData.getDiamete();
//				if(diamate-ndiamate<diamateMin||diamate-ndiamate>diamateMax) {
//					re[0]++;//直径不合格
//					all[0]++;
//					wireData.setDiameteJudge("**");
//				}
				
				//直径判定
				double diamate=wireData.getDiamete1();
				if(Math.abs(diamate-ndiamate)>dDeviation*1.5) {
					re[0]++;
					all[0]++;
					message = 1;
					wireData.setDiameteJudge("**");//不合格
				}else if(Math.abs(diamate-ndiamate)>dDeviation) {
					re[1]++;
					all[1]++;
//					message = 1;//低值
					
				}
				//抗拉强度判定
				double strength=wireData.getTensileStrength();
				if(strength-strengthL>sd.getValue()||strength<strengthL) {
					re[2]++;//抗拉强度不合格
					all[2]++;
					message = 1;
					wireData.setStrengthJudge("**");
				}
				//弯曲次数判定
				if(wireData.getWindingTimes()<minWindTimes) {
					re[3]++;
					all[3]++;
					message = 1;
					wireData.setWindingJudge("**");
				}
				//扭转次数判定  需要根据层数判定 未解决层数问题，如果是异形股钢丝绳，超过一层的扭转次数少一次，只有一层的少两次
				if("圆形股".equals(shape)) {
					if(wireData.getTurnTimes()<minTurnTimes) {
						re[4]++;
						all[4]++;
						message = 1;
						wireData.setTurnJudge("**");
					}
				}else if("异形股".equals(shape)) {
					if(wireData.getTurnTimes()<minTurnTimes-1) {
						re[4]++;
						all[4]++;
						message = 1;
						wireData.setTurnJudge("**");
					}
				}
				if(message == 1) {
					//re[5]存储该公称直径钢丝种不合格的根数
					re[5]++;
				}
				
				try {
					
					int effectedNum = wireDataMapper.updateByPrimaryKeySelective(wireData);
					if(effectedNum != 1) {
						logger.debug("WireData表中Judge信息没有更新成功");
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
	
}
