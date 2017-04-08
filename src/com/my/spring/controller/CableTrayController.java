package com.my.spring.controller;

import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.CableTray;
import com.my.spring.service.CableTrayService;
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
@RequestMapping(value="api/cableTray")
public class CableTrayController {
    @Autowired
    CableTrayService CableTrayService;
    
    //////桥架excel导入  
    @RequestMapping(value="/admin/uploadCableTray", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadCableTray(
            @RequestParam(value = "fileList", required = false) MultipartFile[] fileList,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request){
    	String filePath = "/fileupload/cableTray";
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	for(int i=0;i<fileList.length;i++){
    		if(CableTrayService.batchImports(filePath, fileList[i],token,request,projectId)){
            	dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
            }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
    	}
    	
        return dataWrapper;
    }
    @RequestMapping(value="/addCableTray", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addCableTray(
            @ModelAttribute CableTray CableTray,
            @RequestParam(value = "token",required = true) String token){
        return CableTrayService.addCableTray(CableTray,token);
    }
    @RequestMapping(value="/deleteCableTray")
    @ResponseBody
    public DataWrapper<Void> deleteCableTray(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return CableTrayService.deleteCableTray(id,token);
    }


    @RequestMapping(value="/getCableTrayList")
    @ResponseBody
    public DataWrapper<List<CableTray>> getCableTrayList(){
        return CableTrayService.getCableTrayList();
    }
    @RequestMapping(value="/admin/getCableTrayByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<CableTray> getCableTrayByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return CableTrayService.getCableTrayByProjectId(projectId,token);
    }
    @RequestMapping(value="/updateCableTray",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateCableTray(
    		@ModelAttribute CableTray CableTray,
    		@RequestParam(value = "token",required = true) String token) {
    	return CableTrayService.updateCableTray(CableTray, token);
    	
    }
}
