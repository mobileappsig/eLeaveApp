package com.cgc.mobileappsig.eleave.common;

import android.util.Log;

import com.loopj.android.http.*;

public class EleaveAppClient {
	public static final String SERVERNAME = "http://10.178.255.124:8080";
    private static final String BASE_URL = SERVERNAME + "/eleaveAppServer/API/";
    private static AsyncHttpClient client = new AsyncHttpClient();
    
    public static void setTimeout(int timeout){
    	client.setTimeout(timeout);
    }
    
    public static void get(String url, AsyncHttpResponseHandler responseHandler) {
    	//client.get(getAbsoluteUrl(url), responseHandler);
    	Log.e("debug","get url:"+url);
    	client.get(url, responseHandler);
    }
    
    public static void get(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	client.get(getAbsoluteUrl(url), params, responseHandler);
    	Log.e("debug","get url:"+getAbsoluteUrl(url)+params);
    }
    
    public static void post(String url, RequestParams params, AsyncHttpResponseHandler responseHandler) {
    	client.post(getAbsoluteUrl(url), params, responseHandler);
    	Log.e("debug","post url:"+getAbsoluteUrl(url)+params);
    	//client.post(url, params, responseHandler);
    }
    
    private static String getAbsoluteUrl(String relativeUrl) {
		System.out.print(BASE_URL + relativeUrl);
		return BASE_URL + relativeUrl;
	}
}
