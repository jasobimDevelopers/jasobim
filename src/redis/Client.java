package redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;
import redis.clients.jedis.JedisPoolConfig;

public class Client {

    /**
     * redis 服务器启动：D:\redis-2.0.2>redis-server.exe redis.conf
     * redis 客户端启动：D:\redis-2.0.2>redis-cli.exe -h 192.168.3.22 -p 6379
     * 参考地址    http://www.cnblogs.com/kkgreen/archive/2011/11/09/2243554.html
     * @param args
     */
    public static void main(String[] args) {
    	
   	    JedisPool jedisPool  = new JedisPool(new JedisPoolConfig(),"localhost");
    	RedisTest test = new RedisTest();
    	
    	test.setUp();
    	//test.testList();
        test.testBasicString();
    	/*test.testMap();
    	Jedis jj = jedisPool.getResource();
    	
    	
        // TODO Auto-generated method stub
        //Jedis jj = new  Jedis("localhost");
        jj.set("key1", "I am value 1");
        String ss = jj.get("key1"); 
        
        jj.rename("key1", "key2");
        jj.set("key2", "aaaaaaaaaaaaaaaaaaa");
        System.out.println(ss);
        System.out.println(jj.get("key2"));*/
    	test.destory();
    }
    
}
