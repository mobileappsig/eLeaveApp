package com.cgc.mobileappsig.eleave;

import java.util.Calendar;

/**
 * 日历控件样式绘制类
 * @Description: 日历控件样式绘制类

 * @FileName: DayStyle.java 

 * @Package com.calendar.demo 

 * @Author Hanyonglu

 * @Date 2012-3-18 下午03:33:42 

 * @Version V1.0
 */
public class DayStyle {
	private final static String[] vecStrWeekDayNames = getWeekDayNames();

	private static String[] getWeekDayNames() {
		String[] vec = new String[10];

		vec[Calendar.SUNDAY] = "Sun";
		vec[Calendar.MONDAY] = "Mon";
		vec[Calendar.TUESDAY] = "Tue";
		vec[Calendar.WEDNESDAY] = "Wed";
		vec[Calendar.THURSDAY] = "Thu";
		vec[Calendar.FRIDAY] = "Fri";
		vec[Calendar.SATURDAY] = "Sat";
		
		return vec;
	}

	public static String getWeekDayName(int iDay) {
		return vecStrWeekDayNames[iDay];
	}
	
	public static int getWeekDay(int index, int iFirstDayOfWeek) {
		int iWeekDay = -1;

		if (iFirstDayOfWeek == Calendar.MONDAY) {
			iWeekDay = index + Calendar.MONDAY;
			
			if (iWeekDay > Calendar.SATURDAY)
				iWeekDay = Calendar.SUNDAY;
		}

		if (iFirstDayOfWeek == Calendar.SUNDAY) {
			iWeekDay = index + Calendar.SUNDAY;
		}

		return iWeekDay;
	}
}
