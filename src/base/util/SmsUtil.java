package base.util;

import java.io.StringReader;

import com.google.gson.stream.JsonReader;

/**
 * 公司短信处理类
 * @author zj
 *
 */
public class SmsUtil {
	private static final String APPKEY = "68f10bf021d7734e071e07bbf561aa0f1bfc7974f266f71311b9177b177d39d1";
	/**
	 * 发送短信验证码
	 * @param phone
	 * @throws Exception
	 * 返回serial_number
	 */
	public static String sendCaptcha(String phone) throws Exception {
		String url = "http://www.taplinker.com/ws/sendValidMessage?appKey=" + APPKEY + "&phone=" + phone;
		String data = HttpUtil.sendGet(url);
		String result = "";
		if(data != null) {
			JsonReader reader = new JsonReader(new StringReader(data));
			
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if("code".equals(name)) {
					String code = reader.nextString();
					if("202".equals(code)) {
						result = "invalid";
						break;
					}
				}else if("serial_number".equals(name)) {
					result = reader.nextString();
				}else {
					reader.skipValue();
				}
			}
			reader.endObject();
			reader.close();
		}
		
		return result;
	}
	
	/**
	 * 验证短信验证码
	 * @param code
	 * @return
	 * @throws Exception
	 */
	public static boolean validateCaptcha(String code, String serial) throws Exception {
		String url = "http://www.taplinker.com/ws/validMessage?appKey=" + APPKEY + "&serial_number=" + serial + "&valid_code=" + code;
		String data = HttpUtil.sendGet(url);
		
		boolean flag = false;
		if(data != null) {
			JsonReader reader = new JsonReader(new StringReader(data));
			
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if("code".equals(name)) {
					if("200".equals(reader.nextString())) {
						flag = true;
					}
				}else {
					reader.skipValue();
				}
			}
			reader.endObject();
			reader.close();
		}
		
		return flag;
	}
	
	public static void main(String[] args) throws Exception {
		//String as = sendCaptcha("13699021074");
		boolean as = validateCaptcha("4528", "90e80f97-f266-477f-bb2a-4036dfd583ba");
		System.out.println(as);
	}
}
