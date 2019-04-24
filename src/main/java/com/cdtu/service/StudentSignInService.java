package com.cdtu.service;

import java.util.Map;

public interface StudentSignInService {
	public void initDatabase(String psId, String cId);

	public boolean isSigned(String psId, String sId);

	public void signIn(String psId, String sId, String time, String mark);

	public Map<String, Object> getStudentSignIn(String psId, String sId);
}
