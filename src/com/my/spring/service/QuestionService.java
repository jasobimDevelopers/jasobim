package com.my.spring.service;

import com.my.spring.model.Question;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface QuestionService {
    DataWrapper<Void> deleteQuestion(Long id,String token);
    DataWrapper<Void> updateQuestion(Question question,String token);
    DataWrapper<List<Question>> getQuestionList(String token);
	DataWrapper<Question> getQuestionDetailsByAdmin(Long questionId,String token);
	DataWrapper<Void> addQuestion(Question question, String token, MultipartFile file, HttpServletRequest request);
}
