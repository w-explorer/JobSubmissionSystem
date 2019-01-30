package com.cdtu.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.cdtu.model.Menu;
import com.cdtu.model.Role;
import com.cdtu.model.Student;
import com.cdtu.model.Teacher;
import com.cdtu.service.MenuService;
import com.cdtu.service.StudentService;
import com.cdtu.service.TeacherService;
import com.cdtu.util.Jwt;
import com.cdtu.util.RandomValidateCode;

/**
 * @author wencheng
 */
@Controller
@RequestMapping("role")
public class RoleController {
	@Resource(name = "studentService")
	private StudentService studentService;
	@Resource(name = "teacherService")
	private TeacherService teacherService;
	@Resource(name = "menuService")
	private MenuService menuService;

	// 执行登陆方法
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> doLogin(@RequestBody Role role, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String wcode = (String) request.getSession().getAttribute("randomcode_key");
		// 忽略大小写
		if (!role.getCaptcha().equalsIgnoreCase(wcode)) {
			map.put("status", 0);
			map.put("msg", "验证码错误！");
			return map;
		}

		// 获得当前用户对象
		Subject subject = SecurityUtils.getSubject();// 状态为“未认证”
		// 构造一个用户名密码令牌 ,是否记住我
		UsernamePasswordToken token1 = new UsernamePasswordToken(role.getUsername(), role.getPassword(), role.getRole());
		try {
			token1.setRememberMe(role.isRememberMe());
			System.out.println(role.isRememberMe() + "///////////" + subject.isRemembered() + "登陆 记住密码");
			// 提交认证
			subject.login(token1);
		} catch (Exception e) {
			map.put("msg", "账号或密码错误！");
			return map;
		}
		if (subject.isAuthenticated()) {
			System.out.println("认证成功");
			request.getSession().setAttribute("role", role);
			// 给用户jwt加密生成token
			String token = Jwt.sign(role, 60L * 1000L * 30L);
			System.out.println(token + "//");
			int timeOut = 0;
			if (role.isRememberMe() != false) {
				timeOut = 30;
			}
			map.put("token", token);
			map.put("status", 200);
			map.put("timeOut", timeOut);
		}
		return map;
	}

	// 获取用户信息
	@RequestMapping("/getRole.do")
	public @ResponseBody Map<String, Object> getRole(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<String, Object>();
		String token = request.getHeader("token");
		System.out.println(token + "//////////////");
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		// 如果登录认证或记住我，则再一次授权
		System.out.println(subject.isRemembered() + "记住密码");
		if ("teacher".equals(role.getRole())) {
			Teacher teacher = this.teacherService.getTeacherBytIdAndtPassword(role);
			role.setName(teacher.gettName());
			map.put("role", role);

		} else if ("student".equals(role.getRole())) {
			Student student = this.studentService.getStudentBysIdAndsPassword(role);
			role.setName(student.getsName());
			map.put("role", role);
		} else {
			map.put("msg", "获取用户失败");
			return map;
		}
		map.put("status", 200);
		return map;
	}

	// 修改密码
	@RequiresRoles("student")
	@RequestMapping("/updatePassword.do")
	public @ResponseBody Map<String, Object> doUpdatePassword(@RequestBody Role role, HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();

		// 获取当前用户
		Subject subject = SecurityUtils.getSubject();
		Role nowRole = (Role) subject.getPrincipal();

		if (!role.getOldPassword().equals(nowRole.getPassword())) {
			map.put("status", 0);
			map.put("msg", "旧密码错误");
			return map;
		} else {
			role.setUsername(nowRole.getUsername());
			if (nowRole.getRole().equals("student")) {
				this.studentService.updatasPasswordBysId(role);
			} else if (nowRole.getRole().equals("teacher")) {
				this.teacherService.updatatPasswordBytId(role);
			}
			map.put("msg", "修改成功");
			map.put("status", 200);
		}
		return map;
	}

	@RequestMapping(value = "/test.do")
	@RequiresRoles({ "student" })
	public void test() {
		System.out.println("uuuuuuuuu");
	}

	@RequestMapping("/quit.do")
	public @ResponseBody Map<String, Object> doQuitToLoginJsp(HttpServletRequest request, HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		HttpSession session = request.getSession();
		session.removeAttribute("role");
		Map<String, Object> map = new HashMap<String, Object>();
		int status = 200;
		map.put("status", status);
		return map;
	}

	/**
	 * 获取生成验证码显示到 UI 界面
	 *
	 * @author wencheng
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	@RequestMapping(value = "/checkCode")
	public void checkCode(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/jpeg");

		// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);

		RandomValidateCode randomValidateCode = new RandomValidateCode();
		try {
			randomValidateCode.getRandcode(request, response);// 输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 1请求所有菜单 2根据角色请求菜单
	 */
	@RequestMapping(value = "/getMenuList.do")
	public @ResponseBody Map<String, Object> getMenusByRole(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Role role = (Role) request.getSession().getAttribute("role");
		List<Menu> list = null;
		if ("admin".equals(role.getRole())) {
			list = this.menuService.getMenuAll();
		} else if ("student".equals(role.getRole()) || "teacher".equals(role.getRole())) {
			list = this.menuService.getMenusByRole(role.getRole());
		}
		map.put("menuList", list);
		map.put("msg", "success");
		map.put("status", 200);
		return map;
	}

	@RequestMapping(value = "/updateAvatar.do")
	public @ResponseBody String updateAvatar(MultipartFile file, HttpServletRequest request) {
		return null;
	}

}
