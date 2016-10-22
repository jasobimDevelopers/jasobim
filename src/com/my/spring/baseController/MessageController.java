package com.my.spring.baseController;

import com.my.spring.model.Message;
import com.my.spring.service.MessageService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/message")
public class MessageController {
    @Autowired
    MessageService messageService;
    @RequestMapping(value="addMessage", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMessage(
            @ModelAttribute Message message,
            @RequestParam(value = "token",required = false) String token){
        return messageService.addMessage(message);
    }
    @RequestMapping(value="deleteMessage")
    @ResponseBody
    public DataWrapper<Void> deleteMessage(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return messageService.deleteMessage(id);
    }

    @RequestMapping(value="updateMessage",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMessage(
            @ModelAttribute Message message,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(message);
        return messageService.updateMessage(message);
    }


    @RequestMapping(value="getMessageList")
    @ResponseBody
    public DataWrapper<List<Message>> getMessageList(
            @RequestParam(value = "token",required = false) String token){
        return messageService.getMessageList();
    }
}
