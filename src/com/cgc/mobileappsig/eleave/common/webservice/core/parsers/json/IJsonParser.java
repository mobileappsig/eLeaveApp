/**
 * @(#)IJsonParser.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json;

import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebParseException;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.IParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;



/**
 * @author jeffzha
 * 
 */
public interface IJsonParser<T extends IType> extends IParser<T> {
	public abstract T parse(JsonPullParser parser) throws WebError, WebParseException;
}
