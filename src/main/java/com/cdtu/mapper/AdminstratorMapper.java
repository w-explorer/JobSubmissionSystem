package com.cdtu.mapper;

import java.util.Map;

import com.cdtu.model.Role;

public interface AdminstratorMapper {

	String getPasswordById(String userName);

	Map<String,Object> getAdminByaIdAndaPassword(Role role);
}