package com.cdtu.mapper;

import java.util.List;
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
	public List<Map<String, Object>> selectByTIdAndCID(@Param("tId") String tId, @Param("cId") String cId);

	/**
	 * @author 李红兵
	 */
	public Map<String, Object> selectTimeCodeStatus(@Param("psId") String psId, @Param("sId") String sId);

	/**
	 * @author 李红兵
	 */
	public void insert(@Param("psId") String psId, @Param("tId") String tId, @Param("cId") String cId,
			@Param("time") String time, @Param("checkCode") String checkCode);
}
