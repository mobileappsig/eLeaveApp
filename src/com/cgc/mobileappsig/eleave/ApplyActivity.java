package com.cgc.mobileappsig.eleave;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Hashtable;
import java.util.Locale;
import java.text.DateFormat;
import java.text.SimpleDateFormat;









import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import com.cgc.mobileappsig.eleave.common.EleaveAppClient;
import com.cgc.mobileappsig.eleave.common.ExitApplication;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
import android.view.View.OnClickListener;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.LinearLayout.LayoutParams;
import android.widget.*;
import android.content.Context;
import android.content.Intent;

public class ApplyActivity extends Activity {
	//@Override  
 //  protected void onCreate(Bundle savedInstanceState) {  
//        // TODO Auto-generated method stub  
//        super.onCreate(savedInstanceState);
//
//        TextView tv = new TextView(this); 
//        tv.setText("This is Apply Activity!"); 
//        tv.setGravity(Gravity.CENTER); 
//        setContentView(tv); 
//        ExitApplication.getInstance().addActivity(this);
//	}
		

		// 生成日历，外层容器
		private LinearLayout layContent = null;
		private ArrayList<DateWidgetDayCell> days = new ArrayList<DateWidgetDayCell>();

		// 日期变量
		public static Calendar calStartDate = Calendar.getInstance();
		private Calendar calToday = Calendar.getInstance();
		private Calendar calCalendar = Calendar.getInstance();
		private Calendar calSelected = Calendar.getInstance();

		// 当前操作日期
		private int iMonthViewCurrentMonth = 0;
		private int iMonthViewCurrentYear = 0;
		private int iFirstDayOfWeek = Calendar.MONDAY;

		private int Calendar_Width = 0;
		private int Cell_Width = 0;
		
		protected ArrayAdapter<CharSequence> mAdapter;
		protected int mPos;
		protected String mSelection;


		// 页面控件
		TextView Top_Date = null;
		Button btn_pre_month = null;
		Button btn_next_month = null;
		TextView arrange_text = null;
		LinearLayout mainLayout = null;
		//LinearLayout arrange_layout = null;
		//RelativeLayout arrange_layout = null;
		LinearLayout layoutButton = null;

		// 数据源
		ArrayList<String> Calendar_Source = null;
		Hashtable<Integer, Integer> calendar_Hashtable = new Hashtable<Integer, Integer>();
		Boolean[] flag = null;
		Calendar startDate = null;
		Calendar endDate = null;
		int dayvalue = -1;

		public static int Calendar_WeekBgColor = 0;
		public static int Calendar_DayBgColor = 0;
		public static int isHoliday_BgColor = 0;
		public static int unPresentMonth_FontColor = 0;
		public static int isPresentMonth_FontColor = 0;
		public static int isToday_BgColor = 0;
		public static int special_Reminder = 0;
		public static int common_Reminder = 0;
		public static int Calendar_WeekFontColor = 0;
		

		String UserName = "";
		
		public static int dip2px(Context context, float dpValue) { 
	         final float scale = context.getResources().getDisplayMetrics().density; 
	         return (int) (dpValue * scale + 0.5f); 
	     } 

		@Override
		public void onCreate(Bundle savedInstanceState) {
			super.onCreate(savedInstanceState);

			// 获得屏幕宽和高，并計算出屏幕寬度分七等份的大小
			WindowManager windowManager = getWindowManager();
			Display display = windowManager.getDefaultDisplay();
			int screenWidth = display.getWidth();
			Calendar_Width = screenWidth;
			Cell_Width = Calendar_Width / 7 + 1;

			// 制定布局文件，并设置属性
			mainLayout = (LinearLayout) getLayoutInflater().inflate(
					R.layout.activity_apply, null);
			// mainLayout.setPadding(2, 0, 2, 0);
			setContentView(mainLayout);

			// 声明控件，并绑定事件
			Top_Date = (TextView) findViewById(R.id.Top_Date);
			btn_pre_month = (Button) findViewById(R.id.btn_pre_month);
			btn_next_month = (Button) findViewById(R.id.btn_next_month);
			btn_pre_month.setOnClickListener(new Pre_MonthOnClickListener());
			btn_next_month.setOnClickListener(new Next_MonthOnClickListener());

			// 计算本月日历中的第一天(一般是上月的某天)，并更新日历
			calStartDate = getCalendarStartDate();
			mainLayout.addView(generateCalendarMain());
			DateWidgetDayCell daySelected = updateCalendar();

			if (daySelected != null)
				daySelected.requestFocus();
			

			 //上边距（dp值） 
	         //int minHeight = dip2px(this, 54); 
	         //上padding（dp值） 
	         int topPadding = dip2px(this, 1); 
	         //左padding（dp值） 
	         int leftPadding = dip2px(this, 1); 
	         //按钮布局 
			
			LinearLayout.LayoutParams layoutSpinnerParms = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
			layoutSpinnerParms.gravity = Gravity.CENTER_VERTICAL;
			LinearLayout layoutSpinner = new LinearLayout(this);
			layoutSpinner.setLayoutParams(layoutSpinnerParms);
			layoutSpinner.setOrientation(LinearLayout.VERTICAL);
			layoutSpinner.setPadding(leftPadding, topPadding, leftPadding, topPadding); 
			
			LinearLayout.LayoutParams layoutParamsSpinnerLeaveType = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT); 
			layoutParamsSpinnerLeaveType.gravity = Gravity.TOP; 
			layoutParamsSpinnerLeaveType.topMargin = dip2px(this, 1); 
			layoutParamsSpinnerLeaveType.bottomMargin = dip2px(this, 1); 
			layoutParamsSpinnerLeaveType.weight = 1; 
			
//	         //Button确定 
//	         Button spinnerLeaveType = new Button(this); 
//	         spinnerLeaveType.setLayoutParams(layoutParamsSpinnerLeaveType); 
//	         spinnerLeaveType.setMaxLines(2); 
//	         spinnerLeaveType.setTextSize(18); 
//	         spinnerLeaveType.setText("leave day type"); 
//	         layoutSpinner.addView(spinnerLeaveType); 
	         
	         Spinner spinnerLeaveType = new Spinner(this);
	         spinnerLeaveType.setLayoutParams(layoutParamsSpinnerLeaveType);
	         this.mAdapter = ArrayAdapter.createFromResource(this, R.array.leaveDayType,
	                 R.layout.spinner_dropdown);

	         /*
	          * Attach the mLocalAdapter to the spinner.
	          */

	         spinnerLeaveType.setAdapter(this.mAdapter);
	         layoutSpinner.addView(spinnerLeaveType);
	          
	         OnItemSelectedListener spinnerListener = new myOnItemSelectedListener(this,this.mAdapter);

	         /*
	          * Attach the listener to the Spinner.
	          */

	         spinnerLeaveType.setOnItemSelectedListener(spinnerListener);
	         
	         LinearLayout.LayoutParams layoutParamsLeaveTime = new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT); 
	         layoutParamsLeaveTime.gravity = Gravity.BOTTOM; 
	         layoutParamsLeaveTime.topMargin = dip2px(this, 0); 
	         layoutParamsLeaveTime.bottomMargin = dip2px(this, 1); 
	         layoutParamsLeaveTime.weight = 1; 
	         //Button取消 
//	         Button spinnerLeaveTime = new Button(this); 
//	         spinnerLeaveTime.setLayoutParams(layoutParamsLeaveTime); 
//	         spinnerLeaveTime.setMaxLines(2); 
//	         spinnerLeaveTime.setTextSize(18); 
//	         spinnerLeaveTime.setText("leave time"); 
	         
	         Spinner spinnerLeaveTime = new Spinner(this);
	         spinnerLeaveTime.setLayoutParams(layoutParamsLeaveTime);
	         this.mAdapter = ArrayAdapter.createFromResource(this, R.array.leaveTime,
	                 R.layout.spinner_dropdown);

	         /*
	          * Attach the mLocalAdapter to the spinner.
	          */

	         spinnerLeaveTime.setAdapter(this.mAdapter);
	                  
	         layoutSpinner.addView(spinnerLeaveTime); 
	         
	         OnItemSelectedListener spinnerListener2 = new myOnItemSelectedListener(this,this.mAdapter);

	         /*
	          * Attach the listener to the Spinner.
	          */

	         spinnerLeaveTime.setOnItemSelectedListener(spinnerListener2);
	         
	         mainLayout.addView(layoutSpinner, layoutParamsLeaveTime);
			
			
			//arrange_layout = new RelativeLayout(this);
			int widthMain = dip2px(this, 240);
			layoutButton = new LinearLayout(this);
			LinearLayout.LayoutParams layoutButtonParms = new LinearLayout.LayoutParams(widthMain, LayoutParams.WRAP_CONTENT);
			layoutButtonParms.gravity = Gravity.CENTER_HORIZONTAL;
			
			
			layoutButton.setLayoutParams(layoutButtonParms);
			layoutButton.setOrientation(LinearLayout.HORIZONTAL);
			layoutButton.setPadding(leftPadding, topPadding, leftPadding, topPadding); 
			layoutButton.setId(100000001); 
			
			
			LinearLayout.LayoutParams layoutParamsButtonOK = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
	         layoutParamsButtonOK.gravity = Gravity.LEFT; 
	         layoutParamsButtonOK.leftMargin = dip2px(this, 10); 
	         layoutParamsButtonOK.rightMargin = dip2px(this, 5); 
	         layoutParamsButtonOK.weight = 1; 
	         //Button确定 
	         Button buttonSubmit = new Button(this); 
	         buttonSubmit.setLayoutParams(layoutParamsButtonOK); 
	         buttonSubmit.setMaxLines(1); 
	         buttonSubmit.setTextSize(18); 
	         buttonSubmit.setText("submit"); 
	         layoutButton.addView(buttonSubmit); 
	          
	         //buttonCancel布局参数 
	         LinearLayout.LayoutParams layoutParamsButtonCancel = new LinearLayout.LayoutParams(LayoutParams.WRAP_CONTENT,LayoutParams.WRAP_CONTENT); 
	         layoutParamsButtonCancel.gravity = Gravity.RIGHT; 
	         layoutParamsButtonCancel.leftMargin = dip2px(this, 5); 
	         layoutParamsButtonCancel.rightMargin = dip2px(this, 10); 
	         layoutParamsButtonCancel.weight = 1; 
	         //Button取消 
	         Button buttonCancel = new Button(this); 
	         buttonCancel.setLayoutParams(layoutParamsButtonCancel); 
	         buttonCancel.setMaxLines(1); 
	         buttonCancel.setTextSize(18); 
	         buttonCancel.setText("cancel"); 
	          
	         layoutButton.addView(buttonCancel); 
	          
	        //layoutRoot.addView(layoutButton, layoutParamsEditInfo); 
			
			
			//arrange_layout = createLayout(LinearLayout.HORIZONTAL);
//			arrange_layout.setGravity(Gravity.CENTER_HORIZONTAL);
//			
//			RelativeLayout.LayoutParams parms = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
//            parms.setMargins(10, 0, 0, 0);
//            arrange_layout.setLayoutParams(parms);
//			
//			//arrange_layout.setPadding(10, 5, 0, 0);
//			Button submit = new Button(this);
//			Button cancel = new Button(this);
//			submit.setText("Submit");
//			submit.setTextColor(Color.BLACK);
//			cancel.setText("Cancel");
//			cancel.setTextColor(Color.BLACK);
//			
//			//final Button fcancel = cancel;
//			//int cancelWidth = 0;
//			
//			RelativeLayout.LayoutParams lp1= new RelativeLayout.LayoutParams(
//					ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//			lp1.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//			//int center = screenWidth/2;
//			
//			
//			//cancel.setId(1001);
//			
//			//lp1.setMargins(5,0,0,0);
//			//cancel.setLayoutParams(lp1);
//			
//			
//			
////			cancel_static = cancel;
////			
////			ViewTreeObserver vto = cancel.getViewTreeObserver();
////			vto.addOnPreDrawListener(new ViewTreeObserver.OnPreDrawListener(){
////				public boolean onPreDraw()
////				{
////					cancelWidth = cancel_static.getMeasuredWidth();
////					return true;
////				}
////			
////			});
////			
//			
//			
//			RelativeLayout.LayoutParams lp2= new RelativeLayout.LayoutParams(
//					ViewGroup.LayoutParams.WRAP_CONTENT,ViewGroup.LayoutParams.WRAP_CONTENT);
//			
//			lp2.addRule(RelativeLayout.ALIGN_PARENT_BOTTOM);
//			//lp2.setMargins(center+2, 0, 0, 0);
//		    lp2.addRule(RelativeLayout.LEFT_OF, cancel.getId());
//		    //lp2.setMargins(screenWidth - cancel.getWidth()-10 - submit.getWidth(), 0,0,0);
//		    //submit.requestLayout();
//		    
////		    int leftMargine = 0;
////		    int width = 0;
////		    DisplayMetrics dm = new DisplayMetrics();getWindowManager().getDefaultDisplay().getMetrics(dm);width = dm.widthPixels;
////		    leftMargine = width - cancel.getWidth() - submit.getWidth() - 5;
//		   // lp2.setMargins(0, 0, 0, 5);
//           // lp2.setMargins(100,0,0,0);
//		   // lp2.setMargins(50, 50, 50, 50);
////			Log.e("screenWidth", (new Integer(screenWidth)).toString());
////			
////			Log.e("cancelWidth", (new Integer(cancelWidth)).toString());
////			//Log.e("submitWitdh", (new Integer(submitWitdh)).toString());
////			lp2.setMargins(0,0,cancelWidth + 10, 0);
//			
//			//lp2.addRule(RelativeLayout.RIGHT_OF, cancel.getId());
//			//lp2.addRule(RelativeLayout.ALIGN_TOP, cancel.getId());
//		
//			arrange_layout.addView(cancel,lp1);		    
//			arrange_layout.addView(submit,lp2);
//			
			
			
		   mainLayout.setBackgroundColor(Color.WHITE);
			
			
						
		   buttonSubmit.setOnClickListener(new OnClickListener(){
			public void onClick(View v){
				//do some process
				
				String EmployeeNumber = LoginActivity.EmployeeNum+"";

										         		      
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss",Locale.ENGLISH);       
				Date curDate = new Date(System.currentTimeMillis()); //get current time        
				String issuedDate = formatter.format(curDate);
				//DateFormat curDate = DateFormat.getDateTimeInstance(DateFormat.MEDIUM,DateFormat.MEDIUM);
				//DateFormat curDate = DateFormat.getDateTimeInstance();
				//String issuedDate = formatter.format(new Date());
				Log.e("current time", issuedDate);
				
				
				RequestParams params = new RequestParams();
	            
				
				SelectedDayCells startSelectedDayCell, stopSelectedDayCell;
					
		        
				int stopIndex = DateWidgetDayCell.selectedDayCells.size() - 1;
		        
				JSONObject jsObjs = new JSONObject();
				JSONObject jsObjt = new JSONObject();
				
				JSONArray jsArr = new JSONArray();
				
				if (stopIndex >= 0){
					
					startSelectedDayCell =  DateWidgetDayCell.selectedDayCells.get(0);
			        stopSelectedDayCell = DateWidgetDayCell.selectedDayCells.get(stopIndex);
			           
			        String startDay = Integer.toString(startSelectedDayCell.year) + "-" + Integer.toString(startSelectedDayCell.month + 1) + "-" + Integer.toString(startSelectedDayCell.day);
			        String stopDay =  Integer.toString(stopSelectedDayCell.year) + "-" + Integer.toString(stopSelectedDayCell.month + 1) + "-" + Integer.toString(stopSelectedDayCell.day);	
				
					
					//DateWidgetDayCell.printSelectedDays();
				
					try {		
						
						jsObjs.put("StartDay", startDay).put("StopDay", stopDay).put("HalfDayOrNot", "NULL").put("AmOrPm", "NULL");
						jsArr.put(jsObjs);
						jsObjt.put("EmployeeId", EmployeeNumber).put("LeavaTypeId","1").put("IssuedDate", issuedDate).put("LeaveDetail", jsArr);
						
					} catch (JSONException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				
				}
				else{
					Toast.makeText(ApplyActivity.this, "Leave Date Required", Toast.LENGTH_SHORT).show();
				}
				
				
				
				//JSONArray jsLeaveDetail = new JSONArray();
				//String newRequestValue = "{\"EmployeeId\":EmployeeNumber,\"LeavaTypeId\":\"1\",\"IssuedDate\":issuedDate,\"LeaveDetail\":[{\"StartDay\":startDay,\"StopDay\":stopDay,\"HalfDayOrNot\":\"NULL\",\"AmOrPm\":\"NULL\"}]}";
				
				//Log.e("new Request Value", newRequestValue);
				
				//newRequest = JSON {EmployeeId, CaseId, LeavaTypeId, IssuedDate,  LeaveDetail: [{StartDay, StopDay, HalfDayOrNot, AmOrPm}]}
				//params.put("newRequest", "{\"EmployeeId\":\"10000001\",\"LeavaTypeId\":\"1\",\"IssuedDate\":\"2014-10-16 16:55:00\",\"LeaveDetail\":[{\"StartDay\":\"2014-10-20\",\"StopDay\":\"2014-10-22\",\"HalfDayOrNot\":\"NULL\",\"AmOrPm\":\"NULL\"}]}");
	            params.put("newRequest", jsObjt.toString());
				params.put("type", "new");
	            EleaveAppClient.setTimeout(10000);
	    		Log.e("debug","Submit Request!");
	    		
	    		EleaveAppClient.post("Leave/submitleave", params, new AsyncHttpResponseHandler(){
	    			
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
	                            Toast.makeText(ApplyActivity.this, error,
	                                Toast.LENGTH_SHORT).show();
	                            break;
	                        case 200:
	                            int caseId = 0;
	                            if(jsonObject.has("CaseId")) caseId = jsonObject.getInt("CaseId");
	                        	String prompt = "Submitted Successfully!, CaseId is " + caseId + "";
	                        	Toast.makeText(ApplyActivity.this, prompt,
	                                Toast.LENGTH_SHORT).show();
	                            break;
	                          }
	                      } catch (JSONException e) {
	                          e.printStackTrace();
	                      }
	                          
	                };
	            	
	            	public void onFailure(Throwable arg0) { 
	            		Toast.makeText(ApplyActivity.this, "Submit Failure",Toast.LENGTH_LONG).show();
	            		Log.e("debug",arg0.getMessage());
	            	};
	            	
	            	public void onFinish() { 
	            		
	                };	    			
	    			
	    		});
	    		
				}
			});
			
			
		   buttonCancel.setOnClickListener(new OnClickListener(){
				public void onClick(View v){
					//do some process
					Log.i("Anne", "Cancel button is pressed.");
					DateWidgetDayCell.clearSelectedDays();
					updateCalendar();
				}
				});
		
						

			startDate = GetStartDate();
			calToday = GetTodayDate();

			endDate = GetEndDate(startDate);
			//view.addView(arrange_layout, Param1);
//			mainLayout.addView(view);
			mainLayout.addView(layoutButton, layoutButtonParms);

			// 新建线程
			new Thread() {
				@Override
				public void run() {
					int day = GetNumFromDate(calToday, startDate);
					
					if (calendar_Hashtable != null
							&& calendar_Hashtable.containsKey(day)) {
						dayvalue = calendar_Hashtable.get(day);
					}
				}
				
			}.start();

			Calendar_WeekBgColor = this.getResources().getColor(
					R.color.Calendar_WeekBgColor);
			Calendar_DayBgColor = this.getResources().getColor(
					R.color.Calendar_DayBgColor);
			isHoliday_BgColor = this.getResources().getColor(
					R.color.isHoliday_BgColor);
			unPresentMonth_FontColor = this.getResources().getColor(
					R.color.unPresentMonth_FontColor);
			isPresentMonth_FontColor = this.getResources().getColor(
					R.color.isPresentMonth_FontColor);
			isToday_BgColor = this.getResources().getColor(R.color.isToday_BgColor);
			special_Reminder = this.getResources()
					.getColor(R.color.specialReminder);
			common_Reminder = this.getResources().getColor(R.color.commonReminder);
			Calendar_WeekFontColor = this.getResources().getColor(
					R.color.Calendar_WeekFontColor);
		}
		
		public class myOnItemSelectedListener implements OnItemSelectedListener {

	        /*
	         * provide local instances of the mLocalAdapter and the mLocalContext
	         */

	        ArrayAdapter<CharSequence> mLocalAdapter;
	        Activity mLocalContext;

	        /**
	         *  Constructor
	         *  @param c - The activity that displays the Spinner.
	         *  @param ad - The Adapter view that
	         *    controls the Spinner.
	         *  Instantiate a new listener object.
	         */
	        public myOnItemSelectedListener(Activity c, ArrayAdapter<CharSequence> ad) {

	          this.mLocalContext = c;
	          this.mLocalAdapter = ad;

	        }

	        /**
	         * When the user selects an item in the spinner, this method is invoked by the callback
	         * chain. Android calls the item selected listener for the spinner, which invokes the
	         * onItemSelected method.
	         *
	         * @see android.widget.AdapterView.OnItemSelectedListener#onItemSelected(
	         *  android.widget.AdapterView, android.view.View, int, long)
	         * @param parent - the AdapterView for this listener
	         * @param v - the View for this listener
	         * @param pos - the 0-based position of the selection in the mLocalAdapter
	         * @param row - the 0-based row number of the selection in the View
	         */
	        public void onItemSelected(AdapterView<?> parent, View v, int pos, long row) {

	            ApplyActivity.this.mPos = pos;
	            ApplyActivity.this.mSelection = parent.getItemAtPosition(pos).toString();
	            Log.e("Leave Type Info", ApplyActivity.this.mSelection);
	          
	        }

	        /**
	         * The definition of OnItemSelectedListener requires an override
	         * of onNothingSelected(), even though this implementation does not use it.
	         * @param parent - The View for this Listener
	         */
	        public void onNothingSelected(AdapterView<?> parent) {

	            // do nothing

	        }
	    }


		protected String GetDateShortString(Calendar date) {
			String returnString = date.get(Calendar.YEAR) + "/";
			returnString += date.get(Calendar.MONTH) + 1 + "/";
			returnString += date.get(Calendar.DAY_OF_MONTH);
			
			return returnString;
		}

		// 得到当天在日历中的序号
		private int GetNumFromDate(Calendar now, Calendar returnDate) {
			Calendar cNow = (Calendar) now.clone();
			Calendar cReturnDate = (Calendar) returnDate.clone();
			setTimeToMidnight(cNow);
			setTimeToMidnight(cReturnDate);
			
			long todayMs = cNow.getTimeInMillis();
			long returnMs = cReturnDate.getTimeInMillis();
			long intervalMs = todayMs - returnMs;
			int index = millisecondsToDays(intervalMs);
			
			return index;
		}

		private int millisecondsToDays(long intervalMs) {
			return Math.round((intervalMs / (1000 * 86400)));
		}

		private void setTimeToMidnight(Calendar calendar) {
			calendar.set(Calendar.HOUR_OF_DAY, 0);
			calendar.set(Calendar.MINUTE, 0);
			calendar.set(Calendar.SECOND, 0);
			calendar.set(Calendar.MILLISECOND, 0);
		}

		// 生成布局
		private LinearLayout createLayout(int iOrientation) {
			LinearLayout lay = new LinearLayout(this);
			lay.setLayoutParams(new LayoutParams(
					android.view.ViewGroup.LayoutParams.MATCH_PARENT,
					android.view.ViewGroup.LayoutParams.WRAP_CONTENT));
			lay.setOrientation(iOrientation);
			
			return lay;
		}

		// 生成日历头部
		private View generateCalendarHeader() {
			LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
			// layRow.setBackgroundColor(Color.argb(255, 207, 207, 205));
			
			for (int iDay = 0; iDay < 7; iDay++) {
				DateWidgetDayHeader day = new DateWidgetDayHeader(this, Cell_Width,
						35);
				
				final int iWeekDay = DayStyle.getWeekDay(iDay, iFirstDayOfWeek);
				day.setData(iWeekDay);
				layRow.addView(day);
			}
			
			return layRow;
		}

		// 生成日历主体
		private View generateCalendarMain() {
			layContent = createLayout(LinearLayout.VERTICAL);
			// layContent.setPadding(1, 0, 1, 0);
			layContent.setBackgroundColor(Color.argb(255, 105, 105, 103));
			layContent.addView(generateCalendarHeader());
			days.clear();
			
			for (int iRow = 0; iRow < 6; iRow++) {
				layContent.addView(generateCalendarRow());
			}
			
			return layContent;
		}

		// 生成日历中的一行，仅画矩形
		private View generateCalendarRow() {
			LinearLayout layRow = createLayout(LinearLayout.HORIZONTAL);
			
			for (int iDay = 0; iDay < 7; iDay++) {
				DateWidgetDayCell dayCell = new DateWidgetDayCell(this, Cell_Width,
						Cell_Width);
				dayCell.setItemClick(mOnDayCellClick);
				days.add(dayCell);
				layRow.addView(dayCell);
			}
			
			return layRow;
		}

		// 设置当天日期和被选中日期
		private Calendar getCalendarStartDate() {
			calToday.setTimeInMillis(System.currentTimeMillis());
			calToday.setFirstDayOfWeek(iFirstDayOfWeek);

			if (calSelected.getTimeInMillis() == 0) {
				calStartDate.setTimeInMillis(System.currentTimeMillis());
				calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
			} else {
				calStartDate.setTimeInMillis(calSelected.getTimeInMillis());
				calStartDate.setFirstDayOfWeek(iFirstDayOfWeek);
			}
			
			UpdateStartDateForMonth();
			return calStartDate;
		}

		// 由于本日历上的日期都是从周一开始的，此方法可推算出上月在本月日历中显示的天数
		private void UpdateStartDateForMonth() {
			iMonthViewCurrentMonth = calStartDate.get(Calendar.MONTH);
			iMonthViewCurrentYear = calStartDate.get(Calendar.YEAR);
			calStartDate.set(Calendar.DAY_OF_MONTH, 1);
			calStartDate.set(Calendar.HOUR_OF_DAY, 0);
			calStartDate.set(Calendar.MINUTE, 0);
			calStartDate.set(Calendar.SECOND, 0);
			// update days for week
			UpdateCurrentMonthDisplay();
			int iDay = 0;
			int iStartDay = iFirstDayOfWeek;
			
			if (iStartDay == Calendar.MONDAY) {
				iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
				if (iDay < 0)
					iDay = 6;
			}
			
			if (iStartDay == Calendar.SUNDAY) {
				iDay = calStartDate.get(Calendar.DAY_OF_WEEK) - Calendar.SUNDAY;
				if (iDay < 0)
					iDay = 6;
			}
			
			calStartDate.add(Calendar.DAY_OF_WEEK, -iDay);
		}

		// 更新日历
		private DateWidgetDayCell updateCalendar() {
			DateWidgetDayCell daySelected = null;
			boolean bSelected = false;
			//final boolean bIsSelection = (calSelected.getTimeInMillis() != 0);
			final int iSelectedYear = calSelected.get(Calendar.YEAR);
			final int iSelectedMonth = calSelected.get(Calendar.MONTH);
			final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
			calCalendar.setTimeInMillis(calStartDate.getTimeInMillis());
		
			
			for (int i = 0; i < days.size(); i++) {
				final int iYear = calCalendar.get(Calendar.YEAR);
				final int iMonth = calCalendar.get(Calendar.MONTH);
				final int iDay = calCalendar.get(Calendar.DAY_OF_MONTH);
				final int iDayOfWeek = calCalendar.get(Calendar.DAY_OF_WEEK);
				DateWidgetDayCell dayCell = days.get(i);

				// 判断是否当天
				boolean bToday = false;
				
				if (calToday.get(Calendar.YEAR) == iYear) {
					if (calToday.get(Calendar.MONTH) == iMonth) {
						if (calToday.get(Calendar.DAY_OF_MONTH) == iDay) {
							bToday = true;
						}
					}
				}

				// check holiday
				boolean bHoliday = false;
				if ((iDayOfWeek == Calendar.SATURDAY)
						|| (iDayOfWeek == Calendar.SUNDAY))
					bHoliday = true;
				if ((iMonth == Calendar.JANUARY) && (iDay == 1))
					bHoliday = true;

				// 是否被选中
				bSelected = false;
				
				//if (bIsSelection)
//					if ((iSelectedDay == iDay) && (iSelectedMonth == iMonth)
//							&& (iSelectedYear == iYear))
					{
					 bSelected = DateWidgetDayCell.isSelectedDay(iYear, iMonth, iDay);
			
					}
				
				dayCell.setSelected(bSelected);

				// 是否有记录
				boolean hasRecord = false;
				
				if (flag != null && flag[i] == true && calendar_Hashtable != null
						&& calendar_Hashtable.containsKey(i)) {
					// hasRecord = flag[i];
					hasRecord = Calendar_Source.get(calendar_Hashtable.get(i))
							.contains(UserName);
				}

				if (bSelected)
					daySelected = dayCell;

				dayCell.setData(iYear, iMonth, iDay, bToday, bHoliday,
						iMonthViewCurrentMonth, hasRecord);

				calCalendar.add(Calendar.DAY_OF_MONTH, 1);
			}
			
			layContent.invalidate();
			
			return daySelected;
		}

		// 更新日历标题上显示的年月
		private void UpdateCurrentMonthDisplay() {
			
			int CurMonth = calStartDate.get(Calendar.MONTH) + 1;
			String EngCurMonth = "";
			switch(CurMonth){
				case 1:
					EngCurMonth = "January";
					break;
				case 2:
					EngCurMonth = "February";
					break;
				case 3:
					EngCurMonth = "March";
					break;
				case 4:
					EngCurMonth = "April";
					break;
				case 5:
					EngCurMonth = "May";
					break;
				case 6:
					EngCurMonth = "June";
					break;
				case 7:
					EngCurMonth = "July";
					break;
				case 8:
					EngCurMonth = "August";
					break;
				case 9:
					EngCurMonth = "September";
					break;
				case 10:
					EngCurMonth = "October";
					break;
				case 11:
					EngCurMonth = "November";
					break;
				case 12:
					EngCurMonth = "December";
					break;
			}
				
				
			String date = EngCurMonth + "," + calStartDate.get(Calendar.YEAR);
			Top_Date.setMaxLines(1);
			Top_Date.setText(date);
		}

		// 点击上月按钮，触发事件
		class Pre_MonthOnClickListener implements OnClickListener {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//arrange_text.setText("");
				calSelected.setTimeInMillis(0);
				iMonthViewCurrentMonth--;
				
				if (iMonthViewCurrentMonth == -1) {
					iMonthViewCurrentMonth = 11;
					iMonthViewCurrentYear--;
				}
				
				calStartDate.set(Calendar.DAY_OF_MONTH, 1);
				calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
				calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
				calStartDate.set(Calendar.HOUR_OF_DAY, 0);
				calStartDate.set(Calendar.MINUTE, 0);
				calStartDate.set(Calendar.SECOND, 0);
				calStartDate.set(Calendar.MILLISECOND, 0);
				UpdateStartDateForMonth();

				startDate = (Calendar) calStartDate.clone();
				endDate = GetEndDate(startDate);

				// 新建线程
				new Thread() {
					@Override
					public void run() {

						int day = GetNumFromDate(calToday, startDate);
						
						if (calendar_Hashtable != null
								&& calendar_Hashtable.containsKey(day)) {
							dayvalue = calendar_Hashtable.get(day);
						}
					}
				}.start();

				//DateWidgetDayCell.printSelectedDays();
				updateCalendar();
			}

		}

		// 点击下月按钮，触发事件
		class Next_MonthOnClickListener implements OnClickListener {
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				//arrange_text.setText("");
				calSelected.setTimeInMillis(0);
				iMonthViewCurrentMonth++;
				
				if (iMonthViewCurrentMonth == 12) {
					iMonthViewCurrentMonth = 0;
					iMonthViewCurrentYear++;
				}
				
				calStartDate.set(Calendar.DAY_OF_MONTH, 1);
				calStartDate.set(Calendar.MONTH, iMonthViewCurrentMonth);
				calStartDate.set(Calendar.YEAR, iMonthViewCurrentYear);
				UpdateStartDateForMonth();

				startDate = (Calendar) calStartDate.clone();
				endDate = GetEndDate(startDate);

				// 新建线程
				new Thread() {
					@Override
					public void run() {
						int day = 5;
						
						if (calendar_Hashtable != null
								&& calendar_Hashtable.containsKey(day)) {
							dayvalue = calendar_Hashtable.get(day);
						}
					}
				}.start();

				//DateWidgetDayCell.printSelectedDays();
				updateCalendar();
			}
		}

		// 点击日历，触发事件
		private DateWidgetDayCell.OnItemClick mOnDayCellClick = new DateWidgetDayCell.OnItemClick() {
			public void OnClick(DateWidgetDayCell item) {
				calSelected.setTimeInMillis(item.getDate().getTimeInMillis());
				int day = GetNumFromDate(calSelected, startDate);
				
//				if (calendar_Hashtable != null
//						&& calendar_Hashtable.containsKey(day)) {
//					arrange_text.setText(Calendar_Source.get(calendar_Hashtable
//							.get(day)));
//				} else {
//					arrange_text.setText("暂无数据记录");
//				}
				
				//item.setSelected(true);
				DateWidgetDayCell.addSelectedDay(calSelected);
				//DateWidgetDayCell.printSelectedDays();
				updateCalendar();
			}
		};

		public Calendar GetTodayDate() {
			Calendar cal_Today = Calendar.getInstance();
			cal_Today.set(Calendar.HOUR_OF_DAY, 0);
			cal_Today.set(Calendar.MINUTE, 0);
			cal_Today.set(Calendar.SECOND, 0);
			cal_Today.setFirstDayOfWeek(Calendar.MONDAY);

			return cal_Today;
		}

		// 得到当前日历中的第一天
		public Calendar GetStartDate() {
			int iDay = 0;
			Calendar cal_Now = Calendar.getInstance();
			cal_Now.set(Calendar.DAY_OF_MONTH, 1);
			cal_Now.set(Calendar.HOUR_OF_DAY, 0);
			cal_Now.set(Calendar.MINUTE, 0);
			cal_Now.set(Calendar.SECOND, 0);
			cal_Now.setFirstDayOfWeek(Calendar.MONDAY);

			iDay = cal_Now.get(Calendar.DAY_OF_WEEK) - Calendar.MONDAY;
			
			if (iDay < 0) {
				iDay = 6;
			}
			
			cal_Now.add(Calendar.DAY_OF_WEEK, -iDay);
			
			return cal_Now;
		}

		public Calendar GetEndDate(Calendar startDate) {
			// Calendar end = GetStartDate(enddate);
			Calendar endDate = Calendar.getInstance();
			endDate = (Calendar) startDate.clone();
			endDate.add(Calendar.DAY_OF_MONTH, 41);
			return endDate;
		}


}
