package com.cdtu.mapper;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.Role;
import com.cdtu.model.Teacher;

public interface TeacherMapper {
	/**
	 * @author wencheng
	 */
	public Teacher getTeacherBytIdAndtPassword(Role role);
	/**
	 * @author wencheng
	 */
	public void updatatPasswordBytId(Role role);
	
	public String selectPasswordById(@Param("id") String id);
}