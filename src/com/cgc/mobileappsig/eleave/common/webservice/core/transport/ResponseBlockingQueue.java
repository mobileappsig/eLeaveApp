/**
 * @(#)ResponseBlockingQueue.java
 * June 1, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.transport;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;

/**
 * @author jeffzha
 * 
 */
public class ResponseBlockingQueue {

	private static final int MAX_SIZE = 20;
	private static BlockingQueue<IType> mQueue = new ArrayBlockingQueue<IType>(
			MAX_SIZE);

	private static Object mAccessLock = new Object();

	public static void put(IType object) {
		try {
			synchronized (mAccessLock) {
				if (object != null) {
					mQueue.put(object);
				} else {
					throw new InterruptedException("Try to put a NULL object!");
				}
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static IType take() {
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

}
