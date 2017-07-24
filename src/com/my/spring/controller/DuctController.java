package com.my.spring.controller;

import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Duct;
import com.my.spring.model.DuctPojo;
import com.my.spring.model.DuctPojos;
import com.my.spring.service.DuctService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

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
    @RequestMapping(value="/deleteDuct",method=RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> deleteDuct(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return ductService.deleteDuct(id,token);
    }
    @RequestMapping(value="/getDuctList",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<DuctPojo>> getDuctList(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value = "dateStart",required = false) String dateStart,
            @RequestParam(value = "dateFinished",required = false) String dateFinished,
    		@ModelAttribute Duct duct,
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="content",required=false) String content){
        return ductService.getDuctList(pageIndex,pageSize,duct,token,content,dateStart, dateFinished);
    }
    
    @RequestMapping(value="/getDuctStateSum",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<DuctPojos>> getDuctStateSum(){
        return ductService.getDuctStateSum();
    }
    
    @RequestMapping(value="/admin/getDuctByProjectId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<DuctPojo>> getDuctByProjectId(
    		@ModelAttribute Duct duct,
    		@RequestParam(value = "projectId",required = false) Long projectId,
    		@RequestParam(value = "token",required = true) String token){
        return ductService.getDuctByProjectId(projectId,token,duct);
    }
    
    @RequestMapping(value="/admin/getDuctBySelfId",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<DuctPojo> getDuctBySelfId(
    		@RequestParam(value = "id",required = false) Long id,
    		@RequestParam(value = "selfId",required = false) String selfId,
    		@RequestParam(value = "projectId",required = false) Long projectId){
        return ductService.getDuctBySelfId(id,selfId,projectId);
    }
    
    @RequestMapping(value="/admin/updateDuct",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateDuct(
            @ModelAttribute Duct duct,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return ductService.updateDuct(duct,token,request);
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
    	String filePath = "/fileupload/ducts/"+projectId;
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
    /**
     * 预制化文件导出
     * 
     */
    @RequestMapping(value="/admin/exportDuct",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> exportDuct(
    		@RequestParam(value = "projectId",required = true) Long projectId,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "dateStart",required = false) String dateStart,
            @RequestParam(value = "dateFinished",required = false) String dateFinished,
            HttpServletRequest request){
    	 
        return ductService.exportDuct(projectId, token, request, dateStart, dateFinished);
    }
}
