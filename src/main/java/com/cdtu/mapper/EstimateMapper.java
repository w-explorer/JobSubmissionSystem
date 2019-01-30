package com.cdtu.mapper;

import com.cdtu.model.Estimate;
import com.cdtu.model.EstimateExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface EstimateMapper {
    int countByExample(EstimateExample example);

    int deleteByExample(EstimateExample example);

    int deleteByPrimaryKey(String eId);

    int insert(Estimate record);

    int insertSelective(Estimate record);

    List<Estimate> selectByExample(EstimateExample example);

    Estimate selectByPrimaryKey(String eId);

    int updateByExampleSelective(@Param("record") Estimate record, @Param("example") EstimateExample example);

    int updateByExample(@Param("record") Estimate record, @Param("example") EstimateExample example);

    int updateByPrimaryKeySelective(Estimate record);

    int updateByPrimaryKey(Estimate record);
    
    /**
     * 添加点评
     * @author LR
     * @param estimate
     */
    void insertEstimate(Estimate estimate);
}