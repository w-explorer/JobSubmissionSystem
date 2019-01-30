package com.cdtu.mapper;

import com.cdtu.model.PublishWork;
import com.cdtu.model.PublishWorkExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface PublishWorkMapper {
    int countByExample(PublishWorkExample example);

    int deleteByExample(PublishWorkExample example);

    int deleteByPrimaryKey(String pwId);

    int insert(PublishWork record);

    int insertSelective(PublishWork record);

    List<PublishWork> selectByExampleWithBLOBs(PublishWorkExample example);

    List<PublishWork> selectByExample(PublishWorkExample example);

    PublishWork selectByPrimaryKey(String pwId);

    int updateByExampleSelective(@Param("record") PublishWork record, @Param("example") PublishWorkExample example);

    int updateByExampleWithBLOBs(@Param("record") PublishWork record, @Param("example") PublishWorkExample example);

    int updateByExample(@Param("record") PublishWork record, @Param("example") PublishWorkExample example);

    int updateByPrimaryKeySelective(PublishWork record);

    int updateByPrimaryKeyWithBLOBs(PublishWork record);

    int updateByPrimaryKey(PublishWork record);
    
    /**
     * 通过tscId添加发布作业
     * @author LR
     * @param publishWork
     */
    void insterBytscId(PublishWork publishWork);
    /**
     * 通过ctId添加发布作业
     * @author LR
     * @param publishWork
     */
    void insterByctId(PublishWork publishWork);
    /**
     * 学生查询发布作业表通过s_id和tsc_id
     * @author LR
     * @param sId
     * @param tscId
     * @return
     */
    List<PublishWork> selectStudentPublishWorkBytscId(@Param("sId") String sId,@Param("tscId") Integer tscId,@Param("pwState") Boolean pwState);
    /**
     * 学生查询发布作业表通过s_id和ct_id
     * @author LR
     * @param sId
     * @param ctId
     * @return
     */
    List<PublishWork> selectStudentPublishWorkByctId(@Param("sId") String sId,@Param("ctId") Integer ctId,@Param("pwState") Boolean pwState);
    /**
     * 教师查询发布作业表通过s_id和tsc_id
     * @author LR
     * @param sId
     * @param tscId
     * @param pwState
     * @return
     */
    List<PublishWork> selectTeacherPublishWorkBytscId(@Param("tscId") Integer tscId,@Param("pwState") Boolean pwState);
    /**
     * 教师查询发布作业表通过s_id和ct_id
     * @author LR
     * @param sId
     * @param ctId
     * @param pwState
     * @return
     */
    List<PublishWork> selectTeacherPublishWorkByctId(@Param("ctId") Integer ctId,@Param("pwState") Boolean pwState);
    /**
     * 教师查询发布作业的数量通过tsc_id
     * @author LR
     * @param tId
     * @param tscId
     * @param pwState
     * @return
     */
    Integer selectCountBypwStateBytscId(@Param("tscId") Integer tscId,@Param("pwState") Boolean pwState);
    /**
     * 教师查询发布作业的数量通过ct_id
     * @author LR
     * @param tId
     * @param ctId
     * @param pwState
     * @return
     */
    Integer selectCountBypwStateByctId(@Param("ctId") Integer ctId,@Param("pwState") Boolean pwState);
    /**
     * 查询发布作业名
     * @author weiyuhang
     * @param pwId
     * @return
     */
    String selectPwIdname(String pwId);
}