package base.util;

import java.util.Date;

/**
 * sql返回数据安全获取工具类
 * @author zj
 *
 */
public class SqlResultUtil {
	/**
	 * 获取字符串
	 * @param o
	 * @return
	 */
	public static String getSqlResultString(Object o){
		if(o == null){
			return "";
		}
		return String.valueOf(o);
	}

	/**
	 * 获取long
	 * @param o
	 * @return
	 */
	public static Long getSqlResultLong(Object o){
		if(getSqlResultString(o) != null){
			try{
				return Long.parseLong(getSqlResultString(o));
			}
			catch(Exception e){
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取整型
	 * @param o
	 * @return
	 */
	public static Integer getSqlResultInteger(Object o){
		if(getSqlResultString(o) != null){
			try{
				return Integer.parseInt(getSqlResultString(o));
			}
			catch(Exception e){
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取double
	 * @param o
	 * @return
	 */
	public static Double getSqlResultDouble(Object o){
		if(getSqlResultString(o) != null){
			try{
				return Double.parseDouble(getSqlResultString(o));
			}
			catch(Exception e){
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取float
	 * @param o
	 * @return
	 */
	public static Float getSqlResultFloat(Object o){
		if(getSqlResultString(o) != null){
			try{
				return Float.parseFloat(getSqlResultString(o));
			}
			catch(Exception e){
				return null;
			}
		}
		return null;
	}

	/**
	 * 获取日期
	 * @param o
	 * @return
	 */
	public static Date getSqlResultDate(Object o){
		if(getSqlResultLong(o) != null){
			try{
				return new Date(getSqlResultLong(o) * 1000);
			}
			catch(Exception e){
				return null;
			}
		}
		else if(getSqlResultString(o) != null){
			try{
				if(getSqlResultString(o).length() >= 19){
					return DateProcessUtil.getDate(getSqlResultString(o).substring(0,19));
				}
				else if(getSqlResultString(o).length() >= 10){
					return DateProcessUtil.getDate(getSqlResultString(o).substring(0,10));
				}
			}
			catch(Exception e){
				return null;
			}
		}
		return null;
	}
	
	
}
