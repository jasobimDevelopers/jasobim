package com.my.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.my.spring.enums.UserTypeEnum;
import com.my.spring.model.User;
import com.my.spring.model.UserPojo;
import com.my.spring.service.UserService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/user")
public class UserController {
	@Autowired
	UserService userService;
	
	/**
	 * 
	 * @param userName、password、realName   //必须
	 * @param email、tel可有可无
	 * 其他参数不需要，由程序指定，如日期，用户类型
	 * @return
	 */
	@RequestMapping(value="/register", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> register(
    		@ModelAttribute User user) {
        return userService.register(user);
    }
	@RequestMapping(value="/admin/addUser", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addUser(
    		@ModelAttribute User user,
    		HttpServletRequest request,
    		@RequestParam(value = "file", required = false) MultipartFile file,
    		@RequestParam(value="token",required=true) String token) {
//		MultipartFile file=null;
         return userService.addUser(user,token,file,request);
    }
	@RequestMapping(value="/findUserLike", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<User> findUserLike(
    		@ModelAttribute User user,
    		@RequestParam(value="token",required=true) String token) {
        return userService.findUserLike(user, token);
    }
	
	@RequestMapping(value="/login", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<UserPojo> Login(
    		HttpServletRequest request,
    		@RequestParam(value="username",required=true) String username,
    		@RequestParam(value="password",required=true) String password) {
		DataWrapper<UserPojo> test=userService.login(username, password);
		return test;
    }
	@RequestMapping(value="/findPass", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<User> FindPs(
    		HttpServletRequest request,
    		@RequestParam(value="userName",required=true) String userName,
    		@RequestParam(value="realName",required=true) String realName,
    		@RequestParam(value="tel",required=true) String tel,
    		@RequestParam(value="email",required=true) String email) {
		User user=new User();
		if(userName!=null){
			user.setUserName(userName);
		}else{
			return null;
		}
		if(email!=null){
			user.setEmail(email);

		}else{
			return null;
		}
		if(realName!=null){
			user.setRealName(realName);

		}else{
			return null;
		}
		if(tel!=null){
			user.setTel(tel);

		}else{
			return null;
		}
		
		DataWrapper<User> test=userService.FindPs(user);
		return test;
    }
	
	/**
	 * 
	 * @param realname、email、tel  //只修改这三个参数，且在非空的情况下修改，否则不修改
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/update", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> UpdateUser(
    		@ModelAttribute User user,
    		@RequestParam(value="token",required=true) String token) {
        return userService.updateUser(user, token);
    }
	/**
	 * 
	 * @param password///密码修改
	 * @param token
	 * @return
	 */
	@RequestMapping(value="/updatePsBySelf", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> UpdatePsBySelf(
    		@RequestParam(value="oldPs",required=true) String oldPs,
    		@RequestParam(value="newPs",required=true) String newPs,
    		@RequestParam(value="token",required=true) String token) {
        return userService.updateUserBySelf(oldPs, newPs, token);
    }
	
	//普通用户获取自己的个人详情
	@RequestMapping(value="/details", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<User> getUserDetails(
    		@RequestParam(value="token",required=true) String token) {
        return userService.getUserDetails(token);
    }
	  
	
	//管理员获取其他用户的个人详情
	@RequestMapping(value="/admin/getUserDetails", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<UserPojo> getUserDetailsByAdmin(
    		@RequestParam(value="userId",required=true) Long userId,
    		@RequestParam(value="token",required=true) String token) {
        return userService.getUserDetailsByAdmin(userId,token);
    }
	//管理员删除用户的个人信息
	@RequestMapping(value="/admin/deleteUser", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteUserByAdmin(
    		@RequestParam(value="userId",required=true) Long userId,
	    		@RequestParam(value="token",required=true) String token) {
	        return userService.deleteUser(userId,token);
    }
	
	//管理员获取用户列表
	@RequestMapping(value="/admin/getUserList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserPojo>> getUserListByAdmin(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute User user,
    		@RequestParam(value="token",required=true) String token) {
        return userService.getUserList(pageIndex,pageSize,user,token);
    }
	
	//修改用户权限
	@RequestMapping(value="/admin/changeUser/{userId}/type/{userType}", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> changeUserTypeByAdmin(
    		@PathVariable(value="userId") Long userId,
    		@PathVariable(value="userType") Integer userType,
    		@RequestParam(value="token",required=true) String token) {
		if (userType != 0 && userType != 1) {
			userType = UserTypeEnum.User.getType();
		}
        return userService.changeUserTypeByAdmin(userId,userType,token);
    }
	@RequestMapping(value="/admin/updateUser", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> changeUserTypeByAdmin(
    		@ModelAttribute User user,
    		HttpServletRequest request,
    		@RequestParam(value = "file", required = false) MultipartFile file,
    		@RequestParam(value="token",required=true) String token) {
   
		return userService.updateUserByAdmin(user, token,file,request);
    }
	

}
