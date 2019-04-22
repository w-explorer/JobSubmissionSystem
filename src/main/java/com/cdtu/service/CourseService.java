package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface CourseService {
	public boolean isExisted(String cId);

	public Map<String, Object> getDetails(String cId);

	public Map<String, Object> getCourseByCId(String cId, String sId);

	public List<Map<String, Object>> selectStudents(int page,String cId,String id);
}
