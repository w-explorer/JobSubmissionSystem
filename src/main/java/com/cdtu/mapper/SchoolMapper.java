package com.cdtu.mapper;

import java.util.List;
import org.apache.ibatis.annotations.Param;

import com.cdtu.model.School;
import com.cdtu.model.SchoolExample;

public interface SchoolMapper {
    int countByExample(SchoolExample example);

    int deleteByExample(SchoolExample example);

    int deleteByPrimaryKey(String scId);

    int insert(School record);

    int insertSelective(School record);

    List<School> selectByExample(SchoolExample example);

    School selectByPrimaryKey(String scId);

    int updateByExampleSelective(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByExample(@Param("record") School record, @Param("example") SchoolExample example);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
    /**
     * 查询学院
     * @author weiyuhang
     * @return
     */
    List<String> selectSchool();
}