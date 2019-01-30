package com.cdtu.model;

import java.util.Date;
import java.util.List;

public class Work {
	private String id;

	private String pwId;

	private Date wTime;

	private String wContext;

	private String wAddr;

	private String wProblem;

	private String wRemark;

	private Boolean sWState;
	
	private String sId;
	
	private List<String> sIds;

	public List<String> getsIds() {
		return sIds;
	}

	public void setsIds(List<String> sIds) {
		this.sIds = sIds;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id == null ? null : id.trim();
	}

	public String getPwId() {
		return pwId;
	}

	public void setPwId(String pwId) {
		this.pwId = pwId == null ? null : pwId.trim();
	}

	public Date getwTime() {
		return wTime;
	}

	public void setwTime(Date wTime) {
		this.wTime = wTime;
	}

	public String getwContext() {
		return wContext;
	}

	public void setwContext(String wContext) {
		this.wContext = wContext == null ? null : wContext.trim();
	}

	public String getwAddr() {
		return wAddr;
	}

	public void setwAddr(String wAddr) {
		this.wAddr = wAddr == null ? null : wAddr.trim();
	}

	public String getwProblem() {
		return wProblem;
	}

	public void setwProblem(String wProblem) {
		this.wProblem = wProblem == null ? null : wProblem.trim();
	}

	public String getwRemark() {
		return wRemark;
	}

	public void setwRemark(String wRemark) {
		this.wRemark = wRemark == null ? null : wRemark.trim();
	}

	public Boolean getsWState() {
		return sWState;
	}

	public void setsWState(Boolean sWState) {
		this.sWState = sWState;
	}
}