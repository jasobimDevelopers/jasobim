package com.my.spring.BaseBootParam;

import java.util.List;

import com.my.spring.model.PaperPointInfoGet;

public class PaperPointParam {
	private List<PaperPointInfoGet> getParam;
	private String token;
	public List<PaperPointInfoGet> getGetParam() {
		return getParam;
	}

	public void setGetParam(List<PaperPointInfoGet> getParam) {
		this.getParam = getParam;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}
	
}
