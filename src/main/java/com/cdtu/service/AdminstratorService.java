package com.cdtu.service;

import com.cdtu.model.Adminstrator;
import com.cdtu.model.Role;

public interface AdminstratorService {

	public String getPasswordById(String userName);

	public Adminstrator getAdminByaIdAndaPassword(Role role);
}
