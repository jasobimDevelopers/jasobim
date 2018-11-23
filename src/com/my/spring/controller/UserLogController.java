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
import com.my.spring.model.UserLog;
import com.my.spring.model.UserLogMonth;
import com.my.spring.model.UserLogPart;
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
    		@RequestParam(value="projectIds",required=false) String projectIds,
    		@RequestParam(value="userIds",required=false) String userIds,
    		@RequestParam(value="userTypes",required=false) String userTypes,
    		@ModelAttribute UserLog UserLog,
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="searchContent",required=false) String searchContent) {
        return userLogService.getUserLogList(pageIndex,pageSize,UserLog,token,dateStart,dateFinished,searchContent,projectIds,userIds,userTypes);
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
    /**
     * 
     * 新版本的web打点页面接口
     * 项目分析之各功能区域占比
     * */
    @RequestMapping(value="/admin/countUserLogByPart",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserLogPart>> countUserLogByMonth(
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="startTime",required=false) String startTime,
    		@RequestParam(value="finishedTime",required=false) String finishedTime,
    		@RequestParam(value="projectIdList",required=false) String projectIdList){
        return userLogService.countUserLogByPart(token,startTime,finishedTime,projectIdList);
    }
    /**
     * 
     * 新版本的web打点页面接口
     * 项目分析之各项目按月统计
     * */
    @RequestMapping(value="/admin/countUserLogByMonth",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserLogMonth>> countUserLogByMonth(
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="projectIdList",required=false) String projectIdList,
    		@RequestParam(value="year",required=true) Integer year){
        return userLogService.countUserLogByMonth(token,projectIdList,year);
    }
    /**
     * 新版本用户个人信息统计
     * 通过按月份查询统计
     * */
    @RequestMapping(value="/admin/countUserLogByUserId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserLogMonth>> countUserLogByUserId(
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="userId",required=true) Long userId,
    		@RequestParam(value="year",required=true) Integer year){
        return userLogService.countUserLogByUserId(token,userId,year);
    }
    /**
     * 
     * 新版本的web打点页面接口
     * 个人分析之各功能区域占比
     * */
    @RequestMapping(value="/admin/countPersonLogByPart",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<UserLogPart>> countPersonLogByPart(
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="startTime",required=false) String startTime,
    		@RequestParam(value="finishedTime",required=false) String finishedTime,
    		@RequestParam(value="userId",required=true) Long userId){
        return userLogService.countPersonLogByPart(token,startTime,finishedTime,userId);
    }
    /**
     * 
     * 新版本打点记录项目、个人筛选分析表格导出
     * */
    @RequestMapping(value="/admin/exportUserLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportUserLogList(
    		@RequestParam(value = "dateStart",required = false) String dateStart,
            @RequestParam(value = "dateFinished",required = false) String dateFinished,
    		@RequestParam(value="projectIds",required=false) String projectIds,
    		@RequestParam(value="userIds",required=false) String userIds,
    		@RequestParam(value="token",required=true) String token) {
        return userLogService.exportUserLogList(token,dateStart,dateFinished,projectIds,userIds);
    }
    /**
     * 
     * 新版本打点记录项目条形图、饼图分析表格导出
     * */
    @RequestMapping(value="/admin/exportUserLogEcharts", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportUserLogEcharts(
    		@RequestParam(value = "startTime",required = false) String startTime,
            @RequestParam(value = "finishedTime",required = false) String finishedTime,
    		@RequestParam(value="projectIds",required=false) String projectIds,
    		@RequestParam(value="userIds",required=false) String userIds,
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="year",required=true) Integer year) {
        return userLogService.exportUserLogEcharts(token,startTime,finishedTime,projectIds,userIds,year);
    }
    
    /**
     * 
     * 新版本个人打点记录项目分析表格导出
     * */
    @RequestMapping(value="/admin/exportPersonLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportPersonLogList(
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="startTime",required=false) String startTime,
    		@RequestParam(value="finishedTime",required=false) String finishedTime,
    		@RequestParam(value="userId",required=true) Long userId,
    		@RequestParam(value="year",required=true) Integer year) {
        return userLogService.exportPersonLogList(token,userId,year,startTime,finishedTime);
    }
}
