package test;

import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.commons.lang3.ArrayUtils;

import com.alibaba.fastjson.JSONObject;

public class TestJavas {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x[]={1,2,5,4,3,6,9,8,7,0};
		InsertAllRight(x);
		ArrayToMap();
		
		try {
			captureScreen("test");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static void InsertAllRight(int[] arr){
		 for (int i = 1; i < arr.length; i++)   
	        {   
	            int t = arr[i];   
	            int j = i;   
	            while ((j > 0) && (arr[j - 1] > t))   
	            {   
	                arr[j] = arr[j - 1];//交换顺序   
	                --j;   
	            }   
	            arr[j] = t;   
	        }   
		for(int y:arr){
			System.out.println(y);
		}
		java.util.Date utilDate = new java.util.Date(); 
		java.sql.Date sqlDate = new java.sql.Date(utilDate.getTime());
		System.out.println(utilDate);
		System.out.println(sqlDate);
		JSONObject json = new JSONObject(); 
		json.put("city", "Mumbai"); 
		json.put("country", "India"); 
		String output=json.toString();
		System.out.println(json.get("city"));
	}
	public static void captureScreen(String fileName) throws Exception { 
		   
		  Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize(); 
		  Rectangle screenRectangle = new Rectangle(screenSize); 
		  Robot robot = new Robot(); 
		  BufferedImage image = robot.createScreenCapture(screenRectangle); 
		  ImageIO.write(image, "png", new File(fileName)); 
		} 
	public static void ArrayToMap(){
		String[][] arg={{"United state","New York"},{"Beihaidao","test"},{"Japan","Tokyo"},{"China","BeiJing"}};
		Map str=ArrayUtils.toMap(arg);
		System.out.println(str.get("Beihaidao"));
		System.out.println(str.get("China"));
	}
}
