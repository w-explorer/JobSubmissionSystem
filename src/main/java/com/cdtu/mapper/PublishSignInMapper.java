package com.cdtu.mapper;

import org.apache.ibatis.annotations.Param;

public interface PublishSignInMapper {
	/**
	 * @author 李红兵
	 */
	public void update(@Param("psId") String psId);

	/**
	 * @author 李红兵
	 */
	public void insert(@Param("psId") String psId, @Param("tId") String tId, @Param("cId") String cId,
			@Param("time") String time, @Param("checkCode") String checkCode);
}
