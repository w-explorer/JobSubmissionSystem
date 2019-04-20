package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface TeacherFileMapper {
	/**
	 * @author 李红兵
	 */
	public List<Map<String, Object>> select(@Param("rId") String rId, @Param("cId") String cId);

	/**
	 * @author 李红兵
	 */
	public void delete(@Param("pathName") String pathName, @Param("fileName") String fileName);

	/**
	 * @author 李红兵
	 */
	public void insert(@Param("tId") String tId, @Param("cId") String cId, @Param("pathName") String pathName,
			@Param("fileName") String fileName, @Param("onlineReadAble") boolean onlineReadAble,
			@Param("fileType") String fileType);
}
