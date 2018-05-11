package com.my.spring.utils;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;

import com.my.spring.parameters.Parameters;

public class Test {

	public static void main(String[] args) {
			Date str = new Date();
			String str1=" 08:00";
			String str2=Parameters.getSdfs().format(str)+str1;
			System.out.println(str2);
			Date date=null;
			try {
				date= Parameters.getSdfs().parse(str2);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(date);
	}
}
