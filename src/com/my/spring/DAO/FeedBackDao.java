package com.my.spring.DAO;

import com.my.spring.model.FeedBack;
import com.my.spring.model.User;
import com.my.spring.utils.DataWrapper;

import java.util.List;


public interface FeedBackDao {
	DataWrapper<List<FeedBack>> getFeedBackList(Integer pageSize, Integer pageIndex, FeedBack feedBack);

	boolean addFeedBack(FeedBack feedBack);

	boolean deleteFeedBack(Long id);

	FeedBack getById(Long id);
}
