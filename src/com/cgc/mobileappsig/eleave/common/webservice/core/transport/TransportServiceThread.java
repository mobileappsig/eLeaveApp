/**
 * @(#)TransportServiceThread.java
 * Jun 3, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.transport;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Looper;
import android.util.Log;

import com.cgc.mobileappsig.eleave.common.webservice.core.config.WebCoreConfig;

/**
 * @author jeffzha
 * 
 */
public class TransportServiceThread extends Thread {
	public static final String TAG = TransportServiceThread.class.toString();
	public static final boolean DEBUG = WebCoreConfig.DEBUG;

	private IService service;

	private Context context;

	private boolean isThreadRunToCompletion;

	public TransportServiceThread(IService service, Context context) {
		this.service = service;
		this.isThreadRunToCompletion = false;
		this.context = context;
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
					Log.e(TAG, "OutgoingMsgQueue is empty.", e);
				}
			}

			JSONObject json = OutgoingMessageQueue.take();

			try {
				int requestCode = json.getInt("RequestCode");
				JSONObject requestPayload = json
						.getJSONObject("RequestPayload");
				this.service.service(requestCode, requestPayload);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public boolean isThreadRunToCompletion() {
		return isThreadRunToCompletion;
	}
}
