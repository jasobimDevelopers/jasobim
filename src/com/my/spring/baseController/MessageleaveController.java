package com.my.spring.baseController;

import com.my.spring.model.Messageleave;
import com.my.spring.service.MessageleaveService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/messageleave")
public class MessageleaveController {
    @Autowired
    MessageleaveService messageleaveService;
    @RequestMapping(value="addMessageleave", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMessageleave(
            @ModelAttribute Messageleave messageleave,
            @RequestParam(value = "token",required = false) String token){
        return messageleaveService.addMessageleave(messageleave);
    }
    @RequestMapping(value="deleteMessageleave")
    @ResponseBody
    public DataWrapper<Void> deleteMessageleave(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return messageleaveService.deleteMessageleave(id);
    }

    @RequestMapping(value="updateMessageleave",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMessageleave(
            @ModelAttribute Messageleave messageleave,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(messageleave);
        return messageleaveService.updateMessageleave(messageleave);
    }


    @RequestMapping(value="getMessageleaveList")
    @ResponseBody
    public DataWrapper<List<Messageleave>> getMessageleaveList(
            @RequestParam(value = "token",required = false) String token){
        return messageleaveService.getMessageleaveList();
    }
}
