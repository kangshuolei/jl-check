package com.hbsi.user.service.impl;

import java.security.MessageDigest;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.hbsi.dao.UserMapper;
import com.hbsi.domain.User;
import com.hbsi.exception.BaseException;
import com.hbsi.exception.ExceptionEnum;
import com.hbsi.response.Response;
import com.hbsi.user.service.UserService;
import com.hbsi.util.SecurityMd5;

@Service
public class UserServiceImpl implements UserService{
	private Logger logger=LoggerFactory.getLogger(UserService.class);
	
	@Autowired
	private UserMapper userMapper;
	/**
	 * @description add user
	 */
	@Override
	public Response<User> saveUser(User user) {
		if(user==null) {
			/*记录错误日志*/
			logger.info("添加用户,{}",ExceptionEnum.USER_INFO_NULL.getMessage());
			/*抛出错误异常*/
			throw new BaseException(ExceptionEnum.USER_INFO_NULL);
		}else {
			if(user.getUsername()==null||"".equals(user.getUsername())) {
				logger.debug("添加用户,{}",ExceptionEnum.USER_INFO_NOT_COMPLETE.getMessage());
				throw new BaseException(ExceptionEnum.USER_INFO_NOT_COMPLETE);
			}
			if(user.getPassword()==null||"".equals(user.getPassword())) {
				logger.debug("添加用户,{}",ExceptionEnum.USER_INFO_NOT_COMPLETE.getMessage());
				throw new BaseException(ExceptionEnum.USER_INFO_NOT_COMPLETE);
			}
			if(user.getUserRank()==null||"".equals(user.getUserRank())) {
				logger.debug("添加用户,{}",ExceptionEnum.USER_INFO_NOT_COMPLETE.getMessage());
				throw new BaseException(ExceptionEnum.USER_INFO_NOT_COMPLETE);
			}
			if(user.getUserClass()==null||"".equals(user.getUserClass())) {
				logger.debug("添加用户,{}",ExceptionEnum.USER_INFO_NOT_COMPLETE.getMessage());
				throw new BaseException(ExceptionEnum.USER_INFO_NOT_COMPLETE);
			}
			User u=userMapper.selectByUsername(user.getUsername());
			if(u!=null) {
				logger.debug("添加用户,{}",ExceptionEnum.USER_HAS_FOUND.getMessage());
				throw new BaseException(ExceptionEnum.USER_HAS_FOUND);
			}
			try {
				String password=user.getPassword();
				user.setPassword(SecurityMd5.encode(password));
				int i=userMapper.insert(user);
				if(i==1) {
					logger.info("添加用户成功");
					return new Response<User>(user);
				}else {
					logger.error("添加用户错误，{}",ExceptionEnum.USER_SAVE_ERROR.getMessage());
					throw new BaseException(ExceptionEnum.USER_SAVE_ERROR);
				}
			} catch (Exception e) {
				logger.error("添加用户错误，{}",e.getLocalizedMessage());
				throw new BaseException(ExceptionEnum.USER_SAVE_ERROR);
			}
		}
		
	}
	/**
	 * 用户登录
	 */
	public Response<User> login(User user) {
		if(user==null) {
			/*记录错误日志*/
			logger.debug("用户登录,{}",ExceptionEnum.USER_INFO_NULL.getMessage());
			/*抛出错误异常*/
			throw new BaseException(ExceptionEnum.USER_INFO_NULL);
		}else {
			if(user.getUsername()==null||"".equals(user.getUsername())) {
				logger.debug("用户登录，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
				throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
			}
			if(user.getPassword()==null||"".equals(user.getPassword())) {
				logger.debug("用户登录，{}",ExceptionEnum.USER_PASSWORD_ERROR.getMessage());
				throw new BaseException(ExceptionEnum.USER_PASSWORD_ERROR);
			}
			User u=userMapper.selectByUsername(user.getUsername());
			if(u==null) {
				logger.debug("用户登录，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
				throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
			}else {
				if(!SecurityMd5.encode(user.getPassword()).equals(u.getPassword())) {
					logger.debug("用户登录，{}",ExceptionEnum.USER_PASSWORD_ERROR.getMessage());
					throw new BaseException(ExceptionEnum.USER_PASSWORD_ERROR);
				}else {
					logger.info("用户登录成功");
					return new Response<User>(u);
				}
			}
		}
	}
	/**
	 * 删除用户
	 */
	@Override
	public Response<Integer> deleteUser(Integer id) {
		System.out.println("********   "+id);
		if(id==null) {
			logger.debug("用户删除，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
			throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
		}else {
			int i=userMapper.deleteUser(id);
			if(i>0) {
				logger.info("用户删除成功");
				return new Response<Integer>(id);
			}else {
				logger.debug("用户删除，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
				throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
			}
		}
	}
	/**
	 * 修改密码
	 */
	@Override
	public Response<User> updateUserPassword(String username, String oldPassword, String newPassword) {
		if(username==null) {
			logger.debug("用户修改密码，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
			throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
		}else {
			User u=userMapper.selectByUsername(username);
			if(u==null) {
				logger.debug("用户修改密码，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
				throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
			}else {
				if(!SecurityMd5.encode(oldPassword).equals(u.getPassword())) {
					logger.debug("用户修改密码，{}",ExceptionEnum.USER_PASSWORD_ERROR.getMessage());
					throw new BaseException(ExceptionEnum.USER_PASSWORD_ERROR);
				}else {
					u.setPassword(SecurityMd5.encode(newPassword));
					int i=userMapper.updateByPrimaryKeySelective(u);
					logger.info("用户密码修改成功");
					return new Response<User>(u);
				}
			}
			
		}
	}
	@Override
	public Response<User> resetUserPassword(String username, String password) {
		if(username==null) {
			logger.debug("用户修改密码，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
			throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
		}else {
			String pass=SecurityMd5.encode(password);
			User user=new User();
			user.setUsername(username);
			user.setPassword(pass);
			int i=userMapper.resetPassword(user);
			logger.info("用户密码重置成功");
			return new Response<User>(user);
		}
	}
	@Override
	public Response<User> updateUser(User user) {
		System.out.println(user.getPassword());
		if(user==null||user.getId()==null) {
			logger.debug("用户信息修改，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
			throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
		}else {
			User u=userMapper.selectByPrimaryKey(user.getId());
			if(u==null) {
				logger.debug("用户信息修改，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
				throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
			}else {
				if(user.getUserClass()!=null&&!"".equals(user.getUserClass())) {
					u.setUserClass(user.getUserClass());
				}
				if(user.getUserRank()!=null&&!"".equals(user.getUserRank())) {
					u.setUserRank(user.getUserRank());
				}
				if(user.getPassword()!=null&&!"".equals(user.getPassword())) {
					u.setPassword(SecurityMd5.encode(user.getPassword()));
				}
				int i=userMapper.updateByPrimaryKey(u);
				logger.info("用户信息修改成功");
				return new Response<User>(u);
			}
		}
	}
	@Override
	public Response<List<User>> selectUserList() {
		List<User> list=userMapper.selectAllUser();
		return new Response<List<User>>(list);
	}
	
	/**
	 * 验证用户
	 */
	public Response<User> validUser(String info) {
		JSONObject json=JSON.parseObject(info);
		String username=json.getString("username");
		String password=json.getString("password");
		if(username==null) {
			logger.debug("验证未通过,{}",ExceptionEnum.USER_INFO_NULL.getMessage());
			throw new BaseException(ExceptionEnum.USER_INFO_NULL);
		}else {
			if(password==null||"".equals(password)) {
				logger.debug("验证未通过，{}",ExceptionEnum.USER_PASSWORD_NULL.getMessage());
				throw new BaseException(ExceptionEnum.USER_PASSWORD_NULL);
			}
			User user=userMapper.selectByUsername(username);
			if(user==null) {
				logger.debug("验证未通过，{}",ExceptionEnum.USER_NOT_FOUND.getMessage());
				throw new BaseException(ExceptionEnum.USER_NOT_FOUND);
			}else {
				if(!user.getPassword().equals(SecurityMd5.encode(password))) {
					logger.debug("验证未通过，{}",ExceptionEnum.USER_PASSWORD_ERROR.getMessage());
					throw new BaseException(ExceptionEnum.USER_PASSWORD_ERROR);
				}else {
					logger.info("验证成功");
					return new Response<User>(user);
				}
			}
		}
	}
	

}
