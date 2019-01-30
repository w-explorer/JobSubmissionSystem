package com.cdtu.model;

public class StudentSelectCourseWrapper {
	private int id;
	private String cName;
	private String tName;
	private String cImgSrc;
	private String tImgSrc;
	private String scName;
	private int tscId;
	

	public int getTscId() {
		return tscId;
	}

	public void setTscId(int tscId) {
		this.tscId = tscId;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String getcImgSrc() {
		return cImgSrc;
	}

	public void setcImgSrc(String cImgSrc) {
		this.cImgSrc = cImgSrc;
	}

	public String gettImgSrc() {
		return tImgSrc;
	}

	public void settImgSrc(String tImgSrc) {
		this.tImgSrc = tImgSrc;
	}

	public String getScName() {
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
	}

	

}
