package com.cdtu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdtu.mapper.CourseMapper;
import com.cdtu.mapper.EstimateMapper;
import com.cdtu.mapper.PublishEstimateMapper;
import com.cdtu.mapper.PublishWorkMapper;
import com.cdtu.mapper.StudentMapper;
import com.cdtu.mapper.StudentSelectCourseMapper;
import com.cdtu.mapper.TeacherMapper;
import com.cdtu.mapper.WorkMapper;
import com.cdtu.model.ClassCreate;
import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.PublishEstimate;
import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.model.Teacher;
import com.cdtu.model.Work;
import com.cdtu.service.TeacherService;
import com.cdtu.util.MaxPage;
import com.cdtu.util.OAUtil;

@Transactional
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {
	private @Resource WorkMapper work;
	private @Resource CourseMapper coursemapper;
	private @Resource TeacherMapper teacherMapper;
	private @Resource StudentMapper studentMapper;
	private @Resource CourseMapper courseMapper;
	private @Resource PublishWorkMapper publishWorkMapper;
	private @Resource PublishEstimateMapper publishEstimateMapper;
	private @Resource StudentSelectCourseMapper studentSelectCourseMapper;
	private @Resource EstimateMapper estimateMapper;

	@Override
	public Teacher getTeacherBytIdAndtPassword(Role role) {
		return teacherMapper.getTeacherBytIdAndtPassword(role);
	}

	@Override
	public void updatatPasswordBytId(Role role) {
		teacherMapper.updatatPasswordBytId(role);
	}

	@Override
	public String getPasswordById(String id) {
		return teacherMapper.selectPasswordById(id);
	}

	/**
	 * 教师查询该课程所有学生
	 *
	 * @author weiyuhang
	 */
	@Override
	public List<CourseStudent> selectCourseStudentService(CourseWapper coursewapper) {

		return com.cdtu.util.OrderByUtil.OrderASC(studentSelectCourseMapper.selectCourseStudent(coursewapper));
	}

	/**
	 * 教师查询课程
	 *
	 * @author weiyuhang
	 * @param id
	 * @return
	 */

	@Override
	public List<CourseWapper> selectAllCourceService(String id) {
		List<CourseWapper> courseWappers = new ArrayList<>();
//		courseWappers.addAll(courseMapper.selectClassCreate(id));
		courseWappers.addAll(coursemapper.selectTeacherCourse(id));
		return courseWappers;
	}

	/**
	 * 教师删除课程
	 *
	 * @author weiyuhang
	 * @param ctId
	 * @return
	 */
	@Override
	public String deleteClassCreateService(Integer ctId) {
		courseMapper.deleteClassCreate(ctId);
		return "success";
	}

	/**
	 * 老师创建课程
	 *
	 * @author weiyuhang
	 *
	 */
	@Override
	public String insertClassCreateService(ClassCreate classcreate) {
		boolean flag = false;// 判断是否重复
		List<CourseWapper> classcreates = courseMapper.selectClassCreate(classcreate.gettId());
		for (CourseWapper classCreate2 : classcreates) {
			if (classCreate2.getcName().equals(classcreate.getCtName())) {
				flag = true;
			}
		}
		if (flag == false) {
			courseMapper.insertClassCreate(classcreate);
			return "success";
		}
		return "defeat";
	}

	/**
	 * 教师修改课程
	 *
	 * @author weiyuhang
	 * @param classcreate
	 * @return
	 */
	@Override
	public String updateClassCreateService(ClassCreate classcreate) {
		boolean flag = false;// 判断是否重复
		List<CourseWapper> classcreates = courseMapper.selectClassCreate(classcreate.gettId());
		for (CourseWapper classCreate2 : classcreates) {
			if (classCreate2.getcName().equals(classcreate.getCtName())) {
				flag = true;
			}
		}
		if (flag == false) {
			courseMapper.updateClassCreate(classcreate);
			return "success";
		}
		return "defeat";

	}

	/**
	 * 发布评价
	 *
	 * @author LR
	 */
	@Override
	public Integer PublishEstimate(PublishEstimate publishEstimate, String tId) {
		if (publishEstimate != null) {
			publishEstimate.setEpId(OAUtil.getId());
			publishEstimate.setTscId(publishWorkMapper.selectTscid(publishEstimate.getcId(), tId));
			publishEstimateMapper.insertByTscId(publishEstimate);
			return 1;

		} else {
			return -1;
		}

	}

	@Override
	public String publishWork(PublishWork publishWork, String tId) {
		if (publishWork != null) {
			publishWork.setPwId(OAUtil.getId());
			publishWork.setTscId(publishWorkMapper.selectTscid(publishWork.getcId(), tId));
			publishWorkMapper.insterBycId(publishWork);
			List<String> Ids = studentSelectCourseMapper.selectStudent(publishWork.getcId());
			for (String sId : Ids) {
				String wId = OAUtil.getId();
				work.insertWorks(wId, publishWork.getPwId(), sId);
			}
			return publishWork.getPwId();
		} else {
			return "-1";
		}
	}

	@Override
	public Map<String, Object> demonPublishWork(CourseWapper courseWapper, String tId) {
		List<PublishWork> publishWorkLs = new ArrayList<>();
		if (courseWapper != null) {
			Map<String, Object> publishWorks = new HashMap<>();
			if ("2".equals(courseWapper.getState())) {
				publishWorkLs = publishWorkMapper.selectTeacherPublishWorkBycId(tId, courseWapper.getcId(), true,
						(courseWapper.getPage() - 1) * 5, 5);// 进行
				publishWorks.put("max", MaxPage.getMaxPage(
						publishWorkMapper.selectTeacherPublishWorkCount(tId, courseWapper.getcId(), true), 5));// 最大页数
			}
			if ("3".equals(courseWapper.getState())) {
				publishWorkLs = publishWorkMapper.selectTeacherPublishWorkBycId(tId, courseWapper.getcId(), false,
						(courseWapper.getPage() - 1) * 5, 5);// 结束
				publishWorks.put("max", MaxPage.getMaxPage(
						publishWorkMapper.selectTeacherPublishWorkCount(tId, courseWapper.getcId(), false), 5));// 最大页数
			}
			if ("1".equals(courseWapper.getState())) {
				publishWorkLs = publishWorkMapper.selectTeacherPublishWorkBycId(tId, courseWapper.getcId(), null,
						(courseWapper.getPage() - 1) * 5, 5);// 全部
				publishWorks.put("max", MaxPage.getMaxPage(
						publishWorkMapper.selectTeacherPublishWorkCount(tId, courseWapper.getcId(), null), 5));// 最大页数
			}

			publishWorks.put("countprocess",
					publishWorkMapper.selectTeacherPublishWorkCount(tId, courseWapper.getcId(), true));
			publishWorks.put("countover",
					publishWorkMapper.selectTeacherPublishWorkCount(tId, courseWapper.getcId(), false));
			publishWorks.put("countall",
					publishWorkMapper.selectTeacherPublishWorkCount(tId, courseWapper.getcId(), null));
			for (PublishWork publishWork : publishWorkLs) {
				if (publishWork.getPwState() == true) {
					publishWork.setPwStringState("进行中");
					publishWork.setPwBooleanState(publishWork.getPwState());
				} else {
					publishWork.setPwStringState("已结束");
					publishWork.setPwBooleanState(publishWork.getPwState());
				}
			}
			publishWorks.put("publishWorks", publishWorkLs);
			return publishWorks;
		} else {
			return null;
		}

	}

	/*
	 * @Override public Map<String, Object> showPublishWork(CourseWapper
	 * courseWapper) { List<PublishWork> publishWorkLs=new ArrayList<PublishWork>();
	 * if(courseWapper!=null){ Map<String, Object> publishWorks=new HashMap<String,
	 * Object>(); if(courseWapper.getTscId()!=null){
	 * if("2".equals(courseWapper.getState())){ publishWorkLs=
	 * this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(
	 * ),true,(courseWapper.getPage()-1)*5,5);//进行
	 * publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.
	 * selectCountBypwStateBytscId(courseWapper.getTscId(), true),5));//最大页数 }
	 * if("3".equals(courseWapper.getState())){ publishWorkLs=
	 * this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(
	 * ),false,(courseWapper.getPage()-1)*5,5);//结束
	 * publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.
	 * selectCountBypwStateBytscId(courseWapper.getTscId(), false),5));//最大页数 }
	 * if("1".equals(courseWapper.getState())){ publishWorkLs=
	 * this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(
	 * ),null,(courseWapper.getPage()-1)*5,5);//全部
	 * publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.
	 * selectCountBypwStateBytscId(courseWapper.getTscId(), null),5));//最大页数 }
	 *
	 * publishWorks.put("countprocess",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(),
	 * true)); publishWorks.put("countover",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(),
	 * false)); publishWorks.put("countall",
	 * this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(),
	 * null)); for(PublishWork publishWork:publishWorkLs){
	 * if(publishWork.getPwState()==true){ publishWork.setPwStringState("进行中");
	 * publishWork.setPwBooleanState(publishWork.getPwState()); }else{
	 * publishWork.setPwStringState("已结束");
	 * publishWork.setPwBooleanState(publishWork.getPwState()); } }
	 * publishWorks.put("publishWorks",publishWorkLs); return publishWorks; }else{
	 * courseWapper.setCtId(courseWapper.getId());
	 * if("2".equals(courseWapper.getState())){
	 * publishWorkLs=this.publishWorkMapper.selectTeacherPublishWorkByctId(
	 * courseWapper.getCtId(),true,(courseWapper.getPage()-1)*5,5);//进行
	 * publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.
	 * selectCountBypwStateByctId(courseWapper.getCtId(), true),5));//最大页数 }
	 * if("3".equals(courseWapper.getState())){
	 * publishWorkLs=this.publishWorkMapper.selectTeacherPublishWorkByctId(
	 * courseWapper.getCtId(),false,(courseWapper.getPage()-1)*5,5);//结束
	 * publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.
	 * selectCountBypwStateByctId(courseWapper.getCtId(), false),5));//最大页数 }
	 * if("1".equals(courseWapper.getState())){ publishWorkLs=
	 * this.publishWorkMapper.selectTeacherPublishWorkByctId(courseWapper.getCtId(),
	 * null,(courseWapper.getPage()-1)*5,5);//全部
	 * publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.
	 * selectCountBypwStateByctId(courseWapper.getCtId(), null),5));//最大页数 }
	 *
	 * publishWorks.put("countProcess",
	 * this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(),
	 * true)); publishWorks.put("countOver",
	 * this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(),
	 * false)); publishWorks.put("countAll",
	 * this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(),
	 * null)); for(PublishWork publishWork:publishWorkLs){
	 * if(publishWork.getPwState()==true){ publishWork.setPwStringState("进行中");
	 * publishWork.setPwBooleanState(publishWork.getPwState()); }else{
	 * publishWork.setPwStringState("已结束");
	 * publishWork.setPwBooleanState(publishWork.getPwState()); } }
	 * publishWorks.put("publishWorks",publishWorkLs); return publishWorks; } }else{
	 * return null; }
	 *
	 * }
	 */
	@Override
	public Integer addWorkRemark(Work work) {
		if (work != null) {
			if (this.work.selectWork(work.getsId(), work.getPwId()) != null) {
				this.work.teacherUpdateWork(work.getsId(), work.getPwId(), work.getwRemark());
				return 1;
			} else {
				return 0;
			}
		} else {
			return -1;
		}
	}

	@Override
	public Work selectStudentWork(String sId, String pwId) {
		if (sId != null && pwId != null) {
			return work.selectWork(sId, pwId);
		} else {
			return null;
		}

	}

	@Override
	public Map<String, Object> showStudentInWork(CourseWapper courseWapper) {
		if (courseWapper != null) {
			Map<String, Object> courseStudents = new HashMap<>();
			List<CourseStudent> sumbitStudents = new ArrayList<>();
			List<CourseStudent> unSumbitStudents = new ArrayList<>();
			List<CourseStudent> students = selectCourseStudentService(courseWapper);
			for (CourseStudent courseStudent : students) {
				if (work.selectWork(courseStudent.getsId(), courseWapper.getPwId()) != null) {
					sumbitStudents.add(courseStudent);
				} else {
					unSumbitStudents.add(courseStudent);
				}
			}
			courseStudents.put("sumbitStudents", sumbitStudents);
			courseStudents.put("unSumbitStudents", unSumbitStudents);
			return courseStudents;
		} else {
			return null;
		}
	}

	@Override
	public String selectPwIdname(String pwId) {
		return publishWorkMapper.selectPwIdname(pwId);
	}

	@Override
	public String selectStudentName(String id) {
		return studentMapper.selectStudentName(id);
	}

	@Override
	public int updatePublishwork(PublishWork publishwork) {
		if (publishwork.getPwState() == false) {
			publishwork.setPwState(true);
		} else {
			publishwork.setPwState(false);
		}
		publishWorkMapper.changePublishWork(publishwork);
		return 0;
	}

	/**
	 *
	 */
	@Override
	public int deleteCourseStudent(CourseStudent courseStudent) {
		teacherMapper.deleteCourseStudent(courseStudent);
		return 0;
	}

	@Override
	public void updataAvatar(String path, String username) {
		teacherMapper.updataAvatar(path, username);
	}

	@Override
	public String updatepublishWork(PublishWork publishWork) {
		publishWorkMapper.updatePublishWork(publishWork);
		return null;
	}

	@Override
	public Map<String, Object> selectEstimate(String epId) {
		List<Map<String, Object>> eSpeed = estimateMapper.selecteSpeed(epId);
		List<Map<String, Object>> eSpeeds = new ArrayList<>();
		int[] a = new int[5];
		for (Map<String, Object> map2 : eSpeed) {
			int q = (int) map2.get("StareSpeed");
			long s = (long) map2.get("NumeSpeeds");
			a[q - 1] = (int) s;
		}
		for (int i = 1; i <= 5; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("StareSpeed", i);
			map.put("NumeSpeeds", a[i - 1]);
			eSpeeds.add(map);
		}
		List<Map<String, Object>> eDifficult = estimateMapper.selecteDifficult(epId);
		List<Map<String, Object>> eDifficults = new ArrayList<>();
		int[] b = new int[5];
		for (Map<String, Object> map2 : eDifficult) {
			int q = (int) map2.get("StareDifficult");
			long w = (long) map2.get("NumeDifficults");
			b[q - 1] = (int) w;
		}
		for (int i = 1; i <= 5; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("StareDifficult", i);
			map.put("NumeDifficults", b[i - 1]);
			eDifficults.add(map);
		}
		List<Map<String, Object>> eFeel = estimateMapper.selecteFeel(epId);
		List<Map<String, Object>> eFeels = new ArrayList<>();
		int[] c = new int[5];
		for (Map<String, Object> map2 : eFeel) {
			int q = (int) map2.get("StareFeel");
			long w = (long) map2.get("NumeFeels");
			c[q - 1] = (int) w;
		}
		for (int i = 1; i <= 5; i++) {
			Map<String, Object> map = new HashMap<>();
			map.put("StareFeel", i);
			map.put("NumeFeels", c[i - 1]);
			eFeels.add(map);
		}
		Map<String, Object> map = new HashMap<>();
		map.put("eDifficults", eDifficults);
		map.put("eSpeeds", eSpeeds);
		map.put("eFeels", eFeels);
		map.put("eSuggests", estimateMapper.selecteSuggest(epId));
		return map;
	}

}
