package com.my.spring.controller;

import com.my.spring.model.ProjectTender;
import com.my.spring.model.ProjectTenderPojo;
import com.my.spring.service.ProjectTenderService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value="api/projectTender")
public class ProjectTenderController {
    @Autowired
    ProjectTenderService projectTenderService;
    @RequestMapping(value="/addProjectTender", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProjectTender(
            @ModelAttribute ProjectTender ProjectTender,
            @RequestParam(value = "token",required = true) String token){
        return projectTenderService.addProjectTender(ProjectTender,token);
    }
    @RequestMapping(value="/updateProjectTender", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateProjectTender(
            @ModelAttribute ProjectTender ProjectTender,
            @RequestParam(value = "token",required = true) String token){
        return projectTenderService.updateProjectTender(ProjectTender,token);
    }
    @RequestMapping(value="/deleteProjectTender", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProjectTender(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return projectTenderService.deleteProjectTender(id,token);
    }

    /*
     * 设置标段获取接口
     * */
    @RequestMapping(value="/admin/getProjectTenderList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectTenderPojo>> getProjectTenderByProjectId(
    		@RequestParam(value = "projectId",required = false) Long projectId,
    		@RequestParam(value = "pageSize",required = false) Integer pageSize,
    		@RequestParam(value = "pageIndex",required = false) Integer pageIndex,
    		@RequestParam(value = "token",required = true) String token){
        return projectTenderService.getProjectTenderList(projectId,token,pageSize,pageIndex);
    }
    
    /*
     * 施工日志标段获取
     * */
    @RequestMapping(value="/getProjectTenderList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectTenderPojo>> getProjectTendersByProjectId(
    		@RequestParam(value = "projectId",required = false) Long projectId,
    		@RequestParam(value = "pageSize",required = false) Integer pageSize,
    		@RequestParam(value = "pageIndex",required = false) Integer pageIndex,
    		@RequestParam(value = "token",required = true) String token){
        return projectTenderService.getProjectTendersByProjectId(projectId,token,pageSize,pageIndex);
    }
    
}
