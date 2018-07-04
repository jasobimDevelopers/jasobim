package com.my.spring.DAO;

import com.my.spring.model.NewsInfo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface NewsInfoDao {
	boolean addNewsInfo(NewsInfo NewsInfo);
    boolean deleteNewsInfo(Long id);
    NewsInfo getById(Long id);
    DataWrapper<List<NewsInfo>> getNewsInfoList(Integer pageIndex, Integer pageSize, NewsInfo NewsInfo);
    DataWrapper<List<NewsInfo>> getNewsInfoListByUserId(Long userId);
	boolean updateNewsInfo(NewsInfo newsInfoNew);
}
