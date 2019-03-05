package com.cdtu.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.cdtu.mapper.PublishWorkMapper;
import com.cdtu.model.PublishWork;
import com.cdtu.service.PublishWorkService;

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

}
