 package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProjectStudy;
import com.my.spring.model.ProjectStudyPojo;
import com.my.spring.service.ProjectStudyService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/projectStudy")
public class ProjectStudyController {
    @Autowired
    ProjectStudyService projectStudyService;
    @RequestMapping(value="/admin/addProjectStudy", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProjectStudy(
            @ModelAttribute ProjectStudy ps,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=projectStudyService.addProjectStudy(ps,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deleteProjectStudy",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProjectStudy(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return projectStudyService.deleteProjectStudy(id,token);
    }

    @RequestMapping(value="/admin/getProjectStudyList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectStudyPojo>> getProjectStudyList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProjectStudy ps){
        return projectStudyService.getProjectStudyList(token,pageIndex,pageSize,ps);
    }
    ////通过用户id查找留言
    @RequestMapping(value="/getProjectStudyListByUserId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectStudy>> getNewsListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = true) String token){
        return projectStudyService.getProjectStudyListByUserId(userId,token);
    }
   
}
