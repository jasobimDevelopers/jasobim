package com.my.spring.controller;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogPojo;
import com.my.spring.service.UserLogService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/userLog")
public class UserLogController {
	@Autowired
	UserLogService userLogService;
	
	/**
	 * 
	 * @param userName、password、realName   //必须
	 * @param email、tel可有可无
	 * 其他参数不需要，由程序指定，如日期，用户类型
	 * @return
	 */
	@RequestMapping(value="/addUserLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addUserLog(
    		@RequestParam(value="token",required=true) String token,
    		@ModelAttribute UserLog userLog) {
        return userLogService.addUserLog(userLog,token);
    }
	
	
	@RequestMapping(value="/admin/deleteUserLog", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteUserLogByAdmin(
    		@RequestParam(value="userLogId",required=true) String userLogId,
    		@RequestParam(value="token",required=true) String token) {
	        return userLogService.deleteUserLog(userLogId,token);
    }

	//管理员获取用户列表
	@RequestMapping(value="/admin/getUserLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserLogPojo>> getUserLogListByAdmin(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute UserLog UserLog,
    		@RequestParam(value="token",required=true) String token) {
        return userLogService.getUserLogList(pageIndex,pageSize,UserLog,token);
    }
	

}
