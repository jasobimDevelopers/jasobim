package base.util;

import java.util.Random;

/**
 * 基本数据处理工具类
 * @author zj
 *
 */
public class DataProcessUtil {
	/**
	 * 通过指定长度返回拼装的字符串，左位补0
	 * @param str
	 * @param length
	 * @return String
	 */
	public static String getDistLengthStr(String str, int length){
		String result = "";
		if(length <= 0){
			return result;
		}
		if(str == null){
			for(int i=0;i<length;i++){
				result += "0";
			}
			return result;
		}
		if(str.length() >= length){
			return str;
		}
		else{
			length = length - str.length();
			for(int i=0;i<length;i++){
				result += "0";
			}
			result += str;
			return result;
		}
	}
	
    /**
     * 返回长度为【strLength】的随机数，在前面补0  
     * @param strLength
     * @return
     */
    public static String getFixLenthString(int strLength) {  
        Random rm = new Random();  
        // 获得随机数  
        double pross = (1 + rm.nextDouble()) * Math.pow(10, strLength);  
        // 将获得的获得随机数转化为字符串  
        String fixLenthString = String.valueOf(pross);  
        // 返回固定的长度的随机数  
        return fixLenthString.substring(1, strLength + 1);  
    }  
}
