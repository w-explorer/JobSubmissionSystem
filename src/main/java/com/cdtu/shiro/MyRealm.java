package com.cdtu.shiro;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import com.cdtu.mapper.MenuMapper;
import com.cdtu.model.Menu;
import com.cdtu.model.Role;
import com.cdtu.service.StudentService;
import com.cdtu.service.TeacherService;

/**
 * 自定义realm
 *
 * @author  wenc
 *
 */
public class MyRealm extends AuthorizingRealm {

	@Resource(name = "studentService")
	private StudentService studentService;
	@Resource(name = "teacherService")
	private TeacherService teacherService;
	private @Resource MenuMapper menuMapper;

	/**
	 * 认证方法
	 */
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		System.out.println("认证方法。。。");
		UsernamePasswordToken upToken = (UsernamePasswordToken) token;
		String userName = upToken.getUsername();// 从令牌中获得用户名
		String roleName = upToken.getHost();// 从令牌获取角色名
		String password = null;
		Role role = new Role();
		role.setUsername(userName);
		
		role.setRole(roleName);
		if ("teacher".equals(roleName)) {
			password = this.teacherService.getPasswordById(userName);
			role.setPassword(password);
		} else if ("student".equals(roleName)) {
			password = this.studentService.getPasswordById(userName);
			role.setPassword(password);
		}
		if (password == null) {
			// 用户名不存在
			return null;
		} else {
			// 用户名存在
			// 创建简单认证信息对象
			/***
			 * 参数一：签名，程序可以在任意位置获取当前放入的对象
			 * 参数二：从数据库中查询出的密码
			 * 参数三：当前realm的名称
			 */
			if ("teacher".equals(roleName)) {
				System.out.println("老师");
				String t_password = password;
				//返回给安全管理器，由安全管理器负责比对数据库中查询出的密码和页面提交的密码
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(role, t_password,
						this.getClass().getSimpleName());
				return info;
			} else if ("student".equals(roleName)) {
				System.out.println("student");
				String s_password = password;
				SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(role, s_password,
						this.getClass().getSimpleName());
				return info;
			}

		}
		return null;
	}

	/**
	 * 授权方法
	 */
	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		System.out.println("授權了");
		SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
		Role role = (Role) principals.getPrimaryPrincipal();
		List<Menu> menus = this.menuMapper.selectByRoleName(role.getRole());
		info.addRole(role.getRole());
		for (Menu menu : menus) {
			info.addStringPermission(menu.getPerms());
		}
		return info;
	}

}
