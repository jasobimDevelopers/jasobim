package com.my.spring.DAO;

import com.my.spring.model.Question;
import com.my.spring.model.QuestionPojo;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface QuestionDao {
    boolean addQuestion(Question question);
    boolean deleteQuestion(Long id,Long projectId);
    boolean deleteQuestionByProjectId(Long projectId);
    boolean updateQuestion(Question question);
    DataWrapper<List<QuestionPojo>> getQuestionList(String content, Long projectId, Integer pageIndex, Integer pageSize, Question question,Long[] userIdList);
    Long getQuestionList();
    Long getQuestionListOfSort();
    Long getQuestionListOfImportant();
    Long getQuestionListOfUrgent();
    Question getById(Long id);
	boolean deleteQuestion(Long id);
	DataWrapper<List<Question>> getQuestionListByLike(String content);
}
