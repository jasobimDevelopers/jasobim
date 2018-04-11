package com.my.spring.service;

import com.my.spring.model.PageInfo;
import com.my.spring.model.QualityQuestion;
import com.my.spring.model.QualityQuestionPojo;
import com.my.spring.model.Question;
import com.my.spring.model.QuestionPojo;
import com.my.spring.utils.DataWrapper;
import com.my.spring.utils.DataWrappern;

import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface QualityQuestionService {
    DataWrapper<Void> updateQualityQuestion(QualityQuestionPojo question,String token, MultipartFile[] file, HttpServletRequest request,MultipartFile[] files);
    DataWrapper<List<QualityQuestionPojo>> getQualityQuestionList(String content,Long projectId,String token, Integer pageIndex, Integer pageSize, QualityQuestion question);
	DataWrapper<QualityQuestionPojo> getQualityQuestionDetailsByAdmin(Long questionId,String token, String weixin);
	DataWrapper<Void> addQualityQuestion(QualityQuestion question, String token, MultipartFile[] file, HttpServletRequest request, MultipartFile fileCode,MultipartFile[] voiceFile);
	DataWrapper<Void> deleteQualityQuestion(Long id, String token, HttpServletRequest request, Long projectId);
	DataWrapper<Void> deleteQualityQuestion(Long questionId, String token, HttpServletRequest request);
	DataWrapper<List<QualityQuestion>> getQualityQuestionsByLike(String content, String token);
	DataWrapper<Void> updateQualityQuestionState(Long questionId, String token, Integer state);
	DataWrapper<List<QualityQuestionPojo>> getQualityQuestionListByUserId(String token, Integer pageIndex, Integer pageSize);
	DataWrappern<PageInfo, List<QualityQuestionPojo>, HashMap<String, String>> getQualityQuestionHash(String content, Long projectId,
			String token, Integer pageIndex, Integer pageSize, QualityQuestion question);

}
