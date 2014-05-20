/**
 * @(#)IXmlParser.java
 * May 27, 2014
 *
  * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.parsers.xml;

import org.xmlpull.v1.XmlPullParser;

import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebParseException;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.IParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;


/**
 * @author jeffzha
 * 
 */
public interface IXmlParser<T extends IType> extends IParser<T> {
	public abstract T parse(XmlPullParser parser) throws WebError,
			WebParseException;
}
