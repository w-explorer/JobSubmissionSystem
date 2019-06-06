package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface CourseNoticeMapper {
	
/**
 * 	
 * 对公告进行增删查
 * @return
 */
public List<Map<String,Object>> selectCourseNotices(@Param("tscId") int tscId);
public List<Map<String,Object>> selectCourseNoticess(@Param("cId") String cId,@Param("sId") String sId);
public void deleteCourseNotice(@Param("cnId")int cnId);
public void insertCourseNotice(@Param("cnTitle")String cnTitle,
@Param("cnContent")String cnContent,@Param("tscId")int tscId,@Param("cnPdate")String cnPdate);

}
