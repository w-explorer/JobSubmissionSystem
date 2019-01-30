package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.StudentSelectCourseMapper;
import com.cdtu.service.StudentSelectCourseService;

@Service("sscService")
public class StudentSelectCourseServiceImpl implements StudentSelectCourseService {
	private @Resource StudentSelectCourseMapper sscMapper;

	/**
	 * 根据课程id和学生id判断学生id对应的学生是否已选择课程id所对应的课程
	 *
	 * @author 李红兵
	 */
	@Override
	public boolean isJoined(int cId, String sId) {
		return sscMapper.countBySIdAndCId(cId, sId) != 0;
	}

	/**
	 * 完成学生选课操作
	 *
	 * @author 李红兵
	 */
	@Override
	public void joinCourse(int cId, String sId) {
		sscMapper.insert(cId, sId);
	}

	/**
	 * 根据学生id得到该学生所选择的所有课程
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getJoinedCourses(String sId) {
		return sscMapper.selectBySId(sId);
	}
}
