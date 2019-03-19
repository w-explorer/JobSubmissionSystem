package com.cdtu.service.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
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
	 * 统计该门课程的所有作业的平均分
	 *
	 * @author 李红兵
	 */
	@Override
	public double getAverScore(String sId, String cId) {
		return workMapper.selectAverScore(sId, cId);
	}

	/**
	 * 获取提交情况信息
	 *
	 * @author 李红兵
	 */
	@Override
	public Map<String, Object> getSubInfo(String sId, String cId) {
		return workMapper.selectSubInfo(sId, cId);
	}

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
	public List<Map<String, Object>> getAllWorks(String sId, String cId) {
		List<Map<String, Object>> maps = workMapper.selAllWorks(sId, cId);
		maps.forEach(map -> {
			map.put("submitted", "1".equals(map.get("submitted").toString()));
		});
		return maps;
	}

	/**
	 * 统计近多少天的作业分
	 *
	 * @author 李红兵
	 */
	@Override
	public List<Map<String, Object>> getScoreInLastDays(String sId, String cId, int days) {
		Date curDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String start = formatter.format(curDate.getTime() - days * 24 * 60 * 60 * 1000);
		String end = formatter.format(curDate);
		return workMapper.selByDateSection(sId, cId, start, end);
	}

	@Override
	public List<Map<String, Object>> fuzzySearchWorkBySidAndCid(String sId, String cId, String pwName) {
		List<Map<String, Object>> maps = workMapper.fuzzySearchWorkBySidAndCid(sId, cId, pwName);
		return maps;
	}

	@Override
	public List<Map<String, Object>> fuzzySearchWorkByTidAndCid(String tId, String cId, String pwName) {
		List<Map<String, Object>> maps = workMapper.fuzzySearchWorkByTidAndCid(tId, cId, pwName);
		return maps;
	}

	@Override
	public List<Map<String, Object>> SearchPwByPwName(String tId, String cId, String pwName) {
		List<Map<String, Object>> maps = workMapper.SearchPwByPwName(tId, cId, pwName);
		for (Map<String, Object> map : maps) {
			map.put("pwEnd", FormatDateToString.fromatData(map.get("pwEnd")));// 将时间秒变成字符串形式
			if ((int) map.get("pwState") == 0) {
				map.put("pwStringState", "已结束");
				map.put("pwBooleanState", false);
			} else {
				map.put("pwStringState", "进行中");
				map.put("pwBooleanState", true);
			}
		}
		return maps;
	}

	@Override
	public List<Map<String, Object>> SsearchPwByPwName(String sId, String cId, String pwName) {
		List<Map<String, Object>> maps = workMapper.SsearchPwByPwName(sId, cId, pwName);
		for (Map<String, Object> map : maps) {
			if (workMapper.selectWorkCount(sId, (String) map.get("pwId")) != 0) {
				map.put("wStringState", "已参与");
				map.put("wBooleanState", true);
			} else {
				map.put("wStringState", "未参与");
				map.put("wBooleanState", false);
			}
			map.put("pwEnd", FormatDateToString.fromatData(map.get("pwEnd")));
			if (!(boolean) map.get("pwState")) {
				map.put("pwStringState", "已结束");
				map.put("pwBooleanState", false);
			} else {
				map.put("pwStringState", "进行中");
				map.put("pwBooleanState", true);
			}
		}
		return maps;
	}

	/**
	 * 操作文件
	 *
	 * @author weiyuhang
	 *
	 */
	@Override
	public void insertTeacherFilewAddr(String pwId, String wAddr, String filename, String type, Boolean state) {
		System.out.println(pwId);
		workMapper.insertTeacherFilewAddr(pwId, wAddr, filename, type, state);

	}

	@Override
	public void deleteTeacherFile(Integer tfId) {
		workMapper.deleteTeacherFile(tfId);

	}

	@Override
	public void deleteStudentFile(Integer sfId) {
		workMapper.deleteStudentFile(sfId);

	}

	@Override
	public String selectwId(String sId, String pwId) {
		return workMapper.selectwId(sId, pwId);
	}

	@Override
	public void insertStudentFilewAddr(String wId, String wAddr, String filename, String type, Boolean state) {
		workMapper.insertStudentFilewAddr(wId, wAddr, filename, type, state);
	}

	@Override
	public String selecttfNameService(String tfAdd) {

		return workMapper.selecttfName(tfAdd);
	}

	@Override
	public String selectsfNameService(String sfAdd) {
		// TODO Auto-generated method stub
		return workMapper.selectsfName(sfAdd);
	}

	@Override
	public List<Map<String, Object>> selectWorkAllAddr(String pwId) {
		return workMapper.selectWorkAllAddr(pwId);
	}

	@Override
	public List<Map<String, Object>> selectWorkId(String pwId) {

		return workMapper.selectWorkId(pwId);
	}

	@Override
	public Map<String, Object> selectcName(String pwId) {
		// TODO Auto-generated method stub
		return workMapper.selectcName(pwId);
	}

	@Override
	public void teacherupdatework(Map<String, Object> map) {
		workMapper.teacherupdatework(map);

	}

	@Override
	public Map<String, Object> selectestimate(String epId) {
		// TODO Auto-generated method stub
		return workMapper.selectestimate(epId);
	}

}
