package com.my.spring.utils;


import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import com.my.spring.model.User;

/**
 * Created by tian on 16/9/27.
 */
public class SessionManager {
    private static int KEY_COUNT = 0;
    private static HashMap<String, User> USER_SESSION_MAP = new HashMap<String, User>();

    public static String newSession(User user) {

        String sessionKey = UUIDGenerator.getCode("SK");

        ++KEY_COUNT;
        if (KEY_COUNT >= 10000000) {
            KEY_COUNT = 0;
        }

        USER_SESSION_MAP.put(sessionKey, user);
//        log.info(
//                "Session Updated! Key:" + sessionKey +
//                        " UserId:" + user.getId() +
//                        " UserName:" + user.getName());
//        System.out.println(USER_SESSION_MAP);
        return sessionKey;
    }

    public static User getSession(String key) {
        return USER_SESSION_MAP.get(key);
    }
    public static String getSessionByUserID(Long userId){
        Set<String> set = USER_SESSION_MAP.keySet();
        for(String key :set){
            if(USER_SESSION_MAP.get(key).getId()==userId)
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