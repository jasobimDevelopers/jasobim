package com.my.spring.service;

import com.my.spring.model.Question;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface QuestionService {
    DataWrapper<Void> addQuestion(Question question, String token);
    DataWrapper<Void> deleteQuestion(Long id,String token);
    DataWrapper<Void> updateQuestion(Question question,String token);
    DataWrapper<List<Question>> getQuestionList(String token);
	DataWrapper<Question> getQuestionDetailsByAdmin(Long questionId,String token);
}
