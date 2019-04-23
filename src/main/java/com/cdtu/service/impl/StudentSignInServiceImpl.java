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

}
