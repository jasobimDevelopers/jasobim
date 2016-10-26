package base.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 富文本中修改图片宽度的工具类
 * @author zj
 *
 */
public class TextRegex {
	private static int stWidth = 700;//默认宽度700px
	
	private static Pattern imgPattern = Pattern.compile("<img[^>]+>");
	private static Pattern width = Pattern.compile("width=\"([^\"]+)\"");
	private static Pattern height = Pattern.compile("height=\"([^\"]+)\"");
	
	/**
	 * 
	 * @param text
	 * @return
	 */
	public static String textRegex(String text) {
		Matcher matcher = imgPattern.matcher(text);
		
		StringBuffer sb = new StringBuffer();
		
		int imgWidth = 0;//图片宽度
		int imgHeight = 0;//图片高度
		String imgTag = "";
		String imgChange = "";
		
		int stHeight = 0;
		while(matcher.find()) {
			try {
				imgTag = matcher.group();
				
				Matcher wm = width.matcher(imgTag);//读取宽度信息
				if(wm.find()) {
					imgWidth = Integer.valueOf(wm.group(1));
				}
				Matcher hm = height.matcher(imgTag);//读取高度
				if(hm.find()) {
					imgHeight = Integer.valueOf(hm.group(1));
				}
				stHeight = stWidth * imgHeight / imgWidth;//等比例计算高度
				imgChange = imgTag.replaceFirst(imgWidth + "px;", stWidth + "px;").replaceFirst(imgHeight + "px;", stHeight + "px;")
						.replaceFirst("\"" + imgWidth + "\"", "\"" + stWidth + "\"").replaceFirst("\"" + imgHeight + "\"", "\"" + stHeight + "\"");
				
				//字符替换
				matcher.appendReplacement(sb, imgChange);
			} catch (Exception e) {
				System.out.println("格式匹配异常...");
			}
		}
		matcher.appendTail(sb);
		
		return sb.toString();
	}
	
	public static void main(String[] args) {
		String text = "<p style=\"line-height: 2em; text-align: center;\"><img src=\"/huishangkeji/upload/ueditor/1411193260394084518.jpg\" title=\"chuanmeigang-05.jpg\" style=\"box-sizing: border-box; border: 0px; vertical-align: middle; width: 300px; height: 170px;\" width=\"300\" height=\"170\" border=\"0\" vspace=\"0\" alt=\"chuanmeigang-05.jpg\"/></p>";
		String s = textRegex(text);
		System.out.println(s);
	}
}
