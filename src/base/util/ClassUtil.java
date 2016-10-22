/**
 * 
 */
package base.util;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Properties;
import java.util.Set;


/**
 * @author nijie
 *
 */
public class ClassUtil {
	HashMap typeMap = new HashMap();
	
	public void addProperty(String key,Class clazz){
		typeMap.put(key, clazz);
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Object dynamicClass(Object object) throws Exception {
		HashMap returnMap = new HashMap();
		
		// 读取配置文件
		/*
		Properties prop = new Properties();
		String sourcepackage = object.getClass().getName();
		String classname = sourcepackage.substring(sourcepackage
				.lastIndexOf(".") + 1);
		InputStream in = ClassUtil.class.getResourceAsStream(filepath
				+ classname + ".properties");
		prop.load(in);

		Set<String> keylist = prop.stringPropertyNames();
		*/

		Class type = object.getClass();
		BeanInfo beanInfo = Introspector.getBeanInfo(type);
		PropertyDescriptor[] propertyDescriptors = beanInfo
				.getPropertyDescriptors();
		for (int i = 0; i < propertyDescriptors.length; i++) {
			PropertyDescriptor descriptor = propertyDescriptors[i];
			String propertyName = descriptor.getName();
			if (!propertyName.equals("class")) {
				Method readMethod = descriptor.getReadMethod();
				Object result = readMethod.invoke(object, new Object[0]);
				if (result != null) {
					returnMap.put(propertyName, result);
				} else {
					if(java.lang.String.class.equals(descriptor.getPropertyType()))
					  returnMap.put(propertyName, null);
					else if(java.lang.Long.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, new Long("0"));
					else if(java.lang.Integer.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, new Integer("0"));
					else if(java.lang.Double.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, new Double("0"));
					else if(java.math.BigDecimal.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, new java.math.BigDecimal("0"));
					else if(java.util.Date.class.equals(descriptor.getPropertyType()))
						returnMap.put(propertyName, null);
				}
				typeMap.put(propertyName, descriptor.getPropertyType());
			}
		}
		// 加载配置文件中的属性
		/*
		Iterator<String> iterator = keylist.iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			returnMap.put(key, prop.getProperty(key));
			typeMap.put(key, Class.forName("java.lang.String"));
		}
		*/
		
		// map转换成实体对象
		DynamicBean bean = new DynamicBean(typeMap);
		// 赋值
		Set keys = typeMap.keySet();
		for (Iterator it = keys.iterator(); it.hasNext();) {
			String key = (String) it.next();
			//System.out.println("key->"+key+",value->"+returnMap.get(key));
			bean.setValue(key, returnMap.get(key));
		}
		Object obj = bean.getObject();
		return obj;
	}

	/*public static void main(String[] args) throws Exception {
		ClassUtil classUtil=new ClassUtil();
		classUtil.addProperty("original_sex", java.lang.String.class);
	     Object o=classUtil.dynamicClass(new Doctor());
	     Class clazz = o.getClass();  
	     
	     System.out.println(clazz.getName()); 
	     System.out.println(clazz.getSuperclass().getName());
	     
	        Method[] methods = clazz.getDeclaredMethods();  
	        for (Method curMethod : methods) {  
	            System.out.println(curMethod.getName());  
	        }  
	        
	        Field[] fields = clazz.getDeclaredFields();
	        for (Field filed : fields) {  
	            System.out.println(filed.getName());  
	        }  
	        
	        AnnotationUtil.invokeMethodOnObject(o, "setOriginal_sex", "1");
	        
	        
	     int abc=1;
	     abc++;
	}*/
}
