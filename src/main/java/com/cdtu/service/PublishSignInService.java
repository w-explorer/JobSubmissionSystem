package com.cdtu.service;

import java.util.Map;

public interface PublishSignInService {
	public void stopSignIn(String psId);

	public Map<String, Object> getTimeCodeStatus(String psId, String sId);

	public Map<String, Object> getPublishSignIn(String sId, String cId);

	public void startSignIn(String psId, String tId, String cId, String time, String checkCode);
}
