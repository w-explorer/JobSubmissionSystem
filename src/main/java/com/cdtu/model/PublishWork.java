package com.cdtu.model;


public class PublishWork {
    private String pwId;

    private Integer tscId;

    private Integer ctId;
    private Integer id;

    private Boolean pwState;
    private String pwEnd;

    private String pwName;
    private String wStringstate; 
    private Boolean wBoobleanstate; 
    private String pwStringstate; 
    private Boolean pwBoobleanstate; 

    

    


	public Boolean getPwState() {
		return pwState;
	}

	public void setPwState(Boolean pwState) {
		this.pwState = pwState;
	}

	public Boolean getwBoobleanstate() {
		return wBoobleanstate;
	}

	public void setwBoobleanstate(Boolean wBoobleanstate) {
		this.wBoobleanstate = wBoobleanstate;
	}

	public Boolean getPwBoobleanstate() {
		return pwBoobleanstate;
	}

	public void setPwBoobleanstate(Boolean pwBoobleanstate) {
		this.pwBoobleanstate = pwBoobleanstate;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	public String getwStringstate() {
		return wStringstate;
	}

	public void setwStringstate(String wStringstate) {
		this.wStringstate = wStringstate;
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


    public String getPwStringstate() {
		return pwStringstate;
	}

	public void setPwStringstate(String pwStringstate) {
		this.pwStringstate = pwStringstate;
	}


	public String getPwName() {
        return pwName;
    }

    public void setPwName(String pwName) {
        this.pwName = pwName == null ? null : pwName.trim();
    }
}