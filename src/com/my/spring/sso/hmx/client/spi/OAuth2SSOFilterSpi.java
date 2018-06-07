package com.my.spring.sso.hmx.client.spi;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.spring.sso.hmx.client.OAuth2Credential;

/**
 * OAuth2统一认证授权拦截器spi接口
 * 
 * @author zhixing.ouyang
 * 
 */
public interface OAuth2SSOFilterSpi {

	/**
	 * 认证成功之后处理
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @param userInfo
	 *            用户信息
	 * @param accessToken
	 *            令牌
	 */
	public void onAuthenticateSuccessHandler(HttpServletRequest request, HttpServletResponse response, FilterChain chain, OAuth2Credential credential) throws IOException, ServletException;

	/**
	 * 服务器端登出成功/失败后重定向到的客户端调用登出操作
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @param redirectLoginUrl
	 *            重定向的登录路径
	 * 
	 */
	public void onServerLogoutHandler(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String redirectLoginUrl) throws IOException, ServletException;

	/**
	 * 客户端校验失败登出操作
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @param redirectLoginUrl
	 *            重定向的登录路径
	 * 
	 */
	public void onClientValidationFailedHandler(HttpServletRequest request, HttpServletResponse response, FilterChain chain, String redirectLoginUrl) throws IOException, ServletException;
}
