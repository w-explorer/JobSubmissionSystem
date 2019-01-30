package com.cdtu.model;

public class WorkWrapper {
	private String pwId;
	private String wName;
	private String endTime;
	private Boolean state;
	private Boolean submitted;

	public String getPwId() {
		return pwId;
	}

	public void setPwId(String pwId) {
		this.pwId = pwId;
	}

	public String getwName() {
		return wName;
	}

	public void setwName(String wName) {
		this.wName = wName;
	}

	public String getEndTime() {
		return endTime;
	}

	public void setEndTime(String endTime) {
		this.endTime = endTime;
	}

	public Boolean getState() {
		return state;
	}

	public void setState(Boolean state) {
		this.state = state;
	}

	public Boolean getSubmitted() {
		return submitted;
	}

	public void setSubmitted(Boolean submitted) {
		this.submitted = submitted;
	}

}
