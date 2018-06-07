package com.my.spring.sso.hmx.client;

import java.util.MissingResourceException;
import java.util.ResourceBundle;

/**
 * OAuth2消息
 * 
 * @author zhixing.ouyang
 * 
 */
public class OAuth2Messages {

    private static final String BUNDLE_NAME = "com.my.spring.sso.hmx.client.oAuth2Messages";

    private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
            .getBundle(BUNDLE_NAME);

    private OAuth2Messages() {
    }

    public static String getString(String key) {
        try {
            return RESOURCE_BUNDLE.getString(key);
        } catch (MissingResourceException e) {
            return '!' + key + '!';
        }
    }
}
