package com.cdtu.model;
/**
 * 
 * ClassName:角色管理类
 *
 * @author wencheng
 *
 */
public class Role {

	private String username;
	private String name;
	private String password;
	private String role;
	private String captcha;
	private String oldPassword;
	private String newPassword;
	private String determiNenewPassword;
	private boolean rememberMe;
	private String msg;
	
	
	
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getCaptcha() {
		return captcha;
	}
	public void setCaptcha(String captcha) {
		this.captcha = captcha;
	}
	public boolean isRememberMe() {
		return rememberMe;
	}
	public void setRememberMe(boolean rememberMe) {
		this.rememberMe = rememberMe;
	}
	public String getMsg() {
		return msg;
	}
	public void setMsg(String msg) {
		this.msg = msg;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getRole() {
		return role;
	}
	public void setRole(String role) {
		if("1".equals(role)){
			this.role = "student";
		}
		else if("2".equals(role)){
			this.role = "teacher";
		}
		else if("3".equals(role)){
			this.role = "admin";
		}
		else if("student".equals(role)){
			this.role = "student";
		}
		else if("teacher".equals(role)){
			this.role = "teacher";
		}
		else if("admin".equals(role)){
			this.role = "admin";
		}
	}
	public String getOldPassword() {
		return oldPassword;
	}
	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}
	public String getNewPassword() {
		return newPassword;
	}
	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	public String getDetermiNenewPassword() {
		return determiNenewPassword;
	}
	public void setDetermiNenewPassword(String determiNenewPassword) {
		this.determiNenewPassword = determiNenewPassword;
	}
	@Override
	public String toString() {
		return "Role [username=" + username + ", password=" + password + ", role=" + role + ", captcha=" + captcha
				+ ", oldPassword=" + oldPassword + ", newPassword=" + newPassword + ", determiNenewPassword="
				+ determiNenewPassword + ", rememberMe=" + rememberMe + ", msg=" + msg + "]";
	}

	
}
