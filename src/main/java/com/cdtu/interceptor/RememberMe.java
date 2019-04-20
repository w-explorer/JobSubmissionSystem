package com.cdtu.interceptor;

import java.io.IOException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cdtu.model.Role;
import com.cdtu.util.CookieUtils;
import com.cdtu.util.Jwt;

public class RememberMe implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		String token = CookieUtils.getCookieValue(request, "token");
		// 如果登录认证或记住我，则再一次授权
		Subject subject = SecurityUtils.getSubject();
		if (token != null && !"null".equals(token)) {
			Role role = new Role();
			role = Jwt.unsign(token, role.getClass());
			// token解析失败 重定向
			if (role == null) {
				responseMessage(response, request);
				System.out.println("token失效");
				return true;
			}
			if (!subject.isAuthenticated() || (Role) request.getSession().getAttribute("role") == null) {
				request.getSession().setAttribute("role", role);
				System.out.println("自动登录");
				// 构造一个用户名密码令牌 ,是否记住我
				UsernamePasswordToken token1 = new UsernamePasswordToken(role.getUsername(), role.getPassword(),
						role.getRole());
				// 提交认证
				subject.login(token1);
				return true;
			}
		}
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
	}

	// 请求不通过，返回错误信息给客户端
	private void responseMessage(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.sendRedirect(null);
	}

}
