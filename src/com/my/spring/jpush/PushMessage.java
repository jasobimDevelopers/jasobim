package com.my.spring.jpush;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import org.apache.log4j.Logger;

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

import com.google.common.collect.ImmutableMap;
import com.my.spring.jpush.exception.PushException;

public class PushMessage {
	private static String pushMasterSecret = "f6b600502ebe3aca7bc48507";
	private static String appKey = "1674ebe520e502d71e4de511";
	private static String ALERT = "xyx测试内容";
	private static String TITLE = "xyx测试标题";
	private static boolean jpushProduction = false;
	
	private static final Logger log=Logger.getLogger(PushMessage.class);
	

	
	public static void SendPushMessage(List<String> aliases,String strMessage,ImmutableMap<Object, Object> extras)
	{
		JPushClient jpushClient = new JPushClient(pushMasterSecret,appKey);
		PushPayload payload = buildPushObject_all_all_alert();
		
		try {
            PushResult result = jpushClient.sendPush(payload);
            log.info("Got result - " + result);
        }catch (Exception e) {
            // Connection error, should retry later
        	System.out.println(e.getMessage());
        	log.error("Connection error, should retry later", e);

        }
	}
	
	
	private static PushPayload buildPushObject_ios_audienceMore_messageWithExtras(List<String> aliases,String strMessage,ImmutableMap<Object, Object> extras) {
		PushPayload.Builder pushPayLoadBuild = PushPayload.newBuilder();
		AudienceTarget audience=AudienceTarget.alias(aliases);
		pushPayLoadBuild.setPlatform(Platform.android_ios())
        .setAudience(Audience.newBuilder().addAudienceTarget(audience).build());
		Message.Builder messageBuild = Message.newBuilder();
		messageBuild.setTitle("测试xyx").setMsgContent(strMessage);
		if(extras!=null){
			Iterator<Object> it = extras.keySet().iterator();
			while(it.hasNext()){
				String key = it.next().toString();
				messageBuild.addExtra(key, extras.get(key).toString());
			}
		}
		pushPayLoadBuild.setMessage(messageBuild.build());
		pushPayLoadBuild.setOptions(Options.newBuilder()
                .setApnsProduction(jpushProduction)
                .build());
		return pushPayLoadBuild.build();
    }
	/////可以
	 public static PushPayload buildPushObject_all_all_alert() {
		Date date=new Date();  
	    SimpleDateFormat formatter=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");  
	    String time=formatter.format(date);  
        return PushPayload.alertAll("xyx测试:"+time+"");
	    //return PushPayload.messageAll(ALERT);
	 }
	 //////可以
	 public static PushPayload buildPushObject_android_tag_alertWithTitle() {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.android())
	                .setAudience(Audience.tag("IOS_ALL22"))
	                .setNotification(Notification.ios(ALERT, null))
	                .build();
	 }
	 public static PushPayload buildPushObject_all_alias_alert() {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias("120"))
	                .setNotification(Notification.alert(ALERT))
	                .build();
	 }
	 public static PushPayload buildPushObject_ios_tagAnd_alertWithExtrasAndMessage() {
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.ios())
	                .setAudience(Audience.tag_and("tag1", "tag_all"))
	                .setNotification(Notification.newBuilder()
	                        .addPlatformNotification(IosNotification.newBuilder()
	                                .setAlert(ALERT)
	                                .build())
	                        .build())
	                 .setMessage(Message.content(ALERT))
	                 .setOptions(Options.newBuilder()
	                         .setApnsProduction(true)
	                         .build())
	                 .build();
	 }
	 public static PushPayload buildPushObject_android_and_ios() {  
	        return PushPayload.newBuilder()  
	                .setPlatform(Platform.android_ios())  
	                .setAudience(Audience.tag("tag1"))  
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
	public static void  main(String[] arg){
		
		/*ImmutableMap<Object, Object> extras = ImmutableMap.builder().put("type", "6").put("husername","").put("id",1148).build();
		List<String> sendList = new ArrayList<String>();
		sendList.add("1");
		sendList.add("2");
		SendPushMessage(sendList, "推送测试", extras);*/
		 
		 
		JPushClient jpushClient = new JPushClient(pushMasterSecret, appKey);
		///广播推送
		//PushPayload payload = buildPushObject_all_all_alert();
		///别名推送
		//PushPayload payload = buildPushObject_all_alias_alert();
		///标签推送
		PushPayload payload = buildPushObject_android_tag_alertWithTitle();

		    try {
		        PushResult result = jpushClient.sendPush(payload);
		        log.info("Got result - " + result);

		    } catch (APIConnectionException e) {
		    	System.out.println("Connection error, should retry later"+ e);

		    } catch (APIRequestException e) {
		    	System.out.println("Should review the error, and fix the request"+ e);
		    	System.out.println("HTTP Status: " + e.getStatus());
		    	System.out.println("Error Code: " + e.getErrorCode());
		    	System.out.println("Error Message: " + e.getErrorMessage());
		    }
		/*ImmutableMap<String, String> extras = ImmutableMap.of("a","1","b","2","c","3");
		//extras.entrySet();
		//extras.keySet().add("test1")
		String title="健康计划进度提醒";
	 	String content="你还有制定的计划未完成！";
	 	List<String> allNotFinish = new ArrayList<String>();//需要推送的列表
	 	for(int i=0;i<10;i++){
	 		allNotFinish.add("test"+i);
	 	}
	 	try {
	 		PushNotification.pushBroadCast(content);
			//PushNotification.pushNotificationByAliases(title, content, allNotFinish,extras);
		} catch (PushException e) {
				// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
		
	}
}
