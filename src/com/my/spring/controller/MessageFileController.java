package com.my.spring.controller;

import com.my.spring.model.MessageFile;
import com.my.spring.service.MessageFileService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/messageFile")
public class MessageFileController {
    @Autowired
    MessageFileService messageFileService;
    @RequestMapping(value="addMessageFile", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMessageFile(
            @ModelAttribute MessageFile MessageFile,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "file", required = false) MultipartFile file){
        return messageFileService.addMessageFile(MessageFile,token,file);
    }
    @RequestMapping(value="deleteMessageFile")
    @ResponseBody
    public DataWrapper<Void> deleteMessageFile(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "fileid",required = true) Long fileid,
            @RequestParam(value = "token",required = true) String token){
        return messageFileService.deleteMessageFile(id,fileid,token);
    }



    @RequestMapping(value="getMessageFileList")
    @ResponseBody
    public DataWrapper<List<MessageFile>> getMessageFileList(
            @RequestParam(value = "token",required = true) String token){
        return messageFileService.getMessageFileList(token);
    }
    ////通过用户id查找留言
    @RequestMapping(value="getMessageFileListByUserId")
    @ResponseBody
    public DataWrapper<List<MessageFile>> getMessageFileListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = true) String token){
        return messageFileService.getMessageFileListByUserId(userId,token);
    }
}
