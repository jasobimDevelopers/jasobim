package com.my.spring.DAO;

import com.my.spring.model.QualityQuestion;
import com.my.spring.model.QualityQuestionPojo;
import com.my.spring.model.QuestionCopy;
import com.my.spring.utils.DataWrapper;
import java.util.List;

public interface QualityQuestionDao {
    boolean addQualityQuestion(QualityQuestion question);
    boolean deleteQualityQuestion(Long id,Long projectId);
    boolean deleteQualityQuestionByProjectId(Long projectId);
    boolean updateQualityQuestion(QualityQuestion question);
    Long getQualityQuestionList();
    Long getQualityQuestionListOfSort();
    Long getQualityQuestionListOfImportant();
    Long getQualityQuestionListOfUrgent();
    QualityQuestion getById(Long id);
	boolean deleteQualityQuestion(Long id);
	DataWrapper<List<QualityQuestion>> getQualityQuestionListByLike(String content);
	DataWrapper<List<QualityQuestion>> getQualityQuestionList(String content, Long projectId, Integer pageIndex, Integer pageSize, QualityQuestion question, Long[] userIdList,String projectList);
	DataWrapper<List<QualityQuestion>> getQualityQuestionLists(Integer searchType,String content, Long projectId, Integer pageIndex, Integer pageSize, QualityQuestion question, Long[] userIdList);
	DataWrapper<List<QualityQuestionPojo>> getQualityQuestionList(Integer pageIndex, Integer pageSize, QualityQuestion question);
	List<QuestionCopy> getQuestionListByAdmin(Long long1, Integer pageIndex, Integer pageSize);
	List<QuestionCopy> getQuestionListByLeader(Long id, Integer pageIndex, Integer pageSize);
	List<QuestionCopy> getQuestionListByNorUser(Long id, Integer pageIndex, Integer pageSize);
	boolean deleteQualityQuestionByUserId(Long userId);
}
