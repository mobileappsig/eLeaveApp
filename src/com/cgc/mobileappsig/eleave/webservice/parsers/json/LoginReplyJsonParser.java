package com.cgc.mobileappsig.eleave.webservice.parsers.json;

import java.io.IOException;

import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebParseException;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json.AbstractJsonParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json.JsonPullParser;
import com.cgc.mobileappsig.eleave.webservice.types.LoginReply;
import com.google.gson.Gson;


/**
 * @author jeffzha
 * 
 */
public class LoginReplyJsonParser extends AbstractJsonParser<LoginReply> {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.winjune.wips.webservice.parsers.json.AbstractJsonParser
	 * #parseInner()
	 */
	@Override
	protected LoginReply parseInner(JsonPullParser parser) throws IOException,
			WebError, WebParseException {
		Gson gson = new Gson();
		LoginReply manager = gson.fromJson(parser.getContent(), LoginReply.class);
		return manager;
	}

}
