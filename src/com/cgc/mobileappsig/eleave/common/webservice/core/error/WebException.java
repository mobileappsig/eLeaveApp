/**
 * @(#)WebException.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.error;

/**
 * @author jeffzha
 * 
 */
public class WebException extends Exception {
	/**
	 * 
	 */
	private static final long serialVersionUID = -1721090463886447530L;

	public WebException(String message) {
		super(message);
	}
}
