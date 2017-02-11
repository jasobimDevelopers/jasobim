package test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheTest {
	//CacheManager manager = new CacheManager("src/ehcache.xml");//实例模式  
	//以默认配置创建一个CacheManager单例  
	CacheManager cacheManager = CacheManager.create();  
	//CacheManager.create();//单例模式，默认读取类路径下的ehcache.xml作为配置文件  
	Cache cache = CacheManager.getInstance().getCache("staticResourceCache"); 
	//staticResourceCache在ehcache.xml中提前定义了
	public class Ways{
		//Cache.get(Object key);
		//Cache.put(new Element("keyone", "test"));
		//Cache.remove(Object key);
	}
}
