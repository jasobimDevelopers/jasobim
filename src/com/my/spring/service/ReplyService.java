package com.my.spring.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Reply;
import com.my.spring.model.ReplyPojo;
import com.my.spring.utils.DataWrapper;

public interface ReplyService {
	
	DataWrapper<List<ReplyPojo>> getReplyList(Integer pageIndex, Integer pageSize, Reply Reply, String token);

	DataWrapper<Void> addReply(String token, MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request,
			Reply Reply);
}
