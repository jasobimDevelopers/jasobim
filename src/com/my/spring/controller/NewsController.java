 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.News;
import com.my.spring.model.NewsPojo;
import com.my.spring.service.NewsService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;



@Controller
@RequestMapping(value="api/news")
public class NewsController {
    @Autowired
    NewsService NewsService;
    @RequestMapping(value="/admin/addNews", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addNews(
            @ModelAttribute News news,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=NewsService.addNews(news,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteNews",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteNews(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return NewsService.deleteNews(id,token);
    }

    @RequestMapping(value="/admin/updateNews",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateNews(
            @ModelAttribute News News,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(News);
        return NewsService.updateNews(News,token);
    }


    @RequestMapping(value="/admin/getNewsList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<NewsPojo>> getNewsList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute News News){
        return NewsService.getNewsList(token,pageIndex,pageSize,News);
    }
    ////通过用户id查找留言
    @RequestMapping(value="/getNewsListByUserId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<News>> getNewsListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = true) String token){
        return NewsService.getNewsListByUserId(userId,token);
    }
    /**
     *获取微信公众号文章链接 
     **/
    @RequestMapping(value="/getNewsListByWeixin",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> getNewsListByWeixin(
            @RequestParam(value = "token",required = true) String token){
        return NewsService.getNewsListByWeixin(token);
    }
    /**
     * 测试存储微信公众号连接地址
     * */
    @RequestMapping(value="/saveNewsOfWeixin",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> saveNewsOfWeixin(
            @RequestParam(value = "token",required = true) String token){
        return NewsService.saveWechatUrl(token);
    }
}
