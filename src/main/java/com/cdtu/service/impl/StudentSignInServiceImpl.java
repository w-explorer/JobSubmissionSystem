package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.StudentSelectCourseMapper;
import com.cdtu.mapper.StudentSignInMapper;
import com.cdtu.service.StudentSignInService;
import com.cdtu.util.OAUtil;

@Service(value = "ssService")
public class StudentSignInServiceImpl implements StudentSignInService {
	private @Resource StudentSignInMapper ssMapper;
	private @Resource StudentSelectCourseMapper sscMapper;

	/**
	 * 初始化学生签到表数据
	 *
	 * @author 李红兵
	 */
	@Override
	public void initDatabase(String psId, String cId) {
		List<Map<String, Object>> list = sscMapper.selectSIds(cId);
		list.forEach(map -> {
			map.put("ssId", OAUtil.getId());
			map.put("psId", psId);
		});
		ssMapper.insertAll(list);
	}

	/**
	 * 获取学生是否已签到
	 *
	 * @author 李红兵
	 */
	@Override
	public boolean isSigned(String psId, String sId) {
		return ssMapper.selectStatus(psId, sId);
	}

	/**
	 * 进行签到
	 *
	 * @author 李红兵
	 */
	@Override
	public void signIn(String psId, String sId, String time, String mark) {
		ssMapper.updateStudentSignIn(psId, sId, time, mark);
	}

	/**
	 * 获取学生签到状态
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getStudentSignIn(String psId, String sId) {
		return ssMapper.selectByPsIdAndSId(psId, sId);
	}
}
