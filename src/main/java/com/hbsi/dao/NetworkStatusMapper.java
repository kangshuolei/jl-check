package com.hbsi.dao;

import java.util.List;

import com.hbsi.domain.NetworkStatus;

public interface NetworkStatusMapper {

	int deleteByPrimaryKey(Integer id);

	int insert(NetworkStatus record);

	int insertSelective(NetworkStatus record);

	NetworkStatus selectByPrimaryKey(Integer id);

	int updateByPrimaryKeySelective(NetworkStatus record);

	int updateByPrimaryKey(NetworkStatus record);

	List<NetworkStatus> selectNetworkList();// 查询网络状态列表
	/**
	 * 根据机器编号删除机器
	 * @param machineNumber
	 * @return
	 */
	int deleteBymachine(String machineNumber);
}