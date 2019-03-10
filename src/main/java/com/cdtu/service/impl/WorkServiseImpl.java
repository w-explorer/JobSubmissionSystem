package com.cdtu.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.WorkMapper;
import com.cdtu.service.WorkService;
import com.cdtu.util.FormatDateToString;

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

	@Override
	public List<Map<String, Object>> fuzzySearchWorkBySidAndCid(String sId, String cId,String pwName) {
		List<Map<String,Object>> maps =workMapper.fuzzySearchWorkBySidAndCid(sId,cId,pwName);
		return maps;
	}

	@Override
	public List<Map<String, Object>> fuzzySearchWorkByTidAndCid(String tId, String cId, String pwName) {
		List<Map<String,Object>> maps =workMapper.fuzzySearchWorkByTidAndCid(tId,cId,pwName);
		return maps;
	}

	@Override
	public List<Map<String, Object>> SearchPwByPwName(String tId, String cId, String pwName) {
		List<Map<String,Object>> maps =workMapper.SearchPwByPwName(tId,cId,pwName);
		for (Map<String, Object> map : maps) {
			map.put("pwEnd", FormatDateToString.fromatData(map.get("pwEnd")));//将时间秒变成字符串形式
			if(((int)map.get("pwState"))==0){
				map.put("pwStringState", "已结束");
				map.put("pwBooleanState", false);
			}
			else{
				map.put("pwStringState", "进行中");
				map.put("pwBooleanState", true);
			}
		}
		return maps;
	}

	@Override
	public List<Map<String, Object>> SsearchPwByPwName(String sId, String cId, String pwName) {
		List<Map<String,Object>> maps =workMapper.SsearchPwByPwName(sId,cId,pwName);
		for (Map<String, Object> map : maps) {
			if(this.workMapper.selectWorkCount(sId, (String) map.get("pwId"))!=0){
				map.put("wStringState","已参与");
				map.put("wBooleanState", true);
			}else{
				map.put("wStringState","未参与");
				map.put("wBooleanState",false);
			}
				map.put("pwEnd", FormatDateToString.fromatData(map.get("pwEnd")));
			if(!(boolean) map.get("pwState")){
				map.put("pwStringState", "已结束");
				map.put("pwBooleanState", false);
			}
			else{
				map.put("pwStringState", "进行中");
				map.put("pwBooleanState", true);
			}
		}
		return maps;
	}
}
