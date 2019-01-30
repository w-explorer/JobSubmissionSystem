package com.cdtu.model;

public class CourseWapper {
	private Integer ctId;// 创课号
	private String cId;// 课程号
	private Integer tscId;//老师选课号
	private String cName;// 课程名称
	private Boolean ctSwitch;// 加课开关
	private String tName;// 老师名字
	private String state;
	private String pwId;
	private String scName;//学院名字
	private Integer id;
	private String cImgSrc;
	private String tImgSrc;
	

	public String getScName() {
		return scName;
	}

	public void setScName(String scName) {
		this.scName = scName;
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

	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getPwId() {
		return pwId;
	}

	public void setPwId(String pwId) {
		this.pwId = pwId;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getTscId() {
		return tscId;
	}

	public void setTscId(Integer tscId) {
		this.tscId = tscId;
	}

	public String getcName() {
		return cName;
	}

	public String getcId() {
		return cId;
	}

	public void setcId(String cId) {
		this.cId = cId;
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

	public Integer getCtId() {
		return ctId;
	}

	public void setCtId(Integer ctId) {
		this.ctId = ctId;
	}


	public Boolean getCtSwitch() {
		return ctSwitch;
	}

	public void setCtSwitch(Boolean ctSwitch) {
		this.ctSwitch = ctSwitch;
	}

	@Override
	public String toString() {
		return "CourseWapper [ctId=" + ctId + ", cId=" + cId + ", tscId=" + tscId + ", cName=" + cName + ", ctSwitch="
				+ ctSwitch + ", tName=" + tName + "]";
	}

	
	
	
}
