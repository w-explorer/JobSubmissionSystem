package com.cdtu.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class MyDateUtil {
	/**
	 * 将日期格式化为时间字符串
	 *
	 * @author 李红兵
	 */
	public static String getFormattedTime(Object date, String pattern) {
		SimpleDateFormat dateFomatter = new SimpleDateFormat(pattern);
		return dateFomatter.format(date);
	}

	/**
	 * 将时间字符串格式化为日期
	 *
	 * @author 李红兵
	 */
	public static Date getFormattedDate(String timeStr, String pattern) throws Exception {
		SimpleDateFormat dateFomatter = new SimpleDateFormat(pattern);
		return dateFomatter.parse(timeStr);
	}

	/**
	 * 获取两个时间的间隔（分），不足1分按1分算
	 *
	 * @author 李红兵
	 */
	public static int getTimeInterval(String date1, String date2) throws ParseException {
		SimpleDateFormat dateFomatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		long time1 = dateFomatter.parse(date1).getTime();
		long time2 = dateFomatter.parse(date2).getTime();
		int sInterval = Math.abs((int) (time2 - time1)) / 1000;// 间隔的秒数
		return sInterval % 60 == 0 ? sInterval / 60 : sInterval / 60 + 1;
	}

	/**
	 * 获取间隔几分钟的时间
	 *
	 * @author 李红兵
	 */
	public static String getIntervalTime(Date date, int interval, String pattern) {
		SimpleDateFormat dateFomatter = new SimpleDateFormat(pattern);
		Date newDate = new Date(date.getTime() + interval * 60 * 1000);
		return dateFomatter.format(newDate);
	}
}
