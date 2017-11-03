package com.my.spring.jpush.exception;
/**
 * @Title: PushException.java
 * @Package com.manage.push.exception
 * @Description: 推送消息异常信息类
 * @author hchen
 * @date 2014年7月10日 下午3:30:45
 * @version V1.0
 */
public class PushException extends Exception {

	private static final long serialVersionUID = -719775104032417718L;

	public PushException() {
		super();
	}

	public PushException(String message, Throwable cause,
			boolean enableSuppression, boolean writableStackTrace) {
		super();
	}

	public PushException(String message, Throwable cause) {
		super(message, cause);
	}

	public PushException(String message) {
		super(message);
	}

	public PushException(Throwable cause) {
		super(cause);
	}

	
}

