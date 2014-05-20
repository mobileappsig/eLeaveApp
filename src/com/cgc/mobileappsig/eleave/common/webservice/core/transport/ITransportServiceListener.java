/**
 * 
 */
package com.cgc.mobileappsig.eleave.common.webservice.core.transport;

import com.cgc.mobileappsig.eleave.common.webservice.core.types.IType;

/**
 * @author jeffzha
 *
 */
public interface ITransportServiceListener {
	public void onStartingRequest();

	public void onFinishingRequest();

	public void onResponseReceived(IType object);

	public void onError(String error);
}
