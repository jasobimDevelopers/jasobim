package com.my.spring.utils;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;  
public class ReadTxt {
	  private static  File logoFile = new File("C://Users/Han/Desktop/jaso_logo.png");
	  private static  File QrCodeFile;
	  private static String qurl="";
	    public static void main(String[] args){  
	    	
	        File file=new File("C:/Users/Han/Desktop/水电井.txt");  
	        BufferedReader reader=null;  
	        String temp=null;
	       /**/
	        List<String> itemIdLists = new ArrayList<String>();
	        try{  
	        	/*QrCodeFile= new File("C://Users/Han/Desktop/水电井二维码/电缆桥架/"+"430685"+".png");
        		qurl=new String("http://jasobim.com.cn/page/itemInfo.html?id=430685");
        		QRCodeUtil2.drawLogoQRCode(logoFile, QrCodeFile, qurl, null);*/
	                reader=new BufferedReader(new FileReader(file));  
	                while((temp=reader.readLine())!=null){  
	                    itemIdLists.add(temp);
	                }  
	                if(itemIdLists.size()>0){
	                	for(String s: itemIdLists){
	                			QrCodeFile= new File("C://Users/Han/Desktop/混流风机/"+Integer.valueOf(s)+".png");
		                		qurl=new String("http://jasobim.com.cn/page/itemInfo.html?id="+Integer.valueOf(s));
		                		QRCodeUtil2.drawLogoQRCode(logoFile, QrCodeFile, qurl, null);
	                		
	                	}
	                }
	        }  
	        catch(Exception e){  
	            e.printStackTrace();  
	        }  
	        finally{  
	            if(reader!=null){  
	                try{  
	                    reader.close();  
	                }  
	                catch(Exception e){  
	                    e.printStackTrace();  
	                }  
	            }  
	        }  
	    }  
}
