package com.cdtu.service;

import java.util.List;
import java.util.Map;

import com.cdtu.model.ClassCreate;
import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.PublishEstimate;
import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.model.Teacher;
import com.cdtu.model.Work;

public interface TeacherService {

	/**
	 * @author wencheng
	 * return Teacher
	 */
	public Teacher getTeacherBytIdAndtPassword(Role role);
	/**
	 * @author wencheng
	 * return
	 */
	public void updatatPasswordBytId(Role role);
	
	public String getPasswordById(String id);
	
	
	
	/**
	 * @author weiyuhang
	 */
	public List<CourseStudent> selectCourseStudentService(CourseWapper coursewapper);

	/**
	 * 教师查询课程
	 * 
	 * @author weiyuhang
	 * @param id
	 * @return
	 */
	public List<CourseWapper> selectAllCourceService(String id);

	/**
	 * 教师修改课程
	 * 
	 * @author weiyuhang
	 * @param id
	 * @return
	 */
	public String deleteClassCreateService(Integer id);

	/**
	 * @author weiyuhang
	 * @param classcreate
	 * @return
	 */
	public String insertClassCreateService(ClassCreate classcreate);

	/**
	 * 教师修改课程
	 * 
	 * @author weiyuhang
	 * @param classcreate
	 * @return
	 */
	public String updateClassCreateService(ClassCreate classcreate);

	/**
	 * 发布评价
	 * 
	 * @author LR
	 * @param publishEstimate
	 * @return
	 */
	public Integer PublishEstimate(PublishEstimate publishEstimate);

	/**
	 * 发布作业
	 * 
	 * @author LR
	 * @param publishWork
	 * @return
	 */
	public Integer publishWork(PublishWork publishWork);
	/**
	 *  查询发布的作业
	 * @author LR
	 * @param courseWapper
	 * @param state
	 * @return
	 */
	public Map<String,Object> showPublishWork(CourseWapper courseWapper);
	/**
	 * 填写作业评语
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @param wRemark
	 * @return
	 */
	public Integer addWorkRemark(Work work);
	/**
	 * 查询学生的作业
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @return
	 */
	public Work selectStudentWork(String sId, String pwId);
	/**
	 * 查询作业中的学生
	 * @author LR
	 * @param coursewapper
	 * @param sId
	 * @param pwId
	 * @return
	 */
	public Map<String,Object> showStudentInWork(CourseWapper courseWapper);
	/**
	 * 查询作业名字
	 * @author WYH
	 * @param pwId
	 * @return
	 */
	String selectPwIdname(String pwId);
	/**
	 * 查询学生名字
	 * @author LR
	 * @param id
	 * @return
	 */
	String selectStudentName(String id);
}
