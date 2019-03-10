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

import com.cdtu.service.CourseService;
import com.cdtu.service.PublishWorkService;
import com.cdtu.service.StudentSelectCourseService;
import com.cdtu.util.MaxPage;

@Controller
@RequestMapping(value = "/course")
public class CourseController {
	private @Resource(name = "courseService") CourseService courseService;
	private @Resource(name = "sscService") StudentSelectCourseService sscService;
	private @Resource(name = "publishWorkService") PublishWorkService publishWorkService;

	/**
	 * 查询课堂详情
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequestMapping(value = "/details.do")
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	public Map<String, Object> doDetails(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			map.putAll(courseService.getDetails(cId));// 未进行空值校验，后期斟酌s
			int stusNum = sscService.countStudents(cId);
			map.put("stusNum", stusNum);//学生数量
			int pubWNum = publishWorkService.countPublishWorks(cId);
			map.put("pubWNum", pubWNum);//活动数量
			int pubENum =publishWorkService.countPublishEstimates(cId);
			map.put("pubENum", pubENum);//活动数量
			
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
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
			int stusNum = sscService.countStudents(cId);
			map.put("stusNum", stusNum);
			map.put("students", sscService.getStudents(cId, page));
			map.put("pageNum", MaxPage.getMaxPage(stusNum, 30));
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 统一异常处理
	 *
	 * @author 李红兵
	 */
	private void handlException(Map<String, Object> map, Exception e) {
		e.printStackTrace();
		map.put("status", 500);
		map.put("msg", "抱歉，服务器开小差了");
	}
}
