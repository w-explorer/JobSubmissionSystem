package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface StudentSignInMapper {
	/**
	 * @author 李红兵
	 */
	public void insertAll(@Param("list") List<Map<String, Object>> list);

	/**
	 * @author 李红兵
	 */
	public List<Map<String, Object>> selectByPsId(@Param("psId") String psId);

	/**
	 * @author 李红兵
	 */
	public boolean selectStatus(@Param("psId") String psId, @Param("sId") String sId);

	/**
	 * @author 李红兵
	 */
	public Map<String, Object> selectByPsIdAndSId(@Param("psId") String psId, @Param("sId") String sId);

	/**
	 * @author 李红兵
	 */
	public void updateStudentSignIn(@Param("psId") String psId, @Param("sId") String sId);
}
