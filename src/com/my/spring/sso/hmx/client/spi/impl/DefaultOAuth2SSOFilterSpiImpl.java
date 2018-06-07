package com.my.spring.sso.hmx.client.spi.impl;

import java.io.IOException;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.my.spring.sso.hmx.client.OAuth2Credential;
import com.my.spring.sso.hmx.client.spi.OAuth2SSOFilterSpi;

public class DefaultOAuth2SSOFilterSpiImpl implements OAuth2SSOFilterSpi {

	@Override
	public void onAuthenticateSuccessHandler(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			OAuth2Credential credential) throws IOException, ServletException {
		// 跳转登录成功页面
		((HttpServletResponse) response).sendRedirect("./");
	}

	@Override
	public void onServerLogoutHandler(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			String redirectLoginUrl) throws IOException, ServletException {
		// 重定向登录页面
		((HttpServletResponse) response).sendRedirect(redirectLoginUrl);
	}

	@Override
	public void onClientValidationFailedHandler(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			String redirectLoginUrl) throws IOException, ServletException {
		// 重定向登录页面
		((HttpServletResponse) response).sendRedirect(redirectLoginUrl);
	}

}
