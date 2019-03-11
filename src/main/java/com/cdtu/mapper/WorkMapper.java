package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.Work;

public interface WorkMapper {
	/**
	 * @author 李红兵
	 */
	public List<Map<String, Object>> selSubCon(@Param("pwId") String pwId);

	/**
	 * @author 李红兵
	 */
	public List<Map<String, Object>> selAllWorks(@Param("sId") String sId, @Param("cId") String cId);

	/**
	 * @author 李红兵
	 */
	public List<Map<String, Object>> selByDateSection(@Param("sId") String sId, @Param("cId") String cId,
			@Param("start") String start, @Param("end") String end);

	/**
	 * 添加作业
	 *
	 * @author LR
	 * @param work
	 */
	void insertWork(Work work);

	/**
	 * 学生修改作业
	 *
	 * @author LR
	 * @param work
	 */
	void studentUpdateWork(Work work);

	/**
	 * 查询学生作业
	 *
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @return
	 */
	Work selectWork(@Param("sId") String sId, @Param("pwId") String pwId);

	/**
	 * 查询学生作业提交数量
	 *
	 * @author LR
	 * @param sId
	 * @param pwId
	 * @return
	 */
	Integer selectWorkCount(@Param("sId") String sId, @Param("pwId") String pwId);

	/**
	 * 教师修改作业
	 *
	 * @author LR
	 * @param work
	 */
	void teacherUpdateWork(@Param("sId") String sId, @Param("pwId") String pwId, @Param("wRemark") String wRemark);

	/**
	 * 设置文件地址
	 *
	 * @author LR
	 * @param realPath
	 */
	void updateWorkFlieStudent(@Param("sId") String sId, @Param("pwId") String pwId, @Param("wAddr") String wAddr);

	/**
	 * 获取文件地址
	 *
	 * @author LR
	 * @param sId
	 * @param pwId
	 */
	String selectWorkFlieStudent(@Param("sId") String sId, @Param("pwId") String pwId);

	/**
	 * 模糊查询作业名字
	 *
	 * @param sId
	 * @param cId
	 * @return
	 */
	public List<Map<String, Object>> fuzzySearchWorkBySidAndCid(@Param("sId") String sId, @Param("cId") String cId,
			@Param("pwName") String pwName);

	public List<Map<String, Object>> fuzzySearchWorkByTidAndCid(@Param("tId") String tId, @Param("cId") String cId,
			@Param("pwName") String pwName);

	public List<Map<String, Object>> SearchPwByPwName(@Param("tId") String tId, @Param("cId") String cId,
			@Param("pwName") String pwName);

	public List<Map<String, Object>> SsearchPwByPwName(@Param("sId") String sId, @Param("cId") String cId,
			@Param("pwName") String pwName);

	public List<Map<String, Object>> getPwDetails(@Param("sId") String sId, @Param("pwId") String pwId);
	/**
	 * 存入老师文件地址
	 * @author weiyuhang
	 */
   public void insertTeacherFilewAddr(@Param("pwId")String pwId, @Param("wAddr")String wAddr, @Param("filename")String filename,@Param("type")String type,@Param("state")Boolean state);
   /**
    * 删除老师文件
    */
   public void deleteTeacherFile(@Param("tfId")Integer tfId);
   /**
    * 删除学生文件
    * @author weiyuhang
    */
   public void deleteStudentFile(@Param("sfId")Integer sfId);
   /**
    * 初始化学生作业表
    * @author weiyuhang
    */
   public void insertWorks(@Param("wId")String wId, @Param("pwId")String pwId,@Param("sId")String sId);


   public String selectwId(@Param("pwId")String pwId,@Param("sId")String sId);
   /**
	 * 存入学生文件地址
	 * @author weiyuhang
	 */
  public void insertStudentFilewAddr(@Param("wId")String wId, @Param("wAddr")String wAddr, @Param("filename")String filename,@Param("type")String type,@Param("state")Boolean state);

}