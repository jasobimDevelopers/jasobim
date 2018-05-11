package com.my.spring.service;

import com.my.spring.model.PageInfo;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionCopy;
import com.my.spring.model.QuestionPojo;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.DataWrappern;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface QuestionService {
    DataWrapper<Void> updateQuestion(QuestionPojo question,String token, MultipartFile[] file, HttpServletRequest request,MultipartFile[] files);
    DataWrapper<List<QuestionPojo>> getQuestionList(String content,Long projectId,String token, Integer pageIndex, Integer pageSize, Question question);
	DataWrapper<QuestionPojo> getQuestionDetailsByAdmin(Long questionId,String token, String weixin);
	DataWrapper<Void> addQuestion(Question question, String token, MultipartFile[] file, HttpServletRequest request, MultipartFile fileCode,MultipartFile[] voiceFile);
	DataWrapper<Void> deleteQuestion(Long id, String token, HttpServletRequest request, Long projectId);
	DataWrapper<Void> deleteQuestion(Long questionId, String token, HttpServletRequest request);
	DataWrapper<List<Question>> getQuestionsByLike(String content, String token);
	DataWrapper<Void> updateQuestionState(Long questionId, String token, Integer state);
	DataWrapper<List<QuestionPojo>> getQuestionListByUserId(String token, Integer pageIndex, Integer pageSize);
	DataWrappern<PageInfo, List<QuestionPojo>, HashMap<String, String>> getQuestionHash(Integer searchType,String content, Long projectId,
			String token, Integer pageIndex, Integer pageSize, Question question);
	DataWrapper<List<QuestionCopy>> getQuestionListOfNotRead(String token,Integer pageIndex, Integer pageSize);
}
