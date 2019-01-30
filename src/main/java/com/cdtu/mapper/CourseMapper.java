package com.cdtu.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.Course;
import com.cdtu.model.CourseExample;
import com.cdtu.model.CourseWapper;

public interface CourseMapper {
    int countByExample(CourseExample example);

    int deleteByExample(CourseExample example);

    int deleteByPrimaryKey(String cId);

    int insert(Course record);

    int insertSelective(Course record);

    List<Course> selectByExample(CourseExample example);

    Course selectByPrimaryKey(String cId);

    int updateByExampleSelective(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByExample(@Param("record") Course record, @Param("example") CourseExample example);

    int updateByPrimaryKeySelective(Course record);

    int updateByPrimaryKey(Course record);
    
    /**
	 *  教师查询非创建课程
	 * @author weiyuhang
	 * @param id
	 * @return
	 */
	List<CourseWapper> selectTeacherCourse(String id);
}