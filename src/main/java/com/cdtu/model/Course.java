package com.cdtu.model;

public class Course {
    private String cId;

    private String scId;

    private String cName;

    private Short cCredit;

    public String getcId() {
        return cId;
    }

    public void setcId(String cId) {
        this.cId = cId == null ? null : cId.trim();
    }

    public String getScId() {
        return scId;
    }

    public void setScId(String scId) {
        this.scId = scId == null ? null : scId.trim();
    }

    public String getcName() {
        return cName;
    }

    public void setcName(String cName) {
        this.cName = cName == null ? null : cName.trim();
    }

    public Short getcCredit() {
        return cCredit;
    }

    public void setcCredit(Short cCredit) {
        this.cCredit = cCredit;
    }
}