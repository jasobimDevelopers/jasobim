package com.my.spring.utils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.my.spring.model.Folder;
import com.my.spring.parameters.Parameters;

public class Test {
	public static void main(String[] args) {
		String str = "abc123";   
	
		System.out.println(HasDigit(str));
		System.out.println(getNumbers(str));
	}
	//截取数字  
	public static String getNumbers(String content) {  
	    Pattern pattern = Pattern.compile("\\d+");  
	    Matcher matcher = pattern.matcher(content);  
	    while (matcher.find()) {  
	       return matcher.group(0);  
	    }  
	    return "";  
	}  
	public static boolean HasDigit(String content) {
	    boolean flag = false;
	    Pattern p = Pattern.compile(".*\\d+.*");
	    Matcher m = p.matcher(content);
	    if (m.matches()) {
	        flag = true;
	    }
	    return flag;
	}
	public static void getFileList(String strPath) throws IOException{
		File f=new File(strPath);
        if(f.isDirectory()){ 
		    File[] fs=f.listFiles();
		    for(int i=0;i<fs.length;i++){
		               String fsPath=fs[i].getAbsolutePath();
		               System.out.println(fsPath);
		               getFileList(fsPath);
		            }
		  }else if(f.isFile()){
		     String fname=f.getAbsolutePath();
		     System.out.println(fname);
		  }else{
		    System.out.println("路径不正确!");
         }
	}
}
