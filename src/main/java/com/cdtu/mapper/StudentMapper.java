package com.cdtu.mapper;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.Role;
import com.cdtu.model.Student;


public interface StudentMapper {
	
	/**
	 * author wencheng
	 * @return Student
	 */
    public Student getStudentBysIdAndsPassword(Role role);
    /**
	 * author wencheng
	 * @return 
	 */
    public void updatasPasswordBysId(Role role);
    /**
	 * author wencheng
	 * @return 
	 */
	public String selectPasswordById(@Param("id") String id);
	
	/**
     * 查询学生名字
     * @author LR
     * @param id
     * @return
     */
    String selectStudentName(String id);
	
}