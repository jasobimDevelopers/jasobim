package com.my.spring.utils.fileUtils;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import com.my.spring.parameters.Parameters;
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;

import java.awt.image.BufferedImage;  
import java.util.HashMap;  
import java.util.List;  
import java.util.ArrayList;  
import java.io.File;  
import java.io.IOException;  
import java.io.FileOutputStream;  
import java.util.Map;  
/*
 * 图片压缩工具类
 * */  
public class ResizeImage {  
  
    /** 
     * @param im            原始图像 
     * @param resizeTimes   需要缩小的倍数，缩小2倍为原来的1/2 ，这个数值越大，返回的图片越小 
     * @return              返回处理后的图像 
     */  
    public BufferedImage resizeImage(BufferedImage im, float resizeTimes) {  
        /*原始图像的宽度和高度*/  
        int width = im.getWidth();  
        int height = im.getHeight();  
  
        /*调整后的图片的宽度和高度*/  
        int toWidth = (int) (Float.parseFloat(String.valueOf(width)) / resizeTimes);  
        int toHeight = (int) (Float.parseFloat(String.valueOf(height)) / resizeTimes);  
  
        /*新生成结果图片*/  
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);  
  
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
        return result;  
    }  
  
    /** 
     * @param im            原始图像 
     * @param resizeTimes   倍数,比如0.5就是缩小一半,0.98等等double类型 
     * @return              返回处理后的图像 
     */  
    public BufferedImage zoomImage(BufferedImage im) {  
        /*原始图像的宽度和高度*/  
  
        /*调整后的图片的宽度和高度*/  
        int toWidth=400;  
        int toHeight=280;  
        /*新生成结果图片*/  
        BufferedImage result = new BufferedImage(toWidth, toHeight, BufferedImage.TYPE_INT_RGB);  
  
        result.getGraphics().drawImage(im.getScaledInstance(toWidth, toHeight, java.awt.Image.SCALE_SMOOTH), 0, 0, null);  
        return result;  
    }  
  
    /** 
     * @param path  要转化的图像的文件夹,就是存放图像的文件夹路径 
     * @param type  图片的后缀名组成的数组 
     * @return 
    */  
    public List<BufferedImage> getImageList(String path, String[] type) throws IOException{  
        Map<String,Boolean> map = new HashMap<String, Boolean>();  
        for(String s : type) {  
            map.put(s,true);  
        }  
        List<BufferedImage> result = new ArrayList<BufferedImage>();  
        File[] fileList = new File(path).listFiles();  
        for (File f : fileList) {  
            if(f.length() == 0)  
                continue;  
            if(map.get(getExtension(f.getName())) == null)  
                continue;  
            result.add(javax.imageio.ImageIO.read(f));  
        }  
        return result;  
    }  
    public BufferedImage getImage(String path) throws IOException{  
        return javax.imageio.ImageIO.read(new File(path));  
    }  
  
    /** 
     * 把图片写到磁盘上 
      * @param im 
     * @param path     eg: C://home// 图片写入的文件夹地址 
      * @param fileName DCM1987.jpg  写入图片的名字 
      * @return 
     */  
    public boolean writeToDisk(BufferedImage im, String path, String fileName) {  
        File f = new File(path + fileName);  
        String fileType = getExtension(fileName);  
        if (fileType == null)  
            return false;  
        try {  
            ImageIO.write(im, fileType, f);  
            im.flush();  
            return true;  
        } catch (IOException e) {  
            return false;  
        }  
    }  
  
  
    public boolean writeHighQuality(BufferedImage im, String fileFullPath) {  
        try {  
            /*输出到文件流*/  
            FileOutputStream newimage = new FileOutputStream(fileFullPath);  
            JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(newimage);  
            JPEGEncodeParam jep = JPEGCodec.getDefaultJPEGEncodeParam(im);  
            /* 压缩质量 */  
            jep.setQuality(1f, true);  
            encoder.encode(im, jep);  
           /*近JPEG编码*/  
            newimage.close();  
            return true;  
        } catch (Exception e) {  
            return false;  
        }  
    }  
  
    /** 
     * 返回文件的文件后缀名 
      * @param fileName 
      * @return 
    */  
    public String getExtension(String fileName) {  
        try {  
            return fileName.split("\\.")[fileName.split("\\.").length - 1];  
        } catch (Exception e) {  
            return null;  
        }  
    }  
//测试  
    public static void main(String[] args) throws Exception{  
  
    /*  System.out.println(123); 
        String inputFoler = "F:\\testimages\\yuan";  
         这儿填写你存放要缩小图片的文件夹全地址 
        String outputFolder = "F:\\testimages\\ys";   
        这儿填写你转化后的图片存放的文件夹 
        float times = 0.5f;  
        这个参数是要转化成的倍数,如果是1就是转化成1倍 
 
 
        ResizeImage r = new ResizeImage(); 
       
   List<BufferedImage> imageList = r.getImageList(inputFoler,new String[] {"png"}); 
        for(BufferedImage i : imageList) { 
             
         r.writeHighQuality(r.zoomImage(i,times),outputFolder); 
         System.out.println("..."); 
  }*/  
        ResizeImage r=new ResizeImage();  
        String filepath="C:\\Users\\Han\\Desktop\\";  
        String filename="3f2fc560a0399448430f488d2d695d4e.jpg";  
        BufferedImage im=r.getImage(filepath+filename);  
        r.writeHighQuality(r.zoomImage(im), filepath+"s_"+filename);//为防止覆盖原图片,加s_区分是压缩以后的图片  
    }
    public static String ResizeImages(HttpServletRequest request,String filepath,String filename) throws IOException{
    	String realPath=request.getSession().getServletContext().getRealPath("/");
    	String outpath="D:\\jasobim\\tomcat_8080\\webapps\\ROOT\\files\\projectpics\\";  
    	ResizeImage r=new ResizeImage();  
        BufferedImage im=r.getImage(realPath+Parameters.exchangePath(filepath));  
        r.writeHighQuality(r.zoomImage(im), outpath+"s_"+filename);//为防止覆盖原图片,加s_区分是压缩以后的图片  
		return "files/projectpics/"+"s_"+filename;
    }
}  
