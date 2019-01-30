package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface StudentSelectCourseService {
	public boolean isJoined(int cId, String sId);

	public void joinCourse(int cId, String sId);

	public List<Map<String, Object>> getJoinedCourses(String sId);
}
