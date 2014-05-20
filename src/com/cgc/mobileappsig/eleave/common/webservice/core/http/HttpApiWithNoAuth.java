/**
 * @(#)HttpApiWithNoAuth.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.http;

import java.io.IOException;

import org.apache.http.client.methods.HttpRequestBase;

import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebCredentialsException;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebException;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebParseException;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.xml.IXmlParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;




/**
 * @author jeffzha
 *
 */
public class HttpApiWithNoAuth extends AbstractHttpApi {

    public HttpApiWithNoAuth(String clientVersion) {
        super(clientVersion);
    }

    public IType doHttpRequest(HttpRequestBase httpRequest,
            IXmlParser<? extends IType> parser) throws WebCredentialsException,
            WebParseException, WebException, IOException {
        return doHttpRequest(httpRequest, parser);
    }
}
