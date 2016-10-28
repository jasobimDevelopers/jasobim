package com.my.spring.service;


import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Video;
import com.my.spring.utils.DataWrapper;


public interface VideoService {
	DataWrapper<Void> addVideo(Video Video,String token, MultipartFile file);
	DataWrapper<Video> getVideoDetails(Long id,String token);
	DataWrapper<List<Video>> getVideoList(String token);
	DataWrapper<Void> deleteVideo(Long id, String token, Long fileid);
	

}
