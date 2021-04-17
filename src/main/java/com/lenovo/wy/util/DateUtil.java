package com.lenovo.wy.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


public class DateUtil {
	public static String DEAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

	public static boolean isValidDate(String pattern, String dateStr) {
		boolean convertSuccess = true;
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		try {
			sdf.parse(dateStr);
		} catch (ParseException e) {
			convertSuccess = false;
		}
		return convertSuccess;
	}
	
	public static String getFormatDate(String pattern, String dateStr) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		sdf.setLenient(false);
		try {
			Date d = sdf.parse(dateStr);
			return sdf.format(d);
		} catch (ParseException e) {
			return null;
		}
	}

	public static String getCurrentTime() {
		SimpleDateFormat simple = new SimpleDateFormat(DEAULT_PATTERN);
		return simple.format(new Date());
	}
	
	public static String getCurrentTime(String pattern) {
		SimpleDateFormat simple = new SimpleDateFormat(pattern);
		return simple.format(new Date());
	}

	public static String formatDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DEAULT_PATTERN);
		return sdf.format(date);
	}

	public static String formatDate(Date date, String pattern) {
		SimpleDateFormat sdf = new SimpleDateFormat(pattern);
		return sdf.format(date);
	}

	public static Date formatDateStrToDate(String strDate, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = new Date();
		try {
			date = format.parse(strDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return date;
	}
	
	public static Date formatDateStrToDate(String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		String strDate = format.format(new Date());
		return formatDateStrToDate(strDate,pattern);
	}

	/**
	 * 获取几天前/后的日期
	 * 
	 * @param days
	 * @return
	 */
	public static Date getDifferDate(Date date, int days) {
		Calendar now = Calendar.getInstance();
		now.setTime(date);
		now.set(Calendar.DATE, now.get(Calendar.DATE) + days);
		return now.getTime();
	}
	
	/**
	 * 日期转为string
	 */
	public static String date2String(Date date,String pattern) throws ParseException, java.text.ParseException{
		if(CommonUtil.isNull(pattern)){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		if(date==null){
			return null;
		}
		SimpleDateFormat simple = new SimpleDateFormat(pattern);
		return simple.format(date);
	}
	
	public static Date string2Date(String date,String pattern) throws ParseException, java.text.ParseException{
		if(CommonUtil.isNull(pattern)){
			pattern = "yyyy-MM-dd HH:mm:ss";
		}
		SimpleDateFormat simple = new SimpleDateFormat(pattern);
		return simple.parse(date);
	}
	

}
