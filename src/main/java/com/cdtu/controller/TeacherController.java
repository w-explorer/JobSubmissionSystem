package com.cdtu.controller;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.cdtu.model.ClassCreate;
import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.PublishEstimate;
import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.model.Work;
import com.cdtu.service.StudentSelectCourseService;
import com.cdtu.service.TeacherService;
import com.cdtu.service.WorkService;
import com.cdtu.util.DownloadFile;

@Controller
@RequestMapping(value = "teacher")
public class TeacherController {
	private @Resource(name = "workService") WorkService workService;
	private @Resource(name = "sscService") StudentSelectCourseService sscService;
	private @Resource(name = "teacherService") TeacherService teacherService;

	/**
	 * 老师统计作业提交情况，参数是发布作业码
	 *
	 * @author 李红兵
	 */
	@ResponseBody
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

	private void handlException(Map<String, Object> map, Exception e) {
		e.printStackTrace();
		map.put("status", 500);
		map.put("msg", "抱歉，服务器开小差了");
	}

	/**
	 *
	 * @author weiyuhang
	 */
	@RequestMapping("delete.do")
	public @ResponseBody Map<String, Object> deleteClassCreateJsp(@RequestParam("ctId") Integer ctId) {

		String msg = teacherService.deleteClassCreateService(ctId);
		Map<String, Object> data = new HashMap<String, Object>();
		if ("success".equals(msg)) {
			data.put("status", 200);
			data.put("msg", "删除成功");
		} else {
			data.put("status", 0);
			data.put("msg", "删除失败");
		}
		return data;
	}

	/**
	 * 教师查询该课程学生
	 *
	 * @author weiyuhang
	 */
	@RequestMapping("selectCourseStudent.do")
	public @ResponseBody Map<String, Object> selectCourseStudentJsp(@RequestBody CourseWapper coursewapper) {

		List<CourseStudent> coursestudentslist;
		Map<String, Object> data = new HashMap<String, Object>();
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
	public @ResponseBody Map<String, Object> selectAllCourse() {

		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String tId = role.getUsername();
		Map<String, Object> data = new HashMap<String, Object>();

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
	public @ResponseBody Map<String, Object> createClass(@RequestBody ClassCreate classcreate) {
		Map<String, Object> data = new HashMap<String, Object>();
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
	public @ResponseBody Map<String, String> updateClassCreate(@RequestBody ClassCreate classcreate) {
		String msg = teacherService.updateClassCreateService(classcreate);
		Map<String, String> data = new HashMap<String, String>();
		if ("success".equals(msg)) {
			data.put("status", "200");
			data.put("msg", "修改成功");
		} else {
			data.put("status", "0");
			data.put("msg", "修改课程名重复");
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
	public @ResponseBody Map<String, Object> publishEstimate(@RequestBody PublishEstimate publishEstimate) {
		Map<String, Object> msg = new HashMap<String, Object>();
		Integer status = teacherService.PublishEstimate(publishEstimate);
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
	public @ResponseBody Map<String, Object> publishWork(@RequestBody PublishWork publishWork) {
		Map<String, Object> msg = new HashMap<String, Object>();
		Integer status = teacherService.publishWork(publishWork);
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
	 * 老师查询发布的作业 //判断时间已到修改课程状态（未解决）
	 *
	 * @author LR
	 * @param courseWapper
	 * @param state
	 * @return
	 */
	@RequestMapping(value = "queryPublishWork.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> queryPublishWork(@RequestBody CourseWapper courseWapper) {
		Map<String, Object> msg = this.teacherService.showPublishWork(courseWapper);
		if (msg != null) {
			if (msg.get("publishWorks") != null) {
				msg.put("status", 200);
				return msg;
			}
			msg.put("status", 0);
			return msg;
		} else {
			msg = new HashMap<String, Object>();
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
	public @ResponseBody Map<String, Object> queryStudentInWork(@RequestBody CourseWapper courseWapper) {
		Map<String, Object> msg = this.teacherService.showStudentInWork(courseWapper);
		if (msg != null) {
			msg.put("status", 200);
			return msg;
		} else {
			msg = new HashMap<String, Object>();
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
	public @ResponseBody Map<String, Object> remarkStudentWork(@RequestBody Work work) {
		Map<String, Object> msg = new HashMap<String, Object>();
		Integer status = this.teacherService.addWorkRemark(work);
		if (status == 1) {
			msg.put("status", 200);
		} else {
			if (status == -1) {
				msg.put("status", 0);
				msg.put("msg", "用户为空");
			}
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
	public @ResponseBody Map<String, Object> queryStudentWork(@RequestBody Work work) {
		Map<String, Object> msg = new HashMap<String, Object>();
		Work newWork = this.teacherService.selectStudentWork(work.getsId(), work.getPwId());
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
	public void download(@RequestBody Work work, HttpServletResponse response) throws IOException {
		File file = new File(work.getwAddr());
		String fileName = work.getsId() + "_" + teacherService.selectStudentName(work.getsId()) + "_" + file.getName();
		DownloadFile.downloadFile(file, fileName, response);
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
	@ResponseBody
	public void downloadAll(@RequestBody Work work, HttpServletResponse response) throws IOException {

		// 需要压缩的文件
		// 压缩后的文件
		String resourcesName = this.teacherService.selectPwIdname(work.getPwId()) + ".zip";
		ZipOutputStream zipOut = new ZipOutputStream(new FileOutputStream("D:/" + resourcesName));
		InputStream input = null;

		for (String sId : work.getsIds()) {
			Work newWork = this.teacherService.selectStudentWork(sId, work.getPwId());
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
		DownloadFile.downloadFile(file, resourcesName, response);
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

}
