/**
 * @(#)TestXmlParser.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.parsers.xml;

import java.io.IOException;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.util.Log;

import com.cgc.mobileappsig.eleave.common.Settings;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebParseException;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.Test;


/**
 * @author jeffzha
 * 
 */
public class TestXmlParser extends AbstractXmlParser<Test> {
	public static final String TAG = "TestXmlParser";
	public static final boolean DEBUG = Settings.DEBUG;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.winjune.wips.webservice.parsers.xml.AbstractXmlParser
	 * #parseInner(org.xmlpull.v1.XmlPullParser)
	 */
	@Override
	protected Test parseInner(XmlPullParser parser) throws IOException,
			XmlPullParserException, WebError, WebParseException {
		parser.require(XmlPullParser.START_TAG, null, null);

		Test test = new Test();

		while (parser.nextTag() == XmlPullParser.START_TAG) {
			String name = parser.getName();
			if ("name".equals(name)) {
				test.setName(parser.nextText());

			} else if ("note".equals(name)) {
				test.setNote(parser.nextText());

			} else {
				// Consume something we don't understand.
				if (DEBUG)
					Log.d(TAG, "Found tag that we don't recognize: " + name);
				skipSubTree(parser);
			}
		}

		return test;
	}

}
