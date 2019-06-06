package com.cdtu.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.cdtu.mapper.PublishWorkMapper;
import com.cdtu.model.PublishWork;
import com.cdtu.service.PublishWorkService;
import com.cdtu.util.MyDateUtil;

@Transactional
@Service(value = "publishWorkService")
public class PublishWorkServiceImpl implements PublishWorkService {

	@Resource
	private PublishWorkMapper publishWorkMapper;
	private @Resource(name = "publishWorkService") PublishWorkService publishWorkService;

	@Override
	public void changePublishWorkStateByConparetoEndTime() {
		SimpleDateFormat mySDF = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date = new Date();
		Date nowDate = null;
		Date endDate = null;
		try {
			nowDate = mySDF.parse(mySDF.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		} // 当前时间
		List<PublishWork> publishWorks = publishWorkMapper.getAllPublishWorks();
		for (PublishWork publishWork : publishWorks) {
			try {
				endDate = mySDF.parse(publishWork.getPwEnd());
				if (nowDate.compareTo(endDate) == 1) {
					Boolean flag = false;
					publishWorkMapper.updatePublishWorkState(publishWork.getPwId(), flag);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public List<Map<String, Object>> getPwDetails(String sId, String pwId) {
		List<Map<String, Object>> pwDetails = publishWorkMapper.getPwDetails(sId, pwId);
		for (Map<String, Object> map : pwDetails) {
			map.put("pwEnd", MyDateUtil.getFormattedTime(map.get("pwEnd"), "yyyy-MM-dd HH:mm"));
		}
		return pwDetails;
	}

	@Override
	public List<Map<String, Object>> getTFiles(String sId, String pwId) {
		return publishWorkMapper.getTFiles(sId, pwId);
	}

	@Override
	public List<Map<String, Object>> getSFiles(String sId, String pwId) {
		return publishWorkMapper.getSFiles(sId, pwId);
	}

	@Override
	public int countPublishWorks(String rId, String cId) {
		return publishWorkMapper.countPublishWorks(rId, cId);
	}

	@Override
	public int countPublishEstimates(String tId, String cId) {
		return publishWorkMapper.countPublishEstimates(tId, cId);
	}

	@Override
	public List<Map<String, Object>> getTFilesImages(String sId, String pwId) {
		return publishWorkMapper.getTFilesImages(sId, pwId);
	}

	@Override
	public List<Map<String, Object>> getSFilesImages(String sId, String pwId) {
		return publishWorkMapper.getSFilesImages(sId, pwId);
	}

	@Override
	public int countSPublishEstimates(String cId, String sId) {
		return publishWorkMapper.countSPublishEstimates(cId, sId);
	}

	@Override
	public List<Map<String, Object>> getTPwDetails(String pwId) {
		List<Map<String, Object>> tPwDetails = publishWorkMapper.getTPwDetails(pwId);
		for (Map<String, Object> map : tPwDetails) {
			map.put("pwEnd", MyDateUtil.getFormattedTime(map.get("pwEnd"), "yyyy-MM-dd HH:mm"));
		}
		return tPwDetails;
	}

	@Override
	public List<Map<String, Object>> getTTFiles(String pwId) {
		return publishWorkMapper.getTTFiles(pwId);
	}

	@Override
	public List<Map<String, Object>> getTTFilesImages(String pwId) {
		return publishWorkMapper.getTTFilesImages(pwId);
	}

	@Override
	public List<Map<String, Object>> getTStudents(String pwId) {
		List<Map<String, Object>> list = publishWorkMapper.getTStudentsByPwId(pwId);
		for (Map<String, Object> map : list) {
			String sId = (String) map.get("sId");
			map.put("studentFiles", publishWorkService.getSFiles(sId, pwId));
			map.put("studentFilesImages", publishWorkService.getSFilesImages(sId, pwId));
			map.put("WorkDetails", publishWorkService.getWorkDetails(sId, pwId));
		}
		return list;
	}

	@Override
	public List<Map<String, Object>> getWorkDetails(String sId, String pwId) {
		return publishWorkMapper.getWorkDetails(sId, pwId);
	}

	@Override
	public List<Map<String, Object>> getStudentsBywStateAndpwId(int state, String pwId, int page) {
		List<Map<String, Object>> students = null;
		if (state == 1) {// 带批改
			students = publishWorkMapper.getFinishsAndNotCheckStudent(pwId, (page - 1) * 5, 5);// 为批改同学作业，不包含未提交作业同学
		} else if (state == 2) {// 以及批改的学生
			students = publishWorkMapper.getNotFinishStudents(pwId, (page - 1) * 5, 5);// 未提交作业同学
		} else if (state == 3) {
			students = publishWorkMapper.getFinishStudents(pwId, (page - 1) * 5, 5);// 已经批改作业同学
		}
		return students;
	}

	@Override
	public Map<String, Object> selectPublishwork(String pwId) {
		return publishWorkMapper.selectpublishwork(pwId);
	}

	@Override
	public List<Map<String, Object>> selectTeacherFile(String pwId) {
		return publishWorkMapper.selectTeacherFile(pwId);
	}

	@Override
	public List<Map<String, Object>> getWorkBySid(String pwId, String sId) {
		List<Map<String, Object>> workBySid = publishWorkMapper.getWorkBySid(pwId, sId);
		for (Map<String, Object> map : workBySid) {
			if ((boolean) map.get("swState") == false) {
				map.put("checkStringState", "未提交");
			} else if ((boolean) map.get("twState") == false) {
				map.put("checkStringState", "带批改");
			} else {
				map.put("checkStringState", "已批改");
			}
		}
		return workBySid;
	}

	@Override
	public void deletePublishWorkService(String pwId) {
		publishWorkMapper.deletePublishWork(pwId);
	}

}
