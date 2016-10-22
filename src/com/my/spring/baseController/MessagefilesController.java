package com.my.spring.baseController;

import com.my.spring.model.Messagefiles;
import com.my.spring.service.MessagefilesService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/messagefiles")
public class MessagefilesController {
    @Autowired
    MessagefilesService messagefilesService;
    @RequestMapping(value="addMessagefiles", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMessagefiles(
            @ModelAttribute Messagefiles messagefiles,
            @RequestParam(value = "token",required = false) String token){
        return messagefilesService.addMessagefiles(messagefiles);
    }
    @RequestMapping(value="deleteMessagefiles")
    @ResponseBody
    public DataWrapper<Void> deleteMessagefiles(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return messagefilesService.deleteMessagefiles(id);
    }

    @RequestMapping(value="updateMessagefiles",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMessagefiles(
            @ModelAttribute Messagefiles messagefiles,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(messagefiles);
        return messagefilesService.updateMessagefiles(messagefiles);
    }


    @RequestMapping(value="getMessagefilesList")
    @ResponseBody
    public DataWrapper<List<Messagefiles>> getMessagefilesList(
            @RequestParam(value = "token",required = false) String token){
        return messagefilesService.getMessagefilesList();
    }
}
