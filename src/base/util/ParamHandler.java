package base.util;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

/**
 * 通过对象类获取request数据对象
 * @author zj
 *
 */
public class ParamHandler {

	/**
	 * 将request中的参数setter至对象
	 * @param request
	 * @param clazz
	 * @return
	 * @throws Exception
	 */
	public static <T> T process(HttpServletRequest request, Class<T> clazz) throws Exception {
		//利用反射创建class对象
		T target = clazz.newInstance();
		
		//获得对象属性集合
		Field[] fs = clazz.getDeclaredFields();
		for (int i = 0; i < fs.length; i++) {
			String name = fs[i].getName();
			String value = request.getParameter(name);
			
			//request中有此数据则处理
			if(value != null && !"".equals(value)) {
				String set = "set" + name.substring(0, 1).toUpperCase() + name.substring(1);
				
				Method m = clazz.getMethod(set, new Class[] {fs[i].getType()});
				//方法调用
				m.invoke(target, getValue(value, fs[i].getGenericType()));
			}
		}
		
		return target;
	}
	
	
	/**
	 * 数据类型转化
	 * @param value
	 * @param type
	 * @return
	 */
	@SuppressWarnings("unchecked")
	private static <T> T getValue(String value, Type type) {
		if(String.class == type) {
			return (T) value;
		}else if(Integer.class == type) {
			return (T) Integer.valueOf(value);
		}else if(Long.class == type) {
			return (T) Long.valueOf(value);
		}else if(Boolean.class == type) {
			return (T) Boolean.valueOf(value);
		}else if(Double.class == type) {
			return (T) Double.valueOf(value);
		}else if(Float.class == type) {
			return (T) Float.valueOf(value);
		}else if(Date.class == type) {
			try {
				SimpleDateFormat fmt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
				return (T) fmt.parse(value);
			} catch (ParseException e) {
				return null;
			}
		}else if(BigDecimal.class == type){
			return (T) new BigDecimal(value);
		}else if(Byte.class == type) {
			return (T) Byte.valueOf(value);
		}else if(Short.class == type) {
			return (T) Short.valueOf(value);
		}else if(Character.class == type) {
			return (T) Character.valueOf(value.charAt(0));
		}
		return (T) value;
	}
}
