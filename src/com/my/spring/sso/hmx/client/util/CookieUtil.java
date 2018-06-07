package com.my.spring.sso.hmx.client.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * cookie操作工具类
 * 
 * @author zhixing.ouyang
 * 
 */
public class CookieUtil {

	/** 客户端accessToken存放cookie中的名字 */
	public static final String SFS_COOKIENAME_ACCESSTOKEN = "c2sso_client_accessToken";
	/** 客户端refreshToken存放cookie中的名字 */
	public static final String SFS_COOKIENAME_REFRESHTOKEN = "c2sso_client_refreshToken";
	/** refreshToken在cookie中的过期时间：7天 */
	public static final int SFI_REFRESHTOKEN_MAXAGE = 60 * 60 * 12 * 7;

	/**
	 * 设置参数到cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 * @param value
	 *            cookie值
	 * @param maxAge
	 *            cookie生命周期(秒)
	 */
	public static void setCookie(HttpServletRequest request, HttpServletResponse response, String name, String value,
			int maxAge) {
		if (null != name && !name.trim().equals("")) {
			String path = request.getContextPath();
			Cookie cookie = new Cookie(name, value);
			cookie.setPath((null != path && !path.trim().equals("")) ? path : "/");
			cookie.setMaxAge(maxAge);
			response.addCookie(cookie);
		}
	}

	/**
	 * 根据名字删除cookie
	 * 
	 * @param response
	 * @param name
	 *            cookie名字
	 */
	public static void removeCookie(HttpServletRequest request, HttpServletResponse response, String name) {
		setCookie(request, response, name, null, 0);
	}

	/**
	 * 根据名字获取cookie中的值
	 * 
	 * @param request
	 * @param name
	 *            cookie名字
	 * @return cookie中的值
	 */
	public static String getCookieByName(HttpServletRequest request, String name) {
		if (null != name && !name.trim().equals("")) {
			Cookie[] cookies = request.getCookies();
			if (null != cookies) {
				for (Cookie cookie : cookies) {
					if (name.equals(cookie.getName())) {
						return cookie.getValue();
					}
				}
			}
		}
		return null;
	}
}
