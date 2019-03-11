package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface StudentSelectCourseService {
	public boolean isJoined(String cId, String sId);

	public void joinCourse(String cId, String sId);

	public int countStudents(String cId);

	public List<Map<String, Object>> getJoinedCourses(String sId);

	public List<Map<String, Object>> getStudents(String cId, int page);
}
