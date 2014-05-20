package com.cgc.mobileappsig.eleave.webservice.types;

import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;

public class LoginReply implements IType{

	private String eID;
	private String name;
	public String geteID() {
		return eID;
	}
	public void seteID(String eID) {
		this.eID = eID;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}

	

}
