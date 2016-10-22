package base.util;

import java.io.StringReader;

import com.google.gson.stream.JsonReader;

/**
 * 百度地图处理工具类
 * @author zj
 *
 */
public class BaiduMap {
	
	/**
	 * 通过地址反解析为经纬度（百度）
	 * @param address
	 * @return MapLocationEntity
	 */
	public static MapLocationEntity getLocation(String address) {
		String url = "http://api.map.baidu.com/geocoder/v2/?ak=230a56babd38d0a1b752286d2fffb52d&output=json&address=" + address;
		
		String lat = null;
		String lng = null;
		try {
			String json = HttpUtil.sendGet(url);
			
			JsonReader reader = new JsonReader(new StringReader(json));
			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				
				if("result".equals(name)) {
					reader.beginObject();
					while (reader.hasNext()) {
						String location = reader.nextName();
						if("location".equals(location)) {
							reader.beginObject();
							while (reader.hasNext()) {
								String tag = reader.nextName();
								if("lng".equals(tag)) {
									lng = reader.nextString();
								}else if("lat".equals(tag)) {
									lat = reader.nextString();
								}else {
									reader.skipValue();
								}
							}
							reader.endObject();
						}else {
							reader.skipValue();
						}
					}
					reader.endObject();
				}else {
					reader.skipValue();
				}
			}
			reader.endObject();
			reader.close();
		} catch (Exception e) {
			return null;
		}
		
		if(isNull(lng) || isNull(lat)) {
			return null;
		}
		MapLocationEntity mle = new MapLocationEntity(Double.valueOf(lng), Double.valueOf(lat));
		return mle;
	}
	
	private static boolean isNull(String s) {
		if(s == null || "".equals(s)) {
			return true;
		}
		return false;
	};
	
	public static void main(String[] args) {
		MapLocationEntity d = getLocation("陕西省西安市雁塔区旺座现代城");
		System.out.println(d.getLon()+"--"+d.getLat());
	}
}