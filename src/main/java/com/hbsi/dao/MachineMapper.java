package com.hbsi.dao;

import java.util.List;

import com.hbsi.domain.Machine;

public interface MachineMapper {

	int deleteByPrimaryKey(Integer id);

	/**
	 * 添加机器数据
	 * @param record
	 * @return
	 */
	int insert(Machine record);

	int insertSelective(Machine record);
	/**
	 * 根据id查询
	 * @param id
	 * @return
	 */
	Machine selectById(int id);

	/**
	 * 根据机器地址查询
	 * @param machineAddr
	 * @return
	 */
	Machine selectByPrimaryKey(Integer machineAddr);

	int updateByPrimaryKeySelective(Machine record);

	int updateByPrimaryKey(Machine record);
	
	/**
	 * 查询机器列表
	 * @return
	 */
	List<Machine> selectMachineList();
	
	/**
	 * 删除机器数据
	 * @param id
	 * @return
	 */
	int deleteMachine(Integer id);
}