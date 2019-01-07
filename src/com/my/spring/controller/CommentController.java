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

import com.my.spring.model.Comment;
import com.my.spring.model.CommentPojo;
import com.my.spring.service.CommentService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/comment")
public class CommentController {
	@Autowired
	CommentService CommentService;
	
	/**
	 * 
	 * @param userName、password、realName   //必须
	 * @param email、tel可有可无
	 * 其他参数不需要，由程序指定，如日期，用户类型
	 * @return
	 */
	@RequestMapping(value="/addComment", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addComment(
    		@ModelAttribute Comment comment,
    		@RequestParam(value = "pics",required = false) MultipartFile[] pics,
    		@RequestParam(value = "vois",required = false) MultipartFile[] vois,
            HttpServletRequest request,
    		@RequestParam(value="token",required=true) String token) {
        return CommentService.addComment(token,pics,vois,request,comment);
    }

	//管理员获取用户列表
	@RequestMapping(value="/admin/getCommentList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<CommentPojo>> getCommentListByAdmin(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Comment Comment,
    		@RequestParam(value="token",required=true) String token) {
        return CommentService.getCommentList(pageIndex,pageSize,Comment,token);
    }
	

}
