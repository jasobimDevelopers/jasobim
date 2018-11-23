package com.my.spring.DAO;

import com.my.spring.model.Question;
import com.my.spring.model.QuestionCopy;
import com.my.spring.model.QuestionPojo;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface QuestionDao {
    boolean addQuestion(Question question);
    boolean deleteQuestion(Long id,Long projectId);
    boolean deleteQuestionByProjectId(Long projectId);
    boolean updateQuestion(Question question);
    Long getQuestionList();
    Long getQuestionListOfSort();
    Long getQuestionListOfImportant();
    Long getQuestionListOfUrgent();
    Question getById(Long id);
	boolean deleteQuestion(Long id);
	DataWrapper<List<Question>> getQuestionListByLike(String content);
	DataWrapper<List<Question>> getQuestionList(String content, Long projectId, Integer pageIndex, Integer pageSize, Question question, Long[] userIdList,String projectList);
	DataWrapper<List<Question>> getQuestionLists(Integer searchType,String content, Long projectId, Integer pageIndex, Integer pageSize, Question question, Long[] userIdList);
	DataWrapper<List<QuestionPojo>> getQuestionList(Integer pageIndex, Integer pageSize, Question question);
	List<QuestionCopy> getQuestionListByLeader(Long id, Integer pageIndex, Integer pageSize);
	List<QuestionCopy> getQuestionListByAdmin(Long long1, Integer pageIndex, Integer pageSize);
	List<QuestionCopy> getQuestionListByNorUser(Long id, Integer pageIndex, Integer pageSize);
	boolean deleteQuestionByUserId(Long userId);
}
