package com.my.spring.controller;

import com.my.spring.model.Video;
import com.my.spring.service.VideoService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/video")
public class VideoController {
    @Autowired
    VideoService VideoService;
    @RequestMapping(value="addVideo", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addVideo(
            @ModelAttribute Video Video,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile file){
        return VideoService.addVideo(Video,token,file,request);
    }
    @RequestMapping(value="deleteVideo")
    @ResponseBody
    public DataWrapper<Void> deleteVideo(
            @RequestParam(value = "id",required = true) Long id,
            HttpServletRequest request,
            @RequestParam(value = "fileid",required = true) Long fileid,
            @RequestParam(value = "token",required = true) String token){
        return VideoService.deleteVideo(id,token,fileid,request);
    }

    @RequestMapping(value="getVideoList")
    @ResponseBody
    public DataWrapper<List<Video>> getVideoList(
    		@RequestParam(value = "token",required = true) String token
    		){
        return VideoService.getVideoList(token);
    }
}
