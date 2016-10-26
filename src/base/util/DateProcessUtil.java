package base.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
 
/**
 * 日期处理工具类
 * @author zj
 *
 */
public class DateProcessUtil {

	private static SimpleDateFormat[] dateFormats = null;
	
	static {
		final String[] possibleDateFormats = {
				"MM/dd/yyyy HH:mm:ss a",
				"EEE, dd MMM yyyy HH:mm:ss zzz", // RFC_822
				"EEE, dd MMM yyyy HH:mm:ss z", // RFC_822
				"EEE, dd MMM yyyy HH:mm zzzz",
				"yyyy-MM-dd'T'HH:mm:ssZ",
				"yyyy-MM-dd'T'HH:mm:sszzzz",
				"yyyy-MM-dd'T'HH:mm:ss z",
				"yyyy-MM-dd'T'HH:mm:ssz", // ISO_8601
				"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd'T'HHmmss.SSSz",
				"yyyy-MM-dd'T'HH:mm:ss", "yyyy-MM-dd HH:mm:ss", "yyyy-MM-dd",
				"yyyy/MM/dd", "yyyy.MM.dd", "yyyy'年'MM'月'dd'日'",
				"EEE,dd MMM yyyy HH:mm:ss zzz", // 容错
				"EEE, dd MMM yyyy HH:mm:ss", // RFC_822
				"dd MMM yyyy HH:mm:ss zzz", // 容错
				"dd MM yyyy HH:mm:ss zzz", // 容错
				"EEE, dd MM yyyy HH:mm:ss", // RFC_822
				"dd MM yyyy HH:mm:ss", // 容错
				"EEE MMM dd HH:mm:ss zzz yyyy" // bokee 的时间格式 Tue Mar 28
												// 02:25:45 CST 2006
		};

		dateFormats = new SimpleDateFormat[possibleDateFormats.length];
		TimeZone gmtTZ = TimeZone.getTimeZone("GMT");
		Locale locale = Locale.US;
		for (int i = 0; i < possibleDateFormats.length; i++) {
			dateFormats[i] = new SimpleDateFormat(possibleDateFormats[i],
					locale);
			dateFormats[i].setTimeZone(gmtTZ);
		}
	}
	public static final SimpleDateFormat YYYYMMDD = new SimpleDateFormat("yyyy-MM-dd");
	public static final SimpleDateFormat YYYYMMDDPOINT = new SimpleDateFormat("yyyy.MM.dd");
	public static final SimpleDateFormat YYYYlMMlDD = new SimpleDateFormat("yyyy/MM/dd");
	public static final SimpleDateFormat YYYYMMDDline = new SimpleDateFormat("yyyyMMdd");
	public static final SimpleDateFormat YYYYMMDDHHMMSS = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
	public static final SimpleDateFormat YYYYMMDDHHMM = new SimpleDateFormat("yyyy-MM-dd HH:mm");
	public static final SimpleDateFormat YYYYMMDDHHMMSSline = new SimpleDateFormat("yyyyMMddHHmmss");
	public static final SimpleDateFormat YYYYMMDDHHMMSSCn = new SimpleDateFormat("yyyy年MM月dd日");
	public static final SimpleDateFormat MMDDHHMMCn = new SimpleDateFormat("MM月dd日HH:mm");
	public static final SimpleDateFormat MMDDHHMM = new SimpleDateFormat("MM-dd HH:mm");
	public static final SimpleDateFormat YYMMDD = new SimpleDateFormat("yyMMdd");

	/***
	 * 转换日期格式
	 * @return
	 */
	public static String getStrDate(Date date,SimpleDateFormat format)
	{
		if(date==null)
		{
			return null;
		} 
		return format.format(date);
	}
	/***
	 * 转换日期格式
	 * @return
	 */
	public static String getStrDate(Calendar calendar,SimpleDateFormat format)
	{
		
		if(calendar==null)
		{
			return null;
		} 
		return format.format(calendar.getTime());
	}
	
	public static String getStrDate(Long timeLong, SimpleDateFormat format){
		if(timeLong == null){
			return null;
		}
		Calendar calendar = Calendar.getInstance();
		calendar.setTimeInMillis(timeLong);
		return getStrDate(calendar, format);
	}
	/**
	 * 获取今天的时间
	 * @param format
	 * @return
	 */
	public static String getToday(SimpleDateFormat format)
	{
		Calendar result = Calendar.getInstance();
		return format.format(result.getTime()); 
	}
	/**
	 * 获取今天的时间
	 * @param format
	 * @return
	 */
	public static String getToday()
	{
		Calendar result = Calendar.getInstance();
		return YYYYMMDDHHMMSS.format(result.getTime()); 
	}
	
	public static String getYYMMDDToday(){
		Calendar result = Calendar.getInstance();
		return YYMMDD.format(result.getTime()); 
	}
	/**
	 * 转换date
	 * @param dateStr
	 * @param format
	 * @return
	 */
	public static String getStrDate(String dateStr,SimpleDateFormat format)
	{
		Calendar result = Calendar.getInstance();
		try {
			result.setTime(format.parse(dateStr));
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			return null;
		} 
		return format.format(result.getTime());
	}
	public static String getFileName(String hkname)
	{
		String result="";
		if(StringUtils.isNotBlank(hkname))
		{
			result=	hkname.replace("*", "").replace("|", "")
					.replace("\\", "").replace(":","")
					.replace(">", "")
					.replace("?", "")
					.replace("<", "")
					.replace("/", "").replace("\"","");
		}
		return result;
	}

	/**
	 * 获取字符串的日期格式
	 * @param strdate 格式串
	 * @return 格式化的格式串
	 */
	public static Date getDate(String strdate) {
		if(strdate == null)
			return null;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        try {
			return sdf.parse(strdate);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
		/*strdate = strdate.trim();
		if (strdate.length() > 10) {

			if ((strdate.substring(strdate.length() - 5).indexOf("+") == 0 || strdate
					.substring(strdate.length() - 5).indexOf("-") == 0)
					&& strdate.substring(strdate.length() - 5).indexOf(":") == 2) {

				String sign = strdate.substring(strdate.length() - 5, strdate
						.length() - 4);

				strdate = strdate.substring(0, strdate.length() - 5) + sign
						+ "0" + strdate.substring(strdate.length() - 4);
			}

			String dateEnd = strdate.substring(strdate.length() - 6);
			if ((dateEnd.indexOf("-") == 0 || dateEnd.indexOf("+") == 0)
					&& dateEnd.indexOf(":") == 3) {

				if ("GMT".equals(strdate.substring(strdate.length() - 9,
						strdate.length() - 6))) {

				} else {
					String oldDate = strdate;
					String newEnd = dateEnd.substring(0, 3)
							+ dateEnd.substring(4);
					strdate = oldDate.substring(0, oldDate.length() - 6)
							+ newEnd;

				}
			}
		}
		int i = 0;
		Date result = null;
		while (i < dateFormats.length) {
			try {
				result = dateFormats[i].parse(strdate);
				break;
			} catch (java.text.ParseException eA) {
				i++;
			}
		}
		return result;*/
	}
	
	/**
	 * 日期格式判断
	 * @param sDate 传过来的date
	 * @return 布尔
	 */
	public static boolean isValidDate(String sDate) {
	     String datePattern1 = "\\d{4}-\\d{2}-\\d{2}";
	     String datePattern2 = "^((\\d{2}(([02468][048])|([13579][26]))"
	             + "[\\-\\/\\s]?((((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|"
	             + "(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?"
	             + "((0?[1-9])|([1-2][0-9])))))|(\\d{2}(([02468][1235679])|([13579][01345789]))[\\-\\/\\s]?("
	             + "(((0?[13578])|(1[02]))[\\-\\/\\s]?((0?[1-9])|([1-2][0-9])|(3[01])))|(((0?[469])|(11))[\\-\\/\\s]?"
	             + "((0?[1-9])|([1-2][0-9])|(30)))|(0?2[\\-\\/\\s]?((0?[1-9])|(1[0-9])|(2[0-8]))))))";
	     if ((sDate != null)) {
	         Pattern pattern = Pattern.compile(datePattern1);
	         Matcher match = pattern.matcher(sDate);
	         if (match.matches()) {
	             pattern = Pattern.compile(datePattern2);
	             match = pattern.matcher(sDate);
	             return match.matches();
	         }
	         else {
	             return false;
	         }
	     }
	     return false;
	}

	/**
	 * 获得在指定时间上增加指定分钟数后的时间
	 * @param date 指定时间
	 * @param minute 指定分钟数
	 * @return 返回新的实践
	 */
	public static Date addMinute(Date date, int minute) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minute);
		return calendar.getTime();
	}
	
	/**
	 * 根据时间加或减天数
	 * @param date 指定时间
	 * @param day 天数
	 * @return 添加day后的时间
	 */
	public static Date addDay(Date date, Integer day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.DAY_OF_YEAR, day);
		return calendar.getTime();
	}
	
	/**
	 * 指定格式字符串转日期
	 * @param s String类型日期
	 * @param pattern 格式
	 * @return 格式化的日期
	 */
	public static Date formatStr(String s, SimpleDateFormat sdf) {
		if(s == null)
			return null;
        try {
			return sdf.parse(s);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	/**
	 * 获取当前是周几
	 * @return  周几
	 */
	public static String getWeek(){
		Calendar cld = Calendar.getInstance();
		int week = cld.get(Calendar.DAY_OF_WEEK);
		switch(week)
		{
		case 1: return "日";
		case 2: return "一";
		case 3: return "二";
		case 4: return "三";
		case 5: return "四";
		case 6: return "五";
		case 7: return "六";
		}
		return null;
	}
	
	public static void main(String[] str){
		System.out.println(getYYMMDDToday());
		System.out.println(DateProcessUtil.getStrDate(1429265875000L, YYYYMMDDHHMMSS));
		System.out.println(DateProcessUtil.getStrDate(1429200001000L, YYYYMMDDHHMMSS));
	}
	
	/**
	 * 获取当前距离该时间的字符串表示
	 * @param date
	 * @return String
	 */
	public static String getTime(Date date){
		if(date == null){
			return "";
		}
		Date now = new Date();
		long miSecond = now.getTime() - date.getTime();
		miSecond = miSecond / (1000);
		if(miSecond < 60){
			return miSecond + "秒前";
		}
		else{
			miSecond = miSecond / 60;
			if(miSecond < 60){
				return miSecond + "分前";
			}
			else{
				miSecond = miSecond / 60;
				if(miSecond < 24){
					return miSecond + "小时前";
				}
				else{
					miSecond = miSecond / 24;
					if(miSecond < 30){
						return miSecond + "天前";
					}
					else{
						miSecond = miSecond / 30;
						if(miSecond < 12){
							return miSecond + "月前";
						}
						else{
							miSecond = miSecond / 12;
							return miSecond + "年前";
						}
					}
				}
			}
		}
	}
	
	public static String getStringNumber(String number){
		if(number != null){
			if(Long.parseLong(number) < 10000){
				return number;
			}
			if(Long.parseLong(number) >= 10000 && Long.parseLong(number) < 10000000){
				return Long.parseLong(number)/1000 + "千";
			}
			if(Long.parseLong(number) >= 10000000){
				return Long.parseLong(number)/1000000 + "百万";
			}
			return number;
		}
		else{
			return "0";
		}
	}
}
