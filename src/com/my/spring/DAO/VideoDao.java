package com.my.spring.DAO;

import com.my.spring.model.Video;
import com.my.spring.utils.DataWrapper;

import java.util.List;

public interface VideoDao {
	Video getByVideoName(String VideoName);
	DataWrapper<Video> getById(Long id);
	boolean addVideo(Video Video);
	boolean updateVideo(Video Video);
	boolean deleteVideo(Long id);
	DataWrapper<List<Video>> getVideoList(Long projectId, Integer pageIndex, Integer pageSize, Video video);
}
