package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface PublishWorkService {

	void changePublishWorkStateByConparetoEndTime();

	List<Map<String,Object>> getPwDetails(String sId, String pwId);

	List<Map<String,Object>> getTFiles(String sId, String pwId);

	List<Map<String,Object>> getSFiles(String sId, String pwId);

	int countPublishWorks(String cId);

	int countPublishEstimates(String string);

}
