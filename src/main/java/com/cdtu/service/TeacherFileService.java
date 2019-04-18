package com.cdtu.service;

public interface TeacherFileService {
	public void uploadFile(String tId, String cId, String pathName, String fileName, boolean onlineReadAble,
			String fileType);

	public void deleteFile(String pathName, String fileName);
}
