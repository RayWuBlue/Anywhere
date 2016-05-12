package com.ray.anywhere.utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import android.annotation.SuppressLint;

/**
 * ʱ�乤����
 * 
 * @author way
 * 
 */
@SuppressLint("SimpleDateFormat")
public class TimeUtil {
	
	public static long getCurrentTime(){
		return System.currentTimeMillis();
	}

	public static String converTime(long time) {
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - time / 1000;// ������ʱ���������
		String timeStr = null;
		if (timeGap > 3 * 24 * 60 * 60) {
			timeStr = getDayTime(time) + " " + getMinTime(time);
		} else if (timeGap > 24 * 2 * 60 * 60) {// 2�����Ͼͷ��ر�׼ʱ��
			timeStr = "ǰ�� " + getMinTime(time);
		} else if (timeGap > 24 * 60 * 60) {// 1��-2��
			timeStr = timeGap / (24 * 60 * 60) + "���� " + getMinTime(time);
		} else if (timeGap > 60 * 60) {// 1Сʱ-24Сʱ
			timeStr = timeGap / (60 * 60) + "���� " + getMinTime(time);
		} else if (timeGap > 60) {// 1����-59����
			timeStr = timeGap / 60 + "���� " + getMinTime(time);
		} else {// 1����-59����
			timeStr = "���� " + getMinTime(time);
		}
		return timeStr;
	}

	public static String getChatTime(long time) {
		long currentSeconds = System.currentTimeMillis();
		long timeGap = (currentSeconds - time )/1000;
		String timeStr = null;
		int lastDay=getDay(time);
		int nowDay=getDay(currentSeconds);
		
		int DayGap=Math.abs(nowDay-lastDay);
		if(nowDay-lastDay==0&&timeGap <24 * 60 * 60){
			timeStr =getHourAndMin(time);
		}else if(timeGap <2 * 24 * 60 * 60&&(DayGap==1||DayGap>=27)){
			timeStr = "���� "+ getHourAndMin(time);
		}else if(timeGap <3 * 24 * 60 * 60&&(DayGap==2||DayGap>=27)){
			timeStr = "ǰ�� "+ getHourAndMin(time);
		}else{
			SimpleDateFormat format = new SimpleDateFormat("MM/dd HH:mm:ss");
			timeStr =format.format(new Date(time));
		}
		return timeStr;
	}
	
	public static String getMarketTime(long time){
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - time / 1000;// ������ʱ���������
		String timeStr = null;
		if(timeGap<60){
			timeStr=timeGap+"��ǰ";
		}else if(timeGap<3600){
			timeStr=(int)(timeGap/60)+"����ǰ";
		}else if(timeGap<60*60*5){
			timeStr=(int)(timeGap/3600)+"Сʱǰ";
		}else if(timeGap<60*60*24){
			timeStr="����"+getHourAndMin(time);
		}else{
			timeStr=getDayTime(time);
		}
		return timeStr;
	}
	
	
	public static String getInformTime(long time){
		long currentSeconds = System.currentTimeMillis() / 1000;
		long timeGap = currentSeconds - time / 1000;// ������ʱ���������
		String timeStr = null;
		if (timeGap > 3 * 24 * 60 * 60) {
			timeStr = getDayTime(time);
		}else if (timeGap > 24 * 2 * 60 * 60){
			timeStr = "ǰ�� " + getHourAndMin(time);
		}else if (timeGap > 24 * 60 * 60) {// 1��-2��
			timeStr = "���� " + getHourAndMin(time);
		} else if (timeGap > 60 * 60) {// 1Сʱ-24Сʱ
			timeStr ="���� "+getHourAndMin(time);
		}
		return timeStr;		
	}
	
	/**
	 * ����ר��ʱ��
	 * @param time
	 * @return
	 */
	public static String getCommentTime(long time){
		long currentSeconds = System.currentTimeMillis();
		long timeGap = (currentSeconds - time )/1000;
		String timeStr = null;
		int lastDay=getDay(time);
		int nowDay=getDay(currentSeconds);
		
		int DayGap=Math.abs(nowDay-lastDay);
		if(nowDay-lastDay==0&&timeGap <24 * 60 * 60){
			timeStr ="����" +getHourAndMin(time);
		}else if(timeGap <2 * 24 * 60 * 60&&(DayGap==1||DayGap>=27)){
			timeStr = "���� " + getHourAndMin(time);
		}else if(timeGap <3 * 24 * 60 * 60&&(DayGap==2||DayGap>=27)){
			timeStr = "ǰ�� " + getHourAndMin(time);
		}else{
			timeStr =getDayTime(time);
		}
		return timeStr;
	}
	
	/**
	 * ��̬ר��ʱ��
	 * @param time
	 * @return
	 */
	public static String getSquareTime(long time){
		long currentSeconds = System.currentTimeMillis();
		long timeGap = (currentSeconds - time )/1000;
		String timeStr = null;
		int lastDay=getDay(time);
		int nowDay=getDay(currentSeconds);
		
		int DayGap=Math.abs(nowDay-lastDay);
		if(nowDay-lastDay==0&&timeGap <24 * 60 * 60){
			timeStr ="����" +getHourAndMin(time);
		}else if(timeGap <2 * 24 * 60 * 60&&(DayGap==1||DayGap>=27)){
			timeStr = "���� " + getHourAndMin(time);
		}else if(timeGap <3 * 24 * 60 * 60&&(DayGap==2||DayGap>=27)){
			timeStr = "ǰ�� " + getHourAndMin(time);
		}else{
			timeStr =getMinTime(time);
		}
		return timeStr;
	}



	public static String getDayTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		return format.format(new Date(time));
	}

	public static String getMinTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
		return format.format(new Date(time));
	}
	
	public static String getSecondTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return format.format(new Date(time));
	}
	
	public static String getUpdateTime(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
		return format.format(new Date(time));
	}
	
	//2013��11��09�� 09:00 ����
	public static String getYmdHmW(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy��MM��dd��  HH:mm");
		Date date=new Date(time);
		return (format.format(date)+" "+weekday(getDayOfWeek(date)));
	}
	
	//2013/11/09 ����
	public static String getYmdW(long time) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
		Date date=new Date(time);
		return (format.format(date)+" "+weekday(getDayOfWeek(date)));
	}

	public static String getHourAndMin(long time) {
		SimpleDateFormat format = new SimpleDateFormat("HH:mm");
		return format.format(new Date(time));
	}

	public static String getDayString(long timesamp) {
		String result = "";
		final Calendar calendarToday = Calendar.getInstance();
		calendarToday.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendarToday.setTimeInMillis(System.currentTimeMillis());

		final Calendar calendarday = Calendar.getInstance();
		calendarday.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendarday.setTimeInMillis(timesamp);

		@SuppressWarnings("static-access")
		int time = calendarToday.DAY_OF_MONTH - calendarday.DAY_OF_MONTH;

		if (time == 2) {
			result = "ǰ�� ";
		} else if (time == 1) {
			result = "���� ";
		} else if (time == 0) {
			result = "���� "+getHourAndMin(time);
		} else {
			result = time + "��ǰ ";
			
		}

		return result;
	}
	
	public static String getCommmentString(long timesamp) {
		String result = "";
		final Calendar calendarToday = Calendar.getInstance();
		calendarToday.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendarToday.setTimeInMillis(System.currentTimeMillis());

		final Calendar calendarday = Calendar.getInstance();
		calendarday.setTimeZone(TimeZone.getTimeZone("GMT+8:00"));
		calendarday.setTimeInMillis(timesamp);

		@SuppressWarnings("static-access")
		int time = calendarToday.DAY_OF_MONTH - calendarday.DAY_OF_MONTH;

		if (time == 2) {
			result = "ǰ�� "+getHourAndMin(timesamp);
		} else if (time == 1) {
			result = "���� "+getHourAndMin(timesamp);
		} else if (time == 0) {
			result = "���� "+getHourAndMin(timesamp);
		} else {
			result = getMinTime(time);
		}

		return result;
	}
	
	/**
	 * �������ڼ�
	 * @return
	 */
	public static int getDayOfWeek(long timesamp){
		 Calendar calendar = Calendar.getInstance();
	     Date date = new Date(timesamp);
	     calendar.setTime(date);
	     if(calendar.get(Calendar.DAY_OF_WEEK)==1){
	    	 return 7;
	     }else{
	    	 return calendar.get(Calendar.DAY_OF_WEEK)-1;
	     }	
	}
	
	public static int getDayOfWeek(Date date){
		 Calendar calendar = Calendar.getInstance();
	     calendar.setTime(date);
	     if(calendar.get(Calendar.DAY_OF_WEEK)==1){
	    	 return 7;
	     }else{
	    	 return calendar.get(Calendar.DAY_OF_WEEK)-1;
	     }	
	}
	
	/**
	 * ���ؽ��������ڼ�
	 * @return
	 */
	public static int getDayOfWeek(){
		 Calendar calendar = Calendar.getInstance();
	     Date date = new Date();
	     calendar.setTime(date);
	     if(calendar.get(Calendar.DAY_OF_WEEK)==1){
	    	 return 7;
	     }else{
	    	 return calendar.get(Calendar.DAY_OF_WEEK)-1;
	     }	
	}
	
	/**
	 *�������� ��ȡ��������
	 * @param week
	 * @return
	 */
	public static String weekday(int week){
		String weekday = null;
		switch(week){
		case 1:weekday="����һ";break;
		case 2:weekday="���ڶ�";break;
		case 3:weekday="������";break;
		case 4:weekday="������";break;
		case 5:weekday="������";break;
		case 6:weekday="������";break;
		case 7:weekday="������";break;
		default:weekday="���ڳ���";
		}
		return weekday;
	}
	
	/**
	 * 2��ʱ������������
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int betweenDays(long beginDate,long endDate){
		long timeGap = (endDate - beginDate )/1000;
		int dayGap=getDay(endDate)-getDay(beginDate);
		int day=24*60*60;
		if(timeGap<0){
			return -1;
		}else if(dayGap==0&&timeGap<day){
			return 0;
		}else if(timeGap<2*day){
			return 1;
		}else if(timeGap<3*day){
			return 2;
		}else{
			return (int)(timeGap/day);	
		}		
	}
	
	/**
	 * ����ר��ʱ����(��λ��)
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	public static int newBetweenDays(long beginDate,long endDate){
		long timeGap = (endDate - beginDate )/1000;
		int dayGap=getDay(endDate)-getDay(beginDate);
		int day=24*60*60;
		if(dayGap==0&&timeGap<day){
			return 0;
		}else if(timeGap<2*day){
			return 1;
		}else if(timeGap<3*day){
			return 2;
		}else{
			return (int)(timeGap/day);	
		}	
	}
	
	public static int betweenDays(long endDate){
		return betweenDays(System.currentTimeMillis(), endDate);
	}
	
	
	/**
	 * ���ɶ�Ӧ��ʱ���(��΢��)
	 * @param year
	 * @param month
	 * @param day
	 * @param hour
	 * @param minute
	 * @return
	 */
	public static long createtimesamp(int year,int month,int day,int hour,int minute){
		Calendar ca=Calendar.getInstance();
		ca.set(year, month-1, day,hour, minute);
		return ca.getTimeInMillis();		
	}
	
	
	public static int  getYear(long timesamp){
		SimpleDateFormat format = new SimpleDateFormat("yyyy");
		return Integer.parseInt(format.format(new Date(timesamp)));
	}
	
	public static int getMonth(long timesamp){
		SimpleDateFormat format = new SimpleDateFormat("MM");
		return Integer.parseInt(format.format(new Date(timesamp)));
	}
	
	public static int getDay(long timesamp){
		SimpleDateFormat format = new SimpleDateFormat("dd");
		return Integer.parseInt(format.format(new Date(timesamp)));
	}
	
	public static int getHour(long timesamp){
		SimpleDateFormat format = new SimpleDateFormat("HH");
		return Integer.parseInt(format.format(new Date(timesamp)));
	}
	
	public static int getMin(long timesamp){
		SimpleDateFormat format = new SimpleDateFormat("mm");
		return Integer.parseInt(format.format(new Date(timesamp)));
	}

	/** ���ڸ�ʽ */
	private final static ThreadLocal<SimpleDateFormat> dateFormat = new ThreadLocal<SimpleDateFormat>(){
	    protected SimpleDateFormat initialValue() {
		return new SimpleDateFormat("yyyy-MM-dd");
	    }
	};
	
	/** ʱ���ʽ */
	private final static ThreadLocal<SimpleDateFormat> timeFormat = new ThreadLocal<SimpleDateFormat>(){
	    protected SimpleDateFormat initialValue() {
		return new SimpleDateFormat("yyyy-MM-dd HH:mm");
	    }
	};
	
	/**  
	 * ��ȡ��ǰʱ��:Date
	 */
	public static Date getDate(){
		return new Date();
	}
	
	/**  
	 * ��ȡ��ǰʱ��:Calendar
	 */
	public static Calendar getCal(){
		return Calendar.getInstance();
	}

	/**  
	 * ����ת��Ϊ�ַ���:yyyy-MM-dd
	 */
	public static String dateToStr(Date date){
		if(date != null)
			return dateFormat.get().format(date);
		return null;
	}
	
	/**  
	 * ʱ��ת��Ϊ�ַ���:yyyy-MM-dd HH:mm:ss
	 */
	public static String timeToStr(Date date){
		if(date != null)
			return timeFormat.get().format(date);
		return null;
	}
	
	/**  
	 * �ַ���ת��Ϊ����:yyyy-MM-dd
	 */
	public static Date strToDate(String str){
		Date date = null;
		try {
			date = dateFormat.get().parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**  
	 * �ַ���ת��Ϊʱ��:yyyy-MM-dd HH:mm:ss
	 */
	public static Date strToTime(String str){
		Date date = null;
		try {
			date = timeFormat.get().parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}
	
	/**  
	 * �Ѻõķ�ʽ��ʾʱ��
	 */
	public static String friendlyFormat(String str){
		Date date = strToTime(str);
		if(date == null)
			return ":)";
		Calendar now = getCal();
		String time = new SimpleDateFormat("HH:mm").format(date);
		
		// ��һ�������������ͬһ��
		String curDate = dateFormat.get().format(now.getTime());
		String paramDate = dateFormat.get().format(date);
		if(curDate.equals(paramDate)){
			int hour = (int) ((now.getTimeInMillis() - date.getTime()) / 3600000);
			if(hour > 0)
				return time;
			int minute = (int) ((now.getTimeInMillis() - date.getTime()) / 60000);
			if (minute < 2)
				return "�ո�";
			if (minute > 30)
				return "���Сʱ��ǰ";
			return minute + "����ǰ";
		}
		
		// �ڶ������������ͬһ��
		int days = (int) ((getBegin(getDate()).getTime() - getBegin(date).getTime()) / 86400000 );
		if(days == 1 )
			return "���� "+time;
		if(days == 2)
			return "ǰ�� "+time;
		if(days <= 7)
			return days + "��ǰ";
		return dateToStr(date);
	}
	
	/**  
	 * �������ڵ�0��:2012-07-07 20:20:20 --> 2012-07-07 00:00:00
	 */
	public static Date getBegin(Date date){
		return strToTime(dateToStr(date)+" 00:00:00");
	}

	public static void main(String[] args) {
		System.err.println(friendlyFormat("2013-09-16 11:27:19"));
	}
	
}
