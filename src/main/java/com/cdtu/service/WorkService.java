package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface WorkService {
	public List<Map<String, Object>> staSubCon(String pwId);

	public List<Map<String, Object>> getAllWorks(String sId, int cId);
}
