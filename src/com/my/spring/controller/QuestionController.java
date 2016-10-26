package com.my.spring.controller;

import com.my.spring.model.Question;
import com.my.spring.service.QuestionService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/question")
public class QuestionController {
    @Autowired
    QuestionService questionService;
    @RequestMapping(value="addQuestion", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addQuestion(
            @ModelAttribute Question question,
            @RequestParam(value = "token",required = true) String token){
        return questionService.addQuestion(question,token);
    }
    @RequestMapping(value="deleteQuestion")
    @ResponseBody
    public DataWrapper<Void> deleteQuestion(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = true) String token){
        return questionService.deleteQuestion(id,token);
    }

    @RequestMapping(value="updateQuestion",method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> updateQuestion(
            @ModelAttribute Question question,
            @RequestParam(value = "token",required = true) String token){
        System.out.println(question);
        return questionService.updateQuestion(question,token);
    }


    @RequestMapping(value="getQuestionList")
    @ResponseBody
    public DataWrapper<List<Question>> getQuestionList(
    		@RequestParam(value = "token",required = true) String token)
    {
        return questionService.getQuestionList(token);
    }
    @RequestMapping(value="getQuestionDetails/{questionId}")
    @ResponseBody
    public DataWrapper<Question> getQuestionDetailsByAdmin(
    		@PathVariable(value="questionId") Long questionId,
    		@RequestParam(value="token",required=true) String token){
        return questionService.getQuestionDetailsByAdmin(questionId,token);
    }
    
}
