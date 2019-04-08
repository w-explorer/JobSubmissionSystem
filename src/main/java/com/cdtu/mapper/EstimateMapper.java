package com.cdtu.mapper;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.cdtu.model.Estimate;
import com.cdtu.model.EstimateExample;

public interface EstimateMapper {
	int countByExample(EstimateExample example);

	int deleteByExample(EstimateExample example);

	int deleteByPrimaryKey(String eId);

	int insert(Estimate record);

	int insertSelective(Estimate record);

	List<Estimate> selectByExample(EstimateExample example);

	Estimate selectByPrimaryKey(String eId);

	int updateByExampleSelective(@Param("record") Estimate record, @Param("example") EstimateExample example);

	int updateByExample(@Param("record") Estimate record, @Param("example") EstimateExample example);

	int updateByPrimaryKeySelective(Estimate record);

	int updateByPrimaryKey(Estimate record);

	/**
	 * 添加点评
	 *
	 * @author LR
	 * @param estimate
	 */
	void insertEstimate(Estimate estimate);

	public List<Map<String, Object>> selecteDifficult(@Param("epId") String epId);

	public List<Map<String, Object>> selecteFeel(@Param("epId") String epId);

	public List<Map<String, Object>> selecteSpeed(@Param("epId") String epId);

	public List<Map<String, Object>> selecteSuggest(@Param("epId") String epId);

	/**
	 * @author 李红兵
	 */
	public List<Map<String, Object>> selEstByCourse(@Param("tId") String tId, @Param("cId") String cId);

}