/**
 *
 */
package com.cdtu.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.TeacherSelectCourseMapper;
import com.cdtu.service.TeacherSelectCourseService;

@Service(value = "tscService")

public class TeacherSelectCourseServiceImpl implements TeacherSelectCourseService {
	private @Resource TeacherSelectCourseMapper tscMapper;

	/*
	 * 获取老师和课程名称
	 */
	@Override
	public Map<String, Object> getTNameAndCName(String tId, String cId) {
		return tscMapper.selectByTIdAndCId(tId, cId);
	}

}
