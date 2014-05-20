/**
 * @(#)OutgoingMessageQueue.java
 * May 30, 2014
 *
  * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.transport;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.json.JSONObject;

/**
 * @author ezhipin
 * 
 */
public class OutgoingMessageQueue {
	private static final int MAX_SIZE = 200;
	private static BlockingQueue<JSONObject> mQueue = new ArrayBlockingQueue<JSONObject>(
			MAX_SIZE);

	private static Object mAccessLock = new Object();

	public static boolean offer(JSONObject data) {
		synchronized (mAccessLock) {
			return mQueue.offer(data);
		}
	}

	public static JSONObject take() {
		try {
			synchronized (mAccessLock) {
				return mQueue.take();
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return null;
		}
	}

	public static boolean isEmpty() {
		return mQueue.isEmpty();

	}
}
