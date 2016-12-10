package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Video;
import com.my.spring.model.VideoPojo;
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
    @RequestMapping(value="/admin/addVideo", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addVideo(
            @ModelAttribute Video video,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "videoTypeList",required = true) int videoTypeList[],
            HttpServletRequest request,
            @RequestParam(value = "fileList", required = false) MultipartFile[] fileList){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	for(int i=0;i<fileList.length;i++){
    		dataWrapper=VideoService.addVideo(video, token, fileList[i], request,videoTypeList[i]);
    		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
            }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteVideo")
    @ResponseBody
    public DataWrapper<Void> deleteVideo(
            @RequestParam(value = "id",required = true) Long id,
            HttpServletRequest request,
            @RequestParam(value = "fileid",required = true) Long fileid,
            @RequestParam(value = "token",required = true) String token){
        return VideoService.deleteVideo(id,token,fileid,request);
    }

    @RequestMapping(value="/admin/getVideoList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<VideoPojo>> getVideoList(
    		@RequestParam(value="projectId",required=true) Long projectId,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Video video,
    		@RequestParam(value = "token",required = true) String token
    		){
        return VideoService.getVideoList(token,projectId,pageIndex,pageSize,video);
    }
}
