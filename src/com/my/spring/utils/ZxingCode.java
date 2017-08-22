package com.my.spring.utils;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;  
import javax.imageio.ImageIO;  
import java.io.File;  
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.io.IOException;  
import java.awt.image.BufferedImage;  
public final class ZxingCode {  
  private static final int BLACK = 0xFF000000;  
  private static final int WHITE = 0xFFFFFFFF;  
  private ZxingCode() {}  
  public static BufferedImage toBufferedImage(BitMatrix matrix) {  
    int width = matrix.getWidth();  
    int height = matrix.getHeight();  
    BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
    for (int x = 0; x < width; x++) {  
      for (int y = 0; y < height; y++) {  
        image.setRGB(x, y, matrix.get(x, y) ? BLACK : WHITE);  
      }  
    }  
    return image;  
  }  
  public static void writeToFile(BitMatrix matrix, String format, File file)  
      throws IOException {  
    BufferedImage image = toBufferedImage(matrix);  
    if (!ImageIO.write(image, format, file)) {  
      throw new IOException("Could not write an image of format " + format + " to " + file);  
    }  
  }  
  public static void writeToStream(BitMatrix matrix, String format, OutputStream stream)  
      throws IOException {  
    BufferedImage image = toBufferedImage(matrix);  
    if (!ImageIO.write(image, format, stream)) {  
      throw new IOException("Could not write an image of format " + format);  
    }  
  }  
  @SuppressWarnings("unchecked")
  public static void main(String[] args) { 
	try {
	        String content = "http://mp.weixin.qq.com/s?__biz=MzI2MzU0NDkzNQ==&mid=100000007&idx=1&sn=9a85a9cffc294427379036356657a0a4&chksm=6abb080f5dcc8119c363ae5b0e07360b063b162222f9721cabda130eade63f5ca69dfc39f331#rd"; 
	        String path = "C:/Users/Han/Desktop/e.pdf";
	        MultiFormatWriter multiFormatWriter = new MultiFormatWriter();
	        @SuppressWarnings("rawtypes")
			Map hints = new HashMap();  
	        //内容所使用编码  
	        hints.put(EncodeHintType.CHARACTER_SET, "utf8");  
	        BitMatrix bitMatrix = multiFormatWriter.encode(content,BarcodeFormat.QR_CODE, 200, 200, hints);  
	        //生成二维码  
	        SimpleDateFormat sdf =   new SimpleDateFormat("yyyyMMddHHmmssSSS" );
		   	Date d=new Date();
		   	String str=sdf.format(d);
	        File outputFile = new File(path,str+".jpg"); 
	        MatrixToImageWriter.writeToFile(bitMatrix, "jpg", outputFile);  
	} catch (Exception e) {
	    e.printStackTrace();
	}
 }  
}
