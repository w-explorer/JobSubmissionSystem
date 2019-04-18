package com.cdtu.mapper;

import org.apache.ibatis.annotations.Param;

public interface TeacherFileMapper {
	/**
	 * @author 李红兵
	 */
	void insert(@Param("tId") String tId, @Param("cId") String cId, @Param("pathName") String pathName,
			@Param("fileName") String fileName, @Param("onlineReadAble") boolean onlineReadAble,
			@Param("fileType") String fileType);

	/**
	 * @author 李红兵
	 */
	void delete(@Param("pathName") String pathName, @Param("fileName") String fileName);
}
