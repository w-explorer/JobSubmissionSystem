package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface WorkService {
	public List<Map<String, Object>> staSubCon(String pwId);

	public List<Map<String, Object>> getAllWorks(String sId, String cId);

	public List<Map<String, Object>> getScoreInLastDays(String sId, String cId, int days);

	public List<Map<String, Object>> fuzzySearchWorkBySidAndCid(String sId, String cId, String pwName);

	public List<Map<String, Object>> fuzzySearchWorkByTidAndCid(String tId, String cId, String pwName);

	
	public void insertTeacherFilewAddr (String pwId, String wAddr, String filename); 
    
	public void deleteTeacherFile(Integer tfId);
	
	public void deleteStudentFile(Integer sfId);
	public List<Map<String, Object>> SearchPwByPwName(String tId, String cId, String pwName);

	public List<Map<String, Object>> SsearchPwByPwName(String sId, String cId, String pwName);

}
