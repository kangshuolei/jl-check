package com.hbsi.entrustment;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.dao.EntrustmentMapper;
import com.hbsi.domain.Entrustment;
import com.hbsi.entrustment.entity.EntInfo;
import com.hbsi.entrustment.service.EntrustmentService;
import com.hbsi.response.Response;

/**
 * 委托单
 * 
 * @author lixuyang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class EntrustmentTest {
	
	@Resource
	private EntrustmentService entrustmentService;
	@Autowired
	private EntrustmentMapper entrustmentMapper;
	
	/**
	 * 添加委托单号
	 */
//	@Test
	public void testSaveEntrustment() {
		Entrustment entrustment = new Entrustment();
		entrustment.setSampleBatch(128);
		//生成委托单号
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String entNum = sdf.format(new Date());
		entrustment.setEntrustmentNumber("WG"+entNum+entrustment.getSampleBatch());
//		entrustment.setEntrustmentNumber("LMJ980109");
		Response<Integer> response = entrustmentService.saveEntrustment(entrustment);
		System.out.println(response);
	}
	
	/**
	 * 修改委托单号
	 */
//	@Test
	public void testUpdateEntrustment() {
		EntInfo ei = new EntInfo();
		ei.setSampleBatch(129);
		ei.setOldEntNum("WG20181123129");
		ei.setNewEntNum("WG2018112312000");
		Response<Integer> response = entrustmentService.updateEntrustment(ei);
		System.out.println(response);
	}
	
	/**
	 * 删除委托单号
	 */
//	@Test
	public void testDeleteEntrustment() {
		Entrustment entrustment = new Entrustment();
		entrustment.setSampleBatch(1);
		entrustment.setEntrustmentNumber("WG180927B001");
		Response<Integer> response = entrustmentService.deleteEntrustment(entrustment);
		System.out.println(response);
	}
	
	/**
	 * 查询委托单号列表
	 */
//	@Test
	public void testSelectEntrustmentList() {
		Response<List<Entrustment>> response = entrustmentService.selectEntrustmentList();
		System.out.println(response);
	}
	/**
	 * 测试mapper中新加方法，
	 * 查询最新数据，
	 * 最老数据
	 * 降序排列的所有数据
	 */
//	@Test
	public void testSelectNewestAndOldest() {
		Entrustment entLatest = entrustmentMapper.selectLatestEntrust();
		System.out.println(entLatest+"我是最新的数据");
		Entrustment entOldest = entrustmentMapper.selectOldestEntrust();
		System.out.println(entOldest+"我是最老的一条数据");
		List<Entrustment> list = entrustmentMapper.selectAllByDesc();
		System.out.println(list);
		Integer effetedNum = entrustmentMapper.selectCount();
		System.out.println(effetedNum);
	}
	/**
	 * 测试新改的两个delete方法
	 */
	@Test
	public void testSqldeletemethod() {
		Entrustment entOldest = entrustmentMapper.selectOldestEntrust();
		Entrustment entLatest = entrustmentMapper.selectLatestEntrust();
		System.out.println(entLatest+"我是最新的数据");
		System.out.println(entOldest+"我是最老的一条数据");
//		entrustmentMapper.deleteEntrustmentReally(entOldest);
	}

}
