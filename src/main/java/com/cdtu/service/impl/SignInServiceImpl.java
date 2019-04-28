package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.SignInMapper;
import com.cdtu.mapper.StudentSelectCourseMapper;
import com.cdtu.service.SignInService;
import com.cdtu.util.MyDateUtil;
import com.cdtu.util.OAUtil;

@Service(value = "signInService")
public class SignInServiceImpl implements SignInService {
	private @Resource SignInMapper signInMapper;
	private @Resource StudentSelectCourseMapper sscMapper;

	/**
	 * 获取应到人数
	 *
	 * @author 李红兵
	 */
	@Override
	public int getDueNum(String psId) {
		return signInMapper.selectDueNum(psId);
	}

	/**
	 * 停止签到
	 *
	 * @author 李红兵
	 */
	@Override
	public void stopSignIn(String psId) {
		signInMapper.updatePublishSignIn(psId);
	}

	/**
	 * 获取实到人数
	 *
	 * @author 李红兵
	 */
	@Override
	public int getActualNum(String psId) {
		return signInMapper.selectActualNum(psId);
	}

	/**
	 * 取消签到
	 *
	 * @author 李红兵
	 */
	@Override
	public void cancelSignIn(String psId) {
		signInMapper.deletePublishSignIn(psId);
	}

	/**
	 * 进行签到
	 *
	 * @author 李红兵
	 */
	@Override
	public void signIn(String psId, String sId) {
		signInMapper.updateStudentSignIn(psId, sId);
	}

	/**
	 * 根据老师id和课程id获取正在进行中的发布签到id
	 *
	 * @author 李红兵
	 */
	@Override
	public String getCurrPsId(String tId, String cId) {
		return signInMapper.selectPsId(tId, cId);
	}

	/**
	 * 获取学生是否已签到
	 *
	 * @author 李红兵
	 */
	@Override
	public boolean isSignIned(String psId, String sId) {
		return signInMapper.selectSignInStatus(psId, sId);
	}

	/**
	 * 查看当前是否正在签到
	 *
	 * @author 李红兵
	 */
	@Override
	public boolean isSignIning(String tId, String cId) {
		return signInMapper.countPublishSignIn(tId, cId) == 1;
	}

	/**
	 * 获取签到验证码
	 *
	 * @author 李红兵
	 */
	@Override
	public String getCheckCode(String psId, String sId) {
		return signInMapper.selectCheckCode(psId, sId);
	}

	/**
	 * 老师获取签到情况
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getSignInConditions(String psId) {
		List<Map<String, Object>> maps = signInMapper.selectSignInConditions(psId);
		maps.forEach(map -> {
			Object timeStamp = map.get("ssTime");
			if (timeStamp != null) {
				map.put("ssTime", MyDateUtil.getFormattedTime(timeStamp, "HH:mm:ss"));
			}
		});
		return maps;
	}

	/**
	 * 获取老师当前发布的签到信息
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getPublishSignIn(String sId, String cId) {
		return signInMapper.selectPublishSignIn(sId, cId);
	}

	/**
	 * 获取学生签到信息
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getStudentSignIn(String psId, String sId) {
		return signInMapper.selectStudentSignIn(psId, sId);
	}

	/**
	 * 获取该学生该课程的所有签到信息
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getStudentSignIns(String sId, String cId) {
		return signInMapper.selectStudentSignIns(sId, cId);
	}

	/**
	 * 获取该老师该课程的所有签到信息
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getPublishSignIns(String tId, String cId) {
		return signInMapper.selectPublishSignIns(tId, cId);
	}

	/**
	 * 开始签到
	 *
	 * @author 李红兵
	 */
	@Override
	public void startSignIn(String psId, String tId, String cId, String startTime, String lateTime, String stopTime,
			String checkCode) {
		signInMapper.insertPublishSignIn(psId, tId, cId, startTime, lateTime, stopTime, checkCode);
		List<Map<String, Object>> maps = sscMapper.selectSIds(cId);
		maps.forEach(map -> {
			map.put("ssId", OAUtil.getId());
			map.put("psId", psId);
		});
		signInMapper.insertAllToSignIn(maps);
	}
}
