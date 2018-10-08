package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.UserIndex;
import com.my.spring.service.UserIndexService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/userIndex")
public class UserIndexController {
    @Autowired
    UserIndexService userIndexService;  
   
   
    @RequestMapping(value="/getUserIndexList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserIndex>> getUserIndexList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute UserIndex UserIndex,
            @RequestParam(value = "token",required = true) String token){
        return userIndexService.getUserIndexList(pageIndex,pageSize,UserIndex,token);
    }
    @RequestMapping(value="/addUserIndexList", method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addUserIndexList(
    		@RequestParam(value = "newList",required = true) String newList,////
            @RequestParam(value = "token",required = true) String token){
        return userIndexService.addUserIndexList(newList,token);
    }

}
