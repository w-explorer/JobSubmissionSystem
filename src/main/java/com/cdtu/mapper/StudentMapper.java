package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.Role;


public interface StudentMapper {
	
	/**
	 * author wencheng
	 * @return Student
	 */
    public Map<String,Object> getStudentBysIdAndsPassword(Role role);
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
	public List<Map<String, Object>> fuzzySearchStudentByNameOrId(@Param("nameOrId")String nameOrId);
	public List<Map<String, Object>> SearchStudentById(@Param("sId")String sId);
	public void CreatStudentTableDescRank(@Param("cId")String cId,@Param("tId")String tId);
	public List<Map<String, Object>> selectStudents( @Param("start") int start,
			@Param("end") int end);
	public void updateRoleInfo(@Param("email")String email, @Param("phone")String phone, @Param("username")String username);
	
}