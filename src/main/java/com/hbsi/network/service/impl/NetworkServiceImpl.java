package com.hbsi.network.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.hbsi.dao.NetworkStatusMapper;
import com.hbsi.domain.NetworkStatus;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.network.service.NetworkService;
import com.hbsi.response.Response;

/**
 * 网络状态
 * @author lixuyang
 * @version 1.0，2018-10-02
 *
 */
@Service
public class NetworkServiceImpl implements NetworkService{
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private NetworkStatusMapper networkStatusMapper;//网络状态

	/**
	 * 查询网络状态列表
	 * @return
	 */
	@Transactional
	@Override
	public Response<List<NetworkStatus>> selectNetworkList() {
		List<NetworkStatus> list = null;
		try {
			list = networkStatusMapper.selectNetworkList();
		} catch (Exception e) {
			logger.error("查询网络状态列表信息异常 异常信息：{}", e.getMessage());
			throw new BaseException(ExceptionEnum.NETWORK_SELECT_FAILED);
		}
		return new Response<>(list);
	}

}
