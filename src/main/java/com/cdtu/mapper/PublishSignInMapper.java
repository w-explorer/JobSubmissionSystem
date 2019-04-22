package com.cdtu.mapper;

import org.apache.ibatis.annotations.Param;

public interface PublishSignInMapper {
	public void insert(@Param("psId") String psId, @Param("tId") String tId, @Param("cId") String cId,
			@Param("time") String time);

}
