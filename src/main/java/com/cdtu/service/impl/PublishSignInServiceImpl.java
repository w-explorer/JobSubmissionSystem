package com.cdtu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.PublishSignInMapper;
import com.cdtu.service.PublishSignInService;

@Service(value = "psService")
public class PublishSignInServiceImpl implements PublishSignInService {
	private @Resource PublishSignInMapper psMapper;

	@Override
	public void startSignIn(String tId, String cId, String time) {
		String psId = time.replace("-", "").replace(" ", "").replace(":", "");
		psMapper.insert(psId, tId, cId, time);
	}

}
