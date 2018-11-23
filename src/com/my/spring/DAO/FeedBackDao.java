package com.my.spring.DAO;

import com.my.spring.model.FeedBack;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface FeedBackDao {

	boolean addFeedBack(FeedBack feedBack);

	boolean deleteFeedBack(String[] ids);

	FeedBack getById(Long id);

	DataWrapper<List<FeedBack>> getFeedBackList(Integer pageSize, Integer pageIndex, FeedBack feedBack, String dates);
}
