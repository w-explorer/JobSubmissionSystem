package com.cdtu.model;


public class PublishWork {
    private String pwId;

    private Integer tscId;

    private Integer ctId;
    private Integer id;

    private String pwEnd;

    private Boolean pwState;

    private String pwName;
    private String wstate; 

    

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWstate() {
		return wstate;
	}

	public void setWstate(String wstate) {
		this.wstate = wstate;
	}

	public String getPwId() {
        return pwId;
    }

    public void setPwId(String pwId) {
        this.pwId = pwId == null ? null : pwId.trim();
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



    public String getPwEnd() {
		return pwEnd;
	}

	public void setPwEnd(String pwEnd) {
		this.pwEnd = pwEnd;
	}

	public Boolean getPwState() {
        return pwState;
    }

    public void setPwState(Boolean pwState) {
        this.pwState = pwState;
    }

    public String getPwName() {
        return pwName;
    }

    public void setPwName(String pwName) {
        this.pwName = pwName == null ? null : pwName.trim();
    }
}