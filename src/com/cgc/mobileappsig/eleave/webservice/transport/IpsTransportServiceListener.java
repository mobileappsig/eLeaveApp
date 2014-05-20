/**
 * @(#)IpsTransportServiceListener.java
 * Jun 3, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.webservice.transport;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.cgc.mobileappsig.eleave.common.Settings;
import com.cgc.mobileappsig.eleave.common.webservice.core.transport.ResponseBlockingQueue;
import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;
import com.cgc.mobileappsig.eleave.webservice.messages.InternalMsgConstants;
import com.cgc.mobileappsig.eleave.webservice.transport.IpsTransportServiceThread.TransportServiceListener;

/**
 * @author jeffzha
 * 
 */
public class IpsTransportServiceListener implements TransportServiceListener {
	public static final String TAG = "IpsTransportServiceListener";
	public static final boolean DEBUG = Settings.DEBUG;

	private Handler mHandler;

	public IpsTransportServiceListener(Handler handler) {
		mHandler = handler;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * comcgc.mobileappsig.eleave.webservice.TransportServiceThread.
	 * TransportServiceListener#onStartingRequest()
	 */
	@Override
	public void onStartingRequest() {
		// TODO Auto-generated method stub

		if (mHandler != null)
			mHandler.obtainMessage(InternalMsgConstants.HANDLER_STARTING_REQUEST)
					.sendToTarget();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgc.mobileappsig.eleave.webservice.TransportServiceThread.
	 * TransportServiceListener#onFinishingRequest()
	 */
	@Override
	public void onFinishingRequest() {
		// TODO Auto-generated method stub

		if (mHandler != null)
			mHandler.obtainMessage(InternalMsgConstants.HANDLER_FINISHING_REQUEST)
					.sendToTarget();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.cgc.mobileappsig.eleave.webservice.TransportServiceThread.
	 * TransportServiceListener
	 * #onResponseReceived
	 */
	@Override
	public void onResponseReceived(IType object) {
		// TODO Auto-generated method stub
		if (mHandler != null) {
			ResponseBlockingQueue.put(object);

			mHandler.obtainMessage(InternalMsgConstants.HANDLER_RESPONSE_RECEIVED)
					.sendToTarget();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * comcgc.mobileappsig.eleave.webservice.TransportServiceThread.
	 * TransportServiceListener#onError(java.lang.String)
	 */
	@Override
	public void onError(String error) {
		// TODO Auto-generated method stub
		if (mHandler != null) {
			Message message = mHandler
					.obtainMessage(InternalMsgConstants.HANDLER_ERROR_REPORTED);
			Bundle bundle = new Bundle();
			bundle.putString("error", error);
			message.setData(bundle);

			message.sendToTarget();
		}
	}

}
