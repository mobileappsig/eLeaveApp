/**
 * @(#)IpsTransportServiceThread.java
 * Jun 3, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.webservice.transport;

import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.cgc.mobileappsig.eleave.R;
import com.cgc.mobileappsig.eleave.common.Settings;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebCredentialsException;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebException;
import com.cgc.mobileappsig.eleave.common.webservice.core.transport.OutgoingMessageQueue;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;
import com.cgc.mobileappsig.eleave.webservice.messages.IpsMsgConstants;

/**
 * @author jeffzha
 * 
 */
public class IpsTransportServiceThread extends Thread {
	public static final String TAG = "IpsTransportServiceThread";
	public static final boolean DEBUG = Settings.DEBUG;

	private TransportServiceListener mTransportServiceListener;
	private Context paramContext;
	
	private boolean mIsThreadRunToCompletion;

	public IpsTransportServiceThread(TransportServiceListener listener, Context paramContext) {
		mTransportServiceListener = listener;
		mIsThreadRunToCompletion = false;
		this.paramContext = paramContext;
	}

	@Override
	public void run() {
		Looper.prepare();
		
		while (true) {
			while (OutgoingMessageQueue.isEmpty()) {
				try {
					// sleep 200ms
					Thread.sleep(200);
				} catch (InterruptedException e) {
					Log.e(TAG, "OutgoingMessageQueue is empty.", e);
				}
			}

			JSONObject json = OutgoingMessageQueue.take();

			try {
				int requestCode = json.getInt("RequestCode");
				JSONObject requestPayload = json
						.getJSONObject("RequestPayload");
				processRequest(requestCode, requestPayload);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	private void processRequest(int requestCode, JSONObject requestPayload) {
		if (DEBUG)
			Log.d(TAG, "processRequest()");

		try {
			switch (requestCode) {
			case IpsMsgConstants.MT_TEST:
				mTransportServiceListener.onStartingRequest();
				IpsMessageHandler.testPostJson(requestPayload);
				mTransportServiceListener.onFinishingRequest();

				break;
			case IpsMsgConstants.MT_LOGIN:
				mTransportServiceListener.onStartingRequest();
				IpsMessageHandler.login(requestPayload);
				mTransportServiceListener.onFinishingRequest();

				break;
			

				
			default:
				mTransportServiceListener
						.onError("Do not support request code " + requestCode
								+ ".");

				break;
			}
		} catch (WebCredentialsException e) {
			// TODO Auto-generated catch block
			mTransportServiceListener.onError("051 " + e.toString());
			e.printStackTrace();
		} catch (WebError e) {
			// TODO Auto-generated catch block
			mTransportServiceListener.onError("052 " + e.toString());
			e.printStackTrace();
		} catch (WebException e) {
			// TODO Auto-generated catch block
			mTransportServiceListener.onError("053 " + e.toString());
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			mTransportServiceListener.onError("Connection time out");
			e.printStackTrace();
		}
	}

	public boolean isThreadRunToCompletion() {
		return mIsThreadRunToCompletion;
	}
	
	public static interface TransportServiceListener {
		public void onStartingRequest();

		public void onFinishingRequest();

		public void onResponseReceived(IType object);

		public void onError(String error);
	}
}
