package com.my.spring.sso.hmx.client;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.apache.oltu.oauth2.client.OAuthClient;
import org.apache.oltu.oauth2.client.URLConnectionClient;
import org.apache.oltu.oauth2.client.request.OAuthBearerClientRequest;
import org.apache.oltu.oauth2.client.request.OAuthClientRequest;
import org.apache.oltu.oauth2.client.response.OAuthAccessTokenResponse;
import org.apache.oltu.oauth2.client.response.OAuthResourceResponse;
import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.exception.OAuthProblemException;
import org.apache.oltu.oauth2.common.exception.OAuthSystemException;
import org.apache.oltu.oauth2.common.message.types.GrantType;

import com.my.spring.sso.hmx.client.util.ConstantsUtil;
import com.my.spring.sso.hmx.client.util.StringUtils;

public class OAuth2Credential {
	private static ConstantsUtil constantsUtil = ConstantsUtil.getUtil();

	private Lock lock = new ReentrantLock();

	private Long expirationTimeMilliseconds;

	private String accessToken;
	private String refreshToken;
	private String userInfo;

	/**
	 * 通过refreshToken创建用户凭证，即通过refreshToken去服务器换取accessToken，如果发生错误或RefreshToken失效则抛出异常。
	 * <p>
	 * 注意！要在调用处保证该方法的线程安全
	 * 
	 * @param mutex 互斥锁
	 * @param refreshToken
	 * @return 用户凭证
	 * @throws OAuthProblemException
	 * @throws OAuthSystemException
	 */
	public static OAuth2Credential createCredentialByRefreshToken(String refreshToken) throws OAuthProblemException, OAuthSystemException {
		OAuth2Credential credential=new OAuth2Credential();
		credential.setRefreshToken(refreshToken);
		credential.refreshToken();
		return credential;
	}
	
	/**
	 * 通过从服务器获取的授权码去服务器换取access toekn和refresh token，如果发生错误或授权码失效则抛出异常
	 * @param grantCode
	 * @return 用户凭证
	 * @throws OAuthProblemException
	 * @throws OAuthSystemException
	 */
	public static OAuth2Credential createCredentialByGrantCode(String grantCode) throws OAuthProblemException, OAuthSystemException {
		OAuth2Credential credential=new OAuth2Credential();
		credential.refreshToken(grantCode);
		return credential;
	}
	
	private OAuth2Credential(){}

	/**
	 * 获取凭证中的accessToken，该方法是
	 * 
	 * @return
	 */
	public String getAccessToken() {
		lock.lock();
		try {
			return accessToken;
		} finally {
			lock.unlock();
		}
	}

	public OAuth2Credential setAccessToken(String accessToken) {
		lock.lock();
		try {
			this.accessToken = accessToken;
		} finally {
			lock.unlock();
		}
		return this;
	}

	public String getRefreshToken() {
		lock.lock();
		try {
			return refreshToken;
		} finally {
			lock.unlock();
		}
	}

	public OAuth2Credential setRefreshToken(String refreshToken) {
		lock.lock();
		try {
			this.refreshToken = refreshToken;
		} finally {
			lock.unlock();
		}
		return this;
	}
	
	public String getUserInfo(){
		lock.lock();
		try {
			return userInfo;
		} finally {
			lock.unlock();
		}
	}
	
	public OAuth2Credential setUserInfo(String userInfo){
		lock.lock();
		try {
			this.userInfo = userInfo;
		} finally {
			lock.unlock();
		}
		return this;
	}
	
	public final void refreshUserInfo() throws OAuthProblemException,OAuthSystemException{
		lock.lock();
		try {
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthClientRequest userInfoRequest = new OAuthBearerClientRequest(constantsUtil.getServerInnerUrl()
					+ ConstantsUtil.SFS_OAUTH2_SSO_USERINFOURL).setAccessToken(accessToken).buildQueryMessage();
			OAuthResourceResponse resourceResponse = oAuthClient.resource(userInfoRequest, OAuth.HttpMethod.GET,
					OAuthResourceResponse.class);
			
			setUserInfo(resourceResponse.getBody());
		}finally{
			lock.unlock();
		}
	}

	/**
	 * 通过Refresh Token刷新Access Token
	 * 
	 * @return
	 */
	public final void refreshToken() throws OAuthProblemException,OAuthSystemException{
		lock.lock();
		try {
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthClientRequest accessTokenRequest = OAuthClientRequest
					.tokenLocation(constantsUtil.getServerInnerUrl()+ ConstantsUtil.SFS_OAUTH2_SSO_REFRESHTOKENURL)
					.setGrantType(GrantType.REFRESH_TOKEN)
					.setClientId(constantsUtil.getClientId())
					.setClientSecret(constantsUtil.getClientSecret())
					.setRefreshToken(refreshToken).buildQueryMessage();

			OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(
					accessTokenRequest, OAuth.HttpMethod.POST);

			setAccessToken(oAuthResponse.getAccessToken());
			setRefreshToken(oAuthResponse.getRefreshToken());
			setExpiresInSeconds(oAuthResponse.getExpiresIn().longValue());
			
			//如果没有取过用户信息，则刷新之
			if(StringUtils.isEmpty(userInfo)){
				refreshUserInfo();
			}
		} finally {
			lock.unlock();
		}
	}
	
	/**
	 * 通过grantCode获取Access Token
	 * 
	 * @return
	 */
	public final void refreshToken(String grantCode) throws OAuthProblemException,OAuthSystemException{
		lock.lock();
		try {
			OAuthClient oAuthClient = new OAuthClient(new URLConnectionClient());
			OAuthClientRequest accessTokenRequest = OAuthClientRequest
					.tokenLocation(constantsUtil.getServerInnerUrl() + ConstantsUtil.SFS_OAUTH2_SSO_ACCESSTOKENURL)
					.setGrantType(GrantType.AUTHORIZATION_CODE).setClientId(constantsUtil.getClientId())
					.setClientSecret(constantsUtil.getClientSecret()).setCode(grantCode)
					.setRedirectURI(constantsUtil.getClientUrl() + ConstantsUtil.SFS_OAUTH2_SSO_REDIRECTLOGINURL)
					.buildQueryMessage();

			OAuthAccessTokenResponse oAuthResponse = oAuthClient.accessToken(accessTokenRequest, OAuth.HttpMethod.POST);

			setAccessToken(oAuthResponse.getAccessToken());
			setRefreshToken(oAuthResponse.getRefreshToken());
			setExpiresInSeconds(oAuthResponse.getExpiresIn().longValue());
			
			//如果没有取过用户信息，则刷新之
			if(StringUtils.isEmpty(userInfo)){
				refreshUserInfo();
			}
		} finally {
			lock.unlock();
		}
	}

	public OAuth2Credential setExpirationTimeMilliseconds(
			Long expirationTimeMilliseconds) {
		lock.lock();
		try {
			this.expirationTimeMilliseconds = expirationTimeMilliseconds;
		}finally{
			lock.unlock();
		}
		return this;
	}

	/**
	 * 获取Access Token超时剩余时间
	 * 
	 * @return 还剩多少秒超时
	 */
	public final Long getExpiresInSeconds() {
		lock.lock();
		try {
			if (expirationTimeMilliseconds == null) {
				return null;
			}
			return (expirationTimeMilliseconds - System.currentTimeMillis()) / 1000;
		} finally {
			lock.unlock();
		}
	}

	/**
	 * 设置Access Token多少秒后超时
	 * 
	 * @param expiresIn
	 * @return
	 */
	public OAuth2Credential setExpiresInSeconds(Long expiresIn) {
		return setExpirationTimeMilliseconds(expiresIn == null ? null : System
				.currentTimeMillis() + expiresIn * 1000);
	}
}
