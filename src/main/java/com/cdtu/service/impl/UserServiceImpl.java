package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdtu.mapper.UserMapper;
import com.cdtu.service.UserService;
@Transactional
@Service(value = "userService")
public class UserServiceImpl implements UserService {
	private @Resource UserMapper userMapper;
	@Override
	public List<Map<String, Object>> getPassword(String username, String email) {
		return userMapper.getPassword(username,email);
	}
	@Override
	public String getEmailByUsername(String username) {
		return userMapper.getEmailByUsername(username);
	}

}
