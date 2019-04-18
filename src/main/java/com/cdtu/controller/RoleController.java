package com.cdtu.controller;

import java.io.File;
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
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

import com.cdtu.model.Menu;
import com.cdtu.model.Role;
import com.cdtu.service.AdminstratorService;
import com.cdtu.service.MenuService;
import com.cdtu.service.StudentService;
import com.cdtu.service.TeacherService;
import com.cdtu.service.UserService;
import com.cdtu.util.GetRootPath;
import com.cdtu.util.Jwt;
import com.cdtu.util.RandomValidateCode;
import com.cdtu.util.SendEmail;

/**
 * @author wencheng
 */
@Controller
@RequestMapping("role")
public class RoleController {
	private @Resource(name = "menuService") MenuService menuService;
	private @Resource(name = "userService") UserService userService;
	private @Resource(name = "studentService") StudentService studentService;
	private @Resource(name = "teacherService") TeacherService teacherService;
	private @Resource(name = "adminstratorService") AdminstratorService adminstratorService;

	/**
	 * 统一异常处理
	 *
	 * @author 李红兵
	 */
	private void handlException(Map<String, Object> map, Exception e) {
		e.printStackTrace();
		map.put("status", 500);
		map.put("msg", "抱歉，服务器开小差了");
	}

	// 执行登陆方法
	@RequestMapping(value = "/login.do", method = RequestMethod.POST)
	public @ResponseBody Map<String, Object> doLogin(@RequestBody Role role, HttpServletResponse response,
			HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
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
		UsernamePasswordToken token1 = new UsernamePasswordToken(role.getUsername(), role.getPassword(),
				role.getRole());
		try {
//			System.out.println(role.isRememberMe() + "///////////" + subject.isRemembered() + "登陆 记住密码");
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
//			System.out.println(token + "//");
			int timeOut = 1;
			if (role.isRememberMe() != false) {
				timeOut = 30;
			}
			map.put("token", token);
			map.put("status", 200);
			map.put("timeOut", timeOut);
		}
		return map;
	}

	/**
	 * 获取用户信息
	 * 
	 * @param request
	 * @return
	 */
	@RequestMapping("/getRole.do")
	public @ResponseBody Map<String, Object> getRole(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		// 如果登录认证或记住我，则再一次授权
		if ("teacher".equals(role.getRole())) {
			map.put("role", teacherService.getTeacherBytIdAndtPassword(role));
		} else if ("student".equals(role.getRole())) {
			map.put("role", studentService.getStudentBysIdAndsPassword(role));
		} else if ("admin".equals(role.getRole())) {
			map.put("role", adminstratorService.getAdminByaIdAndaPassword(role));
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
				studentService.updatasPasswordBysId(role);
			} else if (nowRole.getRole().equals("teacher")) {
				teacherService.updatatPasswordBytId(role);
			}
			map.put("msg", "修改成功");
			map.put("status", 200);
		}
		return map;
	}

	@RequestMapping("/quit.do")
	public @ResponseBody Map<String, Object> doQuitToLoginJsp(HttpServletRequest request,
			HttpServletResponse response) {
		Subject subject = SecurityUtils.getSubject();
		subject.logout();
		HttpSession session = request.getSession();
		session.removeAttribute("role");
		Map<String, Object> map = new HashMap<>();
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
	public @ResponseBody Map<String, Object>  checkCode(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		 Map<String, Object> map = new HashMap<String, Object>();
		// 设置相应类型,告诉浏览器输出的内容为图片
		response.setContentType("image/png");

		// 设置响应头信息，告诉浏览器不要缓存此内容
		response.setHeader("pragma", "no-cache");
		response.setHeader("Cache-Control", "no-cache");
		response.setDateHeader("Expire", 0);

		RandomValidateCode randomValidateCode = new RandomValidateCode();
		String randcode = null;
		try {
			randcode=randomValidateCode.getRandcode(request, response);// 输出图片方法
		} catch (Exception e) {
			e.printStackTrace();
		}
		map.put("status", 200);
		map.put("randcodSrc", randcode);
		return map;
	}

	/**
	 * 1请求所有菜单 2根据角色请求菜单
	 */
	@RequiresRoles(value = { "student", "teacher" }, logical = Logical.OR)
	@RequestMapping(value = "/getMenuList.do")
	public @ResponseBody Map<String, Object> getMenusByRole(HttpServletRequest request) {
		Map<String, Object> map = new HashMap<>();
		Role role = (Role) request.getSession().getAttribute("role");
		List<Menu> list = null;
		if ("admin".equals(role.getRole())) {
			list = menuService.getMenuAll();
		} else if ("student".equals(role.getRole()) || "teacher".equals(role.getRole())) {
			list = menuService.getMenusByRole(role.getRole());
		}
		map.put("menuList", list);
		map.put("msg", "success");
		map.put("status", 200);
		return map;
	}

	@RequiresRoles(value = { "student", "teacher", "admin" }, logical = Logical.OR)
	@RequestMapping(value = "updateAvatar.do")
	public @ResponseBody Map<String, Object> updateAvatar(@RequestParam("file") CommonsMultipartFile file,
			HttpServletRequest request) {
		// 获取当前用户
		Subject subject = SecurityUtils.getSubject();
		Role role = (Role) subject.getPrincipal();
		Map<String, Object> map = new HashMap<>();
		String path = "";
		if (!file.isEmpty()) {
			// 获得文件类型（可以判断如果不是图片，禁止上传）
			String contentType = file.getContentType();
			// 获得文件后缀名称
			String imageName = contentType.substring(contentType.lastIndexOf("/") + 1);
			path = GetRootPath.getRootPath(request) + File.separator + "uploadFile" + File.separator + "avatar" + File.separator + role.getRole()
					+ File.separator + role.getUsername() + File.separator + "avatar." + imageName;
			File storeDirectory = new File(path);// 即代表文件又代表目录
			if (!storeDirectory.exists()) {
				storeDirectory.mkdirs();// 创建一个指定的目录
			}
			try {
				file.transferTo(new File(path));
			} catch (IllegalStateException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
			path = File.separator + "imgSrc" + File.separator + role.getRole() + File.separator + role.getUsername()
					+ File.separator + "avatar." + imageName;
		}
		try {
			if ("teacher".equals(role.getRole())) {
				teacherService.updataAvatar(path, role.getUsername());
			} else if ("student".equals(role.getRole())) {
				studentService.updataAvatar(path, role.getUsername());
			} else if ("admin".equals(role.getRole())) {
				adminstratorService.updataAvatar(path, role.getUsername());
			}
			map.put("activityImgSrc", path);
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

	/**
	 * 更具出入邮箱实现忘记密码功能
	 * 
	 * @param paramsMap
	 * @return
	 */
	@RequestMapping(value = "forgetpassword.do")
	public @ResponseBody Map<String, Object> forgetPassword(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String userName = (String) paramsMap.get("userName");
		String email = null;
		email = userService.getEmailByUsername(userName);
		if (email == null) {
			map.put("status", 404);
			map.put("msg", "您未绑定邮箱！请联系管理员！");
			return map;
		}
		List<Map<String, Object>> user = userService.getPassword(userName, email);
		String password = null;
		for (Map<String, Object> map2 : user) {
			password = (String) map2.get("password");
		}
		SendEmail.sendPasswordByEmail(email, password);
		map.put("status", 200);
		map.put("msg", "请前往邮箱查看密码！");
		return map;
	}

	@RequestMapping(value = "updateRoleInfo.do")
	public @ResponseBody Map<String, Object> updateRoleInfo(@RequestBody Map<String, Object> paramsMap) {
		Map<String, Object> map = new HashMap<>();
		String email = (String) paramsMap.get("email");
		String phone = (String) paramsMap.get("phone");
		// 获取当前用户
		Role role = (Role) SecurityUtils.getSubject().getPrincipal();
		try {
			if ("student".equals(role.getRole())) {
				studentService.updateRoleInfo(email, phone, role.getUsername());
			} else if ("teacher".equals(role.getRole())) {
				teacherService.updateRoleInfo(email, phone, role.getUsername());
			} else if ("admin".equals(role.getRole())) {
				adminstratorService.updateRoleInfo(email, phone, role.getUsername());
			}
			map.put("status", 200);
		} catch (Exception e) {
			handlException(map, e);
		}
		return map;
	}

}
