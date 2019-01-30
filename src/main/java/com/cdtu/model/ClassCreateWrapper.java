package com.cdtu.model;

public class ClassCreateWrapper {
	private int id;
	private String cName;
	private String tName;
	private String imgSrc;
	private Boolean joinAble;
	private String msgJoinAble;
	public String getMsgJoinAble() {
		return msgJoinAble;
	}

	public void setMsgJoinAble(String msgJoinAble) {
		this.msgJoinAble = msgJoinAble;
	}

	private Boolean joined;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getcName() {
		return cName;
	}

	public void setcName(String cName) {
		this.cName = cName;
	}

	public String gettName() {
		return tName;
	}

	public void settName(String tName) {
		this.tName = tName;
	}

	public String getImgSrc() {
		return imgSrc;
	}

	public void setImgSrc(String imgSrc) {
		this.imgSrc = imgSrc;
	}

	public Boolean getJoinAble() {
		return joinAble;
	}

	public void setJoinAble(Boolean joinAble) {
		
		this.joinAble = joinAble;
	}

	public Boolean getJoined() {
		return joined;
	}

	public void setJoined(Boolean joined) {
		this.joined = joined;
	}

}
