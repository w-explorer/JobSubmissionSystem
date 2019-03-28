package com.cdtu.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.CourseStudent;
import com.cdtu.model.Role;

public interface TeacherMapper {
	/**
	 * @author wencheng
	 */
	public Map<String,Object> getTeacherBytIdAndtPassword(Role role);
	/**
	 * @author wencheng
	 */
	public void updatatPasswordBytId(Role role);
	
	public String selectPasswordById(@Param("id") String id);
	/**
	 * @author weiyuhang
	 * @param courseStudent
	 * @return
	 */
	public void updataAvatar(@Param("tImgSrc")String path, @Param("tId")String username);
	public void deleteCourseStudent(CourseStudent courseStudent);
	public void updateRoleInfo(@Param("email")String email, @Param("phone")String phone, @Param("username")String username);
}