package com.cdtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdtu.mapper.PublishWorkMapper;
import com.cdtu.model.ClassCreate;
import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.PublishEstimate;
import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.model.Work;
import com.cdtu.service.AdminstratorService;
import com.cdtu.service.PublishWorkService;
import com.cdtu.service.StudentSelectCourseService;
import com.cdtu.service.StudentService;
import com.cdtu.service.TeacherService;
import com.cdtu.service.WorkService;
import com.cdtu.util.DownloadFile;
import com.cdtu.util.MaxPage;

@Controller
@RequestMapping(value = "teacher")
public class TeacherController {
	private @Resource(name = "workService") WorkService workService;
	private @Resource(name = "studentService") StudentService studentService;
	private @Resource(name = "teacherService") TeacherService teacherService;
	private @Resource(name = "sscService") StudentSelectCourseService sscService;
	private @Resource(name = "publishWorkMapper") PublishWorkMapper publishWorkMapper;
	private @Resource(name = "publishWorkService") PublishWorkService publishWorkService;
	private @Resource(name = "adminstratorService") AdminstratorService adminstratorService;

	/**
	 * 老师统计作业提交情况，参数是发布作业码
	 *
	 * @author 李红兵
	 */
	@ResponseBody
	@RequiresRoles(value = { "teacher" })
	@RequestMapping(value = "/statistic.do")
	public Map<String, Object> doStatistic(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			map.put("subCondition", workService.staSubCon((String) paramsMap.get("pwId")));
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

	/**
	 *
	 * @author weiyuhang
	 */
	@RequestMapping("deleteCourse.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> deleteClassCreateJsp(@RequestParam("cId") Integer cId) {
		Map<String, Object> data = new HashMap<>();
		try {
			String msg = teacherService.deleteClassCreateService(cId);
			if ("success".equals(msg)) {
				data.put("status", 200);
				data.put("msg", "删除成功");
			} else {
				data.put("status", 0);
				data.put("msg", "删除失败");
			}
		} catch (Exception e) {
			data.put("status", 0);
			data.put("msg", "无法删除");
		}

		return data;
	}

	/**
	 * 教师查询该课程学生
	 *
	 * @author weiyuhang
	 */
	@RequiresRoles({ "teacher" })
	@RequestMapping("selectCourseStudent.do")
	public @ResponseBody Map<String, Object> selectCourseStudentJsp(@RequestBody CourseWapper coursewapper) {

		List<CourseStudent> coursestudentslist;
		Map<String, Object> data = new HashMap<>();
		try {
			coursestudentslist = teacherService.selectCourseStudentService(coursewapper);
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
	 * 教师查询课程
	 *
	 * @author weiyuhang
	 * @param id
	 * @return
	 */
	@RequestMapping("selectClass.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> selectAllCourse() {

		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String tId = role.getUsername();
		Map<String, Object> data = new HashMap<>();

		List<CourseWapper> courseList;
		try {
			courseList = teacherService.selectAllCourceService(tId);
			data.put("status", 200);
			data.put("courseList", courseList);
		} catch (Exception e) {
			e.printStackTrace();
			data.put("status", 0);
			data.put("msg", "服务器错误");
		}
		return data;
	}

	/**
	 * 添加课程
	 *
	 * @author weiyuhang
	 * @param classcreate
	 * @return
	 */
	@RequestMapping(value = "createClass.do", method = RequestMethod.POST)
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> createClass(@RequestBody ClassCreate classcreate) {
		Map<String, Object> data = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		classcreate.settId(role.getUsername());
		String msg = teacherService.insertClassCreateService(classcreate);
		if ("success".equals(msg)) {
			data.put("msg", "插入成功");
			data.put("status", 200);

		} else {
			data.put("msg", "课程名存在");
			data.put("status", 0);
		}

		return data;
	}

	/**
	 *
	 * 修改老师创建的课程
	 *
	 * @author weiyuhang
	 * @param classcreate
	 * @return
	 */
	@RequestMapping("updateClassCreate.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, String> updateClassCreate(@RequestBody ClassCreate classcreate) {
		Map<String, String> data = new HashMap<>();
		System.out.println("test");
		try {
			String msg = teacherService.updateClassCreateService(classcreate);
			if ("success".equals(msg)) {
				data.put("status", "200");
				data.put("msg", "修改成功");
			} else {
				data.put("status", "0");
				data.put("msg", "修改课程名重复");
			}
		} catch (Exception e) {
			data.put("status", "0");
			data.put("msg", "无法修改");
		}
		return data;
	}

	/**
	 * 老师发布评价
	 *
	 * @author LR
	 * @param publishEstimate
	 * @return
	 */
	@RequestMapping(value = "publishEstimate.do", method = RequestMethod.POST)
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> publishEstimate(@RequestBody PublishEstimate publishEstimate) {
		Map<String, Object> msg = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Integer status = teacherService.PublishEstimate(publishEstimate, role.getUsername());
		if (status == 1) {
			msg.put("status", 200);
		} else {
			if (status == -1) {
				msg.put("status", -1);
				msg.put("msg", "用户为空");
			}
			if (status == 0) {
				msg.put("status", 0);
				msg.put("msg", "错误信息");
			}
		}
		return msg;
	}

	/**
	 * 老师发布作业
	 *
	 * @author LR
	 * @param publishWork
	 * @return
	 */
	@RequestMapping(value = "publishWork.do", method = RequestMethod.POST)
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> publishWork(@RequestBody PublishWork publishWork) {
		Map<String, Object> msg = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		try {
			Role role = (Role) subject.getPrincipal();
			String ms = teacherService.publishWork(publishWork, role.getUsername());
//			System.out.println(publishWork.getPwContent());
			msg.put("status", 200);
			msg.put("pwId", ms);
		} catch (Exception e) {
			msg.put("status", 0);
			msg.put("msg", "错误信息");
		}
		return msg;
	}

	/**
	 * 老师查询发布的作业 //判断时间已到修改课程状态（未解决）
	 *
	 * @author LR
	 * @param courseWapper
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "queryPublishWork.dotime", method = RequestMethod.POST)
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> queryPublishWork(@RequestBody CourseWapper courseWapper) {
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> msg = teacherService.demonPublishWork(courseWapper, role.getUsername());
		if (msg != null) {
			if (msg.get("publishWorks") != null) {
				msg.put("status", 200);
				return msg;
			}
			msg.put("status", 0);
			return msg;
		} else {
			msg = new HashMap<>();
			msg.put("status", 0);
			msg.put("msg", "课程为空");
			return msg;
		}
	}

	/**
	 * 查询作业全部学生
	 *
	 * @author LR
	 * @param courseWapper
	 * @param pwId
	 * @return
	 */
	@RequestMapping(value = "queryStudentsInWork.do", method = RequestMethod.POST)
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> queryStudentInWork(@RequestBody CourseWapper courseWapper) {
		Map<String, Object> msg = teacherService.showStudentInWork(courseWapper);
		if (msg != null) {
			msg.put("status", 200);
			return msg;
		} else {
			msg = new HashMap<>();
			msg.put("status", 0);
			msg.put("msg", "课程为空");
			return msg;
		}
	}

	/**
	 * 对学生作业的点评
	 *
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @param wRemark
	 * @return
	 */
	@RequestMapping(value = "remarkStudentWork.do", method = RequestMethod.POST)
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> remarkStudentWork(@RequestBody Work work) {
		Map<String, Object> msg = new HashMap<>();
		Integer status = teacherService.addWorkRemark(work);
		if (status == 1) {
			msg.put("status", 200);
		}
		if (status == -1) {
			msg.put("status", 0);
			msg.put("msg", "输入值为空");
		}
		if (status == 0) {
			msg.put("status", 0);
			msg.put("msg", "学生未提交作业");
		}
		return msg;
	}

	/**
	 * 查询单个学生的作业
	 *
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @return
	 */
	@RequestMapping(value = "queryStudentWork.do", method = RequestMethod.POST)
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> queryStudentWork(@RequestBody Work work) {
		Map<String, Object> msg = new HashMap<>();
		Work newWork = teacherService.selectStudentWork(work.getsId(), work.getPwId());
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
	 * 文件下载
	 *
	 * @author weiyuhang
	 * @param sId
	 * @param wAddr
	 * @return
	 */
	@RequestMapping(value = "downloadteache.do")
	@RequiresRoles({ "teacher" })
	public void download(@RequestBody Work work, HttpServletResponse response, HttpServletRequest request)
			throws IOException {
		File file = new File(work.getwAddr());
		String fileName = work.getsId() + "_" + teacherService.selectStudentName(work.getsId()) + "_" + file.getName();
		DownloadFile.downloadFile(file, fileName, response, request);
		// return new
		// ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),
		// path, HttpStatus.CREATED);
	}

	/**
	 * 批量下载
	 *
	 * @author WYH
	 * @param sIds
	 * @param pwId
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "downLoadAll.do")
	@RequiresRoles({ "teacher" })
	@ResponseBody
	public void downloadAll(@RequestBody Work work, HttpServletResponse response, HttpServletRequest request)
			throws IOException {

		// 需要压缩的文件
		// 压缩后的文件
		String resourcesName = teacherService.selectPwIdname(work.getPwId()) + ".zip";
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("D:/" + resourcesName));
		InputStream input = null;

		for (String sId : work.getsIds()) {
			Work newWork = teacherService.selectStudentWork(sId, work.getPwId());
			File file = new File(newWork.getwAddr());
			input = new FileInputStream(file);
			zipOut.putNextEntry(new ZipEntry(sId + "_" + teacherService.selectStudentName(sId) + "_" + file.getName()));
			int temp = 0;
			while ((temp = input.read()) != -1) {
				zipOut.write(temp);
			}
			zipOut.closeEntry();
			input.close();
		}
		zipOut.close();
		File file = new File("D:/" + resourcesName);
		DownloadFile.downloadFile(file, resourcesName, response, request);
		// Path path=Paths.get(file.getName());
		// response.setContentType(MediaType.APPLICATION_OCTET_STREAM.toString());
		// response.setHeader("Content-Disposition", "attachment; filename=" +
		// URLEncoder.encode(resourcesName, "UTF-8"));
		// try {
		// Files.copy(path, response.getOutputStream());
		// } catch (IOException ex) {
		// }
		// return new
		// ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file),headers,HttpStatus.CREATED);
	}

	/**
	 *
	 * 控制作业状态
	 *
	 * @author WYH
	 * @param sIds
	 * @param pwId
	 * @param request
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "changePublishWork.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> changePublishWork(@RequestBody PublishWork publishwork) {
		Map<String, Object> msg = new HashMap<>();
		try {
			teacherService.updatePublishwork(publishwork);
			msg.put("status", 200);
		} catch (Exception e) {
			msg.put("status", 0);
			msg.put("msg", "服务器异常");
		}

		return msg;
	}

	/**
	 * @author 文成 模糊查询作业
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "fuzzySearchWorkNames.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> fuzzySearchWork(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String cId = (String) paramsMap.get("cId");
		String pwName = (String) paramsMap.get("pwName");
		if ("".equals(pwName)) {
			map.put("status", 200);
			map.put("fuzzySearchWorks", null);
			return map;
		}
		String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
		try {
			map.put("status", 200);
			map.put("fuzzySearchWorks", workService.fuzzySearchWorkByTidAndCid(tId, cId, pwName));
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 按作业名字查询作业
	 *
	 * @author wencheng
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "SearchPwByPwName.dotime")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> SearchPwByPwName(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String cId = (String) paramsMap.get("cId");
		String pwName = (String) paramsMap.get("pwName");
		String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
		try {
			map.put("status", 200);
			map.put("fuzzySearchWorks", workService.SearchPwByPwName(tId, cId, pwName));
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师删除加错的学生
	 *
	 * @author weiyuhang
	 */
	@RequestMapping(value = "deleteCourseStudent.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> deleteCourseStudent(@RequestBody CourseStudent courseStudent) {
		Map<String, Object> map = new HashMap<>();

		try {
			teacherService.deleteCourseStudent(courseStudent);
			map.put("status", 200);
			map.put("msg", "删除成功");
		} catch (Exception e) {
			handlException(map, e);
			map.put("status", 0);
			map.put("msg", "无法踢人");
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
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> PwDetails(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String pwId = (String) paramsMap.get("pwId");
		try {
			map.put("status", 200);
			map.put("publishWork", publishWorkService.getTPwDetails(pwId));// 发布作业详情
			map.put("teacherFiles", publishWorkService.getTTFiles(pwId));// 发布作业老师附件
			map.put("teacherFilesImages", publishWorkService.getTTFilesImages(pwId));// 发布作业老师图片附件
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师修改发布作业
	 *
	 * @author weiyuhang
	 * @param publishWork
	 * @return
	 */
	@RequestMapping(value = "updatepublishWork.do", method = RequestMethod.POST)
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> updatepublishWork(@RequestBody PublishWork publishWork) {
		Map<String, Object> map = new HashMap<>();

		try {

			teacherService.updatepublishWork(publishWork);
//			System.out.println(publishWork.getPwContent());
			map.put("status", 200);

		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 模糊查询学生
	 *
	 * @author 文成
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "fuzzySearchStudent.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> fuzzySearchStudentsByNameOrId(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String nameOrId = (String) paramsMap.get("nameOrId");
		if ("".equals(nameOrId)) {
			map.put("status", 200);
			map.put("students", null);
			return map;
		}
		try {
			map.put("students", studentService.fuzzySearchStudentByNameOrId(nameOrId));
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 根据学号查询学生
	 *
	 * @author 文成
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "SearchStudent.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> SearchStudentById(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			Role role = new Role();
			Map<String, Object> map1 = new HashMap<>();
			Map<String, Object> map2 = new HashMap<>();
			Map<String, Object> map3 = new HashMap<>();
			Map<String, Object> map4 = new HashMap<>();
			String cId = (String) paramsMap.get("cId");
			String sId = (String) paramsMap.get("sId");
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
			handlException(map, e);
		}
		return map;
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
	@RequestMapping(value = "SearchStudents.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> SearchStudentsBycId(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String tId = role.getUsername();
		String cId = (String) paramsMap.get("cId");
		int page = (int) paramsMap.get("page");
		int stusNum = sscService.countStudents(cId);

		try {
			studentService.CreatStudentTableDescRank(cId, tId);
			map.put("students", studentService.selectStudents(page));
			map.put("stusNum", stusNum);
			map.put("max", MaxPage.getMaxPage(stusNum, 30));
		} catch (Exception e1) {
			handlException(map, e1);
		}
		map.put("status", 200);
		return map;
	}

	@RequestMapping(value = "TeacherUpadte.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> teacherupadtework(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {
			
			workService.teacherupdatework((String)paramsMap.get("sId"),(String)paramsMap.get("pwId"),(Integer)paramsMap.get("wScore"),(String)paramsMap.get("wRemark"));
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
			map.put("status", 0);
			map.put("msg", "批阅失败");

		}
		return map;
	}

	@RequestMapping(value = "selectEstimate.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> selectEstimate(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		try {

			map.put("Estimate", teacherService.selectEstimate((String) paramsMap.get("epId")));
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师查询作业详细情况
	 *
	 * @author 文成
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "getWorkDetails.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> getPwDetails(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String pwId = (String) paramsMap.get("pwId");
		int state = Integer.parseInt((String) paramsMap.get("state"));
		int page = (int) paramsMap.get("page");
		try {
			map.put("publishWork", publishWorkService.getTPwDetails(pwId));// 发布作业详情
			map.put("teacherFiles", publishWorkService.getTTFiles(pwId));// 发布作业老师附件
			map.put("teacherFilesImages", publishWorkService.getTTFilesImages(pwId));// 发布作业老师图片附件
			map.put("students", publishWorkService.getStudentsBywStateAndpwId(state, pwId, page));
			// 、1未批改作业同学（待批改）待批改(作业分数)）2/未提交作业同学（基本信息、未提交） 、3、已批改作业同学（头像、学号、名字、
			// 最大页码数
			Integer countFinishStudents = publishWorkMapper.countFinishStudents(pwId);
			Integer countNotFinishStudents = publishWorkMapper.countNotFinishStudents(pwId);
			Integer countFinishsAndNotCheckStudent = publishWorkMapper.countFinishsAndNotCheckStudent(pwId);
			map.put("countFinishStudents", countFinishStudents);
			map.put("countNotFinishStudents", countNotFinishStudents);
			map.put("countFinishsAndNotCheckStudent", countFinishsAndNotCheckStudent);
			if (state == 1) {
				map.put("max", MaxPage.getMaxPage(countFinishsAndNotCheckStudent, 5));
			} else if (state == 2) {
				map.put("max", MaxPage.getMaxPage(countNotFinishStudents, 5));
			} else if (state == 3) {
				map.put("max", MaxPage.getMaxPage(countFinishStudents, 5));
			}
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师查看学生作业内容
	 *
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "selectOneWorkDetail.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> selectOneWorkDetail(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String sId = (String) paramsMap.get("sId");
		String pwId = (String) paramsMap.get("pwId");
		try {
			map.put("status", 200);
			map.put("publishWork", publishWorkService.getPwDetails(sId, pwId));
//			map.put("teacherFiles", publishWorkService.getTFiles(sId, pwId));
//			map.put("teacherFilesImages", publishWorkService.getTFilesImages(sId, pwId));
			map.put("studentFiles", publishWorkService.getSFiles(sId, pwId));
			map.put("studentFilesImages", publishWorkService.getSFilesImages(sId, pwId));
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 老师按学号搜索学生作业内容
	 *
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "queryWorkBySid.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> queryWorkBySid(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String sId = (String) paramsMap.get("sId");
		String pwId = (String) paramsMap.get("pwId");
		try {
			map.put("status", 200);
			map.put("work", publishWorkService.getWorkBySid(pwId, sId));
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}
	@RequestMapping(value = "deletePublishWork.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> deletePublishWork(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String pwId = (String) paramsMap.get("pwId");
		try {
			publishWorkService.deletePublishWorkService(pwId);
			map.put("status", 200);
	        map.put("msg", "删除成功");
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}
	/**
	 * 查询发布的评价
	 *
	 * @author weiyuhang
	 * @param studentSelectCourse
	 * @return
	 */
	@RequestMapping("selectPE.do")
	@RequiresRoles({ "teacher" })
	public @ResponseBody Map<String, Object> selectPE(@RequestBody Map<String, Object> paramsMap,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String tId = role.getUsername();
		String cId = (String) paramsMap.get("cId");
		int page = (int) paramsMap.get("page");
		int pubENum = teacherService.countSelectPublishEstimateCount(tId, cId);
		try {
			map.put("publishEstimates", teacherService.selectPublishEstimate(tId, cId, (page - 1) * 5, 5));
			map.put("max", MaxPage.getMaxPage(pubENum, 5));
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
			e.printStackTrace();
		}
		return map;

	}

}
