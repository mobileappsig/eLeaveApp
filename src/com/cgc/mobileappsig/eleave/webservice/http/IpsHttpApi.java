/**
 * @(#)WipsHttpApi.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.webservice.http;

import java.io.IOException;

import org.apache.http.auth.AuthScope;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import com.cgc.mobileappsig.eleave.common.Settings;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebCredentialsException;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebException;
import com.cgc.mobileappsig.eleave.common.webservice.core.http.HttpApiWithNoAuth;
import com.cgc.mobileappsig.eleave.common.webservice.core.http.IHttpApi;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.json.TestJsonParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.parsers.xml.TestXmlParser;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.Test;
import com.cgc.mobileappsig.eleave.webservice.parsers.json.LoginReplyJsonParser;
import com.cgc.mobileappsig.eleave.webservice.types.LoginReply;
/**
 * @author jeffzha
 * 
 */
public class IpsHttpApi {
	@SuppressWarnings("unused")
	private static final String TAG = "IpsHttpApi";
	@SuppressWarnings("unused")
	private static final boolean DEBUG = Settings.DEBUG;

	private final String mApiBaseUrl;
	private AuthScope mAuthScope;

	private IHttpApi mHttpApi;

	public IpsHttpApi(String domain) {
		mApiBaseUrl = Settings.URL_PREFIX + domain;		
	}
	
	public boolean initIpsHttpClient(String domain, String clientVersion) {
		
		try	{
			mAuthScope = new AuthScope(domain, 80);
			mHttpApi = new HttpApiWithNoAuth(clientVersion);
		} catch (Exception e) {			
			return false;
		}	
		
		if ((mAuthScope != null) && (mHttpApi != null))
			return true;
		else
			return false;		
		
	}

	public Test testPostXml(String parameter) throws WebException,
			WebCredentialsException, WebError, IOException {
		HttpPost httpPost = mHttpApi.createHttpPost(
				fullUrl(Settings.URL_API_TEST), new BasicNameValuePair(
						"parameter", parameter));
		return (Test) mHttpApi.doHttpRequest(httpPost, new TestXmlParser());
	}

	public Test testPostJson(JSONObject json) throws WebException,
			WebCredentialsException, WebError, IOException {
		HttpPost httpPost = mHttpApi.createHttpPost(
				fullUrl(Settings.URL_API_TEST), json);
		return (Test) mHttpApi.doHttpRequest(httpPost, new TestJsonParser());
	}

	public Test testGet(String parameter) throws WebException,
			WebCredentialsException, WebError, IOException {
		HttpGet httpGet = mHttpApi.createHttpGet(
				fullUrl(Settings.URL_API_TEST), new BasicNameValuePair(
						"parameter", parameter));
		return (Test) mHttpApi.doHttpRequest(httpGet, new TestXmlParser());
	}

	public LoginReply login(JSONObject json)
			throws WebException, WebCredentialsException, WebError, IOException {
		HttpPost httpPost = mHttpApi.createHttpPost(
				fullUrl(Settings.URL_API_LOGIN), json);
		return (LoginReply) mHttpApi.doHttpRequest(httpPost,
				new LoginReplyJsonParser());
	}

	private String fullUrl(String url) {
		return mApiBaseUrl + url;
	}

}
