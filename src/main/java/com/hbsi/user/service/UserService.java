package com.hbsi.user.service;

import java.util.List;

import com.hbsi.domain.User;
import com.hbsi.response.Response;

public interface UserService {
	/**
	 * @description 添加用户  add user
	 * @param user
	 * @return
	 */
	Response<User> saveUser(User user);
	/**
	 * 用户登录
	 * @param user
	 * @return
	 */
	Response<User> login(User user);
	/**
	 * 删除用户
	 * @param id
	 * @return
	 */
	Response<Integer> deleteUser(Integer id);
	/**
	 * 修改密码
	 * @param username
	 * @param oldPassword
	 * @param newPassword
	 * @return
	 */
	Response<User> updateUserPassword(String username,String oldPassword,String newPassword);
	/**
	 * 重置密码
	 * @param username
	 * @param password
	 * @return
	 */
	Response<User> resetUserPassword(String username,String password);
	/**
	 * 修改用户信息
	 * @param user
	 * @return
	 */
	Response<User> updateUser(User user);
	/**
	 * 查询用户列表
	 * @return
	 */
	Response<List<User>> selectUserList();
	
	/**
	 * 验证用户
	 * @return
	 */
	Response<User> validUser(String info);
	
}
