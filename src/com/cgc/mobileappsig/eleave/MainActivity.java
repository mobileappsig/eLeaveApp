package com.cgc.mobileappsig.eleave;

import org.json.JSONObject;

import com.cgc.mobileappsig.eleave.webservice.IpsWebService;
import com.cgc.mobileappsig.eleave.webservice.messages.IpsMsgConstants;
import com.cgc.mobileappsig.eleave.webservice.types.LoginRequest;
import com.google.gson.Gson;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.widget.Toast;

public class MainActivity extends Activity {

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		IpsWebService.startWebService(this);
		login(this);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

	public void login(Activity activity) {
		LoginRequest login = new LoginRequest("jeffzha", "jeffzha");
		
		try {
			Gson gson = new Gson();
			String json = gson.toJson(login);
			JSONObject data = new JSONObject(json);

			if (IpsWebService.sendMessage(activity, IpsMsgConstants.MT_LOGIN, data)) {
//				Util.showShortToast(activity, R.string.query_apk_version);
			} else {
				// All errors should be handled in the sendToServer
				// method
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
}
