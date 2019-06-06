package com.cdtu.util;

import java.util.UUID;

public class OAUtil {
	/**
	 * @author wencheng
	 */
	public static String getId() {
		return UUID.randomUUID().toString().replaceAll("-", "");
	}
}
