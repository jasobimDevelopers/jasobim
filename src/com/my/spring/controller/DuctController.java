package com.my.spring.controller;

import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Duct;
import com.my.spring.service.DuctService;
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
@RequestMapping(value="api/duct")
public class DuctController {
    @Autowired
    DuctService ductService;
    @RequestMapping(value="/addDuct", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addDuct(
            @ModelAttribute Duct Duct,
            @RequestParam(value = "token",required = true) String token){
        return ductService.addDuct(Duct,token);
    }
    @RequestMapping(value="/deleteDuct")
    @ResponseBody
    public DataWrapper<Void> deleteDuct(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ductService.deleteDuct(id,token);
    }


    @RequestMapping(value="/getDuctList")
    @ResponseBody
    public DataWrapper<List<Duct>> getDuctList(){
        return ductService.getDuctList();
    }
    @RequestMapping(value="/admin/getDuctByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Duct> getDuctByProjectId(
    		@RequestParam(value = "projectId",required = true) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return ductService.getDuctByProjectId(projectId,token);
    }
    /*
     *预制化的信息上传
     */
    
    @RequestMapping(value="/admin/uploadDuct", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> uploadDuct(
            @RequestParam(value = "fileList", required = false) MultipartFile[] fileList,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request){
    	String filePath = "/fileupload/ducts";
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	for(int i=0;i<fileList.length;i++){
    		if(ductService.batchImport(filePath, fileList[i],token,request,projectId)){
            	dataWrapper.setErrorCode(ErrorCodeEnum.No_Error);
            }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
    	}
    	
        return dataWrapper;
    }
}
