package com.my.spring.sso.hmx.client.util;

import java.util.HashSet;
import java.util.Set;

import com.my.spring.sso.hmx.client.spi.OAuth2SSOFilterSpi;

/**
 * 常量工具类
 * @author zhixing.ouyang
 *
 */
public class ConstantsUtil {

	private static ConstantsUtil s_constantsUtil;
	
	public static final String SESSION_MUTEX_ATTRIBUTE="C2_SESSION.MUTEX";

	/** web.xml配置key:客户端id */
	public static final String SFS_OAUTH2_SSO_CLIENTID = "clientId";
	/** web.xml配置key:客户端安全KEY */
	public static final String SFS_OAUTH2_SSO_CLIENTSECRET = "clientSecret";
	/** web.xml配置key:oauth2统一认证授权服务器地址 */
	public static final String SFS_OAUTH2_SSO_SERVERURL = "serverUrl";
	/** web.xml配置key:oauth2统一认证授权服务器内部地址 */
	public static final String SFS_OAUTH2_SSO_SERVEINNERRURL = "serverInnerUrl";
	/** web.xml配置key:客户端地址 */
	public static final String SFS_OAUTH2_SSO_CLIENTURL = "clientUrl";
	/** web.xml配置key:每次请求客户端与服务器是否同步校验 */
	public static final String SFS_OAUTH2_SSO_SYNVALIDATE = "synValidate";
	/** web.xml配置key:匿名访问的url，以分号分隔 */
	public static final String SFS_OAUTH2_SSO_ANONURL = "anonUrl";
	/** web.xml配置key:OAuth2统一认证授权拦截器spi接口实现类 */
	public static final String SFS_OAUTH2_SSO_OAUTH2SSOFILTERSPI = "oAuth2SSOFilterSpi";
	
	public static final String SFS_OAUTH2_SSO_CREDENTIAL = "oAuth2SSOCredential";

	/** 获取授权码地址 */
	public static final String SFS_OAUTH2_SSO_AUTHORIZEURL = "/connect/authorize";
	/** 获取令牌路径 */
	public static final String SFS_OAUTH2_SSO_ACCESSTOKENURL = "/connect/access_token";
	/** 获取刷新令牌路径 */
	public static final String SFS_OAUTH2_SSO_REFRESHTOKENURL = "/connect/refresh_token";
	/** 获取用户信息路径 */
	public static final String SFS_OAUTH2_SSO_USERINFOURL = "/connect/user_info";
	/** 服务器端登出路径 */
	public static final String SFS_OAUTH2_SSO_LOGOUTURL = "/connect/logout";
	/** 服务器端登录成功/失败后重定向到的客户端地址 */
	public static final String SFS_OAUTH2_SSO_REDIRECTLOGINURL = "/oauth2-login";
	/** 服务器端登出成功/失败后重定向到的客户端地址 */
	public static final String SFS_OAUTH2_SSO_REDIRECTLOGOUTURL = "/oauth2-logout";

	/** 刷新令牌过期时间 */
	public static final String SFS_RE_EXPIRESIN = "re_expiresIn";

	/** 匿名访问url合集 */
	private Set<String> anonUrlSet = new HashSet<String>() {
		private static final long serialVersionUID = 1L;

		{
			add("/*.js");
			add("/*.css");
			add("/*.woff");
			add("/resources/**");
		}
	};

	/** 客户端id */
	private String clientId;
	/** 客户端安全KEY */
	private String clientSecret;
	/** oauth2统一认证授权服务器地址 */
	private String serverUrl;
	/** oauth2统一认证授权服务器内部地址 */
	private String serverInnerUrl;
	/** 客户端地址 */
	private String clientUrl;
	/** 每次请求客户端与服务器是否同步校验 */
	private boolean isSynValidate;
	/** OAuth2统一认证授权拦截器spi接口 */
	private OAuth2SSOFilterSpi oAuth2SSOFilterSpi;

	/**
	 * 默认构造函数
	 */
	private ConstantsUtil() {
	}

	/**
	 * 获取工具类
	 * 
	 * @return 工具类
	 */
	public synchronized static ConstantsUtil getUtil() {
		if (null == s_constantsUtil) {
			s_constantsUtil = new ConstantsUtil();
		}
		return s_constantsUtil;
	}

	public void addAnonUrlSet(String anonUrl) {
		anonUrlSet.add(anonUrl);
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public void setServerUrl(String serverUrl) {
		this.serverUrl = serverUrl;
	}

	public void setServerInnerUrl(String serverInnerUrl) {
		this.serverInnerUrl = serverInnerUrl;
	}

	public void setClientUrl(String clientUrl) {
		this.clientUrl = clientUrl;
	}

	public void setSynValidate(boolean isSynValidate) {
		this.isSynValidate = isSynValidate;
	}

	/**
	 * 获取匿名访问url合集
	 * 
	 * @return 匿名访问url合集
	 * 
	 */
	public Set<String> getAnonUrlSet() {
		return anonUrlSet;
	}

	/**
	 * 获取oauth2统一认证授权服务器地址
	 * 
	 * @return oauth2统一认证授权服务器地址
	 */
	public String getServerUrl() {
		return serverUrl;
	}

	/**
	 * 获取oauth2统一认证授权服务器内部地址
	 * 
	 * @return oauth2统一认证授权服务器内部地址
	 */
	public String getServerInnerUrl() {
		return (null != serverInnerUrl && !serverInnerUrl.trim().equals("")) ? serverInnerUrl : serverUrl;
	}

	/**
	 * 获取客户端地址
	 * 
	 * @return
	 */
	public String getClientUrl() {
		return clientUrl;
	}

	/**
	 * 获取客户端id
	 * 
	 * @return 客户端id
	 */
	public String getClientId() {
		return clientId;
	}

	/**
	 * 获取客户端安全KEY
	 * 
	 * @return 客户端安全KEY
	 */
	public String getClientSecret() {
		return clientSecret;
	}

	/**
	 * 每次请求客户端与服务器是否同步校验<br>
	 * 如果为true,客户端从cookie中获取accessToken后，再从服务端获取用户信息是否存在<br>
	 * 如果为false,客户端只判断cookie中accessToken是否存在<br>
	 * 
	 * @return true:是，false:否
	 */
	public boolean isSynValidate() {
		return isSynValidate;
	}

	/**
	 * 获取OAuth2统一认证授权拦截器spi接口实现
	 * 
	 * @return OAuth2统一认证授权拦截器spi接口实现
	 */
	public OAuth2SSOFilterSpi getoAuth2SSOFilterSpi() {
		return oAuth2SSOFilterSpi;
	}

	/**
	 * 设置OAuth2统一认证授权拦截器spi接口实现
	 * 
	 * @param oAuth2SSOFilterSpi
	 *            OAuth2统一认证授权拦截器spi接口实现
	 */
	public void setoAuth2SSOFilterSpi(OAuth2SSOFilterSpi oAuth2SSOFilterSpi) {
		this.oAuth2SSOFilterSpi = oAuth2SSOFilterSpi;
	}
}
