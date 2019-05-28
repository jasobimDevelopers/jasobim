package com.my.spring.utils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test {
	public String test(String urls,String access_token) throws IOException {
        //请求的webservice的url
        URL url = new URL(urls);
        //创建http链接
        HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();
    
        //设置请求的方法类型
        httpURLConnection.setRequestMethod("GET");
        
        //设置请求的内容类型
        httpURLConnection.setRequestProperty("Content-type", "application/x-www-form-urlencoded");
        httpURLConnection.setRequestProperty("Authorization", "Bearer "+access_token);
        
        //设置发送数据
        httpURLConnection.setDoOutput(true);
        //设置接受数据
        httpURLConnection.setDoInput(true);
        
        //发送数据,使用输出流
        OutputStream outputStream = httpURLConnection.getOutputStream();
    
       // String content = "user_id="+ URLEncoder.encode("13846", "gbk");
        
        //发送数据
       // outputStream.write(content.getBytes());
    
        //接收数据
        InputStream inputStream = httpURLConnection.getInputStream();
    
        //定义字节数组
        byte[] b = new byte[1024];
        
        //定义一个输出流存储接收到的数据
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        //开始接收数据
        int len = 0;
        while (true) {
            len = inputStream.read(b);
            if (len == -1) {
                //数据读完
                break;
            }
            byteArrayOutputStream.write(b, 0, len);
        }
        
        //从输出流中获取读取到数据(服务端返回的)
        int resultState=httpURLConnection.getResponseCode();
        //200 成功
        //401 未授权
        //500 服务器错误
        String response = byteArrayOutputStream.toString();
        
        System.out.println(response);
        return response;
        
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
