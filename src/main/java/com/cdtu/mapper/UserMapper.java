package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface UserMapper {

	List<Map<String, Object>> getPassword(@Param("username")String username, @Param("email")String email);

	String getEmailByUsername(String username);
}
