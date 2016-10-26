package com.my.spring.service;


import java.util.List;

import com.my.spring.model.Video;
import com.my.spring.utils.DataWrapper;


public interface VideoService {
	DataWrapper<Void> addVideo(Video Video,String token);
	DataWrapper<Void> deleteVideo(Long videoId,String token);
	DataWrapper<Void> updateVideo(Video Video,String token);
	DataWrapper<Video> getVideoDetails(Long id,String token);
	DataWrapper<List<Video>> getVideoList(String token);
	

}
