package com.my.spring.DAO;

import com.my.spring.model.Question;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface QuestionDao {
    boolean addQuestion(Question question);
    boolean deleteQuestion(Long id);
    boolean updateQuestion(Question question);
    DataWrapper<List<Question>> getQuestionList();
    Question getById(Long id);
}
