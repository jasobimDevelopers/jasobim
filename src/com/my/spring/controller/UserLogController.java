package com.my.spring.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.DuctPojos;
import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogPojo;
import com.my.spring.model.UserLogPojos;
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
    		@RequestParam(value = "dateStart",required = false) String dateStart,
            @RequestParam(value = "dateFinished",required = false) String dateFinished,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute UserLog UserLog,
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="searchContent",required=false) String searchContent) {
        return userLogService.getUserLogList(pageIndex,pageSize,UserLog,token,dateStart,dateFinished,searchContent);
    }
	  
    @RequestMapping(value="/getUserLogCountSum",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserLogPojos>> getUserLogSum(
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="startTime",required=false) String startTime,
    		@RequestParam(value="finishedTime",required=false) String finishedTime,
    		@RequestParam(value="projectId",required=false) Long projectId,
    		@RequestParam(value="projectPart",required=false) Integer projectPart,
    		@RequestParam(value="systemType",required=false) Integer systemType){
        return userLogService.getUserLogCountSum(token,startTime,finishedTime,projectId,projectPart,systemType);
    }
    /**
     * 预制化文件导出
     * 
     */
    @RequestMapping(value="/admin/exportUserLog",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportUserLog(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "dateStart",required = false) String dateStart,
            @RequestParam(value = "dateFinished",required = false) String dateFinished,
            HttpServletRequest request){
        return userLogService.exportUserLog(token, request, dateStart, dateFinished);
    }
    
    @RequestMapping(value="/admin/writeUserLogInFile",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<String> writeUserLogInFile(
    		@ModelAttribute UserLog UserLog){
        return userLogService.writeUserLogInFile(UserLog);
    }
    
    @RequestMapping(value="/admin/readUserLogFromFile",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserLog>> readUserLogFromFile(){
        return userLogService.readUserLogFromFile();
    }
    
    

}
