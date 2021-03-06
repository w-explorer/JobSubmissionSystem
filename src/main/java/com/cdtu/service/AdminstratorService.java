package com.cdtu.service;

import java.util.List;
import java.util.Map;

import com.cdtu.model.Role;

public interface AdminstratorService {

	public String getPasswordById(String userName);

	public Map<String,Object> getAdminByaIdAndaPassword(Role role);

	public void updateRoleInfo(String email, String phone, String username);

	public void updataAvatar(String path, String username);
	
	public List<String> selectSchool();
	
	List<Map<String, Object>> selectSchoolCourse(String scId);
 }
