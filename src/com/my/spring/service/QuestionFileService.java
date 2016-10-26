package com.my.spring.service;

import com.my.spring.model.QuestionFile;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface QuestionFileService {
	DataWrapper<Void> addQuestionFile(QuestionFile questionFile,String token);
    DataWrapper<List<QuestionFile>> getQuestionFileList(String token);
    DataWrapper<List<QuestionFile>> getQuestionFileListByUserId(Long userId,String token);
	DataWrapper<Void> deleteQuestionFile(Long id,String token);
}
