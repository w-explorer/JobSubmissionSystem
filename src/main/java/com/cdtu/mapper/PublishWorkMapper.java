package com.cdtu.mapper;

import com.cdtu.model.PublishWork;
import com.cdtu.model.PublishWorkExample;
import java.util.List;
import java.util.Map;

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
    void insterBycId(PublishWork publishWork);
    /**
     * 教师查找cId
     * @author LR
     * @param cId
     * @param tId
     */
    Integer selectTscid(@Param("cId")String cId,@Param("tId")String tId);
																	    /*
																	     * 通过ctId添加发布作业
																	     * @author LR
																	     * @param publishWork
																	     */
																	    //void insterByctId(PublishWork publishWork);
																	    /*
																	     * 通过cId添加发布作业
																	     * @author weiyuhang
																	     * @param publishWork
																	     */
																	    //void insterBycId(PublishWork publishWork);
																																    /*
																																     * 学生查询发布作业表通过cId
																																     * @author LR
																																     * @param sId
																																     * @param cId
																																     * @param pwState
																																     * @param start
																																     * @param end
																																     * @return
																																     */
																																    //List<PublishWork> selectStudentPublishWorkBycId(@Param("sId") String sId,@Param("cId") String cId,@Param("pwState") Boolean pwState,@Param("start") Integer start,@Param("end") Integer end);
																																   
																																    /*
																																     * * 学生查询发布作业表通过s_id和ct_id
																																     * @author LR
																																     * @param sId
																																     * @param ctId
																																     * @param pwState
																																     * @param start
																																     * @param end
																																     * @return
																																     */
																																   // List<PublishWork> selectStudentPublishWorkByctId(@Param("sId") String sId,@Param("ctId") Integer ctId,@Param("pwState") Boolean pwState,@Param("start") Integer start,@Param("end") Integer end);
    /**
     * 学生查询发布作业表通过s_id和tsc_id
     * @author LR
     * @param sId
     * @param tscId
     * @param pwState
     * @param start
     * @param end
     * @return
     */
    List<PublishWork> selectStudentPublishWorkBycId(@Param("sId") String sId,@Param("cId") String cId,@Param("pwState") Boolean pwState,@Param("start") Integer start,@Param("end") Integer end);
    /**
     * 学生查询发布作业表通过cId数量
     * @author LR
     * @param sId
     * @param cId
     * @param pwState
     * @return
     */
    Integer selectStudentPublishWorkCount(@Param("sId") String sId,@Param("cId") String cId,@Param("pwState") Boolean pwState);
    /**
     * 教师查询发布作业表通过cId
     * @author LR
     * @param tscId
     * @param pwState
     * @param start
     * @param end
     * @return
     */
    List<PublishWork> selectTeacherPublishWorkBycId(@Param("tId") String tId,@Param("cId") String cId,@Param("pwState") Boolean pwState,@Param("start") Integer start,@Param("end") Integer end);
    /**
     *  教师查询发布作业表通过cId数量
     * @author LR
     * @param tId
     * @param cId
     * @param pwState
     * @return
     */
   Integer selectTeacherPublishWorkCount(@Param("tId") String tId,@Param("cId") String cId,@Param("pwState") Boolean pwState);
																																    /*
																																     * 教师查询发布作业表通过s_id和tsc_id
																																     * @author LR
																																     * @param tscId
																																     * @param pwState
																																     * @param start
																																     * @param end
																																     * @return
																																     */
																																    //List<PublishWork> selectTeacherPublishWorkBycId(@Param("cId") Integer cId,@Param("pwState") Boolean pwState,@Param("start") Integer start,@Param("end") Integer end);
																																    /*
																																     * 教师查询发布作业表通过s_id和ct_id
																																     * @author LR
																																     * @param ctId
																																     * @param pwState
																																     * @param start
																																     * @param end
																																     * @return
																																     */
																																    //List<PublishWork> selectTeacherPublishWorkByctId(@Param("ctId") Integer ctId,@Param("pwState") Boolean pwState,@Param("start") Integer start,@Param("end") Integer end);
																																    
																																    /*
																																     * 教师查询发布作业的数量通过tsc_id
																																     * @author LR
																																     * @param tId
																																     * @param tscId
																																     * @param pwState
																																     * @return
																																     */
																																    //Integer selectCountBypwStateBytscId(@Param("tscId") Integer tscId,@Param("pwState") Boolean pwState);
																																    /*
																																     * 教师查询发布作业的数量通过ct_id（失效）
																																     * @author LR
																																     * @param tId
																																     * @param ctId
																																     * @param pwState
																																     * @return
																																     */
																																    // Integer selectCountBypwStateByctId(@Param("ctId") Integer ctId,@Param("pwState") Boolean pwState);
    /**
     * 查询发布作业名
     * @author weiyuhang
     * @param pwId
     * @return
     */
    String selectPwIdname(String pwId);
    /**
     * 改变作业状态
     * @author weiyuhang
     * @param pwId
     * @return
     */
    void changePublishWork(PublishWork publishwork);

	List<PublishWork> getAllPublishWorks();

	void updatePublishWorkState(@Param("pwId")String pwId, @Param("flag")Boolean flag);

	List<Map<String, Object>> getPwDetails(@Param("sId")String sId, @Param("pwId")String pwId);

	List<Map<String, Object>> getTFiles(@Param("sId")String sId, @Param("pwId")String pwId);

	List<Map<String, Object>> getSFiles(@Param("sId")String sId, @Param("pwId")String pwId);

	int countPublishWorks(@Param("cId")String cId);

	int countPublishEstimates(@Param("cId")String cId);
	 void  updatePublishWork(PublishWork publishwork);

}