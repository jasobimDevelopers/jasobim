package com.my.spring.bimface.sdk.myController;


import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.bimface.sdk.bean.response.CategoryBean;
import com.my.spring.bimface.sdk.service.BimfaceService;
import com.my.spring.utils.DataWrapper;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("api/bimface")
public class BimfaceController {
	@Autowired
	BimfaceService bimfaceService;
	
	/*
	 * 获取模型的展示码
	 * */
    @RequestMapping(value = "/getModelViewToken",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> getUserList(
    		@RequestParam(value="fileId",required=true) Long fileId,
    		@RequestParam(value="token",required=false) String token){
        return bimfaceService.getViewTokenByFileId(fileId);
    }
    
    /*
     * 上传模型并转换
     * */
    @RequestMapping(value = "/uploadModelFile",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Long> uploadModelFile(
    		@RequestParam(value="modelFile",required=true) MultipartFile[] modelFile,
    		@RequestParam(value="token",required=true) String token,
    		@RequestParam(value="projectId",required=true) Long projectId,
    		HttpServletRequest request){
        return bimfaceService.uploadModelFile(modelFile[0],request,token,projectId);
    }
    
    /*
     * 通过集成模型的id获取集成模型的展示码
     * */
    @RequestMapping(value = "/getModeViewTokenByIntegrateId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<String> getModeViewTokenByIntegrateId(
    		@RequestParam(value="integrateId",required=true) Long integrateId,
    		@RequestParam(value="token",required=false) String token,
    		@RequestParam(value="projectId",required=true) Long projectId,
    		HttpServletRequest request){
        return bimfaceService.getModeViewTokenByIntegrateId(integrateId,request,token,projectId);
    }
    /*
     * 根据fileId获取模型的专业分类构件信息
     * */
    @RequestMapping(value="/getCategory",method= RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<CategoryBean>> getCategory(
    		@RequestParam(value="fileId",required=true) Long fileId,
    		@RequestParam(value="token",required=false) String token,
    		@RequestParam(value="projectId",required=false) Long projectId){
        return bimfaceService.getCategory(fileId,token,projectId);
    }
}
