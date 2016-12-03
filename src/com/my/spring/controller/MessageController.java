 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Message;
import com.my.spring.model.MessagePojo;
import com.my.spring.service.MessageService;
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
@RequestMapping(value="api/message")
public class MessageController {
    @Autowired
    MessageService messageService;
    @RequestMapping(value="/admin/addMessage", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMessage(
            @ModelAttribute Message message,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile[] file){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=messageService.addMessage(message,token,file,request);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteMessage",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteMessage(
            @RequestParam(value = "id",required = true) Long id,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return messageService.deleteMessage(id,token,request);
    }

    @RequestMapping(value="/admin/updateMessage",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMessage(
            @ModelAttribute Message message,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(message);
        return messageService.updateMessage(message,token);
    }


    @RequestMapping(value="/admin/getMessageList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MessagePojo>> getMessageList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Message message){
        return messageService.getMessageList(token,pageIndex,pageSize,message);
    }
    ////通过用户id查找留言
    @RequestMapping(value="/getMessageListByUserId")
    @ResponseBody
    public DataWrapper<List<Message>> getMessageListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = true) String token){
        return messageService.getMessageListByUserId(userId,token);
    }
    ////通过留言id查找留言
    @RequestMapping(value="/admin/getMessageById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Message> getMessageListById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return messageService.getMessageListById(id,token);
    }
    ////通过问题id查找留言
    @RequestMapping(value="/getMessageListByQuestionId")
    @ResponseBody
    public DataWrapper<List<Message>> getMessageListByQuestionId(
    		@RequestParam(value = "questionId",required = true) Long questionId,
            @RequestParam(value = "token",required = true) String token){
        return messageService.getMessageListByQuestionId(questionId,token);
    }
}
