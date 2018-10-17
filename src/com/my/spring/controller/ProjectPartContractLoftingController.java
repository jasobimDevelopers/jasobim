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
import com.my.spring.service.ProjectPartContractLoftingService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/contractLofting")
public class ProjectPartContractLoftingController {
    @Autowired
    ProjectPartContractLoftingService projectPartContractLoftingService;  
   
    @RequestMapping(value="/admin/addProjectPartContractLofting", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ProjectPartContractLofting> addProjectPartContractLofting(
            @ModelAttribute ProjectPartContractLofting contractLofting,
            @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.addProjectPartContractLofting(token,contractLofting);
    }
    @RequestMapping(value="/admin/deleteProjectPartContractLofting",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProjectPartContractLofting(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.deleteProjectPartContractLoftingById(token,id);
    }

    @RequestMapping(value="/admin/getProjectPartContractLoftingList", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectPartContractLofting>> getProjectPartContractLoftingList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProjectPartContractLofting contractLofting,
            @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.getProjectPartContractLoftingList(pageIndex,pageSize,contractLofting,token);
    }
    @RequestMapping(value="/admin/getProjectPartContractLoftingById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ProjectPartContractLofting> getProjectPartContractLoftingById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return projectPartContractLoftingService.getProjectPartContractLoftingById(token,id);
    }

}
