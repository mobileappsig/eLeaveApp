package com.cgc.mobileappsig.eleave;

import com.cgc.mobileappsig.eleave.common.ExitApplication;
import com.cgc.mobileappsig.eleave.common.EleaveAppClient;
import com.loopj.android.http.RequestParams;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.apache.commons.logging.LogFactory;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;
//import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import android.widget.Toast;
import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity; 
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.TextView;


/***
 * 
 * @author ezzgxxo
 *
 */

public class HomeActivity extends Activity {
	
	
	public static int EmployeeNum = 0;
	
	
	
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);

        //TextView tv = new TextView(this); 
        //tv.setText("This is Home Activity!"); 
        //tv.setGravity(Gravity.CENTER); 
        setContentView(R.layout.home);
        
        ShowHomePage(LoginActivity.EID);
        
        //get trl_show
    	LinearLayout linearLayout = (LinearLayout) findViewById(R.id.trl_show);
    	//set listener to trl_show
    	linearLayout.setOnClickListener(new OnClickListener()
    	{

			@Override
			public void onClick(View source) {
				ShowHomePage(LoginActivity.EID);
			}
				
			});
    	
        //get trl_show
    	 Button bn = (Button) findViewById(R.id.button1);
    	//set listener to trl_show
    	 bn.setOnClickListener(new OnClickListener()
    	{

			@Override
			public void onClick(View source) {
				ShowHomePage(LoginActivity.EID);
			}
				
			});
    		
    	}
    	
	public void ShowHomePage(String EID){
		
		RequestParams params = new RequestParams();
		params.put("EID", EID);
		
		EleaveAppClient.post("Users/leaveinfo", params, new AsyncHttpResponseHandler(){
        	
        	@Override
        	public void onSuccess(String response) {
        		Log.e("User Leave Info",response);
                //Toast.makeText(MainActivity.this, response, Toast.LENGTH_SHORT).show();

        		if (response == null)
                    return;
                  try {
                    JSONObject jsonObject = new JSONObject(response);
                    
                	//String jsonStr = "{\"state\":\"200\",\"EnglishName\":\"andyxiao\",\"EmploymentDate\":\"2007-07-07\",\"JobStartingYear\":\"2007\",\"ThisStat\":\"5\",\"ThisAddi\":\"20\",\"LastLeft\":\"10\",\"ThisTakeStat\":\"2\",\"ThisTakeAddi\":\"4\",\"LastTake\":\"6\"}";  
                	
                	//JSONObject jsonObject = new JSONObject(jsonStr);
                			
                	int state = 200;
                    if(jsonObject.has("state")) state = jsonObject.getInt("state");
                    Log.e("debug", state+"");
                   

                    switch(state){
                    case 104:
	                    String error = "";
	                    if(jsonObject.has("msg")) error = jsonObject.getString("msg");
	                    Toast.makeText(HomeActivity.this, error,
	                        Toast.LENGTH_SHORT).show();
	                    break;
                    case 200:
                    	if(jsonObject.has("EmployeeId")) EmployeeNum = jsonObject.getInt("EmployeeId");                  	     
                 	        
                    	Log.e("EmployeeNum", EmployeeNum+"");
                    	
                    	String EnglishName = null;
                    	
                    	if(jsonObject.has("EnglisthName")) EnglishName = jsonObject.getString("EnglisthName");                  	
                    	TextView tv_en = (TextView)findViewById(R.id.tv_eid);
                    	tv_en.setText(EnglishName);
             
                                 
                    	String EmploymentDate = null;
                    	if(jsonObject.has("EmploymentDate")) EmploymentDate = jsonObject.getString("EmploymentDate");                    	
                    	TextView tv_employment_date = (TextView)findViewById(R.id.tv_employment_date_value);
                    	tv_employment_date.setText(EmploymentDate);
                    	                          	
                    	String JobStartingYear = null;
                    	if(jsonObject.has("JobStartingYear")) JobStartingYear = jsonObject.getString("JobStartingYear");                    	
                    	TextView tv_job_starting_year = (TextView)findViewById(R.id.tv_job_starting_year_value);
                    	tv_job_starting_year.setText(JobStartingYear);
                    	
                    	int ThisStat = 0;
                    	if(jsonObject.has("ThisStat")) ThisStat = jsonObject.getInt("ThisStat");
                    	TextView tv_this_stat = (TextView)findViewById(R.id.tv_entitled_slty);
                    	tv_this_stat.setText(ThisStat+"");
                    	
                    	int ThisAddi = 0;
                    	if(jsonObject.has("ThisAddi")) ThisAddi = jsonObject.getInt("ThisAddi");
                    	TextView tv_this_addi = (TextView)findViewById(R.id.tv_entitled_alty);
                    	tv_this_addi.setText(ThisAddi+"");
                    	
                    	int LastLeft = 0;
                    	if(jsonObject.has("LastLeft")) LastLeft = jsonObject.getInt("LastLeft");
                    	TextView tv_last_left = (TextView)findViewById(R.id.tv_entitled_lily);
                    	tv_last_left.setText(LastLeft+"");                   	
                    	
                    	int ThisTakeStat = 0;
                    	if(jsonObject.has("ThisTakeStat")) ThisTakeStat = jsonObject.getInt("ThisTakeStat");
                    	TextView tv_this_take_stat = (TextView)findViewById(R.id.tv_taken_slty);
                    	tv_this_take_stat.setText(ThisTakeStat+"");
                    	
                    	int ThisTakeAddi = 0;
                    	if(jsonObject.has("ThisTakeAddi")) ThisTakeAddi = jsonObject.getInt("ThisTakeAddi");
                    	TextView tv_this_take_addi = (TextView)findViewById(R.id.tv_taken_alty);
                    	tv_this_take_addi.setText(ThisTakeAddi+"");
                    	
                    	int LastTake = 0;
                    	if(jsonObject.has("LastTake")) LastTake = jsonObject.getInt("LastTake");
                    	TextView tv_last_take = (TextView)findViewById(R.id.tv_taken_lily);
                    	tv_last_take.setText(LastTake+"");    	
                    	
                    	int TotalTake = ThisTakeStat+ThisTakeAddi+LastTake;
                    	TextView tv_total_take = (TextView)findViewById(R.id.tv_total_taken);
                    	tv_total_take.setText(TotalTake+"");    	
                    	
                    	
                    	int StatRemained = ThisStat-ThisTakeStat;                    	
                    	TextView tv_stat_remained = (TextView)findViewById(R.id.tv_remained_slty);
                    	tv_stat_remained.setText(StatRemained+"");
                    	
                    	int AddiRemained = ThisAddi-ThisTakeAddi;                    
                    	TextView tv_addi_remained = (TextView)findViewById(R.id.tv_remained_alty);
                    	tv_addi_remained.setText(AddiRemained+"");
                    	
                    	int LastRemained = LastLeft-LastTake;                    
                    	TextView tv_last_remained = (TextView)findViewById(R.id.tv_remained_lily);
                    	tv_last_remained.setText(LastRemained+"");    	
                    	                    	                           
                    	int TotalRemained = StatRemained+AddiRemained+LastRemained;                    
                    	TextView tv_total_remained = (TextView)findViewById(R.id.tv_total_remained);
                    	tv_total_remained.setText(TotalRemained+"");
                    	
                    	
//                        EmploymentDate: String, 
//                        JobStartingYear: String, 
//                        ThisStat: int, //Statutory leave this year
//                        ThisAddi: int, //Additional leave this year
//                        LastLeft: int,//leave brought from last year
//                        ThisTakeStat: int,  // Statutory taken this year
//                        ThisTakeAddi: int, // Additional taken this year
//                        LastTake: int //leave taken from last year

                    	
                    	
                    	
                                        	
                      }
                  } catch (JSONException e) {
                      e.printStackTrace();
                  }
                      
            };
        	
        	public void onFailure(Throwable arg0) { 
        		
        	};
        	
        	public void onFinish() { 
        		
            };
            
            private void refresh() {  

            	onCreate(null);  
            };

        
        });
	}
    	       


}

