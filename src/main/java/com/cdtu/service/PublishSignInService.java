package com.cdtu.service;

public interface PublishSignInService {
	public void stopSignIn(String psId);

	public void startSignIn(String psId, String tId, String cId, String time, String checkCode);
}
