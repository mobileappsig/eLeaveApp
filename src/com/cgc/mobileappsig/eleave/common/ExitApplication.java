package com.cgc.mobileappsig.eleave.common;

import java.util.List;
import java.util.LinkedList;
import android.app.Application;
import android.app.Activity;

public class ExitApplication extends Application {
	private List<Activity> activityList=new LinkedList<Activity>();
	private static ExitApplication instance;
	
	private ExitApplication(){
		
	}
	
	public static ExitApplication getInstance(){
		if(null == instance){
			instance = new ExitApplication();
		}
		return instance;
	}
	
	public void addActivity(Activity activity){
		activityList.add(activity);
	}
	
	public void exit(){
		for(Activity activity:activityList) {
			 activity.finish();
		}
		
		System.exit(0);
	}
}
