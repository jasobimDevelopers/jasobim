package com.my.spring.controller;

import com.my.spring.model.Video;
import com.my.spring.service.VideoService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
            @RequestParam(value = "token",required = true) String token){
        return VideoService.addVideo(Video,token);
    }
    @RequestMapping(value="deleteVideo")
    @ResponseBody
    public DataWrapper<Void> deleteVideo(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return VideoService.deleteVideo(id,token);
    }

    @RequestMapping(value="updateVideo",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateVideo(
            @ModelAttribute Video Video,
            @RequestParam(value = "token",required = true) String token){
       // System.out.println(Video);
        return VideoService.updateVideo(Video,token);
    }


    @RequestMapping(value="getVideoList")
    @ResponseBody
    public DataWrapper<List<Video>> getVideoList(
    		@RequestParam(value = "token",required = true) String token
    		){
        return VideoService.getVideoList(token);
    }
}
