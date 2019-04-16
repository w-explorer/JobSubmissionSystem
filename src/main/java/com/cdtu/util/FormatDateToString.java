package com.cdtu.util;

import java.text.SimpleDateFormat;

/**
 * 将数据库中的date(以秒的形式呈现)转换为String ClassName:描述类
 *
 * @author wencheng
 *
 */
public class FormatDateToString {

	public static String fromatData(Object date) {
		SimpleDateFormat mySDF = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return mySDF.format(date);
	}
}
