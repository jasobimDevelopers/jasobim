package com.my.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.model.Reply;
import com.my.spring.model.ReplyPojo;
import com.my.spring.service.ReplyService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/reply")
public class ReplyController {
	@Autowired
	ReplyService replyService;
	
	/**
	 * 
	 * @param userName、password、realName   //必须
	 * @param email、tel可有可无
	 * 其他参数不需要，由程序指定，如日期，用户类型
	 * @return
	 */
	@RequestMapping(value="/addReply", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addReply(
    		@ModelAttribute Reply Reply,
    		@RequestParam(value = "pics",required = true) MultipartFile[] pics,
    		@RequestParam(value = "vois",required = true) MultipartFile[] vois,
            HttpServletRequest request,
    		@RequestParam(value="token",required=true) String token) {
        return replyService.addReply(token,pics,vois,request,Reply);
    }

	//管理员获取用户列表
	@RequestMapping(value="/admin/getReplyList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ReplyPojo>> getReplyListByAdmin(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Reply Reply,
    		@RequestParam(value="token",required=true) String token) {
        return replyService.getReplyList(pageIndex,pageSize,Reply,token);
    }
	

}
