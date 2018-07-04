package com.my.spring.service;

import com.my.spring.model.NewsInfo;
import com.my.spring.model.NewsInfoPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;



public interface NewsInfoService {
    DataWrapper<List<NewsInfoPojo>> getNewsInfoList(String token, Integer pageIndex, Integer pageSize, NewsInfo NewsInfo);
    DataWrapper<List<NewsInfo>> getNewsInfoListByUserId(Long userId,String token);
	DataWrapper<Void> addNewsInfo(NewsInfo NewsInfo, String token);
	DataWrapper<Void> deleteNewsInfo(Long id, String token);
	DataWrapper<String> uploadNewsInfoFiles(MultipartFile file, HttpServletRequest request, String token);
	DataWrapper<NewsInfoPojo> updateNewsInfo(NewsInfo newsInfo, String token);
}
