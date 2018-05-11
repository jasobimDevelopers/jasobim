package com.my.spring.utils;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateUtil{
	
	public static String DateCa(String strDateStart,String strDateEnd ){
		String days = null;
		try{	
		   SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
//*************得到传入的两日期,并将时间早的排到前***********************************
		   Date date_start = sdf.parse(strDateStart);
		   Date date_end = sdf.parse(strDateEnd);
		   Calendar dd = Calendar.getInstance();
		   Calendar ff = Calendar.getInstance();
		   dd.setTime(date_start);
		   ff.setTime(date_end);
		   if(dd.after(ff)){
			  Calendar nn = Calendar.getInstance();
			  nn=ff;
			  ff=dd;
			  dd=nn;
		   }
		   
//*************取得文件中的日期数组**************************************************		  
		   File file=new File("src/h.txt");
		   String[] Array=DateUtil.getDateFromFile(file);
		   int daysofH=0;
		   for(int i=0;i<Array.length;i++){
			   Calendar ca = Calendar.getInstance();
			   Date Hdate = sdf.parse( Array[i]);
			   ca.setTime(Hdate);
			   
			   if(ca.after(dd)&&ca.before(ff)){
				   daysofH=daysofH+1;
			   	}
			   else if(ca.equals(dd)){
				   daysofH=daysofH+1;
			   }
			   else if(ca.equals(ff)){
				   daysofH=daysofH+1;
			   }
		   }
		   System.out.println("读取文件后判断中间的法定假日天数为："+daysofH);		  			  			   
		   Calendar cal_start = Calendar.getInstance();
		   Calendar cal_end = Calendar.getInstance();
		   cal_start.setTime(date_start);
		   cal_end.setTime(date_end);
		   DateUtil app = new DateUtil();	
		   int Nworkday=app.getWorkingDay(cal_start, cal_end)+1;
		   System.out.println("计算的未除去法定假日的工作日："+Nworkday);	 
		   days=Integer.toString(Nworkday-daysofH);		   
		   
		} catch (Exception e) {
		}
		return days;
	}
	
	public static String[] getDateFromFile(File file) throws Exception
	{
		String[] array = null;
		
	   BufferedReader breader = new BufferedReader(new FileReader(file));		   		   
	   String str = "";
	   String puString="";
	 while((str = breader.readLine()) != null)
	   {			   
		   puString=str+puString;			  		   
	   }
	 System.out.println("文本读取的字符串为"+puString);
	 array = puString.split(" ");
	 /*for(int i=0;i<array.length;i++)
	 { System.out.println(array[i]);
	 }*/
	 breader.close();
	 return array;
	}	
	

//****************************************************		
	public int getDaysBetween(java.util.Calendar d1, java.util.Calendar d2) {
	if (d1.after(d2)) { 
	   java.util.Calendar swap = d1;
	   d1 = d2;
	   d2 = swap;
	}
	int days = d2.get(java.util.Calendar.DAY_OF_YEAR)- d1.get(java.util.Calendar.DAY_OF_YEAR);
	int y2 = d2.get(java.util.Calendar.YEAR);
	if (d1.get(java.util.Calendar.YEAR) != y2) {
	   d1 = (java.util.Calendar) d1.clone(); 
	   do {
	    days += d1.getActualMaximum(java.util.Calendar.DAY_OF_YEAR);
	    d1.add(java.util.Calendar.YEAR, 1);
	   } while (d1.get(java.util.Calendar.YEAR) != y2);
	}
	return days;
	}
/**
* 计算2个日期之间的相隔天数
* @param d1
* @param d2
* @return
*/
public int getWorkingDay(java.util.Calendar d1, java.util.Calendar d2) {
int result = -1;
if (d1.after(d2)) { 
   java.util.Calendar swap = d1;
   d1 = d2;
   d2 = swap;
}
int betweendays = getDaysBetween(d1, d2);
int charge_date = 0;
int charge_start_date = 0;//开始日期的日期偏移量
int charge_end_date = 0;//结束日期的日期偏移量
   // 日期不在同一个日期内
   int stmp;
   int etmp;
   stmp = 7 - d1.get(Calendar.DAY_OF_WEEK);
   etmp = 7 - d2.get(Calendar.DAY_OF_WEEK);
   if (stmp != 0 && stmp != 6) {// 开始日期为星期六和星期日时偏移量为0
    charge_start_date = stmp - 1;
   }
   if (etmp != 0 && etmp != 6) {// 结束日期为星期六和星期日时偏移量为0
    charge_end_date = etmp - 1;
   }
result = (getDaysBetween(this.getNextMonday(d1), this.getNextMonday(d2)) / 7)
    * 5 + charge_start_date - charge_end_date; 
return result;
}
public String getChineseWeek(Calendar date) {
final String dayNames[] = { "星期日", "星期一", "星期二", "星期三", "星期四", "星期五","星期六" };
int dayOfWeek = date.get(Calendar.DAY_OF_WEEK);
return dayNames[dayOfWeek - 1];
}
/**
* 获得日期的下一个星期一的日期
* 
* @param date
* @return
*/
public Calendar getNextMonday(Calendar date) {
Calendar result = null;
result = date;
do {
 result = (Calendar) result.clone(); //可能是在while中需要保持值的不变clone
 result.add(Calendar.DATE, 1);
} while (result.get(Calendar.DAY_OF_WEEK) != 2);
return result;
}

public int getHolidays(Calendar d1,Calendar d2){
return this.getDaysBetween(d1, d2)-this.getWorkingDay(d1, d2);
}
}
