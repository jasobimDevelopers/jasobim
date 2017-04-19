package com.my.spring.controller;

import com.my.spring.enums.CallStatusEnum;
import com.my.spring.enums.ErrorCodeEnum;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionPojo;
import com.my.spring.service.QuestionService;
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
@RequestMapping(value="api/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @RequestMapping(value="/addQuestion", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addQuestion(
            @ModelAttribute Question question,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile[] file,
            @RequestParam(value = "fileCode", required = false) MultipartFile fileCode
            ){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=questionService.addQuestion(question,token,file,request,fileCode);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    //////根据项目id和问题id删除问题
    @RequestMapping(value="/admin/deleteQuestion")
    @ResponseBody
    public DataWrapper<Void> deleteQuestion(
            @RequestParam(value = "questionId",required = true) Long questionId,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return questionService.deleteQuestion(questionId,token,request,projectId);
    }
    //////根据问题id删除问题
    @RequestMapping(value="/admin/deleteQuestionById",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<Void> deleteQuestionById(
            @RequestParam(value = "questionId",required = true) Long questionId,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return questionService.deleteQuestion(questionId,token,request);
    }
    
    @RequestMapping(value="/admin/updateQuestion",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQuestion(
            @ModelAttribute QuestionPojo question,
            @RequestParam(value = "token",required = true) String token,
            HttpServletRequest request,
            @RequestParam(value = "file", required = false) MultipartFile[] file){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	dataWrapper=questionService.updateQuestion(question,token,file,request);
    	if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
        }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }
    @RequestMapping(value="/updateQuestionState",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQuestionState(
    		@RequestParam(value = "questionId",required = true) Long questionId,
            @RequestParam(value = "token",required = true) String token,
            @RequestParam(value = "state",required = true) Integer state
            ){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
    	dataWrapper=questionService.updateQuestionState(questionId,token,state);
    	if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
        }else{
            	dataWrapper.setErrorCode(ErrorCodeEnum.Error);
        }
        return dataWrapper;
    }

    @RequestMapping(value="/admin/getQuestionList",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QuestionPojo>> getQuestionList(
    		@RequestParam(value="content",required=false) String content,
    		@RequestParam(value="projectId",required=false) Long projectId,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Question question,
    		@RequestParam(value = "token",required = true) String token)
    {
        return questionService.getQuestionList(content,projectId,token,pageIndex,pageSize,question);
    }
    
    @RequestMapping(value="/getQuestionListByUserId",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QuestionPojo>> getQuestionListByUserId(
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@RequestParam(value = "token",required = true) String token)
    {
        return questionService.getQuestionListByUserId(token,pageIndex,pageSize);
    }
    
    /*
     * 通过问题id查找问题的详细信息
     * 
     * */
    @RequestMapping(value="/getQuestionDetails",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<QuestionPojo> getQuestionDetails(
    		@RequestParam(value="questionId",required=true) Long questionId,
    		@RequestParam(value="token",required=true) String token){
        return questionService.getQuestionDetailsByAdmin(questionId,token);
    }
    
    
    @RequestMapping(value="/getQuestionsByLike",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<Question>> getQuestionsByLike(
    		@RequestParam(value="content",required=true) String content,
    		@RequestParam(value="token",required=true) String token){
        return questionService.getQuestionsByLike(content,token);
    }
    
}
