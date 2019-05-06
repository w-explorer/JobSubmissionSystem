package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdtu.mapper.CourseMapper;
import com.cdtu.service.CourseService;

@Transactional
@Service(value = "courseService")
public class CourseServiceImpl implements CourseService {
	public @Resource CourseMapper courseMapper;

	/**
	 * 判断课程是否存在
	 *
	 * @author 李红兵
	 */
	@Override
	public boolean isExisted(String cId) {
		return courseMapper.countByCId(cId) != 0;
	}

	/**
	 * 根据课程号查课程
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getCourseByCId(String cId, String sId) {
		Map<String, Object> map = courseMapper.selectByCId(cId, sId);
		map.put("joined", "1".equals(map.get("joined").toString()));// 1：true，0：false
		return map;
	}

	/**
	 * 根据课堂号查询课程详情
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getDetails(String cId) {
		return courseMapper.selectDetails(cId);
	}

	@Override
	public List<Map<String, Object>> selectStudents(int page, String id, String cId) {
		System.out.println(page + " /////" + id + cId);
		return courseMapper.selectStudents((page - 1) * 30, page * 30, id, cId);
	}

	@Override
	public List<String> selectAllEmailInClass(String id, String cId) {
		// TODO Auto-generated method stub
		System.out.println(id + cId + "////");
		return courseMapper.selectAllEmailInClass(id, cId);
	}

	@Override
	public String selectCnameByCid(String cId) {
		// TODO Auto-generated method stub
		return courseMapper.selectCnameByCid(cId);
	}
}
