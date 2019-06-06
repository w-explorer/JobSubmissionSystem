package com.cdtu.ai.model;

/**
 * @author 小帅丶
 * 页面参数对象
 */
public class FacePageBean {
	private String imgdata;
	private String cId;
	private String imgname;
	private String password;
	private String user_info;
	public String getImgdata() {
		return imgdata;
	}
	
	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
	}

	public void setImgdata(String imgdata) {
		this.imgdata = imgdata;
	}
	public String getImgname() {
		return imgname;
	}
	public void setImgname(String imgname) {
		this.imgname = imgname;
	}
	public String getUser_info() {
		return user_info;
	}
	public void setUser_info(String user_info) {
		this.user_info = user_info;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	@Override
	public String toString() {
		return "FacePageBean [imgdata=" + imgdata + ", imgname=" + imgname + ", password=" + password + ", user_info="
				+ user_info + "]";
	}
	
	
}
