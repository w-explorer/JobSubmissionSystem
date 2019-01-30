package com.cdtu.model;

public class PublishEstimate {
    private String epId;

    private Integer tscId;

    private Integer ctId;
    private Integer id;

    private String epStartTime;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEpId() {
        return epId;
    }

    public void setEpId(String epId) {
        this.epId = epId == null ? null : epId.trim();
    }

    public Integer getTscId() {
        return tscId;
    }

    public void setTscId(Integer tscId) {
        this.tscId = tscId;
    }

    public Integer getCtId() {
        return ctId;
    }

    public void setCtId(Integer ctId) {
        this.ctId = ctId;
    }

	public String getEpStartTime() {
		return epStartTime;
	}

	public void setEpStartTime(String epStartTime) {
		this.epStartTime = epStartTime;
	}

   
}