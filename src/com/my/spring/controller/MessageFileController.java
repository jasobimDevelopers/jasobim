package com.my.spring.controller;

import com.my.spring.model.MessageFile;
import com.my.spring.model.MessageFilePojo;
import com.my.spring.service.MessageFileService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="api/messageFile")
public class MessageFileController {
    @Autowired
    MessageFileService messageFileService;
    @RequestMapping(value="/addMessageFile", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<MessageFilePojo> addMessageFile(
            @ModelAttribute MessageFile MessageFile,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "file", required = false) MultipartFile file){
        return messageFileService.addMessageFile(MessageFile,token,file,request);
    }
    @RequestMapping(value="deleteMessageFile")
    @ResponseBody
    public DataWrapper<Void> deleteMessageFile(
            @RequestParam(value = "id",required = true) Long id,
            HttpServletRequest request,
            @RequestParam(value = "fileid",required = true) Long fileid,
            @RequestParam(value = "token",required = true) String token){
        return messageFileService.deleteMessageFile(id,fileid,token,request);
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
