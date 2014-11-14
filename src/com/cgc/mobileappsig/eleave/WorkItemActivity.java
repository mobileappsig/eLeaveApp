package com.cgc.mobileappsig.eleave;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cgc.mobileappsig.eleave.R;
import com.cgc.mobileappsig.eleave.common.DialogUtil;
import com.cgc.mobileappsig.eleave.common.EleaveAppClient;
import com.cgc.mobileappsig.eleave.common.WorkItem;
import com.google.gson.JsonArray;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.R.bool;
import android.os.Bundle;
import android.app.Activity;
import android.content.Intent;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TabHost;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

public class WorkItemActivity extends Activity {
	
	private ArrayList<WorkItem> workItemListforMgr, workItemListforEmployee;
	private ArrayList<String> stringListforMgr, stringListforEmployee;
	private boolean isManager = false;
	ListView employeetodolist;
	ListView managertodolist;
	TextView mlist;
	TextView elist;
	private TextView TextView;
	public static int employeetodocount = 0;
	public static int managertodocount = 0;
	
	//from CaseDetailActivity
	private WorkItem caseItem;
	private String startDay;
	private String stopDay;
	private Boolean isHalfDay;
	private String amOrPM;
	private Button btnApprove;
	private Button btnReject;
	
	private ArrayAdapter<String> employeeArrayAdapter;
	private ArrayAdapter <String> managerArrayAdapter;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_todo);
		
		// 
        TabHost tabHost = (TabHost) findViewById(R.id.tabhost);  
        
        // tabHost  
        tabHost.setup();  
        
//        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("->")  
//                .setContent(R.id.elist));
        
        tabHost.addTab(tabHost.newTabSpec("tab1").setIndicator("Employee Todo List")  
                .setContent(R.id.emploeeview));
        
        Log.e("LoginActivity.EmployeeRole", LoginActivity.EmployeeRole);
        
        if (LoginActivity.EmployeeRole.equals("manager") ){
        	
              	tabHost.addTab(tabHost.newTabSpec("tab2").setIndicator("Manager Todo List")
               		.setContent(R.id.managerview)); 
        }
     
  			
		workItemListforMgr = new ArrayList<WorkItem>(); //The work item list for manager
		workItemListforEmployee = new ArrayList<WorkItem>(); // The work item list for employee
		
		stringListforMgr = new  ArrayList<String>();
		stringListforEmployee = new ArrayList<String>();
		
		//TO-DO: Render the UI with the data in the work item lists.
		
		//getWorkItems();// get the work item lists from the server
		
		//addWorkItems(); // add the work item into list view	
		employeetodolist = (ListView) findViewById(R.id.EmployeeTodoList);
		employeeArrayAdapter = new ArrayAdapter <String>(this,android.R.layout.simple_expandable_list_item_1,stringListforEmployee);
		employeetodolist.setAdapter(employeeArrayAdapter);

		managertodolist = (ListView) findViewById(R.id.ManagerTodoList);
		managerArrayAdapter = new ArrayAdapter <String> (this, android.R.layout.simple_expandable_list_item_1,stringListforMgr);
		managertodolist.setAdapter(managerArrayAdapter);
	
		managertodolist.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				Log.e("OnItemClickListener", position+"");
//				Toast.makeText(WorkItemActivity.this, "OnItemClickListener!!!",Toast.LENGTH_LONG).show();
				choseItem(parent, position);
				
			}
		});
		
		employeetodolist.setOnItemClickListener(new OnItemClickListener()
		{
			@Override
			public void onItemClick(AdapterView<?> parent, View view,
				int position, long id)
			{
				Log.e("OnItemClickListener", position+"");
//				Toast.makeText(WorkItemActivity.this, "OnItemClickListener!!!",Toast.LENGTH_LONG).show();
				viewItem(parent,position);
				
			}
		});
				
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
		workItemListforEmployee.clear();
		stringListforEmployee.clear();
		stringListforMgr.clear();
		
		RequestParams params = new RequestParams();
		params.put("EID", LoginActivity.EID);
		//params.put("EID", "eleamgr");
		
		EleaveAppClient.post("Leave/todo", params, new AsyncHttpResponseHandler(){
			
			@Override
        	public void onSuccess(String response) {
        		Log.e("debug",response);
        		//String jsonStr1 = "{\"state\":\"200\",\"EnglishName\":\"andyxiao\",\"EmploymentDate\":\"2007-07-07\",\"JobStartingYear\":\"2007\",\"ThisStat\":\"5\",\"ThisAddi\":\"20\",\"LastLeft\":\"10\",\"ThisTakeStat\":\"2\",\"ThisTakeAddi\":\"4\",\"LastTake\":\"6\"}";

                            			
        		if (response == null)
                    return;
        		
                try {
                	
                	//String jsonStr = "{\"Manager\":{},\"Employee\":{\"ResultSet\":[{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000070\",\"IssuedDate\":\"2014-07-23 10:44:00.0\"},{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000071\",\"IssuedDate\":\"2014-07-23 10:44:00.0\"},{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000076\",\"IssuedDate\":\"2014-07-23 10:44:00.0\"},{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000084\",\"IssuedDate\":\"2014-07-23 10:44:00.0\"},{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000088\",\"IssuedDate\":\"2014-07-23 10:44:00.0\"},{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000148\",\"IssuedDate\":\"2014-11-06 15:51:53.0\"},{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000149\",\"IssuedDate\":\"2014-11-07 13:29:01.0\"},{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000150\",\"IssuedDate\":\"2014-11-10 11:52:35.0\"},{\"LeaveTypeName\":\"StatutoryAnnualLeaveInThisYear\",\"EnglisthName\":\"eleave\",\"CaseId\":\"0000000151\",\"IssuedDate\":\"2014-11-10 12:03:56.0\"}]}}";
            		
                	//JSONObject jsonObject = new JSONObject(jsonStr);
                	
                    JSONObject jsonObject = new JSONObject(response);
                    
                    if(jsonObject.has("Manager")){
                    	
                        Log.e("debug","AS Manager to list "+jsonObject.getString("Manager"));
                        JSONObject jsonObjectResultSet = new JSONObject(jsonObject.getString("Manager"));
     
	                        isManager = true;
	                        
	                        if(jsonObjectResultSet.has("ResultSet")){
		                        Log.e("debug","++++jsonArrayCase+"+jsonObjectResultSet.getString("ResultSet"));
		                        JSONArray  jsonArrayCase = new JSONArray(jsonObjectResultSet.getString("ResultSet"));
		                                        
			                    Log.e("debug","++++jsonArrayCase.length()="+jsonArrayCase.length());
			                    
			                    managertodocount = jsonArrayCase.length();
			                    
			                    for (int i = 0; i<jsonArrayCase.length(); i++) {
			                	
				                	JSONObject jObject = jsonArrayCase.getJSONObject(i);
				                    
				                	WorkItem tmpItem = new WorkItem();
				                	Log.e("debug","jObject.getString(CaseId)=++++"+jObject.getString("CaseId"));
				                	tmpItem.setCaseID(jObject.getString("CaseId"));
				                	Log.e("debug","jObject.getString(LeaveTypeName)=++++"+jObject.getString("LeaveTypeName"));
				                	tmpItem.setLeaveType(jObject.getString("LeaveTypeName"));
				                	Log.e("debug","jObject.getString(EnglisthName)=++++"+jObject.getString("EnglisthName"));
				                	tmpItem.setIssueBy(jObject.getString("EnglisthName"));
				                	Log.e("debug","jObject.getString(IssuedDate)=++++"+jObject.getString("IssuedDate"));
				                	tmpItem.setIssueDate(jObject.getString("IssuedDate"));
				                	//tmpItem.setDays(jObject.getInt("LeaveDays"));
				                	//tmpItem.setStatus(jObject.getString("StatusName"));
				                	
				                	String list = "Case ID: " + tmpItem.getCaseID() + "\nIssued By: " + tmpItem.getIssueBy();
				                	stringListforMgr.add(list);
				                	workItemListforMgr.add(tmpItem);
          	                	
			                    }
			                    
			                    runOnUiThread(new Runnable(){

									@Override
									public void run() {
										// TODO Auto-generated method stub
										managerArrayAdapter.notifyDataSetChanged();
					                    //managertodolist.setAdapter(managerArrayAdapter);
									}
			                    		
			                    });
			             
	                        }
	                    	                    
                    }
                    
                    
	                if(jsonObject.has("Employee")){
	                    	Log.e("debug","AS Employee to list ++++"+jsonObject.getString("Employee"));
	                        JSONObject jsonObjectResultSet = new JSONObject(jsonObject.getString("Employee"));
	                        jsonObject = jsonObjectResultSet;	                   
	                    
		                    if(jsonObject.has("ResultSet")){
		                        Log.e("debug","jsonArrayCase=++++"+jsonObject.getString("ResultSet"));
		                        JSONArray  jsonArrayCase = new JSONArray(jsonObject.getString("ResultSet"));
		                                        
			                    Log.e("debug","jsonArrayCase.length()=++++"+jsonArrayCase.length());
			                    
			                    employeetodocount = jsonArrayCase.length();
			                    Log.e("debug","employeetodocount = jsonArrayCase.length();"+ employeetodocount);
			                    
			                    for (int i = 0; i<jsonArrayCase.length(); i++) {
			                	
				                	JSONObject jObject = jsonArrayCase.getJSONObject(i);
				                    
				                	WorkItem tmpItem = new WorkItem();
				                	Log.e("debug","jObject.getString(CaseId)=++++"+jObject.getString("CaseId"));
				                	tmpItem.setCaseID(jObject.getString("CaseId"));
				                	Log.e("debug","jObject.getString(LeaveTypeName)=++++"+jObject.getString("LeaveTypeName"));
				                	tmpItem.setLeaveType(jObject.getString("LeaveTypeName"));
				                	Log.e("debug","jObject.getString(EnglisthName)=++++"+jObject.getString("EnglisthName"));
				                	tmpItem.setIssueBy(jObject.getString("EnglisthName"));
				                	Log.e("debug","jObject.getString(IssuedDate)=++++"+jObject.getString("IssuedDate"));
				                	tmpItem.setIssueDate(jObject.getString("IssuedDate"));
				                	//tmpItem.setDays(jObject.getInt("LeaveDays"));
				                	//tmpItem.setStatus(jObject.getString("StatusName"));
				                	
				                	String list = "Case ID: " + tmpItem.getCaseID() + "\nIssued By: " + tmpItem.getIssueBy();
				                	stringListforEmployee.add(list);
				                	workItemListforEmployee.add(tmpItem);			                	
				          
				                	
			                    }
			                    
			                    runOnUiThread(new Runnable(){

									@Override
									public void run() {
										// TODO Auto-generated method stub
										employeeArrayAdapter.notifyDataSetChanged();
					                    //employeetodolist.setAdapter(employeeArrayAdapter);
									}
			                    		
			                    });
			                    	

		                    }
                    }
	                
	                
	                
	                
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
        		
                MainTabActivity.todo_num =employeetodocount + managertodocount;
                if ( MainTabActivity.todo_num >0){
                    MainTabActivity.badge.setText(MainTabActivity.todo_num.toString());
                    MainTabActivity.badge.show();
                }
                
//        		elist = (TextView) findViewById(R.id.elist);
//    			String elisttext = "You have "+ employeetodocount+" unclosed case(s)!\n\nClick the Item in the EMPLOYEE TODO LIST to show detail!";
//    			
//	    		if (LoginActivity.EmployeeRole.equals("manager")){
//	    			elisttext = elisttext + "\n\n\nAnd " + managertodocount +" case(s) need you to handle!\n\nClick the Item in the MANAGER TODO LIST to show detail!";
//	    		}
//	    		elist.setText(elisttext);
    		
			}
			
			public void onFailure(Throwable arg0) { 
        		Toast.makeText(WorkItemActivity.this, "on Failure!!!",Toast.LENGTH_LONG).show();
        		Log.e("debug",arg0.getMessage());
        		
        	};
			
		});
		
		
	}
	
	private void viewItemDetail(AdapterView<?> parent,int position)
	{
		
		//
		View detailView = getLayoutInflater().inflate(R.layout.activity_case_detail, null);
		//
		final EditText itemName = (EditText) detailView.findViewById(R.id.case_detail);

		//
		String ItemDetail = (String) parent.getAdapter().getItem(position);
		Log.e("Print list item", ItemDetail);
//		Toast.makeText(WorkItemActivity.this, ItemDetail,Toast.LENGTH_LONG).show();
//		Toast.makeText(WorkItemActivity.this, ItemDetail.substring(9, 19),Toast.LENGTH_LONG).show();
		String Caseid = ItemDetail.substring(9, 19);
		
		RequestParams params = new RequestParams();
		params.put("CaseId", Caseid); 
		
		EleaveAppClient.post("Leave/queryleave", params, new AsyncHttpResponseHandler(){
			@Override
        	public void onSuccess(String response) {
        		Log.e("debug",response);
//        		Toast.makeText(WorkItemActivity.this, response.toString(),Toast.LENGTH_LONG).show();
        		Log.e("jsonObject.toString()",response.toString());
        		
        		
        		if (response == null)
                    return;
        		
                try {
                    JSONObject jsonObject = new JSONObject(response);
                    Log.e("jsonObject.toString()",jsonObject.toString());
//                    Toast.makeText(WorkItemActivity.this, jsonObject.toString(),Toast.LENGTH_LONG).show();
                    itemName.setText(jsonObject.toString());
//                    Toast.makeText(WorkItemActivity.this, jsonObject.toString(),Toast.LENGTH_LONG).show();
//                    String startDay = jsonObject.getString("StartDay");
//                    String stopDay = jsonObject.getString("StopDay");
//                    Boolean isHalfDay = jsonObject.getBoolean("HalfDayOrNot");
//                    String amOrPM = jsonObject.getString("AmOrPm");
                    
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        		
			}
			
			public void onFailure(Throwable arg0) { 
        		Toast.makeText(WorkItemActivity.this, "on Failure",Toast.LENGTH_LONG).show();
        		Log.e("debug",arg0.getMessage());
        	};
			
		});
		
		
		DialogUtil.showDialog(WorkItemActivity.this, detailView);
	}
	
	private void viewItem(AdapterView<?> parent,int position)
	{
		
		// 
		String ItemDetail = (String) parent.getAdapter().getItem(position);
		String Caseid = ItemDetail.substring(9, 19);
		
		WorkItem chosenitem = new WorkItem();
		chosenitem.setCaseID(Caseid);
		chosenitem.setStatus("invisible");
		
      	Intent intent = new Intent(WorkItemActivity.this,CaseDetailActivity.class);
      	intent.putExtra("item", chosenitem);
		startActivity(intent);
		
	}
	
	private void choseItem(AdapterView<?> parent,int position)
	{
		
		// 
		String ItemDetail = (String) parent.getAdapter().getItem(position);
		String Caseid = ItemDetail.substring(9, 19);
		
		WorkItem chosenitem = new WorkItem();
		chosenitem.setCaseID(Caseid);
		chosenitem.setStatus("visible");
		
      	Intent intent = new Intent(WorkItemActivity.this,CaseDetailActivity.class);
      	intent.putExtra("item", chosenitem);
		startActivity(intent);
		
	}
	
	private void addWorkItems()
	{
		getWorkItems();
		
		employeetodolist = (ListView) findViewById(R.id.EmployeeTodoList);
		ArrayAdapter<String> employeeArrayAdapter = new ArrayAdapter <String>(this,android.R.layout.simple_expandable_list_item_1,stringListforEmployee);
		employeetodolist.setAdapter(employeeArrayAdapter);

		managertodolist = (ListView) findViewById(R.id.ManagerTodoList);
		ArrayAdapter <String> mangerrrayAdapter = new ArrayAdapter <String> (this, android.R.layout.simple_expandable_list_item_1,stringListforMgr);
		managertodolist.setAdapter(mangerrrayAdapter);	
		
		
//		elist = (TextView) findViewById(R.id.elist);
//			String elisttext = "You have unclosed case(s)!\nClick Employee Todo Tab to show all!";
//			
//		if (LoginActivity.EmployeeRole.equals("manager")){
//			elisttext = elisttext + "\n\nYou have case(s) to handle!\nClick Manager Todo Tab to show all!";
//		}
//		elist.setText(elisttext);
		
	}
}
