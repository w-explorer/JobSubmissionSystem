package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

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
	public void updataAvatar(@Param("sImgSrc")String path,@Param("sId") String username);
	public List<Map<String, Object>> fuzzySearchStudentByNameOrId(@Param("nameOrId")String nameOrId, @Param("cId")String cId);
	public List<Map<String, Object>> SearchStudentById(@Param("sId")String sId);
	
}