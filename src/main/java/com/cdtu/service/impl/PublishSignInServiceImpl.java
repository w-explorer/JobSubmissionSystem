package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.PublishSignInMapper;
import com.cdtu.service.PublishSignInService;
import com.cdtu.util.MyDateUtil;

@Service(value = "psService")
public class PublishSignInServiceImpl implements PublishSignInService {
	private @Resource PublishSignInMapper psMapper;

	/**
	 *
	 * 查看本节课（95分钟）是否有签到
	 *
	 * @author 李红兵
	 */
	@Override
	public boolean isSignIning(String tId, String cId) {
		return psMapper.count(tId, cId) == 1;
	}

	/**
	 * 老师获取签到情况
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getSignInCondition(String tId, String cId) {
		List<Map<String, Object>> maps = psMapper.selectByTIdAndCID(tId, cId);
		maps.forEach(map -> {
			Object timeStamp = map.get("ssTime");
			if (timeStamp != null) {
				map.put("ssTime", MyDateUtil.getFormattedTime(timeStamp, "HH:mm:ss"));
			}
		});
		return maps;
	}

	/**
	 * 获取发布的签到信息
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getPublishSignIn(String sId, String cId) {
		return psMapper.selectBySIdAndCId(sId, cId);
	}

	/**
	 * 获取签到验证码
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getTimeCodeStatus(String psId, String sId) {
		return psMapper.selectTimeCodeStatus(psId, sId);
	}

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
