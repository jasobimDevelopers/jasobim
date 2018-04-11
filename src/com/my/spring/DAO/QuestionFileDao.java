package com.my.spring.DAO;

import com.my.spring.model.QuestionFile;
import com.my.spring.utils.DataWrapper;

import java.util.List;

/**
 * Created by Administrator on 2016/6/22.
 */
public interface QuestionFileDao {
	boolean addQuestionFile(QuestionFile questionFile);
    boolean deleteQuestionFile(Long id);
    DataWrapper<List<QuestionFile>> getQuestionFileByQuestionId(Long questionId);
    DataWrapper<List<QuestionFile>> getQuestionFileList();
    DataWrapper<List<QuestionFile>> getQuestionFileListByUserId(Long userId);
	boolean deleteQuestionFileByQuestionId(Long id);
	DataWrapper<List<QuestionFile>> getQuestionFileByQualityId(Long id);
	boolean deleteQuestionFileByQualityId(Long id);
}
