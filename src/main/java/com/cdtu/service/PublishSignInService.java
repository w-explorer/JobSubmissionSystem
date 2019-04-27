package com.cdtu.service;

import java.util.Map;

public interface PublishSignInService {
	public void stopSignIn(String psId);

	public boolean isSignIning(String tId, String cId);

	public Map<String, Object> getPublishSignIn(String sId, String cId);

	public String getCheckCode(String psId, String sId);

	public void startSignIn(String psId, String tId, String cId, String startTime, String lateTime, String stopTime,
			String checkCode);
}
