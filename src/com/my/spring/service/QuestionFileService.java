package com.my.spring.service;

import com.my.spring.model.QuestionFile;
import com.my.spring.utils.DataWrapper;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.multipart.MultipartFile;

public interface QuestionFileService {
	DataWrapper<Void> addQuestionFile(QuestionFile questionFile,String token, MultipartFile file,HttpServletRequest request);
    DataWrapper<List<QuestionFile>> getQuestionFileList(String token);
    DataWrapper<List<QuestionFile>> getQuestionFileListByUserId(Long userId,String token);
	DataWrapper<Void> deleteQuestionFile(Long id, String token, Long fileid, HttpServletRequest request);
}
