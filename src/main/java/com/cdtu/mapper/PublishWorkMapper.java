package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.PublishWork;
import com.cdtu.model.PublishWorkExample;

public interface PublishWorkMapper {
	public int countByExample(PublishWorkExample example);

	public int deleteByExample(PublishWorkExample example);

	public int deleteByPrimaryKey(String pwId);

	public int insert(PublishWork record);

	public int insertSelective(PublishWork record);

	public List<PublishWork> selectByExampleWithBLOBs(PublishWorkExample example);

	public List<PublishWork> selectByExample(PublishWorkExample example);

	public PublishWork selectByPrimaryKey(String pwId);

	public int updateByExampleSelective(@Param("record") PublishWork record,
			@Param("example") PublishWorkExample example);

	public int updateByExampleWithBLOBs(@Param("record") PublishWork record,
			@Param("example") PublishWorkExample example);

	public int updateByExample(@Param("record") PublishWork record, @Param("example") PublishWorkExample example);

	public int updateByPrimaryKeySelective(PublishWork record);

	public int updateByPrimaryKeyWithBLOBs(PublishWork record);

	public int updateByPrimaryKey(PublishWork record);

	/**
	 * 通过tscId添加发布作业
	 *
	 * @author LR
	 * @param publishWork
	 */
	public void insterBycId(PublishWork publishWork);

	/**
	 * 教师查找cId
	 *
	 * @author LR
	 * @param cId
	 * @param tId
	 */
	public int selectTscid(@Param("cId") String cId, @Param("tId") String tId);

	/**
	 * 学生查询发布作业表通过s_id和tsc_id
	 *
	 * @author LR
	 * @param sId
	 * @param tscId
	 * @param pwState
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PublishWork> selectStudentPublishWorkBycId(@Param("sId") String sId, @Param("cId") String cId,
			@Param("pwState") Boolean pwState, @Param("start") int start, @Param("end") int end);

	/**
	 * 学生查询发布作业表通过cId数量
	 *
	 * @author LR
	 * @param sId
	 * @param cId
	 * @param pwState
	 * @return
	 */
	public int selectStudentPublishWorkCount(@Param("sId") String sId, @Param("cId") String cId,
			@Param("pwState") Boolean pwState);

	/**
	 * 教师查询发布作业表通过cId
	 *
	 * @author LR
	 * @param tscId
	 * @param pwState
	 * @param start
	 * @param end
	 * @return
	 */
	public List<PublishWork> selectTeacherPublishWorkBycId(@Param("tId") String tId, @Param("cId") String cId,
			@Param("pwState") Boolean pwState, @Param("start") int start, @Param("end") int end);

	/**
	 * 教师查询发布作业表通过cId数量
	 *
	 * @author LR
	 * @param tId
	 * @param cId
	 * @param pwState
	 * @return
	 */
	public int selectTeacherPublishWorkCount(@Param("tId") String tId, @Param("cId") String cId,
			@Param("pwState") Boolean pwState);

	public String selectPwIdname(String pwId);

	/**
	 * 改变作业状态
	 *
	 * @author weiyuhang
	 * @param pwId
	 * @return
	 */
	public void changePublishWork(PublishWork publishwork);

	public List<PublishWork> getAllPublishWorks();

	void updatePublishWorkState(@Param("pwId") String pwId, @Param("flag") Boolean flag);

	public List<Map<String, Object>> getPwDetails(@Param("sId") String sId, @Param("pwId") String pwId);

	public List<Map<String, Object>> getTFiles(@Param("sId") String sId, @Param("pwId") String pwId);

	public List<Map<String, Object>> getSFiles(@Param("sId") String sId, @Param("pwId") String pwId);

	public int countPublishWorks(@Param("rId") String rId, @Param("cId") String cId);

	public int countPublishEstimates(@Param("cId") String cId);

	public void updatePublishWork(PublishWork publishwork);

	public List<Map<String, Object>> getTFilesImages(@Param("sId") String sId, @Param("pwId") String pwId);

	public List<Map<String, Object>> getSFilesImages(@Param("sId") String sId, @Param("pwId") String pwId);

	public int countSPublishEstimates(@Param("cId") String cId, @Param("sId") String sId);

	public List<Map<String, Object>> getTPwDetails(@Param("pwId") String pwId);

	public List<Map<String, Object>> getTTFiles(@Param("pwId") String pwId);

	public List<Map<String, Object>> getTTFilesImages(@Param("pwId") String pwId);

	public List<Map<String, Object>> getTStudentsByPwId(@Param("pwId") String pwId);

	public List<Map<String, Object>> getWorkDetails(@Param("sId") String sId, @Param("pwId") String pwId);

	public List<Map<String, Object>> getFinishStudents(@Param("pwId") String pwId, @Param("start") int start,
			@Param("end") int end);

	public List<Map<String, Object>> getNotFinishStudents(@Param("pwId") String pwId, @Param("start") int start,
			@Param("end") int end);

	public List<Map<String, Object>> getFinishsAndNotCheckStudent(@Param("pwId") String pwId, @Param("start") int start,
			@Param("end") int end);

	public int countFinishStudents(@Param("pwId") String pwId);

	public int countNotFinishStudents(@Param("pwId") String pwId);

	public int countFinishsAndNotCheckStudent(@Param("pwId") String pwId);

	public Map<String, Object> selectpublishwork(@Param("pwId") String pwId);

	public List<Map<String, Object>> selectTeacherFile(@Param("pwId") String pwId);

	public List<Map<String, Object>> getWorkBySid(@Param("pwId") String pwId, @Param("sId") String sId);

	public void deletePublishWork(@Param("pwId") String pwId);
}