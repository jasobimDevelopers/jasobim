package com.my.spring.service;


import java.util.List;



import com.my.spring.model.FeedBack;
import com.my.spring.utils.DataWrapper;


public interface FeedBackService {
	
	DataWrapper<Void> addFeedBack(String content, String token,String tel);

	DataWrapper<Void> deleteFeedBack(Long id, String token);

	DataWrapper<List<FeedBack>> getFeedBackList(Integer pageIndex, Integer pageSize, FeedBack feedBack, String token);
}
