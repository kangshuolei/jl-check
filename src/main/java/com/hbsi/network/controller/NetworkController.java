package com.hbsi.network.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hbsi.domain.NetworkStatus;
import com.hbsi.network.service.NetworkService;
import com.hbsi.response.Response;

/**
 * 网络状态
 * @author lixuyang
 * @version 1.0，2018-10-02
 *
 */
@RestController
@RequestMapping("/network")
public class NetworkController {
	
	@Autowired
	private NetworkService networkService;
	
	/**
	 * 查询网络状态列表
	 * @return
	 */
	@PostMapping("/selectNetworkList")
	public Response<List<NetworkStatus>> selectNetworkList(){
		return networkService.selectNetworkList();
	}

}
