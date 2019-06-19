package com.hbsi.strength.service.impl;


import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.hbsi.response.Response;
import com.hbsi.strength.service.StrengthService;

@Service
public class StrengthServiceImpl implements StrengthService{
		
	/**
	 * 展示所有额定强度
	 */
	@Override
	public Response<List<Integer>> showAllStrength() {
		//String[] strengthArr = {"1570" ,"1670", "1770" ,"1870" ,"1960","2160"};
		List<Integer> strengthList = new ArrayList<Integer>();//存放所有的钢丝强度
		//添加钢丝额定强度
		strengthList.add(1570);
		strengthList.add(1670);
		strengthList.add(1770);
		strengthList.add(1870);
		strengthList.add(1960);
		strengthList.add(2160);
		
		return new Response<List<Integer>>(strengthList);
	}
	
	
}
