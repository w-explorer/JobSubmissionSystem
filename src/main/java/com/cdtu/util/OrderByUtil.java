package com.cdtu.util;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cdtu.model.CourseStudent;

public class OrderByUtil {

	public static List<CourseStudent> OrderASC(List<CourseStudent> courseStudent) {
		Collections.sort(courseStudent, new Comparator<CourseStudent>(){
			public int compare(CourseStudent o1, CourseStudent o2){
				return o1.getsId().compareTo(o2.getsId());
			}
		});
		return courseStudent;
	}
}
