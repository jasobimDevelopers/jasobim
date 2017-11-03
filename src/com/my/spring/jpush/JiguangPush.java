package com.my.spring.jpush;


import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import cn.jiguang.common.ClientConfig;
import cn.jpush.api.JPushClient;
import cn.jpush.api.common.APIConnectionException;
import cn.jpush.api.common.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Options;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.audience.AudienceTarget;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;


/**
 * java后台极光推送方式二：使用Java SDK
 */
public class JiguangPush {
	protected static final Logger LOG = LoggerFactory.getLogger(JiguangPush.class);

    public static final String TITLE = "mk推送测试";
    public static final String ALERT = "消息提示";
    public static final String MSG_CONTENT = "推送内容aaaaaaaaaaaaaaaaaaa";
    public static final String TAG = "tag_api";
    public static final String ALAIS = "mk";
    public static JPushClient jPushClient = null;
    public static Map<String , String> map = new HashMap<String , String>();   
    public static void main(String[] args) {
    	testSendPush("1674ebe520e502d71e4de511","f6b600502ebe3aca7bc48507");
    }
    

    public static void  testSendPush(String appkey, String masterSecret) {

        jPushClient = new JPushClient(masterSecret, appkey);
      
           /* TagAliasResult results;
			try {
				results = jPushClient.getDeviceTagAlias("120");
				LOG.info(results.alias);
	            LOG.info(results.tags.toString());
			} catch (cn.jpush.api.common.resp.APIConnectionException e) {
				// TODO Auto-generated catch block
				 LOG.error("Connection error. Should retry later. ", e);
			} catch (cn.jpush.api.common.resp.APIRequestException e) {
				// TODO Auto-generated catch block
			     LOG.error("Error response from JPush server. Should review and fix it. ", e);
		            LOG.info("HTTP Status: " + e.getStatus());
		            LOG.info("Error Code: " + e.getErrorCode());
		            LOG.info("Error Message: " + e.getErrorMessage());
			}
*/
            
  

        //PushPayload payload = buildPushObject_all_all_alert();
        PushPayload payload = buildPushObject_ios_audienceMore_messageWithExtras();
        //PushPayload payload = buildPushObject_android_tag_alertWithTitle();
            
		
			System.out.println(payload.toString());
			PushResult result;
			try {
				result = jPushClient.sendPush(payload);
				System.out.println(result + "...............");
		        LOG.info("GOT RESULT----" + result);
			} catch (APIConnectionException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (APIRequestException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		
           
    }
    ////可以
    public static PushPayload buildPushObject_all_all_alert() {
        return PushPayload.alertAll(ALERT);
    }
    /*public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())//设置接受的平台
                .setAudience(Audience.all())//Audience设置为all，说明采用广播方式推送，所有用户都可以接收到,为了推送到特定的某一个人 就写了ALAIS测试用, 可能有人会问 这个ALAIS哪里来呢? 是由客户端那边设置的 jpush下载Android demo 在高级功能里可以修改这个ALAIS
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Title").build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())

                .build();
    }*/
  
    public static PushPayload buildPushObject_android_tag_alertWithTitle() {
    	map.put("key1", "xyx110");
    	map.put("key2", "xyx002");
        return PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.tag("IOS_ALL22"))
                .setNotification(Notification.android(ALERT, "ceshi", map))
                .build();
    }


    public static PushPayload buildPushObject_android_and_ios() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.android_ios())
                .setAudience(Audience.tag("IOS_ALL22"))
                .setNotification(Notification.newBuilder()
                        .setAlert("alert content")
                        .addPlatformNotification(AndroidNotification.newBuilder()
                                .setTitle("Android Title").build())
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setBadge(1)
                                .addExtra("extra_key", "extra_value").build())
                        .build())
                .build();
    }

    public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.tag_and("tag1", "tag_all"))
                .setNotification(Notification.newBuilder()
                        .addPlatformNotification(IosNotification.newBuilder()
                                .setAlert(ALERT)
                                .setBadge(5)
                                .setSound("happy")
                                .addExtra("from", "JPush")
                                .build())
                        .build())
                 .setMessage(Message.content(MSG_CONTENT))
                 .setOptions(Options.newBuilder()
                         .setApnsProduction(true)
                         .build())
                 .build();
    }

    public static PushPayload buildPushObject_all_alias_alert() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.alias("1"))
                .setNotification(Notification.alert(ALERT))
                .build();
    }
    public static PushPayload buildPushObject_ios_audienceMore_messageWithExtras() {
        return PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.newBuilder()
                        .addAudienceTarget(AudienceTarget.tag("IOS_ALL22", "IOS_ALL13"))
                        .addAudienceTarget(AudienceTarget.alias("110", "120","33"))
                        .build())
                .setMessage(Message.newBuilder()
                        .setMsgContent(MSG_CONTENT)
                        .addExtra("from", "JPush")
                        .build())
                .build();
    }

}