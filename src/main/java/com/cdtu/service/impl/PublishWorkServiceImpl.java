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

}
