package com.cdtu.model;

/**
 * 
 * ClassName:管理员类
 *
 * @author wencheng
 *
 */
public class Adminstrator {
    private String aId;

    private String aPassword;
    private String aName;
    

    public String getaName() {
		return aName;
	}

	public void setaName(String aName) {
		this.aName = aName;
	}

	public String getaId() {
        return aId;
    }

    public void setaId(String aId) {
        this.aId = aId == null ? null : aId.trim();
    }

    public String getaPassword() {
        return aPassword;
    }

    public void setaPassword(String aPassword) {
        this.aPassword = aPassword == null ? null : aPassword.trim();
    }
}