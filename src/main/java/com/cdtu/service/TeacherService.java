package com.cdtu.service;

import java.util.List;
import java.util.Map;

import com.cdtu.model.ClassCreate;
import com.cdtu.model.CourseStudent;
import com.cdtu.model.CourseWapper;
import com.cdtu.model.PublishEstimate;
import com.cdtu.model.PublishWork;
import com.cdtu.model.Role;
import com.cdtu.model.Work;

public interface TeacherService {

	/**
	 * @author wencheng
	 * return Teacher
	 */
	public Map<String,Object> getTeacherBytIdAndtPassword(Role role);
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
	public Integer PublishEstimate(PublishEstimate publishEstimate,String tId);

	/**
	 * 发布作业
	 * 
	 * @author LR
	 * @param publishWork
	 * @return
	 */
	public String publishWork(PublishWork publishWork,String tId);
	/*
	 *  查询发布的作业
	 * @author LR
	 * @param courseWapper
	 * @param state
	 * @return
	 */
	//public Map<String,Object> showPublishWork(CourseWapper courseWappe);
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
	/**
	 * 改变作业状态
	 * @author weiyuhang
	 * @param publishwork
	 * @return
	 */
	int updatePublishwork(PublishWork publishwork);
	/**
	 * 老师删除加错的学生
	 * @param CourseStudent
	 * @return
	 */
	int deleteCourseStudent(CourseStudent CourseStudent);
	/**
	 * 查询发布的作业(课程cId)
	 * @author LR
	 * @param courseWapper
	 * @return
	 */
	public Map<String, Object> demonPublishWork(CourseWapper courseWapper,String tId);
	
	/**
	 * 老师修改头像
	 * @param path
	 * @param username
	 */
	public void updataAvatar(String path, String username);
	public String updatepublishWork(PublishWork publishWork);
    /**
     * 老师查询评价
     * @author weiyuhang
     * @param epId
     * @return
     */
	public Map<String,Object> selectEstimate(String epId);
	public void updateRoleInfo(String email, String phone, String username);
	public int countSelectPublishEstimateCount(String tId,String cId);
	public List<Map<String,Object>> selectPublishEstimate(String tId,String cId,int start,int end);
	public List<String> selectEsuggest(String epId);
	/**
	 * 对公告进行增删改
	 */
	public List<Map<String,Object>> selectCoursenoticeSrvice(String tId,String cId);
	public void deleteCoursenoticeSrvice(int cnId);
	public void insertCoursenoticeSrvice(String cnTitle,String cnContent,String tId,String cId);
    	
}
