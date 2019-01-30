package com.cdtu.service.impl;

import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.ClassCreateMapper;
import com.cdtu.service.ClassCreateService;

/**
 *
 * ClassName:描述类
 *
 * @author lihongbing
 *
 */
@Service("ccService")
public class ClassCreateServiceImpl implements ClassCreateService {
	public @Resource ClassCreateMapper ccMapper;

	/**
	 * 判断课程是否存在
	 *
	 * @author 李红兵
	 */
	@Override
	public boolean isExisted(int cId) {
		return ccMapper.countByCId(cId) != 0;
	}

	/**
	 * 根据课程号查课程
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getCourseByCId(int cId, String sId) {
		Map<String, Object> map = ccMapper.selectByCId(cId, sId);
		map.put("joined", "1".equals(map.get("joined").toString()));// 1：true，0：false
		map.put("msgJoinAble", (boolean) map.get("joinAble") ? "是" : "否");
		return map;
	}
}
