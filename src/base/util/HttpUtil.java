package base.util;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

/**
 * https请求处理类
 * @author zj
 *
 */
public class HttpUtil {
	
	/**
	 * 发送HTTP POST 请求
	 * @param reqUrl
	 * @param params
	 * @throws Exception
	 */
	public static String sendPost(String reqUrl, Map<String, String> params) throws Exception {
		return httpWithParams(reqUrl, "POST", params);
	}
	
	
	public static String sendPost(String reqUrl, byte[] data) throws Exception {
		return http(reqUrl, "POST", data);
	}
	
	/**
	 * 发送HTTP GET 请求
	 * @param reqUrl
	 * @throws Exception
	 */
	public static String sendGet(String reqUrl) throws Exception {
		return httpWithParams(reqUrl, "GET", null);
	}
	
	/**
	 * 发送HTTP 请求
	 * @param reqUrl
	 * @param method
	 * @param params
	 * @return
	 * @throws Exception
	 */
	private static String httpWithParams(String reqUrl, String method, Map<String, String> params) throws Exception {
		//HTTP URL链接
		HttpURLConnection urlConn = null;
		
		URL url = new URL(reqUrl);
		// 打开连接
		urlConn = (HttpURLConnection) url.openConnection();
		
		urlConn.setRequestMethod(method);//设置访问方式POST/GET
		urlConn.setDoOutput(true);//设置是否向connection输出，如果是POST请求，参数要放在http正文内，因此需要设为true
		urlConn.setDoInput(true);//读取链接中的表单，设置为true
		urlConn.setUseCaches(false);//请求不能使用缓存
		urlConn.setInstanceFollowRedirects(true);//设置成员函数，仅作用于当前函数
		
		/*//添加HTTP请求head信息
		if (propertys != null) {
			for (String key : propertys.keySet()) {
				urlConn.addRequestProperty(key, propertys.get(key));
			}
		}*/
		
		//POST请求参数时，设置正文
		if("POST".equals(method) && params != null) {
//			StringBuffer param = new StringBuffer();
//			
//			for (String key : params.keySet()) {
//				param.append("&").append(key).append("=").append(params.get(key));
//			}
//			
//			//发送正文信息
//			DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
//			out.writeBytes(param.toString());
//			out.flush();
//			out.close();
			
			doPost(urlConn,params);
		}
		
		//处理返回信息
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));//设置编码,否则中文乱码
		
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null){
			sb.append(line);
			line = reader.readLine();
		}
		
		//关闭流和链接
		reader.close();
		urlConn.disconnect();
		
		return sb.toString();
	}
	
	
	
	private static String http(String reqUrl, String method, byte[] data) throws Exception {
		//HTTP URL链接
		HttpURLConnection urlConn = null;
		
		URL url = new URL(reqUrl);
		// 打开连接
		urlConn = (HttpURLConnection) url.openConnection();
		
		urlConn.setRequestMethod(method);//设置访问方式POST/GET
		urlConn.setDoOutput(true);//设置是否向connection输出，如果是POST请求，参数要放在http正文内，因此需要设为true
		urlConn.setDoInput(true);//读取链接中的表单，设置为true
		urlConn.setUseCaches(false);//请求不能使用缓存
		urlConn.setInstanceFollowRedirects(true);//设置成员函数，仅作用于当前函数
		
		/*//添加HTTP请求head信息
		if (propertys != null) {
			for (String key : propertys.keySet()) {
				urlConn.addRequestProperty(key, propertys.get(key));
			}
		}*/
		
		//POST请求参数时，设置正文
		if("POST".equals(method) ) {
//			StringBuffer param = new StringBuffer();
//			
//			for (String key : params.keySet()) {
//				param.append("&").append(key).append("=").append(params.get(key));
//			}
//			
//			//发送正文信息
//			DataOutputStream out = new DataOutputStream(urlConn.getOutputStream());
//			out.writeBytes(param.toString());
//			out.flush();
//			out.close();
			
			doPost(urlConn,data);
		}
		
		//处理返回信息
		BufferedReader reader = new BufferedReader(new InputStreamReader(urlConn.getInputStream(), "UTF-8"));//设置编码,否则中文乱码
		
		StringBuilder sb = new StringBuilder();
		String line = reader.readLine();
		while (line != null){
			sb.append(line);
			line = reader.readLine();
		}
		
		//关闭流和链接
		reader.close();
		urlConn.disconnect();
		
		return sb.toString();
	}
	
	
   private static void doPost(HttpURLConnection urlCon,Map<String, String> params){
	   StringBuffer param = new StringBuffer();
		
		for (String key : params.keySet()) {
			param.append("&").append(key).append("=").append(params.get(key));
		}
		
		//发送正文信息
		DataOutputStream out;
		try {
			out = new DataOutputStream(urlCon.getOutputStream());
			out.writeBytes(param.toString());
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
   }
   
   private static void doPost(HttpURLConnection urlCon,byte[] data){
	   //发送正文信息
		DataOutputStream out;
		try {
			out = new DataOutputStream(urlCon.getOutputStream());
			out.writeBytes(new String(data));
			out.flush();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
   }
	
	
	
	
}