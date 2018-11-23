 package com.my.spring.controller;

import com.my.spring.model.NewsInfo;
import com.my.spring.model.NewsInfoPojo;
import com.my.spring.service.NewsInfoService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="api/newsInfo")
public class NewsInfoController {
    @Autowired
    NewsInfoService NewsInfoService;
    @RequestMapping(value="/admin/addNewsInfo", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addNewsInfo(
            @ModelAttribute NewsInfo NewsInfo,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=NewsInfoService.addNewsInfo(NewsInfo,token);
        return dataWrapper;
    }
    @RequestMapping(value="/deleteNewsInfo",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteNewsInfo(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return NewsInfoService.deleteNewsInfo(id,token);
    }
    @RequestMapping(value="/updateNewsInfo",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<NewsInfoPojo> updateNewsInfo(
    		@ModelAttribute NewsInfo newsInfo,
            @RequestParam(value = "token",required = true) String token){
        return NewsInfoService.updateNewsInfo(newsInfo,token);
    }

    @RequestMapping(value="/admin/getNewsInfoList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<NewsInfoPojo>> getNewsInfoList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute NewsInfo NewsInfo){
        return NewsInfoService.getNewsInfoList(token,pageIndex,pageSize,NewsInfo);
    }
    ////通过用户id查找留言
    @RequestMapping(value="/getNewsInfoListByUserId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<NewsInfo>> getNewsInfoListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = true) String token){
        return NewsInfoService.getNewsInfoListByUserId(userId,token);
    }
    @RequestMapping(value="/app/uploadNewsInfoFiles", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<String> uploadNewsInfoFiles(
    		 @RequestParam(value = "file", required = false) MultipartFile file,
             @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request){
    	return NewsInfoService.uploadNewsInfoFiles(file,request,token);
    }

}
