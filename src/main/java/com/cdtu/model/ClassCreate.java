package com.cdtu.model;

public class ClassCreate {
    private Integer ctId;

    private String tId;

    private String ctName;

    private Boolean ctSwitch;

    public Integer getCtId() {
        return ctId;
    }

    public void setCtId(Integer ctId) {
        this.ctId = ctId;
    }

    public String gettId() {
        return tId;
    }

    public void settId(String tId) {
        this.tId = tId == null ? null : tId.trim();
    }

    public String getCtName() {
        return ctName;
    }

    public void setCtName(String ctName) {
        this.ctName = ctName == null ? null : ctName.trim();
    }

    public Boolean getCtSwitch() {
        return ctSwitch;
    }

    public void setCtSwitch(Boolean ctSwitch) {
        this.ctSwitch = ctSwitch;
    }
}