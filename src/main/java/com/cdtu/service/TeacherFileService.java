package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface TeacherFileService {
	public void deleteFile(String pathName, String fileName);

	public List<Map<String, Object>> getResource(String rId, String cId);

	public void uploadFile(String tId, String cId, String pathName, String fileName, boolean onlineReadAble,
			String fileType);
}
