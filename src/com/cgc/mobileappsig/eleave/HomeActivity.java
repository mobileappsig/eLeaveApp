package com.cgc.mobileappsig.eleave;

import com.cgc.mobileappsig.eleave.common.ExitApplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.Gravity; 
import android.widget.TextView;

public class HomeActivity extends Activity {
	@Override  
    protected void onCreate(Bundle savedInstanceState) {  
        // TODO Auto-generated method stub  
        super.onCreate(savedInstanceState);

        TextView tv = new TextView(this); 
        tv.setText("This is Home Activity!"); 
        tv.setGravity(Gravity.CENTER); 
        setContentView(tv); 
        ExitApplication.getInstance().addActivity(this);
	}
}
