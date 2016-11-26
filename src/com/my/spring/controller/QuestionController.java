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
            @RequestParam(value = "file", required = false) MultipartFile[] file
            ){
    	DataWrapper<Void> dataWrapper = new DataWrapper<Void>();
		dataWrapper=questionService.addQuestion(question,token,file,request);
		if(dataWrapper.getCallStatus()==CallStatusEnum.SUCCEED){
            	return dataWrapper;
    	}else{
    		dataWrapper.setErrorCode(ErrorCodeEnum.Error);
    	}
        return dataWrapper;
    }
    @RequestMapping(value="/admin/deleteQuestion")
    @ResponseBody
    public DataWrapper<Void> deleteQuestion(
            @RequestParam(value = "questionId",required = true) Long questionId,
            @RequestParam(value = "projectId",required = true) Long projectId,
            HttpServletRequest request,
            @RequestParam(value = "token",required = true) String token){
        return questionService.deleteQuestion(questionId,token,request,projectId);
    }

    @RequestMapping(value="/updateQuestion",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQuestion(
            @ModelAttribute Question question,
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


    @RequestMapping(value="/admin/getQuestionList",method = RequestMethod.GET)
    @ResponseBody
    public DataWrapper<List<QuestionPojo>> getQuestionList(
    		@RequestParam(value="projectId",required=false) Long projectId,
    		@RequestParam(value="pageIndex",required=false) Integer pageIndex,
    		@RequestParam(value="pageSize",required=false) Integer pageSize,
    		@ModelAttribute Question question,
    		@RequestParam(value = "token",required = true) String token)
    {
        return questionService.getQuestionList(projectId,token,pageIndex,pageSize,question);
    }
    @RequestMapping(value="/getQuestionDetails/{questionId}")
    @ResponseBody
    public DataWrapper<Question> getQuestionDetailsByAdmin(
    		@PathVariable(value="questionId") Long questionId,
    		@RequestParam(value="token",required=true) String token){
        return questionService.getQuestionDetailsByAdmin(questionId,token);
    }
    
}
