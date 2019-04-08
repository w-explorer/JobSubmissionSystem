package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.TeacherSelectCourse;
import com.cdtu.model.TeacherSelectCourseExample;

public interface TeacherSelectCourseMapper {
	int countByExample(TeacherSelectCourseExample example);

	int deleteByExample(TeacherSelectCourseExample example);

	int deleteByPrimaryKey(Integer tscId);

	int insert(TeacherSelectCourse record);

	int insertSelective(TeacherSelectCourse record);

	List<TeacherSelectCourse> selectByExample(TeacherSelectCourseExample example);

	TeacherSelectCourse selectByPrimaryKey(Integer tscId);

	int updateByExampleSelective(@Param("record") TeacherSelectCourse record,
			@Param("example") TeacherSelectCourseExample example);

	int updateByExample(@Param("record") TeacherSelectCourse record,
			@Param("example") TeacherSelectCourseExample example);

	int updateByPrimaryKeySelective(TeacherSelectCourse record);

	int updateByPrimaryKey(TeacherSelectCourse record);

	/**
	 * @author 李红兵
	 */
	Map<String, Object> selectByTIdAndCId(@Param("tId") String tId, @Param("cId") String cId);
}