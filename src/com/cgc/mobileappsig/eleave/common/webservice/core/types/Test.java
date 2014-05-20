/**
 * @(#)Test.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.types;


/**
 * @author jeffzha
 * 
 */
public class Test implements IType {
	private String mName;
	private String mNote;

	/**
	 * @return the name
	 */
	public String getName() {
		return mName;
	}

	/**
	 * @param name
	 *            the name to set
	 */
	public void setName(String name) {
		mName = name;
	}

	/**
	 * @return the note
	 */
	public String getNote() {
		return mNote;
	}

	/**
	 * @param note
	 *            the note to set
	 */
	public void setNote(String note) {
		mNote = note;
	}

}
