package com.my.spring.sso.hmx.client.exception;

public class C2OAuth2Exception extends Exception {
	private static final long serialVersionUID = 5059849662975779534L;

	public C2OAuth2Exception(String message){
		super(message);
	}
	
	public C2OAuth2Exception(String message,Throwable cause){
		super(message,cause);
	}
}
