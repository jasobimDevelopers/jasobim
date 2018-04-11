 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ConstructionLog;
import com.my.spring.model.ConstructionLogPojo;
import com.my.spring.model.ConstructionTask;
import com.my.spring.model.ConstructionTaskPojo;
import com.my.spring.service.ConstructionLogService;
import com.my.spring.service.ConstructionTaskService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;



@Controller
@CrossOrigin
@RequestMapping(value="api/constructionLog")
public class ConstructionLogController {
    @Autowired
    ConstructionLogService constructionLogService;
    @RequestMapping(value="/web/addConstructionLog", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addConstructionLog(
            @ModelAttribute ConstructionLog ps,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "files",required = false) MultipartFile[] files){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=constructionLogService.addConstructionLog(ps,token,files,request);
        return dataWrapper;
    }
    
    @RequestMapping(value="/deleteConstructionLog",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteConstructionLog(
            @RequestParam(value = "id",required = true) String id,
            @RequestParam(value = "token",required = true) String token){
        return constructionLogService.deleteConstructionLog(id,token);
    }
    @RequestMapping(value="/updateConstructionLog",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateConstructionLog(
    		@ModelAttribute ConstructionLog ps,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "files",required = false) MultipartFile[] files,
            HttpServletRequest request){
        return constructionLogService.updateConstructionLog(ps,token,files,request);
    }
    
    @RequestMapping(value="/web/getConstructionLogList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ConstructionLogPojo>> getConstructionLogList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ConstructionLog ps){
        return constructionLogService.getConstructionLogList(token,pageIndex,pageSize,ps);
    }
    
   
}
