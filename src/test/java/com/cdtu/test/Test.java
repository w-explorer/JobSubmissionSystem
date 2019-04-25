package com.cdtu.test;

import java.util.Date;

import com.cdtu.util.MyDateUtil;

public class Test {
	public static void main(String[] args) {
		long ms = Long.parseLong("1556209939000");
		System.out.println(MyDateUtil.getFormattedTime(new Date(ms), "HH:mm:ss"));
	}
}
