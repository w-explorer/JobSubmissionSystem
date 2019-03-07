package com.cdtu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.AdminstratorMapper;
import com.cdtu.model.Adminstrator;
import com.cdtu.model.Role;
import com.cdtu.service.AdminstratorService;

@Service("adminstratorService")
public class AdminstratorServiceImpl implements AdminstratorService {

	public @Resource AdminstratorMapper adminstratorMapper;
	@Override
	public String getPasswordById(String userName) {
		return adminstratorMapper.getPasswordById(userName);
	}
	@Override
	public Adminstrator getAdminByaIdAndaPassword(Role role) {
		return adminstratorMapper.getAdminByaIdAndaPassword(role);
	}

}
