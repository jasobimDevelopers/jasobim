package com.my.spring.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.my.spring.model.ProjectOutputValue;
import com.my.spring.service.ProjectOutputValueService;
import com.my.spring.utils.DataWrapper;

@Controller
@RequestMapping(value="api/projectOutputValue")
public class ProjectOutputValueController {
    @Autowired
    ProjectOutputValueService ProjectOutputValueService;  
   
    @RequestMapping(value="/admin/addProjectOutputValue", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<ProjectOutputValue> addProjectOutputValue(
            @ModelAttribute ProjectOutputValue ProjectOutputValue,
            @RequestParam(value = "token",required = true) String token){
        return ProjectOutputValueService.addProjectOutputValue(token,ProjectOutputValue);
    }
    @RequestMapping(value="/admin/updateProjectOutputValue", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateProjectOutputValue(
            @ModelAttribute ProjectOutputValue ProjectOutputValue,
            @RequestParam(value = "token",required = true) String token){
        return ProjectOutputValueService.updateProjectOutputValue(token,ProjectOutputValue);
    }
    @RequestMapping(value="/admin/deleteProjectOutputValue",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProjectOutputValue(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ProjectOutputValueService.deleteProjectOutputValueById(token,id);
    }
   

    @RequestMapping(value="/admin/getProjectOutputValueByProjectId", method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ProjectOutputValue> getProjectOutputValueByProjectId(
    		@RequestParam(value="projectId",required=true) Long projectId,
            @RequestParam(value = "token",required = true) String token){
        return ProjectOutputValueService.getProjectOutputValueByProjectId(projectId,token);
    }
    @RequestMapping(value="/admin/getProjectOutputValueById",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<ProjectOutputValue> getProjectOutputValueById(
    		@RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = false) String token){
        return ProjectOutputValueService.getProjectOutputValueById(token,id);
    }

}
