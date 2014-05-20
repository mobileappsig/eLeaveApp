/**
 * @(#)JsonPullParser.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json;

import java.io.InputStream;

/**
 * @author jeffzha
 *
 */
public class JsonPullParser {
	private String content;
	
	public void setInput(String reader) {
		content = reader;
	}
	
	public void setInput(InputStream is, String input) {
		
	}
	
	public String getContent() {
		return content;
	}
}
