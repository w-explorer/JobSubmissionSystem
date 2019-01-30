package com.cdtu.model;

public class StudentSelectCourse {
    private Integer sstId;

    private Integer ctId;

    private Integer tscId;
    private Integer id;

    private String sId;
    private String state;

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getSstId() {
        return sstId;
    }

    public void setSstId(Integer sstId) {
        this.sstId = sstId;
    }

    public Integer getCtId() {
        return ctId;
    }

    public void setCtId(Integer ctId) {
        this.ctId = ctId;
    }

    public Integer getTscId() {
        return tscId;
    }

    public void setTscId(Integer tscId) {
        this.tscId = tscId;
    }

    public String getsId() {
        return sId;
    }

    public void setsId(String sId) {
        this.sId = sId == null ? null : sId.trim();
    }
}