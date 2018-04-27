package com.my.spring.parameters;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class Parameters {
	private static final String pan = "C";
	private static final String baseFilePath = pan + ":/jasobim/tomcat80/webapps/own/";
	public static final String userLogFilePath = baseFilePath  + "userLog/";
	public static final String ductfilePath = baseFilePath + "duct/";
	public static final String quantityPath = baseFilePath + "quantity/";
	public static final String appKey = "1674ebe520e502d71e4de511";
	public static final String masterSecret = "f6b600502ebe3aca7bc48507";
	public static final String[] checkContent={"户内强电箱","户内弱电箱","盒子墙面的垂直度","开关","开关","厨房插座","厨房插座","客厅或卧室插座","阳台插座","闭路电视","红外幕帘","网络电话","手动报警按钮","手动报警按钮","客厅空调插座","客厅空调插座","插座","插座","坐标","坐标"};
	public static final String[] checkTemplate={"[-5,5]mm","[-1,1]mm"};
	public static final String[] designLevel={"1800","500","","1300","150~200","1300","","850","1300","850","2400","750","750","500","2500","300","300",""};
	public static final String[] locationSize={"1600","300","","标高","门边","1300","","850","1300","850","2400","750","750","500","2500","301","300",""};
	public static final String[] checkContentFanglei={"户内强电箱","户内弱电箱","盒子墙面的垂直度","开关","开关","厨房插座","厨房插座","客厅或卧室插座","阳台插座","闭路电视","红外幕帘","网络电话","手动报警按钮","手动报警按钮","客厅空调插座","客厅空调插座","插座","插座","坐标","坐标"};
	public static final String[] checkContentXianhe={};
	public static final String[] projectFilesType={"图纸会审 ","施工组织设计","专项方案","签证资料","人员备案（特殊工种）","花名册","施工日志","图纸深化及翻样","施工资料","竣工验收资料","竣工图","材料复检报告","第三方强制检测","样板实施计划","材料进度计划"};
	public static final String[][] projectDatas={{"",},{}};
	private static final SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static SimpleDateFormat getSdf() {
		return sdf;
	} 
	private static final SimpleDateFormat sdfs=new SimpleDateFormat("yyyy-MM-dd");
	
	private static final SimpleDateFormat sdfDay=new SimpleDateFormat("HH:mm:ss");
	public static SimpleDateFormat getSdfs() {
		return sdfs;
	}
	public static SimpleDateFormat getSdfday() {
		return sdfDay;
	} 
	public static int getDaysByYearMonth(int year, int month) {
		  
		         Calendar a = Calendar.getInstance();
		         a.set(Calendar.YEAR, year);
		          a.set(Calendar.MONTH, month - 1);
		         a.set(Calendar.DATE, 1);
		         a.roll(Calendar.DATE, -1);
		         int maxDate = a.get(Calendar.DATE);
		         return maxDate;
		     }
}
