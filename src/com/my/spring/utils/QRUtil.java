package com.my.spring.utils;
import java.awt.Color;  
import java.awt.Font;  
import java.awt.Graphics;  
import java.awt.Graphics2D;  
import java.awt.Image;  
import java.awt.geom.AffineTransform;  
import java.awt.image.AffineTransformOp;  
import java.awt.image.BufferedImage;  
import java.io.File;  
import java.io.IOException;  
import java.util.HashMap;  
import java.util.Map;  
import javax.imageio.ImageIO;  
import com.google.zxing.BarcodeFormat;  
import com.google.zxing.EncodeHintType;  
import com.google.zxing.MultiFormatWriter;  
import com.google.zxing.WriterException;  
import com.google.zxing.common.BitMatrix;  
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import com.my.spring.model.Item;
  
/*** <p>Title: QRUtil</p> 
* <p>Description:生成二维码 </p> 
* @author sigangjun 
* @date 2016-2-14 下午2:41:38 
*/  
public class QRUtil {  
    // 二维码中间的LOGO图片设置  
    private static final int IMAGE_WIDTH = 20; // 宽度  
    private static final int IMAGE_HEIGHT = 20; // 高度  
    private static final int IMAGE_HALF_WIDTH = IMAGE_WIDTH / 2;  
    private static final int FRAME_WIDTH = 8; // 距离二维码  
  
    // 二维码写码器  
    private static MultiFormatWriter mutiWriter = new MultiFormatWriter();  
  
    /** 
     * 添加在指定位置追加图片水印 
     *  
     * @param waterImage 
     *            --水印文件地址 
     * @param img 
     *            -- 目标 
     * @param x 
     *            --x坐标 
     * @param y 
     *            --y坐标 
     */  
    public final static BufferedImage addWaterImage(String waterImage, BufferedImage img, int x, int y) {  
        try {  
            // 目标文件  
            int width = img.getWidth(null);  
            int height = img.getHeight(null);  
            File _filebiao = new File(waterImage);  
            BufferedImage image = ImageIO.read(_filebiao);  
            Graphics g = image.createGraphics();  
            g.drawImage(img, x, y, width, height, null);  
            g.dispose();  
            return image;  
        } catch (Exception e) {  
            e.printStackTrace();  
            return null;  
        }  
    }  
  
    /** 
     * 添加在指定位置追加文字水印 
     *  
     * @param text 
     *            --要添加的文字 
     * @param img 
     *            --目标 
     * @param fontName 
     *            --字体 
     * @param fontStyle 
     *            --样式 
     * @param color 
     *            --字体颜色 
     * @param fontSize 
     *            --字体大小 
     * @param x 
     *            --x坐标 
     * @param y 
     *            --y坐标 
     * @return 
     */  
    public static BufferedImage addWaterText(String text, BufferedImage img, String fontName, int fontStyle, Color color, int fontSize, int x, int y) {  
        try {  
  
            // 目标文件  
            int width = img.getWidth(null);  
            int height = img.getHeight(null);  
            Graphics g = img.createGraphics();  
            g.drawImage(img, 0, 0, width, height, null);  
            g.setColor(color);  
            
            g.setFont(new Font(fontName, fontStyle, fontSize));  
            g.drawString(text, x, y + fontSize);  
            g.dispose();  
            return img;  
        } catch (Exception e) {  
            System.out.println(e);  
            return null;  
        }  
    }  
  
    /** 
     *  
     * 生成对应的二维码 
     *  
     * @param text 
     *            --二维码内容 
     * @param width 
     *            --二维码图片宽度 
     * @param height 
     *            --二维码图片高度 
     * @param srcImagePath 
     *            -- logo图片地址 
     * @return 
     * @throws WriterException 
     * @throws IOException 
     */  
    public static BufferedImage createQR(String text, int width, int height, String srcImagePath) throws WriterException, IOException {  
        // 读取源图像  
        BufferedImage scaleImage = scale(srcImagePath, IMAGE_WIDTH, IMAGE_HEIGHT, true);  
        int[][] srcPixels = new int[IMAGE_WIDTH][IMAGE_HEIGHT];  
        for (int i = 0; i < scaleImage.getWidth(); i++) {  
            for (int j = 0; j < scaleImage.getHeight(); j++) {  
                srcPixels[i][j] = scaleImage.getRGB(i, j);  
            }  
        }  
  
        Map<EncodeHintType, Object> hint = new HashMap<EncodeHintType, Object>();  
        hint.put(EncodeHintType.CHARACTER_SET, "utf-8");  
        hint.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);  
        hint.put(EncodeHintType.MARGIN, 0);  
        // 生成二维码  
        BitMatrix matrix = mutiWriter.encode(text, BarcodeFormat.QR_CODE, width, height, hint);  
        // 二维矩阵转为一维像素数组  
        int halfW = matrix.getWidth() / 2;  
        int halfH = matrix.getHeight() / 2;  
        int[] pixels = new int[width * height];  
  
        for (int y = 0; y < matrix.getHeight(); y++) {  
            for (int x = 0; x < matrix.getWidth(); x++) {  
                // 读取图片  
                if (x > halfW - IMAGE_HALF_WIDTH && x < halfW + IMAGE_HALF_WIDTH && y > halfH - IMAGE_HALF_WIDTH && y < halfH + IMAGE_HALF_WIDTH) {  
                    pixels[y * width + x] = srcPixels[x - halfW + IMAGE_HALF_WIDTH][y - halfH + IMAGE_HALF_WIDTH];  
                }  
                // 在图片四周形成边框  
                else if ((x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW - IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW + IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH - IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH - IMAGE_HALF_WIDTH + FRAME_WIDTH) || (x > halfW - IMAGE_HALF_WIDTH - FRAME_WIDTH && x < halfW + IMAGE_HALF_WIDTH + FRAME_WIDTH && y > halfH + IMAGE_HALF_WIDTH - FRAME_WIDTH && y < halfH + IMAGE_HALF_WIDTH + FRAME_WIDTH)) {  
                    pixels[y * width + x] = 0xfffffff;  
                } else {  
                    // 此处可以修改二维码的颜色，可以分别制定二维码和背景的颜色；  
                    pixels[y * width + x] = matrix.get(x, y) ? 0xff000000 : 0xfffffff;  
                }  
            }  
        }  
  
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
        image.getRaster().setDataElements(0, 0, width, height, pixels);  
  
        return image;  
    }  
  
    /** 
     * 把传入的原始图像按高度和宽度进行缩放，生成符合要求的图标 
     *  
     * @param srcImageFile 
     *            --源文件地址 
     * @param height 
     *            --目标高度 
     * @param width 
     *            --目标宽度 
     * @param hasFiller 
     *            -- 比例不对时是否需要补白：true为补白; false为不补白; 
     * @throws IOException 
     */  
    private static BufferedImage scale(String srcImageFile, int height, int width, boolean hasFiller) throws IOException {  
        double ratio = 0.0; // 缩放比例  
        File file = new File(srcImageFile);  
        BufferedImage srcImage = ImageIO.read(file);  
        Image destImage = srcImage.getScaledInstance(width, height, BufferedImage.SCALE_SMOOTH);  
        // 计算比例  
        if ((srcImage.getHeight() > height) || (srcImage.getWidth() > width)) {  
            if (srcImage.getHeight() > srcImage.getWidth()) {  
                ratio = (new Integer(height)).doubleValue() / srcImage.getHeight();  
            } else {  
                ratio = (new Integer(width)).doubleValue() / srcImage.getWidth();  
            }  
            AffineTransformOp op = new AffineTransformOp(AffineTransform.getScaleInstance(ratio, ratio), null);  
            destImage = op.filter(srcImage, null);  
        }  
        if (hasFiller) {// 补白  
            BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);  
            Graphics2D graphic = image.createGraphics();  
            graphic.setColor(Color.white);  
            graphic.fillRect(0, 0, width, height);  
            if (width == destImage.getWidth(null))  
                graphic.drawImage(destImage, 0, (height - destImage.getHeight(null)) / 2, destImage.getWidth(null), destImage.getHeight(null), Color.white, null);  
            else  
                graphic.drawImage(destImage, (width - destImage.getWidth(null)) / 2, 0, destImage.getWidth(null), destImage.getHeight(null), Color.white, null);  
            graphic.dispose();  
            destImage = image;  
        }  
        return (BufferedImage) destImage;  
    }  
  
    public void runCode (Item item) throws WriterException, IOException {  
        // 生成二维码  
        BufferedImage img = QRUtil.createQR("http://jasobim.com.cn/page/itemInfo.html?id="+item.getId(), 150, 150, "C://Users/Han/Desktop/jaso_logo.png");  
      
        // 追加二维码模板  
        img = QRUtil.addWaterImage("C://Users/Han/Desktop/test3.jpg", img, 186,79);  
  
        // 追加文字  
        
        img = QRUtil.addWaterText("构件ID:"+item.getSelfId(), img, "微软雅黑", 0, Color.BLACK, 16, 12, 80);  
        img = QRUtil.addWaterText("名称:"+item.getName(), img, "微软雅黑", 0, Color.BLACK, 16, 12, 112); 
        if(item.getInstallUser()!=null){
        	img = QRUtil.addWaterText("安装人员:"+item.getInstallUser(), img, "微软雅黑", 0, Color.BLACK, 16, 12, 176);
        }
        if(item.getSize()!=null && !item.getSize().equals("")){
        	img = QRUtil.addWaterText("尺寸:"+item.getSize(), img, "微软雅黑", 0, Color.BLACK, 16, 12, 145);
        }else{
        	img = QRUtil.addWaterText("构件品牌:"+item.getItemBrand(), img, "微软雅黑", 0, Color.BLACK, 16, 12, 145);
        }
        img = QRUtil.addWaterText("安装日期:"+item.getInstallDate(), img, "微软雅黑", 0, Color.BLACK, 16, 12, 208);
        ImageIO.write(img, "jpg", new File("C://Users/Han/Desktop/风管附件/"+item.getSelfId()+".jpg"));  
    }  
  
} 
