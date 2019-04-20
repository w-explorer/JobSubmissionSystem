package com.cdtu.controller;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdtu.service.AdminstratorService;
import com.cdtu.service.CourseService;
import com.cdtu.service.EstimateService;
import com.cdtu.service.TeacherSelectCourseService;
import com.cdtu.util.MyExceptionResolver;

@Controller
@RequestMapping(value = "admin")
public class AdminController {
	private @Resource(name = "courseService") CourseService courseService;
	private @Resource(name = "adminstratorService") AdminstratorService admin;
	private @Resource(name = "estimateService") EstimateService estimateService;
	private @Resource(name = "tscService") TeacherSelectCourseService tscService;

	/**
	 * 查询学院
	 *
	 * @author weiyuhang
	 */
	@RequestMapping(value = "selectSchool.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> selectSchool() {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("Schools", admin.selectSchool());
			map.put("status", 200);
		} catch (Exception e) {
			map.put("status", 0);
			map.put("msg", "服务器异常");
		}
		return map;
	}

	/**
	 * 查询课程
	 *
	 * @author weiyuhang
	 */
	@RequestMapping(value = "selectCourse")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> selectCourse(@RequestBody Map<String, Object> pmap) {
		return pmap;
	}

	/**
	 * 管理员统计某个老师对应课程的所有学生的评价
	 *
	 * @author 李红兵
	 */
	@RequiresRoles(value = { "admin" })
	@RequestMapping(value = "staEstByCourse.do")
	public @ResponseBody Map<String, Object> doStaEstByCourse(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String tId = (String) paramsMap.get("tId");
			String cId = (String) paramsMap.get("cId");
			map.putAll(tscService.getTNameAndCName(tId, cId));
			map.put("estimats", estimateService.getEstimats(tId, cId));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}
}
