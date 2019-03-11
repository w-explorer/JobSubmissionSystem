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
}