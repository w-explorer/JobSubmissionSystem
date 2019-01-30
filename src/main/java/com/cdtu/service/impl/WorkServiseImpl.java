package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.WorkMapper;
import com.cdtu.service.WorkService;

@Service(value = "workService")
public class WorkServiseImpl implements WorkService {
	private @Resource WorkMapper workMapper;

	/**
	 * 统计作业提交情况
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> staSubCon(String pwId) {
		List<Map<String, Object>> maps = workMapper.selSubCon(pwId);
		maps.forEach(map -> {
			map.put("submitted", "1".equals(map.get("submitted").toString()));
		});
		return maps;
	}

	/**
	 * 查询该学生对应课程的所有作业
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getAllWorks(String sId, int cId) {
		List<Map<String, Object>> maps = workMapper.selAllWorks(sId, cId);
		maps.forEach(map -> {
			map.put("submitted", "1".equals(map.get("submitted").toString()));
		});
		return maps;
	}
}
