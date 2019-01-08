package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.PageInfo;
import com.my.spring.model.QualityQuestion;
import com.my.spring.model.QualityQuestionPojo;
import com.my.spring.service.QualityQuestionService;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.DataWrappern;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.util.HashMap;
import java.util.List;
import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping(value="api/quality")
public class QualityQuestionController {
    @Autowired
    QualityQuestionService questionService;
    @RequestMapping(value="/addQuality", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<QualityQuestion> addQuality(
            @ModelAttribute QualityQuestion question,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam(value = "fileCode", required = false) MultipartFile fileCode,
            @RequestParam(value = "voiceFile", required = false) MultipartFile[] voiceFile
            ){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	DataWrapper<QualityQuestion> dataWrappers = new DataWrapper<QualityQuestion>();
		dataWrapper=questionService.addQualityQuestion(question,token,file,request,fileCode,voiceFile);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
			dataWrappers.setCallStatus(CallStatusEnum.SUCCEED);
			dataWrappers.setData(question);
            return dataWrappers;
    	}else{
    		dataWrappers.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrappers;
    }
    //////根据项目id和问题id删除问题
    @RequestMapping(value="/admin/deleteQuality",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteQuality(
            @RequestParam(value = "qualityId",required = true) Long qualityId,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return questionService.deleteQualityQuestion(qualityId,token,request,projectId);
    }
    //////根据问题id删除问题
    @RequestMapping(value="/admin/deleteQualityById",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteQuestionById(
            @RequestParam(value = "qualityId",required = true) Long qualityId,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return questionService.deleteQualityQuestion(qualityId,token,request);
    }
    
    @RequestMapping(value="/admin/updateQuality",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQuestion(
            @ModelAttribute QualityQuestionPojo question,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam(value = "voiceFile", required = false) MultipartFile[] voiceFile){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	dataWrapper=questionService.updateQualityQuestion(question,token,file,request,voiceFile);
    	if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
        }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }
    @RequestMapping(value="/updateQualityState",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQualityState(
    		@RequestParam(value = "qualityId",required = true) Long qualityId,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "state",required = true) Integer state
            ){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	dataWrapper=questionService.updateQualityQuestionState(qualityId,token,state);
    	if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
        }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @RequestMapping(value="/admin/getQualityList",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QualityQuestionPojo>> getQuestionList(
    		@RequestParam(value="content",required=false) String content,
    		@RequestParam(value="projectId",required=false) Long projectId,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute QualityQuestion question,
    		@RequestParam(value = "token",required = true) String token)
    {
        return questionService.getQualityQuestionList(content,projectId,token,pageIndex,pageSize,question);
    }
    ////////////hashMap返回测试
    @RequestMapping(value="/admin/getQualityHash",method = RequestMethod.GET)
    @ResponseBody
    public DataWrappern< PageInfo,List<QualityQuestionPojo>,HashMap<String,String>>  getQualityHash(
    		@RequestParam(value="content",required=false) String content,
    		@RequestParam(value="searchType",required=false) Integer searchType,
    		@RequestParam(value="projectId",required=false) Long projectId,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute QualityQuestion question,
    		@RequestParam(value = "token",required = true) String token)
    {
        return questionService.getQualityQuestionHash(searchType,content,projectId,token,pageIndex,pageSize,question);
    }
    
    @RequestMapping(value="/getQualityListByUserId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QualityQuestionPojo>> getQualityListByUserId(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value = "token",required = true) String token)
    {
        return questionService.getQualityQuestionListByUserId(token,pageIndex,pageSize);
    }
    
    /*
     * 通过问题id查找问题的详细信息
     * 
     * */
    @RequestMapping(value="/getQualityDetails",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<QualityQuestionPojo> getQualityQuestionDetails(
    		@RequestParam(value="questionId",required=true) Long questionId,
    		@RequestParam(value="weixin",required=false) String weixin,
    		@RequestParam(value="token",required=false) String token){
        return questionService.getQualityQuestionDetailsByAdmin(questionId,token,weixin);
    }
    
    
    @RequestMapping(value="/getQualitysByLike",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QualityQuestion>> getQuestionsByLike(
    		@RequestParam(value="content",required=true) String content,
    		@RequestParam(value="token",required=true) String token){
        return questionService.getQualityQuestionsByLike(content,token);
    }
    
}
