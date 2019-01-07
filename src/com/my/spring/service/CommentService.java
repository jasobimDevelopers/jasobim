package com.my.spring.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Comment;
import com.my.spring.model.CommentPojo;
import com.my.spring.utils.DataWrapper;

public interface CommentService {
	
	DataWrapper<List<CommentPojo>> getCommentList(Integer pageIndex, Integer pageSize, Comment Comment, String token);

	DataWrapper<Void> addComment(String token, MultipartFile[] pics, MultipartFile[] vois, HttpServletRequest request,
			Comment comment);
}
