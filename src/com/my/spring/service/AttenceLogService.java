package com.my.spring.service;

import java.util.List;

import com.my.spring.model.AttenceLog;
import com.my.spring.model.AttenceLogPojo;
import com.my.spring.utils.DataWrapper;

public interface AttenceLogService {
	 DataWrapper<Void> addAttenceLog(AttenceLog duct, String token, Double lat, Double lng);
	 DataWrapper<Void> deleteAttenceLog(Long id,String token);
	 DataWrapper<Void> updateAttenceLog(AttenceLog duct,String token);
	 DataWrapper<List<AttenceLogPojo>> getAttenceLogList(String token, AttenceLog duct, Integer pageSize,
			Integer pageIndex, Integer year, Integer month);
}
