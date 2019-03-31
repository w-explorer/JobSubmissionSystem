package com.cdtu.service;

import java.util.List;
import java.util.Map;

import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.Estimate;
import com.cdtu.model.Role;
import com.cdtu.model.StudentSelectCourse;
import com.cdtu.model.Work;

public interface StudentService {

	/**
	 * @author wencheng return Student
	 */
	public  Map<String,Object> getStudentBysIdAndsPassword(Role role);

	/**
	 * @author wencheng return
	 */
	public void updatasPasswordBysId(Role role);

	public String getPasswordById(String id);

	/**
	 * 学生查询发布的点评
	 * 
	 * @author LR
	 * @param studentSelectCourse
	 * @return
	 */
	public List<Map<String,Object>> selectPublishEstimate(String cId,String sId,int start,int end);
	/**
	 * 提交点评
	 * 
	 * @author LR
	 * @param estimate
	 * @return
	 */
	public Integer submitEvaluation(Estimate estimate);

	/*
	 * 学生查询发布作业
	 * 
	 * @author LR
	 * 
	 * @param studentSelectCourse
	 * 
	 * @return
	 */
	// public Map<String, Object> selectPublishWork(StudentSelectCourse
	// studentSelectCourse);
	/**
	 * 提交作业
	 * 
	 * @author LR
	 * @param work
	 * @param sId
	 * @param pwId
	 * @return
	 */
	public Integer submitWork(Work work);

	/**
	 * 上传作业
	 * 
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @param wAddr
	 * @return
	 */
	public Integer updateWorkwAddr(String sId, String pwId, String wAddr);

	/**
	 * 查询文件地址
	 * 
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @return
	 */
	public String selectWorkwAddr(String sId, String pwId);

	/**
	 * 查询学生名字
	 * 
	 * @author LR
	 * @param id
	 * @return
	 */
	String selectStudentName(String id);

	/**
	 * 查询单个学生课程
	 * 
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @return
	 */
	Work showStudentWork(String sId, String pwId);

	/**
	 * @author weiyuhang
	 * @param coursewapper
	 * @return
	 */
	public List<CourseStudent> selectCourseStudentService(CourseWapper coursewapper);

	/**
	 * 学生查询发布作业（cId）
	 * 
	 * @author LR
	 * @param studentSelectCourse
	 * @param sId
	 * @return
	 */
	Map<String, Object> demonPublishWork(StudentSelectCourse studentSelectCourse, String sId);

	public void updataAvatar(String path, String username);

	public List<Map<String, Object>> fuzzySearchStudentByNameOrId(String nameOrId);

	public List<Map<String, Object>> SearchStudentById(String sId);

	public void CreatStudentTableDescRank(String cId, String tId);

	public List<Map<String, Object>> selectStudents(int page);

	public void updateRoleInfo(String email, String phone, String username);

}
