package com.cgc.mobileappsig.eleave;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cgc.mobileappsig.eleave.common.EleaveAppClient;
import com.cgc.mobileappsig.eleave.common.WorkItem;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

public class CaseDetailActivity extends Activity {
	
	private WorkItem caseItem;
	private String startDay;
	private String stopDay;
	private Boolean isHalfDay;
	private String amOrPM;
	private Button btnApprove;
	private Button btnReject;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_case_detail);
		
		Intent intent = getIntent();
		caseItem = (WorkItem) intent.getSerializableExtra("item");
		
		RequestParams params = new RequestParams();
		params.put("CaseID", caseItem.getCaseID()); 
		
		EleaveAppClient.get("Leave/queryleave", params, new AsyncHttpResponseHandler(){
			@Override
        	public void onSuccess(String response) {
        		Log.e("debug",response);
        		
        		if (response == null)
                    return;
        		
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    
                    startDay = jsonObject.getString("StartDay");
                    stopDay = jsonObject.getString("StopDay");
                    isHalfDay = jsonObject.getBoolean("HalfDayOrNot");
                    amOrPM = jsonObject.getString("AmOrPm");
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        		
			}
			
			public void onFailure(Throwable arg0) { 
        		Toast.makeText(CaseDetailActivity.this, "on Failure",Toast.LENGTH_LONG).show();
        		Log.e("debug",arg0.getMessage());
        	};
			
		});
		
		btnApprove = (Button) findViewById(R.id.btn_approve);
		btnReject = (Button) findViewById(R.id.btn_reject);
		
		btnApprove.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RequestParams params = new RequestParams();
				params.put("CaseID", caseItem.getCaseID()); 
				
				EleaveAppClient.post("Leave/approveleave", params, new AsyncHttpResponseHandler(){
					@Override
		        	public void onSuccess(String response) {
		        		Log.e("debug",response);
		        		
		        		if (response == null)
		                    return;
		        		
		                try {
		                    JSONObject jsonObject = new JSONObject(response);
		                    
		                    Toast.makeText(CaseDetailActivity.this, "Request Approved!", Toast.LENGTH_SHORT).show();
		                    
		                } catch (JSONException e) {
		                    e.printStackTrace();
		                }
		        		
					}
					
					public void onFailure(Throwable arg0) { 
		        		Toast.makeText(CaseDetailActivity.this, "on Failure",Toast.LENGTH_LONG).show();
		        		Log.e("debug",arg0.getMessage());
		        	};
					
				});
			}
			
		});
		
		btnReject.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.case_detail, menu);
		return true;
	}

}
