package com.my.spring.controller;

import com.my.spring.model.Project;
import com.my.spring.model.ProjectIds;
import com.my.spring.model.ProjectPojo;
import com.my.spring.service.ProjectService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.List;
import javax.servlet.http.HttpServletRequest;


@Controller
@RequestMapping(value="api/project")
public class ProjectController {
    @Autowired
    ProjectService projectService;
    @RequestMapping(value="/admin/addProject", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ProjectPojo> addProject(
            @ModelAttribute Project project,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "modelFile", required = false) MultipartFile[] modelFile,
            @RequestParam(value = "picFile", required = false) MultipartFile[] picFile){
        return projectService.addProject(project,token,modelFile,picFile,request);
    }
    
    @RequestMapping(value="/admin/deleteProject")
    @ResponseBody
    public DataWrapper<Void> deleteProject(
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return projectService.deleteProject(projectId,token,request);
    }

    @RequestMapping(value="/admin/updateProject",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateProject(
            @ModelAttribute Project project,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "modelFile", required = false) MultipartFile[] modelFile,
            @RequestParam(value = "picFile", required = false) MultipartFile[] picFile) {
        return projectService.updateProject(project,token,modelFile,picFile,request);
    }

    @RequestMapping(value="/admin/getProjectList",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectPojo>> getProjectList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value="content",required=false) String content,
    		@ModelAttribute Project project,
    		HttpServletRequest request,
    		@RequestParam(value="token",required=true) String token) throws IOException
    {
        return projectService.getProjectList(pageIndex,pageSize,project,token,content,request);
    }
    @RequestMapping(value="/admin/getProjectDetails",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ProjectPojo> getProjectDetailsByAdmin(
    		@RequestParam(value="projectId",required=true) Long projectId,
    		@RequestParam(value="token",required=true) String token){
        return projectService.getProjectDetailsByAdmin(projectId,token);
    }
    //////游客新建项目
    @RequestMapping(value="/visitor/addProject", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ProjectPojo> addVisitorProject(
            @ModelAttribute Project project,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "modelFile", required = false) MultipartFile[] modelFile,
            @RequestParam(value = "picFile", required = false) MultipartFile[] picFile){
        return projectService.addProject(project,token,modelFile,picFile,request);
    }
    ////更新标准工时接口
    @RequestMapping(value="/visitor/updateWorkHour", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateWorkHour(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "workHour", required = true) Integer workHour){
        return projectService.updateWorkHour(projectId,token,workHour);
    }
    /////获取项目标准工时接口
    @RequestMapping(value="/admin/getProjectHour",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> getProjectHour(
    		@RequestParam(value="projectId",required=true) Long projectId,
    		@RequestParam(value="token",required=true) String token){
        return projectService.getProjectHour(projectId,token);
    }
/////获取项目标准工时接口
    @RequestMapping(value="/admin/getAllProjectId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectIds>> getAllProjectId(
    		@RequestParam(value="token",required=true) String token){
        return projectService.getAllProjectId(token);
    }
}
