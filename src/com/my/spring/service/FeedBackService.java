package com.my.spring.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.FeedBack;
import com.my.spring.model.User;
import com.my.spring.model.UserPojo;
import com.my.spring.utils.DataWrapper;


public interface FeedBackService {
	
	DataWrapper<Void> addFeedBack(String content, String token);

	DataWrapper<Void> deleteFeedBack(Long id, String token);

	DataWrapper<List<FeedBack>> getFeedBackList(Integer pageIndex, Integer pageSize, FeedBack feedBack, String token);
}
