package com.hbsi.machine;

import javax.annotation.Resource;

import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.hbsi.JlCheckApplication;
import com.hbsi.domain.Machine;
import com.hbsi.machine.service.MachineService;
import com.hbsi.response.PageResult;
import com.hbsi.response.Response;

/**
 * 机器数据
 * 
 * @author lixuyang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class MachineTest {
	
	@Resource
	private MachineService machineService;
	
	/**
	 * 添加机器数据
	 */
//	@Test
	public void testSaveMachine() {
	    Machine machine = new Machine();
	    machine.setMachineAddr(5);
	    machine.setMachineType("DC-L");
	    machine.setMachineNumber("DC-L11");
		Response<Integer> response = machineService.saveMachine(machine);
		System.out.println(response);
	}
	
	/**
	 * 查询机器数据列表
	 */
//	@Test
	public void testSelectMachineList() {
		JSONObject json = new JSONObject();
		json.put("offset", 1);
		json.put("limit", 20);
		Response<PageResult> response = machineService.selectMachineList(json.toJSONString());
		System.out.println(response);
	}
	
	
	/**
	 * 删除机器数据
	 */
//	@Test
	public void testDeleteMachine() {
		JSONObject json = new JSONObject();
		json.put("id", 7);
		Response<Integer> response = machineService.deleteMachine(json.toJSONString());
		System.out.println(response);
	}
	
}
