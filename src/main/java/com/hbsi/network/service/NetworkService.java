package com.hbsi.network.service;

import java.util.List;

import com.hbsi.domain.NetworkStatus;
import com.hbsi.response.Response;

/**
 * 网络状态
 * @author lixuyang
 * @version 1.0，2018-10-02
 *
 */
public interface NetworkService {
	
	/**
	 * 查询网络状态列表
	 * @return
	 */
	Response<List<NetworkStatus>> selectNetworkList();

}
