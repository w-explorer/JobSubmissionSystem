package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface WorkService {
	public List<Map<String, Object>> staSubCon(String pwId);

	public List<Map<String, Object>> getAllWorks(String sId, int cId);

	public List<Map<String, Object>> fuzzySearchWorkBySidAndCid(String sId, int cId,String pwName);

	public List<Map<String, Object>> fuzzySearchWorkByTidAndCid(String tId, int cId, String pwName);

	public List<Map<String, Object>> SearchPwByPwName(String tId, int cId, String pwName);

	public List<Map<String, Object>> SsearchPwByPwName(String sId, int cId, String pwName);
}
