package com.cgc.mobileappsig.eleave;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cgc.mobileappsig.eleave.R;
import com.cgc.mobileappsig.eleave.common.EleaveAppClient;
import com.cgc.mobileappsig.eleave.common.WorkItem;
import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.os.Bundle;
import android.app.Activity;
import android.util.Log;
import android.view.Menu;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class WorkItemActivity extends Activity {
	
	private ArrayList<WorkItem> workItemListforMgr, workItemListforEmployee;

	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		//ListView managertodolist = (ListView) findViewById(R.id.ManagerTodoList);
		ListView employeetodolist = (ListView) findViewById(R.id.EmployeeTodoList);
				
		workItemListforMgr = new ArrayList<WorkItem>(); //The work item list for manager
		workItemListforEmployee = new ArrayList<WorkItem>(); // The work item list for employee
		
		getWorkItems(); // get the work item lists from the server
	
		//TO-DO: Render the UI with the data in the work item lists.
		
		//managertodolist.setAdapter(new ArrayAdapter<WorkItem>(this, android.R.layout.simple_list_item_1, workItemListforMgr));  
		employeetodolist.setAdapter(new ArrayAdapter <WorkItem>(this,android.R.layout.simple_list_item_1,workItemListforEmployee));
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		
		// Get the work item list again for every time this activity is resumed,
		// as the list may be changed when the activity is suspended. 
		getWorkItems(); 
		
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		//getMenuInflater().inflate(R.menu.work_item, menu);
		return true;
	}
	
	private void getWorkItems() {
		
		workItemListforMgr.clear();
		
		RequestParams params = new RequestParams();
		params.put("EID", "ELEAERI"); 
		
		EleaveAppClient.post("Leave/todo", params, new AsyncHttpResponseHandler(){
			
			@Override
        	public void onSuccess(String response) {
        		Log.e("debug",response);
        		
        		if (response == null)
                    return;
        		
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    
                    if(jsonObject.has("Manager")){
                        Log.e("debug","jsonArrayEmployee=++++"+jsonObject.getString("Manager"));
                        JSONArray jsonArrayEmployee = new JSONArray(jsonObject.getString("Manager"));
                        for (int i = 0; i<jsonArrayEmployee.length(); i++) {
                        	JSONObject jObject = jsonArrayEmployee.getJSONObject(i);
                        	//to do add case id into 
                        }
                    }
//                    	
//                    	
//                    	
//                    	
//                    
//                    Log.e("debug","jsonObjectResultSet=++++"+jsonObjectEmployee.getString("Employee"));
//                    JSONObject jsonObjectResultSet = new JSONObject(jsonObjectEmployee.getString("Employee"));
//              
//                    Log.e("debug","jsonArrayCase=++++"+jsonObjectResultSet.getString("ResultSet"));
//                    JSONArray  jsonArrayCase = new JSONArray(jsonObjectResultSet.getString("ResultSet"));
//                                        
//                    Log.e("debug","jsonArrayCase.length()=++++"+jsonArrayCase.length());
//                    
//                    for (int i = 0; i<jsonArrayCase.length(); i++) {
//                	
//	                	JSONObject jObject = jsonArrayCase.getJSONObject(i);
//	                    
//	                	WorkItem tmpItem = new WorkItem();
//	                	tmpItem.setCaseID(jObject.getString("CaseId"));
//	                	tmpItem.setLeaveType(jObject.getString("LeaveTypeName"));
//	                	tmpItem.setIssueBy(jObject.getString("EnglisthName"));
//	                	tmpItem.setIssueDate(jObject.getString("IssuedDate"));
//	                	//tmpItem.setDays(jObject.getInt("LeaveDays"));
//	                	//tmpItem.setStatus(jObject.getString("StatusName"));
//	                	
//	                	workItemListforEmployee.add(tmpItem);
//                    }
            
                    
                    // Get the work item list for manager
//                    if(jsonObject.has("Manager")) {
//                    	
//	                    JSONArray jsonArrayMgr = jsonObject.getJSONArray("Manager");
//	                    
//	                    for (int i = 0; i<jsonArrayMgr.length(); i++) {
//	                    	
//	                    	JSONObject jObject = jsonArrayMgr.getJSONObject(i);
//	                        
//	                    	WorkItem tmpItem = new WorkItem();
//	                    	tmpItem.setCaseID(jObject.getString("caseId"));
//	                    	tmpItem.setLeaveType(jObject.getString("LeaveTypeName"));
//	                    	tmpItem.setIssueBy(jObject.getString("EID"));
//	                    	tmpItem.setIssueDate(jObject.getString("IssueDate"));
//	                    	tmpItem.setDays(jObject.getInt("LeaveDays"));
//	                    	tmpItem.setStatus(jObject.getString("StatusName"));
//	                    	
//	                    	workItemListforMgr.add(tmpItem);
//	                    }
//                    }
                    
                    // Get the work item list for employee
                    

                    	
                    	//                    	
//	                    JSONArray jsonArrayEmployee = jsonObject.getJSONArray("Employee");
//	                    
//	                    for (int i = 0; i<jsonArrayEmployee.length(); i++) {
//	                    	
//	                    	JSONObject jObject = jsonArrayEmployee.getJSONObject(i);
//	                        
//	                    	WorkItem tmpItem = new WorkItem();
//	                    	tmpItem.setCaseID(jObject.getString("CaseId"));
//	                    	tmpItem.setLeaveType(jObject.getString("LeaveTypeName"));
//	                    	tmpItem.setIssueBy(jObject.getString("EID"));
//	                    	tmpItem.setIssueDate(jObject.getString("IssueDate"));
//	                    	tmpItem.setDays(jObject.getInt("LeaveDays"));
//	                    	tmpItem.setStatus(jObject.getString("StatusName"));
//	                    	
//	                    	workItemListforEmployee.add(tmpItem);
//	                    }
                    
                    	
                    
	                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        		
			}
			
			public void onFailure(Throwable arg0) { 
        		Toast.makeText(WorkItemActivity.this, "on Failure!!!",Toast.LENGTH_LONG).show();
        		Log.e("debug",arg0.getMessage());
        		
        	};
			
		});
	}
	
}
