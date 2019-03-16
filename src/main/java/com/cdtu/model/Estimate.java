package com.cdtu.model;

public class Estimate {
    private String eId;

    private String epId;

    private int eSpeed;

    private int eFeel;

    private String eSuggest;

    private int eDifficult;
    private String sId;

    private Boolean sEState;

	public String geteId() {
		return eId;
	}

	public void seteId(String eId) {
		this.eId = eId;
	}

	public String getEpId() {
		return epId;
	}

	public void setEpId(String epId) {
		this.epId = epId;
	}

	public int geteSpeed() {
		return eSpeed;
	}

	public void seteSpeed(int eSpeed) {
		this.eSpeed = eSpeed;
	}

	

	public int geteFeel() {
		return eFeel;
	}

	public void seteFeel(int eFeel) {
		this.eFeel = eFeel;
	}

	public String geteSuggest() {
		return eSuggest;
	}

	public void seteSuggest(String eSuggest) {
		this.eSuggest = eSuggest;
	}

	public int geteDifficult() {
		return eDifficult;
	}

	public void seteDifficult(int eDifficult) {
		this.eDifficult = eDifficult;
	}

	public String getsId() {
		return sId;
	}

	public void setsId(String sId) {
		this.sId = sId;
	}

	public Boolean getsEState() {
		return sEState;
	}

	public void setsEState(Boolean sEState) {
		this.sEState = sEState;
	}


}