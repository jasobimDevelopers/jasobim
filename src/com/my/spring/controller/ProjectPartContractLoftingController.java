package com.my.spring.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.my.spring.model.ProjectPartContractLofting;
import com.my.spring.model.ProjectPartContractLoftingPojo;
import com.my.spring.service.ProjectPartContractLoftingService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/projectPartContractLofting")
public class ProjectPartContractLoftingController {
    @Autowired
    ProjectPartContractLoftingService projectPartContractLoftingService;  
   
    @RequestMapping(value="/admin/addProjectPartContractLofting", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ProjectPartContractLofting> addProjectPartContractLofting(
            @ModelAttribute ProjectPartContractLofting contractLofting,
            @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.addProjectPartContractLoftingList(token,contractLofting);
    }
    @RequestMapping(value="/deleteProjectPartContractLoftingByName",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProjectPartContractLoftingByName(
    		 @RequestParam(value = "name",required = true) String name,
    		 @RequestParam(value = "projectId",required = true) Long projectId,
             @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.deleteProjectPartContractLoftingByName(token,name,projectId);
    }

    @RequestMapping(value="/admin/getProjectPartContractLoftingList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectPartContractLoftingPojo>> getProjectPartContractLoftingList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProjectPartContractLofting contractLofting,
            @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.getProjectPartContractLoftingList(pageIndex,pageSize,contractLofting,token);
    }
    @RequestMapping(value="/getDefaultList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectPartContractLoftingPojo>> getDefaultList(
    		@RequestParam(value="projectId",required=true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.getDefaultList(projectId,token);
    }
    @RequestMapping(value="/updateProjectPartContractLofting",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ProjectPartContractLofting> updateProjectPartContractLofting(
    		@ModelAttribute ProjectPartContractLofting contractLofting,
            @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.updateProjectPartContractLofting(token,contractLofting);
    }

}
