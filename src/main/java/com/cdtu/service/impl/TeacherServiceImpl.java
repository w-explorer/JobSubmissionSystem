package com.cdtu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.ClassCreateMapper;
import com.cdtu.mapper.CourseMapper;
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
import com.cdtu.util.OAUtil;
@Service("teacherService")
public class TeacherServiceImpl implements TeacherService {

	@Resource
	private TeacherMapper teacherMapper;
	@Resource
	private PublishEstimateMapper publishEstimateMapper;
	@Resource
	private PublishWorkMapper publishWorkMapper;
	@Resource
	private ClassCreateMapper classcreatemapper;
	@Resource
	private CourseMapper coursemapper;
	@Resource
	private StudentSelectCourseMapper studentSelectCourseMapper;
	@Resource
	private WorkMapper work;
	@Resource
	private StudentMapper studentMapper;
	
	public Teacher getTeacherBytIdAndtPassword(Role role) {
		return this.teacherMapper.getTeacherBytIdAndtPassword(role);
	}
	@Override
	public void updatatPasswordBytId(Role role) {
		this.teacherMapper.updatatPasswordBytId(role);
	}
	@Override
	public String getPasswordById(String id) {
		return this.teacherMapper.selectPasswordById(id);
	}

	
	
	/**
	 * 教师查询该课程学生
	 * 
	 * @author weiyuhang
	 */
	@Override
	public List<CourseStudent> selectCourseStudentService(CourseWapper coursewapper) {
		return this.studentSelectCourseMapper.selectCourseStudent(coursewapper);
	}

	/**
	 * 教师查询课程
	 * 
	 * @author weiyuhang
	 * @param id
	 * @return
	 */

	public List<CourseWapper> selectAllCourceService(String id) {
		List<CourseWapper> courseWappers = new ArrayList<CourseWapper>();
		courseWappers.addAll(classcreatemapper.selectClassCreate(id));
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
		classcreatemapper.deleteClassCreate(ctId);
		return "success";
	}

	/**
	 * 老师创建课程
	 * 
	 * @author weiyuhang
	 *
	 */
	public String insertClassCreateService(ClassCreate classcreate) {
		boolean flag = false;// 判断是否重复
		List<CourseWapper> classcreates = classcreatemapper.selectClassCreate(classcreate.gettId());
		for (CourseWapper classCreate2 : classcreates) {
			if (classCreate2.getcName().equals(classcreate.getCtName())) {
				flag = true;
			}
		}
		if (flag == false) {
			classcreatemapper.insertClassCreate(classcreate);
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
		List<CourseWapper> classcreates = classcreatemapper.selectClassCreate(classcreate.gettId());
		for (CourseWapper classCreate2 : classcreates) {
			if (classCreate2.getcName().equals(classcreate.getCtName())) {
				flag = true;
			}
		}
		if (flag == false) {
			classcreatemapper.updateClassCreate(classcreate);
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
	public Integer PublishEstimate(PublishEstimate publishEstimate) {
		if (publishEstimate != null) {
			publishEstimate.setEpId(OAUtil.getId());
			if (publishEstimate.getTscId() != null ) {
				this.publishEstimateMapper.insertByTscId(publishEstimate);
				return 1;
			}
			else{
				publishEstimate.setCtId(publishEstimate.getId());
				this.publishEstimateMapper.insertByCtId(publishEstimate);
				return 1;
			}
		} else {
			return -1;
		}

	}

	@Override
	public Integer publishWork(PublishWork publishWork) {
		if (publishWork != null) {
			publishWork.setPwId(OAUtil.getId());
			if (publishWork.getTscId() != null ) {
				this.publishWorkMapper.insterBytscId(publishWork);
				return 1;
			}else{
				publishWork.setCtId(publishWork.getId());
				this.publishWorkMapper.insterByctId(publishWork);
				return 1;
			}
		} else {
			return -1;
		}
	}

	@Override
	public Map<String, Object> showPublishWork(CourseWapper courseWapper) {
		if(courseWapper!=null){
			Map<String, Object> publishWorks=new HashMap<String, Object>();
			if(courseWapper.getTscId()!=null){
				if("process".equals(courseWapper.getState())){
					publishWorks.put("publishWorks", this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(),true));
				}
				if("over".equals(courseWapper.getState())){
					publishWorks.put("publishWorks", this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(),false));
				}
				if("all".equals(courseWapper.getState())){
					publishWorks.put("publishWorks", this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(),null));
				}
				
				publishWorks.put("countprocess", this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), true));
				publishWorks.put("countover", this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), false));
				publishWorks.put("countall", this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), null));
				return publishWorks;
			}else{
				courseWapper.setCtId(courseWapper.getId());
				if("process".equals(courseWapper.getState())){
					publishWorks.put("publishWorks", this.publishWorkMapper.selectTeacherPublishWorkByctId(courseWapper.getCtId(),true));
				}
				if("over".equals(courseWapper.getState())){
					publishWorks.put("publishWorks", this.publishWorkMapper.selectTeacherPublishWorkByctId(courseWapper.getCtId(),false));
				}
				if("all".equals(courseWapper.getState())){
					publishWorks.put("publishWorks", this.publishWorkMapper.selectTeacherPublishWorkByctId(courseWapper.getCtId(),null));
				}
				
				publishWorks.put("countProcess", this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), true));
				publishWorks.put("countOver", this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), false));
				publishWorks.put("countAll", this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), null));
				return publishWorks;
			}
		}else{
			return null;
		}
		
	}

	@Override
	public Integer addWorkRemark(Work work) {
		if(work!=null){
			this.work.teacherUpdateWork(work.getsId(), work.getPwId(), work.getwRemark());
			return 1;
		}else{
			return -1;
		}
	}

	@Override
	public Work selectStudentWork(String sId, String pwId) {
		if(sId!=null&&pwId!=null){
			return this.work.selectWork(sId, pwId);
		}else{
			return null;
		}
		
	}

	@Override
	public Map<String, Object> showStudentInWork(CourseWapper courseWapper) {
		if(courseWapper!=null){
			Map<String, Object> courseStudents=new HashMap<String, Object>();
			List<CourseStudent> sumbitStudents=new ArrayList<CourseStudent>();
			List<CourseStudent> unSumbitStudents=new ArrayList<CourseStudent>();
			List<CourseStudent> students=selectCourseStudentService(courseWapper);
			for(CourseStudent courseStudent:students){
				if(this.work.selectWork(courseStudent.getsId(), courseWapper.getPwId())!=null){
					sumbitStudents.add(courseStudent);
				}else{
					unSumbitStudents.add(courseStudent);
				}
			}
			courseStudents.put("sumbitStudents", sumbitStudents);
			courseStudents.put("unSumbitStudents", unSumbitStudents);
			return courseStudents;
		}else{
			return null;
		}
	}
	@Override
	public String selectPwIdname(String pwId) {
		return this.publishWorkMapper.selectPwIdname(pwId);
	}
	@Override
	public String selectStudentName(String id){
		return this.studentMapper.selectStudentName(id);
	}
	
}
