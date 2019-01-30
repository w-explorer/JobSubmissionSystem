package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.Work;

public interface WorkMapper {
	/**
	 * @Author 李红兵
	 */
	public List<Map<String, Object>> selSubCon(@Param("pwId") String pwId);

	/**
	 * @Author 李红兵
	 */
	public List<Map<String, Object>> selAllWorks(@Param("sId") String sId, @Param("cId") int cId);

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

}