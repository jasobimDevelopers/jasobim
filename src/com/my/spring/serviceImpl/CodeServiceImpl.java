package com.my.spring.serviceImpl;


import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Files;
import com.my.spring.model.User;
import com.my.spring.service.CodeService;
import com.my.spring.service.FileService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.SessionManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

@Service("CodeService")
public class CodeServiceImpl implements CodeService {

	@Autowired
	FileService fileSerivce;

	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<List<String>> batchImport(String name, MultipartFile[] file,String token,HttpServletRequest request) {
		DataWrapper<List<String>> urllist= new DataWrapper<List<String>>();
		List<String> temp = new ArrayList<String>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if (file != null && file.length>0) {
				name=name+"/"+userInMemory.getId();
				for(int i=0;i<file.length;i++){
					Files files=fileSerivce.uploadFile(name, file[i], 8, request);
					if(files!=null){
						SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmssSSS" );
        			   	Date d=new Date();
        			   	String str=sdf.format(d);
						/*String rootPath = request.getSession().getServletContext().getRealPath("/");
        			   	String imgpath=rootPath+name;*/
        			   	try{
        			        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        			        @SuppressWarnings("rawtypes")
        					Map hints = new HashMap();  
        			        //内容所使用编码  
        			        hints.put(EncodeHintType.CHARACTER_SET, "utf8");  
        			        BitMatrix bitMatrix = multiFormatWriter.encode("jasobim.com.cn/"+files.getUrl(),BarcodeFormat.QR_CODE, 200, 200, hints);  
        			        //生成二维码  
        			     
        			        File outputFile = new File("C:/jasobim/tomcat80/webapps/ROOT/files/code",str+".png"); 
        			        MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile);  
        			   		/*MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
        			        @SuppressWarnings("rawtypes")
        					Map hints = new HashMap();  
        			        //内容所使用编码  
        			        hints.put(EncodeHintType.CHARACTER_SET, "utf8");  
        			        BitMatrix bitMatrix = multiFormatWriter.encode("139.224.59.3:8080/jasobim"+files.getUrl(),BarcodeFormat.QR_CODE, 200, 200, hints);  
        			        //生成二维码  
        			        File outputFile = new File(imgpath,str+".png"); 
        			        
        			        MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile); */ 
        				} catch (Exception e) {
        					e.printStackTrace();
        				}
        			   	String urlReal="files/code/"+str+".png";
						temp.add(urlReal);
					}else{
						temp.add(file[i].getOriginalFilename()+" format error");
					}
				}
				urllist.setData(temp);
			}
		}else{
			urllist.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return urllist;
	}
	@SuppressWarnings("unchecked")
	@Override
	public DataWrapper<String> batchImportText(String name, String content,String token,HttpServletRequest request) {
		DataWrapper<String> url= new DataWrapper<String>();
		User userInMemory = SessionManager.getSession(token);
		if(userInMemory!=null){
			if (content != null && !content.equals("")) {
				name=name+"/"+userInMemory.getId();
				SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmssSSS" );
			   	Date d=new Date();
			   	String str=sdf.format(d);
			   	try{
			        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
			        @SuppressWarnings("rawtypes")
					Map hints = new HashMap();  
			        //内容所使用编码  
			        hints.put(EncodeHintType.CHARACTER_SET, "utf8");  
			        BitMatrix bitMatrix = multiFormatWriter.encode("jasobim.com.cn/"+content,BarcodeFormat.QR_CODE, 200, 200, hints);  
			        //生成二维码  
			        File outputFile = new File("C:/jasobim/tomcat80/webapps/ROOT/files/code",str+".png"); 
			        MatrixToImageWriter.writeToFile(bitMatrix, "png", outputFile);  
				} catch (Exception e) {
					e.printStackTrace();
				}
			   	String urlReal="files/code/"+str+".png";
				url.setData(urlReal);;
			}
		}else{
			url.setErrorCode(ErrorCodeEnum.User_Not_Logined);
		}
        return url;
	}
}
