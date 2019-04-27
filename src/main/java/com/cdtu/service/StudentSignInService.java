package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface StudentSignInService {
	public void initDatabase(String psId, String cId);

	public boolean isSigned(String psId, String sId);

	public List<Map<String, Object>> getSignInCondition(String psId);

	public void signIn(String psId, String sId);

	public Map<String, Object> getStudentSignIn(String psId, String sId);
}
