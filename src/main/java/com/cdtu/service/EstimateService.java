package com.cdtu.service;

import java.util.List;
import java.util.Map;

public interface EstimateService {
	List<Map<String, Object>> getEstimats(String tId, String cId);

}
