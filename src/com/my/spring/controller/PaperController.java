package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Paper;
import com.my.spring.model.PaperPojo;
import com.my.spring.service.PaperService;
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
@RequestMapping(value="api/paper")
public class PaperController {
    @Autowired
    PaperService paperService;

    @RequestMapping(value="/admin/uploadPaper", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addPaper(
            @ModelAttribute Paper paper,
            @RequestParam(value = "fileList", required = true) MultipartFile[] fileList,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	for(int i=0;i<fileList.length;i++){
    		dataWrapper=paperService.addPaper(paper, token, fileList[i], request);
    		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
            }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
            }
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/deletePaper")
    @ResponseBody
    public DataWrapper<Void> deletePaper(
            @RequestParam(value = "id",required = true) Long id,
            HttpServletRequest request,
            @RequestParam(value = "fileid",required = true) Long fileid,
            @RequestParam(value = "token",required = true) String token){
        return paperService.deletePaper(id,fileid,token,request);
    }
    
    @RequestMapping(value="/admin/deletePaper")
    @ResponseBody
    public DataWrapper<Void> deletePaperByAdmin(
            @RequestParam(value = "id",required = true) Long id,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return paperService.deletePaperByAdmin(id,token,request);
    }
    
    @RequestMapping(value="/updatePaper",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updatePaper(
            @ModelAttribute Paper paper,
            @RequestParam(value = "token",required = true) String token){
        System.out.println(paper);
        return paperService.updatePaper(paper,token);
    }


    @RequestMapping(value="/admin/getPaperList",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<PaperPojo>> getPaperList(
    		@RequestParam(value="projectId",required=true) Long projectId,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Paper paper,
    		@RequestParam(value="token",required=true) String token){
        return paperService.getPaperList(projectId,token,pageIndex,pageSize,paper);
    }
    @RequestMapping(value="/getPaperDetails/{paperId}")
    @ResponseBody
    public DataWrapper<Paper> getPaperDetailsByAdmin(
    		@PathVariable(value="paperId") Long paperId,
    		@RequestParam(value="token",required=true) String token){
        return paperService.getPaperDetailsByAdmin(paperId,token);
    }
    
}
