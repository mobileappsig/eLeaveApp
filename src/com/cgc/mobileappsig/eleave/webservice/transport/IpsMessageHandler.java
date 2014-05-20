package com.cgc.mobileappsig.eleave.webservice.transport;

import java.io.IOException;

import org.json.JSONObject;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebCredentialsException;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebError;
import com.cgc.mobileappsig.eleave.common.webservice.core.error.WebException;
import com.cgc.mobileappsig.eleave.common.webservice.core.transport.ResponseBlockingQueue;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.Test;
import com.cgc.mobileappsig.eleave.webservice.http.IpsHttpApi;
import com.cgc.mobileappsig.eleave.webservice.messages.InternalMsgConstants;
import com.cgc.mobileappsig.eleave.webservice.types.LoginReply;

public class IpsMessageHandler {
	private static Activity activity;
	public static IpsHttpApi mWifiIpsHttpApi;
	private static IpsTransportServiceThread mTransportServiceThread;

	public static boolean initialize(String domain, String clientVersion) {
		
		mWifiIpsHttpApi = new IpsHttpApi(domain);
		
		if (mWifiIpsHttpApi != null)
			return mWifiIpsHttpApi.initIpsHttpClient(domain, clientVersion);		

		return false;
	}
	
	public static boolean reInitialize(String domain, String clientVersion) {
		IpsMessageHandler.mWifiIpsHttpApi = null;
		return initialize(domain, clientVersion);
	}
	
	public static void setActivity(Activity activity) {
		Log.i("IpsMessageHandler", activity.toString());
		IpsMessageHandler.activity = activity;
	}

	@SuppressLint("HandlerLeak")
	private static Handler mHandler = new Handler() {
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case InternalMsgConstants.HANDLER_STARTING_REQUEST:
				handleStartingRequest();
				break;
			case InternalMsgConstants.HANDLER_FINISHING_REQUEST:
				handleFinishingRequest();
				break;
			case InternalMsgConstants.HANDLER_RESPONSE_RECEIVED:
				handleResponseReceived();
				break;
			case InternalMsgConstants.HANDLER_ERROR_REPORTED:
				Bundle bundle = msg.getData();
				String error = bundle.getString("error");
				handleErrorReported(error);
				break;
			default:
				break;
			}
		}
	};

	private static void handleStartingRequest() {
		// showProgressDialog();
	}

	private static void handleFinishingRequest() {
		// dismissProgressDialog();
	}

	private static void handleResponseReceived() {
		IType object = ResponseBlockingQueue.take();

		handleMessage(object);
	}

	private static void handleErrorReported(String error) {
		if ((error != null) && !TextUtils.isEmpty(error)) {
//			Util.showToast(activity, error, Toast.LENGTH_LONG);
		}
	}

	private static void handleMessage(IType object) {
		if (object == null) {
			return;
		}
		
		if (object instanceof LoginReply) {
			LoginReply loginResult = (LoginReply) object;
			// Handle login result here.
			Log.i("IpsMessageHandler", "eID:"+loginResult.geteID()+" name:"+loginResult.getName());
			return;
		}				
		
	}
			

	public static void startTransportServiceThread() {
		Log.e("IpsMessageHandler", "Start IpsMessageHandler!");
		
		if (mTransportServiceThread == null) {
			IpsTransportServiceListener listener = new IpsTransportServiceListener(mHandler);
			mTransportServiceThread = new IpsTransportServiceThread(listener,
					activity.getApplicationContext());
			mTransportServiceThread.setName("TransportService");

			mTransportServiceThread.start();

			Log.e("IpsMessageHandler", "IpsMessageHandler Started!");

		} else {

			// Returns true if the receiver has already been started and
			// still runs code (hasn't died yet). Returns false either if
			// the receiver hasn't been started yet or if it has already
			// started and run to completion and died.
			//
			// Only start thread when it haven't started.
			if (!mTransportServiceThread.isAlive()) {
				if (mTransportServiceThread.isThreadRunToCompletion()) {
					// can not start the old thread again
					// just new a same thread
					IpsTransportServiceListener listener = new IpsTransportServiceListener(mHandler);
					mTransportServiceThread = new IpsTransportServiceThread(
							listener, activity.getApplicationContext());
					mTransportServiceThread.setName("TransportService");
					mTransportServiceThread.start();
					Log.e("IpsMessageHandler",
							"IpsMessageHandler ReStarted from Complete!");
				} else {
					mTransportServiceThread.start();
					Log.e("IpsMessageHandler", "IpsMessageHandler ReStarted!");
				}
			} else {
				Log.e("IpsMessageHandler", "IpsMessageHandler Already Started!");
			}
		}
	}
	
	public static Test testPostXml(String parameter) throws WebException,
			WebCredentialsException, WebError, IOException {
		
		if (mWifiIpsHttpApi == null) {			
			throw new WebException("IPS HTTP API is null!");			
		}
		
		return mWifiIpsHttpApi.testPostXml(parameter);
	}

	public static Test testPostJson(JSONObject json) throws WebException,
			WebCredentialsException, WebError, IOException {
		
		if (mWifiIpsHttpApi == null) {			
			throw new WebException("IPS HTTP API is null!");			
		}
		
		return mWifiIpsHttpApi.testPostJson(json);
	}

	public static LoginReply login(JSONObject json)
			throws WebException, WebCredentialsException, WebError, IOException {

		if (mWifiIpsHttpApi == null) {
			throw new WebException("IPS HTTP API is null!");
		}

		return mWifiIpsHttpApi.login(json);
	}
}
