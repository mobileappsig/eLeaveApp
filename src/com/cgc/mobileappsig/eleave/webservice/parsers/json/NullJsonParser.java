/**
 * @(#)NullJsonParser.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.webservice.parsers.json;

import java.io.IOException;










import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebParseException;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json.AbstractJsonParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json.JsonPullParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;

/**
 * @author jeffzha
 * 
 */
public class NullJsonParser extends AbstractJsonParser<IType> {

	/* (non-Javadoc)
	 * @see comcgc.mobileappsig.eleave.webservice.parsers.json.AbstractJsonParser#parseInner(comcgc.mobileappsig.eleave.webservice.parsers.json.JsonPullParser)
	 */
	@Override
	protected IType parseInner(JsonPullParser parser) throws IOException,
			WebError, WebParseException {
		// TODO Auto-generated method stub
		return null;
	}

}
