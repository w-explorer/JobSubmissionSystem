package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StudentSignInMapper {
	/**
	 * @author 李红兵
	 */
	public void insertAll(@Param("list") List<Map<String, Object>> list);
}
