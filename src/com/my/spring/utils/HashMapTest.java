package com.my.spring.utils;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.my.spring.model.User;

public class HashMapTest {
	private static HashMap<String, User> USER_SESSION_MAP = new HashMap<String, User>();
	private static int KEY_COUNT = 0;
	private static int KEY_WECHAT_COUNT = 0;
	private static HashMap<String, String> WECHAT_SESSION_MAP = new HashMap<String, String>();
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Date date = new Date();
		User user1 = new User();
		User user2 = new User();
		user1.setId((long)12);
		user1.setDepartmentId((long)13);
		user1.setEmail("123");
		user1.setMenuItemList("1,3,5");
		user1.setPassword("123456");
		user1.setUserName("xyx");
		user1.setRoleId((long)1);
		user1.setRealName("xyx");
		user1.setRegisterDate(date);
		user1.setSystemId(0);//安卓端
		user1.setSystemType(0);
		user1.setTeamId((long)2);
		user1.setTel("123");
		user1.setUserIcon((long)123);
		user1.setUserType(0);
		user1.setWorkName("管理员");
		/////////////////////
		user2.setId((long)12);
		user2.setDepartmentId((long)13);
		user2.setEmail("123");
		user2.setMenuItemList("1,3,5");
		user2.setPassword("123456");
		user2.setUserName("xyx");
		user2.setRoleId((long)1);
		user2.setRealName("xyx");
		user2.setRegisterDate(date);
		user2.setSystemId(1);//苹果端
		user2.setSystemType(0);
		user2.setTeamId((long)2);
		user2.setTel("123");
		user2.setUserIcon((long)123);
		user2.setUserType(0);
		user2.setWorkName("管理员");
		String token1=newSession(user1);
		System.out.println(token1);
		String token2=newSession(user2);
		System.out.println(token2);
		
	}
	
    public static String newSession(User user) {
        String sessionKey = UUIDGenerator.getCode("SK")+user.getSystemId();
        ++KEY_COUNT;
        if (KEY_COUNT >= 10000000) {
            KEY_COUNT = 0;
        }
        USER_SESSION_MAP.put(sessionKey, user);       
        return sessionKey;
    }
    public static String newWechatSession(String dates,String wechatUrl) {
        String sessionKey = dates;
        ++KEY_WECHAT_COUNT;
        if (KEY_WECHAT_COUNT >= 10000000) {
        	KEY_WECHAT_COUNT = 0;
        }
        WECHAT_SESSION_MAP.put(sessionKey, wechatUrl);       
        return sessionKey;
    }
    public static User getSession(String key) {
        return USER_SESSION_MAP.get(key);
    }
    public static String getWechatSession(String key) {
        return WECHAT_SESSION_MAP.get(key);
    }
    public static String getSessionByUserID(Long userId){
        Set<String> set = USER_SESSION_MAP.keySet();
        for(String key :set){
            if(USER_SESSION_MAP.get(key).getId()==userId)
                return key;
        }
        return null;
    }
    public static String getSessionByUserIDAndSystem(Long userId,Integer system){
        Set<String> set = USER_SESSION_MAP.keySet();
        for(String key :set){
            if(USER_SESSION_MAP.get(key).getId()==userId && USER_SESSION_MAP.get(key).getSystemId()==system)
                return key;
        }
        return null;
    }
    public static void removeSession(String key) {
        if (USER_SESSION_MAP.containsKey(key)) {
            //log.info("Session Destroyed! Key:" + key);
            USER_SESSION_MAP.remove(key);
        }
    }
    public static void removeWechatSession(String key) {
        if (WECHAT_SESSION_MAP.containsKey(key)) {
            //log.info("Session Destroyed! Key:" + key);
        	WECHAT_SESSION_MAP.remove(key);
        }
    }
    /**
     * 删除某用户的Session
     * @param userId
     */
    public static void removeSessionByUserId(Long userId) {
        Iterator<Map.Entry<String, User>> iter = USER_SESSION_MAP.entrySet().iterator();
        while (iter.hasNext()) {
            Map.Entry<String, User> entry =  iter.next();
            String key = entry.getKey();
            User value = entry.getValue();
            if (value!=null && value.getId() == userId) {
                removeSession(key);
                break;
            }

        }
    }
}
