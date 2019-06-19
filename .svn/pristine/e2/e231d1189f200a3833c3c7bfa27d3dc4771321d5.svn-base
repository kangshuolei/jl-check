package com.hbsi.entrustmenthistory.util;

import java.text.SimpleDateFormat;
import java.util.Date;

public class CreateEntrustmentNum {

	/**
	 * 生成委托单号默认值
	 * 
	 * 根据时间+批次号 = 生成委托单号 例如：WG181130B125
	 * 
	 * 
	 * WG + 181130 125
	 * 
	 * 181130 日期 WG 批次号 ? 125 随机数??
	 * 
	 * @return
	 */
	private static void createEntrustmentDefaultNum() {

		Date date = new Date();// 获取当前日期
		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");// 格式化 形式
		String yMD = sdf.format(date);// 格式化当前日期
		SimpleDateFormat hours = new SimpleDateFormat("HHmm");
		String hM = hours.format(date);// 格式化当前日期
		int i = Integer.parseInt(hM);
		// 5点45之前 yyMMdd+B 之后 yyMMdd+Y
		String num = i > 1745 ? yMD + "Y" + (int) Math.floor(Math.random() * 1000)
				: yMD + "B" + (int) Math.floor(Math.random() * 1000);
		System.out.println(num);

	}

	private static void testTrimMethod(String str) {
		String num = str.trim();//去除字符串中的空格
		//System.out.println(num);
		String strNew = "";//存放新的只有数字的 字符串
		if(num != null && !"".equals(num)){
			for(int i=0;i<num.length();i++){
				if(num.charAt(i)>=48 && num.charAt(i)<=57){
					strNew+=num.charAt(i);
				}
		   }
		}
		System.out.println(strNew);
	}

	public static void main(String[] args) {
		//createEntrustmentDefaultNum();
		testTrimMethod("   asd  fg12____3asgh123");
	}
}
