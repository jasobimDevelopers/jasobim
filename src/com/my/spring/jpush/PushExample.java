package com.my.spring.jpush;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.Notification;


public class PushExample {
	private static final String appKey = "1674ebe520e502d71e4de511";
	private static final String masterSecret = "f6b600502ebe3aca7bc48507";


   /**
    *  所有平台，所有设备，内容为 【叼哥你妹！】 的通知
    *  
    *  */
    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll("叼哥你妹！!");
    }

    
    /**
     *  所有平台，推送目标是别名为 "alias1"，通知内容为  【神马都是浮云!】
     *  
     *  */
    public static PushPayload buildPushObject_all_alias_alert(String[] userids,String title) {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(userids))
                .setNotification(Notification.ios(title, null))
                .build();
    }


    
    /**
     *  平台是 Android，目标是 tag 为 "tag1" 的设备，内容是 【这是内容】，并且标题为 【这是标题】。
     *  
     *  */
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag("IOS_ALL22"))
                .setNotification(Notification.ios("这是内容", null))
                .build();
    }

        
    
    //测试主方法
    public static void main(String arg[]){
    	  JPushClient jpushClient = new JPushClient(masterSecret, appKey, 3);
          // For push, all you need do is to build PushPayload object.
          //PushPayload payload =  buildPushObject_android_tag_alertWithTitle();
    	  String[] userids = {"112","33","150"};
    	  PushPayload payload = buildPushObject_all_alias_alert(userids,"别名推送测试");
          try {
              PushResult result = jpushClient.sendPush(payload);
              System.out.println(result);
          } catch (APIConnectionException e) {
            e.printStackTrace();         
          } catch (APIRequestException e) {
              System.out.println("Should review the error, and fix the request"+ e);
              System.out.println("HTTP Status: " + e.getStatus());
              System.out.println("Error Code: " + e.getErrorCode());
              System.out.println("Error Message: " + e.getErrorMessage());
          }


    }


}
