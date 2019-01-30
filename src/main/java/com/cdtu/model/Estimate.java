package com.cdtu.model;

public class Estimate {
    private String eId;

    private String epId;

    private String eSpeed;

    private String eFile;

    private String eSuggest;

    private String eDifficult;
    private String sId;

    private Boolean sEState;

    public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public String geteId() {
        return eId;
    }

    public void seteId(String eId) {
        this.eId = eId == null ? null : eId.trim();
    }

    public String getEpId() {
        return epId;
    }

    public void setEpId(String epId) {
        this.epId = epId == null ? null : epId.trim();
    }

    public String geteSpeed() {
        return eSpeed;
    }

    public void seteSpeed(String eSpeed) {
        this.eSpeed = eSpeed == null ? null : eSpeed.trim();
    }

    public String geteFile() {
        return eFile;
    }

    public void seteFile(String eFile) {
        this.eFile = eFile == null ? null : eFile.trim();
    }

    public String geteSuggest() {
        return eSuggest;
    }

    public void seteSuggest(String eSuggest) {
        this.eSuggest = eSuggest == null ? null : eSuggest.trim();
    }

    public String geteDifficult() {
        return eDifficult;
    }

    public void seteDifficult(String eDifficult) {
        this.eDifficult = eDifficult == null ? null : eDifficult.trim();
    }

    public Boolean getsEState() {
        return sEState;
    }

    public void setsEState(Boolean sEState) {
        this.sEState = sEState;
    }
}