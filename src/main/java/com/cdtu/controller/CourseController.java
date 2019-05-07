package com.cdtu.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdtu.model.CourseWapper;
import com.cdtu.model.Role;
import com.cdtu.service.CourseService;
import com.cdtu.service.PublishWorkService;
import com.cdtu.service.StudentSelectCourseService;
import com.cdtu.service.StudentService;
import com.cdtu.service.TeacherService;
import com.cdtu.util.MaxPage;
import com.cdtu.util.MyExceptionResolver;

@Controller
@RequestMapping(value = "course")
public class CourseController {
	private @Resource(name = "courseService") CourseService courseService;
	private @Resource(name = "sscService") StudentSelectCourseService sscService;
	private @Resource(name = "publishWorkService") PublishWorkService publishWorkService;
	private @Resource(name = "teacherService") TeacherService teacherService;
	private @Resource(name = "studentService") StudentService studentService;

	/**
	 * 查询课堂详情
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequestMapping(value = "/details.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public Map<String, Object> doDetails(@RequestBody Map<String, Object> paramsMap, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		try {
			Role role = (Role) SecurityUtils.getSubject().getPrincipal();
			String cId = (String) paramsMap.get("cId");
			String rId = role.getUsername();
			map.putAll(courseService.getDetails(cId));// 未进行空值校验，后期斟酌
			map.put("stusNum", sscService.countStudents(rId, cId));// 学生数量
			map.put("pubWNum", publishWorkService.countPublishWorks(rId, cId));// 活动数量
			if ("teacher".equals(role.getRole())) {
				map.put("pubENum", publishWorkService.countPublishEstimates(cId, null));// 评价数量（？）
			} else {
				map.put("pubENum", publishWorkService.countSPublishEstimates(cId, rId));// 评价数量（？）
			}
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 查看课堂成员
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequestMapping(value = "/queryStudents.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public Map<String, Object> doQueryStudents(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			int page = (int) paramsMap.get("page");
			int stusNum = sscService.countStudents(null, cId);
			map.put("stusNum", stusNum);
			map.put("students", sscService.getStudents(cId, page));
			map.put("pageNum", MaxPage.getMaxPage(stusNum, 30));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 执行查询选课记录操作，返回选课列表
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	@RequestMapping(value = "/queryJoinedCourses.do")
	public Map<String, Object> doQueryJoinedCourses() {
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String roleName = role.getRole();
		String id = role.getUsername();
		Map<String, Object> map = new HashMap<>();
		if ("teacher".equals(roleName)) {
			List<CourseWapper> courseList;
			try {
				courseList = teacherService.selectAllCourceService(id);
				map.put("status", 200);
				map.put("courseList", courseList);
			} catch (Exception e) {
				MyExceptionResolver.handlException(map, e);
			}
			return map;
		} else {
			try {
				map.put("status", 200);
				map.put("courseList", sscService.getJoinedCourses(id));
			} catch (Exception e) {
				MyExceptionResolver.handlException(map, e);
			}
			return map;
		}
	}

	/**
	 * 通过建立一张临时表 存储班级成绩排名 每个同学的总分(sql 高级排名 开启mybatis 一次执行多条SQL )
	 * 怎么开启呢？在拼装mysql链接的url时，为其加上allowMultiQueries参数，设置为true，如下：
	 * jdbc.jdbcUrl=jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true
	 *
	 * @author 文成
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "membersOfClass.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> SearchStudentsBycId(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String id = role.getUsername();
		String roleName = role.getRole();
		String cId = (String) paramsMap.get("cId");
		int pubWNum = publishWorkService.countPublishWorks(null, cId);// 用于解决 班级排名
		int page = (int) paramsMap.get("page");
		int stusNum = sscService.countStudents(null, cId);
		if (pubWNum == 0) {
			map.put("students", courseService.selectStudents(page, cId, id));
		} else {
			try {
				if ("teacher".equals(roleName)) {
					studentService.CreatStudentTableDescRank(cId, id);
				} else {
					studentService.CreatStudentTableDescRankByStudent(cId, id);
				}
				map.put("students", studentService.selectStudents(page));
			} catch (Exception e) {
				MyExceptionResolver.handlException(map, e);
			}
		}
		map.put("stusNum", stusNum);
		map.put("max", MaxPage.getMaxPage(stusNum, 30));
		map.put("status", 200);
		return map;
	}

}
