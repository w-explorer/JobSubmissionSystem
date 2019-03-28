package com.cdtu.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdtu.mapper.AdminstratorMapper;
import com.cdtu.model.Role;
import com.cdtu.service.AdminstratorService;
@Transactional
@Service("adminstratorService")
public class AdminstratorServiceImpl implements AdminstratorService {

	public @Resource AdminstratorMapper adminstratorMapper;
	@Override
	public String getPasswordById(String userName) {
		return adminstratorMapper.getPasswordById(userName);
	}
	@Override
	public Map<String,Object> getAdminByaIdAndaPassword(Role role) {
		return adminstratorMapper.getAdminByaIdAndaPassword(role);
	}
	@Override
	public void updateRoleInfo(String email, String phone, String username) {
		// TODO Auto-generated method stub
		adminstratorMapper.updateRoleInfo(email,  phone,  username);
	}
	@Override
	public void updataAvatar(String path, String username) {
		adminstratorMapper.updataAvatar(path, username);
		
	}

}
