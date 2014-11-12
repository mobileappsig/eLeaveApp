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
import android.view.Window;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class CaseDetailActivity extends Activity {
	
	private WorkItem caseItem;
	private String leaveType;
	private String status;
	private String caseID;
	private String startDay;
	private String stopDay;
	private String isHalfDay;
	private String amOrPM;
	private String employeeId;
	
	private Button btnApprove;
	private Button btnReject;
	private Button btnCancel;
	
	private String daytext;
	
	private String datedetail = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_case_detail);
		
		final TextView itemName = (TextView) findViewById(R.id.case_detail);
		
		Intent intent = getIntent();
		caseItem = (WorkItem) intent.getSerializableExtra("item");
		
		RequestParams params = new RequestParams();
		params.put("CaseId", caseItem.getCaseID());
		
		EleaveAppClient.post("Leave/queryleave", params, new AsyncHttpResponseHandler(){
			@Override
        	public void onSuccess(String response) {
        		Log.e("debug",response);
        		
        		if (response == null)
                    return;
        		
                try {
                	JSONObject jsonObject = new JSONObject(response);
                	
                	leaveType = jsonObject.getString("LeaveTypeName");
                	status = jsonObject.getString("StatusName"); 
                	employeeId = jsonObject.getString("EmployeeId");
                	
                	JSONArray jsonArrayDetail =  new JSONArray(jsonObject.getString("LeaveDetail"));

                	for (int i = 0; i<jsonArrayDetail.length(); i++) {
	                	
	                	JSONObject jObject = jsonArrayDetail.getJSONObject(i);
	                    
	                	startDay = jObject.getString("StartDay");
	                    stopDay = jObject.getString("StopDay");
	                    isHalfDay = jObject.getString("HalfDayOrNot");
	                    amOrPM = jObject.getString("AmOrPm");
	                    
	                    if (startDay.equals(stopDay)){
	                    	datedetail = datedetail + startDay;
	                    }else{
	                    	datedetail = datedetail + "From" + " " + startDay + "To" + " " + stopDay;
	                    }
	                    
	                    if (isHalfDay.equals("\u0001")){
	                    	if (amOrPM.equals("\u0001"))
	                    		daytext = "AM";
	                    	if (amOrPM.equals("\u0000"))
	                    		daytext = "PM";
	                    }
	                    if (isHalfDay.equals("\u0000"))
	                    	daytext = "WholeDay";		                	
	                    
	                    datedetail = datedetail + " " + daytext + "\n";
	                	
                    }
                    
                		
                    itemName.setText("Employee Id:\n" + " " + employeeId + "\n\n" +
                    				 "Leave Type:\n" + " " + "Statutory Annual Leave" + "\n\n" +
                    				 "Status:\n"	+ " " + status + "\n\n" +
                    				 "Detail:\n" + datedetail);
                    
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
		if (caseItem.getStatus().equals("invisible")){
			btnApprove.setVisibility(btnApprove.INVISIBLE);
			btnReject.setVisibility(btnReject.GONE);
		}
		
		btnApprove.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View arg0) {
				// TODO Auto-generated method stub
				RequestParams params = new RequestParams();
				params.put("CaseId", caseItem.getCaseID()); 
				
				EleaveAppClient.post("Leave/approveleave", params, new AsyncHttpResponseHandler(){
					@Override
		        	public void onSuccess(String response) {
		        		Log.e("debug",response);
		        		
		        		if (response == null)
		                    return;
		        		
		                try {
		                    JSONObject jsonObject = new JSONObject(response);
		                    
//		                    Toast.makeText(CaseDetailActivity.this, "Request Approved!", Toast.LENGTH_SHORT).show();
		                    
		                } catch (JSONException e) {
		                    e.printStackTrace();
		                }
		        		
					}
					
					public void onFailure(Throwable arg0) { 
		        		Toast.makeText(CaseDetailActivity.this, "on Failure",Toast.LENGTH_LONG).show();
		        		Log.e("debug",arg0.getMessage());
		        	};
					
				});
				
				back();
			}
			
		});
		
	
		btnReject.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back();
			}
			
		});
		
		btnCancel = (Button) findViewById(R.id.btn_cancel);
		btnCancel.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				back();
			}
			
		});
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.case_detail, menu);
		return true;
	}
	
    public void back() {  

		this.finish();
      }  
	
    public void backandrefresh() {  
      	Intent intent = new Intent();
		intent.setClass(CaseDetailActivity.this,MainTabActivity.class);
		startActivity(intent);
		this.finish();
      }  

}
