package com.cdtu.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.PublishSignInMapper;
import com.cdtu.service.PublishSignInService;

@Service(value = "psService")
public class PublishSignInServiceImpl implements PublishSignInService {
	private @Resource PublishSignInMapper psMapper;

	/**
	 * 停止签到
	 *
	 * @author 李红兵
	 */
	@Override
	public void stopSignIn(String psId) {
		psMapper.update(psId);
	}

	/**
	 * 开始签到
	 *
	 * @author 李红兵
	 */
	@Override
	public void startSignIn(String psId, String tId, String cId, String time, String checkCode) {
		psMapper.insert(psId, tId, cId, time, checkCode);
	}
}
