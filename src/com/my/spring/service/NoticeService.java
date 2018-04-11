package com.my.spring.service;


import java.util.List;

import com.my.spring.model.Notice;
import com.my.spring.utils.DataWrapper;

import net.sf.json.JSONArray;

public interface NoticeService {
	DataWrapper<Notice> getById(String token,Long id);
	DataWrapper<Void> deleteNoticeById(String token,Long id);
	DataWrapper<Void> deleteNoticeByIdList(String token,String[] id);
	DataWrapper<Void> addNotice(String token, Notice file);
	DataWrapper<JSONArray> getMenuListMapByIdList(List<Notice> menu);
}
