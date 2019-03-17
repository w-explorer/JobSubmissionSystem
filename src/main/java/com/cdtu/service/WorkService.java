package com.cdtu.service;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

public interface WorkService {
	public List<Map<String, Object>> staSubCon(String pwId);

	public List<Map<String, Object>> getAllWorks(String sId, String cId);

	public List<Map<String, Object>> getScoreInLastDays(String sId, String cId, int days);

	public List<Map<String, Object>> fuzzySearchWorkBySidAndCid(String sId, String cId, String pwName);

	public List<Map<String, Object>> fuzzySearchWorkByTidAndCid(String tId, String cId, String pwName);

	
	public void insertTeacherFilewAddr (String pwId, String wAddr, String filename,String type,Boolean state); 
    
	public void deleteTeacherFile(Integer tfId);
	
	public void deleteStudentFile(Integer sfId);
	public List<Map<String, Object>> SearchPwByPwName(String tId, String cId, String pwName);

	public List<Map<String, Object>> SsearchPwByPwName(String sId, String cId, String pwName);

	public String selectwId(String sId, String pwId);

	public void insertStudentFilewAddr(String wId, String wAddr, String filename, String type, Boolean state);
  /**
   * @author weiyuhang
   * 查询文件名
   */
	 public String selecttfNameService(String tfAdd);
	  public String selectsfNameService(String sfAdd);
	  /**
	    * 查询作业的所有文件地址
	    * @author weiyuhang
	    */
		public List<Map<String, Object>> selectWorkAllAddr(String pwId);
		public List<Map<String, Object>> selectWorkId(String pwId);
		public Map<String, Object> selectcName(String pwId);
		public void teacherupdatework(Map<String,Object> map);
		public Map<String, Object> selectestimate(String epId);
}
