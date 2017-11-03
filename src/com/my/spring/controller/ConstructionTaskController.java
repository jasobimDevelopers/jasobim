 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ConstructionTask;
import com.my.spring.model.ConstructionTaskPojo;
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
@RequestMapping(value="api/constructionTask")
public class ConstructionTaskController {
    @Autowired
    ConstructionTaskService ConstructionTaskService;
    @RequestMapping(value="/admin/addConstructionTask", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addConstructionTask(
            @ModelAttribute ConstructionTask ps,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "files",required = false) MultipartFile[] files){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=ConstructionTaskService.addConstructionTask(ps,token,files,request);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    
    @RequestMapping(value="/deleteConstructionTask",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteConstructionTask(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ConstructionTaskService.deleteConstructionTask(id,token);
    }
    
    @RequestMapping(value="/admin/getConstructionTaskList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ConstructionTaskPojo>> getConstructionTaskList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value="state",required=false) Integer state,
    		@ModelAttribute ConstructionTask ps){
        return ConstructionTaskService.getConstructionTaskList(token,pageIndex,pageSize,ps,state);
    }
////通过用户id查找留言
    @RequestMapping(value="/getConstructionTaskListByUserId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ConstructionTask>> getConstructionTaskListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = true) String token){
        return ConstructionTaskService.getConstructionTaskListByUserId(userId,token);
    }
    @RequestMapping(value="/getConstructionTaskById",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ConstructionTaskPojo> getConstructionTaskById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = false) String token,
            @RequestParam(value = "weixin",required = false) String weixin){
        return ConstructionTaskService.getConstructionTaskById(id,token,weixin);
    }
    
    @RequestMapping(value="/updateConstructionTask",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateConstructionTask(
    		@ModelAttribute ConstructionTask ps,
            @RequestParam(value = "token",required = true) String token){
        return ConstructionTaskService.updateConstructionTask(ps, token);
    }
    @RequestMapping(value="/exportConstructionTaskById",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportConstructionTaskById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = false) String token){
        return ConstructionTaskService.exportConstructionTaskById(id,token);
    }
   
}
