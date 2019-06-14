package com.my.spring.utils;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
 
import javax.imageio.ImageIO;
 
import com.spire.pdf.PdfDocument;
public class PdfReadUtil {

	public static void main(String[] args) throws IOException {
	 
		//加载PDF文件
		PdfDocument doc = new PdfDocument();
		//File filePdf = new File();
		doc.loadFromFile("C:/Users/Han/Desktop/st/mycat-definitive-guide.pdf");
	 
		//保存PDF的每一页到图片
		BufferedImage image;
		for (int i = 0; i < doc.getPages().getCount(); i++) {
			image = doc.saveAsImage(i);
			File file = new File( String.format("C:/Users/Han/Desktop/st/ToImage-img-%d.png", i));
			ImageIO.write(image, "PNG", file);
		}
		doc.close();
	}
}
