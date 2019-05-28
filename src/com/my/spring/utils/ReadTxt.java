 package com.my.spring.utils;
import java.io.BufferedReader;  
import java.io.File;  
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.my.spring.DAO.ItemDao;
//import com.my.spring.DAOImpl.ItemDaoImpl;
import com.my.spring.model.Item;
public class ReadTxt {
	
	 // private static  File logoFile = new File("C://Users/Han/Desktop/jaso_logo.png");
	  //private static  File QrCodeFile;
	  //private static String qurl="";
	  @Autowired
	  private static ItemDao itemDao;  
	  public ReadTxt(ItemDao itemDao){
		  ReadTxt.itemDao=itemDao;
	  }
	  public Item getItem(Long projectId){
		  return itemDao.getItemBySelfId(projectId);
	  }
	    public static void main(String[] args){  
	    	//QRUtil qr = new QRUtil();
	        File file=new File("C:/Users/Han/Desktop/水电井.txt");  
	        BufferedReader reader=null;  
	        String temp=null;
	       /**/
	        //ItemDao itemDao= new ItemDaoImpl();
	        //ReadTxt readTxt = new ReadTxt(itemDao);
	        List<String> itemIdLists = new ArrayList<String>();
	        try{  
	        	/*QrCodeFile= new File("C://Users/Han/Desktop/水电井二维码/电缆桥架/"+"430685"+".png");
        		qurl=new String("http://jasobim.com.cn/page/itemInfo.html?id=430685");
        		QRCodeUtil2.drawLogoQRCode(logoFile, QrCodeFile, qurl, null);*/
	                reader=new BufferedReader(new FileReader(file));  
	                while((temp=reader.readLine())!=null){  
	                    itemIdLists.add(temp);
	                }  
	               
	               /* if(itemIdLists.size()>0){
	                	for(String s: itemIdLists){
	                			//Long selfId=Long.valueOf(s);
	                			Long projectId=(long) 271;
	                			Item items = new Item();
	                			items=readTxt.getItem( projectId);
	                			if(items!=null){
	                				qr.runCode(items);
	                			}
	                			
	                			QrCodeFile= new File("C://Users/Han/Desktop/句容香山壹境/管道/"+Integer.valueOf(s)+".png");
		                		qurl=new String("http://jasobim.com.cn/page/itemInfo.html?id="+Integer.valueOf(s));
		                		QRCodeUtil.drawLogoQRCode(logoFile, QrCodeFile, qurl, null);
	                	}
	                }*/
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
