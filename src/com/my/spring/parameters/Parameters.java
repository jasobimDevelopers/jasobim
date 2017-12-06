package com.my.spring.parameters;

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
	public static final String[] designLevel={"1800","500","","1300","150~200","1500","","300","1500","300","","300","800","","2200","","300",""};
	public static final String[] locationSize={"","","","标高","门边","","","","","","","","","","","","",""};
}
