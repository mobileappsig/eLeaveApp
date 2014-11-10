package com.cgc.mobileappsig.eleave;

import android.app.Activity;
import android.os.Bundle;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.app.TabActivity;
import android.widget.RadioGroup;
import android.widget.Toast;
import android.content.Intent;
import android.widget.RadioButton;
import android.widget.TabHost;
import android.widget.RadioGroup.OnCheckedChangeListener;
import android.widget.Button;
import android.widget.TabHost.TabContentFactory;
import android.widget.TabWidget;
import android.view.View.OnClickListener;
import android.view.View;
import android.app.AlertDialog;
import android.content.DialogInterface;

import com.readystatesoftware.viewbadger.BadgeView;

import com.cgc.mobileappsig.eleave.common.ExitApplication;

@SuppressWarnings("deprecation")
public class MainTabActivity extends TabActivity implements OnCheckedChangeListener {
	private RadioGroup mainTab;
	private TabHost tabhost;
	private Intent iHome;
	private Intent iApply;
	private Intent iReport;
	private Intent iTodo;
	private Button iexit;
	private RadioButton btn_todo;
	private BadgeView badge;
	private Integer todo_num;
	
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_basicinfo); 
        
        mainTab=(RadioGroup)findViewById(R.id.main_tab);
        mainTab.setOnCheckedChangeListener(this);
        tabhost = getTabHost();
        
        iHome = new Intent(this, HomeActivity.class);
        tabhost.addTab(tabhost.newTabSpec("iHome")
        		.setIndicator(getResources().getString(R.string.main_home), getResources().getDrawable(R.drawable.icon_1_n))
        		.setContent(iHome));
        
        iApply = new Intent(this, ApplyActivity.class);
		tabhost.addTab(tabhost.newTabSpec("iApply")
	        	.setIndicator(getResources().getString(R.string.main_apply), getResources().getDrawable(R.drawable.icon_2_n))
	        	.setContent(iApply));
		
		iReport = new Intent(this, WorkItemActivity.class);
		tabhost.addTab(tabhost.newTabSpec("iReport")
		        	.setIndicator(getResources().getString(R.string.main_report), getResources().getDrawable(R.drawable.icon_3_n))
		        	.setContent(iReport));
		
		iTodo = new Intent(this, HomeActivity.class);
		tabhost.addTab(tabhost.newTabSpec("iTodo")
		        	.setIndicator(getResources().getString(R.string.main_todo), getResources().getDrawable(R.drawable.icon_4_n))
		        	.setContent(iTodo));
		
		iexit = (Button)findViewById(R.id.radio_exit);
		iexit.setOnClickListener(exitClick);
		ExitApplication.getInstance().addActivity(this);
		
		btn_todo = (RadioButton)findViewById(R.id.radio_todo);
		btn_todo.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mainTab.check(R.id.radio_todo);
				badge.hide();
			}
			
		}
		);
		
		badge = new BadgeView(this, btn_todo);
		
		// TO-DO: get the number of To-do items
		todo_num = 3;
		if (todo_num > 0){
			badge.setText(todo_num.toString());
			badge.show();
		}
		
	}
	
	OnClickListener exitClick=new OnClickListener() {
		@Override
		public void onClick(View v) {
			new AlertDialog.Builder(MainTabActivity.this)
			 .setTitle("Info") 
			 .setMessage("Are you sure to exit?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
		        public void onClick(DialogInterface dialog, int whichButton) { 
		        	dialog.dismiss();
		        	ExitApplication.getInstance().exit();
		    }})
			.setNegativeButton("No", null)
			.show();

			//ExitApplication.getInstance().exit();
		}
	};
	
	@Override
	public boolean dispatchKeyEvent(KeyEvent event) {
    	if (event.getAction() == KeyEvent.ACTION_DOWN ) {  
    		
    		new AlertDialog.Builder(MainTabActivity.this)
			 .setTitle("Info") 
			 .setMessage("Are you sure to exit?")
			.setPositiveButton("Yes", new DialogInterface.OnClickListener() { 
		        public void onClick(DialogInterface dialog, int whichButton) { 
		        	dialog.dismiss();
		        	ExitApplication.getInstance().exit();
		    }})
			.setNegativeButton("No", null)
			.show();
    	}
    	return false;
    }

	@Override
	public void onCheckedChanged(RadioGroup group, int checkedId) {
		switch(checkedId){
		case R.id.radio_home:
			this.tabhost.setCurrentTabByTag("iHome");
			break;
		case R.id.radio_apply:
			this.tabhost.setCurrentTabByTag("iApply");
			break;
		case R.id.radio_report:
			this.tabhost.setCurrentTabByTag("iReport");
			break;
		case R.id.radio_todo:
			this.tabhost.setCurrentTabByTag("itodo");
			break;
		}
	}
	
    
}
