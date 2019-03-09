package com.cdtu.mapper;

import com.cdtu.model.Adminstrator;
import com.cdtu.model.Role;

public interface AdminstratorMapper {

	String getPasswordById(String userName);

	Adminstrator getAdminByaIdAndaPassword(Role role);
}