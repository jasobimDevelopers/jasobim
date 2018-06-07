package com.my.spring.sso.hmx.client.service;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.json.JSONObject;

import com.my.spring.sso.hmx.client.OAuth2Credential;
import com.my.spring.sso.hmx.client.OAuth2Messages;
import com.my.spring.sso.hmx.client.exception.C2OAuth2Exception;
import com.my.spring.sso.hmx.client.util.AntPathMatcher;
import com.my.spring.sso.hmx.client.util.ConstantsUtil;
import com.my.spring.sso.hmx.client.util.CookieUtil;
import com.my.spring.sso.hmx.client.util.StringUtils;

/**
 * OAuth2统一认证授权服务类
 * @author zhixing.ouyang
 *
 */
public class OAuth2SSOService {

	private static OAuth2SSOService oAuth2SSOService;

	private ConstantsUtil constantsUtil = ConstantsUtil.getUtil();

	/** 路径校验器 */
	private static final AntPathMatcher sf_PATHMATCHER = new AntPathMatcher();

	/**
	 * 默认构造函数
	 */
	private OAuth2SSOService() {
	}

	/**
	 * 获取OAuth2统一认证授权服务类
	 * 
	 * @return OAuth2统一认证授权服务类
	 */
	public synchronized static OAuth2SSOService getOAuth2SSOService() {
		if (null == oAuth2SSOService) {
			oAuth2SSOService = new OAuth2SSOService();
		}
		return oAuth2SSOService;
	}
	
	/**
	 * 通过授权码到服务器获取用户凭证
	 * 
	 * @param request 请求
	 * @param response 响应
	 * @param grantCode 授权码
	 * @return 获取到的凭证
	 * @throws C2OAuth2Exception 
	 */
	public OAuth2Credential fetchCredential(HttpServletRequest request, HttpServletResponse response, String grantCode) throws C2OAuth2Exception{
		try {
			return OAuth2Credential.createCredentialByGrantCode(grantCode);
		} catch (Exception e) {
			throw new C2OAuth2Exception(OAuth2Messages.getString("OAUTH2.SSOLOGIN_ERROR"),e);
		} 
	}
	
	/**
	 * 获取当前用户的凭证，没有登录则返回null；如果用户在服务器端的refresh token改变了，这边会把cookie中的也修改掉
	 * 
	 * @param request 请求
	 * @param response 响应
	 * @return 获取到的凭证
	 * @throws C2OAuth2Exception 
	 */
	public OAuth2Credential fetchCredential(HttpServletRequest request, HttpServletResponse response) throws C2OAuth2Exception{
		try {
			String refreshToken = CookieUtil.getCookieByName((HttpServletRequest) request, CookieUtil.SFS_COOKIENAME_REFRESHTOKEN);
			if(StringUtils.isEmpty(refreshToken)){
				//refresh token不存在，不管session有没有凭证，都认为是已经登出了
				return null;
			}
			
			OAuth2Credential credential = (OAuth2Credential)request.getSession().getAttribute(ConstantsUtil.SFS_OAUTH2_SSO_CREDENTIAL);
			if(credential == null ){
				//凭证不存在，看cookie中有没有refresh token，如果有，则通过refresh token建一个新的
				synchronized (getSessionMutex(request.getSession())) {
					credential = OAuth2Credential.createCredentialByRefreshToken(refreshToken);
				}
			}
			
			if(credential.getExpiresInSeconds()<0){
				//Access Token已过期，刷新之
				credential.refreshToken();
				if(!refreshToken.equals(credential.getRefreshToken())){
					CookieUtil.setCookie((HttpServletRequest) request, (HttpServletResponse) response,CookieUtil.SFS_COOKIENAME_REFRESHTOKEN, credential.getRefreshToken(),CookieUtil.SFI_REFRESHTOKEN_MAXAGE);
				}
			}
			return credential;
		} catch (Exception e) {
			throw new C2OAuth2Exception(OAuth2Messages.getString("OAUTH2.SSOLOGIN_ERROR"),e);
		} 
	}
	
	/**
	 * Session互斥锁，比直接拿session做锁更可靠
	 * @param session
	 * @return
	 */
	private static Object getSessionMutex(HttpSession session) {
		Object mutex = session.getAttribute(ConstantsUtil.SESSION_MUTEX_ATTRIBUTE);
		//拿不到说明没有注册HttpSessionMutexListener，只能拿session做锁了
		if (mutex == null) {
			mutex = session;
		}
		return mutex;
	}

	/**
	 * 获取当前登录用户信息
	 * 
	 * @param credential 用户凭证
	 * @return 用户信息(json对象)，不会返回空对象，获取不正确就会抛异常
	 * @throws C2OAuth2Exception 
	 */
	public JSONObject fetchUserInfo(HttpServletRequest request, HttpServletResponse response) throws C2OAuth2Exception{
		OAuth2Credential credential = fetchCredential(request, response);
		try {
			if(isSynValidate()){
				credential.refreshUserInfo();
			}
			String userInfoJson = credential.getUserInfo();
			if(StringUtils.isEmpty(userInfoJson)){
				throw new C2OAuth2Exception(OAuth2Messages.getString("OAUTH2.USERINFO_IS_NULL"));
			}
			return new JSONObject(userInfoJson);
		} catch (OAuthSystemException e) {
			throw new C2OAuth2Exception(OAuth2Messages.getString("USERINFO_IS_NULL"),e);
		} catch (OAuthProblemException e) {
			throw new C2OAuth2Exception(OAuth2Messages.getString("USERINFO_IS_NULL"),e);
		}
	}

	/**
	 * 获取重定向登录路径
	 * 
	 * @param request
	 * @return 登录路径
	 */
	public String caculateRedirectLoginUrl() {
		String st ="9ef53eede85ea9afa0091bd0b050f581cc2b694fb9604cac7237c529d28621c9";
		String t ="9ef53eede85ea9afa0091bd0b050f581cc2b694fb96032132";
		StringBuffer url = new StringBuffer();
		url.append(constantsUtil.getServerUrl());
		url.append(ConstantsUtil.SFS_OAUTH2_SSO_AUTHORIZEURL);
		url.append("?client_id=");
		url.append(constantsUtil.getClientId());
		url.append("&response_mode=form_post&scope=openid&state="+st);
		url.append("&nonce="+t);
		url.append("&response_type=id_token token&redirect_uri=");
		url.append(constantsUtil.getClientUrl());
		//url.append(ConstantsUtil.SFS_OAUTH2_SSO_REDIRECTLOGINURL);
		return url.toString();
	}

	/**
	 * 获取重定向登出路径
	 * 
	 * @param request
	 * @param response
	 * @return 登出路径
	 */
	public String caculateRedirectLogoutUrl() {
		StringBuffer url = new StringBuffer();
		url.append(constantsUtil.getServerUrl());
		url.append(ConstantsUtil.SFS_OAUTH2_SSO_LOGOUTURL);
		url.append("?client_id=");
		url.append(constantsUtil.getClientId());
		url.append("&response_type=code&redirect_uri=");
		url.append(constantsUtil.getClientUrl());
		url.append(ConstantsUtil.SFS_OAUTH2_SSO_REDIRECTLOGOUTURL);

		return url.toString();
	}

	/**
	 * 登出
	 * 
	 * @param request
	 * @param response
	 */
	public void logout(HttpServletRequest request, HttpServletResponse response) {
		CookieUtil.removeCookie(request, response, CookieUtil.SFS_COOKIENAME_REFRESHTOKEN);
	}

	/**
	 * 判断是否匿名访问的路径
	 * 
	 * @param url
	 *            路径
	 * @return true:是，false:否
	 */
	public boolean isAnonUrl(String url) {
		if (null != url && !url.trim().equals("")) {
			for (String anonUrl : constantsUtil.getAnonUrlSet()) {
				if (sf_PATHMATCHER.match(caculateUrl(anonUrl), url)) {
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * 判断是否服务器端登录成功/失败后重定向到的客户端地址
	 * 
	 * @param url
	 *            路径
	 * @return true:是，false:否
	 */
	public boolean isRedirectLoginUrl(String url) {
		if (null != url && !url.trim().equals("")) {
			return sf_PATHMATCHER.match(caculateUrl(ConstantsUtil.SFS_OAUTH2_SSO_REDIRECTLOGINURL), url);
		}
		return false;
	}

	/**
	 * 判断是否服务器端登出成功/失败后重定向到的客户端地址
	 * 
	 * @param url
	 *            路径
	 * @return true:是，false:否
	 */
	public boolean isRedirectLogoutUrl(String url) {
		if (null != url && !url.trim().equals("")) {
			return sf_PATHMATCHER.match(caculateUrl(ConstantsUtil.SFS_OAUTH2_SSO_REDIRECTLOGOUTURL), url);
		}
		return false;
	}

	/**
	 * 每次请求客户端与服务器是否同步校验<br>
	 * 如果为true,客户端从cookie中获取accessToken后，再从服务端获取用户信息是否存在<br>
	 * 如果为false,客户端只判断cookie中accessToken是否存在<br>
	 * 
	 * @return true:是，false:否
	 */
	public boolean isSynValidate() {
		return constantsUtil.isSynValidate();
	}

	private String caculateUrl(String url) {
		if (null != url && !url.trim().equals("")) {
			url = (url.startsWith("/") ? "**" : "**/") + url;
		}
		return url;
	}
}
