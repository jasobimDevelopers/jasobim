package com.my.spring.controller;

import com.my.spring.model.Project;
import com.my.spring.model.ProjectPojo;
import com.my.spring.service.ProjectService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @RequestMapping(value="/admin/addProject", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProject(
            @ModelAttribute Project project,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "modelFile", required = false) MultipartFile modelFile,
            @RequestParam(value = "picFile", required = false) MultipartFile picFile){
        return projectService.addProject(project,token,modelFile,picFile,request);
    }
    @RequestMapping(value="/findProjectLike", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Project>> findProjectLike(
            @ModelAttribute Project project,
            @RequestParam(value = "token",required = true) String token){
        return projectService.findProjectLike(project,token);
    }
    
    @RequestMapping(value="/admin/deleteProject")
    @ResponseBody
    public DataWrapper<Void> deleteProject(
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request,
            @RequestParam(value = "modelid",required = false) Long modelid,
            @RequestParam(value = "token",required = true) String token){
        return projectService.deleteProject(projectId,token,modelid,request);
    }

    @RequestMapping(value="/admin/updateProject",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateProject(
            @ModelAttribute Project project,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "modelFile", required = false) MultipartFile modelFile,
            @RequestParam(value = "picFile", required = false) MultipartFile picFile){
        return projectService.updateProject(project,token,modelFile,picFile,request);
    }

    @RequestMapping(value="/admin/getProjectList",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectPojo>> getProjectList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Project project,
    		@RequestParam(value="token",required=true) String token)
    {
        return projectService.getProjectList(pageIndex,pageSize,project,token);
    }
    @RequestMapping(value="/admin/getProjectDetails",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Project> getProjectDetailsByAdmin(
    		@RequestParam(value="projectId",required=true) Long projectId,
    		@RequestParam(value="token",required=true) String token){
        return projectService.getProjectDetailsByAdmin(projectId,token);
    }
    
}
