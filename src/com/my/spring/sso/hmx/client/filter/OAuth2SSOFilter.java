package com.my.spring.sso.hmx.client.filter;

import java.io.IOException;

import javax.jws.WebParam;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.FilterRegistration;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.annotation.WebInitParam;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.oltu.oauth2.common.OAuth;
import org.apache.oltu.oauth2.common.error.OAuthError;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.my.spring.sso.hmx.client.OAuth2Credential;
import com.my.spring.sso.hmx.client.exception.C2OAuth2Exception;
import com.my.spring.sso.hmx.client.service.OAuth2SSOService;
import com.my.spring.sso.hmx.client.spi.OAuth2SSOFilterSpi;
import com.my.spring.sso.hmx.client.spi.impl.DefaultOAuth2SSOFilterSpiImpl;
import com.my.spring.sso.hmx.client.util.ConstantsUtil;
import com.my.spring.sso.hmx.client.util.CookieUtil;

/**
 * OAuth2统一认证授权拦截器
 * @author zhixing.ouyang
 *
 */
@Configuration
@WebFilter(filterName="OAuth2SSOFilter",urlPatterns = "/hmx",
		initParams = {
				@WebInitParam(name = "clientId",value = "xuyuxiang"),
				@WebInitParam(name = "clientSecret",value = "secret"),
				@WebInitParam(name = "serverUrl",value = "http://192.168.2.53:8020"),
				@WebInitParam(name = "clientUrl",value = "http://localhost:8080/jasobim/api/ssocallback"),
				@WebInitParam(name = "synValidate",value = "false"),
				@WebInitParam(name = "oAuth2SSOFilterSpi",value = "com.chinacreator.asp.comp.sys.oauth2.sso.client.spi.impl.DefaultOAuth2SSOFilterSpiImpl")
		})

//@Order(FilterRegistration.LOWEST_PRECEDENCE)
public class OAuth2SSOFilter implements Filter {

	private OAuth2SSOService oAuth2SSOService;
	private ConstantsUtil constantsUtil;
	private OAuth2SSOFilterSpi oAuth2SSOFilterSpi;

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
		oAuth2SSOService = OAuth2SSOService.getOAuth2SSOService();
		constantsUtil = ConstantsUtil.getUtil();

		String clientId = filterConfig.getInitParameter(ConstantsUtil.SFS_OAUTH2_SSO_CLIENTID);
		if (null == clientId || clientId.trim().equals("")) {
			throw new NullPointerException(ConstantsUtil.SFS_OAUTH2_SSO_CLIENTID + "不能为空！请检查web.xml中的相关配置！");
		}
		String clientSecret = filterConfig.getInitParameter(ConstantsUtil.SFS_OAUTH2_SSO_CLIENTSECRET);
		if (null == clientSecret || clientSecret.trim().equals("")) {
			throw new NullPointerException(ConstantsUtil.SFS_OAUTH2_SSO_CLIENTSECRET + "不能为空！请检查web.xml中的相关配置！");
		}
		String serverUrl = filterConfig.getInitParameter(ConstantsUtil.SFS_OAUTH2_SSO_SERVERURL);
		if (null == serverUrl || serverUrl.trim().equals("")) {
			throw new NullPointerException(ConstantsUtil.SFS_OAUTH2_SSO_SERVERURL + "不能为空！请检查web.xml中的相关配置！");
		}
		String serverInnerUrl = filterConfig.getInitParameter(ConstantsUtil.SFS_OAUTH2_SSO_SERVEINNERRURL);
		if (null == serverInnerUrl || serverInnerUrl.trim().equals("")) {
			serverInnerUrl = serverUrl;
		}
		String clientUrl = filterConfig.getInitParameter(ConstantsUtil.SFS_OAUTH2_SSO_CLIENTURL);
		if (null == clientUrl || clientUrl.trim().equals("")) {
			throw new NullPointerException(ConstantsUtil.SFS_OAUTH2_SSO_CLIENTURL);
		}
		String synValidate = filterConfig.getInitParameter(ConstantsUtil.SFS_OAUTH2_SSO_SYNVALIDATE);
		String anonUrl = filterConfig.getInitParameter(ConstantsUtil.SFS_OAUTH2_SSO_ANONURL);

		constantsUtil.setClientId(clientId);
		constantsUtil.setClientSecret(clientSecret);
		constantsUtil.setServerUrl(serverUrl);
		constantsUtil.setServerInnerUrl(serverInnerUrl);
		constantsUtil.setClientUrl(clientUrl);
		constantsUtil.setSynValidate("true".equals(synValidate));
		if (null != anonUrl && !anonUrl.trim().equals("")) {
			String[] urls = anonUrl.split(";");
			for (String url : urls) {
				if (null != url && !url.trim().equals("")) {
					constantsUtil.addAnonUrlSet(url);
				}
			}
		}

		String filterSpi = filterConfig.getInitParameter(ConstantsUtil.SFS_OAUTH2_SSO_OAUTH2SSOFILTERSPI);
		if (null != filterSpi && !filterSpi.trim().equals("")) {
			try {
				oAuth2SSOFilterSpi = (OAuth2SSOFilterSpi) Class.forName(filterSpi).newInstance();
			} catch (Exception e) {
				oAuth2SSOFilterSpi = new DefaultOAuth2SSOFilterSpiImpl();
			}
		} else {
			oAuth2SSOFilterSpi = new DefaultOAuth2SSOFilterSpiImpl();
		}
	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException,
			ServletException {

		HttpServletRequest httpServletRequest = (HttpServletRequest) request;
		String currentURL = httpServletRequest.getRequestURL().toString();

		// 判断是否匿名访问的路径
		if (oAuth2SSOService.isAnonUrl(currentURL)) {
			chain.doFilter(request, response);
		}
		// 判断是否服务器端登录成功/失败后重定向到的客户端地址
		else if (oAuth2SSOService.isRedirectLoginUrl(currentURL)) {
			doOAuth2LoginFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
		}
		// 判断是否服务器端登出成功/失败后重定向到的客户端地址
		else if (oAuth2SSOService.isRedirectLogoutUrl(currentURL)) {
			doOAuth2LogoutFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
		}
		// 受保护的资源访问路径
		else {
			doAuthcFilter((HttpServletRequest)request, (HttpServletResponse)response, chain);
		}
	}

	/**
	 * 服务器端登录成功/失败后重定向到的客户端调用
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doOAuth2LoginFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String error = request.getParameter("error");
		String errorDescription = request.getParameter("error_description");

		// 如果服务端返回了错误
		if (null != error && !error.trim().equals("")) {
			if (OAuthError.CodeResponse.ACCESS_DENIED.equals(error)) {
				outErrorMessHtml(request, response, errorDescription, oAuth2SSOService.caculateRedirectLogoutUrl());
			} else {
				outErrorMessHtml(request, response, errorDescription, null);
			}
			return;
		}
		
		try {
			OAuth2Credential credential = oAuth2SSOService.fetchCredential(request, response, request.getParameter(OAuth.OAUTH_CODE));
			
			request.getSession().setAttribute(ConstantsUtil.SFS_OAUTH2_SSO_CREDENTIAL, credential);
			CookieUtil.setCookie((HttpServletRequest) request, (HttpServletResponse) response,CookieUtil.SFS_COOKIENAME_REFRESHTOKEN, credential.getRefreshToken(),CookieUtil.SFI_REFRESHTOKEN_MAXAGE);
			
			// 调用认证成功后处理
			oAuth2SSOFilterSpi.onAuthenticateSuccessHandler(request, response, chain, credential);
		} catch (C2OAuth2Exception e) {
			outErrorMessHtml(request, response, e.getMessage(), oAuth2SSOService.caculateRedirectLogoutUrl());
		}
	}

	/**
	 * 服务器端登出成功/失败后重定向到的客户端调用
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 *             , ServletException
	 */
	private void doOAuth2LogoutFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		String error = request.getParameter("error");
		String errorDescription = request.getParameter("error_description");

		// 如果服务端返回了错误
		if (null != error && !error.trim().equals("")) {
			outErrorMessHtml(request, response, errorDescription, null);
			return;
		}

		// 登出(清除cookie)
		oAuth2SSOService.logout((HttpServletRequest) request, (HttpServletResponse) response);
		// 调用登出操作
		oAuth2SSOFilterSpi.onServerLogoutHandler(request, response, chain, oAuth2SSOService.caculateRedirectLoginUrl());
	}

	/**
	 * 受保护的资源访问路径调用
	 * 
	 * @param request
	 * @param response
	 * @param chain
	 * @throws IOException
	 * @throws ServletException
	 */
	private void doAuthcFilter(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException{
		try {
			if(oAuth2SSOService.fetchCredential(request, response) == null ){
				//凭证获取失败，当前用户没有登录
				oAuth2SSOService.logout((HttpServletRequest) request, (HttpServletResponse) response);
				oAuth2SSOFilterSpi.onClientValidationFailedHandler(request, response, chain,oAuth2SSOService.caculateRedirectLoginUrl());
				return;
			}
			oAuth2SSOService.fetchUserInfo(request, response);
		} catch (C2OAuth2Exception e) {
			oAuth2SSOService.logout((HttpServletRequest) request, (HttpServletResponse) response);
			oAuth2SSOFilterSpi.onClientValidationFailedHandler(request, response, chain, oAuth2SSOService.caculateRedirectLoginUrl());
			outErrorMessHtml(request, response, e.getMessage(), null);
			return;
		}
		chain.doFilter(request, response);
	}

	private void outErrorMessHtml(ServletRequest request, ServletResponse response, String errMess, String redirectUrl)
			throws IOException {
		response.setContentType("text/html;charset=UTF-8");
		response.getWriter().write(errMess);
		if (null != redirectUrl && !redirectUrl.trim().equals("")) {
			((HttpServletResponse) response).setHeader("refresh", "3;url=" + redirectUrl);
			response.getWriter().write("<br/>3秒后自动跳转，如果跳转失败请点击<a href='" + redirectUrl + "'>重新登录</a>");
		}
	}

	@Override
	public void destroy() {
	}

}
