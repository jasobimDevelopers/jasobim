package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.FeedBack;
import com.my.spring.service.FeedBackService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/feedback")
public class FeedBackController {
	@Autowired
	FeedBackService feedBackService;
	
	/**
	 * 
	 * @param userName、password、realName   //必须
	 * @param email、tel可有可无
	 * 其他参数不需要，由程序指定，如日期，用户类型
	 * @return
	 */
	@RequestMapping(value="/addFeedback", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addFeedBack(
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="content",required=true) String content) {
        return feedBackService.addFeedBack(content,token);
    }
	
	//管理员删除用户的个人信息
	@RequestMapping(value="/admin/delete", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteFeedBackByAdmin(
    		@RequestParam(value="id",required=true) Long id,
    		@RequestParam(value="token",required=true) String token) {
	        return feedBackService.deleteFeedBack(id,token);
    }
	
	//管理员获取用户列表
	@RequestMapping(value="/admin/getFeedBackList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<FeedBack>> getFeedBackListByAdmin(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute FeedBack feedBack,
    		@RequestParam(value="token",required=true) String token) {
        return feedBackService.getFeedBackList(pageIndex,pageSize,feedBack,token);
    }
	

}
