package com.cdtu.interceptor;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.cdtu.model.Role;
import com.cdtu.util.Jwt;

/**
 *
 * ClassName:视图与Controller间的拦截器类 1.判断当前token是否过期 2.实现自动登陆 2.1认证 授权
 *
 * @author wencheng
 *
 */
public class TokenInterceptor implements HandlerInterceptor {
	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception arg3) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView model)
			throws Exception {
	}

	/**
	 * 设置成静态以后不是不会改变，而是改变以后其他人如果访问这个变量或是方法 它已经的被修改过的 所以利用这个性质可以做验证来保证初始化一次
	 */
	static boolean flag = true;

	// 拦截每个请求
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		// <mvc:exclude-mapping path="/role/checkCode" />
		if (flag) {
			flag = false;
			String path = request.getSession().getServletContext().getRealPath("/");
			path = path.substring(0, path.length() - 20) + "ROOT";
			ServletContext application = request.getServletContext();
			application.setAttribute("path", path);
			// 写要执行的代码
		}
		String servletPath = request.getServletPath();
		if (servletPath.contains("checkCode")) {
			return true;
		}
		String token = request.getHeader("token");
		Subject subject = SecurityUtils.getSubject();
		if (token != null && !"null".equals(token)) {
			Role role = new Role();
			role = Jwt.unsign(token, role.getClass());
			// token解析失败 重定向
			if (role == null) {
				responseMessage(response, request);
				System.out.println("token失效");
				return false;
			}
			if (!subject.isAuthenticated() || (Role) request.getSession().getAttribute("role") == null) {
				request.getSession().setAttribute("role", role);
				System.out.println("认证");
				// 构造一个用户名密码令牌 ,是否记住我
				UsernamePasswordToken token1 = new UsernamePasswordToken(role.getUsername(), role.getPassword(),
						role.getRole());
				// 提交认证
				subject.login(token1);
				response.sendRedirect("http://localhost:8080/#/student-classList");
				return true;
			}
		}

		response.setCharacterEncoding("utf-8");
		// 1、从cookie中取TT_TOKEN
		Role role1 = (Role) request.getSession().getAttribute("role");
		// token不存在
		if (null != token && null != role1 && !"null".equals(token)) {
			String id = role1.getUsername();
			Role role = Jwt.unsign(token, Role.class);
//			System.out.println(role.toString());

			// 解密token后的id与用户传来的id不一致，一般都是token过期
			if (null != id && null != role) {
				if (id.equals(role.getUsername())) {
					return true;
				} else {
					responseMessage(response, request);
					System.out.println("token拦截了");
					return false;
				}
			} else {
				responseMessage(response, request);
				return false;
			}
		} else {
			System.out.println("拦截  重定向");
			responseMessage(response, request);
			return false;
		}
	}

	// 请求不通过，返回错误信息给客户端
	private void responseMessage(HttpServletResponse response, HttpServletRequest request) throws IOException {
		response.sendRedirect(null);
	}

}