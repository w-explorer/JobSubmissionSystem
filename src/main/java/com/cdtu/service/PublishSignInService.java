package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface PublishSignInService {
	public void stopSignIn(String psId);

	public boolean isSignIning(String tId, String cId);

	public Map<String, Object> getPublishSignIn(String sId, String cId);

	public Map<String, Object> getTimeCodeStatus(String psId, String sId);

	public List<Map<String, Object>> getSignInCondition(String tId, String cId);

	public void startSignIn(String psId, String tId, String cId, String time, String checkCode);
}
