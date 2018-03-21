package com.my.spring.controller;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.DAO.FileDao;
import com.my.spring.model.Files;
import com.my.spring.service.FileService;
import com.my.spring.serviceImpl.FileServiceImpl;
import com.my.spring.utils.MD5Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserAvatar {
	@Autowired
	private static FileDao fileDao;
	public static void main(String[] arg){
		try {
			createImage("王峰", new Font("黑体", Font.PLAIN, 43), new File("C:/Users/Han/Desktop/sss/" +"ceshi.png"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public static String CreateUserIcon(String name){
		 try {
			 	String newFileName = MD5Util.getMD5String(new Date() + UUID.randomUUID().toString()).replace(".","");
			 	//批量导入。参数：文件名，文件。
			 	createImage(name, new Font("黑体", Font.PLAIN, 43), new File("D:/jasobim/tomcat_3001/webapps/userIcons/" +newFileName+ ".png"));
	           
	            return "userIcons/"+newFileName+".png";
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return null;
	}
	
	public static Files CreateUserIcon(String name,HttpServletRequest request,String filePath){
		 try {
			 	String newFileName = MD5Util.getMD5String(new Date() + UUID.randomUUID().toString()).replace(".","");
			 	//批量导入。参数：文件名，文件。
			 	createImage(name, new Font("黑体", Font.PLAIN, 43), new File("D:/jasobim/tomcat_3001/webapps/userIcons/" +newFileName+ ".png"));
	            File file = new File("D:/jasobim/tomcat_3001/webapps/userIcons/" +newFileName+ ".png");
	            if (file == null || filePath == null || filePath.equals("")) {
	    			return null;
	    		}
	    		String rootPath = request.getSession().getServletContext().getRealPath("/");
	           
	    		  // 读入 文件   
	            FileInputStream in_file = new FileInputStream(file);  
	              
	            // 转 MultipartFile  
	            MultipartFile multi = new MockMultipartFile(newFileName+ ".png", in_file);  
//	            boolean b = itemService.batchImport(newFileName,file);
	            File fileDir = new File(rootPath + "/" + filePath);
	            Files files=new Files();
	            if (!fileDir.exists()) {
	                fileDir.mkdirs();
	            }
	            try {
	                FileOutputStream out = new FileOutputStream(rootPath + "/" + filePath + "/"
	                        + newFileName);
	                    // 写入文件
	                out.write(multi.getBytes());
	                out.flush();
	                out.close();
	               
	                
	                Date date=new Date();
	                DateFormat format=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	                String time=format.format(date);
	                files.setIntro(time);
	                String realPath=filePath + "/"+ newFileName;
	                files.setName(newFileName);//////构件的url
	                files.setUrl(realPath);
	                files.setFileType(5);
	                files.setRealName(multi.getOriginalFilename().substring(0,multi.getOriginalFilename().lastIndexOf(".")));
	                fileDao.addFiles(files);
	            } catch (Exception e) {
	                e.printStackTrace();
	                return null;
	            }
	            return files;
	        } catch (Exception e) {
	            e.printStackTrace();
	        }
		 return null;
	}
	

    public static void createImage(String str, Font font, File outFile) throws Exception {
        if (!isChinese(str)) {
            throw new IllegalArgumentException("unsupported yet!");
        }
        //截取后面两个字
        if (str.length() > 2) {
            str = str.substring(str.length() - 2, str.length());
        }
        int width = 144;
        int height = 144;
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_BGR);
        Graphics2D g = image.createGraphics();
        g.setColor(new Color(98, 189, 154));
        g.fillRect(0, 0, width, height);
        g.setColor(Color.white);
        g.setFont(font);
        g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        FontMetrics fm = g.getFontMetrics(font);
        int textWidth = fm.stringWidth(str);
        int x = (width - textWidth) / 2;
        int ascent = fm.getAscent();
        int descent = fm.getDescent();
        int y = (height - ascent - descent) / 2 + ascent;
        g.drawString(str, x, y);
        g.dispose();
        //输出png图片
        ImageIO.write(image, "png", outFile);
    }

    /**
     * 判断是否是中文
     *
     * @param str
     * @return
     */
    public static boolean isChinese(String str) {
        String regEx = "[\u4e00-\u9fa5]";
        Pattern pat = Pattern.compile(regEx);
        Matcher matcher = pat.matcher(str);
        boolean flg = false;
        if (matcher.find())
            flg = true;
        return flg;
    }

}
