package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.ClassCreate;
import com.cdtu.model.CourseWapper;

public interface CourseMapper {
	/**
	 * @author 李红兵
	 */
	public int countByCId(@Param("cId") String cId);

	/**
	 * @author 李红兵
	 */
	public Map<String, Object> selectByCId(@Param("cId") String cId, @Param("sId") String sId);

	/**
	 * @author 李红兵
	 */
	public Map<String, Object> selectDetails(@Param("cId") String cId);

	/**
	 * @author weiyuhang
	 */
	List<CourseWapper> selectTeacherCourse(String id);

	/**
	 * @author weiyuhang
	 */
	int insertClassCreate(ClassCreate classcreate);

	/**
	 * @author weiyuhang
	 */
	List<CourseWapper> selectClassCreate(String id);

	/**
	 * @author weiyuhang
	 */
	int updateClassCreate(ClassCreate classcreate);

	/**
	 * @author weiyuhang
	 */
	int deleteClassCreate(int ctId);
	/**
	 * 查询学院课程
	 * @author weiyuhang
	 */
	List<Map<String,Object>> selectSchoolCourse(@Param("scId") String scId);

	public List<Map<String, Object>> selectStudents(@Param("start")int start,@Param("end")int end,@Param("id")String id,@Param("cId")String cId);

	public List<String> selectAllEmailInClass(@Param("id")String id,@Param("cId")String cId);

	public String selectCnameByCid(@Param("cId") String cId);
}