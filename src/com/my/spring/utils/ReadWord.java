package com.my.spring.utils;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.apache.poi.POIXMLDocument;
import org.apache.poi.POIXMLTextExtractor;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.xwpf.extractor.XWPFWordExtractor;
public class ReadWord {
	 public String readWord(String path) {  
	        String buffer = "";  
	        try {  
	            if (path.endsWith(".doc")) {  
	                InputStream is = new FileInputStream(new File(path));  
	                WordExtractor ex = new WordExtractor(is);  
	                String c=ex.getTextFromPieces();
	                System.out.println(c+"//////");
	                buffer = ex.getText();  
	                //ex.close();  
	            } else if (path.endsWith("docx")) {  
	                OPCPackage opcPackage = POIXMLDocument.openPackage(path);  
	                POIXMLTextExtractor extractor = new XWPFWordExtractor(opcPackage);  
	                buffer = extractor.getText();  
	                //extractor.close();  
	            } else {  
	                System.out.println("此文件不是word文件！");  
	            }  
	  
	        } catch (Exception e) {  
	            e.printStackTrace();  
	        }  
	  
	        return buffer;  
	    }  
	  
	    public static void main(String[] args) {  
	        // TODO Auto-generated method stub  
	    	ReadWord tp = new ReadWord();  
	    	
	        String content = tp.readWord("C:\\Users\\Han\\Desktop\\advancedOrders.doc");  
	        System.out.println("content===="+content);  
	    }  
}
