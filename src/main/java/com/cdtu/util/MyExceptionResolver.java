package com.cdtu.util;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.authz.UnauthorizedException;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;

public class MyExceptionResolver implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (ex instanceof UnauthorizedException) {
			ModelAndView mv = new ModelAndView("unauthorizedUrl");
			return mv;
		}
		return null;
	}

	/**
	 * 统一异常处理
	 *
	 * @author 李红兵
	 */
	public static void handlException(Map<String, Object> map, Exception e) {
		e.printStackTrace();
		map.put("status", 500);
		map.put("msg", "抱歉，服务器开小差了");
	}

}
