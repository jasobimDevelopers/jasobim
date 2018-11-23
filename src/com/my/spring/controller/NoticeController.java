package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.service.NoticeService;
import com.my.spring.utils.DataWrapperDiy;
import com.my.spring.model.CommonNotice;

@Controller
@RequestMapping(value="api/notice")
public class NoticeController {
	 	@Autowired
	    NoticeService noticeService;
	   
	    @RequestMapping(value="/app/getAllNoticeList", method = RequestMethod.GET)
	    @ResponseBody
	    public DataWrapperDiy<List<CommonNotice>> getAllNoticeList(
	            @RequestParam(value = "token",required = false) String token,
	            @RequestParam(value = "pageSize",required = false) Integer pageSize,
	            @RequestParam(value = "pageIndex",required = false) Integer pageIndex){
	        return noticeService.getAllNoticeList(token, pageSize, pageIndex);
	    }
	    @RequestMapping(value="/app/getNotReadNum", method = RequestMethod.GET)
	    @ResponseBody
	    public DataWrapperDiy<Integer> getNotReadNum(
	            @RequestParam(value = "token",required = false) String token){
	        return noticeService.getNotReadNum(token);
	    }
}
