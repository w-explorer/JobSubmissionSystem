package com.cdtu.test;

import java.util.Date;

import com.cdtu.util.MyDateUtil;

public class Test {
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(MyDateUtil.getIntervalTime(date, 5, "yyyy-MM-dd HH:mm:ss"));
	}
}
