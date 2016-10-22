package base.util;


import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.imageio.ImageIO;

public class ThumbnailImage {
	
	public static boolean scale(String imagepath,String newpath, double w, double h){ 
//		返回一个 BufferedImage，作为使用从当前已注册 ImageReader 中自动选择的 ImageReader 解码所提供 File 的结果 
		BufferedImage image=null; 
		try { 
			image = ImageIO.read(new File(imagepath)); 
		} catch (IOException e) { 
			System.out.println("读取图片文件出错！"+e.getMessage()); 
			return false; 
		} 

//		Image Itemp = image.getScaledInstance(100, 100, image.SCALE_SMOOTH); 
		double Ratio = 0.0; 

        if ((image.getHeight() > h) ||(image.getWidth() > w)) { 
            if (image.getHeight() > image.getWidth()) 
//				图片要缩放的比例 
            	Ratio = h / image.getHeight(); 
            else 
                Ratio = w / image.getWidth(); 
        } else {
        	return false; 
        }
//		根据仿射转换和插值类型构造一个 AffineTransformOp。 
        AffineTransformOp op = new AffineTransformOp(AffineTransform 
	                .getScaleInstance(Ratio, Ratio), null); 
//		转换源 BufferedImage 并将结果存储在目标 BufferedImage 中。 
        image = op.filter(image,null); 
//      image.getScaledInstance(100,100,image.SCALE_SMOOTH); 

		FileOutputStream out=null; 
		try { 
			out = new FileOutputStream(newpath); 
			ImageIO.write((BufferedImage)image,"bmp",out); 
			out.close(); 
		} catch (Exception e) { 
			System.out.println("写图片文件出错!!"+e.getMessage()); 
			return false; 
		} 
		return true; 
	}
	
	
	public static String createThumbnailImage(String thumbnailAbsolutePath,String relativePath, String originalPath) {
		String url = relativePath;
		double w = Double.parseDouble(Settings.getInstance().getString("thumbnail.width"));
		double h = Double.parseDouble(Settings.getInstance().getString("thumbnail.height"));
		if (!ThumbnailImage.scale(originalPath, thumbnailAbsolutePath, w, h)) {
			return null;
		}
		return url;
	}
}
