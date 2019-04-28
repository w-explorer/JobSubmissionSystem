package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface SignInService {
	public int getDueNum(String psId);

	public void stopSignIn(String psId);

	public int getActualNum(String psId);

	public void cancelSignIn(String string);

	public void signIn(String psId, String sId);

	public String getCurrPsId(String tId, String cId);

	public boolean isSignIned(String psId, String sId);

	public boolean isSignIning(String tId, String cId);

	public String getCheckCode(String psId, String sId);

	public List<Map<String, Object>> getSignInConditions(String psId);

	public Map<String, Object> getPublishSignIn(String sId, String cId);

	public Map<String, Object> getStudentSignIn(String psId, String sId);

	public List<Map<String, Object>> getStudentSignIns(String sId, String cId);

	public List<Map<String, Object>> getPublishSignIns(String tId, String cId);

	public void startSignIn(String psId, String tId, String cId, String startTime, String lateTime, String stopTime,
			String checkCode);

}
