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

import com.my.spring.model.Folder;
import com.my.spring.parameters.Parameters;

public class Test {
	public static void main(String[] args) {
		System.out.println(UUID.randomUUID().toString());
		HashMap nodeList = new HashMap();
		nodeList.put("202", "value_202");
		nodeList.put("203", "value_203");
		nodeList.put("204", "value_204");
		nodeList.put("205", "value_205");
		Set entrySet = nodeList.entrySet();  
		System.out.println(nodeList.get("202"));
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
