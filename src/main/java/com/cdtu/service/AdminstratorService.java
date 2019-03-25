package com.cdtu.service;

import java.util.Map;

import com.cdtu.model.Role;

public interface AdminstratorService {

	public String getPasswordById(String userName);

	public Map<String,Object> getAdminByaIdAndaPassword(Role role);
}
