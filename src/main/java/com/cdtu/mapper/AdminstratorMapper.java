package com.cdtu.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.Role;

public interface AdminstratorMapper {

	String getPasswordById(String userName);

	Map<String,Object> getAdminByaIdAndaPassword(Role role);

    void updateRoleInfo(@Param("email")String email, @Param("phone")String phone, @Param("username")String username);

	void updataAvatar(@Param("path")String path, @Param("username")String username);
}