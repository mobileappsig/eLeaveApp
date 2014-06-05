/**
 * @(#)IpsWebService.java
 * May 27, 2014
 *
 * Copyright 2013 - 2014 Mobile App Sig All rights reserved.
 */
package com.cgc.mobileappsig.eleave.webservice;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.util.Log;
import android.widget.Toast;

//import com.cgc.mobileappsig.eleave.R;
import com.cgc.mobileappsig.eleave.common.Settings;
import com.cgc.mobileappsig.eleave.common.webservice.core.transport.OutgoingMessageQueue;
import com.cgc.mobileappsig.eleave.webservice.transport.IpsMessageHandler;
import com.cgc.mobileappsig.eleave.common.webservice.core.transport.NetworkInfoManager;

/**
 * @author jeffzha
 * 
 */
public class IpsWebService {
	private static boolean httpConnectionEstablished = false;
	private static boolean serverReachable = false;	
	private static NetworkInfoManager networkInfoManager;
	
	public static void setActivity(Activity activity) {
		
		IpsMessageHandler.setActivity(activity);
	}	
	
	public static void activateWebService(Activity activity) {
		
		IpsMessageHandler.setActivity(activity);
		
		IpsMessageHandler.startTransportServiceThread();
		
	}

	public static void startWebService(final Activity activity) {		
		
		Settings.getServerAddress(activity, true);
				
		networkInfoManager = new NetworkInfoManager(activity.getApplicationContext());
		
		if (initialize(Settings.SERVER, Settings.VERSION)) {
			
			IpsMessageHandler.setActivity(activity);
			
			// Start the Ips Message Handler Thread if it has not been started yet.
			IpsMessageHandler.startTransportServiceThread();
			
			setHttpConnectionEstablished(true);
			
			//Hoare: bypass ping check since it doesn't work in some mobiles
			setServerReachable(true);
			//setServerReachable(WifiIpsSettings.isPingable());					
		}		
	}
	
	public static boolean sendMessage(Activity activity, int requestCode, JSONObject data) {
		
		Log.e("Request", "Code: " + requestCode + ", Data" + data.toString());
		
		if (!networkInfoManager.isConnected()) {
//			Util.showLongToast(activity, R.string.no_data_connection);
			return false;
		}		
		
		if (!isServerReachable()) {
//			Util.showLongToast(activity, R.string.server_unreachable);
			return false;
		}
		
		if (!isHttpConnectionEstablished()) {
//			Util.showLongToast(activity, R.string.no_http_connection);
			return false;
		}
				
		JSONObject json = new JSONObject();
		try {
			json.put("RequestCode", requestCode);
			json.put("RequestPayload", data);
			if (isHttpConnectionEstablished()) {
				OutgoingMessageQueue.offer(json);
			} else {
				final JSONObject json1 = json;
				
				// Use a Thread to wait for 1 more minute if the HTTP connection is not ready
				new Thread() {
					public void run() {
						for (int counter=0;counter<60;counter++) { // Run 60 * 1 s
							if (isHttpConnectionEstablished()) {
								OutgoingMessageQueue.offer(json1);
								break;
							}
							
							try {
								sleep(1000);
							} catch (InterruptedException e) {
								e.printStackTrace();
							}
						}
					}
				}.start();
			}

			return true;
		} catch (JSONException e) {
//			Util.showToast(activity, "031 " + e.toString(), Toast.LENGTH_LONG);
		}
		

		return false;
	}
	
	public static boolean isServerReachable() {
		return serverReachable;
	}

	public static void setServerReachable(boolean serverReachable) {
		IpsWebService.serverReachable = serverReachable;
	}			
	
	public static boolean isHttpConnectionEstablished() {
		return httpConnectionEstablished;
	}

	public static void setHttpConnectionEstablished(
			boolean httpConnectionEstablished) {
		IpsWebService.httpConnectionEstablished = httpConnectionEstablished;
	}	
	

	
	public static boolean initialize(String domain, String clientVersion) {
		return IpsMessageHandler.initialize(domain, clientVersion);
	}
	
	public static boolean reInitialize(String domain, String clientVersion) {		
		return IpsMessageHandler.reInitialize(domain, clientVersion);
	}
	
	/*public void setProxy(){
		WebProxy myProxy = new WebProxy("192.168.0.3:8080",true);
		//myProxy.Credentials = new NetworkCredential("username","password","domainname");
		myService.Proxy = myProxy;

	}*/	
	

}
