package com.my.spring.service;

import com.my.spring.model.News;
import com.my.spring.model.NewsPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;



public interface NewsService {
    DataWrapper<List<NewsPojo>> getNewsList(String token, Integer pageIndex, Integer pageSize, News News);
    DataWrapper<List<News>> getNewsListByUserId(Long userId,String token);
	DataWrapper<Void> addNews(News News, String token);
	DataWrapper<Void> deleteNews(Long id, String token);
	DataWrapper<Void> updateNews(News News, String token);
	DataWrapper<String> getNewsListByWeixin(String token);
	DataWrapper<Void> saveWechatUrl(String token);
}
