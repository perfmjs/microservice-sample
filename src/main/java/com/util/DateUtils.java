package com.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * 
 * 日期工具
 * @project micro-service
 * @author tony.shen
 * @date 2010-11-18
 * Copyright (C) 2010-2012 www.xxx.com Inc. All rights reserved.
 */
public class DateUtils {

	public static SimpleDateFormat SDF_YYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	public static SimpleDateFormat SDF_YYYY_MM_DD_HH_MM_SS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

	public static String StringToDate_YYYY_MM_DD(Date date){
		String dates="";
		try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            dates = sdf.format(date);
        } catch (Exception e) {
            date = null;
        }
        return dates;
	}
	
    public static Date stringToDate(String dateString, String format) {
        Date date;
        try {
            date = new SimpleDateFormat(format).parse(dateString);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }
	
    public static Date stringToDate(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateString);
        } catch (Exception e) {
            date = null;
        }
        return date;
    }

    public static String dateToString(Date date, String format){
    	if(date == null){
    		return "";
    	}
    	return new SimpleDateFormat(format).format(date);
    }

    public static Date StringToDate_YYYY_MM_DD_HH_MM(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = sdf.parse(dateString);
        }
        catch (Exception e) {
            date = null;
        }
        return date;
    }
    
    public static Date StringToDate_YYYY_MM_DD_HH_MM_SS(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            date = sdf.parse(dateString);
        }
        catch (Exception e) {
            date = null;
        }
        return date;
    }
    
    public static Date stringToDateShort(String dateString) {
        Date date;
        try {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
            date = sdf.parse(dateString);
        }
        catch (Exception e) {
            date = null;
        }
        return date;
    }
    public static String dateToString(Date date){
    	return new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(date);
    }

    public static String getByTimeMillis(long timeMillis){
		Calendar c = Calendar.getInstance();
		c.setTimeInMillis(timeMillis);
		return dateToString(c.getTime());
    }
    
    public static long getTimeMillis(String dateTime){
    	if(dateTime == null || dateTime.equals("")){
    		return 0l;
    	}
		Date date = DateUtils.stringToDate(dateTime);
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.getTimeInMillis();
    }
    
    public static String getNowDate(String format){
    	return new SimpleDateFormat(format).format(new Date());
    }

    public static String getNowDateYYYYMMDD(){
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
    	return sdf.format(date);
    }
    
    public static String getNowDateYYYY_MM_DD(){
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    	return sdf.format(date);
    }
    
    public static String getNowDateYYYY_MM_DD_HH_MM(){
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm");
    	return sdf.format(date);
    }
    
    public static String getNowDateYYYY_MM_DD_HH_MM_SS(){
    	Date date = new Date();
    	SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    	return sdf.format(date);
    }
    
    public static String getNowDateYYYYMMDDHHMMSS(){
    	return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }
    
    public static Calendar getNowCalendar() {
        return Calendar.getInstance();
    }
    
    //获得一个星期前的日期
    public static String getLastWeekDateYYYY_MM_DD(Date date) {
    	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   	 
    	   Calendar calendar = Calendar.getInstance();
    	   calendar.setTime(date);
    	   calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH) -1);//一星期前
   	  
   	       return sdf.format(calendar.getTime());//一星期前
    }
    
    //获得两个星期前的日期
    public static String getLastTwoWeekDateYYYY_MM_DD(Date date){
 	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");   	 
 	   Calendar calendar = Calendar.getInstance();
 	   calendar.setTime(date);
 	   calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH) -1);//一星期前
	  
	   String oneweek = sdf.format(calendar.getTime());//一星期前
	   Date oneWeekDate = null;
	   try{
		   oneWeekDate = sdf.parse(oneweek);
	   }catch(Exception e){
		   e.printStackTrace();
	   }
	   if(oneWeekDate == null){
		   calendar.setTime(new Date());
	 	   calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH) -2);//一星期前
	   }else{
		   calendar.setTime(oneWeekDate);
	 	   calendar.set(Calendar.WEEK_OF_MONTH, calendar.get(Calendar.WEEK_OF_MONTH) -1);//一星期前
	   }
 	   return sdf.format(calendar.getTime());//两个星期前
    }
    
    //获得一个月前的日期
    public static String getLastMonthDateYYYY_MM_DD(Date date){
 	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 	   Calendar calendar = Calendar.getInstance();
 	   calendar.setTime(date);
 	   calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) -1);//一星期前
	  
	   return sdf.format(calendar.getTime());//一个月前
    }
    
    //获得三个月前的日期
    public static String getLastThreeMonthDateYYYY_MM_DD(Date date){
 	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 	   Calendar calendar = Calendar.getInstance();
 	   calendar.setTime(date);
 	   calendar.set(Calendar.MONTH, calendar.get(Calendar.MONTH) -3);//三个月前
 	  
	   return sdf.format(calendar.getTime());//一个月前
    }
    
    //获得一年前的日期
    public static String getLastYearDateYYYY_MM_DD(Date date){
 	   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
 	   Calendar calendar = Calendar.getInstance();
 	   calendar.setTime(date);
 	   calendar.set(Calendar.YEAR, calendar.get(Calendar.YEAR) -1);//一年前
	  
	   return sdf.format(calendar.getTime());
    }
    
    /**
     * 返回true表示时间比预计时间大，不能查询
     * @param startTime
     * @param differsence
     * @return
     * @create_time 2010-11-18 下午02:27:47
     */
    public static boolean compareTime(String startTime, int differsence) {
        Calendar startcalendar = Calendar.getInstance();
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            startcalendar.setTime(sdf.parse(startTime));
            startcalendar.set(startcalendar.MONTH, (startcalendar.get(startcalendar.MONTH) + differsence));
            return calendar.after(startcalendar);
        } catch (Exception e) {
            return true;
        }
    }
    
    /**
     * 返回指定日期所在周的周一和周日, 从周一算起, 符合中国周的算法
     */
    public static Date[] getChineseWeekDateRange(Date dt) {
        return getWeekDateRange(dt);
    }

    /**
     * 返回指定日期所在周的周一和周日, 从周一算起, 符合中国周的算法
     */
    public static Date[] getWeekDateRange(Date dt) {
        int[] minus = { -6, 0, -1, -2, -3, -4, -5 };
        int[] plus = { 0, +6, +5, +4, +3, +2, +1 };

        Calendar cal = Calendar.getInstance();
        cal.setTime(dt);

        int w = cal.get(Calendar.DAY_OF_WEEK);
        if (w < 0)
            w = 0;
        if (w > 7)
            w = 7;

        Calendar cal0 = Calendar.getInstance();
        cal0.setTime(dt);
        cal0.add(Calendar.DAY_OF_WEEK, minus[w - 1]);

        Calendar cal1 = Calendar.getInstance();
        cal1.setTime(dt);
        cal1.add(Calendar.DAY_OF_WEEK, plus[w - 1]);

        return new Date[] { cal0.getTime(), cal1.getTime() };
    }
    
    /**
     * 返回指定年份的周数所在周的周一和周日, 从周一算起, 符合中国周的算法
     */
    public static Date[] getWeekDateRange(int year, int weekNumber) {
        Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.YEAR, year);
        calendar.set(Calendar.WEEK_OF_YEAR, weekNumber);
        calendar.set(Calendar.DAY_OF_WEEK, 6);
        return getWeekDateRange(calendar.getTime());
    }

    /**
     * 计算指定日期为一年中的第几周,为符合中国习惯:将周日所在的周数-1
     * @param dt
     * @return, e.g. {2009, 1}
     */
    public static int[] getYearAndWeekNumber(Date dt) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(dt);
        int dayNum = getWeekDayNumber(dt);
        int[] yearAndWeek = new int[2];
        if (calendar.get(Calendar.MONTH) == 11 && calendar.get(Calendar.WEEK_OF_YEAR) == 1) {
            yearAndWeek = new int[] { calendar.get(Calendar.YEAR) + 1,
                    (dayNum == 7) ? (calendar.get(Calendar.WEEK_OF_YEAR) - 1) : calendar.get(Calendar.WEEK_OF_YEAR) };
        } else {
            yearAndWeek = new int[] { calendar.get(Calendar.YEAR),
                    (dayNum == 7) ? (calendar.get(Calendar.WEEK_OF_YEAR) - 1) : calendar.get(Calendar.WEEK_OF_YEAR) };
        }
        if (dayNum == 7 && yearAndWeek[1] == 0) {
            calendar.add(Calendar.DAY_OF_MONTH, -1);
            return getYearAndWeekNumber(calendar.getTime());
        }
        return yearAndWeek;
    }

    /**
     * 从指定日期开始，往前推算指定周几的日期, 如指定日期正好是指定的周几数，则返回该指定日期
     * @param lastWeekDay：从1至7的整数，如前一个周一为1, 前一个周二为２,　前一个周日为7
     * @return
     */
    public static Date getLastChineseWeekDay(Date startDate, int lastWeekDay) {
        if (lastWeekDay < 1 || lastWeekDay > 7) {
            throw new IllegalArgumentException("lastWeekDay:" + lastWeekDay + " must be larger than 0 or less than 8");
        }
        int[] chineseWeekDay = { 7, 1, 2, 3, 4, 5, 6 }; //中国习惯的周几，如周一为１,周日为7
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(startDate);
        if (chineseWeekDay[calendar.get(Calendar.DAY_OF_WEEK) - 1] == lastWeekDay) {
            return startDate;
        }
        for (int i = 1; i < 8; i++) {
            calendar.add(Calendar.DAY_OF_YEAR, -1);
            if (chineseWeekDay[calendar.get(Calendar.DAY_OF_WEEK) - 1] == lastWeekDay) {
                return calendar.getTime();
            }
        }
        return null;
    }

    /**
     * 计算指定日期是周几
     * @param dt
     * @return
     */
    public static int getWeekDayNumber(Date dt) {
        for (int i = 1; i <= 7; i++) {
            if (getLastChineseWeekDay(dt, i).compareTo(dt) == 0) {
                return i;
            }
        }
        return -1;
    }

    /**
     * 计算多少天前
     * e.g. 20分钟前, 10小时前, 12天前
     * @param dt
     */
    public static String timeBeforeDesc(Date dt) {
        long diff = (new Date().getTime() - dt.getTime());
        long dayDiff = diff / (24*60*60*1000);

        if (dayDiff > 0) {
            return dayDiff + "月前";
        }

        long hourDiff = diff / (60*60*1000);
        if (hourDiff > 0) {
            return hourDiff + "小时前";
        }

        long minuteDiff = diff / (60*1000);
        if (minuteDiff > 0) {
            return minuteDiff + "分钟前";
        } else {
            return "1分钟内";
        }
    }
    
    public static void main(String args[]) {
    	System.out.println(getLastMonthDateYYYY_MM_DD(new Date()));
    	System.out.println(getLastThreeMonthDateYYYY_MM_DD(new Date()));
        System.out.println(timeBeforeDesc(stringToDate("2015-08-27 16:55", "yyyy-MM-dd HH:mm")));
    }
}
