package com.cdtu.mapper;

import com.cdtu.model.TeacherSelectCourse;
import com.cdtu.model.TeacherSelectCourseExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface TeacherSelectCourseMapper {
    int countByExample(TeacherSelectCourseExample example);

    int deleteByExample(TeacherSelectCourseExample example);

    int deleteByPrimaryKey(Integer tscId);

    int insert(TeacherSelectCourse record);

    int insertSelective(TeacherSelectCourse record);

    List<TeacherSelectCourse> selectByExample(TeacherSelectCourseExample example);

    TeacherSelectCourse selectByPrimaryKey(Integer tscId);

    int updateByExampleSelective(@Param("record") TeacherSelectCourse record, @Param("example") TeacherSelectCourseExample example);

    int updateByExample(@Param("record") TeacherSelectCourse record, @Param("example") TeacherSelectCourseExample example);

    int updateByPrimaryKeySelective(TeacherSelectCourse record);

    int updateByPrimaryKey(TeacherSelectCourse record);
}