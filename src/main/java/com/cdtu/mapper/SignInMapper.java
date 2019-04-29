package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

/**
 * 签到mapper
 *
 * @author 李红兵
 *
 */
public interface SignInMapper {
	public int selectDueNum(@Param("psId") String psId);

	public int selectActualNum(@Param("psId") String psId);

	public String selectCheckCode(@Param("psId") String psId);

	public void updatePublishSignIn(@Param("psId") String psId);

	public void deletePublishSignIn(@Param("psId") String psId);

	public String selectPsId(@Param("tId") String tId, @Param("cId") String cId);

	public void insertAllToSignIn(@Param("list") List<Map<String, Object>> list);

	public int countPublishSignIn(@Param("tId") String tId, @Param("cId") String cId);

	public void updateSignMark(@Param("ssId") String ssId, @Param("mark") String mark);

	public List<Map<String, Object>> selectSignInCondition(@Param("psId") String psId);

	public void updateStudentSignIn(@Param("psId") String psId, @Param("sId") String sId);

	public boolean selectSignInStatus(@Param("psId") String psId, @Param("sId") String sId);

	public Map<String, Object> selectPublishSignIn(@Param("sId") String sId, @Param("cId") String cId);

	public Map<String, Object> selectStudentSignIn(@Param("psId") String psId, @Param("sId") String sId);

	public List<Map<String, Object>> selectPublishSignIns(@Param("tId") String tId, @Param("cId") String cId);

	public List<Map<String, Object>> selectStudentSignIns(@Param("sId") String sId, @Param("cId") String cId);

	public void insertPublishSignIn(@Param("psId") String psId, @Param("tId") String tId, @Param("cId") String cId,
			@Param("startTime") String startTime, @Param("lateTime") String lateTime,
			@Param("stopTime") String stopTime, @Param("checkCode") String checkCode);

	public List<Map<String, Object>> selectSignInByStatus(@Param("psId") String psId, @Param("status") boolean status);

}
