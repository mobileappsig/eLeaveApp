package com.cgc.mobileappsig.eleave;



import android.os.Bundle;
import android.os.Handler;
import android.app.Activity;
import android.content.Intent;
import android.view.Window;

public class Appstart extends Activity{

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);	
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.appstart);
		
	new Handler().postDelayed(new Runnable(){
		@Override
		public void run(){
			Intent intent = new Intent (Appstart.this,WorkItemActivity.class);			
			//Intent intent = new Intent (Appstart.this,HomeActivity.class);
			//Intent intent = new Intent (Appstart.this,LoginActivity.class);
			startActivity(intent);			
			Appstart.this.finish();
		}
	}, 2000);
   }
}