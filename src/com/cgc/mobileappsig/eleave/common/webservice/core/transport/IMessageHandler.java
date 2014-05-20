/**
 * @(#)IMessageHandler.java
 * May 30, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.transport;

/**
 * @author jeffzha
 * 
 */
public interface IMessageHandler {
	public void handleStartingRequest();

	public void handleFinishingRequest();

	public void handleResponseReceived();

	public void handleErrorReported(String error);

}
