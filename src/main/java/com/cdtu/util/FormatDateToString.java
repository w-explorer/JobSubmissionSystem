package com.cdtu.util;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 将数据库中的date(以秒的形式呈现)转换为String ClassName:描述类
 *
 * @author wencheng
 *
 */
public class FormatDateToString {

	public static String fromatData(Object date) {
		SimpleDateFormat mySDF = new SimpleDateFormat("yyyy-MM-dd");
		return mySDF.format(date);
	}

	public static void main(String[] args) {
		Date curDate = new Date();
		SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
		String start = formatter.format(curDate.getTime() - 7 * 24 * 60 * 60 * 1000);
		String end = formatter.format(curDate);
		System.out.println(start);
		System.out.println(end);
	}
}
