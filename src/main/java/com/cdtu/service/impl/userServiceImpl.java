package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.UserMapper;
import com.cdtu.service.UserService;
@Service(value = "userService")
public class userServiceImpl implements UserService {
	private @Resource UserMapper userMapper;
	@Override
	public List<Map<String, Object>> getPassword(String username, String email) {
		return userMapper.getPassword(username,email);
	}

}
