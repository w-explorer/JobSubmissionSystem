package com.cdtu.test;

import java.util.HashMap;
import java.util.Map;

public class Test {
	public static void main(String[] args) {
		Map<String, Object> map = new HashMap<>();
		map.put("ssStatus", 0);
		System.out.println("1".equals(map.get("ssStatus").toString()));
	}
}
