/**
 * @(#)IService.java
 * May 30, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.transport;

import org.json.JSONObject;

/**
 * @author jeffzha
 * 
 */
public interface IService {
	public void service(int requestCode, JSONObject requestPayload);
}
