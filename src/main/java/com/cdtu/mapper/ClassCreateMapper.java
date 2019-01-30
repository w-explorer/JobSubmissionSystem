package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.ClassCreate;
import com.cdtu.model.CourseWapper;

public interface ClassCreateMapper {
	/**
	 * @Author 李红兵
	 */
	public int countByCId(@Param("cId") int cId);

	/**
	 * @Author 李红兵
	 */
	public Map<String, Object> selectByCId(@Param("cId") int cId, @Param("sId") String sId);

	/**
	 * 教师增添课程
	 *
	 * @author weiyuhang
	 * @return
	 */
	int insertClassCreate(ClassCreate classcreate);

	/**
	 * 教师查询课程
	 *
	 * @author weiyuhang
	 * @return
	 */
	List<CourseWapper> selectClassCreate(String id);

	/**
	 * 教师修改课程
	 *
	 * @author weiyuhang
	 * @return
	 */
	int updateClassCreate(ClassCreate classcreate);

	/**
	 * 教师删除课程
	 *
	 * @author weiyuhang
	 * @return
	 */
	int deleteClassCreate(int ctId);

}