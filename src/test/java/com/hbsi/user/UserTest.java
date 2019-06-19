package com.hbsi.user;

import javax.annotation.Resource;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import com.alibaba.fastjson.JSONObject;
import com.hbsi.JlCheckApplication;
import com.hbsi.domain.SampleRecord;
import com.hbsi.domain.User;
import com.hbsi.response.Response;
import com.hbsi.user.service.UserService;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest(classes =JlCheckApplication.class)
@WebAppConfiguration
public class UserTest {
	@Resource
	private UserService userService;
	//@Test
	public void testSaveUser() {
		User user=new User();
		user.setUsername("admin");
		user.setPassword("admin123");
		user.setUserRank("管理员");
		user.setUserClass("0");
		Response<User> r=userService.saveUser(user);
		System.out.println(r);
	}
	
	@Test
	public void testValidUser() {
		JSONObject json = new JSONObject();
		json.put("username","lxy");
		json.put("password","123");
		Response<User> r=userService.validUser(json.toString());
		System.out.println(r);
	}

}
