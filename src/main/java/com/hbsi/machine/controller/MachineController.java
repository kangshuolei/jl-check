package com.hbsi.machine.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
@RestController
@RequestMapping("/machine")
public class MachineController {
	
	@Autowired
	private MachineService machineService;
	
	/**
	 * 添加机器数据
	 * @return
	 */
	@Transactional
	@PostMapping("/saveMachine")
	public Response<Integer> saveMachine(@RequestBody Machine machine){
		return machineService.saveMachine(machine);
	}
	
	/**
	 * 查询机器列表
	 * @param info
	 * @return
	 */
	@Transactional
	@PostMapping("/selectMachineList")
	public Response<PageResult> selectMachineList(@RequestBody String info){
		return machineService.selectMachineList(info);
	}
	
	/**
	 * 删除机器数据
	 * @return
	 */
	@Transactional
	@PostMapping("/deleteMachine")
	public Response<Integer> deleteMachine(@RequestBody String info){
		return machineService.deleteMachine(info);
	}
	

}
