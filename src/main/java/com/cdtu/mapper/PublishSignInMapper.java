package com.cdtu.mapper;

import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PublishSignInMapper {
	/**
	 * @author 李红兵
	 */
	public void update(@Param("psId") String psId);

	/**
	 * @author 李红兵
	 */
	public int count(@Param("tId") String tId, @Param("cId") String cId);

	/**
	 * @author 李红兵
	 */
	public Map<String, Object> selectBySIdAndCId(@Param("sId") String sId, @Param("cId") String cId);

	/**
	 * @author 李红兵
	 */
	public String selectCheckCode(@Param("psId") String psId, @Param("sId") String sId);

	/**
	 * @author 李红兵
	 */
	public void insert(@Param("psId") String psId, @Param("tId") String tId, @Param("cId") String cId,
			@Param("startTime") String startTime, @Param("lateTime") String lateTime,
			@Param("stopTime") String stopTime, @Param("checkCode") String checkCode);
}
