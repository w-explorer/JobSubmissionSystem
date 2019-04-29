package com.cdtu.test;

import java.util.Date;

import com.cdtu.util.MyDateUtil;

public class Test {
	public static void main(String[] args) {
		Date date = new Date();
		System.out.println(MyDateUtil.getFormattedTime(date, "yyyy-MM-dd EEE HH:mm:ss"));
	}
}
