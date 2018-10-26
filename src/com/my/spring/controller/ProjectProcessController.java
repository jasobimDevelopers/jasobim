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
import org.springframework.web.multipart.MultipartFile;
import com.my.spring.model.ProjectProcess;
import com.my.spring.model.ProjectProcessPojo;
import com.my.spring.service.ProjectProcessService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/projectProcess")
public class ProjectProcessController {
    @Autowired
    ProjectProcessService projectProcessService;  
   
    @RequestMapping(value="/admin/addProjectProcess", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ProjectProcess> addprojectProcess(
            @ModelAttribute ProjectProcess projectProcess,
            @RequestParam(value = "token",required = true) String token){
        return projectProcessService.addProjectProcess(token,projectProcess);
    }
    @RequestMapping(value="/admin/updateProjectProcess", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateprojectProcess(
            @ModelAttribute ProjectProcess projectProcess,
            @RequestParam(value = "token",required = true) String token){
        return projectProcessService.updateProjectProcess(token,projectProcess);
    }
    @RequestMapping(value="/admin/deleteprojectProcess",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteprojectProcess(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return projectProcessService.deleteProjectProcessById(token,id);
    }
   

    @RequestMapping(value="/admin/getprojectProcessList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectProcessPojo>> getprojectProcessList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProjectProcess projectProcess,
            @RequestParam(value = "token",required = false) String token){
        return projectProcessService.getProjectProcessList(pageIndex,pageSize,projectProcess,token);
    }
    @RequestMapping(value="/importProjectProcessByProjectId",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> importProjectProcessByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		HttpServletRequest request,
            @RequestParam(value = "file", required = true) MultipartFile file,
            @RequestParam(value = "token",required = false) String token){
        return projectProcessService.importProjectProcess(token,file,request,projectId);
    }

}