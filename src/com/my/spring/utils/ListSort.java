package com.my.spring.utils;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.List;

public class ListSort {
	public static class UserBean {
        private String id;
        private String birthday;

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getBirthday() {
            return birthday;
        }

        public void setBirthday(String birthday) {
            this.birthday = birthday;
        }
    }

    public static void main(String[] args) {
        List<UserBean> list = new ArrayList<UserBean>();
        UserListGenerate(list);
        for(UserBean us:list){
        	 System.out.println("排序前："+us.getBirthday());
        }
       
        
        ListSort(list);
        for(UserBean us:list){
       	 System.out.println("排序后："+us.getBirthday());
       }
    }

    private static void UserListGenerate(List<UserBean> list) {
        UserBean user1 = new UserBean();
        UserBean user2 = new UserBean();
        UserBean user3 = new UserBean();
        UserBean user4 = new UserBean();
        user1.setId("zhagnsan");
        user1.setBirthday("1980-12-07");

        user2.setId("lisi");
        user2.setBirthday("1981-12-01");

        user3.setId("wangwu");
        user3.setBirthday("1980-04-07");
        
        user4.setId("likui");
        user4.setBirthday("1980-05-01");

        list.add(user1);
        list.add(user2);
        list.add(user3);
        list.add(user4);
    }

    private static void ListSort(List<UserBean> list) {
        Collections.sort(list, new Comparator<UserBean>() {
            @Override
            public int compare(UserBean o1, UserBean o2) {
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
                try {
                    Date dt1 = format.parse(o1.getBirthday());
                    Date dt2 = format.parse(o2.getBirthday());
                    if (dt1.getTime() < dt2.getTime()) {
                        return 1;
                    } else if (dt1.getTime() > dt2.getTime()) {
                        return -1;
                    } else {
                        return 0;
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
                return 0;
            }
        });
    }
}
