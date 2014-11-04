package com.cgc.mobileappsig.eleave;

import java.util.ArrayList;
import java.util.Calendar;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Paint.Style;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.widget.LinearLayout.LayoutParams;


public class DateWidgetDayCell extends View {
	// 字体大小
	private static final int fTextSize =35;
	
	// 基本元素
	private OnItemClick itemClick = null;
	private Paint pt = new Paint();
	private RectF rect = new RectF();
	private String sDate = "";

	// 当前日期
	private int iDateYear = 0;
	private int iDateMonth = 0;
	private int iDateDay = 0;

	// 布尔变量
	private boolean bSelected = false;
	private boolean bIsActiveMonth = false;
	private boolean bToday = false;
	private boolean bTouchedDown = false;
	private boolean bHoliday = false;
	private boolean hasRecord = false;
	private boolean isAm = false;
	private boolean isPm = false;

	public static int ANIM_ALPHA_DURATION = 100;
	
	// Anne's code
	// Add the code to support more than one days are selected.
	
	//public int selectedLeaveType = 1;
	//public boolean selectedHalfDay = false;
 	//public boolean selectedDayAm = false;
 	public boolean bAmOrPm = false;
 	public boolean bMorning = false;
 	public boolean bAfternoon = false;
	
	public static ArrayList<SelectedDayCells> selectedDayCells = new ArrayList<SelectedDayCells>();
	
	public void addSelectedDay(Calendar calSelected){
		final int iSelectedYear = calSelected.get(Calendar.YEAR);
		final int iSelectedMonth = calSelected.get(Calendar.MONTH);
		final int iSelectedDay = calSelected.get(Calendar.DAY_OF_MONTH);
		
		
		SelectedDayCells tempSelectedDayCell;
		
		for(int i = 0; i < selectedDayCells.size(); i++){
			
			tempSelectedDayCell =  selectedDayCells.get(i);
			
			if((iSelectedYear == tempSelectedDayCell.year)&&(iSelectedMonth == tempSelectedDayCell.month)&&(iSelectedDay == tempSelectedDayCell.day)){
				
				//The day has been selected before, cancel the selected if it is selected again.
				selectedDayCells.remove(i);
				return;
			}
		}  		
		
		//The day is not selected before, then select it now.
		
		SelectedDayCells selectedDay = new SelectedDayCells();
		selectedDay.year = iSelectedYear;
		selectedDay.month = iSelectedMonth;
		selectedDay.day = iSelectedDay;
		selectedDay.leaveType = ApplyActivity.selectedLeaveType;
		selectedDay.halfDay = ApplyActivity.selectedHalfDay;
		selectedDay.dayAm = ApplyActivity.selectedDayAm;
		selectedDayCells.add(selectedDay);

	}
	
	public static boolean isSelectedDay(int year, int month, int day){
		
        SelectedDayCells tempSelectedDayCell;
		
		for(int i = 0; i < selectedDayCells.size(); i++){
			
			tempSelectedDayCell =  selectedDayCells.get(i);
			
			if((year == tempSelectedDayCell.year)&&(month == tempSelectedDayCell.month)&&(day == tempSelectedDayCell.day)){
				
				//Find it.
				return true;
			}
		}
		return false;
	}
	
	public static void clearSelectedDays(){
		
			//Log.e("anne", (new Integer(selectedDayCells.size()).toString()));
			selectedDayCells.clear();

	}
	
	public static void printSelectedDays(){
		Log.i("Anne", "started...");
		
		   SelectedDayCells tempSelectedDayCell;
		
            for(int i = 0; i < selectedDayCells.size(); i++){
			
			tempSelectedDayCell =  selectedDayCells.get(i);
			
			Log.i("Anne", "year:"+ (new Integer(tempSelectedDayCell.year)).toString() + "month:" + (new Integer(tempSelectedDayCell.month)).toString() + "day:" +(new Integer(tempSelectedDayCell.day)).toString());
			
            }
	}

	public interface OnItemClick {
		public void OnClick(DateWidgetDayCell item);
	}

	// 构造函数
	public DateWidgetDayCell(Context context, int iWidth, int iHeight) {
		super(context);
		setFocusable(true);
		setLayoutParams(new LayoutParams(iWidth, iHeight));
		//added by ezzgxxo
		//setWillNotDraw(false);
	}

	// 取变量值
	public Calendar getDate() {
		Calendar calDate = Calendar.getInstance();
		calDate.clear();
		calDate.set(Calendar.YEAR, iDateYear);
		calDate.set(Calendar.MONTH, iDateMonth);
		calDate.set(Calendar.DAY_OF_MONTH, iDateDay);
		return calDate;
	}

	// 设置变量值
	public void setData(int iYear, int iMonth, int iDay, Boolean bToday,
			Boolean bHoliday, int iActiveMonth, boolean hasRecord, boolean isAm, boolean isPm) {
		iDateYear = iYear;
		iDateMonth = iMonth;
		iDateDay = iDay;

		this.sDate = Integer.toString(iDateDay);
		this.bIsActiveMonth = (iDateMonth == iActiveMonth);
		this.bToday = bToday;
		this.bHoliday = bHoliday;
		this.hasRecord = hasRecord;
		this.isAm = isAm;
		this.isPm = isPm;
	}

	// 重载绘制方法
	@Override
	protected void onDraw(Canvas canvas) {
		// TODO Auto-generated method stub
		super.onDraw(canvas);
		
		//setWillNotDraw(false);
		rect.set(0, 0, this.getWidth(), this.getHeight());
		rect.inset(1, 1);

		final boolean bFocused = IsViewFocused();
		
		//added by ezzgxxo
		invalidate();
		drawDayView(canvas, bFocused);
		drawDayNumber(canvas);
	}

	public boolean IsViewFocused() {
		return (this.isFocused() || bTouchedDown);
	}

	// 绘制日历方格
	private void drawDayView(Canvas canvas, boolean bFocused) {

		if (bSelected || bFocused) {
			LinearGradient lGradBkg = null;

			if (bFocused) {
				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
						0xffaa5500, 0xffffddbb, Shader.TileMode.CLAMP);
			}

			if (bSelected) {				
					
//				if (bAfternoon == true){
//					lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
//							0xff112233, 0xffddccaa, Shader.TileMode.CLAMP);
//				}
//					
//				if (bMorning == true){
//					lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
//							0xffabcdef, 0xffaaccdd, Shader.TileMode.CLAMP);
//				}
//				
//				if (bMorning == false && bAfternoon == false){
//					lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
//							0xff225599, 0xff4455ff, Shader.TileMode.CLAMP);
//
//				}
				
				lGradBkg = new LinearGradient(rect.left, 0, rect.right, 0,
				0xffaa5500, 0xffffddbb, Shader.TileMode.CLAMP);					
				
				}		
						
							
					
			if (lGradBkg != null) {
				pt.setShader(lGradBkg);
				canvas.drawRect(rect, pt);
			}

			pt.setShader(null);

		} else {
			pt.setColor(getColorBkg(bHoliday, bToday));
			canvas.drawRect(rect, pt);
		}

		if (hasRecord) {
			CreateReminder(canvas, ApplyActivity.special_Reminder);
		}
		// else if (!hasRecord && !bToday && !bSelected) {
		// CreateReminder(canvas, Calendar_TestActivity.Calendar_DayBgColor);
		// }
	}

	// 绘制日历中的数字
	public void drawDayNumber(Canvas canvas) {
		// draw day number
		pt.setTypeface(null);
		pt.setAntiAlias(true);
		pt.setShader(null);
		pt.setFakeBoldText(true);
		pt.setTextSize(fTextSize);
		pt.setColor(ApplyActivity.isPresentMonth_FontColor);
		pt.setUnderlineText(false);
		
		if (!bIsActiveMonth)
			pt.setColor(ApplyActivity.unPresentMonth_FontColor);

		if (bToday){
			pt.setUnderlineText(true);
			pt.setTextSize(fTextSize+10);
			pt.setColor(Color.RED);
		}
		
//		if (bHoliday){
//			pt.setColor(Color.RED);
//			//pt.setUnderlineText(true);
//		}
		//if (bHoliday && ApplyActivity.isCurrentMonthSatSun )
			
		
		final int iPosX = (int) rect.left + ((int) rect.width() >> 1)
				- ((int) pt.measureText(sDate) >> 1);

		final int iPosY = (int) (this.getHeight()
				- (this.getHeight() - getTextHeight()) / 2 - pt
				.getFontMetrics().bottom);
		
		if(ApplyActivity.isCancelPressed == true){
			canvas.drawText(sDate, iPosX, iPosY, pt);
			invalidate();
		}
				
		if (bSelected == true){
			
//			Log.e("Morning", bMorning+"");
//			Log.e("Afternoon", bAfternoon+"");
			
			if (bMorning == false && bAfternoon == false) {
				canvas.drawText(sDate, iPosX, iPosY, pt);
			}	
			
			if (bMorning == true){
				canvas.drawText(sDate+"AM", iPosX, iPosY, pt);
			}
			
			if (bAfternoon == true){
				canvas.drawText(sDate+"PM", iPosX, iPosY, pt);
			}						
		}
			
		canvas.drawText(sDate, iPosX, iPosY, pt);
		
		pt.setUnderlineText(false);
	}

	// 得到字体高度
	private int getTextHeight() {
		return (int) (-pt.ascent() + pt.descent());
	}

	// 根据条件返回不同颜色值
	public static int getColorBkg(boolean bHoliday, boolean bToday) {
		
		int tempColor = 0;
		
		if(bToday)
			tempColor = ApplyActivity.isToday_BgColor;
		
		if(bHoliday)
			tempColor = ApplyActivity.isHoliday_BgColor;
			//Calendar_DayBgColor;
		//如需周末有特殊背景色，可去掉注释
		 //return Calendar_TestActivity.isHoliday_BgColor;			
		return ApplyActivity.Calendar_DayBgColor;
	}

	// 设置是否被选中
	//@Override
	public void setSelected(boolean bEnable) {
		if (this.bSelected != bEnable) {
			this.bSelected = bEnable;
		}
	}
	
	public void setAmorPm()
	{	
			if(ApplyActivity.selectedHalfDay == true)
			{
				
				if(ApplyActivity.selectedDayAm == true){
					bMorning = true;
					bAfternoon = false;
					//Log.e("Morning", bMorning+"");		
				}
				else{
					
					bAfternoon = true;
					bMorning = false;
					//Log.e("Afternoon", bAfternoon+"");
				}
					
			}
			else{
				bAfternoon = false;
				bMorning = false;
			}

	}

	public void setItemClick(OnItemClick itemClick) {
		this.itemClick = itemClick;
	}

	public void doItemClick() {
		if (itemClick != null)
			itemClick.OnClick(this);
	}

	// 点击事件
	@Override
	public boolean onTouchEvent(MotionEvent event) {
		boolean bHandled = false;
		if (event.getAction() == MotionEvent.ACTION_DOWN) {
			bHandled = true;
			bTouchedDown = true;
			invalidate();
			startAlphaAnimIn(DateWidgetDayCell.this);
		}
		if (event.getAction() == MotionEvent.ACTION_CANCEL) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
		}
		if (event.getAction() == MotionEvent.ACTION_UP) {
			bHandled = true;
			bTouchedDown = false;
			invalidate();
			doItemClick();
		}
		return bHandled;
	}

	// 点击事件
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		boolean bResult = super.onKeyDown(keyCode, event);
		if ((keyCode == KeyEvent.KEYCODE_DPAD_CENTER)
				|| (keyCode == KeyEvent.KEYCODE_ENTER)) {
			doItemClick();
		}
		return bResult;
	}

	// 不透明度渐变
	public static void startAlphaAnimIn(View view) {
		AlphaAnimation anim = new AlphaAnimation(0.5F, 1);
		anim.setDuration(ANIM_ALPHA_DURATION);
		anim.startNow();
		view.startAnimation(anim);
	}

	public void CreateReminder(Canvas canvas, int Color) {
		pt.setStyle(Paint.Style.FILL_AND_STROKE);
		pt.setColor(Color);
		Path path = new Path();
		path.moveTo(rect.right - rect.width() / 4, rect.top);
		path.lineTo(rect.right, rect.top);
		path.lineTo(rect.right, rect.top + rect.width() / 4);
		path.lineTo(rect.right - rect.width() / 4, rect.top);
		path.close();
		canvas.drawPath(path, pt);
	}
}

final class SelectedDayCells{
	int year = 0;
	int month = 0;
	int day = 0;
	
	int leaveType = 1;
	boolean halfDay = false;
	boolean dayAm = false;
}
