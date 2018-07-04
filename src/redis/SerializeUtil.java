package redis;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.HashMap;

import redis.mode.Person;
import redis.clients.jedis.Jedis;

public class SerializeUtil {
	
	
	 public static void main(String [] args){
	        Jedis jedis = new Jedis("localhost");
	        //String keys = "name";
	        // 删数据
	        //jedis.del(keys);
	        // 存数据
	       // jedis.set(keys, "zy");
	        // 取数据
	       // String value = jedis.get(keys);
	       // System.out.println(value);
	        
	        //存对象
	        Person p=new Person();  //peson类记得实现序列化接口 Serializable
	        Person p1=new Person(); 
	        HashMap hm = new HashMap();
	        p.setUserName("姚波");
	        p.setAge(25);
	        p.setId((long)1);
	        p.setSex(1);
	        p1.setAge(24);
	        p1.setUserName("姚辉");
	        p1.setSex(0);
	        p1.setId((long)2);
	        hm.put("person0", p);
	        hm.put("person1", p1);
	        jedis.set("person_hm".getBytes(), serialize(hm));
	        byte[] byt=jedis.get("person_hm".getBytes());
	        Object obj=unserizlize(byt);
	        HashMap getResult = (HashMap) obj;
	        Person person1=(Person) getResult.get("person0");
	        Person person2=(Person) getResult.get("person1");
	        System.out.println(person1.getUserName());
	        System.out.println(person2.getUserName());
	        /*if(obj instanceof Person){
	            System.out.println( ((Person) obj).getUserName());
	        }*/
	    }
	 //序列化 
	    public static byte [] serialize(Object obj){
	        ObjectOutputStream obi=null;
	        ByteArrayOutputStream bai=null;
	        try {
	            bai=new ByteArrayOutputStream();
	            obi=new ObjectOutputStream(bai);
	            obi.writeObject(obj);
	            byte[] byt=bai.toByteArray();
	            return byt;
	        } catch (IOException e) {
	            e.printStackTrace();
	        }
	        return null;
	    }
	    
	    //反序列化
	    public static Object unserizlize(byte[] byt){
	        ObjectInputStream oii=null;
	        ByteArrayInputStream bis=null;
	        bis=new ByteArrayInputStream(byt);
	        try {
	            oii=new ObjectInputStream(bis);
	            Object obj=oii.readObject();
	            return obj;
	        } catch (Exception e) {
	            
	            e.printStackTrace();
	        }
	    
	        
	        return null;
	    }
}
