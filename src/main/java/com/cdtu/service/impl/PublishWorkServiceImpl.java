package com.cdtu.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.PublishWorkMapper;
import com.cdtu.model.PublishWork;
import com.cdtu.service.PublishWorkService;
import com.cdtu.util.FormatDateToString;

@Service("publishWorkService")
public class PublishWorkServiceImpl implements PublishWorkService {

	@Resource
	private PublishWorkMapper publishWorkMapper;
	private @Resource(name = "publishWorkService") PublishWorkService publishWorkService;
	@Override
	public void changePublishWorkStateByConparetoEndTime() {
		SimpleDateFormat mySDF = new SimpleDateFormat("yyyy-MM-dd");
		Date date = new Date();
		Date nowDate = null;
		Date endDate = null;
	    try {
			nowDate = mySDF.parse(mySDF.format(date));
		} catch (ParseException e) {
			e.printStackTrace();
		}//当前时间
		List<PublishWork> publishWorks= publishWorkMapper.getAllPublishWorks();
		for (PublishWork publishWork : publishWorks) {
			try {
				endDate=mySDF.parse(publishWork.getPwEnd());
				if(nowDate.compareTo(endDate)==1){
					Boolean flag =false;
					publishWorkMapper.updatePublishWorkState(publishWork.getPwId(),flag);
				}
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
	}
	@Override
	public List<Map<String, Object>> getPwDetails(String sId, String pwId) {
		// TODO Auto-generated method stub
		List<Map<String,Object>> pwDetails = publishWorkMapper.getPwDetails(sId,pwId);
		for (Map<String, Object> map : pwDetails) {
			map.put("pwEnd", FormatDateToString.fromatData(map.get("pwEnd")));
		}
		return pwDetails;
	}
	@Override
	public List<Map<String, Object>> getTFiles(String sId, String pwId) {
		return publishWorkMapper.getTFiles(sId,pwId);
	}
	@Override
	public List<Map<String, Object>> getSFiles(String sId, String pwId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.getSFiles(sId,pwId);
	}
	@Override
	public int countPublishWorks(String cId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.countPublishWorks(cId);
	}
	@Override
	public int countPublishEstimates(String cId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.countPublishEstimates(cId);
	}
	@Override
	public List<Map<String, Object>> getTFilesImages(String sId, String pwId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.getTFilesImages(sId,pwId);
	}
	@Override
	public List<Map<String, Object>> getSFilesImages(String sId, String pwId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.getSFilesImages(sId,pwId);
	}
	@Override
	public int countSPublishEstimates(String cId, String sId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.countSPublishEstimates(cId,sId);
	}
	@Override
	public List<Map<String, Object>> getTPwDetails(String pwId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.getTPwDetails(pwId);
	}
	@Override
	public List<Map<String, Object>> getTTFiles(String pwId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.getTTFiles(pwId);
	}
	@Override
	public List<Map<String, Object>> getTTFilesImages(String pwId) {
		// TODO Auto-generated method stub
		return publishWorkMapper.getTTFilesImages(pwId);
	}
	@Override
	public List<Map<String, Object>> getTStudents(String pwId) {
		List<Map<String, Object>> list =publishWorkMapper.getTStudentsByPwId(pwId);
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
		// TODO Auto-generated method stub
		return publishWorkMapper.getWorkDetails(sId,pwId);
	}
	@Override
	public List<Map<String, Object>> getStudentsBywStateAndpwId(int state, String pwId,int page) {
		// TODO Auto-generated method stub
		List<Map<String, Object>> students =null;
		if(state==1){
			students = publishWorkMapper.getFinishStudents(pwId,(page - 1) * 5, 5);//提交作业同学
			for (Map<String, Object> map : students) {
				if((boolean) map.get("twState")){
					map.put("checkStringState", "已批改");
				}else{
					map.put("checkStringState", "待批改");
				}
			}
		}else if(state==2){
			students = publishWorkMapper.getNotFinishStudents(pwId,(page - 1) * 5, 5);//未提交作业同学
		}else if(state==3){
			students = publishWorkMapper.getFinishsAndNotCheckStudent(pwId,(page - 1) * 5, 5);//为批改同学作业，不包含未提交作业同学
		}
		return students;
	}

}
