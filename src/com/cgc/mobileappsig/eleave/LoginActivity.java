package com.cgc.mobileappsig.eleave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.app.Activity;
import android.view.Menu;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.view.View;
import android.view.View.OnClickListener;
import android.util.Log;

import com.loopj.android.http.RequestParams;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.cgc.mobileappsig.eleave.common.EleaveAppClient;
import com.cgc.mobileappsig.eleave.common.ExitApplication;

public class LoginActivity extends Activity {
	private Button btnlogin=null;
	public static int EmployeeNum = 0;
	public static String EmployeeRole = "";
	public static String EID = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		Log.e("debug","onCreate!");
        btnlogin = (Button)findViewById(R.id.btn_login);
        btnlogin.setOnClickListener(listener);
        ExitApplication.getInstance().addActivity(this);
	}
	
	private OnClickListener listener =new OnClickListener(){
		@Override 
		public void onClick(View v) {
//			Intent cSwitchIntent = new Intent();
//			cSwitchIntent.setClass(LoginActivity.this,ApplyActivity.class);  
//            startActivity(cSwitchIntent);
//            Log.e("anne", "before");
//            LoginActivity.this.finish();
//			Log.e("anne", "finished");
			
			EditText userid = (EditText)findViewById(R.id.et_eid);
			EditText password = (EditText)findViewById(R.id.et_pass);
			String suid = userid.getText().toString().toUpperCase().trim();
			String spass = password.getText().toString();
            RequestParams params = new RequestParams();
            params.put("EID", suid);
            params.put("password", spass);
            
            EID = suid;
            Log.e("EID", EID);
            
            //Users/login
            EleaveAppClient.setTimeout(10000);
    		Log.e("debug","OnClickListener Login!");
    		
    		/*
    		Intent cSwitchIntent = new Intent();
			cSwitchIntent.setClass(LoginActivity.this,LoadingActivity.class);  
            startActivity(cSwitchIntent);
            LoginActivity.this.finish();
            */
    		
            EleaveAppClient.post("Users/login", params, new AsyncHttpResponseHandler(){
            //EleaveAppClient.post("http://146.11.0.41:8080/eleaveAppServer/API/Users/login", params, new AsyncHttpResponseHandler(){
    		//EleaveAppClient.get("http://client.azrj.cn/json/cook/cook_list.jsp?type=1&p=2", new AsyncHttpResponseHandler(){
            	@Override
            	public void onSuccess(String response) {
            		Log.e("debug",response);
                    //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

            		if (response == null)
                        return;
                      try {
                        JSONObject jsonObject = new JSONObject(response);
                        int state = 104;
                        if(jsonObject.has("state")) state = jsonObject.getInt("state");

                        switch(state){
                        case 104:
                            String error = "";
                            if(jsonObject.has("msg")) error = jsonObject.getString("msg");
                            Toast.makeText(LoginActivity.this, error,
                                Toast.LENGTH_SHORT).show();
                            break;
                        case 200:
                            //Toast.makeText(LoginActivity.this, "Login successfully!",
                            //    Toast.LENGTH_SHORT).show();
                        	
                        	JSONArray  RoleNum = new JSONArray();
                            RoleNum = jsonObject.getJSONArray("ResultSet");
                            JSONObject detailItem = RoleNum.getJSONObject(0);
                     
                        	if(detailItem.has("EmployeeId")) EmployeeNum = detailItem.getInt("EmployeeId");                  	     
                        	Log.e("Employee Number", EmployeeNum+"");
                        	
                        	if(detailItem.has("Role")) EmployeeRole = detailItem.getString("Role");                  	     
                        	Log.e("Employee Role", EmployeeRole);
                        	
                        	Intent cSwitchIntent = new Intent();
                			cSwitchIntent.setClass(LoginActivity.this,MainTabActivity.class);  
                            startActivity(cSwitchIntent);
                            LoginActivity.this.finish();
                            break;
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                          
                };
            	
            	public void onFailure(Throwable arg0) { 
            		Toast.makeText(LoginActivity.this, "on Failure",Toast.LENGTH_LONG).show();
            		Log.e("debug",arg0.getMessage());
            	};
            	
            	public void onFinish() { 
            		
                };

            });

		      
			
		}
	};

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
