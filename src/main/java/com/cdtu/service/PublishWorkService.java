package com.cdtu.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface PublishWorkService {

	void changePublishWorkStateByConparetoEndTime();

	List<Map<String, Object>> getPwDetails(String sId, String pwId);

	List<Map<String, Object>> getTFiles(String sId, String pwId);

	List<Map<String, Object>> getSFiles(String sId, String pwId);

	int countPublishWorks(String rId, String cId);

	int countPublishEstimates(String string);

	List<Map<String, Object>> getTFilesImages(String sId, String pwId);

	List<Map<String, Object>> getSFilesImages(String sId, String pwId);

	int countSPublishEstimates(String cId, String sId);

	List<Map<String, Object>> getTPwDetails(String pwId);

	List<Map<String, Object>> getTTFiles(String pwId);

	List<Map<String, Object>> getTTFilesImages(String pwId);

	List<Map<String, Object>> getTStudents(String pwId);

	List<Map<String, Object>> getWorkDetails(String sId, String pwId);

	List<Map<String, Object>> getStudentsBywStateAndpwId(int state, String pwId, int page);

	Map<String, Object> selectPublishwork(String pwId);

	List<Map<String, Object>> selectTeacherFile(String pwId);

	List<Map<String, Object>> getWorkBySid(String pwId, String sId);

	void deletePublishWorkService(@Param("pwId") String pwId);

}
