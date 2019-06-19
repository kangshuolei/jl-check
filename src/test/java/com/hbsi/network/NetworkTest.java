package com.hbsi.network;

import java.util.List;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.hbsi.JlCheckApplication;
import com.hbsi.domain.NetworkStatus;
import com.hbsi.network.service.NetworkService;
import com.hbsi.response.Response;

/**
 * 网络状态
 * 
 * @author lixuyang
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class NetworkTest {
	
	@Resource
	private NetworkService networkService;
	
	/**
	 * 查询网络状态列表
	 */
	@Test
	public void testSelectNetworkList() {
		Response<List<NetworkStatus>> response = networkService.selectNetworkList();
		System.out.println(response);
	}

}
