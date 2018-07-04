package com.my.spring.DAO;

import com.my.spring.model.News;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface NewsDao {
	boolean addNews(News news);
    boolean deleteNews(Long id);
    boolean updateNews(News news);
    News getById(Long id);
    DataWrapper<List<News>> getNewsList(Integer pageIndex, Integer pageSize, News news);
    News getNewsEarly();
    DataWrapper<List<News>> getNewsListByUserId(Long userId);
}
