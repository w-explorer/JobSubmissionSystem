package com.cdtu.util;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

/**
 * 得到tomcat 下root路径
 * ClassName:描述类
 *
 * @author wencheng
 *
 */
public class GetRootPath {

	public static String getRootPath(HttpServletRequest request){
		ServletContext application = request.getServletContext();
		String attribute = (String) application.getAttribute("path");
		return attribute;
	}
}
