package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface PublishWorkService {

	void changePublishWorkStateByConparetoEndTime();

	List<Map<String,Object>> getPwDetails(String sId, String pwId);

}
