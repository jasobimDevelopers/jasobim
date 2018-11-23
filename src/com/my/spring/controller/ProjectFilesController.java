package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.ProjectFiles;
import com.my.spring.model.ProjectFilesPojo;
import com.my.spring.service.ProjectFilesService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="api/projectFiles")
public class ProjectFilesController {
    @Autowired
    ProjectFilesService projectFilesService;

    @RequestMapping(value="/admin/uploadProjectFiles", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addProjectFiles(
            @ModelAttribute ProjectFiles paper,
            @RequestParam(value = "fileList", required = true) MultipartFile[] fileList,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	for(int i=0;i<fileList.length;i++){
    		dataWrapper=projectFilesService.addProjectFiles(paper, token, fileList[i], request);
    		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	//return dataWrapper;
            }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
    	}
        return dataWrapper;
    }
    
    
    @RequestMapping(value="/admin/deleteProjectFiles",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteProjectFilesByAdmin(
            @RequestParam(value = "id",required = true) String id,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return projectFilesService.deleteProjectFiles(id,token,request);
    }
    
    


    @RequestMapping(value="/admin/getProjectFilesLists",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<ProjectFilesPojo>> getProjectFilesLists(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute ProjectFiles paper,
    		@RequestParam(value="token",required=true) String token){
        return projectFilesService.getProjectFilesList(token,pageIndex,pageSize,paper);
    }
   
    
}
