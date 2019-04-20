package com.cdtu.controller;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.Estimate;
import com.cdtu.model.Role;
import com.cdtu.model.StudentSelectCourse;
import com.cdtu.model.Work;
import com.cdtu.service.CourseService;
import com.cdtu.service.PublishWorkService;
import com.cdtu.service.StudentSelectCourseService;
import com.cdtu.service.StudentService;
import com.cdtu.service.WorkService;
import com.cdtu.util.DownloadFile;
import com.cdtu.util.FileUtil;
import com.cdtu.util.MaxPage;
import com.cdtu.util.MyExceptionResolver;

@Controller
@RequestMapping(value = "student")
public class StudentController {
	private @Resource(name = "workService") WorkService workService;
	private @Resource(name = "courseService") CourseService courseService;
	private @Resource(name = "studentService") StudentService studentService;
	private @Resource(name = "sscService") StudentSelectCourseService sscService;
	private @Resource(name = "publishWorkService") PublishWorkService publishWorkService;

	/**
	 * 执行根据课程号查询课程
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "queryCourse.do")
	public Map<String, Object> doQueryCourse(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			if (courseService.isExisted(cId)) {
				map.put("status", 200);
				map.put("course", courseService.getCourseByCId(cId, sId));
			} else {
				map.put("status", 404);
				map.put("msg", "未查询到课程");
			}
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 执行选课操作，即向选课表插入一条记录
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "joinCourse.do")
	public Map<String, Object> doJoinCourse(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			if (courseService.isExisted(cId)) {
				if (sscService.isJoined(cId, sId)) {
					map.put("status", 500);
					map.put("msg", "已加入该课程");
				} else {
					sscService.joinCourse(cId, sId);
					map.put("status", 200);
					map.put("msg", "成功加入该课程");
				}
			} else {
				map.put("status", 404);
				map.put("msg", "课程未找到");
			}
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 学生查询对应课程的所有作业，参数是学生id和课程id
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "queryWorks.do")
	public Map<String, Object> doQueryWorks(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			map.put("allWorks", workService.getAllWorks(sId, cId));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 学生统计近几天内的某门课程的作业分数
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "statisticScore.do")
	public Map<String, Object> doStatisticScore(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			String cId = (String) paramsMap.get("cId");
			int days = Integer.parseInt((String) paramsMap.get("days"));
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			map.put("scores", workService.getScoreInLastDays(sId, cId, days));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 学生统计某一门课程的所有作业的平均分
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "staWorkInfo.do")
	public Map<String, Object> doStaWorkInfo(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			Role role = new Role();
			Map<String, Object> map1 = new HashMap<>();
			Map<String, Object> map2 = new HashMap<>();
			Map<String, Object> map3 = new HashMap<>();
			Map<String, Object> map4 = new HashMap<>();
			String cId = (String) paramsMap.get("cId");
			String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
			double averSore = workService.getAverScore(sId, cId);
			List<Map<String, Object>> averMaps = new ArrayList<>();
			List<Map<String, Object>> submitMaps = new ArrayList<>();
			Map<String, Object> subInfo = workService.getSubInfo(sId, cId);
			List<Map<String, Object>> myWorkInfo = workService.getMyWorkInfo(sId, cId);
			int[] scores = new int[myWorkInfo.size()];
			String[] names = new String[myWorkInfo.size()];

			role.setUsername(sId);
			map.put("role", studentService.getStudentBysIdAndsPassword(role));

			map1.put("value", averSore);
			map1.put("name", "我的平均分");
			map2.put("value", 100 - averSore);
			map2.put("name", "仍需努力的分数");
			averMaps.add(map1);
			averMaps.add(map2);
			map.put("averScore", averMaps);

			map3.put("value", subInfo.get("submitted"));
			map3.put("name", "已提交");
			map4.put("value", (int) subInfo.get("toSubmit") - (int) subInfo.get("submitted"));
			map4.put("name", "未提交");
			submitMaps.add(map3);
			submitMaps.add(map4);
			map.put("submitMap", submitMaps);

			for (int i = 0; i < myWorkInfo.size(); i++) {
				names[i] = (String) myWorkInfo.get(i).get("name");
				scores[i] = (int) myWorkInfo.get(i).get("score");
			}
			map.put("listString", names);
			map.put("listInt", scores);
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 学生下载老师上传的资源文件
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "student" })
	@RequestMapping(value = "downloadResource.do")
	public Map<String, Object> doDownloadResource(@RequestBody Map<String, Object> paramsMap,
			HttpServletRequest request, HttpServletResponse response) {
		Map<String, Object> map = new HashMap<>();
		try {
			String fileName = (String) paramsMap.get("name");
			String relativePath = (String) paramsMap.get("path");
			String absolutePath = FileUtil.getAbsolutePath(request);
			File file = new File(absolutePath + relativePath, fileName);
			if (FileUtil.downloadFile(file, response)) {
				map.put("status", 200);
			} else {
				map.put("msg", "资源不存在！");
				map.put("status", 404);
			}
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 查询发布的评价
	 *
	 * @author LR
	 * @param studentSelectCourse
	 * @return
	 */
	@RequestMapping("selectPE.do")
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> selectPE(@RequestBody Map<String, Object> paramsMap,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String sId = role.getUsername();
		String cId = (String) paramsMap.get("cId");
		int page = (int) paramsMap.get("page");
		int pubENum = publishWorkService.countSPublishEstimates(cId, sId);
		try {
			map.put("publishEstimates", studentService.selectPublishEstimate(cId, sId, (page - 1) * 5, 5));
			map.put("max", MaxPage.getMaxPage(pubENum, 5));
			map.put("status", 200);
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
			e.printStackTrace();
		}
		return map;

	}

	/**
	 * 提交评价
	 *
	 * @author LR
	 * @param estimate
	 * @return
	 */
	@RequestMapping("remarkOn.do")
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> remarkOn(@RequestBody Estimate estimate) {
		Map<String, Object> msg = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		estimate.setsId(role.getUsername());
		Integer status = studentService.submitEvaluation(estimate);
		if (status == 1) {
			msg.put("status", 200);
			return msg;
		} else {
			msg.put("status", -1);
			msg.put("msg", "用户为空");
			return msg;
		}
	}

	/**
	 * 查询发布作业
	 *
	 * @author LR
	 * @param studentSelectCourse
	 * @param state
	 * @return
	 */
	@RequestMapping("selectPw.dotime")
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> selectPW(@RequestBody StudentSelectCourse studentSelectCourse) {
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> msg = studentService.demonPublishWork(studentSelectCourse, role.getUsername());
		if (msg == null) {
			msg = new HashMap<>();
			msg.put("status", 0);
			msg.put("msg", "信息错误");
			return msg;
		} else {
			msg.put("status", 200);
			return msg;
		}
	}

	/**
	 * 提交作业
	 *
	 * @author LR
	 * @param work
	 * @return
	 */
	@RequestMapping("workOn.do")
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> workOn(@RequestBody Work work) {
		Map<String, Object> msg = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		work.setsId(role.getUsername());
		Integer status = studentService.submitWork(work);

		if (status == 1) {
			msg.put("status", 200);
			return msg;
		} else {
			msg.put("status", -1);
			msg.put("msg", "用户为空");
			return msg;
		}
	}

	/**
	 * 查询单个作业
	 *
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @return
	 */
	@RequestMapping(value = "queryStudentWork.do", method = RequestMethod.POST)
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> queryStudentWork(@RequestBody Work work) {
		Map<String, Object> msg = new HashMap<>();
		Work newWork = studentService.showStudentWork(work.getsId(), work.getPwId());
		if (newWork != null) {
			msg.put("status", 200);
			msg.put("work", newWork);
			return msg;
		} else {
			msg.put("status", 0);
			msg.put("msg", "用户为空");
			return msg;
		}
	}

	/**
	 * 文件上传
	 *
	 * @author LR
	 * @param file
	 * @param sId
	 * @param pwId
	 * @return
	 */
	@RequestMapping("uploadFile.do")
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> uploadFile(@RequestParam("file") CommonsMultipartFile file,
			@RequestParam("pwId") String pwId) {
		Map<String, Object> msg = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String sId = role.getUsername();
		String oldPath = studentService.selectWorkwAddr(sId, pwId);
		try {
			String newPath = FileUtil.updateFile(file, oldPath, sId, pwId);
			if ("-1".equals(newPath)) {
				msg.put("status", -1);
				msg.put("msg", "保存文件类型有误");
				return msg;
			} else {
				Integer status = studentService.updateWorkwAddr(sId, pwId, newPath);
				if (status == 1) {
					msg.put("status", 200);
					return msg;
				} else {
					msg.put("status", -2);
					msg.put("msg", "未知异常");
					return msg;
				}

			}
		} catch (IOException e) {
			msg.put("status", -3);
			msg.put("msg", "IO异常");
			e.printStackTrace();
			return msg;
		}

	}

	/**
	 * 文件下载
	 *
	 * @author weiyuhang
	 * @param sId
	 * @param wAddr
	 * @return
	 */
	@RequestMapping(value = "downloadstudent.do")
	@RequiresRoles({ "student" })
	public void download(@RequestBody Work work, HttpServletRequest request, HttpServletResponse response)
			throws IOException {
		File file = new File(work.getwAddr());
		String fielName = work.getsId() + "_" + studentService.selectStudentName(work.getsId()) + "_" + file.getName();
		DownloadFile.downloadFile(file, fielName, response, request);
		// return new
		// ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
		// headers, HttpStatus.CREATED);
	}

	/**
	 * 查询班级学生
	 *
	 * @author weiyuhang
	 * @param coursewapper
	 * @return
	 */
	@RequestMapping(value = "selectCourseStudent.do")
	@RequiresRoles({ "student" })
	@ResponseBody
	public Map<String, Object> selectCourseStud(@RequestBody CourseWapper coursewapper) {
		List<CourseStudent> coursestudentslist;
		Map<String, Object> data = new HashMap<>();
		try {
			coursestudentslist = studentService.selectCourseStudentService(coursewapper);
			data.put("status", 200);
			data.put("coursestudentslist", coursestudentslist);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("status", 0);
			data.put("msg", "服务器错误");
		}
		return data;
	}

	/**
	 * @author 文成 模糊查询作业
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "fuzzySearchWork.do")
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> fuzzySearchWork(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String cId = (String) paramsMap.get("cId");
		String pwName = (String) paramsMap.get("pwName");
		if ("".equals(pwName)) {
			map.put("status", 200);
			map.put("fuzzySearchWorks", null);
		}
		String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
		try {
			map.put("status", 200);
			map.put("fuzzySearchWorks", workService.fuzzySearchWorkBySidAndCid(sId, cId, pwName));
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 学生按作业名字查询作业
	 *
	 * @author wencheng
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "SearchPwByPwName.dotime")
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> SearchPwByPwName(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String cId = (String) paramsMap.get("cId");
		String pwName = (String) paramsMap.get("pwName");
		String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
		try {
			map.put("status", 200);
			map.put("fuzzySearchWorks", workService.SsearchPwByPwName(sId, cId, pwName));
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	/**
	 * 得到作业详情
	 *
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "getPwDetails.do")
	@RequiresRoles({ "student" })
	public @ResponseBody Map<String, Object> PwDetails(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String pwId = (String) paramsMap.get("pwId");
		String sId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
		try {
			map.put("status", 200);
			map.put("publishWork", publishWorkService.getPwDetails(sId, pwId));
			map.put("teacherFiles", publishWorkService.getTFiles(sId, pwId));
			map.put("teacherFilesImages", publishWorkService.getTFilesImages(sId, pwId));
			map.put("studentFiles", publishWorkService.getSFiles(sId, pwId));
			map.put("studentFilesImages", publishWorkService.getSFilesImages(sId, pwId));
		} catch (Exception e) {
			MyExceptionResolver.handlException(map, e);
		}
		return map;
	}

	@RequestMapping(value = "selectcoursenotice.do")
	@RequiresRoles(value = { "student" }, logical = Logical.OR)
	public @ResponseBody Map<String, Object> selectcoursenotice(@RequestBody Map<String, Object> maps) {
		Map<String, Object> map = new HashMap<>();

		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String sId = role.getUsername();
		map.put("CourseNotices", studentService.selectCoursenoticeSrvice((String) maps.get("cId"), sId));

		map.put("status", 200);
		return map;
	}
}
