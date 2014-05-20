/**
 * @(#)TestJsonParser.java
 * May 27, 2014
 *
  * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json;

import java.io.IOException;

import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebParseException;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.Test;
import com.google.gson.Gson;

/**
 * @author jeffzha
 * 
 */
public class TestJsonParser extends AbstractJsonParser<Test> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.winjune.wips.webservice.parsers.json.AbstractJsonParser
	 * #parseInner()
	 */
	@Override
	protected Test parseInner(JsonPullParser parser) throws IOException,
			WebError, WebParseException {

		// JSONObject jsonObject = new JSONObject(parser.getContent());

		Gson gson = new Gson();
		Test test = gson.fromJson(parser.getContent(), Test.class);
		return test;
	}

}
