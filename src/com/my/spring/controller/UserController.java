package com.my.spring.controller;

import com.my.spring.model.UserEntity;
import com.my.spring.service.UserService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 用户接口
 */
@Controller
@RequestMapping(value="api/user")
public class UserController {
    @Autowired
    UserService userService;
    /**
     * 用户注册接口
     */
    @RequestMapping(value="addUser", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addUser(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "token",required = false) String token){
        return userService.addUser(user);
    }
    /**
     * 用户删除接口
     */
    @RequestMapping(value="deleteUser")
    @ResponseBody
    public DataWrapper<Void> deleteUser(
            @RequestParam(value = "id",required = false) Long id,
            @RequestParam(value = "token",required = false) String token){
        return userService.deleteUser(id);
    }

    /**
     * 用户更新接口
     */
    @RequestMapping(value="updateUser",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateUser(
            @ModelAttribute UserEntity user,
            @RequestParam(value = "token",required = false) String token){
        System.out.println(user);
        return userService.updateUser(user);
    }


    /**
     * 所有用户信息获取接口
     */
    @RequestMapping(value="getUserList")
    @ResponseBody
    public DataWrapper<List<UserEntity>> getUserList(
            @RequestParam(value = "token",required = false) String token){
        return userService.getUserList();
    }
    /**
     * 用户登录接口
     */
    @RequestMapping(value="login",method= RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> login(
            @RequestParam(value="userName",required=true) String userName,
            @RequestParam(value = "password",required=true) String password
    ) {
        return userService.login(userName,password);
    }
}
