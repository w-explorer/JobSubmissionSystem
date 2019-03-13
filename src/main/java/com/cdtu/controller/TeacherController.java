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

import com.cdtu.model.ClassCreate;
import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.PublishEstimate;
import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.model.Work;
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
	private @Resource(name = "teacherService") TeacherService teacherService;
	private @Resource(name = "sscService") StudentSelectCourseService sscService;
	private @Resource(name = "publishWorkService") PublishWorkService publishWorkService;
	private @Resource(name = "studentService") StudentService studentService;

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
		if (status == 1)
			msg.put("status", 200);
		else {
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
		try{
			Role role = (Role) subject.getPrincipal();
			String  ms= teacherService.publishWork(publishWork, role.getUsername());
			System.out.println(publishWork.getPwContent());
			msg.put("status", 200);
			msg.put("pwId", ms);
		}catch (Exception e) {
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
	@RequestMapping(value = "queryPublishWork.do", method = RequestMethod.POST)
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
		if (status == 1)
			msg.put("status", 200);
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
	public void download(@RequestBody Work work, HttpServletResponse response,HttpServletRequest request) throws IOException {
		File file = new File(work.getwAddr());
		String fileName = work.getsId() + "_" + teacherService.selectStudentName(work.getsId()) + "_" + file.getName();
		DownloadFile.downloadFile(file, fileName, response,request);
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
	public void downloadAll(@RequestBody Work work, HttpServletResponse response,HttpServletRequest request) throws IOException {

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
			while ((temp = input.read()) != -1)
				zipOut.write(temp);
			zipOut.closeEntry();
			input.close();
		}
		zipOut.close();
		File file = new File("D:/" + resourcesName);
		DownloadFile.downloadFile(file, resourcesName, response,request);
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
			System.out.println(publishwork.getPwState());
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
		String tId = ((Role) SecurityUtils.getSubject().getPrincipal()).getUsername();
		try {
			map.put("status", 200);
			map.put("publishWork", publishWorkService.getPwDetails(tId, pwId));
			map.put("teacherFiles", publishWorkService.getTFiles(tId, pwId));
			map.put("studentFiles", publishWorkService.getSFiles(tId, pwId));
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

		try{
		
		 teacherService.updatepublishWork(publishWork);
		System.out.println(publishWork.getPwContent());
		map.put("status", 200);
		
	}catch (Exception e) {
		handlException(map, e);
	}
		return map;
	}
	/**
	 *模糊查询学生
	 * @author 文成
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="fuzzySearchStudent.do")
	@RequiresRoles({"teacher"})
	public @ResponseBody Map<String,Object> fuzzySearchStudentsByNameOrId(@RequestBody Map<String,Object> paramsMap){
		Map<String,Object> map = new HashMap<String,Object>();
		String nameOrId = (String) paramsMap.get("nameOrId");
		String cId = (String) paramsMap.get("cId");
		try {
			map.put("students", studentService.fuzzySearchStudentByNameOrId(nameOrId,cId));
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}
	/**
	 * 根据学号查询学生
	 * @author 文成
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="SearchStudent.do")
	@RequiresRoles({"teacher"})
	public @ResponseBody Map<String,Object> SearchStudentById(@RequestBody Map<String,Object> paramsMap){
		Map<String,Object> map = new HashMap<String,Object>();
		String sId = (String) paramsMap.get("sId");
		
		try {
			
			map.put("student", studentService.SearchStudentById(sId));
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}
	/**
	 * 通过建立一张临时表 存储班级成绩排名 每个同学的总分(sql 高级排名  开启mybatis 一次执行多条SQL )
	 * 怎么开启呢？在拼装mysql链接的url时，为其加上allowMultiQueries参数，设置为true，如下：
	 * jdbc.jdbcUrl=jdbc:mysql://127.0.0.1:3306/database?useUnicode=true&characterEncoding=utf8&allowMultiQueries=true
	 * @author 文成
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value="SearchStudents.do")
	@RequiresRoles({"teacher"})
	public @ResponseBody Map<String,Object> SearchStudentsBycId(@RequestBody Map<String,Object> paramsMap){
		Map<String,Object> map = new HashMap<String,Object>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		String tId = role.getUsername();
		String cId = (String) paramsMap.get("cId");
		int page = Integer.parseInt((String) paramsMap.get("page"));
		int stusNum = sscService.countStudents(cId);
		try {
			map.put("students", studentService.selectStudents(page));
			map.put("stusNum", stusNum);
			map.put("pageNum", MaxPage.getMaxPage(stusNum, 30));
		} catch (Exception e) {
			try {
				studentService.CreatStudentTableDescRank(cId,tId);
				map.put("students", studentService.selectStudents(page));
				map.put("stusNum", stusNum);
				map.put("pageNum", MaxPage.getMaxPage(stusNum, 30));
			} catch (Exception e1) {
				handlException(map, e);
			}
		}
		map.put("status", 200);
		return map;
	}
	
}
