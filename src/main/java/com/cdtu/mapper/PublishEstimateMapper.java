package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.PublishEstimate;
import com.cdtu.model.PublishEstimateExample;

public interface PublishEstimateMapper {
    int countByExample(PublishEstimateExample example);

    int deleteByExample(PublishEstimateExample example);

    int deleteByPrimaryKey(String epId);

    int insert(PublishEstimate record);

    int insertSelective(PublishEstimate record);

    List<PublishEstimate> selectByExample(PublishEstimateExample example);

    PublishEstimate selectByPrimaryKey(String epId);

    int updateByExampleSelective(@Param("record") PublishEstimate record, @Param("example") PublishEstimateExample example);

    int updateByExample(@Param("record") PublishEstimate record, @Param("example") PublishEstimateExample example);

    int updateByPrimaryKeySelective(PublishEstimate record);

    int updateByPrimaryKey(PublishEstimate record);
    
    /**
     * 添加有tsc_id的发布评价表
     * @author LR
     * @param publishEstimate
     */
    void insertByTscId(PublishEstimate publishEstismate);
    /**
     * 添加有ct_id的发布评价表
     * @author LR
     * @param publishEstimate
     */
    void insertByCtId(PublishEstimate publishEstimate);
    /**
     * 查询发布点评表通过s_id和tsc_id
     * @author LR
     * @param sId
     * @param cstId
     * @return
     */
    List<Map<String,Object>> selectPublishEstimateBytscId(@Param("cId") String cId,@Param("sId") String sId,@Param("start")int start,@Param("end")int end);
    /**
     * 查询发布点评表通过s_id和ct_id
     * @author LR
     * @param sId
     * @param ctId
     * @return
     */
    List<PublishEstimate> selectPublishEstimateByctId(@Param("sId") String sId,@Param("ctId") Integer ctId);
    int selectPublishEstimateCount(@Param("tId")String tId, @Param("cId")String cId);
    List<Map<String,Object>> selectPublishEstimateBytId(@Param("tId") String tId,@Param("cId") String cId,@Param("start")int start,@Param("end")int end);
    
}