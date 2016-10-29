package com.my.spring.service;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Video;
import com.my.spring.utils.DataWrapper;


public interface VideoService {
	DataWrapper<Video> getVideoDetails(Long id,String token);
	DataWrapper<List<Video>> getVideoList(String token);
	DataWrapper<Void> deleteVideo(Long id, String token, Long fileid,HttpServletRequest request);
	DataWrapper<Void> addVideo(Video video, String token, MultipartFile file, HttpServletRequest request);
	

}
