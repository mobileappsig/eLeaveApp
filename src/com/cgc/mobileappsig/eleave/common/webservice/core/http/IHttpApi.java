/**
 * @(#)IHttpApi.java
 * May 27, 2013
 *
 * Copyright 2012 - 2013 Nortels Software Inc. All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.http;

import java.io.IOException;

import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.json.JSONObject;

import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebCredentialsException;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebException;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebParseException;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json.IJsonParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.xml.IXmlParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;



/**
 * @author jeffzha
 *
 */
public interface IHttpApi {
    abstract public IType doHttpRequest(HttpRequestBase httpRequest,
            IXmlParser<? extends IType> parser) throws WebCredentialsException,
            WebParseException, WebException, IOException;

    abstract public IType doHttpRequest(HttpRequestBase httpRequest,
            IJsonParser<? extends IType> parser) throws WebCredentialsException,
            WebParseException, WebException, IOException;
    
    abstract public HttpGet createHttpGet(String url, NameValuePair... nameValuePairs);

    abstract public HttpPost createHttpPost(String url, NameValuePair... nameValuePairs);
    
    abstract public HttpPost createHttpPost(String url, JSONObject json);
}
