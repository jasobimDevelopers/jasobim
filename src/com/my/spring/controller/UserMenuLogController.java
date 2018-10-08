package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.UserMenuLog;
import com.my.spring.service.UserMenuLogService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/userMenuLog")
public class UserMenuLogController {
    @Autowired
    UserMenuLogService UserMenuLogService;  
   
    @RequestMapping(value="/admin/addUserMenuLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<UserMenuLog> addUserMenuLog(
            @ModelAttribute UserMenuLog UserMenuLog,
            @RequestParam(value = "token",required = true) String token){
        return UserMenuLogService.addUserMenuLog(token,UserMenuLog);
    }
    @RequestMapping(value="/admin/updateUserMenuLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateUserMenuLog(
    		@ModelAttribute UserMenuLog UserMenuLog,
            @RequestParam(value = "token",required = true) String token){
        return UserMenuLogService.updateUserMenuLog(token,UserMenuLog);
    }
    @RequestMapping(value="/admin/deleteUserMenuLog",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteUserMenuLog(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return UserMenuLogService.deleteUserMenuLogById(token,id);
    }
   
    @RequestMapping(value="/admin/getUserMenuLogByUserId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserMenuLog>> getUserMenuLogById(
            @RequestParam(value = "token",required = true) String token){
        return UserMenuLogService.getUserMenuLogList(token);
    }

}
