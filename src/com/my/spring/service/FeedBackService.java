package com.my.spring.service;


import java.util.List;



import com.my.spring.model.FeedBack;
import com.my.spring.model.FeedBackPojo;
import com.my.spring.utils.DataWrapper;


public interface FeedBackService {
	
	DataWrapper<Void> addFeedBack(String content, String token,String tel);

	DataWrapper<Void> deleteFeedBack(String ids, String token);

	DataWrapper<List<FeedBackPojo>> getFeedBackList(Integer pageIndex, Integer pageSize, FeedBack feedBack, String token);
}
