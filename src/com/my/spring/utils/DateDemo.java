package com.my.spring.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
public class DateDemo {
	static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
	
	public static void main(String[] args) throws ParseException {
		String date = "2018-04-01";
		System.out.println(date);
		System.out.println("年份：" + getYear(date));
		System.out.println("月份：" + getMonth(date));
		System.out.println("日期：" + getDay(date));
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");  
        System.out.println(getDaysOfMonth(sdf.parse("2015-02-2")));  
		//System.out.println("月初日期是: " + getMinMonthDate(date));
		//System.out.println("月末日期是: " + getMaxMonthDate(date));
	}
	/**
	* 获取日期年份
	* @param date
	* @return
	* @throws ParseException
	*/
	public static int getYear(String date) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		return calendar.get(Calendar.YEAR);
	}
	
	/**
	* 获取日期月份
	* @param date
	* @return
	* @throws ParseException
	*/
	public static int getMonth(String date) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		return (calendar.get(Calendar.MONTH) + 1);
	}
	
	/**
	* 获取日期号
	* @param date
	* @return
	* @throws ParseException
	*/
	public static int getDay(String date) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		return calendar.get(Calendar.DAY_OF_MONTH);
	}
	/**
	* 获取月份起始日期
	* @param date
	* @return
	* @throws ParseException
	*/
	public static String getMinMonthDate(String date) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	
	/**
	* 获取月份最后日期
	* @param date
	* @return
	* @throws ParseException
	*/
	public static String getMaxMonthDate(String date) throws ParseException{
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(dateFormat.parse(date));
		calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
		return dateFormat.format(calendar.getTime());
	}
	/**
	* 获取当月天数
	* @param date
	* @return
	* @throws ParseException
	*/
	 public static int getDaysOfMonth(Date date) {  
	        Calendar calendar = Calendar.getInstance();  
	        calendar.setTime(date);  
	        return calendar.getActualMaximum(Calendar.DAY_OF_MONTH);  
	  }  
	  
}