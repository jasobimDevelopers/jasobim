package cn.jpush.api;

import java.util.Map;
import java.util.Set;

import cn.jpush.api.push.CIDResult;
import com.google.gson.JsonObject;

import cn.jiguang.common.ClientConfig;
import cn.jiguang.common.TimeUnit;
import cn.jiguang.common.Week;
import cn.jiguang.common.utils.Preconditions;
import cn.jiguang.common.connection.HttpProxy;
import cn.jiguang.common.resp.APIConnectionException;
import cn.jiguang.common.resp.APIRequestException;
import cn.jiguang.common.resp.BooleanResult;
import cn.jiguang.common.resp.DefaultResult;
import cn.jpush.api.push.PushClient;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.SMS;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.IosAlert;
import cn.jpush.api.push.model.notification.Notification;


/**
 * The global entrance of JPush API library.
 */
public class JPushClient {
    private final PushClient _pushClient;
	
	
	/**
	 * Create a JPush Client.
	 * 
	 * @param masterSecret API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 */
	public JPushClient(String masterSecret, String appKey) {
	    _pushClient = new PushClient(masterSecret, appKey);
	}

    /**
     * Create a JPush Client by custom Client configuration.
     *
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param conf The client configuration. Can use ClientConfig.getInstance() as default.
     */
    public JPushClient(String masterSecret, String appKey, HttpProxy proxy, ClientConfig conf) {
        _pushClient = new PushClient(masterSecret, appKey, proxy, conf);
    }

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes The max retry times.
     */
    @Deprecated
	public JPushClient(String masterSecret, String appKey, int maxRetryTimes) {
        _pushClient = new PushClient(masterSecret, appKey, maxRetryTimes);
	}

    /**
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes The max retry times.
     * @param proxy The proxy, if there is no proxy, should be null.
     */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy) {
        _pushClient = new PushClient(masterSecret, appKey, maxRetryTimes, proxy);
    }
    
    /**
     * Create a JPush Client by custom Client configuration.
     *
     * If you are using JPush privacy cloud, maybe this constructor is what you needed.
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setMaxRetryTimes} instead of this constructor.
     *
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes Client request retry times.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param conf The client configuration. Can use ClientConfig.getInstance() as default.
     */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy, ClientConfig conf) {
        conf.setMaxRetryTimes(maxRetryTimes);

        _pushClient = new PushClient(masterSecret, appKey, proxy, conf);
    }

    /**
     * Create a JPush Client by custom Client configuration with global settings.
     *
     * If you are using JPush privacy cloud, and you want different settings from default globally,
     * maybe this constructor is what you needed.
     * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setGlobalPushSetting} instead of this constructor.
     *
     * @param masterSecret API access secret of the appKey.
     * @param appKey The KEY of one application on JPush.
     * @param maxRetryTimes Client request retry times.
     * @param proxy The proxy, if there is no proxy, should be null.
     * @param conf The client configuration. Can use ClientConfig.getInstance() as default.
     * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
     * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
     */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, int maxRetryTimes, HttpProxy proxy, ClientConfig conf,
                       boolean apnsProduction, long timeToLive) {
        conf.setMaxRetryTimes(maxRetryTimes);
        conf.setApnsProduction(apnsProduction);
        conf.setTimeToLive(timeToLive);
        _pushClient = new PushClient(masterSecret, appKey, proxy, conf);
      
    }
    
	/**
	 * Create a JPush Client with global settings.
	 * 
	 * If you want different settings from default globally, this constructor is what you needed.
	 * This will be removed in the future. Please use ClientConfig{jiguang-common cn.jiguang.common.ClientConfig#setGlobalPushSetting} instead of this constructor.
     *
	 * @param masterSecret API access secret of the appKey.
	 * @param appKey The KEY of one application on JPush.
	 * @param apnsProduction Global APNs environment setting. It will override PushPayload Options.
	 * @param timeToLive Global time_to_live setting. It will override PushPayload Options.
	 */
    @Deprecated
    public JPushClient(String masterSecret, String appKey, boolean apnsProduction, long timeToLive) {
        ClientConfig conf = ClientConfig.getInstance();
        conf.setApnsProduction(apnsProduction);
        conf.setTimeToLive(timeToLive);
        _pushClient = new PushClient(masterSecret, appKey);
       
    }

    public PushClient getPushClient() {
        return _pushClient;
    }

    // ----------------------------- Push API

    /**
     * Send a push with PushPayload object.
     * 
     * @param pushPayload payload object of a push. 
     * @return PushResult The result object of a Push. Can be printed to a JSON.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
	public PushResult sendPush(PushPayload pushPayload) throws APIConnectionException, APIRequestException {
	    return _pushClient.sendPush(pushPayload);
	}
	
	/**
	 * Send a push with JSON string.
	 * 
	 * You can send a push JSON string directly with this method.
	 * 
	 * Attention: globally settings cannot be affect this type of Push.
     * 
     * @param  payloadString payload of a push.
     * @return PushResult. Can be printed to a JSON.
	 * @throws APIConnectionException if a remote or network exception occurs.
	 * @throws APIRequestException if a request exception occurs.
	 */
    public PushResult sendPush(String payloadString) throws APIConnectionException, APIRequestException {
        return _pushClient.sendPush(payloadString);
    }
    
    /**
     * Validate a push action, but do NOT send it actually.
     * 
     * @param payload payload of a push.
     * @return PushResult. Can be printed to a JSON.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendPushValidate(PushPayload payload) throws APIConnectionException, APIRequestException {
    	return _pushClient.sendPushValidate(payload);
    }

    public PushResult sendPushValidate(String payloadString) throws APIConnectionException, APIRequestException {
    	return _pushClient.sendPushValidate(payloadString);
    }

    /**
     * Get cid list, the data form of cid is appKey-uuid.
     * @param count the count of cid list, from 1 to 1000. default is 1.
     * @param type default is push, option: schedule
     * @return CIDResult, an array of cid
     * @throws APIConnectionException connect exception
     * @throws APIRequestException request exception
     */
    public CIDResult getCidList(int count, String type) throws APIConnectionException, APIRequestException {
        return _pushClient.getCidList(count, type);
    }

    
    // ------------------------------- Report API

    /**
     * Get received report. 
     * 
     * @param msgIds 100 msgids to batch getting is supported.
     * @return ReceivedResult. Can be printed to JSON.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    
    
   
    
    // ------------------------------ Shortcuts - notification

    public PushResult sendNotificationAll(String alert) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.alertAll(alert);
        return _pushClient.sendPush(payload);
    }

    /**
     * Send a notification to all.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @return push result
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendNotificationAll(String alert, SMS sms) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.alertAll(alert, sms);
        return _pushClient.sendPush(payload);
    }

    public PushResult sendAndroidNotificationWithAlias(String title, String alert, 
            Map<String, String> extras, String... alias) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(alert, title, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send a notification to Android with alias.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The notification title.
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra parameter.
     * @param alias The users' alias.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendAndroidNotificationWithAlias(String title, String alert, SMS sms,
                                                       Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.android(alert, title, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    public PushResult sendAndroidNotificationWithRegistrationID(String title, String alert, 
            Map<String, String> extras, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.android(alert, title, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send a notification to Android with RegistrationID.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The notification title.
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra parameter.
     * @param registrationID The registration id generated by JPush.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendAndroidNotificationWithRegistrationID(String title, String alert, SMS sms,
                                                                Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.android(alert, title, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithAlias(String alert,
            Map<String, String> extras, String... alias) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send a notification to iOS with alias.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra parameter.
     * @param alias The users' alias.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(String alert, SMS sms,
                                                   Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with alias.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     *
     * @param alert The wrapper of APNs alert.
     * @param extras The extra params.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(IosAlert alert,
                                                   Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with alias.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The wrapper of APNs alert.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(IosAlert alert, SMS sms,
                                                   Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with alias.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     *
     * @param alert The JSON object of APNs alert.
     * @param extras The extra params.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(JsonObject alert,
                                                   Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with alias.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The JSON object of APNs alert.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithAlias(JsonObject alert, SMS sms,
                                                   Map<String, String> extras, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    public PushResult sendIosNotificationWithRegistrationID(String alert, 
            Map<String, String> extras, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The notification content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param registrationID The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(String alert, SMS sms,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     *
     * @param alert The wrapper of APNs alert.
     * @param extras The extra params.
     * @param registrationID The registration ids.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(IosAlert alert,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The wrapper of APNs alert.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param registrationID The registration ids.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(IosAlert alert, SMS sms,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     *
     * @param alert The wrapper of APNs alert.
     * @param extras The extra params.
     * @param registrationID The registration ids.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(JsonObject alert,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS notification with registrationIds.
     * If you want to send alert as a Json object, maybe this method is what you needed.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param alert The JSON object of APNs alert.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param extras The extra params.
     * @param registrationID The registration ids.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosNotificationWithRegistrationID(JsonObject alert, SMS sms,
                                                            Map<String, String> extras, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setNotification(Notification.ios(alert, extras))
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    
    // ---------------------- shortcuts - message

    public PushResult sendMessageAll(String msgContent) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.messageAll(msgContent);
        return _pushClient.sendPush(payload);
    }

    /**
     * Send a message to all
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendMessageAll(String msgContent, SMS sms) throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.messageAll(msgContent, sms);
        return _pushClient.sendPush(payload);
    }

    public PushResult sendAndroidMessageWithAlias(String title, String msgContent, String... alias) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an Android message with alias.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendAndroidMessageWithAlias(String title, String msgContent, SMS sms, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    public PushResult sendAndroidMessageWithRegistrationID(String title, String msgContent, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an Android message with registration id.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param registrationID The registration id list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendAndroidMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.android())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    public PushResult sendIosMessageWithAlias(String title, String msgContent, String... alias) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS message with alias.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param alias The alias list.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosMessageWithAlias(String title, String msgContent, SMS sms, String... alias)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.alias(alias))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    public PushResult sendIosMessageWithRegistrationID(String title, String msgContent, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send an iOS message with registration id.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param registrationID The registrationIds generated by JPush.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendIosMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.ios())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }

    public PushResult sendMessageWithRegistrationID(String title, String msgContent, String... registrationID) 
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .build();
        return _pushClient.sendPush(payload);
    }

    /**
     * Send a message with registrationIds.
     * If it doesn't received within the delay time,JPush will send a SMS to the corresponding users.
     *
     * @param title The message title.
     * @param msgContent The message content.
     * @param sms The SMS content and delay time. If null, sms doesn't work, no effect on Push feature.
     * @param registrationID The registrationIds generated by JPush.
     * @return push result.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    public PushResult sendMessageWithRegistrationID(String title, String msgContent, SMS sms, String... registrationID)
            throws APIConnectionException, APIRequestException {
        PushPayload payload = PushPayload.newBuilder()
                .setPlatform(Platform.all())
                .setAudience(Audience.registrationId(registrationID))
                .setMessage(Message.newBuilder()
                        .setTitle(title)
                        .setMsgContent(msgContent)
                        .build())
                .setSMS(sms)
                .build();
        return _pushClient.sendPush(payload);
    }


    
    // ----------------------- Device
    
   

    // ----------------------- Schedule

    /**
     * Create a single schedule.
     * @param name The schedule name.
     * @param time The push time, format is 'yyyy-MM-dd HH:mm:ss'
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    

    /**
     * Create a daily schedule push everyday.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    

    /**
     * Create a daily schedule push with a custom frequency.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'
     * @param frequency The custom frequency.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   

    /**
     * Create a weekly schedule push every week at the appointed days.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'
     * @param days The appointed days.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    

    /**
     * Create a weekly schedule push with a custom frequency at the appointed days.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'.
     * @param frequency The custom frequency.
     * @param days The appointed days.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    
    /**
     * Create a monthly schedule push every month at the appointed days.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'.
     * @param points The appointed days.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   

    /**
     * Create a monthly schedule push with a custom frequency at the appointed days.
     * @param name The schedule name.
     * @param start The schedule comes into effect date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param end The schedule expiration date, format 'yyyy-MM-dd HH:mm:ss'.
     * @param time The push time, format 'HH:mm:ss'.
     * @param frequency The custom frequency.
     * @param points The appointed days.
     * @param push The push payload.
     * @return The created scheduleResult instance.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   

    /**
     * Get the schedule information by the schedule id.
     * @param scheduleId The schedule id.
     * @return The schedule information.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   

    /**
     * Get the schedule list size and the first page.
     * @return The schedule list size and the first page.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    

    /**
     * Get the schedule list by the page.
     * @param page The page to search.
     * @return The schedule list of the appointed page.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   

    /**
     * Update the schedule name
     * @param scheduleId The schedule id.
     * @param name The new name.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    

    /**
     * Enable the schedule.
     * @param scheduleId The schedule id.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    
    /**
     * Disable the schedule.
     * @param scheduleId The schedule id.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   

    /**
     * Update the trigger of the schedule.
     * @param scheduleId The schedule id.
     * @param trigger The new trigger.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   

    /**
     * Update the push content of the schedule.
     * @param scheduleId The schedule id.
     * @param push The new push payload.
     * @return The schedule information after updated.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
    

    /**
     * Update a schedule by the id.
     * @param scheduleId The schedule id to update.
     * @param payload The new schedule payload.
     * @return The new schedule information.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   
    /**
     * Delete a schedule by id.
     * @param scheduleId The schedule id.
     * @throws APIConnectionException if a remote or network exception occurs.
     * @throws APIRequestException if a request exception occurs.
     */
   

 

    public void close() {
        _pushClient.close();
    }
}

