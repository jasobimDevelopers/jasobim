package com.my.spring.controller;

import com.my.spring.model.QuestionFile;
import com.my.spring.service.QuestionFileService;
import com.my.spring.utils.DataWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
@Controller
@RequestMapping(value="api/questionFile")
public class QuestionFileController {
    @Autowired
    QuestionFileService questionFileService;
    @RequestMapping(value="addQuestionFile", method = RequestMethod.POST)
    @ResponseBody
    public DataWrapper<Void> addQuestionFile(
            @ModelAttribute QuestionFile questionFile,
            @RequestParam(value = "token",required = false) String token){
        return questionFileService.addQuestionFile(questionFile,token);
    }
    @RequestMapping(value="deleteQuestionFile")
    @ResponseBody
    public DataWrapper<Void> deleteQuestionFile(
            @RequestParam(value = "id",required = true) Long id,
            @RequestParam(value = "token",required = false) String token){
        return questionFileService.deleteQuestionFile(id,token);
    }



    @RequestMapping(value="getQuestionFileList")
    @ResponseBody
    public DataWrapper<List<QuestionFile>> getQuestionFileList(
            @RequestParam(value = "token",required = false) String token){
        return questionFileService.getQuestionFileList(token);
    }
    ////通过用户id查找留言
    @RequestMapping(value="getQuestionFileListByUserId")
    @ResponseBody
    public DataWrapper<List<QuestionFile>> getQuestionFileListByUserId(
    		@RequestParam(value = "userId",required = true) Long userId,
            @RequestParam(value = "token",required = false) String token){
        return questionFileService.getQuestionFileListByUserId(userId,token);
    }
}
