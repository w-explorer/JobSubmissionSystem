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
import com.cdtu.util.MaxPage;
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
	 * 教师查询该课程所有学生
	 * 
	 * @author weiyuhang
	 */
	@Override
	public List<CourseStudent> selectCourseStudentService(CourseWapper coursewapper) {
		
		return  com.cdtu.util.OrderByUtil.OrderASC(studentSelectCourseMapper.selectCourseStudent(coursewapper));
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
	public String updateClassCreateService(ClassCreate classcreate){
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
	public Integer PublishEstimate(PublishEstimate publishEstimate,String tId) {
		if (publishEstimate != null) {
			publishEstimate.setEpId(OAUtil.getId());
			publishEstimate.setTscId(this.publishWorkMapper.selectTscid(publishEstimate.getcId(), tId));
				this.publishEstimateMapper.insertByTscId(publishEstimate);
				return 1;
			
		} else {
			return -1;
		}

	}

	@Override
	public Integer publishWork(PublishWork publishWork,String tId) {
		if (publishWork != null) {
			publishWork.setPwId(OAUtil.getId());
			System.out.println(publishWork.getcId()+"                            "+tId);
			publishWork.setTscId(this.publishWorkMapper.selectTscid(publishWork.getcId(), tId));
			this.publishWorkMapper.insterBycId(publishWork);
			return 1;
		} else {
			return -1;
		}
	}
	@Override
	public Map<String, Object> demonPublishWork(CourseWapper courseWapper,String tId) {
		List<PublishWork> publishWorkLs=new ArrayList<PublishWork>();
		if(courseWapper!=null){
			Map<String, Object> publishWorks=new HashMap<String, Object>();
				if("2".equals(courseWapper.getState())){
					publishWorkLs= this.publishWorkMapper.selectTeacherPublishWorkBycId(tId,courseWapper.getcId(),true,(courseWapper.getPage()-1)*5,5);//进行
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectTeacherPublishWorkCount(tId,courseWapper.getcId(), true),5));//最大页数
				}
				if("3".equals(courseWapper.getState())){
					publishWorkLs= this.publishWorkMapper.selectTeacherPublishWorkBycId(tId,courseWapper.getcId(),false,(courseWapper.getPage()-1)*5,5);//结束
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectTeacherPublishWorkCount(tId,courseWapper.getcId(), false),5));//最大页数
				}
				if("1".equals(courseWapper.getState())){
					publishWorkLs= this.publishWorkMapper.selectTeacherPublishWorkBycId(tId,courseWapper.getcId(),null,(courseWapper.getPage()-1)*5,5);//全部
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectTeacherPublishWorkCount(tId,courseWapper.getcId(), null),5));//最大页数
				}
				
				publishWorks.put("countprocess", this.publishWorkMapper.selectTeacherPublishWorkCount(tId,courseWapper.getcId(), true));
				publishWorks.put("countover", this.publishWorkMapper.selectTeacherPublishWorkCount(tId,courseWapper.getcId(), false));
				publishWorks.put("countall", this.publishWorkMapper.selectTeacherPublishWorkCount(tId,courseWapper.getcId(), null));
				for(PublishWork publishWork:publishWorkLs){
					if(publishWork.getPwState()==true){
						publishWork.setPwStringState("进行中");
						publishWork.setPwBooleanState(publishWork.getPwState());
					}else{
						publishWork.setPwStringState("已结束");
						publishWork.setPwBooleanState(publishWork.getPwState());
					}
				}
				publishWorks.put("publishWorks",publishWorkLs);
				return publishWorks;
			}else{
			return null;
		}
		
	}
/*
	@Override
	public Map<String, Object> showPublishWork(CourseWapper courseWapper) {
		List<PublishWork> publishWorkLs=new ArrayList<PublishWork>();
		if(courseWapper!=null){
			Map<String, Object> publishWorks=new HashMap<String, Object>();
			if(courseWapper.getTscId()!=null){
				if("2".equals(courseWapper.getState())){
					publishWorkLs= this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(),true,(courseWapper.getPage()-1)*5,5);//进行
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), true),5));//最大页数
				}
				if("3".equals(courseWapper.getState())){
					publishWorkLs= this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(),false,(courseWapper.getPage()-1)*5,5);//结束
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), false),5));//最大页数
				}
				if("1".equals(courseWapper.getState())){
					publishWorkLs= this.publishWorkMapper.selectTeacherPublishWorkBytscId(courseWapper.getTscId(),null,(courseWapper.getPage()-1)*5,5);//全部
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), null),5));//最大页数
				}
				
				publishWorks.put("countprocess", this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), true));
				publishWorks.put("countover", this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), false));
				publishWorks.put("countall", this.publishWorkMapper.selectCountBypwStateBytscId(courseWapper.getTscId(), null));
				for(PublishWork publishWork:publishWorkLs){
					if(publishWork.getPwState()==true){
						publishWork.setPwStringState("进行中");
						publishWork.setPwBooleanState(publishWork.getPwState());
					}else{
						publishWork.setPwStringState("已结束");
						publishWork.setPwBooleanState(publishWork.getPwState());
					}
				}
				publishWorks.put("publishWorks",publishWorkLs);
				return publishWorks;
			}else{
				courseWapper.setCtId(courseWapper.getId());
				if("2".equals(courseWapper.getState())){
					publishWorkLs=this.publishWorkMapper.selectTeacherPublishWorkByctId(courseWapper.getCtId(),true,(courseWapper.getPage()-1)*5,5);//进行
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), true),5));//最大页数
				}
				if("3".equals(courseWapper.getState())){
					publishWorkLs=this.publishWorkMapper.selectTeacherPublishWorkByctId(courseWapper.getCtId(),false,(courseWapper.getPage()-1)*5,5);//结束
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), false),5));//最大页数
				}
				if("1".equals(courseWapper.getState())){
					publishWorkLs= this.publishWorkMapper.selectTeacherPublishWorkByctId(courseWapper.getCtId(),null,(courseWapper.getPage()-1)*5,5);//全部
					publishWorks.put("max",MaxPage.getMaxPage(this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), null),5));//最大页数
				}
				
				publishWorks.put("countProcess", this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), true));
				publishWorks.put("countOver", this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), false));
				publishWorks.put("countAll", this.publishWorkMapper.selectCountBypwStateByctId(courseWapper.getCtId(), null));
				for(PublishWork publishWork:publishWorkLs){
					if(publishWork.getPwState()==true){
						publishWork.setPwStringState("进行中");
						publishWork.setPwBooleanState(publishWork.getPwState());
					}else{
						publishWork.setPwStringState("已结束");
						publishWork.setPwBooleanState(publishWork.getPwState());
					}
				}
				publishWorks.put("publishWorks",publishWorkLs);
				return publishWorks;
			}
		}else{
			return null;
		}
		
	}
*/
	@Override
	public Integer addWorkRemark(Work work) {
		if(work!=null){
			if(this.work.selectWork(work.getsId(), work.getPwId())!=null){
				this.work.teacherUpdateWork(work.getsId(), work.getPwId(), work.getwRemark());
				return 1;
			}
			else{
				return 0;
			}
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
	@Override
	public int updatePublishwork(PublishWork publishwork) {
		if(publishwork.getPwState()==false){
			publishwork.setPwState(true);
		}else{
			publishwork.setPwState(false);
		}
		 this.publishWorkMapper.changePublishWork(publishwork);
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

	/**
	 * 分页查找班级内所有学生
	 * @authorweiyuhang
	 */
	public Map<String, Object> selectCourseStudents(CourseWapper courseWapper) {
		Map<String, Object> msg=new HashMap<String, Object>();
		int count=studentSelectCourseMapper.count(courseWapper);
		System.out.println(courseWapper.getTscId());
		System.out.println(courseWapper.getPage()-1);
		int cId;
		if(courseWapper.getTscId()!=null){
			cId=courseWapper.getTscId();
		}else{
			cId=courseWapper.getCtId();
		}
		List<CourseStudent> courseStudents=studentSelectCourseMapper.selectCourseStudents(cId, (courseWapper.getPage()-1)*30, 30);
		int maxpage=0;
		if(count%30!=0){
			maxpage=count/30+1;
		}else{
			maxpage=count/30;
		}
		msg.put("max", maxpage);
		msg.put("courseStudents",courseStudents);
		return msg;
	}

	@Override
	public void updataAvatar(String path, String username) {
		teacherMapper.updataAvatar(path, username);
	}

}
