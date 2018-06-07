package com.my.spring.sso.hmx.client.listener;

import java.io.Serializable;

import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.my.spring.sso.hmx.client.util.ConstantsUtil;

/**
 * @author zhixing.ouyang
 *
 */
public class HttpSessionMutexListener implements HttpSessionListener {

	public void sessionCreated(HttpSessionEvent event) {
		event.getSession().setAttribute(ConstantsUtil.SESSION_MUTEX_ATTRIBUTE, new Mutex());
	}

	public void sessionDestroyed(HttpSessionEvent event) {
		event.getSession().removeAttribute(ConstantsUtil.SESSION_MUTEX_ATTRIBUTE);
	}

	/**
	 * 互斥锁对象，啥都不用干，能序列化就行
	 */
	@SuppressWarnings("serial")
	private static class Mutex implements Serializable {
	}
}