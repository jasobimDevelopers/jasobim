/**
 * 
 */
package base.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.persistence.Column;

import com.wordnik.swagger.annotations.ApiModelProperty;

import base.tag.BaseTag;

/**
 * @author nijie
 *
 */
public class AnnotationUtil {

	public static String resolveClassFullNameByEntityName(String entityName){
		String[] pak=BaseTag.CLASS_PACKAGE.split(",");
			if(pak!=null&&pak.length>0){
				for(int i=0;i<pak.length;i++){
					boolean found=false;
					Class c=null;
					try {
					    c=Class.forName(pak[i]+entityName);
						found=true;
					} catch (ClassNotFoundException e) {
						found=false;
					}
					if(found)
						return c!=null?c.getName():null;
				}
			}
			
		return null;
	}
	
	
	public static boolean isAnnotationPresentForEntity(String name,Class annoClass){
		Annotation[]  anns=getAnnotationsByEntityName(name);
		for(Annotation an:anns){
			if(an.annotationType().equals(annoClass))
				return true;
		}
		return false;
	}
	
	public static Annotation[] getAnnotationsByEntityName(String name) {
		try {
			Class clazz = Class.forName(name);
			return clazz.getAnnotations();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static String getTableNameFromEntityName(String entityName) {
		try {
		Annotation[] entityAnnos = AnnotationUtil
				.getAnnotationsByEntityName(entityName);
		if (entityAnnos != null) {
			for (int i = 0; i < entityAnnos.length; i++) {
				if (entityAnnos[i].annotationType().equals(
						javax.persistence.Table.class)) {
					javax.persistence.Table ta = Class.forName(entityName).getAnnotation(javax.persistence.Table.class);
					return ta!=null?ta.name():null;
				}
			}
		}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return null;
		
	}
	
	
	public static Map<String,String> getAllPropertysFromEntityName(String entityName) {
		Map<String,String>  map=new HashMap<String,String>();
		try {
		  Class clazz=Class.forName(entityName);
		  Field[] fs=clazz.getDeclaredFields();
		  if(fs!=null&&fs.length>0){
			  for(Field f:fs){
				  if(f.isAnnotationPresent(ApiModelProperty.class)){
					  String propertyName=f.getName();
					  map.put(propertyName, convertToFieldName(propertyName));
				  }  
			  }
			  
		  }
		  
		  
		  Class superClazz=clazz.getSuperclass();
		  if(superClazz!=null){
			  Field[] spFs=superClazz.getDeclaredFields();
			  if(spFs!=null&&spFs.length>0){
				  for(Field f:spFs){
					  if(f.isAnnotationPresent(ApiModelProperty.class)){
						  String propertyName=f.getName();
						  map.put(propertyName, convertToFieldName(propertyName));
					  }  
				  }
				  
			  }
		  }
		  
		  
		  
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} 
		return map;
		
	}
	
	
	public static String convertToFieldName(String propertyName){
		Pattern p=Pattern.compile("[A-Z]");
		Matcher m=p.matcher(propertyName);
		int start=0;
		String columnName="";
		while(m.find()) { 
			  columnName+=propertyName.substring(start,m.start()).toLowerCase()+"_";
		     start=m.start();
		} 
		columnName+=propertyName.substring(start).toLowerCase()+"_";
		
	  columnName=columnName.substring(0,columnName.length()-1);
	  return columnName;
	}
	
	public static String convertToPropName(String fieldName){
		Pattern p=Pattern.compile("_");
		Matcher m=p.matcher(fieldName);
		int end=0;
		String propName="";
		
		while(m.find()) { 
			String cutStr=fieldName.substring(end,m.end()-1);
			propName+=cutStr.substring(0,1).toUpperCase()+cutStr.substring(1);
			end=m.end();
		} 
		String cutStr=fieldName.substring(end);
		propName+=cutStr.substring(0,1).toUpperCase()+cutStr.substring(1);
		propName=propName.substring(0,1).toLowerCase()+propName.substring(1);
	  return propName;
	}
	
	public static String getFiledNameFromPropertyName(String entityName,
			String propertyName) {
		Annotation[] anno = AnnotationUtil
				.getAnnotationsByEntityAndPropertyName(entityName, propertyName);
		try {
		if (anno != null) {
			for (int i = 0; i < anno.length; i++) {
				if (anno[i].annotationType().equals(
						javax.persistence.Column.class)) {
					Class c = Class.forName(entityName);
					
					if (c != null) {
						Method m = c.getMethod(
								"get"
										+ propertyName.substring(0, 1)
												.toUpperCase()
										+ propertyName.substring(1)
												.toLowerCase(), null);
						if (m != null) {
							javax.persistence.Column cola = m
									.getAnnotation(javax.persistence.Column.class);
							return cola!=null?cola.name():null;
						}
					}
				}
			}
		}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}

	public static Annotation[] getAnnotationsByEntityAndPropertyName(
			String entityName, String properyyName) {
		try {
			Class clazz = Class.forName(entityName);

			Field f = clazz.getField(properyyName);
			if (f != null) {
				return f.getAnnotations();
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
	}
	
	public static Object invokeMethodOnObject(Object o, String methodName,
			Object... params) {

		Object ro = null;
		Class[] classes = null;
		try {
			if (params != null && params.length > 0) {
				classes = new Class[params.length];
				for (int i = 0; i < params.length; i++) {
					classes[i] = params[i].getClass();
					
					if(classes[i].getName().equals("java.math.BigInteger")){
						classes[i]=Long.class;
						params[i]=Long.valueOf(String.valueOf(params[i]));	
					}
				}
			}
			Method m = o.getClass().getMethod(methodName, classes);
			ro = m.invoke(o, params);
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SecurityException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return ro;
	}

	
	public  static void main(String[] args){
		String filedName="prop";
		Pattern p=Pattern.compile("_");
		Matcher m=p.matcher(filedName);
		int end=0;
		String propName="";
		
		while(m.find()) { 
			String cutStr=filedName.substring(end,m.end()-1);
			propName+=cutStr.substring(0,1).toUpperCase()+cutStr.substring(1);
			end=m.end();
		} 
		String cutStr=filedName.substring(end);
		propName+=cutStr.substring(0,1).toUpperCase()+cutStr.substring(1);
		propName=propName.substring(0,1).toLowerCase()+propName.substring(1);
		 System.out.println(propName);
	}

}
