package com.cdtu.service;

import java.util.Map;

public interface ClassCreateService {
	public boolean isExisted(int cId);

	public Map<String, Object> getCourseByCId(int cId, String sId);
}
