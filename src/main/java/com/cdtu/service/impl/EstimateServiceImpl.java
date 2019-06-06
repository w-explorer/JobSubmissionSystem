/**
 *
 */
package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.EstimateMapper;
import com.cdtu.service.EstimateService;

@Service(value = "estimateService")
public class EstimateServiceImpl implements EstimateService {
	private @Resource EstimateMapper estimateMapper;

	/**
	 * 获取某个老师某门课程的所有学生对该老师的评价
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getEstimats(String tId, String cId) {
		return estimateMapper.selEstByCourse(tId, cId);
	}

}
