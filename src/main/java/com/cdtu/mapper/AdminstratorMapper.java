package com.cdtu.mapper;

import com.cdtu.model.Adminstrator;
import com.cdtu.model.AdminstratorExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface AdminstratorMapper {
    int countByExample(AdminstratorExample example);

    int deleteByExample(AdminstratorExample example);

    int insert(Adminstrator record);

    int insertSelective(Adminstrator record);

    List<Adminstrator> selectByExample(AdminstratorExample example);

    int updateByExampleSelective(@Param("record") Adminstrator record, @Param("example") AdminstratorExample example);

    int updateByExample(@Param("record") Adminstrator record, @Param("example") AdminstratorExample example);
}