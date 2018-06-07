package com.my.spring.utils;

import java.text.ParseException;
import java.util.Date;

import com.my.spring.parameters.Parameters;

public class Timetest {

	public static void main(String[] args) {
		/*// TODO Auto-generated method stub
		String time1="8:00:00";
		String time2="7:30:00";
		Date date1=null;
		Date date2=null;
		try {
			date1 = Parameters.getSdfday().parse(time1);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		try {
			date2 = Parameters.getSdfday().parse(time2);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if(date1.before(date2)){
			System.out.println("超时了");
		}else{
			System.out.println("可以打卡");
		}*/
		String str="/abc/mp3";
		String[] re=str.split("\\/");
		String newstr="b";
		for(int i=0;i<re.length;i++){
			if(newstr.equals("b")){
				newstr=re[i];
			}else{
				newstr=newstr+"\\"+re[i];
			}
		}
		System.out.println(newstr);
	}

}
