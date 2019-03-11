package com.cdtu.util;

import java.util.UUID;
/**
 * 
 * ClassName:UUID
 *
 * @author wencheng
 *
 */
public class OAUtil {
	public static String getId() {
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replaceAll("-", "");
	}
}
