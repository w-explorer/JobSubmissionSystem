package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;

public interface StudentSelectCourseMapper {
	/**
	 * @Author 李红兵
	 */
	public List<Map<String, Object>> selectBySId(@Param("sId") String sId);

	/**
	 * @Author 李红兵
	 */
	public int insert(@Param("cId") int cId, @Param("sId") String sId);

	/**
	 * @Author 李红兵
	 */
	public int countBySIdAndCId(@Param("cId") int cId, @Param("sId") String sId);

	/**
	 * 查询该课程的学生
	 *
	 * @author weiyuhang
	 */
	public List<CourseStudent> selectCourseStudent(CourseWapper coursewapper);
}