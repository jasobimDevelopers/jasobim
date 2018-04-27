package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.UserTeam;
import com.my.spring.model.UserTeamPojo;
import com.my.spring.service.UserTeamService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/userTeam")
public class UserTeamController {
    @Autowired
    UserTeamService userTeamService;  
    /*
     *模型的原始构件信息上传
     */
    @RequestMapping(value="/admin/addUserTeam", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addUserTeam(
    		@ModelAttribute UserTeam userTeam,
            @RequestParam(value = "token",required = true) String token){
        return userTeamService.addUserTeam(token, userTeam);
    }
    
    @RequestMapping(value="/admin/updateUserTeam", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateUserTeam(
    		@ModelAttribute UserTeam userTeam,
            @RequestParam(value = "token",required = true) String token){
        return userTeamService.updateUserTeam(token, userTeam);
    }
  
    
    
    @RequestMapping(value="/admin/deleteUserTeam")
    @ResponseBody
    public DataWrapper<Void> deleteUserTeam(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return userTeamService.deleteUserTeamById(token,id);
    }
  


    @RequestMapping(value="/admin/getUserTeamList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserTeamPojo>> getItemList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute UserTeam item,
            @RequestParam(value = "token",required = false) String token){
        return userTeamService.getUserTeamList(pageIndex,pageSize,item,token);
    }
   
}
