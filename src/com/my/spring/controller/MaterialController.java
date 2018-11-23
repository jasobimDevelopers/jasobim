package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Material;
import com.my.spring.model.MaterialImportLogPojo;
import com.my.spring.model.MaterialPojo;
import com.my.spring.service.MaterialService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="api/material")
public class MaterialController {
	@Autowired
    MaterialService materialService;
    @RequestMapping(value="/vs/addMaterial", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addMaterial(
            @ModelAttribute Material news,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=materialService.addMaterial(news,token);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/admin/deleteMaterial",method=RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteNews(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return materialService.deleteMaterial(id,token);
    }

    @RequestMapping(value="/updateMaterial",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateMaterial(
            @ModelAttribute Material News,
            @RequestParam(value = "token",required = false) String token){
        return materialService.updateMaterial(News,token);
    }


    @RequestMapping(value="/vs/getMaterialList", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MaterialPojo>> getMaterialList(
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Material News){
        return materialService.getMaterialList(token,pageIndex,pageSize,News);
    }
    /////导入物资清单
    @RequestMapping(value="/web/importMaterial", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> importMaterialList(
    		 @RequestParam(value = "file", required = false) MultipartFile file,
             @RequestParam(value = "token",required = true) String token,
             @ModelAttribute Material News,
            HttpServletRequest request){
    	return materialService.importMaterial(file,request,token,News);
    }
   //手机端扫描录入
    /////导入物资清单
    @RequestMapping(value="/app/importMaterial", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> importMaterialFile(
    		 @RequestParam(value = "fileUrl", required = false) String fileUrl,
    		 @RequestParam(value = "htmlUrl", required = false) String htmlUrl,
             @RequestParam(value = "token",required = true) String token,
             @ModelAttribute Material News,
            HttpServletRequest request){
    	return materialService.importAppMaterial(htmlUrl,fileUrl,request,token,News);
    }
    /*
     * 手机端获取扫描记录
     * 
     * */
    @RequestMapping(value="/app/getImportMaterialLog", method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<MaterialImportLogPojo>> getImportMaterialLog(
    		 @RequestParam(value = "projectId", required = false) Long projectId,
             @RequestParam(value = "token",required = true) String token){
    	return materialService.getImportMaterialLog(token,projectId);
    }
}
